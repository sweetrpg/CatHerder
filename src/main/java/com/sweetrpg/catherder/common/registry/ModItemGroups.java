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
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;

import java.util.List;
import java.util.function.Consumer;

public class ModItemGroups {

    public static CreativeModeTab GENERAL;
    public static CreativeModeTab CAT_TREE;
    public static CreativeModeTab PET_DOOR;

    public static void onCreativeTabRegister(CreativeModeTabEvent.Register event) {
        Consumer<CreativeModeTab.Builder> GENERAL_BUILDER = builder ->
                builder.title(Component.translatable("itemGroup.catherder"))
                        .icon(() -> {
                            return new ItemStack(ModItems.TRAINING_TREAT.get());
                        })
                        .displayItems((a, b) -> {
                            var allItemsIter = ModItems.ITEMS.getEntries();
                            for (var val : allItemsIter) {
                                if (val.get() instanceof BlockItem blockItem) {
                                    if (blockItem.getBlock() instanceof CatTreeBlock) {
                                        continue;
                                    }
                                    else if (blockItem.getBlock() instanceof PetDoorBlock) {
                                        continue;
                                    }
                                }
                                b.accept(val.get());
                            }
                        });

        GENERAL = event.registerCreativeModeTab(
                Util.getResource("ch_tab_general"),
                GENERAL_BUILDER);

        Consumer<CreativeModeTab.Builder> CAT_TREE_BUILDER = builder ->
                builder.title(Component.translatable("itemGroup.catherder.cattree"))
                        .icon(CatTreeUtil::createRandomTree)
                        .displayItems((a, b) -> {
                            for(IColorMaterial colorId : CatHerderAPI.COLOR_MATERIAL.get().getValues()) {
                                b.accept(CatTreeUtil.createItemStack(colorId));
                            }
                        });

        CAT_TREE = event.registerCreativeModeTab(
                Util.getResource("ch_tab_cattree"),
                List.of(), List.of(GENERAL),
                CAT_TREE_BUILDER
        );

        Consumer<CreativeModeTab.Builder> PET_DOOR_BUILDER = builder ->
                builder.title(Component.translatable("itemGroup.catherder.petdoor"))
                        .icon(PetDoorUtil::createRandomDoor)
                        .displayItems((a, b) -> {
                            for(IStructureMaterial structureId : CatHerderAPI.STRUCTURE_MATERIAL.get().getValues()) {
                                b.accept(PetDoorUtil.createItemStack(structureId));
                            }
                        });

        PET_DOOR = event.registerCreativeModeTab(
                Util.getResource("ch_tab_petdoor"),
                List.of(), List.of(CAT_TREE),
                PET_DOOR_BUILDER
        );
    }

}
