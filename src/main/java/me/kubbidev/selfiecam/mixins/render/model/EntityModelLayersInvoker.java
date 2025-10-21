package me.kubbidev.selfiecam.mixins.render.model;

import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(EntityModelLayers.class)
public interface EntityModelLayersInvoker {

    @Accessor("LAYERS")
    static Set<EntityModelLayer> getLayers() {
        throw new UnsupportedOperationException();
    }
}
