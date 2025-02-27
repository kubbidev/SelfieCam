package me.kubbidev.selfiecam.camera;

import net.minecraft.client.render.entity.model.BipedEntityModel;

@FunctionalInterface
public interface CameraViewAction {
    void accept(BipedEntityModel<?> bipedEntityModel, float cameraAngle);
}