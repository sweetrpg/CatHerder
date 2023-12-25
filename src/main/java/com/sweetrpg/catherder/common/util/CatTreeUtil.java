package com.sweetrpg.catherder.common.util;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.registry.IDyeMaterial;
import com.sweetrpg.catherder.api.registry.IColorMaterial;
import com.sweetrpg.catherder.common.block.entity.CatTreeBlockEntity;
import com.sweetrpg.catherder.common.registry.ModBlocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class CatTreeUtil {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    public static void setTreeVariant(CatTreeBlockEntity catTreeBlockEntity, ItemStack stack) {
        IColorMaterial colorMaterial = CatTreeUtil.getColorMaterial(stack);

        catTreeBlockEntity.setColor(colorMaterial);
    }

    public static ItemStack createRandomTree() {
        IColorMaterial color = pickRandom(CatHerderAPI.COLOR_MATERIAL.get());
        return CatTreeUtil.createItemStack(color);
    }

    public static IColorMaterial getColorMaterial(ItemStack stack) {
        CompoundTag tag = stack.getTagElement("catherder");
        if (tag != null) {
            IColorMaterial colorId = NBTUtil.getRegistryValue(tag, "colorId", CatHerderAPI.COLOR_MATERIAL.get());

            return colorId;
        }

        return null;
    }

    public static ItemStack createItemStack(IColorMaterial colorId) {
        ItemStack stack = new ItemStack(ModBlocks.CAT_TREE.get(), 1);

        CompoundTag tag = stack.getOrCreateTagElement("catherder");
        NBTUtil.putRegistryValue(tag, "colorId", colorId);
//        NBTUtil.putRegistryValue(tag, "beddingId", beddingId);

        return stack;
    }

    public static IColorMaterial getColorFromStack(IForgeRegistry<IColorMaterial> registry, ItemStack stack) {
        for (IColorMaterial m : registry.getValues()) {
            if (m.getIngredient().test(stack)) {
                return m;
            }
        }

        return null;
    }

    public static IDyeMaterial getDyeFromStack(IForgeRegistry<IDyeMaterial> registry, ItemStack stack) {
        for (IDyeMaterial m : registry.getValues()) {
            if (m.getIngredient().test(stack)) {
                return m;
            }
        }

        return null;
    }

//    public static IBeddingMaterial getBeddingFromStack(IForgeRegistry<IBeddingMaterial> registry, ItemStack stack) {
//        for (IBeddingMaterial m : registry.getValues()) {
//            if (m.getIngredient().test(stack)) {
//                return m;
//            }
//        }
//
//        return null;
//    }

    public static <T extends IForgeRegistryEntry<T>> T pickRandom(IForgeRegistry<T> registry) {
        Collection<T> values = registry.getValues();
        List<T> list = values instanceof List ? (List<T>) values : new ArrayList<>(values);
        return list.get(RANDOM.nextInt(list.size()));
    }
}
