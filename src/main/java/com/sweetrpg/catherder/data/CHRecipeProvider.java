package com.sweetrpg.catherder.data;

import com.google.gson.JsonObject;
import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.common.registry.ModBlocks;
import com.sweetrpg.catherder.common.registry.ModItems;
import com.sweetrpg.catherder.common.registry.ModRecipeSerializers;
import com.sweetrpg.catherder.common.util.Util;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.HashCache;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.nio.file.Path;
import java.util.function.Consumer;

public class CHRecipeProvider extends RecipeProvider {

    public CHRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "CatHerder Recipes";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        CatHerder.LOGGER.debug("Build crafting recipes: {}", consumer);

        //TODO
        ShapelessRecipeBuilder.shapeless(ModItems.SUPER_TREAT.get(), 5)
                .requires(ModItems.TRAINING_TREAT.get(), 5)
                .requires(Items.GOLDEN_APPLE, 1)
                .unlockedBy("has_golden_apple", has(Items.GOLDEN_APPLE))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItems.WILD_TREAT.get(), 1)
                .requires(ModItems.MASTER_TREAT.get(), 5)
                .requires(Blocks.END_STONE, 1)
                .unlockedBy("has_master_treat", has(ModItems.MASTER_TREAT.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItems.BREEDING_TREAT.get(), 2)
                .requires(ModItems.MASTER_TREAT.get(), 1)
                .requires(Items.COOKED_BEEF, 1)
                .requires(Items.COOKED_PORKCHOP, 1)
                .requires(Items.COOKED_CHICKEN, 1)
                .requires(Items.COOKED_COD, 1)
                .unlockedBy("has_cooked_porkchop", has(Items.COOKED_PORKCHOP))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItems.MASTER_TREAT.get(), 5)
                .requires(ModItems.SUPER_TREAT.get(), 5)
                .requires(Items.DIAMOND, 1)
                .unlockedBy("has_master_treat", has(ModItems.SUPER_TREAT.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.TRAINING_TREAT.get(), 1)
                .pattern("TFR")
                .pattern("XXX")
                .pattern("YYY")
                .define('T', Items.STRING)
                .define('F', ItemTags.FISHES)
                .define('R', ModItems.YARN.get())
                .define('X', Items.SUGAR)
                .define('Y', ModItems.CATNIP.get())
                .unlockedBy("has_catnip", has(ModItems.CATNIP.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.COLLAR_SHEARS.get(), 1)
                .pattern(" X ")
                .pattern("XYX")
                .pattern(" X ")
                .define('X', ModItems.YARN.get())
                .define('Y', Items.SHEARS)
                .unlockedBy("has_shears", has(Items.SHEARS))
                .unlockedBy("has_yarn", has(ModItems.YARN.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.CAT_BOWL.get(), 1)
//                .pattern("XXX")
                .pattern("FXW")
                .pattern("XXX")
                .define('X', Items.IRON_INGOT)
                .define('F', ItemTags.FISHES)
                .define('W', Items.WATER_BUCKET)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .unlockedBy("has_fishes", has(ItemTags.FISHES))
                .unlockedBy("has_water", has(Items.WATER_BUCKET))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.CAT_TOY.get(), 1)
                .pattern("SW")
                .pattern("WS")
                .define('W', ModItems.CATNIP.get())
                .define('S', Items.SUGAR)
                .unlockedBy("has_sugar", has(Items.SUGAR))
                .unlockedBy("has_catnip", has(ModItems.CATNIP.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.WOOL_COLLAR.get(), 1)
                .pattern("SSS")
                .pattern("S S")
                .pattern("SSS")
                .define('S', ItemTags.WOOL)
                .unlockedBy("has_stick", has(ItemTags.WOOL))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.TREAT_BAG.get(), 1)
                .pattern("LCL")
                .pattern("LLL")
                .define('L', Items.LEATHER)
                .define('C', ModItems.CAT_TOY.get())
                .unlockedBy("has_leather", has(Items.LEATHER))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.CAPE.get(), 1)
                .pattern("S S")
                .pattern("LWL")
                .pattern("WLW")
                .define('L', Items.LEATHER)
                .define('S', Items.STRING)
                .define('W', ItemTags.WOOL)
                .unlockedBy("has_leather", has(Items.LEATHER))
                .save(consumer);
//        ShapedRecipeBuilder.shaped(CatItems.CAPE_COLOURED.get(), 1).pattern("S S").pattern("LLL").pattern("LLL").define('L', Items.LEATHER).define('S', Items.STRING).unlockedBy("has_leather", has(Items.LEATHER)).save(consumer);
//        ShapedRecipeBuilder.shaped(CatItems.GUARD_SUIT.get(), 1).pattern("S S").pattern("BWB").pattern("BWB").define('S', Items.STRING).define('W', Blocks.WHITE_WOOL).define('B', Blocks.BLACK_WOOL).unlockedBy("has_string", has(Items.STRING)).save(consumer);
//        ShapedRecipeBuilder.shaped(CatItems.LEATHER_JACKET.get(), 1).pattern("L L").pattern("LWL").pattern("LWL").define('L', Items.LEATHER).define('W', ItemTags.WOOL).unlockedBy("has_leather", has(Items.LEATHER)).save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.SPOTTED_COLLAR.get(), 1)
                .pattern("BWB")
                .pattern("WCW")
                .pattern("BSB")
                .define('C', ModItems.WOOL_COLLAR.get())
                .define('B', Items.BLACK_DYE)
                .define('W', Items.WHITE_DYE)
                .define('S', ItemTags.WOOL)
                .unlockedBy("has_wool_collar", has(ModItems.WOOL_COLLAR.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.SPOTTED_COLLAR.get(), 1)
                .pattern("WBW")
                .pattern("BCB")
                .pattern("WSW")
                .define('C', ModItems.WOOL_COLLAR.get())
                .define('B', Items.BLACK_DYE)
                .define('W', Items.WHITE_DYE)
                .define('S', ItemTags.WOOL)
                .unlockedBy("has_wool_collar", has(ModItems.WOOL_COLLAR.get()))
                .save(consumer, Util.getResource("spotted_collar_alt"));
        ShapelessRecipeBuilder.shapeless(ModItems.MULTICOLORED_COLLAR.get(), 1)
                .requires(ModItems.WOOL_COLLAR.get())
                .requires(Items.STRING)
                .requires(Items.BLUE_DYE)
                .requires(Items.LIME_DYE)
                .requires(Items.YELLOW_DYE)
                .requires(Items.ORANGE_DYE)
                .requires(Items.RED_DYE)
                .requires(Items.PURPLE_DYE)
                .unlockedBy("has_wool_collar", has(ModItems.WOOL_COLLAR.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.SUNGLASSES.get(), 1)
                .pattern(" s ")
                .pattern("S S")
                .pattern("GSG")
                .define('s', Items.STRING)
                .define('S', Items.STICK)
                .define('G', Blocks.GLASS_PANE)
                .unlockedBy("has_string", has(Items.STRING))
                .unlockedBy("has_stick", has(Items.STICK))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.CAT_SMALLERER.get(), 1)
                .pattern("WM")
                .define('W', Items.POTION)
                .define('M', Items.BROWN_MUSHROOM)
                .unlockedBy("has_potion", has(Items.POTION))
                .unlockedBy("has_mushroom", has(Items.BROWN_MUSHROOM))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.CAT_BIGGERER.get(), 1)
                .pattern("MC")
                .define('M', Items.RED_MUSHROOM)
                .define('C', Items.CAKE)
                .unlockedBy("has_mushroom", has(Items.RED_MUSHROOM))
                .unlockedBy("has_cake", has(Items.CAKE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModItems.RADIO_COLLAR.get(), 1)
                .pattern("XX")
                .pattern("XY")
                .define('X', Items.IRON_INGOT)
                .define('Y', Items.REDSTONE)
                .unlockedBy("has_redstone", has(Items.REDSTONE))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(ModItems.RADAR.get(), 1)
                .requires(Items.MAP, 1)
                .requires(Items.REDSTONE, 1)
                .requires(ModItems.RADIO_COLLAR.get(), 1)
                .unlockedBy("has_redstone", has(Items.REDSTONE))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.LITTERBOX.get(), 1)
                .pattern("ISI")
                .pattern("III")
                .define('I', Items.IRON_INGOT)
                .define('S', Items.SAND)
                .unlockedBy("has_sand", has(Items.SAND))
                .unlockedBy("has_iron", has(Items.IRON_INGOT))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.LITTER_SCOOP.get(), 1)
                .pattern("IWI")
                .pattern("III")
                .pattern(" I ")
                .define('I', Items.COPPER_INGOT)
                .define('W', Items.IRON_BARS)
                .unlockedBy("has_copper", has(Items.SAND))
                .unlockedBy("has_iron_bars", has(Items.IRON_BARS))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.CARDBOARD_BOX.get(), 1)
                .pattern("CCC")
                .pattern("C C")
                .pattern("CCC")
                .define('C', ModItems.CARDBOARD.get())
                .unlockedBy("has_cardboard", has(ModItems.CARDBOARD.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.CARDBOARD.get(), 9)
                .pattern("PPP")
                .pattern("PPP")
                .define('P', Items.PAPER)
                .unlockedBy("has_paper", has(Items.PAPER))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.YARN.get(), 1)
                .pattern("WW ")
                .pattern("WW ")
                .pattern("  W")
                .define('W', ItemTags.WOOL)
                .unlockedBy("has_wool", has(ItemTags.WOOL))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.MOUSE_TRAP.get(), 1)
                .pattern("CIT")
                .pattern("PPP")
                .define('C', ModItems.CHEESE_WEDGE.get())
                .define('I', Items.IRON_INGOT)
                .define('T', Items.TRIPWIRE_HOOK)
                .define('P', ItemTags.PLANKS)
                .unlockedBy("has_cheese", has(ModItems.CHEESE_WEDGE.get()))
                .unlockedBy("has_iron", has(Items.IRON_INGOT))
                .unlockedBy("has_tripwire_hook", has(Items.TRIPWIRE_HOOK))
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.CHEESE_WHEEL.get(), 1)
                .pattern("MSM")
                .pattern(" B ")
                .define('M', Items.MILK_BUCKET)
                .define('S', Items.SLIME_BALL)
                .define('B', Items.BOWL)
                .unlockedBy("has_milk", has(Items.MILK_BUCKET))
                .unlockedBy("has_slime", has(Items.SLIME_BALL))
                .unlockedBy("has_bowl", has(Items.BOWL))
                .save(consumer);
