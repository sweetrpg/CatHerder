package com.sweetrpg.catherder.common.inventory.recipe;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.registry.IStructureMaterial;
import com.sweetrpg.catherder.common.registry.ModRecipeSerializers;
import com.sweetrpg.catherder.common.util.PetDoorUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.IShapedRecipe;

public class PetDoorRecipe extends CustomRecipe implements IShapedRecipe<CraftingContainer> {

    public PetDoorRecipe(ResourceLocation resource) {
        super(resource);
    }

    @Override
    public boolean matches(CraftingContainer inv, Level worldIn) {
        IStructureMaterial structureId = null;

        for(int col = 0; col < 3; col++) {
            for(int row = 0; row < 3; row++) {
                if(col == 1 && row == 1) {
                    if(!inv.getItem(row * inv.getWidth() + col).is(ItemTags.DOORS)) {
                        return false;
                    }
                }
                else if(col == 1 && row == 2) {
                    if(!inv.getItem(row * inv.getWidth() + col).is(Items.TRIPWIRE_HOOK)) {
                        return false;
                    }
                }
                else {
                    IStructureMaterial id = PetDoorUtil.getStructureFromStack(CatHerderAPI.STRUCTURE_MATERIAL.get(), inv.getItem(row * inv.getWidth() + col));

                    // If the item is not structure material, then it doesn't match this recipe
                    if(id == null) {
                        return false;
                    }

                    if(structureId == null) {
                        structureId = id;
                    }
                    else if(structureId != id) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public ItemStack assemble(CraftingContainer inv) {
        IStructureMaterial structureId = PetDoorUtil.getStructureFromStack(CatHerderAPI.STRUCTURE_MATERIAL.get(), inv.getItem(1));

        return PetDoorUtil.createItemStack(structureId);
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
        NonNullList<ItemStack> nonNullList = NonNullList.<ItemStack>withSize(inv.getContainerSize(), ItemStack.EMPTY);

        for(int i = 0; i < nonNullList.size(); ++i) {
            ItemStack itemstack = inv.getItem(i);
            nonNullList.set(i, net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack));
        }

        return nonNullList;
    }

    //Is on a 3x3 grid or bigger
    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.PET_DOOR.get();
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
