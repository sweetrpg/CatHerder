package kittytalents.client.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import kittytalents.client.ClientSetup;
import kittytalents.client.entity.render.layer.BoneLayer;
import kittytalents.client.entity.render.layer.LayerFactory;
import kittytalents.common.config.ConfigHandler;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CatRenderer extends MobRenderer<kittytalents.common.entity.CatEntity, kittytalents.client.entity.model.CatModel<kittytalents.common.entity.CatEntity>> {

    public CatRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new kittytalents.client.entity.model.CatModel(ctx.bakeLayer(ClientSetup.CAT)), 0.5F);
//        this.addLayer(new DogTalentLayer(this, ctx));
//        this.addLayer(new DogAccessoryLayer(this, ctx));
        this.addLayer(new BoneLayer(this));
        for (LayerFactory<kittytalents.common.entity.CatEntity, kittytalents.client.entity.model.CatModel<kittytalents.common.entity.CatEntity>> layer : CollarRenderManager.getLayers()) {
            this.addLayer(layer.createLayer(this, ctx));
        }
    }

    @Override
    protected float getBob(kittytalents.common.entity.CatEntity livingBase, float partialTicks) {
        return livingBase.getTailRotation();
    }

    @Override
    public void render(kittytalents.common.entity.CatEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        if (entityIn.isDogWet()) {
            float f = entityIn.getShadingWhileWet(partialTicks);
            this.model.setColor(f, f, f);
        }

        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        if (this.shouldShowName(entityIn)) {

            double d0 = this.entityRenderDispatcher.distanceToSqr(entityIn);
            if (d0 <= 64 * 64) {
                String tip = entityIn.getMode().getTip();
                String label = String.format(ConfigHandler.SERVER.DOG_GENDER.get() ? "%s(%d)%s" : "%s(%d)",
                        new TranslatableComponent(tip).getString(),
                        Mth.ceil(entityIn.getCatHunger()),
                        new TranslatableComponent(entityIn.getGender().getUnlocalisedTip()).getString());

                RenderUtil.renderLabelWithScale(entityIn, this, this.entityRenderDispatcher, label, matrixStackIn, bufferIn, packedLightIn, 0.01F, 0.12F);

                if (d0 <= 5 * 5 && this.entityRenderDispatcher.camera.getEntity().isShiftKeyDown()) {
                    RenderUtil.renderLabelWithScale(entityIn, this, this.entityRenderDispatcher, entityIn.getOwnersName().orElseGet(() -> this.getNameUnknown(entityIn)), matrixStackIn, bufferIn, packedLightIn, 0.01F, -0.25F);
                }
            }
        }


        if (entityIn.isDogWet()) {
            this.model.setColor(1.0F, 1.0F, 1.0F);
        }
    }


    private Component getNameUnknown(kittytalents.common.entity.CatEntity catIn) {
        return new TranslatableComponent(catIn.getOwnerUUID() != null ? "entity.kittytalents.cat.unknown_owner" : "entity.kittytalents.cat.untamed");
    }

    @Override
    public ResourceLocation getTextureLocation(kittytalents.common.entity.CatEntity catIn) {
        return kittytalents.client.CatTextureManager.INSTANCE.getTexture(catIn);
    }

    @Override
    protected void scale(kittytalents.common.entity.CatEntity catIn, PoseStack matrixStackIn, float partialTickTime) {
        float size = catIn.getCatSize() * 0.3F + 0.1F;
        matrixStackIn.scale(size, size, size);
        this.shadowRadius = size * 0.5F;
    }
}
