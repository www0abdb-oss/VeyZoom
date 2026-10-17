
package www0abd.oss.veyzoom.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import www0abd.oss.veyzoom.ZoomController;

@Mixin(Camera.class)
public abstract class CameraFovMixin {
    @ModifyReturnValue(method = "calculateFov", at = @At("RETURN"))
    private float veyzoom$modifyFov(float original) {
        double zoom = ZoomController.getCurrentZoom();
        return (float) (original / Math.max(1.0, zoom));
    }
}
