package com.sweetrpg.catherder.api.impl;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.registry.IDyeMaterial;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class DyeMaterial extends IDyeMaterial {

    private final Supplier<Item> item;
    protected ResourceLocation texture;

    @Nullable
    private String translationKey;

    public DyeMaterial(Supplier<Item> itemIn) {
        this.item = itemIn;
    }

    public DyeMaterial(Supplier<Item> itemIn, ResourceLocation texture) {
        this.item = itemIn;
        this.texture = texture;
    }

    /**
     * Texture location that for material, eg 'minecraft:item/pink_dye'
     */
    @Override
    public ResourceLocation getTexture() {
        if (this.texture == null) {
            ResourceLocation loc = ForgeRegistries.ITEMS.getKey(this.item.get());
            this.texture = new ResourceLocation(loc.getNamespace(), "item/" + loc.getPath());
        }

        return this.texture;
    }

    /**
     * The translation key using for the tooltip
     */
    @Override
    public Component getTooltip() {
        if (this.translationKey == null) {
            this.translationKey = Util.makeDescriptionId("color", CatHerderAPI.DYE_MATERIAL.get().getKey(this));
        }

        return Component.translatable(this.translationKey);
    }

    /**
     * The ingredient used in the crafting recipe
     */
    @Override
    public Ingredient getIngredient() {
        return Ingredient.of(this.item.get());
    }
}
