package me.kubbidev.selfiecam.api.render.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface AwarePlayerEntityRenderState {

    boolean isClientPlayer();

    void setClientPlayer(boolean clientPlayer);
}
