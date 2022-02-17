package com.sweetrpg.catherder.client.entity.render.layer.accessory;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sweetrpg.catherder.common.registry.ModAccessoryTypes;
import com.sweetrpg.catherder.api.inferface.IColoredObject;
import com.sweetrpg.catherder.api.registry.AccessoryInstance;
import com.sweetrpg.catherder.client.ClientSetup;
import com.sweetrpg.catherder.common.entity.accessory.ArmourAccessory;
import com.sweetrpg.catherder.client.entity.model.CatModel;
import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class ArmorAccessoryRenderer extends RenderLayer<CatEntity, CatModel<CatEntity>> {

    private CatModel model;

    public ArmorAccessoryRenderer(RenderLayerParent parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
        this.model = new CatModel(ctx.bakeLayer(ClientSetup.CAT_ARMOR));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, CatEntity cat, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        // Only show armour if cat is tamed or visible
        if (!cat.isTame() || cat.isInvisible()) {
            return;
        }

        this.getParentModel().copyPropertiesTo(this.model);
        this.model.prepareMobModel(cat, limbSwing, limbSwingAmount, partialTicks);
        this.model.setupAnim(cat, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        for (AccessoryInstance accessoryInst : cat.getAccessories()) {
            if (accessoryInst instanceof ArmourAccessory.Instance armorInst) {

                this.model.setVisible(false);

                if (accessoryInst.ofType(ModAccessoryTypes.FEET)) {
                    this.model.legBackLeft.visible = true;
                    this.model.legBackRight.visible = true;
                    this.model.legFrontLeft.visible = true;
                    this.model.legFrontRight.visible = true;
                } else if (accessoryInst.ofType(ModAccessoryTypes.HEAD)) {
                    this.model.head.visible = true;
                } else if (accessoryInst.ofType(ModAccessoryTypes.CLOTHING)) {
                    this.model.body.visible = true;
                    this.model.mane.visible = true;
                } else if (accessoryInst.ofType(ModAccessoryTypes.TAIL)) {
                    this.model.tail.visible = true;
                }

                if (accessoryInst instanceof IColoredObject) {
                    float[] color = ((IColoredObject) armorInst).getColor();
                    this.renderArmorCutout(this.model, armorInst.getModelTexture(cat), poseStack, buffer, packedLight, cat, color[0], color[1], color[2], armorInst.hasEffect());
                } else {
                    this.renderArmorCutout(this.model, armorInst.getModelTexture(cat), poseStack, buffer, packedLight, cat, 1.0F, 1.0F, 1.0F, armorInst.hasEffect());
                }
            }
        }
    }

    public static <T extends LivingEntity> void renderArmorCutout(EntityModel<T> modelIn, ResourceLocation textureLocationIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityIn, float red, float green, float blue, boolean enchanted) {
        VertexConsumer ivertexbuilder = ItemRenderer.getArmorFoilBuffer(bufferIn, RenderType.armorCutoutNoCull(textureLocationIn), false, enchanted);
        modelIn.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
    }
}
