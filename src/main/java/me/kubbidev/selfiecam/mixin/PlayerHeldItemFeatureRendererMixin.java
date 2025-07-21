package me.kubbidev.selfiecam.mixin;

import me.kubbidev.selfiecam.CameraView;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.PlayerHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerHeldItemFeatureRenderer.class)
public class PlayerHeldItemFeatureRendererMixin<S extends PlayerEntityRenderState> {

    @Inject(method = "renderItem", at = @At("HEAD"), cancellable = true)
    private void renderItem(
        S playerEntityRenderState, ItemRenderState itemRenderState, Arm arm, MatrixStack matrixStack,
        VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci
    ) {
        if (CameraView.isDisabled()) {
            return;
        }
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null || playerEntityRenderState.id != player.getId()) {
            return;
        }

        if (arm == Arm.LEFT && CameraView.isLeftHanded()) {
            ci.cancel();
        } else if (arm == Arm.RIGHT && CameraView.isRightHanded()) {
            ci.cancel();
        }
    }
}
