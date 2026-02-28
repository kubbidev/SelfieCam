package me.kubbidev.selfiecam.render.model;

import me.kubbidev.selfiecam.SelfieState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;

@Environment(EnvType.CLIENT)
public class SelfieStickModel extends Model<PlayerEntityRenderState> {

    /**
     * The key of the left stick model part, whose value is {@value}.
     */
    private static final String LEFT_STICK  = "left_stick";
    /**
     * The key of the right stick model part, whose value is {@value}.
     */
    private static final String RIGHT_STICK = "right_stick";

    protected final ModelPart leftStick;
    protected final ModelPart rightStick;

    public SelfieStickModel(ModelPart root) {
        super(root, RenderLayers::entityCutout);
        this.leftStick = root.getChild(LEFT_STICK);
        this.rightStick = root.getChild(RIGHT_STICK);
    }

    public ModelPart getLeftStick() {
        return this.leftStick;
    }

    public ModelPart getRightStick() {
        return this.rightStick;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData
            .addChild(LEFT_STICK,
                ModelPartBuilder.create()
                    .uv(4, 0).cuboid(0.0F, 10.0F, -1.0F, 2.0F, 5.0F, 2.0F)
                    .uv(0, 0).cuboid(0.5F, 15.0F, -0.5F, 1.0F, 13.0F, 1.0F),
                ModelTransform.NONE);

        modelPartData
            .addChild(RIGHT_STICK,
                ModelPartBuilder.create()
                    .uv(4, 0).cuboid(-2.0F, 10.0F, -1.0F, 2.0F, 5.0F, 2.0F)
                    .uv(0, 0).cuboid(-1.5F, 15.0F, -0.5F, 1.0F, 13.0F, 1.0F),
                ModelTransform.NONE);

        return TexturedModelData.of(modelData, 16, 16);
    }

    @Override
    public void setAngles(PlayerEntityRenderState playerEntityRenderState) {
        super.setAngles(playerEntityRenderState);

        if (SelfieState.selfieState == SelfieState.LEFT_STICK) {
            this.leftStick.visible = true;
            this.rightStick.visible = false;
        } else if (SelfieState.selfieState == SelfieState.RIGHT_STICK) {
            this.leftStick.visible = false;
            this.rightStick.visible = true;
        }
    }
}
