package me.kubbidev.selfiecam.mixins.render;

import me.kubbidev.selfiecam.SelfieState;
import me.kubbidev.selfiecam.api.render.state.AwarePlayerEntityRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.PlayerHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(PlayerHeldItemFeatureRenderer.class)
public class PlayerHeldItemFeatureRendererMixin<S extends PlayerEntityRenderState> {

    @Inject(method = "renderItem", at = @At("HEAD"), cancellable = true)
    private void renderItem(
        S playerEntityRenderState,
        ItemRenderState itemRenderState,
        Arm arm,
        MatrixStack matrixStack,
        OrderedRenderCommandQueue orderedRenderCommandQueue,
        int i,
        CallbackInfo ci
    ) {
        if (SelfieState.isDisabled()) {
            return;
        }

        if (((AwarePlayerEntityRenderState) playerEntityRenderState).isClientPlayer()) {
            if ((arm == Arm.LEFT && SelfieState.isLeftHanded()) || (arm == Arm.RIGHT && SelfieState.isRightHanded())) {
                ci.cancel(); // Do not render items in selfie hand
            }
        }
    }
}
