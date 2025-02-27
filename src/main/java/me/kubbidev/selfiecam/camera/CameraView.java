package me.kubbidev.selfiecam.camera;

import net.minecraft.client.render.entity.model.BipedEntityModel;

public enum CameraView implements CameraViewAction {
    RIGHT_SELFIE(15.0F, 5.0F, 30.0F, 3.0F) {
        @Override
        public void accept(BipedEntityModel<?> bipedEntityModel, float cameraAngle) {
            bipedEntityModel.rightArm.pitch = bipedEntityModel.head.pitch - 1.8F - cameraAngle / 32.0F;
            bipedEntityModel.rightArm.yaw   = bipedEntityModel.head.yaw   + 0.5F + cameraAngle / 6.0F;
            bipedEntityModel.rightArm.roll  = -0.2F - cameraAngle / 3.0F;
        }
    },
    LEFT_SELFIE(15.0F, -5.0F, 30.0F, -3.0F) {
        @Override
        public void accept(BipedEntityModel<?> bipedEntityModel, float cameraAngle) {
            bipedEntityModel.leftArm.pitch = bipedEntityModel.head.pitch - 1.8F + cameraAngle / 32.0F;
            bipedEntityModel.leftArm.yaw   = bipedEntityModel.head.yaw   - 0.5F + cameraAngle / 6.0F;
            bipedEntityModel.leftArm.roll  = 0.2F + cameraAngle / 3.0F;
        }
    },
    RIGHT_SELFIE_STICK(30.0F, 8.0F, 45.0F, 2.0F) {
        @Override
        public void accept(BipedEntityModel<?> bipedEntityModel, float cameraAngle) {
            bipedEntityModel.rightArm.pitch = bipedEntityModel.head.pitch - 1.8F - cameraAngle / 32.0F;
            bipedEntityModel.rightArm.yaw   = bipedEntityModel.head.yaw   + 0.5F + cameraAngle / 6.0F;
            bipedEntityModel.rightArm.roll  = -0.2F - cameraAngle / 3.0F;
        }
    },
    LEFT_SELFIE_STICK(30.0F, -8.0F, 45.0F, -2.0F) {
        @Override
        public void accept(BipedEntityModel<?> bipedEntityModel, float cameraAngle) {
            bipedEntityModel.leftArm.pitch = bipedEntityModel.head.pitch - 1.8F + cameraAngle / 32.0F;
            bipedEntityModel.leftArm.yaw   = bipedEntityModel.head.yaw   - 0.5F + cameraAngle / 6.0F;
            bipedEntityModel.leftArm.roll  = 0.2F + cameraAngle / 3.0F;
        }
    };

    public static CameraView INSTANCE = null;

    public static boolean isEnabled() {
        return INSTANCE != null;
    }

    public static boolean isDisabled() {
        return INSTANCE == null;
    }

    public static boolean isRightHanded() {
        return INSTANCE == RIGHT_SELFIE || INSTANCE == RIGHT_SELFIE_STICK;
    }

    public static boolean isLeftHanded() {
        return INSTANCE == LEFT_SELFIE || INSTANCE == LEFT_SELFIE_STICK;
    }

    private final float fov;
    private final float yaw;
    private final float portraitFov;
    private final float portraitYaw;

    CameraView(float fov, float yaw, float portraitFov, float portraitYaw) {
        this.fov = fov;
        this.yaw = yaw;
        this.portraitFov = portraitFov;
        this.portraitYaw = portraitYaw;
    }

    public float getFov() {
        return this.fov;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPortraitFov() {
        return this.portraitFov;
    }

    public float getPortraitYaw() {
        return this.portraitYaw;
    }

    @Override
    public abstract void accept(BipedEntityModel<?> bipedEntityModel, float cameraAngle);
}
