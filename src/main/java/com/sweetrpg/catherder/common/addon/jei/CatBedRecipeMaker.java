package com.sweetrpg.catherder.common.addon.jei;

//import com.sweetrpg.catherder.api.CatHerderAPI;
//import com.sweetrpg.catherder.api.registry.IBeddingMaterial;
//import com.sweetrpg.catherder.api.registry.ICasingMaterial;
//import com.sweetrpg.catherder.common.util.CattreeUtil;
//import com.sweetrpg.catherder.common.util.Util;
//import net.minecraft.core.NonNullList;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.Container;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.crafting.Ingredient;
//import net.minecraft.world.item.crafting.ShapedRecipe;
//import net.minecraftforge.common.crafting.IShapedRecipe;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//public final class CattreeRecipeMaker {
//
//    public static List<IShapedRecipe<? extends Container>> createCattreeRecipes() {
//        Collection<IBeddingMaterial> beddingMaterials = CatHerderAPI.BEDDING_MATERIAL.getValues();
//        Collection<ICasingMaterial>  casingMaterials  = CatHerderAPI.CASING_MATERIAL.getValues();
//
//        List<IShapedRecipe<? extends Container>> recipes = new ArrayList<>(beddingMaterials.size() * casingMaterials.size());
//        String group = "catherder.cattree";
//        for (IBeddingMaterial beddingId : CatHerderAPI.BEDDING_MATERIAL.getValues()) {
//            for (ICasingMaterial casingId : CatHerderAPI.CASING_MATERIAL.getValues()) {
//
//                Ingredient beddingIngredient = beddingId.getIngredient();
//                Ingredient casingIngredient = casingId.getIngredient();
//                NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY,
//                    casingIngredient, beddingIngredient, casingIngredient,
//                    casingIngredient, beddingIngredient, casingIngredient,
//                    casingIngredient, casingIngredient, casingIngredient
//                );
//                ItemStack output = CattreeUtil.createItemStack(casingId, beddingId);
//
//                ResourceLocation id = Util.getResource("" + output.getDescriptionId()); //TODO update resource location
//                ShapedRecipe recipe = new ShapedRecipe(id, group, 3, 3, inputs, output);
//                recipes.add(recipe);
//            }
//        }
//        return recipes;
//    }
//}
