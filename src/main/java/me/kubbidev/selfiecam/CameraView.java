package me.kubbidev.selfiecam;

import net.minecraft.client.render.entity.model.BipedEntityModel;

public enum CameraView {
    RIGHT_SELFIE(15.0F, 5.0F, 30.0F, 3.0F, (bipedEntityModel, cameraAngle) -> {
        bipedEntityModel.rightArm.pitch = bipedEntityModel.head.pitch - 1.8F - cameraAngle / 32.0F;
        bipedEntityModel.rightArm.yaw   = bipedEntityModel.head.yaw   + 0.5F + cameraAngle / 6.0F;
        bipedEntityModel.rightArm.roll  = -0.2F - cameraAngle / 3.0F;
    }),
    LEFT_SELFIE(15.0F, -5.0F, 30.0F, -3.0F, (bipedEntityModel, cameraAngle) -> {
        bipedEntityModel.leftArm.pitch = bipedEntityModel.head.pitch - 1.8F + cameraAngle / 32.0F;
        bipedEntityModel.leftArm.yaw   = bipedEntityModel.head.yaw   - 0.5F + cameraAngle / 6.0F;
        bipedEntityModel.leftArm.roll  = 0.2F + cameraAngle / 3.0F;
    }),
    RIGHT_SELFIE_STICK(30.0F, 8.0F, 45.0F, 2.0F, (bipedEntityModel, cameraAngle) -> {
        bipedEntityModel.rightArm.pitch = bipedEntityModel.head.pitch - 1.8F - cameraAngle / 32.0F;
        bipedEntityModel.rightArm.yaw   = bipedEntityModel.head.yaw   + 0.5F + cameraAngle / 6.0F;
        bipedEntityModel.rightArm.roll  = -0.2F - cameraAngle / 3.0F;
    }),
    LEFT_SELFIE_STICK(30.0F, -8.0F, 45.0F, -2.0F, (bipedEntityModel, cameraAngle) -> {
        bipedEntityModel.leftArm.pitch = bipedEntityModel.head.pitch - 1.8F + cameraAngle / 32.0F;
        bipedEntityModel.leftArm.yaw   = bipedEntityModel.head.yaw   - 0.5F + cameraAngle / 6.0F;
        bipedEntityModel.leftArm.roll  = 0.2F + cameraAngle / 3.0F;
    });

    public static volatile CameraView instance = null;

    public static boolean isEnabled() {
        return instance != null;
    }

    public static boolean isDisabled() {
        return instance == null;
    }

    public static boolean isRightHanded() {
        return instance == RIGHT_SELFIE || instance == RIGHT_SELFIE_STICK;
    }

    public static boolean isLeftHanded() {
        return instance == LEFT_SELFIE || instance == LEFT_SELFIE_STICK;
    }

    @FunctionalInterface
    public interface CameraViewAction {
        void accept(BipedEntityModel<?> bipedEntityModel, float cameraAngle);
    }

    private final float fov;
    private final float yaw;
    private final float portraitFov;
    private final float portraitYaw;
    private final CameraViewAction action;

    CameraView(float fov, float yaw, float portraitFov, float portraitYaw, CameraViewAction action) {
        this.fov = fov;
        this.yaw = yaw;
        this.portraitFov = portraitFov;
        this.portraitYaw = portraitYaw;
        this.action = action;
    }

    public float getFov(boolean landscape) {
        return landscape ? this.fov : this.portraitFov;
    }

    public float getYaw(boolean landscape) {
        return landscape ? this.yaw : this.portraitYaw;
    }

    public void setAngles(BipedEntityModel<?> bipedEntityModel, float cameraAngle) {
        this.action.accept(bipedEntityModel, cameraAngle);
    }
}
