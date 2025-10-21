package me.kubbidev.selfiecam.mixins.render;

import me.kubbidev.selfiecam.api.render.state.AwarePlayerEntityRenderState;
import me.kubbidev.selfiecam.render.feature.SelfieStickFeature;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerLikeEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.entity.PlayerLikeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin<T extends PlayerLikeEntity & ClientPlayerLikeEntity> extends LivingEntityRendererMixin {

    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
        addFeature(new SelfieStickFeature<>((PlayerEntityRenderer<?>) (Object) this, ctx.getEntityModels()));
    }

    @Inject(method = "updateRenderState", at = @At("TAIL"))
    public void updateRenderState(T playerLikeEntity, PlayerEntityRenderState playerEntityRenderState, float tickProgress,
                                  CallbackInfo ci
    ) {
        AwarePlayerEntityRenderState awarePlayerEntityRenderState = (AwarePlayerEntityRenderState) playerEntityRenderState;
        awarePlayerEntityRenderState.setClientPlayer(playerLikeEntity == MinecraftClient.getInstance().player);
    }
}