package me.kubbidev.selfiecam.render.feature;

import me.kubbidev.selfiecam.SelfieCamMod;
import me.kubbidev.selfiecam.SelfieCamModelLayers;
import me.kubbidev.selfiecam.SelfieState;
import me.kubbidev.selfiecam.api.render.state.AwarePlayerEntityRenderState;
import me.kubbidev.selfiecam.render.model.SelfieStickModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SelfieStickFeature<S extends PlayerEntityRenderState, M extends PlayerEntityModel> extends FeatureRenderer<S, M> {

    public static final Identifier TEXTURE = SelfieCamMod.id("textures/stick.png");

    private final SelfieStickModel selfieStickModel;

    public SelfieStickFeature(FeatureRendererContext<S, M> featureRendererContext, LoadedEntityModels loader) {
        super(featureRendererContext);
        this.selfieStickModel = new SelfieStickModel(loader.getModelPart(SelfieCamModelLayers.SELFIECAM));
    }

    @Override
    public void render(MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, int light, S playerEntityRenderState,
                       float limbAngle, float limbDistance
    ) {
        if (!SelfieState.isSelfieStick()) {
            return;
        }

        if (((AwarePlayerEntityRenderState) playerEntityRenderState).isClientPlayer()) {
            matrixStack.push();
            Arm arm = SelfieState.selfieState.getArm();
            getContextModel().setArmAngle(playerEntityRenderState, arm, matrixStack);
            orderedRenderCommandQueue.submitModel(
                this.selfieStickModel,
                playerEntityRenderState,
                matrixStack,
                this.selfieStickModel.getLayer(TEXTURE),
                light,
                OverlayTexture.DEFAULT_UV,
                playerEntityRenderState.outlineColor,
                null
            );
            matrixStack.pop();
        }
    }
}
