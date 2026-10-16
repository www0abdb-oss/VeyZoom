package www0abd.oss.veyzoom.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import www0abd.oss.veyzoom.ZoomKeyMapping;

@Mixin(Camera.class)
public abstract class CameraFovMixin {
    @ModifyReturnValue(method = "calculateFov", at = @At("RETURN"))
    private float veyzoom$modifyFov(float original) {
        return ZoomKeyMapping.isZooming()
                ? Math.max(5.0F, original * 0.25F)
                : original;
    }
}
