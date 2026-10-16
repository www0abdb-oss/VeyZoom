package www0abd.oss.veyzoom;

import dev.faststats.ErrorTracker;
import dev.faststats.Metrics;
import dev.faststats.fabric.FabricContext;
import net.fabricmc.api.ModInitializer;

public final class FastStatsIntegration implements ModInitializer {

    public static final ErrorTracker ERROR_TRACKER =
        ErrorTracker.contextAware();

    private FabricContext context;

    @Override
    public void onInitialize() {
        String token = "fe4844a783b1df40044393312661ef31";

        if (token.isEmpty()) {
            Constants.LOG.info("FastStats is disabled: no project token configured.");
            return;
        }

        try {
            context = new FabricContext.Factory(Constants.MOD_ID, token)
                .errorTrackerService(ERROR_TRACKER)
                .metrics(Metrics.Factory::create)
                .create();

            context.ready();
            Constants.LOG.info("FastStats integration initialized.");
        } catch (RuntimeException exception) {
            context = null;
            Constants.LOG.error("Failed to initialize FastStats integration.", exception);
        }
    }
}
