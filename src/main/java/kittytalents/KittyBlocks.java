package kittytalents;

import kittytalents.common.block.CatBathBlock;
import kittytalents.common.block.CatBedBlock;
import kittytalents.common.block.FoodBowlBlock;
import kittytalents.common.block.LitterBoxBlock;
import kittytalents.common.lib.Constants;
import kittytalents.common.util.Util;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.function.Supplier;

public class KittyBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = KittyItems.ITEMS;

    public static final RegistryObject<CatBedBlock> CAT_BED = registerWithItem("cat_bed", CatBedBlock::new, (prop) -> prop.tab(KittyItemGroups.CAT_BED));
    public static final RegistryObject<CatBathBlock> CAT_BATH = registerWithItem("cat_bath", CatBathBlock::new);
    public static final RegistryObject<LitterBoxBlock> LITTER_BOX = registerWithItem("litter_box", LitterBoxBlock::new);
    public static final RegistryObject<FoodBowlBlock> FOOD_BOWL = registerWithItem("food_bowl", FoodBowlBlock::new);

    private static Item.Properties createInitialProp() {
        return new Item.Properties().tab(KittyItemGroups.GENERAL);
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

        Util.acceptOrElse(KittyBlocks.CAT_BATH, (block) -> {
            blockColors.register((state, world, pos, tintIndex) -> {
                return world != null && pos != null ? BiomeColors.getAverageWaterColor(world, pos) : -1;
             }, block);
        }, KittyBlocks::logError);
    }

    public static void logError() {
        // Only try to register if blocks were successfully registered
        // Trying to avoid as reports like KittyTalents#242, where it says
        // KittyTalents crashed but is not the CAUSE of the crash

         KittyTalents2.LOGGER.info("Items/Blocks were not registered for some reason... probably beacuse we are c...r..a..s.hing");
    }
}
