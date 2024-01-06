package com.sweetrpg.catherder.common.inventory.recipe;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.registry.IDyeMaterial;
import com.sweetrpg.catherder.api.registry.IColorMaterial;
import com.sweetrpg.catherder.common.registry.ModRecipeSerializers;
import com.sweetrpg.catherder.common.registry.ModTags;
import com.sweetrpg.catherder.common.util.CatTreeUtil;
import com.sweetrpg.catherder.common.util.ColorDyeUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class CatTreeRecipeDyed extends CustomRecipe implements Recipe<CraftingContainer> {

    public CatTreeRecipeDyed(ResourceLocation resource) {
        super(resource);
    }

    @Override
    public boolean matches(CraftingContainer inv, Level worldIn) {
        IDyeMaterial colorId = null;
        Item tree = null;

        for (int col = 0; col < inv.getWidth(); col++) {
            for (int row = 0; row < inv.getHeight(); row++) {
                // color (dye)
                IDyeMaterial id = CatTreeUtil.getDyeFromStack(CatHerderAPI.DYE_MATERIAL.get(), inv.getItem(row * inv.getWidth() + col));
                if(id != null) {
                    if (colorId != null) {
                        return false;
                    }

                    colorId = id;
                }
                // cat tree
                else {
                    ItemStack stack = inv.getItem(row * inv.getWidth() + col);
                    if(stack.is(ModTags.CAT_TREES)) {
                        if(tree != null) {
                            return false;
                        }

                        tree = stack.getItem();
                    }
                }
            }
        }

        if(colorId != null && tree != null) {
            return true;
        }

        return false;
    }

    @Override
    public ItemStack assemble(CraftingContainer inv) {
        for (int col = 0; col < inv.getWidth(); col++) {
            for (int row = 0; row < inv.getHeight(); row++) {
                IDyeMaterial dye = CatTreeUtil.getDyeFromStack(CatHerderAPI.DYE_MATERIAL.get(), inv.getItem(row * inv.getWidth() + col));
                if(dye != null) {
                    IColorMaterial id = ColorDyeUtil.getColorFromDye(dye);
                    if (id != null) {
                        return CatTreeUtil.createItemStack(id);
                    }
                }
            }
        }

        return null;
    }

    //Is on a 2x2 grid or bigger
    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 2 && height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CAT_TREE_DYED.get();
    }

}
