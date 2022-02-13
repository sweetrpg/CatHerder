package kittytalents.client.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import kittytalents.KittyTalents;
import kittytalents.api.registry.TalentInstance;
import kittytalents.client.ClientSetup;
import kittytalents.common.lib.Resources;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

import java.util.Optional;

public class PackKittyRenderer extends RenderLayer<kittytalents.common.entity.CatEntity, kittytalents.client.entity.model.CatModel<kittytalents.common.entity.CatEntity>> {

    private kittytalents.client.entity.model.CatBackpackModel model;

    public PackKittyRenderer(RenderLayerParent parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
        this.model = new kittytalents.client.entity.model.CatBackpackModel(ctx.bakeLayer(ClientSetup.DOG_BACKPACK));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, kittytalents.common.entity.CatEntity cat, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (cat.isInvisible()) {
            return;
        }

        Optional<TalentInstance> inst = cat.getTalent(KittyTalents.PACK_KITTY);
        if (inst.isPresent() && inst.get().level() >= 0) {
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(cat, limbSwing, limbSwingAmount, partialTicks);
            this.model.setupAnim(cat, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

            RenderLayer.renderColoredCutoutModel(this.model, Resources.TALENT_CHEST, poseStack, buffer, packedLight, cat, 1.0F, 1.0F, 1.0F);
        }

    }
}
