package com.sweetrpg.catherder.common.util;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.registry.IStructureMaterial;
import com.sweetrpg.catherder.common.block.entity.PetDoorBlockEntity;
import com.sweetrpg.catherder.common.registry.ModBlocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class PetDoorUtil {

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    public static void setDoorVariant(PetDoorBlockEntity petDoorBlockEntity, ItemStack stack) {
        IStructureMaterial structureMaterial = PetDoorUtil.getStructureMaterial(stack);

        petDoorBlockEntity.setStructure(structureMaterial);
    }

    public static ItemStack createRandomDoor() {
        IStructureMaterial structure = pickRandom(CatHerderAPI.STRUCTURE_MATERIAL.get());
        return PetDoorUtil.createItemStack(structure);
    }

    public static IStructureMaterial getStructureMaterial(ItemStack stack) {
        CompoundTag tag = stack.getTagElement("catherder");
        if (tag != null) {
            IStructureMaterial structureId = NBTUtil.getRegistryValue(tag, "structureId", CatHerderAPI.STRUCTURE_MATERIAL.get());

            return structureId;
        }

        return null;
    }

    public static ItemStack createItemStack(IStructureMaterial structureId) {
        ItemStack stack = new ItemStack(ModBlocks.PET_DOOR.get(), 1);

        CompoundTag tag = stack.getOrCreateTagElement("catherder");
        NBTUtil.putRegistryValue(tag, "structureId", structureId);

        return stack;
    }

    public static IStructureMaterial getStructureFromStack(IForgeRegistry<IStructureMaterial> registry, ItemStack stack) {
        for (IStructureMaterial m : registry.getValues()) {
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
