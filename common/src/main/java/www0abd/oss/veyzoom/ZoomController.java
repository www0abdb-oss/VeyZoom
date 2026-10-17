package www0abd.oss.veyzoom;

public final class ZoomController {
    private static final double MIN_ZOOM = 1.0;

    private static double currentZoom = MIN_ZOOM;
    private static double targetZoom = 4.0;

    private ZoomController() {
    }

    public static double getCurrentZoom() {
        return currentZoom;
    }

    public static double getTargetZoom() {
        return targetZoom;
    }

    public static void adjustZoom(double scroll) {
        if (scroll == 0.0) {
            return;
        }

        targetZoom = clamp(
                targetZoom * Math.pow(ZoomConfig.getZoomStep(), -scroll),
                MIN_ZOOM,
                ZoomConfig.getMaxZoom()
        );
    }

    public static void tick(boolean active) {
        double target = active ? targetZoom : MIN_ZOOM;
        double smoothing = ZoomConfig.getSmoothing();

        currentZoom += (target - currentZoom) * smoothing;

        if (Math.abs(target - currentZoom) < 0.001) {
            currentZoom = target;
        }
    }

    public static void applyConfig() {
        targetZoom = clamp(
                ZoomConfig.getDefaultZoom(),
                MIN_ZOOM,
                ZoomConfig.getMaxZoom()
        );
    }

    public static void reset() {
        currentZoom = MIN_ZOOM;
        applyConfig();
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
