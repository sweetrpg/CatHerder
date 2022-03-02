package com.sweetrpg.catherder.api.impl;

import com.sweetrpg.catherder.api.registry.IBeddingMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class MissingBeddingMaterial extends IBeddingMaterial {

    public static final IBeddingMaterial NULL = new MissingBeddingMaterial();
    private static final ResourceLocation MISSING_TEXTURE = new ResourceLocation("missingno");

    @Override
    public ResourceLocation getTexture() {
        return MissingBeddingMaterial.MISSING_TEXTURE;
    }

    @Override
    public Component getTooltip() {
        return new TranslatableComponent("cattree.bedding.missing", this.getRegistryName());
    }

    @Override
    public Ingredient getIngredient() {
        return Ingredient.EMPTY;
    }
}
