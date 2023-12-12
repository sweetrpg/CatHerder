package com.sweetrpg.catherder.common.registry;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.common.inventory.container.CatInventoriesContainer;
import com.sweetrpg.catherder.common.inventory.container.FoodBowlContainer;
import com.sweetrpg.catherder.common.inventory.container.PackCatContainer;
import com.sweetrpg.catherder.common.inventory.container.TreatBagContainer;
import com.sweetrpg.catherder.common.lib.Constants;
import com.sweetrpg.catherder.common.entity.CatEntity;
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

public class ModContainerTypes {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.Keys.CONTAINER_TYPES, CatHerderAPI.MOD_ID);

    public static final RegistryObject<MenuType<FoodBowlContainer>> CAT_BOWL = register("cat_bowl", (windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new FoodBowlContainer(windowId, inv.player.level, pos, inv, inv.player);
    });
    public static final RegistryObject<MenuType<PackCatContainer>> PACK_CAT = register("pack_cat", (windowId, inv, data) -> {
        Entity entity = inv.player.level.getEntity(data.readInt());
        return entity instanceof CatEntity ? new PackCatContainer(windowId, inv, (CatEntity) entity) : null;
    });
    public static final RegistryObject<MenuType<TreatBagContainer>> TREAT_BAG = register("treat_bag", (windowId, inv, data) -> {
        int slotId = data.readByte();
        return new TreatBagContainer(windowId, inv, slotId, data.readItem());
    });
    public static final RegistryObject<MenuType<CatInventoriesContainer>> CAT_INVENTORIES = register("cat_inventories", (windowId, inv, data) -> {
        int noCats = data.readInt();
        List<CatEntity> cats = new ArrayList<>(noCats);
        SimpleContainerData array = new SimpleContainerData(noCats);
        for (int i = 0; i < noCats; i++) {
            Entity entity = inv.player.level.getEntity(data.readInt());
            if (entity instanceof CatEntity) {
                cats.add((CatEntity) entity);
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
