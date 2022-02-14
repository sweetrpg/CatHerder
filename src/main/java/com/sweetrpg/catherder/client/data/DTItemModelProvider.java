package com.sweetrpg.catherder.client.data;

import com.sweetrpg.catherder.CatBlocks;
import com.sweetrpg.catherder.CatItems;
import com.sweetrpg.catherder.common.lib.Constants;
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
        return "CatHerder Item Models";
    }

    @Override
    protected void registerModels() {
//        handheld(CatItems.TINY_BONE);
//        handheld(CatItems.BIG_BONE);

        handheld(CatItems.TRAINING_TREAT);
        handheld(CatItems.SUPER_TREAT);
        handheld(CatItems.MASTER_TREAT);
        handheld(CatItems.DIRE_TREAT);
        handheld(CatItems.BREEDING_TREAT);
        handheld(CatItems.CAT_TOY);

        radar(CatItems.CREATIVE_RADAR);
        radar(CatItems.RADAR);

        generated(CatItems.CAPE);
//        generated(CatItems.CAPE_COLOURED);
        generated(CatItems.COLLAR_SHEARS);
        generated(CatItems.CREATIVE_COLLAR);
        generated(CatItems.CAT_CHARM);
//        generated(CatItems.GUARD_SUIT);
//        generated(CatItems.LEATHER_JACKET);
        generated(CatItems.MULTICOLOURED_COLLAR);
        generated(CatItems.OWNER_CHANGE);
        generated(CatItems.RADIO_COLLAR);
        generated(CatItems.SPOTTED_COLLAR);
        generated(CatItems.SUNGLASSES);
//        generated(CatItems.THROW_BONE);
//        generated(CatItems.THROW_BONE_WET);
//        generated(CatItems.THROW_STICK);
//        generated(CatItems.THROW_STICK_WET);
        generated(CatItems.TREAT_BAG);
//        generated(CatItems.WHISTLE);
        generated(CatItems.WOOL_COLLAR);

//        blockItem(CatBlocks.CAT_BATH);
        blockItem(CatBlocks.CAT_BED);
        blockItem(CatBlocks.FOOD_BOWL);
        blockItem(CatBlocks.LITTER_BOX);
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
