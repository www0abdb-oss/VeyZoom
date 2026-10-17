package www0abd.oss.veyzoom;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class ZoomConfig {
    private static final String FILE_NAME = "veyzoom.properties";

    private static double defaultZoom = 4.0;
    private static double maxZoom = 30.0;
    private static double zoomStep = 1.15;
    private static double smoothing = 0.25;

    private ZoomConfig() {
    }

    public static void load(Path gameDirectory) {
        Path directory = gameDirectory.resolve("config");
        Path file = directory.resolve(FILE_NAME);
        Properties properties = new Properties();

        try {
            Files.createDirectories(directory);

            if (Files.exists(file)) {
                try (InputStream input = Files.newInputStream(file)) {
                    properties.load(input);
                }
            }

            defaultZoom = readDouble(properties, "defaultZoom", 4.0, 1.0, 30.0);
            maxZoom = readDouble(properties, "maxZoom", 30.0, 1.0, 100.0);
            zoomStep = readDouble(properties, "zoomStep", 1.15, 1.01, 2.0);
            smoothing = readDouble(properties, "smoothing", 0.25, 0.01, 1.0);

            defaultZoom = Math.min(defaultZoom, maxZoom);

            properties.setProperty("defaultZoom", Double.toString(defaultZoom));
            properties.setProperty("maxZoom", Double.toString(maxZoom));
            properties.setProperty("zoomStep", Double.toString(zoomStep));
            properties.setProperty("smoothing", Double.toString(smoothing));

            try (OutputStream output = Files.newOutputStream(file)) {
                properties.store(output, "VeyZoom Configuration");
            }

            ZoomController.applyConfig();
            Constants.LOG.info("VeyZoom configuration loaded");
        } catch (IOException exception) {
            Constants.LOG.error("Failed to load VeyZoom configuration", exception);
        }
    }

    private static double readDouble(
            Properties properties,
            String key,
            double fallback,
            double min,
            double max
    ) {
        try {
            double value = Double.parseDouble(properties.getProperty(key, Double.toString(fallback)));

            if (!Double.isFinite(value)) {
                return fallback;
            }

            return Math.max(min, Math.min(max, value));
        } catch (NumberFormatException exception) {
            return fallback;
        }
    }

    public static double getDefaultZoom() {
        return defaultZoom;
    }

    public static double getMaxZoom() {
        return maxZoom;
    }

    public static double getZoomStep() {
        return zoomStep;
    }

    public static double getSmoothing() {
        return smoothing;
    }
}
