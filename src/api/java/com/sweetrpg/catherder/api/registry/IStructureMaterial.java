package com.sweetrpg.catherder.api.registry;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * Used for pet doors (and possibly others) that can be made from multiple material types.
 */
public abstract class IStructureMaterial extends ForgeRegistryEntry<IStructureMaterial> {

    /**
     * Texture location for that material, eg 'minecraft:block/oak_planks'
     */
    public abstract ResourceLocation getTexture();

    /**
     * The translation key using for the tooltip
     */
    public abstract Component getTooltip();

    /**
     * The ingredient used in the crafting recipe of the item
     */
    public abstract Ingredient getIngredient();
}
