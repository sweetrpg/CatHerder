package com.sweetrpg.catherder.common.util;

import com.sweetrpg.catherder.common.block.tileentity.CatTreeBlockEntity;
import com.sweetrpg.catherder.common.registry.ModBlocks;
import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.registry.IBeddingMaterial;
import com.sweetrpg.catherder.api.registry.ICasingMaterial;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class CatTreeUtil {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    public static void setVariant(CatTreeBlockEntity catTreeBlockEntity, ItemStack stack) {
        Pair<ICasingMaterial, IBeddingMaterial> materials = CatTreeUtil.getMaterials(stack);

        catTreeBlockEntity.setCasing(materials.getLeft());
        catTreeBlockEntity.setBedding(materials.getRight());
    }

    /**
     * Create a cat tree by selecting a random bedding and casing material.
     * @return
     */
    public static ItemStack createRandomTree() {
        ICasingMaterial casing = pickRandom(CatHerderAPI.CASING_MATERIAL);
        IBeddingMaterial bedding = pickRandom(CatHerderAPI.BEDDING_MATERIAL);
        return CatTreeUtil.createItemStack(casing, bedding);
    }

    /**
     * Return a Pair containing the casing and bedding material for the item in the provided stack.
     * @param stack
     * @return
     */
    public static Pair<ICasingMaterial, IBeddingMaterial> getMaterials(ItemStack stack) {
        CompoundTag tag = stack.getTagElement("catherder");
        if (tag != null) {
            ICasingMaterial casingId = NBTUtil.getRegistryValue(tag, "casingId", CatHerderAPI.CASING_MATERIAL);
            IBeddingMaterial beddingId = NBTUtil.getRegistryValue(tag, "beddingId", CatHerderAPI.BEDDING_MATERIAL);

            return Pair.of(casingId, beddingId);
        }

        return Pair.of(null, null);
    }

    /**
     * Create an item stack containing 1 cat tree made from the specified bedding and casing.
     * @param casingId
     * @param beddingId
     * @return
     */
    public static ItemStack createItemStack(ICasingMaterial casingId, IBeddingMaterial beddingId) {
        ItemStack stack = new ItemStack(ModBlocks.CAT_TREE.get(), 1);

        CompoundTag tag = stack.getOrCreateTagElement("catherder");
        NBTUtil.putRegistryValue(tag, "casingId", casingId);
        NBTUtil.putRegistryValue(tag, "beddingId", beddingId);

        return stack;
    }

    /**
     * Determine if the item stack contains one of the materials from the casing registry.
     * @param registry
     * @param stack
     * @return
     */
    public static ICasingMaterial getCasingFromStack(IForgeRegistry<ICasingMaterial> registry, ItemStack stack) {
        for (ICasingMaterial m : registry.getValues()) {
            if (m.getIngredient().test(stack)) {
                return m;
            }
        }

        return null;
    }

    /**
     * Determine if the item stack contains one of the materials from the bedding registry.
     * @param registry
     * @param stack
     * @return
     */
    public static IBeddingMaterial getBeddingFromStack(IForgeRegistry<IBeddingMaterial> registry, ItemStack stack) {
        for (IBeddingMaterial m : registry.getValues()) {
            if (m.getIngredient().test(stack)) {
                return m;
            }
        }

        return null;
    }

    /**
     * Pick a random item from the specified registry.
     * @param registry
     * @param <T>
     * @return
     */
    public static <T extends IForgeRegistryEntry<T>> T pickRandom(IForgeRegistry<T> registry) {
        Collection<T> values = registry.getValues();
        List<T> list = values instanceof List ? (List<T>) values : new ArrayList<>(values);
        return list.get(RANDOM.nextInt(list.size()));
    }
}
