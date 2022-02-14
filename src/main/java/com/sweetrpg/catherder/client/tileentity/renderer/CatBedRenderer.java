package com.sweetrpg.catherder.client.tileentity.renderer;

import com.google.common.base.Objects;
import com.mojang.blaze3d.vertex.PoseStack;
import com.sweetrpg.catherder.common.block.tileentity.CatBedTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class CatBedRenderer implements BlockEntityRenderer<CatBedTileEntity> {

    public CatBedRenderer(BlockEntityRendererProvider.Context ctx) {}

    @Override
    public void render(CatBedTileEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (tileEntityIn.getBedName() != null && this.isLookingAtBed(tileEntityIn)) {
            matrixStackIn.pushPose();
            matrixStackIn.translate(0.5D, 0.5D, 0.5D);
            // TODO RenderUtil.renderLabelWithScale(true, Minecraft.getInstance().getEntityRenderDispatcher(), tileEntityIn.getBedName(), matrixStackIn, bufferIn, combinedLightIn, 0.025F, 1.2F);
            matrixStackIn.popPose();
        }

    }

    public boolean isLookingAtBed(CatBedTileEntity tileEntityIn) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.hitResult != null && mc.hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockpos = ((BlockHitResult) mc.hitResult).getBlockPos();
            return Objects.equal(blockpos, tileEntityIn.getBlockPos());
         }

        return false;
    }
}
