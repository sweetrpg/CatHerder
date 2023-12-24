package com.sweetrpg.catherder.common;

import com.sweetrpg.catherder.common.registry.ModItems;
import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.common.block.entity.CatBowlBlockEntity;
import com.sweetrpg.catherder.common.inventory.container.CatInventoriesContainer;
import com.sweetrpg.catherder.common.inventory.container.PackCatContainer;
import com.sweetrpg.catherder.common.inventory.container.TreatBagContainer;
import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkHooks;

import java.util.List;

public class Screens {

    public static class PackCatContainerProvider implements MenuProvider {

        private AbstractCatEntity cat;

        public PackCatContainerProvider(AbstractCatEntity catIn) {
            this.cat = catIn;
        }

        @Override
        public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
            return new PackCatContainer(windowId, inventory, this.cat);
        }

        @Override
        public Component getDisplayName() {
            return new TranslatableComponent("container.catherder.pack_cat");
        }
    }

    public static class CatInventoriesContainerProvider implements MenuProvider {

        private List<CatEntity> cats;

        public CatInventoriesContainerProvider(List<CatEntity> catIn) {
            this.cats = catIn;
        }

        @Override
        public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
            SimpleContainerData array = new SimpleContainerData(this.cats.size());
            for (int i = 0; i < array.getCount(); i++) {
                array.set(i, this.cats.get(i).getId());
            }
            return new CatInventoriesContainer(windowId, inventory, array);
        }

        @Override
        public Component getDisplayName() {
            return new TranslatableComponent("container.catherder.cat_inventories");
        }
    }

    public static class TreatBagContainerProvider implements MenuProvider {

        private ItemStack stack;
        private int slotId;

        public TreatBagContainerProvider(ItemStack stackIn, int slotId) {
            this.stack = stackIn;
            this.slotId = slotId;
        }

        @Override
        public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
            return new TreatBagContainer(windowId, inventory, this.slotId, this.stack);
        }

        @Override
        public Component getDisplayName() {
            return new TranslatableComponent("container.catherder.treat_bag");
        }
    }

    public static void openPackCatScreen(ServerPlayer player, AbstractCatEntity catIn) {
        if (catIn.isAlive()) {
            NetworkHooks.openGui(player, new PackCatContainerProvider(catIn), (buf) -> {
                buf.writeInt(catIn.getId());
            });
        }
    }

    public static void openCatInventoriesScreen(ServerPlayer player, List<CatEntity> catIn) {
        if (!catIn.isEmpty()) {
            NetworkHooks.openGui(player, new CatInventoriesContainerProvider(catIn), (buf) -> {
                buf.writeInt(catIn.size());
                for (CatEntity cat : catIn) {
                    buf.writeInt(cat.getId());
                }
            });
        }
    }

    public static void openFoodBowlScreen(ServerPlayer player, CatBowlBlockEntity foodBowl) {
        NetworkHooks.openGui(player, foodBowl, foodBowl.getBlockPos());
    }

    public static void openTreatBagScreen(ServerPlayer player, ItemStack stackIn, int slotId) {
        if (stackIn.getItem() == ModItems.TREAT_BAG.get()) {
            NetworkHooks.openGui(player, new TreatBagContainerProvider(stackIn, slotId), buf -> {
                buf.writeVarInt(slotId);
                buf.writeItem(stackIn);
            });
        }
    }

}
