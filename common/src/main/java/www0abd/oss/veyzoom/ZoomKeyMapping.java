package www0abd.oss.veyzoom;

import net.minecraft.client.KeyMapping;

public final class ZoomKeyMapping {
    public static KeyMapping KEY;

    private ZoomKeyMapping() {
    }

    public static boolean isZooming() {
        return KEY != null && KEY.isDown();
    }
}
