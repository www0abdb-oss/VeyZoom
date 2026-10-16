package www0abd.oss.veyzoom;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(
        modid = Constants.MOD_ID,
        value = Dist.CLIENT
)
public final class VeyZoomClientEvents {
    private VeyZoomClientEvents() {
    }

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        ZoomKeyMapping.KEY = new KeyMapping(
                "key.veyzoom.zoom",
                InputConstants.Type.KEYSYM,
                InputConstants.KEY_C,
                KeyMapping.Category.MISC
        );
        event.register(ZoomKeyMapping.KEY);
    }
}
