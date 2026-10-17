package www0abd.oss.veyzoom.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import www0abd.oss.veyzoom.ZoomController;
import www0abd.oss.veyzoom.ZoomKeyMapping;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerScrollMixin {
    @Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
    private void veyzoom$onScroll(
            long window,
            double horizontal,
            double vertical,
            CallbackInfo info
    ) {
        Minecraft client = Minecraft.getInstance();

        if (ZoomKeyMapping.isZooming()
                && client.player != null
                && vertical != 0.0) {
            ZoomController.adjustZoom(-vertical);
            info.cancel();
        }
    }
}
