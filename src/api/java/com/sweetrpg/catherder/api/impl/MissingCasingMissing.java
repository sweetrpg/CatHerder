package com.sweetrpg.catherder.api.impl;

import com.sweetrpg.catherder.api.registry.ICasingMaterial;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;

public class MissingCasingMissing extends ICasingMaterial {

    public static final ICasingMaterial NULL = new MissingCasingMissing();
    private static final ResourceLocation MISSING_TEXTURE = new ResourceLocation("missingno");

    @Override
    public ResourceLocation getTexture() {
        return MissingCasingMissing.MISSING_TEXTURE;
    }

    @Override
    public Component getTooltip() {
        return new TranslatableComponent("cattree.casing.missing", "//TODO" /*ForgeRegistries.BLOCKS.getKey(this)*/);
    }

    @Override
    public Ingredient getIngredient() {
        return Ingredient.EMPTY;
    }
}