package com.sweetrpg.catherder.client.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.api.registry.TalentInstance;
import com.sweetrpg.catherder.client.ClientSetup;
import com.sweetrpg.catherder.common.lib.Resources;
import com.sweetrpg.catherder.client.entity.model.CatModel;
import com.sweetrpg.catherder.client.entity.model.CatRescueModel;
import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

import java.util.Optional;

public class RescueCatRenderer extends RenderLayer<CatEntity, CatModel<CatEntity>> {

    private CatRescueModel model;

    public RescueCatRenderer(RenderLayerParent parentRenderer, EntityRendererProvider.Context ctx) {
        super(parentRenderer);
        this.model = new CatRescueModel(ctx.bakeLayer(ClientSetup.CAT_RESCUE_BOX));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, CatEntity cat, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (cat.isInvisible()) {
            return;
        }

        Optional<TalentInstance> inst = cat.getTalent(CatHerder.RESCUE_CAT);
        if (inst.isPresent() && inst.get().level() >= 5) {
            this.getParentModel().copyPropertiesTo(this.model);
            this.model.prepareMobModel(cat, limbSwing, limbSwingAmount, partialTicks);
            this.model.setupAnim(cat, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

            RenderLayer.renderColoredCutoutModel(this.model, Resources.TALENT_RESCUE, poseStack, buffer, packedLight, cat, 1.0F, 1.0F, 1.0F);
        }

    }
}
