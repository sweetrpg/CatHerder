package com.sweetrpg.catherder.common.registry;

import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.common.block.*;
import com.sweetrpg.catherder.common.lib.Constants;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.Keys.BLOCKS, CatHerderAPI.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = ModItems.ITEMS;

    // cat items
    public static final RegistryObject<CatTreeBlock> CAT_TREE = registerWithItem("cat_tree", CatTreeBlock::new);
//    public static final RegistryObject<CatTreeBlock> BLACK_CAT_TREE = registerWithItem("black_cat_tree", () -> new CatTreeBlock(CatTreeBlock.Type.BLACK));
//    public static final RegistryObject<CatTreeBlock> GREY_CAT_TREE = registerWithItem("grey_cat_tree", () -> new CatTreeBlock(CatTreeBlock.Type.GREY));
//    public static final RegistryObject<CatTreeBlock> LIGHT_GREY_CAT_TREE = registerWithItem("light_grey_cat_tree", () -> new CatTreeBlock(CatTreeBlock.Type.LIGHT_GREY));
//    public static final RegistryObject<CatTreeBlock> PINK_CAT_TREE = registerWithItem("pink_cat_tree", () -> new CatTreeBlock(CatTreeBlock.Type.PINK));
//    public static final RegistryObject<CatTreeBlock> MAGENTA_CAT_TREE = registerWithItem("magenta_cat_tree", () -> new CatTreeBlock(CatTreeBlock.Type.MAGENTA));
//    public static final RegistryObject<CatTreeBlock> RED_CAT_TREE = registerWithItem("red_cat_tree", () -> new CatTreeBlock(CatTreeBlock.Type.RED));
//    public static final RegistryObject<CatTreeBlock> BROWN_CAT_TREE = registerWithItem("brown_cat_tree", () -> new CatTreeBlock(CatTreeBlock.Type.BROWN));
//    public static final RegistryObject<CatTreeBlock> ORANGE_CAT_TREE = registerWithItem("orange_cat_tree", () -> new CatTreeBlock(CatTreeBlock.Type.ORANGE));
//    public static final RegistryObject<CatTreeBlock> YELLOW_CAT_TREE = registerWithItem("yellow_cat_tree", () -> new CatTreeBlock(CatTreeBlock.Type.YELLOW));
//    public static final RegistryObject<CatTreeBlock> GREEN_CAT_TREE = registerWithItem("green_cat_tree", () -> new CatTreeBlock(CatTreeBlock.Type.GREEN));
//    public static final RegistryObject<CatTreeBlock> LIME_CAT_TREE = registerWithItem("lime_cat_tree", () -> new CatTreeBlock(CatTreeBlock.Type.LIME));
//    public static final RegistryObject<CatTreeBlock> BLUE_CAT_TREE = registerWithItem("blue_cat_tree", () -> new CatTreeBlock(CatTreeBlock.Type.BLUE));
//    public static final RegistryObject<CatTreeBlock> LIGHT_BLUE_CAT_TREE = registerWithItem("light_blue_cat_tree", () -> new CatTreeBlock(CatTreeBlock.Type.LIGHT_BLUE));
//    public static final RegistryObject<CatTreeBlock> CYAN_CAT_TREE = registerWithItem("cyan_cat_tree", () -> new CatTreeBlock(CatTreeBlock.Type.CYAN));
//    public static final RegistryObject<CatTreeBlock> PURPLE_CAT_TREE = registerWithItem("purple_cat_tree", () -> new CatTreeBlock(CatTreeBlock.Type.PURPLE));
    public static final RegistryObject<LitterboxBlock> LITTER_BOX = registerWithItem("litter_box", LitterboxBlock::new);
    public static final RegistryObject<CatBowlBlock> CAT_BOWL = registerWithItem("cat_bowl", CatBowlBlock::new);
    public static final RegistryObject<Block> CARDBOARD_BOX = registerWithItem("cardboard_box", CardboardBoxBlock::new);

    // miscellaneous items
    public static final RegistryObject<Block> MOUSE_TRAP = registerWithItem("mouse_trap", MouseTrapBlock::new);
    public static final RegistryObject<Block> PET_DOOR = registerWithItem("pet_door", PetDoorBlock::new);

    // food
    public static final RegistryObject<Block> CHEESE_WHEEL = registerWithItem("cheese_wheel",
                                                                             () -> new CheeseWheelBlock(Block.Properties.copy(Blocks.CAKE), true));

    // crops
    public static final RegistryObject<WildCatnipBlock> WILD_CATNIP = BLOCKS.register("wild_catnip",
                                                                                      () -> new WildCatnipBlock(MobEffects.DIG_SLOWDOWN, 6, Block.Properties.copy(Blocks.TALL_GRASS)));
    public static final RegistryObject<CatnipBlock> CATNIP_CROP = BLOCKS.register("catnip",
                                                                                  () -> new CatnipBlock(Block.Properties.copy(Blocks.WHEAT)));

    private static Item.Properties createInitialProp() {
        return new Item.Properties().tab(ModItemGroups.GENERAL);
    }

    private static BlockItem makeItemBlock(Block block) {
        return makeItemBlock(block, null);
    }

    private static BlockItem makeItemBlock(Block block, @Nullable Function<Item.Properties, Item.Properties> extraPropFunc) {
        Item.Properties prop = createInitialProp();
        return new BlockItem(block, extraPropFunc != null ? extraPropFunc.apply(prop) : prop);
    }

    private static <T extends Block> RegistryObject<T> registerWithItem(final String name, final Supplier<T> blockSupplier, @Nullable Function<Item.Properties, Item.Properties> extraPropFunc) {
        return register(name, blockSupplier, (b) -> makeItemBlock(b.get(), extraPropFunc));
    }

    private static <T extends Block> RegistryObject<T> registerWithItem(final String name, final Supplier<T> blockSupplier) {
        return register(name, blockSupplier, (b) -> makeItemBlock(b.get()));
    }

    private static <T extends Block> RegistryObject<T> register(final String name, final Supplier<T> blockSupplier, final Function<RegistryObject<T>, Item> itemFunction) {
        RegistryObject<T> blockObj = register(name, blockSupplier);
        ITEMS.register(name, () -> itemFunction.apply(blockObj));
        return blockObj;
    }

    private static <T extends Block> RegistryObject<T> register(final String name, final Supplier<T> blockSupplier) {
        return BLOCKS.register(name, blockSupplier);
    }

    public static void registerBlockColours(final ColorHandlerEvent.Block event) {
        BlockColors blockColors = event.getBlockColors();

//        Util.acceptOrElse(CatBlocks.CAT_BATH, (block) -> {
//            blockColors.register((state, world, pos, tintIndex) -> {
//                return world != null && pos != null ? BiomeColors.getAverageWaterColor(world, pos) : -1;
//             }, block);
//        }, CatBlocks::logError);
    }

    public static void logError() {
        // Only try to register if blocks were successfully registered
        // Trying to avoid as reports like CatHerder#242, where it says
        // CatHerder crashed but is not the CAUSE of the crash

        CatHerder.LOGGER.info("Items/Blocks were not registered for some reason... probably because we are c...r..a..s.hing");
    }
}
