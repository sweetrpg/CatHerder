package com.sweetrpg.catherder.common.registry;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.common.inventory.recipe.CatTreeRecipe;
import com.sweetrpg.catherder.common.inventory.recipe.CatTreeRecipeDyed;
import com.sweetrpg.catherder.common.inventory.recipe.PetDoorRecipe;
import com.sweetrpg.catherder.common.lib.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CatHerderAPI.MOD_ID);

    public static final RegistryObject<SimpleCraftingRecipeSerializer<CatTreeRecipe>> CAT_TREE = register("cat_tree", CatTreeRecipe::new);
    public static final RegistryObject<SimpleCraftingRecipeSerializer<CatTreeRecipeDyed>> CAT_TREE_DYED = register("cat_tree_dyed", CatTreeRecipeDyed::new);
    public static final RegistryObject<SimpleCraftingRecipeSerializer<PetDoorRecipe>> PET_DOOR = register("pet_door", PetDoorRecipe::new);
////    public static final RegistryObject<SpecialRecipeSerializer<CatCollarRecipe>> COLLAR_COLOURING = register("collar_colouring", CatCollarRecipe::new);
////    public static final RegistryObject<SpecialRecipeSerializer<CatCapeRecipe>> CAPE_COLOURING = register("cape_colouring", CatCapeRecipe::new);

    private static <R extends CraftingRecipe, T extends RecipeSerializer<R>> RegistryObject<SimpleCraftingRecipeSerializer<R>> register(final String name, SimpleCraftingRecipeSerializer.Factory<R> factory) {
        return register(name, () -> new SimpleCraftingRecipeSerializer<>(factory));
    }

    private static <T extends RecipeSerializer<?>> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return RECIPE_SERIALIZERS.register(name, sup);
    }
}
