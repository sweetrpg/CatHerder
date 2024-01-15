package com.sweetrpg.catherder.common.registry;

import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.registry.IColorMaterial;
import com.sweetrpg.catherder.api.registry.IStructureMaterial;
import com.sweetrpg.catherder.common.block.CatTreeBlock;
import com.sweetrpg.catherder.common.block.PetDoorBlock;
import com.sweetrpg.catherder.common.item.IDyeableArmorItem;
import com.sweetrpg.catherder.common.util.CatTreeUtil;
import com.sweetrpg.catherder.common.util.PetDoorUtil;
import com.sweetrpg.catherder.common.util.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModItemGroups {

    public static final DeferredRegister<CreativeModeTab> ITEM_GROUP = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CatHerderAPI.MOD_ID);
// TODO   public static CreativeModeTab CAT_TREE;
// TODO   public static CreativeModeTab PET_DOOR;

    public static RegistryObject<CreativeModeTab> GENERAL = register("tabgeneral", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.catherder"))
            .icon(() -> {
                return new ItemStack(ModItems.TRAINING_TREAT.get());
            })
            .displayItems((a, b) -> {
                var allItemsIter = ModItems.ITEMS.getEntries();
                for (var val : allItemsIter) {
                    if (val.get() instanceof BlockItem blockItem) {
                        if(blockItem.getBlock() instanceof CatTreeBlock) {
                            for(IColorMaterial colorId : CatHerderAPI.COLOR_MATERIAL.get().getValues()) {
                                b.accept(CatTreeUtil.createItemStack(colorId));
                            }
                            continue;
                        }
                        else if(blockItem.getBlock() instanceof PetDoorBlock) {
                            for(IStructureMaterial structureId : CatHerderAPI.STRUCTURE_MATERIAL.get().getValues()) {
                                b.accept(PetDoorUtil.createItemStack(structureId));
                            }
                            continue;
                        }
                    }
                    b.accept(val.get());
                }
            }).build());

    public static RegistryObject<CreativeModeTab> register(String name, Supplier<CreativeModeTab> sup) {
        return ITEM_GROUP.register(name, sup);
    }

}