//        ShapedRecipeBuilder.shaped(ModItems.LASAGNA.get(), 1)
//                .pattern("PPP")
//                .pattern("SCM")
//                .pattern("PPP")
//                .define('P', PASTA.get())
//                .define('S', TOMATO_SAUCE.get())
//                .define('C', ModItems.CHEESE_WEDGE.get())
//                .define('M', Items.BEEF)
//                           .unlockedBy("has_pasta", has(PASTA.get()))
//                           .unlockedBy("has_sauce", has(SAUCE.get()))
//                           .unlockedBy("has_cheese", has(ModItems.CHEESE_WEDGE.get()))
//                           .unlockedBy("has_meat", has(Items.BEEF))
//                           .save(consumer);

        // loop over wool colors and create a recipe for each
//        List<Tuple<RegistryObject<CatTreeBlock>, Item>> itemWoolTreeTuple = Arrays.asList(
//                new Tuple<>(ModBlocks.WHITE_CAT_TREE, Items.WHITE_WOOL),
//                new Tuple<>(ModBlocks.BLACK_CAT_TREE, Items.BLACK_WOOL),
//                new Tuple<>(ModBlocks.ORANGE_CAT_TREE, Items.ORANGE_WOOL),
//                new Tuple<>(ModBlocks.YELLOW_CAT_TREE, Items.YELLOW_WOOL),
//                new Tuple<>(ModBlocks.GREEN_CAT_TREE, Items.GREEN_WOOL),
//                new Tuple<>(ModBlocks.BLUE_CAT_TREE, Items.BLUE_WOOL),
//                new Tuple<>(ModBlocks.PURPLE_CAT_TREE, Items.PURPLE_WOOL),
//                new Tuple<>(ModBlocks.RED_CAT_TREE, Items.RED_WOOL),
//                new Tuple<>(ModBlocks.CYAN_CAT_TREE, Items.CYAN_WOOL),
//                new Tuple<>(ModBlocks.LIME_CAT_TREE, Items.LIME_WOOL),
//                new Tuple<>(ModBlocks.GREY_CAT_TREE, Items.GRAY_WOOL),
//                new Tuple<>(ModBlocks.LIGHT_GREY_CAT_TREE, Items.LIGHT_GRAY_WOOL),
//                new Tuple<>(ModBlocks.LIGHT_BLUE_CAT_TREE, Items.LIGHT_BLUE_WOOL),
//                new Tuple<>(ModBlocks.PINK_CAT_TREE, Items.PINK_WOOL),
//                new Tuple<>(ModBlocks.BROWN_CAT_TREE, Items.BROWN_WOOL),
//                new Tuple<>(ModBlocks.MAGENTA_CAT_TREE, Items.MAGENTA_WOOL)
//                );
//        for(Tuple<RegistryObject<CatTreeBlock>, Item> tElement : itemWoolTreeTuple) {
//            ShapedRecipeBuilder.shaped(tElement.getA().get(), 1)
//                    .pattern("sWs")
//                    .pattern("SF ")
//                    .pattern("sss")
//                    .define('s', ItemTags.SLABS)
//                    .define('S', Items.STRING)
//                    .define('F', ItemTags.WOODEN_FENCES)
//                    .define('W', tElement.getB())
//                    .unlockedBy("has_slabs", has(ItemTags.SLABS))
//                    .unlockedBy("has_string", has(Items.STRING))
//                    .unlockedBy("has_fences", has(ItemTags.WOODEN_FENCES))
//                    .unlockedBy("has_wool", has(ItemTags.WOOL))
//                    .save(consumer);
//        }
        SpecialRecipeBuilder.special(ModRecipeSerializers.CAT_TREE.get())
                            .save(consumer, Util.getResourcePath("cat_tree"));
        SpecialRecipeBuilder.special(ModRecipeSerializers.CAT_TREE_DYED.get())
                            .save(consumer, Util.getResourcePath("cat_tree_dyed"));

        // dyeable cat trees
