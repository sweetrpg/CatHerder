package kittytalents.common.addon.jei;

import kittytalents.api.KittyTalentsAPI;
import kittytalents.api.registry.IBeddingMaterial;
import kittytalents.api.registry.ICasingMaterial;
import kittytalents.common.util.DogBedUtil;
import kittytalents.common.util.Util;
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

public final class DogBedRecipeMaker {

    public static List<IShapedRecipe<? extends Container>> createDogBedRecipes() {
        Collection<IBeddingMaterial> beddingMaterials = KittyTalentsAPI.BEDDING_MATERIAL.getValues();
        Collection<ICasingMaterial>  casingMaterials  = KittyTalentsAPI.CASING_MATERIAL.getValues();

        List<IShapedRecipe<? extends Container>> recipes = new ArrayList<>(beddingMaterials.size() * casingMaterials.size());
        String group = "kittytalents.dogbed";
        for (IBeddingMaterial beddingId : KittyTalentsAPI.BEDDING_MATERIAL.getValues()) {
            for (ICasingMaterial casingId : KittyTalentsAPI.CASING_MATERIAL.getValues()) {

                Ingredient beddingIngredient = beddingId.getIngredient();
                Ingredient casingIngredient = casingId.getIngredient();
                NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY,
                    casingIngredient, beddingIngredient, casingIngredient,
                    casingIngredient, beddingIngredient, casingIngredient,
                    casingIngredient, casingIngredient, casingIngredient
                );
                ItemStack output = DogBedUtil.createItemStack(casingId, beddingId);

                ResourceLocation id = Util.getResource("" + output.getDescriptionId()); //TODO update resource location
                ShapedRecipe recipe = new ShapedRecipe(id, group, 3, 3, inputs, output);
                recipes.add(recipe);
            }
        }
        return recipes;
    }
}
