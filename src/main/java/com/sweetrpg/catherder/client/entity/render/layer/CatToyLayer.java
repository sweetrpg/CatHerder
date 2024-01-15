package com.sweetrpg.catherder.client.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.sweetrpg.catherder.api.inferface.IThrowableItem;
import com.sweetrpg.catherder.client.entity.render.CatRenderer;
import com.sweetrpg.catherder.client.entity.model.CatModel;
import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.item.ItemDisplayContext;

public class CatToyLayer extends RenderLayer<CatEntity, CatModel<CatEntity>> {

    private ItemInHandRenderer itemInHandRenderer;

    public CatToyLayer(CatRenderer catRendererIn, ItemInHandRenderer itemInHandRenderer) {
        super(catRendererIn);
        this.itemInHandRenderer = itemInHandRenderer;
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource bufferSource, int packedLight, CatEntity cat, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (cat.hasToy()) {

            matrixStack.pushPose();
            CatModel<CatEntity> model = this.getParentModel();
            if (model.young) {
                // derived from AgeableModel head offset
                matrixStack.translate(0.0F, 5.0F / 16.0F, 2.0F / 16.0F);
            }

            model.head.translateAndRotate(matrixStack);

            matrixStack.translate(-0.025F, 0.125F, -0.32F);
            matrixStack.mulPose(Axis.YP.rotationDegrees(45.0F));
            matrixStack.mulPose(Axis.XP.rotationDegrees(90.0F));

            IThrowableItem throwableItem = cat.getThrowableItem();
            this.itemInHandRenderer.renderItem(cat, throwableItem != null ? throwableItem.getRenderStack(cat.getToyVariant()) : cat.getToyVariant(), ItemDisplayContext.GROUND, false, matrixStack, bufferSource, packedLight);
            matrixStack.popPose();
        }
    }
}
