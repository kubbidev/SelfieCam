package me.kubbidev.selfiecam.mixin;

import me.kubbidev.selfiecam.CameraView;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRenderMixin {

    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
    private void getFov(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> ci) {
        if (CameraView.isDisabled() || camera.getFocusedEntity() == null) {
            return;
        }

        float pitch = camera.getFocusedEntity().getRotationClient().x;
        float cameraAngle = Math.max(4.0F - (90.0F + camera.getFocusedEntity().getRotationClient().x) / 10.0F, 0.0F);

        Window window = MinecraftClient.getInstance().getWindow();
        applyCameraFov(window.getWidth() >= window.getHeight(), pitch, cameraAngle, ci);
    }

    private void applyCameraFov(boolean landscape, float pitch, float cameraAngle, CallbackInfoReturnable<Double> ci) {
        if (pitch < -40.0F) {
            ci.setReturnValue(CameraView.instance.getFov(landscape) - cameraAngle / 1.5);
        } else if (pitch > 40.0F) {
            cameraAngle = (90.0F + pitch) / 10.0F - 14.0F;
            ci.setReturnValue(CameraView.instance.getFov(landscape) - cameraAngle * 2.5);
        } else {
            ci.setReturnValue((double) CameraView.instance.getFov(landscape));
        }
    }
}
