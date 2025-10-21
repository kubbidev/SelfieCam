package me.kubbidev.selfiecam;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.KeyBinding.Category;
import net.minecraft.client.option.Perspective;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class KeybindHandler {

    /**
     * A key binding for toggling selfie perspective. Bound to {@linkplain org.lwjgl.glfw.GLFW#GLFW_KEY_F6 the F6 key} by default.
     */
    public static final KeyBinding TOGGLE_PERSPECTIVE_KEY = KeyBindingHelper.registerKeyBinding(
        new KeyBinding("selfiecam.key.toggle_perspective", GLFW.GLFW_KEY_F6, Category.MISC)
    );

    public static Perspective defaultPerspective;

    public static void registerListener() {
        ClientTickEvents.END_CLIENT_TICK.register(KeybindHandler::clientTick);
    }

    private static void clientTick(MinecraftClient client) {
        if (client.player == null) {
            return;
        }

        if (TOGGLE_PERSPECTIVE_KEY.wasPressed()) {
            whenKeyPressed(client);
        }

        if (SelfieState.isEnabled()) {
            client.options.setPerspective(Perspective.THIRD_PERSON_FRONT);
        } else if (defaultPerspective != null) {
            client.options.setPerspective(defaultPerspective);
            defaultPerspective = null;
        }
    }

    private static void whenKeyPressed(MinecraftClient client) {
        if (SelfieState.isDisabled()) {
            SelfieState.selfieState = SelfieState.RIGHT;
        } else if (SelfieState.selfieState == SelfieState.RIGHT) {
            SelfieState.selfieState = SelfieState.LEFT;
        } else if (SelfieState.selfieState == SelfieState.LEFT) {
            SelfieState.selfieState = SelfieState.RIGHT_STICK;
        } else if (SelfieState.selfieState == SelfieState.RIGHT_STICK) {
            SelfieState.selfieState = SelfieState.LEFT_STICK;
        } else {
            SelfieState.selfieState = null;
        }

        if (defaultPerspective == null) {
            defaultPerspective = client.options.getPerspective();
        }
    }
}
