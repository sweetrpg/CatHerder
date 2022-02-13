package kittytalents;

import kittytalents.common.inventory.container.CatInventoriesContainer;
import kittytalents.common.inventory.container.FoodBowlContainer;
import kittytalents.common.inventory.container.PackKittyContainer;
import kittytalents.common.inventory.container.TreatBagContainer;
import kittytalents.common.lib.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class KittyContainerTypes {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Constants.MOD_ID);

    public static final RegistryObject<MenuType<FoodBowlContainer>> FOOD_BOWL = register("food_bowl", (windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new FoodBowlContainer(windowId, inv.player.level, pos, inv, inv.player);
    });
    public static final RegistryObject<MenuType<PackKittyContainer>> PACK_KITTY = register("pack_kitty", (windowId, inv, data) -> {
        Entity entity = inv.player.level.getEntity(data.readInt());
        return entity instanceof kittytalents.common.entity.CatEntity ? new PackKittyContainer(windowId, inv, (kittytalents.common.entity.CatEntity) entity) : null;
    });
    public static final RegistryObject<MenuType<TreatBagContainer>> TREAT_BAG = register("treat_bag", (windowId, inv, data) -> {
        int slotId = data.readByte();
        return new TreatBagContainer(windowId, inv, slotId, data.readItem());
    });
    public static final RegistryObject<MenuType<CatInventoriesContainer>> CAT_INVENTORIES = register("cat_inventories", (windowId, inv, data) -> {
        int noCats = data.readInt();
        List<kittytalents.common.entity.CatEntity> cats = new ArrayList<>(noCats);
        SimpleContainerData array = new SimpleContainerData(noCats);
        for (int i = 0; i < noCats; i++) {
            Entity entity = inv.player.level.getEntity(data.readInt());
            if (entity instanceof kittytalents.common.entity.CatEntity) {
                cats.add((kittytalents.common.entity.CatEntity) entity);
                array.set(i, entity.getId());
            }
        }
        return !cats.isEmpty() ? new CatInventoriesContainer(windowId, inv, array) : null;
    });

    private static <X extends AbstractContainerMenu, T extends MenuType<X>> RegistryObject<MenuType<X>> register(final String name, final IContainerFactory<X> factory) {
        return register(name, () -> IForgeMenuType.create(factory));
    }

    private static <T extends MenuType<?>> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return CONTAINERS.register(name, sup);
    }
}
