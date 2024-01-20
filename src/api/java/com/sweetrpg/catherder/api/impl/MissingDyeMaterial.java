package com.sweetrpg.catherder.api.impl;

import com.sweetrpg.catherder.api.registry.IDyeMaterial;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

public class MissingDyeMaterial extends IDyeMaterial {

    public static final IDyeMaterial NULL = new MissingDyeMaterial();
    private static final ResourceLocation MISSING_TEXTURE = new ResourceLocation("missingno");

    @Override
    public ResourceLocation getTexture() {
        return MissingDyeMaterial.MISSING_TEXTURE;
    }

    @Override
    public Component getTooltip() {
        return Component.translatable("cattree.dye.missing");
    }

    @Override
    public Ingredient getIngredient() {
        return Ingredient.EMPTY;
    }
}
