package com.sweetrpg.catherder.common.addon.jei;

import com.sweetrpg.catherder.api.registry.IColorMaterial;
import com.sweetrpg.catherder.common.registry.ModBlocks;
import com.sweetrpg.catherder.common.util.CatTreeUtil;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.ModIds;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class CHPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ModIds.JEI_ID, "catherder");
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(ModBlocks.CAT_TREE.get().asItem(), (stack, ctx) -> {
            IColorMaterial colorMaterial = CatTreeUtil.getColorMaterial(stack);

            String colorKey = colorMaterial != null ? colorMaterial.toString()
                    : "catherder:casing_missing";

            return colorKey;
        });
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(RecipeTypes.CRAFTING, CatTreeRecipeMaker.createCatTreeRecipes());
        registration.addRecipes(RecipeTypes.CRAFTING, PetDoorRecipeMaker.createPetDoorRecipes());
    }
}
