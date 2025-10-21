package me.kubbidev.selfiecam.mixins.render.model;

import com.google.common.collect.ImmutableMap;
import me.kubbidev.selfiecam.SelfieCamModelLayers;
import me.kubbidev.selfiecam.render.model.SelfieStickModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModels;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(EntityModels.class)
public class EntityModelsMixin {

    @Inject(method = "getModels", at = @At("RETURN"), cancellable = true, require = 0)
    private static void afterGetModels(CallbackInfoReturnable<ImmutableMap<EntityModelLayer, TexturedModelData>> cir) {
        ImmutableMap.Builder<EntityModelLayer, TexturedModelData> builder = ImmutableMap.builder();
        builder.putAll(cir.getReturnValue());

        addModels(builder);
        cir.setReturnValue(builder.build());
    }

    private static void addModels(ImmutableMap.Builder<EntityModelLayer, TexturedModelData> builder) {
        builder.put(SelfieCamModelLayers.SELFIECAM, SelfieStickModel.getTexturedModelData());
    }
}
