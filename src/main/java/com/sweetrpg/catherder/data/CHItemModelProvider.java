package com.sweetrpg.catherder.data;

import com.sweetrpg.catherder.common.registry.ModBlocks;
import com.sweetrpg.catherder.common.registry.ModItems;
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

public class CHItemModelProvider extends ItemModelProvider {

    public CHItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "CatHerder Item Models";
    }

    @Override
    protected void registerModels() {

        handheld(ModItems.CAT_SMALLERER);
        handheld(ModItems.CAT_BIGGERER);

        handheld(ModItems.TRAINING_TREAT);
        handheld(ModItems.SUPER_TREAT);
        handheld(ModItems.MASTER_TREAT);
        handheld(ModItems.WILD_TREAT);
        handheld(ModItems.BREEDING_TREAT);
        handheld(ModItems.CAT_TOY);

        radar(ModItems.CREATIVE_RADAR);
        radar(ModItems.RADAR);

        generated(ModItems.CAPE);
//        generated(CatItems.CAPE_COLOURED);
        generated(ModItems.COLLAR_SHEARS);
        generated(ModItems.CREATIVE_COLLAR);
        generated(ModItems.CAT_CHARM);
//        generated(CatItems.GUARD_SUIT);
//        generated(CatItems.LEATHER_JACKET);
        generated(ModItems.MULTICOLOURED_COLLAR);
        generated(ModItems.OWNER_CHANGE);
        generated(ModItems.RADIO_COLLAR);
        generated(ModItems.SPOTTED_COLLAR);
        generated(ModItems.SUNGLASSES);
//        generated(CatItems.THROW_BONE);
//        generated(CatItems.THROW_BONE_WET);
//        generated(CatItems.THROW_STICK);
//        generated(CatItems.THROW_STICK_WET);
        generated(ModItems.TREAT_BAG);
//        generated(CatItems.WHISTLE);
        generated(ModItems.WOOL_COLLAR);
        generated(ModItems.CATNIP);
        generated(ModItems.CATNIP_SEEDS);
        generated(ModItems.CARDBOARD);
        generated(ModItems.YARN);
//        generated(ModItems.RODENT);
        generated(ModItems.CHEESE_WEDGE);
//        generated(ModItems.LASAGNA);

//        blockItem(CatBlocks.CAT_BATH);
        blockItem(ModBlocks.CAT_BED);
        blockItem(ModBlocks.FOOD_BOWL);
        blockItem(ModBlocks.LITTER_BOX);
        blockItem(ModBlocks.WILD_CATNIP);
        blockItem(ModBlocks.CARDBOARD_BOX);
        blockItem(ModBlocks.CHEESE_WHEEL);
        blockItem(ModBlocks.MOUSE_TRAP);
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
