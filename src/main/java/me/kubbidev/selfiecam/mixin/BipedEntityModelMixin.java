package me.kubbidev.selfiecam.mixin;

import me.kubbidev.selfiecam.renderer.PlayerEntityRender;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public class BipedEntityModelMixin<T extends LivingEntity> {

    @Inject(method = "setAngles", at = @At("TAIL"))
    public void setAngles(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        //noinspection DataFlowIssue
        PlayerEntityRender.setAngles((BipedEntityModel<?>) (Object) this, livingEntity, f, g, h, i, j);
    }
}
