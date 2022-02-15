package com.sweetrpg.catherder.common.util;

import com.sweetrpg.catherder.common.registry.ModBlocks;
import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.registry.IBeddingMaterial;
import com.sweetrpg.catherder.api.registry.ICasingMaterial;
import com.sweetrpg.catherder.common.block.tileentity.CatBedTileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class CatBedUtil {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    public static void setBedVariant(CatBedTileEntity catBedTileEntity, ItemStack stack) {
        Pair<ICasingMaterial, IBeddingMaterial> materials = CatBedUtil.getMaterials(stack);

        catBedTileEntity.setCasing(materials.getLeft());
        catBedTileEntity.setBedding(materials.getRight());
    }

    public static ItemStack createRandomBed() {
        ICasingMaterial casing = pickRandom(CatHerderAPI.CASING_MATERIAL);
        IBeddingMaterial bedding = pickRandom(CatHerderAPI.BEDDING_MATERIAL);
        return CatBedUtil.createItemStack(casing, bedding);
    }

    public static Pair<ICasingMaterial, IBeddingMaterial> getMaterials(ItemStack stack) {
        CompoundTag tag = stack.getTagElement("catherder");
        if (tag != null) {
            ICasingMaterial casingId = NBTUtil.getRegistryValue(tag, "casingId", CatHerderAPI.CASING_MATERIAL);
            IBeddingMaterial beddingId = NBTUtil.getRegistryValue(tag, "beddingId", CatHerderAPI.BEDDING_MATERIAL);

            return Pair.of(casingId, beddingId);
        }

        return Pair.of(null, null);
    }

    public static ItemStack createItemStack(ICasingMaterial casingId, IBeddingMaterial beddingId) {
        ItemStack stack = new ItemStack(ModBlocks.CAT_BED.get(), 1);

        CompoundTag tag = stack.getOrCreateTagElement("catherder");
        NBTUtil.putRegistryValue(tag, "casingId", casingId);
        NBTUtil.putRegistryValue(tag, "beddingId", beddingId);

        return stack;
    }

    public static ICasingMaterial getCasingFromStack(IForgeRegistry<ICasingMaterial> registry, ItemStack stack) {
        for (ICasingMaterial m : registry.getValues()) {
            if (m.getIngredient().test(stack)) {
                return m;
            }
        }

        return null;
    }

    public static IBeddingMaterial getBeddingFromStack(IForgeRegistry<IBeddingMaterial> registry, ItemStack stack) {
        for (IBeddingMaterial m : registry.getValues()) {
            if (m.getIngredient().test(stack)) {
                return m;
            }
        }

        return null;
    }

    public static <T extends IForgeRegistryEntry<T>> T pickRandom(IForgeRegistry<T> registry) {
        Collection<T> values = registry.getValues();
        List<T> list = values instanceof List ? (List<T>) values : new ArrayList<>(values);
        return list.get(RANDOM.nextInt(list.size()));
    }
}
