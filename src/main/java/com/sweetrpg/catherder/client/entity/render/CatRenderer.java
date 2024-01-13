package com.sweetrpg.catherder.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sweetrpg.catherder.client.CatTextureManager;
import com.sweetrpg.catherder.client.ClientSetup;
import com.sweetrpg.catherder.client.entity.model.CatModel;
//import com.sweetrpg.catherder.client.entity.render.layer.CatnipLayer;
import com.sweetrpg.catherder.client.entity.render.layer.CatToyLayer;
import com.sweetrpg.catherder.client.entity.render.layer.LayerFactory;
import com.sweetrpg.catherder.common.config.ConfigHandler;
import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CatRenderer extends MobRenderer<CatEntity, CatModel<CatEntity>> {

    public CatRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new CatModel(ctx.bakeLayer(ClientSetup.CAT)), 0.5F);
//        this.addLayer(new CatTalentLayer(this, ctx));
//        this.addLayer(new CatAccessoryLayer(this, ctx));
        this.addLayer(new CatToyLayer(this, ctx.getItemInHandRenderer()));
        for(LayerFactory<CatEntity, CatModel<CatEntity>> layer : CollarRenderManager.getLayers()) {
            this.addLayer(layer.createLayer(this, ctx));
        }
    }

    @Override
    protected float getBob(CatEntity livingBase, float partialTicks) {
        return livingBase.getTailRotation();
    }

    @Override
    public void render(CatEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        if(entityIn.isCatWet()) {
            float f = entityIn.getShadingWhileWet(partialTicks);
            this.model.setColor(f, f, f);
        }

        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        if(this.shouldShowName(entityIn)) {
            double d0 = this.entityRenderDispatcher.distanceToSqr(entityIn);
            if(d0 <= 64 * 64) {
                String tip = entityIn.getMode().getTip();
                String label;
                if(ConfigHandler.SERVER.CAT_GENDER.get()) {
                    if(this.entityRenderDispatcher.camera.getEntity().isShiftKeyDown()) {
                        label = String.format("%s(%d/%d)%s", Component.translatable(tip).getString(), Mth.ceil(entityIn.getCatHunger()), Mth.ceil(entityIn.getMaxHunger()), Component.translatable(entityIn.getGender().getUnlocalizedTip()).getString());
                    }
                    else {
                        label = String.format("%s(%d)%s", Component.translatable(tip).getString(), Mth.ceil(entityIn.getCatHunger()), Component.translatable(entityIn.getGender().getUnlocalizedTip()).getString());
                    }
                }
                else {
                    if(this.entityRenderDispatcher.camera.getEntity().isShiftKeyDown()) {
                        label = String.format("%s(%d/%d)", Component.translatable(tip).getString(), Mth.ceil(entityIn.getCatHunger()), Mth.ceil(entityIn.getMaxHunger()));
                    }
                    else {
                        label = String.format("%s(%d)", Component.translatable(tip).getString(), Mth.ceil(entityIn.getCatHunger()));
                    }
                }
                RenderUtil.renderLabelWithScale(entityIn, this, this.entityRenderDispatcher, label, matrixStackIn, bufferIn, packedLightIn, 0.01F, 0.12F);

                if(d0 <= 5 * 5 && this.entityRenderDispatcher.camera.getEntity().isShiftKeyDown()) {
                    RenderUtil.renderLabelWithScale(entityIn, this, this.entityRenderDispatcher, entityIn.getOwnersName().orElseGet(() -> this.getNameUnknown(entityIn)), matrixStackIn, bufferIn, packedLightIn, 0.01F, -0.25F);
                }
            }
        }

        if(entityIn.isCatWet()) {
            this.model.setColor(1.0F, 1.0F, 1.0F);
        }
    }


    private Component getNameUnknown(CatEntity catIn) {
        return Component.translatable(catIn.getOwnerUUID() != null ? "entity.catherder.cat.unknown_owner" : "entity.catherder.cat.untamed");
    }

    @Override
    public ResourceLocation getTextureLocation(CatEntity catIn) {
        return CatTextureManager.INSTANCE.getTexture(catIn);
    }

    @Override
    protected void scale(CatEntity catIn, PoseStack matrixStackIn, float partialTickTime) {
        float size = catIn.getCatSize() * 0.3F + 0.1F;
        matrixStackIn.scale(size, size, size);
        this.shadowRadius = size * 0.5F;
    }
}
