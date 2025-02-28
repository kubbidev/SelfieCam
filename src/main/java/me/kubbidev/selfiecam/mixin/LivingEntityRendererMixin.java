package me.kubbidev.selfiecam.mixin;

import me.kubbidev.selfiecam.renderer.SelfieStickFeature;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(EntityRendererFactory.Context ctx, M model, float shadowRadius, CallbackInfo ci) {
        //noinspection DataFlowIssue
        LivingEntityRenderer<?, ?, ?> renderer = (LivingEntityRenderer<?, ?, ?>) (Object) this;

        if (renderer instanceof PlayerEntityRenderer playerEntityRenderer) {
            this.addFeature(new SelfieStickFeature<>(playerEntityRenderer));
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    @Shadow
    public abstract boolean addFeature(FeatureRenderer<?, ?> feature);
}
