package www0abd.oss.veyzoom.mixin;

import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import www0abd.oss.veyzoom.AnalyticsClient;
import www0abd.oss.veyzoom.Constants;
import www0abd.oss.veyzoom.ZoomKeyMapping;
import www0abd.oss.veyzoom.ZoomController;
import www0abd.oss.veyzoom.ZoomConfig;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Inject(at = @At("TAIL"), method = "<init>")
    private void veyzoom$initializeAnalytics(CallbackInfo info) {
        Minecraft minecraft = (Minecraft) (Object) this;
        AnalyticsClient.initialize(minecraft.gameDirectory.toPath());
        ZoomConfig.load(minecraft.gameDirectory.toPath());

        Constants.LOG.info("VeyZoom initialized for Minecraft {}", SharedConstants.getCurrentVersion().name());
    }

@Inject(at = @At("TAIL"), method = "tick")
private void veyzoom$analyticsTick(CallbackInfo info) {
    ZoomController.tick(ZoomKeyMapping.isZooming());
    AnalyticsClient.tick(ZoomKeyMapping.isZooming());
}
}
