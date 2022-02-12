package kittytalents.common;

import kittytalents.KittyItems;
import kittytalents.common.block.tileentity.FoodBowlTileEntity;
import kittytalents.common.inventory.container.DogInventoriesContainer;
import kittytalents.common.inventory.container.PackPuppyContainer;
import kittytalents.common.inventory.container.TreatBagContainer;
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

    public static class PackPuppyContainerProvider implements MenuProvider {

        private kittytalents.api.inferface.AbstractCatEntity cat;

        public PackPuppyContainerProvider(kittytalents.api.inferface.AbstractCatEntity dogIn) {
            this.cat = dogIn;
        }

        @Override
        public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
            return new PackPuppyContainer(windowId, inventory, this.cat);
        }

        @Override
        public Component getDisplayName() {
            return new TranslatableComponent("container.kittytalents.pack_puppy");
        }
    }

    public static class DogInventoriesContainerProvider implements MenuProvider {

        private List<kittytalents.common.entity.CatEntity> dogs;

        public DogInventoriesContainerProvider(List<kittytalents.common.entity.CatEntity> dogIn) {
            this.dogs = dogIn;
        }

        @Override
        public AbstractContainerMenu createMenu(int windowId, Inventory inventory, Player player) {
            SimpleContainerData array = new SimpleContainerData(this.dogs.size());
            for (int i = 0; i < array.getCount(); i++) {
                array.set(i, this.dogs.get(i).getId());
            }
            return new DogInventoriesContainer(windowId, inventory, array);
        }

        @Override
        public Component getDisplayName() {
            return new TranslatableComponent("container.kittytalents.dog_inventories");
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
            return new TranslatableComponent("container.kittytalents.treat_bag");
        }
    }

    public static void openPackPuppyScreen(ServerPlayer player, kittytalents.api.inferface.AbstractCatEntity dogIn) {
        if (dogIn.isAlive()) {
            NetworkHooks.openGui(player, new PackPuppyContainerProvider(dogIn), (buf) -> {
                buf.writeInt(dogIn.getId());
            });
        }
    }

    public static void openDogInventoriesScreen(ServerPlayer player, List<kittytalents.common.entity.CatEntity> dogIn) {
        if (!dogIn.isEmpty()) {
            NetworkHooks.openGui(player, new DogInventoriesContainerProvider(dogIn), (buf) -> {
                buf.writeInt(dogIn.size());
                for (kittytalents.common.entity.CatEntity cat : dogIn) {
                    buf.writeInt(cat.getId());
                }
            });
        }
    }

    public static void openFoodBowlScreen(ServerPlayer player, FoodBowlTileEntity foodBowl) {
        NetworkHooks.openGui(player, foodBowl, foodBowl.getBlockPos());
    }

    public static void openTreatBagScreen(ServerPlayer player, ItemStack stackIn, int slotId) {
        if (stackIn.getItem() == KittyItems.TREAT_BAG.get()) {
            NetworkHooks.openGui(player, new TreatBagContainerProvider(stackIn, slotId), buf -> {
                buf.writeVarInt(slotId);
                buf.writeItem(stackIn);
            });
        }
    }

}
