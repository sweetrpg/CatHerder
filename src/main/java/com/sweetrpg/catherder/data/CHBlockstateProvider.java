package com.sweetrpg.catherder.data;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.common.block.CatnipBlock;
import com.sweetrpg.catherder.common.block.CheeseWheelBlock;
import com.sweetrpg.catherder.common.lib.Constants;
import com.sweetrpg.catherder.common.registry.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class CHBlockstateProvider extends BlockStateProvider {

    // Applies texture to all faces and for the input face culls that direction
    private static final BiFunction<String, Direction, BiConsumer<Direction, ModelBuilder<BlockModelBuilder>.ElementBuilder.FaceBuilder>> cullFaceFactory = (texture, input) -> (d, b) -> b.texture(texture).cullface(d == input ? d : null);

    public CHBlockstateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, CatHerderAPI.MOD_ID, exFileHelper);
    }

    public ExistingFileHelper getExistingHelper() {
        return this.models().existingFileHelper;
    }

    @Override
    public String getName() {
        return "CatHerder Blockstates/Block Models";
    }

    @Override
    protected void registerStatesAndModels() {
//        catTree(ModBlocks.CAT_TREE);
//        createFromShape(ModBlocks.CAT_BOWL, new AABB(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D));
        stageBlock(ModBlocks.CATNIP_CROP.get(), CatnipBlock.CATNIP_AGE);
        wildCropBlock(ModBlocks.WILD_CATNIP.get());
//        makeSimple(ModBlocks.CARDBOARD_BOX);
//        mouseTrapBlock(ModBlocks.MOUSE_TRAP);
        cheeseWheelBlock(ModBlocks.CHEESE_WHEEL.get(), CheeseWheelBlock.SERVINGS);
    }

    private String blockName(Block block) {
        return block.getRegistryName().getPath();
    }

    public ResourceLocation resourceBlock(String path) {
        return new ResourceLocation(CatHerderAPI.MOD_ID, "block/" + path);
    }

    protected void wildCropBlock(Block block) {
        this.simpleBlock(block, models().cross(blockName(block), resourceBlock(blockName(block))));
    }

    protected void cheeseWheelBlock(Block block, IntegerProperty suffixProperty) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            int suffix = state.getValue(suffixProperty);
            String stageName = blockName(block) + "_servings" + suffix;
            return ConfiguredModel.builder().modelFile(this.models().getExistingFile(resourceBlock(stageName))).build();
        });
    }

//    protected void mouseTrapBlock(Supplier<? extends Block> block) {
//        BlockModelBuilder model = this.models()
//                                      .getBuilder(name(block))
////                                      .parent(this.models().getExistingFile(mcLoc(ModelProvider.BLOCK_FOLDER + "/block")))
//                                      .texture("triggered=false", extend(blockTexture(block), "_armed"))
//                                      .texture("triggered=true", extend(blockTexture(block), "_triggered"));
//
//        model.element()
//                .from(1.0f, 0.0f, 1.0f)
//                .to(15.0f, 4.0f, 15.0f);
////                .allFaces((d, f) -> f.cullface(d == Direction.DOWN ? d : null)
////                        .texture(d.getAxis().isHorizontal() ? "#side" : d == Direction.DOWN ? "#bottom" : "#top"));
//
//        horizontalBlock(block.get(), model);
////        this.simpleBlock(block.get(), model);
//    }

    protected void createFromShape(Supplier<? extends Block> blockIn, AABB bb) {
        BlockModelBuilder model = this.models()
                                      .getBuilder(name(blockIn))
                                      .parent(this.models().getExistingFile(mcLoc(ModelProvider.BLOCK_FOLDER + "/block")))
                                      .texture("particle", extend(blockTexture(blockIn), "_bottom"))
                                      .texture("bottom", extend(blockTexture(blockIn), "_bottom"))
                                      .texture("top", extend(blockTexture(blockIn), "_top"))
                                      .texture("side", extend(blockTexture(blockIn), "_side"));

        model.element()
             .from((float) bb.minX, (float) bb.minY, (float) bb.minZ)
             .to((float) bb.maxX, (float) bb.maxY, (float) bb.maxZ)
             .allFaces((d, f) -> f.cullface(d == Direction.DOWN ? d : null).texture(d.getAxis().isHorizontal() ? "#side" : d == Direction.DOWN ? "#bottom" : "#top"));

        this.simpleBlock(blockIn.get(), model);
    }