//        List<Tuple<RegistryObject<CatTreeBlock>, Item>> itemDyeTreeTuple = Arrays.asList(
//                new Tuple<>(ModBlocks.WHITE_CAT_TREE, Items.WHITE_DYE),
//                new Tuple<>(ModBlocks.BLACK_CAT_TREE, Items.BLACK_DYE),
//                new Tuple<>(ModBlocks.ORANGE_CAT_TREE, Items.ORANGE_DYE),
//                new Tuple<>(ModBlocks.YELLOW_CAT_TREE, Items.YELLOW_DYE),
//                new Tuple<>(ModBlocks.GREEN_CAT_TREE, Items.GREEN_DYE),
//                new Tuple<>(ModBlocks.BLUE_CAT_TREE, Items.BLUE_DYE),
//                new Tuple<>(ModBlocks.PURPLE_CAT_TREE, Items.PURPLE_DYE),
//                new Tuple<>(ModBlocks.RED_CAT_TREE, Items.RED_DYE),
//                new Tuple<>(ModBlocks.CYAN_CAT_TREE, Items.CYAN_DYE),
//                new Tuple<>(ModBlocks.LIME_CAT_TREE, Items.LIME_DYE),
//                new Tuple<>(ModBlocks.GREY_CAT_TREE, Items.GRAY_DYE),
//                new Tuple<>(ModBlocks.LIGHT_GREY_CAT_TREE, Items.LIGHT_GRAY_DYE),
//                new Tuple<>(ModBlocks.LIGHT_BLUE_CAT_TREE, Items.LIGHT_BLUE_DYE),
//                new Tuple<>(ModBlocks.PINK_CAT_TREE, Items.PINK_DYE),
//                new Tuple<>(ModBlocks.BROWN_CAT_TREE, Items.BROWN_DYE),
//                new Tuple<>(ModBlocks.MAGENTA_CAT_TREE, Items.MAGENTA_DYE)
//        );
//        for(Tuple<RegistryObject<CatTreeBlock>, Item> tElement : itemDyeTreeTuple) {
//            ShapelessRecipeBuilder.shapeless(tElement.getA().get(), 1)
//                    .requires(ModTags.CAT_TREES)
//                    .requires(tElement.getB())
//                    .unlockedBy("has_cat_tree", has(tElement.getA().get()))
//                    .unlockedBy("has_dye", has(Items.BLUE_DYE))
//                    .save(consumer);
//        }
//
//        List<Tuple<RegistryObject<Block>, Item>> itemWoodDoorTuple = Arrays.asList(
//                new Tuple<>(ModBlocks.OAK_PET_DOOR, Items.OAK_PLANKS),
//                new Tuple<>(ModBlocks.DARK_OAK_PET_DOOR, Items.DARK_OAK_PLANKS),
//                new Tuple<>(ModBlocks.BIRCH_PET_DOOR, Items.BIRCH_PLANKS),
//                new Tuple<>(ModBlocks.ACACIA_PET_DOOR, Items.ACACIA_PLANKS),
//                new Tuple<>(ModBlocks.SPRUCE_PET_DOOR, Items.SPRUCE_PLANKS),
//                new Tuple<>(ModBlocks.JUNGLE_PET_DOOR, Items.JUNGLE_PLANKS),
//                new Tuple<>(ModBlocks.CRIMSON_PET_DOOR, Items.CRIMSON_STEM),
//                new Tuple<>(ModBlocks.WARPED_PET_DOOR, Items.WARPED_STEM)
//                );
//        for(Tuple<RegistryObject<Block>, Item> tElement : itemWoodDoorTuple) {
//            ShapedRecipeBuilder.shaped(tElement.getA().get(), 1)
//                    .pattern("WWW")
//                    .pattern("WDW")
//                    .pattern("WWW")
//                    .define('W', tElement.getB())
//                    .define('D', ItemTags.WOODEN_DOORS)
//                    .unlockedBy("has_planks", has(ItemTags.PLANKS))
//                    .unlockedBy("has_door", has(ItemTags.WOODEN_DOORS))
//                    .save(consumer);
//        }
        SpecialRecipeBuilder.special(ModRecipeSerializers.PET_DOOR.get())
                .save(consumer, Util.getResourcePath("pet_door"));

    }

    @Override
    protected void saveAdvancement(HashCache cache, JsonObject advancementJson, Path pathIn) {
        // NOOP - We don't replace any of the advancement things yet...
    }
}
