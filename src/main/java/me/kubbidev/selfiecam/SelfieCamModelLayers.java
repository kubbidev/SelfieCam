package me.kubbidev.selfiecam;

import me.kubbidev.selfiecam.render.model.SelfieStickModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;

@Environment(EnvType.CLIENT)
public final class SelfieCamModelLayers {

    private static final String           MAIN      = "main";
    public static final  EntityModelLayer SELFIECAM = ofMain("selfiecam");

    public static void register() {
        EntityModelLayerRegistry.registerModelLayer(SELFIECAM, SelfieStickModel::getTexturedModelData);
    }

    private static EntityModelLayer of(String name, String layer) {
        return new EntityModelLayer(SelfieCamMod.id(name), layer);
    }

    private static EntityModelLayer ofMain(String name) {
        return of(name, MAIN);
    }
}