//    protected void catTree(Supplier<? extends Block> blockIn) {
//        BlockModelBuilder model = this.models()
//                                      .getBuilder(name(blockIn))
//                                      .parent(this.models().getExistingFile(mcLoc(ModelProvider.BLOCK_FOLDER + "/block")))
//                                      .texture("particle", blockTexture(Blocks.OAK_PLANKS.delegate))
//                                      .texture("bedding", blockTexture(Blocks.WHITE_WOOL.delegate))
//                                      .texture("casing", blockTexture(Blocks.OAK_PLANKS.delegate)).ao(false);
//
//        model.element().from(1.6F, 3.2F, 1.6F).to(14.4F, 6.4F, 14.4F).face(Direction.UP).texture("#bedding").end().face(Direction.NORTH).texture("#bedding");
//
//        model.element() //base
//             .from(0, 0, 0).to(16, 3.2F, 16).allFaces(cullFaceFactory.apply("#casing", Direction.DOWN));
//
//        model.element().from(11.2F, 3.2F, 0).to(16, 9.6F, 1.6F).allFaces(cullFaceFactory.apply("#casing", Direction.NORTH));
//
//        model.element().from(0, 3.2F, 0).to(4.8F, 9.6F, 1.6F).allFaces(cullFaceFactory.apply("#casing", Direction.NORTH));
//
//        model.element().from(14.4F, 3.2F, 0).to(16, 9.6F, 16).allFaces(cullFaceFactory.apply("#casing", Direction.EAST));
//
//        model.element().from(0, 3.2F, 14.4F).to(16, 9.6F, 16).allFaces(cullFaceFactory.apply("#casing", Direction.SOUTH));
//
//        model.element().from(0, 3.2F, 0).to(1.6F, 9.6F, 16).allFaces(cullFaceFactory.apply("#casing", Direction.WEST));
//
//        this.simpleBlock(blockIn.get(), model);
//    }

//    protected void catBath(Supplier<? extends Block> blockIn) {
//        BlockModelBuilder model = this.models()
//                .getBuilder(name(blockIn))
//                .parent(this.models().getExistingFile(mcLoc(ModelProvider.BLOCK_FOLDER + "/block")))
//                .texture("particle", blockTexture(Blocks.IRON_BLOCK.delegate))
//                .texture("water", extend(blockTexture(Blocks.WATER.delegate), "_still"))
//                .texture("side", blockTexture(Blocks.IRON_BLOCK.delegate))
//                .texture("bottom", blockTexture(Blocks.IRON_BLOCK.delegate))
//                .ao(false);
//
//        model.element()
//          .from(1, 0, 1)
//          .to(15, 6, 15)
//          .face(Direction.UP).texture("#water").tintindex(0);
//
//        model.element()
//          .from(1, 0, 1)
//          .to(15, 6, 15)
//          .face(Direction.DOWN).texture("#bottom");
//
//        model.element()
//          .from(0, 0, 0)
//          .to(16, 8, 1)
//          .allFaces(cullFaceFactory.apply("#side", Direction.NORTH));
//
//        model.element()
//          .from(15, 0, 0)
//          .to(16, 8, 16)
//          .allFaces(cullFaceFactory.apply("#side", Direction.EAST));
//
//        model.element()
//          .from(0, 0, 15)
//          .to(16, 8, 16)
//          .allFaces(cullFaceFactory.apply("#side", Direction.SOUTH));
//
//        model.element()
//          .from(0, 0, 0)
//          .to(1, 8, 16)
//          .allFaces(cullFaceFactory.apply("#side", Direction.WEST));
//
//        this.simpleBlock(blockIn.get(), model);
//    }

    public void stageBlock(Block block, IntegerProperty ageProperty, Property<?>... ignored) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            int ageSuffix = state.getValue(ageProperty);
            String stageName = blockName(block) + "_stage" + ageSuffix;
            return ConfiguredModel.builder().modelFile(models().cross(stageName, resourceBlock(stageName))).build();
        }, ignored);
    }

    public void customStageBlock(Block block, @Nullable ResourceLocation parent, String textureKey, IntegerProperty ageProperty, List<Integer> suffixes, Property<?>... ignored) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            int ageSuffix = state.getValue(ageProperty);
            String stageName = blockName(block) + "_stage";
            stageName += suffixes.isEmpty() ? ageSuffix : suffixes.get(Math.min(suffixes.size(), ageSuffix));
            if(parent == null) {
                return ConfiguredModel.builder().modelFile(models().cross(stageName, resourceBlock(stageName))).build();
            }
            return ConfiguredModel.builder().modelFile(models().singleTexture(stageName, parent, textureKey, resourceBlock(stageName))).build();
        }, ignored);
    }

    private String name(Supplier<? extends IForgeRegistryEntry<?>> block) {
        return block.get().getRegistryName().getPath();
    }

    private ResourceLocation blockTexture(Supplier<? extends Block> block) {
        ResourceLocation base = block.get().getRegistryName();
        return prextend(base, ModelProvider.BLOCK_FOLDER + "/");
    }

    public ModelFile cross(Supplier<? extends Block> block) {
        return this.models().cross(name(block), blockTexture(block));
    }

    protected void makeSimple(Supplier<? extends Block> blockIn) {
        this.simpleBlock(blockIn.get());
    }

    private ResourceLocation prextend(ResourceLocation rl, String prefix) {
        return new ResourceLocation(rl.getNamespace(), prefix + rl.getPath());
    }

    private ResourceLocation extend(ResourceLocation rl, String suffix) {
        return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
    }
}
