package me.kubbidev.selfiecam;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.Perspective;
import org.lwjgl.glfw.GLFW;

public class KeybindHandler {
    /**
     * A key binding for taking a screenshot.
     * Bound to {@linkplain org.lwjgl.glfw.GLFW#GLFW_KEY_F6 the F6 key} by default.
     */
    public static final KeyBinding SCREENSHOT_KEY = KeyBindingHelper.registerKeyBinding(
            new KeyBinding("selfiecam.key.screenshot", GLFW.GLFW_KEY_F6, "Selfie Cam"));

    public static Perspective defaultPerspective;

    public static void registerListener() {
        ClientTickEvents.END_CLIENT_TICK.register(KeybindHandler::clientTick);
    }

    private static void clientTick(MinecraftClient client) {
        if (client.player == null) return;

        if (SCREENSHOT_KEY.wasPressed()) {
            whenKeyPressed(client);
        }

        if (CameraView.isEnabled()) {
            client.options.setPerspective(Perspective.THIRD_PERSON_FRONT);
        } else if (defaultPerspective != null) {
            client.options.setPerspective(defaultPerspective);
            defaultPerspective = null;
        }
    }

    private static void whenKeyPressed(MinecraftClient client) {
        if (CameraView.isDisabled()) {
            CameraView.instance = CameraView.RIGHT_SELFIE;
        } else if (CameraView.instance == CameraView.RIGHT_SELFIE) {
            CameraView.instance = CameraView.LEFT_SELFIE;
        } else if (CameraView.instance == CameraView.LEFT_SELFIE) {
            CameraView.instance = CameraView.RIGHT_SELFIE_STICK;
        } else if (CameraView.instance == CameraView.RIGHT_SELFIE_STICK) {
            CameraView.instance = CameraView.LEFT_SELFIE_STICK;
        } else {
            CameraView.instance = null;
        }

        if (defaultPerspective == null) {
            defaultPerspective = client.options.getPerspective();
        }
    }
}
