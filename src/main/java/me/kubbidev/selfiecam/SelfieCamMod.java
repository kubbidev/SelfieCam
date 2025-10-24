package me.kubbidev.selfiecam;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class SelfieCamMod implements ClientModInitializer {

    public static final String MOD_ID = "selfiecam";

    /**
     * The static mod logger instance.
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    /**
     * Create an identifier namespaced with the {@link #MOD_ID}.
     *
     * @param path the path of the identifier
     * @return a newly namespaced identifier
     */
    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    @Override
    public void onInitializeClient() {
        SelfieCamModelLayers.register();
        KeybindHandler.registerListener();
    }
}