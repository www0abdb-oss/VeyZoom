package www0abd.oss.veyzoom;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(
        modid = Constants.MOD_ID,
        value = Dist.CLIENT
)
public final class VeyZoomNeoForgeClientTicks {
    private VeyZoomNeoForgeClientTicks() {
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft client = Minecraft.getInstance();
        AnalyticsClient.initialize(client.gameDirectory.toPath());
        AnalyticsClient.tick(ZoomKeyMapping.isZooming());
    }
}
