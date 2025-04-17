package com.sweetrpg.catherder.common.addon.jei;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.registry.IColorMaterial;
import com.sweetrpg.catherder.api.registry.IStructureMaterial;
import com.sweetrpg.catherder.common.util.CatTreeUtil;
import com.sweetrpg.catherder.common.util.Util;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.IShapedRecipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CatTreeRecipeMaker {

    public static List<IShapedRecipe<? extends Container>> createCatTreeRecipes() {
        Collection<IColorMaterial> colorMaterials = CatHerderAPI.COLOR_MATERIAL.get().getValues();

        List<IShapedRecipe<? extends Container>> recipes = new ArrayList<>(colorMaterials.size());
        String group = "catherder";
        for(IColorMaterial colorId : CatHerderAPI.COLOR_MATERIAL.get()) {

            Ingredient colorIdIngredient = colorId.getIngredient();
            Ingredient slabIngredient = Ingredient.of(ItemTags.SLABS);
            Ingredient stringIngredient = Ingredient.of(Tags.Items.STRING);
            Ingredient fenceIngredient = Ingredient.of(Tags.Items.FENCES);
            Ingredient noIngredient = Ingredient.EMPTY;
            NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY,
                    slabIngredient, colorIdIngredient, slabIngredient,
                    stringIngredient, fenceIngredient, noIngredient,
                    slabIngredient, slabIngredient, slabIngredient
            );
            ItemStack output = CatTreeUtil.createItemStack(colorId);

            ResourceLocation id = Util.getResource("" + output.getDescriptionId()); // TODO: update resource location
            ShapedRecipe recipe = new ShapedRecipe(id, group, 3, 3, inputs, output);
            recipes.add(recipe);
        }

        return recipes;
    }

}
