package www0abd.oss.veyzoom;

import net.minecraft.SharedConstants;
import www0abd.oss.veyzoom.platform.Services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Locale;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public final class AnalyticsClient {
    private static final String ENDPOINT = "https://veyzoom-analytics.www0abdb.workers.dev/event";
    private static final HttpClient HTTP = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    private static String installId;
    private static boolean enabled;
    private static boolean previousZoomState;
    private static boolean initialized;

    private AnalyticsClient() {
    }

    public static synchronized void initialize(Path gameDirectory) {
        if (initialized) {
            return;
        }

        initialized = true;

        try {
            Path configDirectory = gameDirectory.resolve("config");
            Path configFile = configDirectory.resolve("veyzoom-analytics.properties");

            Files.createDirectories(configDirectory);

            Properties properties = new Properties();

            if (Files.exists(configFile)) {
                try (InputStream input = Files.newInputStream(configFile)) {
                    properties.load(input);
                }
            }

            enabled = Boolean.parseBoolean(properties.getProperty("enabled", "true"));

            installId = properties.getProperty("installId");

            if (installId == null || !installId.matches(
                    "(?i)[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")) {
                installId = UUID.randomUUID().toString();
            }

            properties.setProperty("enabled", Boolean.toString(enabled));
            properties.setProperty("installId", installId);

            try (OutputStream output = Files.newOutputStream(configFile)) {
                properties.store(output, "VeyZoom Analytics");
            }

            if (enabled) {
                sendEvent("session");
            }
        } catch (IOException exception) {
            Constants.LOG.warn("Unable to initialize VeyZoom Analytics");
        }
    }

    public static void tick(boolean zooming) {
        if (!initialized || !enabled) {
            previousZoomState = zooming;
            return;
        }

        if (zooming && !previousZoomState) {
            sendEvent("zoom_activated");
        }

        previousZoomState = zooming;
    }

    private static void sendEvent(String event) {
        if (!enabled || installId == null
                || ENDPOINT.contains("YOUR-WORKER.workers.dev")) {
            return;
        }

        String platformName = Services.PLATFORM.getPlatformName().toLowerCase(Locale.ROOT);
        String platform = platformName.contains("neo") ? "neoforge" : "fabric";
        String minecraftVersion = SharedConstants.getCurrentVersion().name();

        String body = """
                {
                  "installId": "%s",
                  "event": "%s",
                  "platform": "%s",
                  "minecraftVersion": "%s",
                  "modVersion": "1.0.0"
                }
                """.formatted(installId, event, platform, minecraftVersion);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ENDPOINT))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .build();

        CompletableFuture<HttpResponse<Void>> future = HTTP.sendAsync(
                request,
                HttpResponse.BodyHandlers.discarding()
        );

        future.exceptionally(error -> {
            Constants.LOG.debug("VeyZoom Analytics request failed");
            return null;
        });
    }
}
