package me.kubbidev.selfiecam.mixins.render.state;

import me.kubbidev.selfiecam.api.render.state.AwarePlayerEntityRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderState.class)
public class PlayerEntityRenderStateMixin implements AwarePlayerEntityRenderState {

    @Unique
    public boolean clientPlayer;

    @Override
    public boolean isClientPlayer() {
        return this.clientPlayer;
    }

    @Override
    public void setClientPlayer(boolean clientPlayer) {
        this.clientPlayer = clientPlayer;
    }
}
