package me.kubbidev.selfiecam.mixin;

import me.kubbidev.selfiecam.CameraView;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public class BipedEntityModelMixin<T extends BipedEntityRenderState> {

    @Inject(method = "setAngles", at = @At("TAIL"))
    public void setAngles(T bipedEntityRenderState, CallbackInfo ci) {
        if (CameraView.isDisabled()) return;

        if (!(bipedEntityRenderState instanceof PlayerEntityRenderState playerRenderState)) {
            return;
        }

        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null || playerRenderState.id != player.getId()) return;

        //noinspection DataFlowIssue
        BipedEntityModel<?> model = (BipedEntityModel<?>) (Object) this;
        CameraView.instance.setAngles(model, Math.max(3.0F - (90.0F + playerRenderState.pitch) / 18.0F, 0.0F));
    }
}
