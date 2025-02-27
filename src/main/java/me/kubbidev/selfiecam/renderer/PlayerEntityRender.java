package me.kubbidev.selfiecam.renderer;

import me.kubbidev.selfiecam.camera.CameraView;
import me.kubbidev.selfiecam.SelfieCamMod;
import me.kubbidev.selfiecam.mixin.CameraAccessor;
import me.kubbidev.selfiecam.mixin.LivingEntityRendererAccessor;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.Window;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class PlayerEntityRender {
    public static Perspective defaultPerspective;
    public static boolean featureRegistered = false;

    public static void setAngles(BipedEntityModel<?> model, LivingEntity livingEntity, float f, float g, float h, float i, float j) {
        if (CameraView.isDisabled()) return;

        if (livingEntity == MinecraftClient.getInstance().player) {
            CameraView.INSTANCE.accept(model, Math.max(3.0F - (90.0F + livingEntity.getRotationClient().x) / 18.0F, 0.0F));
        }
    }

    public static void registerListener() {
        ClientTickEvents.END_CLIENT_TICK.register(PlayerEntityRender::clientTick);
    }

    private static void clientTick(MinecraftClient client) {
        if (client.player == null) return;

        if (SelfieCamMod.SCREENSHOT_KEY.wasPressed()) {
            whenKeyPressed(client);
        }

        if (CameraView.isEnabled()) {
            client.options.setPerspective(Perspective.THIRD_PERSON_FRONT);
        } else if (defaultPerspective != null) {
            client.options.setPerspective(defaultPerspective);
            defaultPerspective = null;
        }
    }

    private static void whenKeyPressed(MinecraftClient client) {
        if (CameraView.isDisabled()) {
            CameraView.INSTANCE = CameraView.RIGHT_SELFIE;
        } else if (CameraView.INSTANCE == CameraView.RIGHT_SELFIE) {
            CameraView.INSTANCE = CameraView.LEFT_SELFIE;
        } else if (CameraView.INSTANCE == CameraView.LEFT_SELFIE) {
            CameraView.INSTANCE = CameraView.RIGHT_SELFIE_STICK;
        } else if (CameraView.INSTANCE == CameraView.RIGHT_SELFIE_STICK) {
            CameraView.INSTANCE = CameraView.LEFT_SELFIE_STICK;
        } else {
            CameraView.INSTANCE = null;
        }

        if (defaultPerspective == null) {
            defaultPerspective = client.options.getPerspective();
        }
    }

    public static void onEntityRender(LivingEntity livingEntity, LivingEntityRenderer<?, ?> renderer) {
        if (!featureRegistered && livingEntity instanceof PlayerEntity) {
            //noinspection unchecked
            ((LivingEntityRendererAccessor) renderer).invokeAddFeature(new SelfieStickFeature(
                    (FeatureRendererContext<PlayerEntity, PlayerEntityModel<PlayerEntity>>) renderer
            ));
            featureRegistered = true;
        }
    }

    public static void onCameraUpdate(Camera camera) {
        if (CameraView.isDisabled() || camera.getFocusedEntity() == null) {
            return;
        }

        Window window = MinecraftClient.getInstance().getWindow();
        if (window.getWidth() < window.getHeight()) {
            ((CameraAccessor) camera).invokeSetRotation(camera.getYaw() + CameraView.INSTANCE.getPortraitYaw(), camera.getPitch());
        } else {
            float cameraAngle = Math.max(3.0F - (90.0F + camera.getFocusedEntity().getRotationClient().x) / 10.0F, 0.0F);
            ((CameraAccessor) camera).invokeSetRotation(camera.getYaw() + CameraView.INSTANCE.getYaw() - cameraAngle, camera.getPitch() - cameraAngle);
        }
    }

    public static void onFovRender(Camera camera, CallbackInfoReturnable<Double> ci) {
        if (CameraView.isDisabled() || camera.getFocusedEntity() == null) {
            return;
        }

        float pitch = camera.getFocusedEntity().getRotationClient().x;
        float cameraAngle = Math.max(4.0F - (90.0F + camera.getFocusedEntity().getRotationClient().x) / 10.0F, 0.0F);

        Window window = MinecraftClient.getInstance().getWindow();
        if (window.getWidth() < window.getHeight()) {
            if (pitch < -40.0F) {
                ci.setReturnValue(CameraView.INSTANCE.getPortraitFov() - cameraAngle * 1.5);
            } else if (pitch > 40.0F) {
                cameraAngle = (90.0F + pitch) / 10.0F - 14.0F;
                ci.setReturnValue(CameraView.INSTANCE.getPortraitFov() - cameraAngle * 2.5);
            } else {
                ci.setReturnValue((double) CameraView.INSTANCE.getPortraitFov());
            }
        } else if (pitch < -40.0F) {
            ci.setReturnValue(CameraView.INSTANCE.getFov() - cameraAngle / 1.5);
        } else if (pitch > 40.0F) {
            cameraAngle = (90.0F + pitch) / 10.0F - 14.0F;
            ci.setReturnValue(CameraView.INSTANCE.getFov() - cameraAngle * 2.5);
        } else {
            ci.setReturnValue((double) CameraView.INSTANCE.getFov());
        }
    }
}
