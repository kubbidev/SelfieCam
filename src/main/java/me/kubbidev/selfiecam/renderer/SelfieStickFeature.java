package me.kubbidev.selfiecam.renderer;

import me.kubbidev.selfiecam.CameraView;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SelfieStickFeature<S extends PlayerEntityRenderState, M extends PlayerEntityModel> extends FeatureRenderer<S, M> {
    public static final Identifier TEXTURE = Identifier.of("selfiecam", "textures/stick.png");

    private final SelfieStickModel model;

    public SelfieStickFeature(FeatureRendererContext<S, M> context) {
        super(context);
        this.model = new SelfieStickModel(SelfieStickModel.createTexturedModelData().createModel());
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, S state, float limbAngle, float limbDistance) {
        if (!CameraView.isSelfieStick()) return;
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null || state.id != player.getId()) {
            return;
        }

        if (CameraView.instance == CameraView.RIGHT_SELFIE_STICK) {
            this.model.rightStick.visible = true;
            this.model.leftStick.visible = false;
        } else if (CameraView.instance == CameraView.LEFT_SELFIE_STICK) {
            this.model.rightStick.visible = false;
            this.model.leftStick.visible = true;
        }

        this.model.rightStick.copyTransform(getContextModel().rightArm);
        this.model.leftStick.copyTransform(getContextModel().leftArm);
        this.model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutout(TEXTURE)), light, OverlayTexture.DEFAULT_UV);
    }
}
