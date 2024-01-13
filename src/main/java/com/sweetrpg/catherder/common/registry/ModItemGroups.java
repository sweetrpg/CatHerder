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

public class ModItemGroups {

    public static CreativeModeTab GENERAL;
// TODO   public static CreativeModeTab CAT_TREE;
// TODO   public static CreativeModeTab PET_DOOR;

//    public static final CreativeModeTab GENERAL = new CustomItemGroup("catherder", () -> new ItemStack(ModItems.TRAINING_TREAT.get()));
//    public static final CreativeModeTab CAT_TREE = new CustomItemGroup("catherder.cattree", CattreeUtil::createRandomBed);

    public static void creativeModeTabRegisterEvent(final CreativeModeTabEvent.Register event) {
        GENERAL = event.registerCreativeModeTab(Util.getResource("main"), (builder) -> builder.title(Component.translatable("itemGroup.catherder")).icon(() -> new ItemStack(ModItems.TRAINING_TREAT.get())));
    }

    public static void creativeModeTabBuildEvent(final CreativeModeTabEvent.BuildContents event) {
        CatHerder.LOGGER.debug("Creative mode tab build event: {}", event);

        for(var item : ModItems.ITEMS.getEntries()) {
            if(item.get() instanceof IDyeableArmorItem dyeableItem) {
                ItemStack stack = new ItemStack(item.get());

                dyeableItem.setColor(stack, dyeableItem.getDefaultColor(stack));
                event.accept(stack);
                continue;
            }
            else if(item.get() instanceof BlockItem blockItem) {
                if(blockItem.getBlock() instanceof CatTreeBlock) {
                    for(IColorMaterial colorId : CatHerderAPI.COLOR_MATERIAL.get().getValues()) {
                        event.accept(CatTreeUtil.createItemStack(colorId));
                    }
                    continue;
                }
                else if(blockItem.getBlock() instanceof PetDoorBlock) {
                    for(IStructureMaterial structureId : CatHerderAPI.STRUCTURE_MATERIAL.get().getValues()) {
                        event.accept(PetDoorUtil.createItemStack(structureId));
                    }
                    continue;
                }
            }

            event.accept(item);
        }
    }
}
