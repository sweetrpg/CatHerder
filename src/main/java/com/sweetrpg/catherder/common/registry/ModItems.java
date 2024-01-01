package com.sweetrpg.catherder.common.registry;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.api.feature.CatLevel;
import com.sweetrpg.catherder.api.registry.Accessory;
import com.sweetrpg.catherder.common.lib.FoodValues;
import com.sweetrpg.catherder.common.entity.accessory.DyeableAccessory;
import com.sweetrpg.catherder.common.item.*;
import com.sweetrpg.catherder.common.util.Util;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.item.*;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.Keys.ITEMS, CatHerderAPI.MOD_ID);

//    public static final RegistryObject<Item> CAT_TREE = register("cat_tree", () -> new BlockItem(ModBlocks.CAT_TREE.get(), new Item.Properties().tab(ModItemGroups.GENERAL)));

    // catnip
    public static final RegistryObject<Item> WILD_CATNIP = register("wild_catnip", () -> new BlockItem(ModBlocks.WILD_CATNIP.get(), new Item.Properties().tab(ModItemGroups.GENERAL)));
    public static final RegistryObject<Item> CATNIP = register("catnip", () -> new CatnipItem(new Item.Properties()./*food(FoodValues.CATNIP).*/tab(ModItemGroups.GENERAL)));
    public static final RegistryObject<Item> CATNIP_SEEDS = ITEMS.register("catnip_seeds", () -> new ItemNameBlockItem(ModBlocks.CATNIP_CROP.get(), new Item.Properties().tab(ModItemGroups.GENERAL)));

    // toys
    public static final RegistryObject<Item> YARN = registerThrowToy("yarn");
    public static final RegistryObject<Item> CAT_TOY = registerThrowToy("cat_toy");

    // mobs and related
//    public static final RegistryObject<Item> RODENT = register("rodent");
    public static final RegistryObject<Item> CAT_GUT = register("cat_gut");

    // food
    public static final RegistryObject<Item> CHEESE_WEDGE = register("cheese_wedge",
                                                                     () -> new Item(new Item.Properties().food(FoodValues.CHEESE).tab(ModItemGroups.GENERAL)));
//    public static final RegistryObject<Item> LASAGNA = register("lasagna");

    // treats
    public static final RegistryObject<Item> TRAINING_TREAT = registerTreat("training_treat", CatLevel.Type.NORMAL, 20);
    public static final RegistryObject<Item> SUPER_TREAT = registerTreat("super_treat", CatLevel.Type.NORMAL, 40);
    public static final RegistryObject<Item> MASTER_TREAT = registerTreat("master_treat", CatLevel.Type.NORMAL, 60);
    public static final RegistryObject<Item> WILD_TREAT = registerTreat("wild_treat", CatLevel.Type.WILD, 30);
    public static final RegistryObject<Item> BREEDING_TREAT = register("breeding_treat");

    // maintenance items
    public static final RegistryObject<Item> CAT_SHEARS = registerWith("collar_shears", CatShearsItem::new, 1);
    public static final RegistryObject<Item> CAT_CHARM = registerWith("cat_charm", CatCharmItem::new, 1);
    public static final RegistryObject<Item> RADAR = registerWith("radar", RadarItem::new, 1);
    public static final RegistryObject<Item> CREATIVE_RADAR = registerWith("creative_radar", RadarItem::new, 1);
    public static final RegistryObject<Item> TREAT_BAG = registerWith("treat_bag", TreatBagItem::new, 1);
    public static final RegistryObject<Item> CAT_SMALLERER = registerSizer("small_catsizer", CatSizerItem.Type.SMALL);
    public static final RegistryObject<Item> CAT_BIGGERER = registerSizer("big_catsizer", CatSizerItem.Type.BIG);
    public static final RegistryObject<Item> OWNER_CHANGE = registerWith("owner_change", ChangeOwnerItem::new, 1);
    public static final RegistryObject<Item> LITTER_SCOOP = register("litter_scoop", () -> new LitterScoopItem(new Item.Properties().durability(64).tab(ModItemGroups.GENERAL)));
    //public static final RegistryObject<Item> PATROL = registerWith("patrol_item", PatrolItem::new, 1);

    // other items
    public static final RegistryObject<Item> CARDBOARD = register("cardboard");

    // accessories
    public static final RegistryObject<AccessoryItem> RADIO_COLLAR = registerAccessory("radio_collar", ModAccessories.RADIO_BAND);
    public static final RegistryObject<DyeableAccessoryItem> WOOL_COLLAR = registerAccessoryDyed("wool_collar", ModAccessories.DYEABLE_COLLAR);
    public static final RegistryObject<AccessoryItem> CREATIVE_COLLAR = registerAccessory("creative_collar", ModAccessories.GOLDEN_COLLAR);
    public static final RegistryObject<AccessoryItem> SPOTTED_COLLAR = registerAccessory("spotted_collar", ModAccessories.SPOTTED_COLLAR);
    public static final RegistryObject<AccessoryItem> MULTICOLORED_COLLAR = registerAccessory("multicolored_collar", ModAccessories.MULTICOLORED_COLLAR);
    public static final RegistryObject<AccessoryItem> CAPE = registerAccessory("cape", ModAccessories.CAPE);
    //    public static final RegistryObject<DyeableAccessoryItem> CAPE_COLOURED = registerAccessoryDyed("cape_coloured", CatAccessories.DYEABLE_CAPE);
    public static final RegistryObject<AccessoryItem> SUNGLASSES = registerAccessory("sunglasses", ModAccessories.SUNGLASSES);
    //    public static final RegistryObject<AccessoryItem> GUARD_SUIT = registerAccessory("guard_suit", CatAccessories.GUARD_SUIT);
