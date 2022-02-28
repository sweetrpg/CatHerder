package com.sweetrpg.catherder.common.registry;

//import com.sweetrpg.catherder.common.inventory.recipe.CattreeRecipe;
import com.sweetrpg.catherder.common.lib.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Constants.MOD_ID);

//    public static final RegistryObject<SimpleRecipeSerializer<CattreeRecipe>> CAT_TREE = register("cat_tree", CattreeRecipe::new);
////    public static final RegistryObject<SpecialRecipeSerializer<CatCollarRecipe>> COLLAR_COLOURING = register("collar_colouring", CatCollarRecipe::new);
////    public static final RegistryObject<SpecialRecipeSerializer<CatCapeRecipe>> CAPE_COLOURING = register("cape_colouring", CatCapeRecipe::new);

    private static <R extends Recipe<?>, T extends RecipeSerializer<R>> RegistryObject<SimpleRecipeSerializer<R>> register(final String name, Function<ResourceLocation, R> factory) {
        return register(name, () -> new SimpleRecipeSerializer<>(factory));
    }

    private static <T extends RecipeSerializer<?>> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return RECIPE_SERIALIZERS.register(name, sup);
    }
}
