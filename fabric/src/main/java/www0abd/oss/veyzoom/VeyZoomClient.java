package www0abd.oss.veyzoom;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;

public class VeyZoomClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ZoomKeyMapping.KEY = KeyMappingHelper.registerKeyMapping(
                new KeyMapping(
                        "key.veyzoom.zoom",
                        InputConstants.Type.KEYSYM,
                        InputConstants.KEY_C,
                        KeyMapping.Category.MISC
                )
        );
    }
}
