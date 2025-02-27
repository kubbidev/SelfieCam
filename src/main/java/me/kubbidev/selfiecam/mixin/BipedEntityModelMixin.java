package me.kubbidev.selfiecam.mixin;

import me.kubbidev.selfiecam.CameraView;
import net.minecraft.client.MinecraftClient;
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
        if (CameraView.isDisabled()) return;
        if (livingEntity != MinecraftClient.getInstance().player) {
            return;
        }

        //noinspection DataFlowIssue
        BipedEntityModel<?> model = (BipedEntityModel<?>) (Object) this;
        CameraView.instance.setAngles(model, Math.max(3.0F - (90.0F + livingEntity.getRotationClient().x) / 18.0F, 0.0F));
    }
}
