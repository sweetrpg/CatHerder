package com.sweetrpg.catherder.common.registry;

//import com.sweetrpg.catherder.common.block.CatBathBlock;
import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.common.block.*;
import com.sweetrpg.catherder.common.lib.Constants;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = ModItems.ITEMS;

    public static final RegistryObject<CatBedBlock> CAT_BED = registerWithItem("cat_bed", CatBedBlock::new, (prop) -> prop.tab(ModItemGroups.CAT_BED));
//    public static final RegistryObject<CatBathBlock> CAT_BATH = registerWithItem("cat_bath", CatBathBlock::new);
    public static final RegistryObject<LitterBoxBlock> LITTER_BOX = registerWithItem("litter_box", LitterBoxBlock::new);
    public static final RegistryObject<FoodBowlBlock> FOOD_BOWL = registerWithItem("food_bowl", FoodBowlBlock::new);

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

         CatHerder.LOGGER.info("Items/Blocks were not registered for some reason... probably beacuse we are c...r..a..s.hing");
    }
}