//    public static final RegistryObject<AccessoryItem> LEATHER_JACKET = registerAccessory("leather_jacket", CatAccessories.LEATHER_JACKET_CLOTHING);

    private static Item.Properties createInitialProp() {
        return new Item.Properties().tab(ModItemGroups.GENERAL);
    }

    private static RegistryObject<Item> registerThrowToy(final String name) {
        return register(name, () -> new ThrowableItem(createInitialProp().stacksTo(2)));
    }

    private static RegistryObject<Item> registerSizer(final String name, final CatSizerItem.Type typeIn) {
        return register(name, () -> new CatSizerItem(typeIn, createInitialProp()));
    }

    private static RegistryObject<Item> registerTreat(final String name, final CatLevel.Type typeIn, int maxLevel) {
        return register(name, () -> new TreatItem(maxLevel, typeIn, createInitialProp()));
    }

    private static RegistryObject<DyeableAccessoryItem> registerAccessoryDyed(final String name, Supplier<? extends DyeableAccessory> type) {
        return register(name, () -> new DyeableAccessoryItem(type, createInitialProp()));
    }

    private static RegistryObject<AccessoryItem> registerAccessory(final String name, Supplier<? extends Accessory> type) {
        return register(name, () -> new AccessoryItem(type, createInitialProp()));
    }

    private static <T extends Item> RegistryObject<T> registerWith(final String name, Function<Item.Properties, T> itemConstructor, int maxStackSize) {
        return register(name, () -> itemConstructor.apply(createInitialProp().stacksTo(maxStackSize)));
    }

    private static <T extends Item> RegistryObject<T> register(final String name, Function<Item.Properties, T> itemConstructor) {
        return register(name, () -> itemConstructor.apply(createInitialProp()));
    }

    private static RegistryObject<Item> register(final String name) {
        return registerWith(name, null);
    }

    private static RegistryObject<Item> registerWith(final String name, @Nullable Function<Item.Properties, Item.Properties> extraPropFunc) {
        Item.Properties prop = createInitialProp();
        return register(name, () -> new Item(extraPropFunc != null ? extraPropFunc.apply(prop) : prop));
    }

    private static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return ITEMS.register(name, sup);
    }

    public static void registerItemColours(final ColorHandlerEvent.Item event) {
        ItemColors itemColors = event.getItemColors();
        Util.acceptOrElse(ModItems.WOOL_COLLAR, (item) -> {
            itemColors.register((stack, tintIndex) -> {
                return tintIndex > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack);
            }, item);
        }, ModBlocks::logError);

//        Util.acceptOrElse(CatItems.CAPE_COLOURED, (item) -> {
//            itemColors.register((stack, tintIndex) -> {
//                return tintIndex > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack);
//             }, item);
//        }, CatBlocks::logError);

//        Util.acceptOrElse(CatBlocks.CAT_BATH, (item) -> {
//            itemColors.register((stack, tintIndex) -> {
//                return 4159204;
//             }, item);
//        }, CatBlocks::logError);
    }
}
