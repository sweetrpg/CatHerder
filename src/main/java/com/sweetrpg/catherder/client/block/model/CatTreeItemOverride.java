package com.sweetrpg.catherder.client.block.model;

import com.sweetrpg.catherder.api.registry.IColorMaterial;
import com.sweetrpg.catherder.common.util.CatTreeUtil;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class CatTreeItemOverride extends ItemOverrides {

    @Override
    public BakedModel resolve(BakedModel modelOriginal, ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity livingEntity, int p_173469_) {
        if(modelOriginal instanceof CatTreeModel catTreeModel) {
            IColorMaterial colorMaterial = CatTreeUtil.getColorMaterial(stack);
            return catTreeModel.getModelVariant(colorMaterial, Direction.NORTH);
        }

        return modelOriginal;
    }
}
