package kittytalents;

import kittytalents.common.block.tileentity.DogBedTileEntity;
import kittytalents.common.block.tileentity.FoodBowlTileEntity;
import kittytalents.common.lib.Constants;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class KittyTileEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Constants.MOD_ID);

    public static final RegistryObject<BlockEntityType<DogBedTileEntity>> CAT_BED = register("cat_bed", DogBedTileEntity::new, KittyBlocks.CAT_BED);
    public static final RegistryObject<BlockEntityType<FoodBowlTileEntity>> FOOD_BOWL = register("food_bowl", FoodBowlTileEntity::new, KittyBlocks.FOOD_BOWL);

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(final String name, final BlockEntityType.BlockEntitySupplier<T> sup, Supplier<? extends Block> validBlock) {
        return register(name, () -> BlockEntityType.Builder.of(sup, validBlock.get()).build(null));
    }

    private static <T extends BlockEntityType<?>> RegistryObject<T> register(final String name, final Supplier<T> sup) {
        return TILE_ENTITIES.register(name, sup);
    }
}
