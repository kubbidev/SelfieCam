package me.kubbidev.selfiecam;

import me.kubbidev.selfiecam.renderer.PlayerEntityRender;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;
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
     * A key binding for taking a screenshot.
     * Bound to {@linkplain org.lwjgl.glfw.GLFW#GLFW_KEY_F6 the F6 key} by default.
     */
    public static final KeyBinding SCREENSHOT_KEY = KeyBindingHelper.registerKeyBinding(
            new KeyBinding("selfiecam.key.screenshot",GLFW.GLFW_KEY_F6, "Selfie Cam"));


    @Override
    public void onInitializeClient() {
        PlayerEntityRender.registerListener();
    }
}
