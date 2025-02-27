package me.kubbidev.selfiecam;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class SelfieCamMod implements ClientModInitializer {
    public static final String MOD_ID = "selfiecam";

    /**
     * The static mod logger instance.
     */
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    @Override
    public void onInitializeClient() {
        KeybindHandler.registerListener();
    }
}
