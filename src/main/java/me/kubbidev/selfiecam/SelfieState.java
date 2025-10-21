package me.kubbidev.selfiecam;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.util.Arm;

@Environment(EnvType.CLIENT)
public enum SelfieState {
    LEFT(Arm.LEFT, 15.0F, -5.0F, 30.0F, -3.0F) {
        @Override
        public void setAngles(PlayerEntityModel playerEntityModel, float cameraAngle) {
            playerEntityModel.leftArm.pitch = playerEntityModel.head.pitch - 1.8F + cameraAngle / 32.0F;
            playerEntityModel.leftArm.yaw = playerEntityModel.head.yaw - 0.5F + cameraAngle / 6.0F;
            playerEntityModel.leftArm.roll = 0.2F + cameraAngle / 3.0F;
        }
    },
    RIGHT(Arm.RIGHT, 15.0F, 5.0F, 30.0F, 3.0F) {
        @Override
        public void setAngles(PlayerEntityModel playerEntityModel, float cameraAngle) {
            playerEntityModel.rightArm.pitch = playerEntityModel.head.pitch - 1.8F - cameraAngle / 32.0F;
            playerEntityModel.rightArm.yaw = playerEntityModel.head.yaw + 0.5F + cameraAngle / 6.0F;
            playerEntityModel.rightArm.roll = -0.2F - cameraAngle / 3.0F;
        }
    },
    LEFT_STICK(Arm.LEFT, 30.0F, -8.0F, 45.0F, -2.0F) {
        @Override
        public void setAngles(PlayerEntityModel playerEntityModel, float cameraAngle) {
            playerEntityModel.leftArm.pitch = playerEntityModel.head.pitch - 1.8F + cameraAngle / 32.0F;
            playerEntityModel.leftArm.yaw = playerEntityModel.head.yaw - 0.5F + cameraAngle / 6.0F;
            playerEntityModel.leftArm.roll = 0.2F + cameraAngle / 3.0F;
        }
    },
    RIGHT_STICK(Arm.RIGHT, 30.0F, 8.0F, 45.0F, 2.0F) {
        @Override
        public void setAngles(PlayerEntityModel playerEntityModel, float cameraAngle) {
            playerEntityModel.rightArm.pitch = playerEntityModel.head.pitch - 1.8F - cameraAngle / 32.0F;
            playerEntityModel.rightArm.yaw = playerEntityModel.head.yaw + 0.5F + cameraAngle / 6.0F;
            playerEntityModel.rightArm.roll = -0.2F - cameraAngle / 3.0F;
        }
    };

    public static volatile SelfieState selfieState = null;

    public static boolean isEnabled() {
        return selfieState != null;
    }

    public static boolean isDisabled() {
        return selfieState == null;
    }

    public static boolean isRightHanded() {
        return selfieState == RIGHT || selfieState == RIGHT_STICK;
    }

    public static boolean isLeftHanded() {
        return selfieState == LEFT || selfieState == LEFT_STICK;
    }

    public static boolean isSelfieStick() {
        return selfieState == RIGHT_STICK || selfieState == LEFT_STICK;
    }

    private final Arm   arm;
    private final float landscapeFov;
    private final float landscapeYaw;
    private final float portraitFov;
    private final float portraitYaw;

    SelfieState(Arm arm, float landscapeFov, float landscapeYaw, float portraitFov, float portraitYaw) {
        this.arm = arm;
        this.landscapeFov = landscapeFov;
        this.landscapeYaw = landscapeYaw;
        this.portraitFov = portraitFov;
        this.portraitYaw = portraitYaw;
    }

    public Arm getArm() {
        return this.arm;
    }

    public float getFov(boolean landscape) {
        return landscape ? this.landscapeFov : this.portraitFov;
    }

    public float getYaw(boolean landscape) {
        return landscape ? this.landscapeYaw : this.portraitYaw;
    }

    public abstract void setAngles(PlayerEntityModel playerEntityModel, float cameraAngle);
}
