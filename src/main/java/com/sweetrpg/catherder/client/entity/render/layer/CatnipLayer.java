package com.sweetrpg.catherder.client.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.sweetrpg.catherder.api.inferface.IThrowableItem;
import com.sweetrpg.catherder.client.entity.render.CatRenderer;
import com.sweetrpg.catherder.client.entity.model.CatModel;
import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class CatnipLayer extends RenderLayer<CatEntity, CatModel<CatEntity>> {

    public CatnipLayer(CatRenderer catRendererIn) {
        super(catRendererIn);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource bufferSource, int packedLight, CatEntity cat, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (cat.hasBone()) {

            matrixStack.pushPose();
            CatModel<CatEntity> model = this.getParentModel();
            if (model.young) {
                // derived from AgeableModel head offset
                matrixStack.translate(0.0F, 5.0F / 16.0F, 2.0F / 16.0F);
            }

            model.head.translateAndRotate(matrixStack);

            matrixStack.translate(-0.025F, 0.125F, -0.32F);
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(45.0F));
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));

            IThrowableItem throwableItem = cat.getThrowableItem();
            Minecraft.getInstance().getItemInHandRenderer().renderItem(cat, throwableItem != null ? throwableItem.getRenderStack(cat.getBoneVariant()) : cat.getBoneVariant(), ItemTransforms.TransformType.GROUND, false, matrixStack, bufferSource, packedLight);
            matrixStack.popPose();
        }
    }
}
