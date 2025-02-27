package me.kubbidev.selfiecam.renderer;

import me.kubbidev.selfiecam.CameraView;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SelfieStickFeature<T extends AbstractClientPlayerEntity, M extends PlayerEntityModel<T>> extends FeatureRenderer<T, M> {
    public static final SelfieStickModel MODEL = new SelfieStickModel();
    public static final Identifier TEXTURE = new Identifier("selfiecam", "textures/stick.png");

    public SelfieStickFeature(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Override
    protected Identifier getTexture(T entity) {
        return TEXTURE;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (MinecraftClient.getInstance().player != entity) return;

        if (CameraView.instance == CameraView.RIGHT_SELFIE_STICK) {
            MODEL.rightStick.visible = true;
            MODEL.leftStick.visible = false;
        } else if (CameraView.instance == CameraView.LEFT_SELFIE_STICK) {
            MODEL.rightStick.visible = false;
            MODEL.leftStick.visible = true;
        } else {
            MODEL.rightStick.visible = false;
            MODEL.leftStick.visible = false;
        }
        MODEL.rightStick.copyTransform(getContextModel().rightArm);
        MODEL.leftStick.copyTransform(getContextModel().leftArm);
        MODEL.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutout(TEXTURE)), light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
