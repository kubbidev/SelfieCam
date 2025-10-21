package me.kubbidev.selfiecam.mixins.render.model;

import me.kubbidev.selfiecam.SelfieState;
import me.kubbidev.selfiecam.api.render.state.AwarePlayerEntityRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityModel.class)
public abstract class PlayerEntityModelMixin {

    @Inject(method = "setAngles", at = @At("TAIL"))
    public void setAngles(PlayerEntityRenderState playerEntityRenderState, CallbackInfo ci) {
        if (SelfieState.isDisabled()) {
            return;
        }

        if (((AwarePlayerEntityRenderState) playerEntityRenderState).isClientPlayer()) {
            float cameraAngle = Math.max(3.0F - (90.0F + playerEntityRenderState.pitch) / 18.0F, 0.0F);
            SelfieState selfieState = SelfieState.selfieState;
            //noinspection DataFlowIssue
            selfieState.setAngles((PlayerEntityModel) (Object) this, cameraAngle);
        }
    }
}
