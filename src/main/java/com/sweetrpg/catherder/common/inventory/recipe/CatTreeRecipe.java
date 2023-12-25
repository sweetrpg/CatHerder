package com.sweetrpg.catherder.common.inventory.recipe;

import com.sweetrpg.catherder.api.registry.IColorMaterial;
import com.sweetrpg.catherder.common.registry.ModRecipeSerializers;
import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.common.util.CatTreeUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.IShapedRecipe;

public class CatTreeRecipe extends CustomRecipe implements IShapedRecipe<CraftingContainer> {

    public CatTreeRecipe(ResourceLocation resource) {
        super(resource);
    }

    @Override
    public boolean matches(CraftingContainer inv, Level worldIn) {
        IColorMaterial colorId = null;

        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                // color (wool)
                if (col == 1 && row == 0) {
                    IColorMaterial id = CatTreeUtil.getColorFromStack(CatHerderAPI.COLOR_MATERIAL.get(), inv.getItem(row * inv.getWidth() + col));

                    if (id == null) {
                        return false;
                    }

                    if (colorId == null) {
                        colorId = id;
                    }
                    else if (colorId != id) {
                        return false;
                    }
                }
                // empty
                else if(row == 1 && col == 2) {
                    Item item = inv.getItem(row * inv.getWidth() + col).getItem();
                    if(item != null && item != Items.AIR) {
                        return false;
                    }
                }
                // string
                else if(row == 1 && col == 0) {
                    Item item = inv.getItem(row * inv.getWidth() + col).getItem();
                    if(item != Items.STRING) {
                        return false;
                    }
                }
                // fence
                else if(row == 1 && col == 1) {
                    ItemStack stack = inv.getItem(row * inv.getWidth() + col);
                    if(!stack.is(ItemTags.FENCES)) {
                        return false;
                    }
                }
                // slabs
                else {
                    ItemStack stack = inv.getItem(row * inv.getWidth() + col);
                    if(!stack.is(ItemTags.SLABS)) {
                        return false;
                    }
//                    ICasingMaterial id = CattreeUtil.getCasingFromStack(CatHerderAPI.CASING_MATERIAL, inv.getItem(row * inv.getWidth() + col));
//
//                    if (id == null) {
//                        return false;
//                    }
//
//                    if (casingId == null) {
//                        casingId = id;
//                    } else if (casingId != id) {
//                        return false;
//                    }
                }
            }
        }

        return true;
    }

    @Override
    public ItemStack assemble(CraftingContainer inv) {
        IColorMaterial colorId = CatTreeUtil.getColorFromStack(CatHerderAPI.COLOR_MATERIAL.get(), inv.getItem(1));
//        ICasingMaterial casingId = CattreeUtil.getCasingFromStack(CatHerderAPI.CASING_MATERIAL, inv.getItem(0));

        return CatTreeUtil.createItemStack(colorId);
    }

//    @Override
//    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
//        NonNullList<ItemStack> nonNullList = NonNullList.<ItemStack>withSize(inv.getContainerSize(), ItemStack.EMPTY);
//
//        for (int i = 0; i < nonNullList.size(); ++i) {
//            ItemStack itemstack = inv.getItem(i);
//            nonNullList.set(i, net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack));
//        }
//
//        return nonNullList;
//    }

    //Is on a 3x3 grid or bigger
    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CAT_TREE.get();
    }

    @Override
    public int getRecipeWidth() {
        return 3;
    }

    @Override
    public int getRecipeHeight() {
        return 3;
    }
}
