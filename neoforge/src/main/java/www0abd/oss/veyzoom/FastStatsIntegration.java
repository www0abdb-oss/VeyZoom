
package www0abd.oss.veyzoom;

import dev.faststats.ErrorTracker;
import dev.faststats.Metrics;
import dev.faststats.neoforge.NeoForgeContext;

public final class FastStatsIntegration {

    public static final ErrorTracker ERROR_TRACKER =
        ErrorTracker.contextAware();

    private static final String TOKEN_PROPERTY =
        "veyzoom.faststats.token";

    private static NeoForgeContext context;

    private FastStatsIntegration() {
    }

    public static synchronized void start() {
        if (context != null) {
            return;
        }

        String token = "fe4844a783b1df40044393312661ef31";

        if (token.isEmpty()) {
            Constants.LOG.warn(
                "FastStats is disabled because no project token was configured."
            );
            return;
        }

        try {
            context = new NeoForgeContext.Factory(
                Constants.MOD_ID,
                token
            )
                .errorTrackerService(ERROR_TRACKER)
                .metrics(Metrics.Factory::create)
                .create();

            context.ready();

            Constants.LOG.info("FastStats integration initialized.");
        } catch (RuntimeException exception) {
            context = null;
            Constants.LOG.error(
                "Failed to initialize FastStats integration.",
                exception
            );
        }
    }

    public static synchronized void shutdown() {
        if (context == null) {
            return;
        }

        try {
            context.shutdown();
            Constants.LOG.info("FastStats integration stopped.");
        } catch (RuntimeException exception) {
            Constants.LOG.error(
                "Failed to stop FastStats integration.",
                exception
            );
        } finally {
            context = null;
        }
    }
}
