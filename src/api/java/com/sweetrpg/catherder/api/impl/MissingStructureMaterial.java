package com.sweetrpg.catherder.api.impl;

import com.sweetrpg.catherder.api.registry.IStructureMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class MissingStructureMaterial extends IStructureMaterial {

    public static final IStructureMaterial NULL = new MissingStructureMaterial();
    private static final ResourceLocation MISSING_TEXTURE = new ResourceLocation("missingno");

    @Override
    public ResourceLocation getTexture() {
        return MissingStructureMaterial.MISSING_TEXTURE;
    }

    @Override
    public Component getTooltip() {
        return Component.translatable("structure.missing");
    }

    @Override
    public Ingredient getIngredient() {
        return Ingredient.EMPTY;
    }
}
