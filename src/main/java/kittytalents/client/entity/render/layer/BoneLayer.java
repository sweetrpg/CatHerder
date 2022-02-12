package kittytalents.client.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import kittytalents.api.inferface.IThrowableItem;
import kittytalents.client.entity.render.DogRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

public class BoneLayer extends RenderLayer<kittytalents.common.entity.CatEntity, kittytalents.client.entity.model.CatModel<kittytalents.common.entity.CatEntity>> {

    public BoneLayer(DogRenderer dogRendererIn) {
        super(dogRendererIn);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource bufferSource, int packedLight, kittytalents.common.entity.CatEntity cat, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (cat.hasBone()) {

            matrixStack.pushPose();
            kittytalents.client.entity.model.CatModel<kittytalents.common.entity.CatEntity> model = this.getParentModel();
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
