package kittytalents.common.util;

import kittytalents.KittyBlocks;
import kittytalents.api.KittyTalentsAPI;
import kittytalents.api.registry.IBeddingMaterial;
import kittytalents.api.registry.ICasingMaterial;
import kittytalents.common.block.tileentity.DogBedTileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class DogBedUtil {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    public static void setBedVariant(DogBedTileEntity dogBedTileEntity, ItemStack stack) {
        Pair<ICasingMaterial, IBeddingMaterial> materials = DogBedUtil.getMaterials(stack);

        dogBedTileEntity.setCasing(materials.getLeft());
        dogBedTileEntity.setBedding(materials.getRight());
    }

    public static ItemStack createRandomBed() {
        ICasingMaterial casing = pickRandom(KittyTalentsAPI.CASING_MATERIAL);
        IBeddingMaterial bedding = pickRandom(KittyTalentsAPI.BEDDING_MATERIAL);
        return DogBedUtil.createItemStack(casing, bedding);
    }

    public static Pair<ICasingMaterial, IBeddingMaterial> getMaterials(ItemStack stack) {
        CompoundTag tag = stack.getTagElement("kittytalents");
        if (tag != null) {
            ICasingMaterial casingId = NBTUtil.getRegistryValue(tag, "casingId", KittyTalentsAPI.CASING_MATERIAL);
            IBeddingMaterial beddingId = NBTUtil.getRegistryValue(tag, "beddingId", KittyTalentsAPI.BEDDING_MATERIAL);

            return Pair.of(casingId, beddingId);
        }

        return Pair.of(null, null);
    }

    public static ItemStack createItemStack(ICasingMaterial casingId, IBeddingMaterial beddingId) {
        ItemStack stack = new ItemStack(KittyBlocks.CAT_BED.get(), 1);

        CompoundTag tag = stack.getOrCreateTagElement("kittytalents");
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
