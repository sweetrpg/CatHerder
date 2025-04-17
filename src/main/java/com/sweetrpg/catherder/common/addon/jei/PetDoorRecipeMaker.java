package com.sweetrpg.catherder.common.addon.jei;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.registry.IStructureMaterial;
import com.sweetrpg.catherder.common.util.PetDoorUtil;
import com.sweetrpg.catherder.common.util.Util;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.common.crafting.IShapedRecipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Lock;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Blocks;

public final class PetDoorRecipeMaker {

    public static List<IShapedRecipe<? extends Container>> createPetDoorRecipes() {
        Collection<IStructureMaterial> structureMaterials = CatHerderAPI.STRUCTURE_MATERIAL.get().getValues();

        List<IShapedRecipe<? extends Container>> recipes = new ArrayList<>(structureMaterials.size());
        String group = "catherder";
        for(IStructureMaterial structureId : CatHerderAPI.STRUCTURE_MATERIAL.get()) {

            Ingredient structureIdIngredient = structureId.getIngredient();
            Ingredient doorIngredient = Ingredient.of(ItemTags.DOORS);
            Ingredient lockIngredient = Ingredient.of(Blocks.TRIPWIRE_HOOK);
            NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY,
                    structureIdIngredient, structureIdIngredient, structureIdIngredient,
                    structureIdIngredient, doorIngredient, structureIdIngredient,
                    structureIdIngredient, lockIngredient, structureIdIngredient
            );
            ItemStack output = PetDoorUtil.createItemStack(structureId);

            ResourceLocation id = Util.getResource("" + output.getDescriptionId()); // TODO: update resource location
            ShapedRecipe recipe = new ShapedRecipe(id, group, 3, 3, inputs, output);
            recipes.add(recipe);
        }

        return recipes;
    }

}
