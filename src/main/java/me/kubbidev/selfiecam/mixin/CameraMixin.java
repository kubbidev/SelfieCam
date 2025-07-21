package me.kubbidev.selfiecam.mixin;

import me.kubbidev.selfiecam.CameraView;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.Window;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Inject(method = "update", at = @At("RETURN"))
    private void update(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        if (CameraView.isDisabled()) {
            return;
        }

        Window window = MinecraftClient.getInstance().getWindow();
        if (window.getWidth() >= window.getHeight()) {
            float cameraAngle = Math.max(3.0F - (90.0F + focusedEntity.getRotationClient().x) / 10.0F, 0.0F);
            applyCameraRotation(true, cameraAngle);
        } else {
            applyCameraRotation(false, 0);
        }
    }

    private void applyCameraRotation(boolean landscape, float cameraAngle) {
        //noinspection DataFlowIssue
        Camera camera = (Camera) (Object) this;
        setRotation(camera.getYaw() + CameraView.instance.getYaw(landscape) - cameraAngle, camera.getPitch() - cameraAngle);
    }

    @Shadow
    public abstract void setRotation(float yaw, float pitch);
}
