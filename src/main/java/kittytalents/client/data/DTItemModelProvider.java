package kittytalents.client.data;

import kittytalents.KittyBlocks;
import kittytalents.KittyItems;
import kittytalents.common.lib.Constants;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder.Perspective;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class DTItemModelProvider extends ItemModelProvider {

    public DTItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "KittyTalents Item Models";
    }

    @Override
    protected void registerModels() {
        handheld(KittyItems.TINY_BONE);
        handheld(KittyItems.BIG_BONE);

        handheld(KittyItems.TRAINING_TREAT);
        handheld(KittyItems.SUPER_TREAT);
        handheld(KittyItems.MASTER_TREAT);
        handheld(KittyItems.DIRE_TREAT);
        handheld(KittyItems.BREEDING_BONE);
        handheld(KittyItems.CHEW_STICK);

        radar(KittyItems.CREATIVE_RADAR);
        radar(KittyItems.RADAR);

        generated(KittyItems.CAPE);
        generated(KittyItems.CAPE_COLOURED);
        generated(KittyItems.COLLAR_SHEARS);
        generated(KittyItems.CREATIVE_COLLAR);
        generated(KittyItems.KITTY_CHARM);
        generated(KittyItems.GUARD_SUIT);
        generated(KittyItems.LEATHER_JACKET);
        generated(KittyItems.MULTICOLOURED_COLLAR);
        generated(KittyItems.OWNER_CHANGE);
        generated(KittyItems.RADIO_COLLAR);
        generated(KittyItems.SPOTTED_COLLAR);
        generated(KittyItems.SUNGLASSES);
        generated(KittyItems.THROW_BONE);
        generated(KittyItems.THROW_BONE_WET);
        generated(KittyItems.THROW_STICK);
        generated(KittyItems.THROW_STICK_WET);
        generated(KittyItems.TREAT_BAG);
        generated(KittyItems.WHISTLE);
        generated(KittyItems.WOOL_COLLAR);

        blockItem(KittyBlocks.CAT_BATH);
        blockItem(KittyBlocks.CAT_BED);
        blockItem(KittyBlocks.FOOD_BOWL);
    }

    private ResourceLocation itemTexture(Supplier<? extends ItemLike> item) {
        return modLoc(ModelProvider.ITEM_FOLDER + "/" + name(item));
    }

    private String name(Supplier<? extends ItemLike> item) {
        return item.get().asItem().getRegistryName().getPath();
    }

    private ItemModelBuilder blockItem(Supplier<? extends Block> block) {
        return blockItem(block, "");
    }

    private ItemModelBuilder radar(Supplier<? extends ItemLike> item) {
        return radar(item, itemTexture(item));
    }

    private ItemModelBuilder radar(Supplier<? extends ItemLike> item, ResourceLocation texture) {
        ItemModelBuilder builder = generated(item, texture);
        builder.transforms().transform(Perspective.THIRDPERSON_RIGHT).rotation(0, 0, 55F).translation(0, 4F, 0.5F).scale(0.85F);
        builder.transforms().transform(Perspective.THIRDPERSON_LEFT).rotation(0, 0, -55F).translation(0, 4F, 0.5F).scale(0.85F);
        builder.transforms().transform(Perspective.FIRSTPERSON_RIGHT).translation(-3.13F, 3.2F, 1.13F).scale(0.8F);
        builder.transforms().transform(Perspective.FIRSTPERSON_LEFT).translation(-3.13F, 3.2F, 1.13F).scale(0.8F);
        return builder;
    }

    private ItemModelBuilder generated(Supplier<? extends ItemLike> item) {
        return generated(item, itemTexture(item));
    }

    private ItemModelBuilder generated(Supplier<? extends ItemLike> item, ResourceLocation texture) {
        return getBuilder(name(item)).parent(new UncheckedModelFile(ModelProvider.ITEM_FOLDER + "/generated")).texture("layer0", texture);
    }

    private ItemModelBuilder handheld(Supplier<? extends ItemLike> item) {
        return handheld(item, itemTexture(item));
    }

    private ItemModelBuilder handheld(Supplier<? extends ItemLike> item, ResourceLocation texture) {
        return getBuilder(name(item)).parent(new UncheckedModelFile(ModelProvider.ITEM_FOLDER + "/handheld")).texture("layer0", texture);
    }

    private ItemModelBuilder blockItem(Supplier<? extends Block> block, String suffix) {
        return withExistingParent(name(block), modLoc(ModelProvider.BLOCK_FOLDER + "/" + name(block) + suffix));
    }
}
