package com.sweetrpg.catherder.client.entity.render.layer.accessory;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sweetrpg.catherder.api.inferface.IColoredObject;
import com.sweetrpg.catherder.api.registry.AccessoryInstance;
import com.sweetrpg.catherder.client.entity.model.CatModel;
import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class DefaultAccessoryRenderer extends RenderLayer<CatEntity, CatModel<CatEntity>> {

    public DefaultAccessoryRenderer(RenderLayerParent parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, CatEntity cat, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        // Only show armour if cat is tamed or visible
        if (!cat.isTame() || cat.isInvisible()) {
            return;
        }

        for (AccessoryInstance accessoryInst : cat.getAccessories()) {
            if (accessoryInst.usesRenderer(this.getClass())) {
                if (accessoryInst instanceof IColoredObject coloredObject) {
                    float[] color = coloredObject.getColor();
                    this.renderColoredCutoutModel(this.getParentModel(), accessoryInst.getModelTexture(cat), poseStack, buffer, packedLight, cat, color[0], color[1], color[2]);
                } else {
                    RenderLayer.renderColoredCutoutModel(this.getParentModel(), accessoryInst.getModelTexture(cat), poseStack, buffer, packedLight, cat, 1.0F, 1.0F, 1.0F);
                }
            }
        }
    }
}
