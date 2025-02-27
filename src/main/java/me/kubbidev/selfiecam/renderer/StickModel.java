package me.kubbidev.selfiecam.renderer;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class StickModel extends Model {
    protected final ModelPart rightStick;
    protected final ModelPart leftStick;

    public StickModel() {
        super(RenderLayer::getEntityCutout);
        TexturedModelData texturedModelData = createTexturedModelData();
        ModelPart model = texturedModelData.createModel();
        this.rightStick = model.getChild("right_stick");
        this.leftStick = model.getChild("left_stick");
    }

    public static TexturedModelData createTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();
        root.addChild("right_stick", ModelPartBuilder.create()
                        .uv(4, 0).cuboid(-2.0F, 10.0F, -1.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F))
                        .uv(0, 0).cuboid(-1.5F, 15.0F, -0.5F, 1.0F, 13.0F, 1.0F, new Dilation(0.0F)),
                ModelTransform.pivot(-5.0F, 2.0F, 0.0F));
        root.addChild("left_stick", ModelPartBuilder.create()
                        .uv(4, 0).cuboid(0.0F, 10.0F, -1.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F))
                        .uv(0, 0).cuboid(0.5F, 15.0F, -0.5F, 1.0F, 13.0F, 1.0F, new Dilation(0.0F)),
                ModelTransform.pivot(5.0F, 2.0F, 0.0F));
        return TexturedModelData.of(modelData, 16, 16);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.rightStick.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        this.leftStick.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
