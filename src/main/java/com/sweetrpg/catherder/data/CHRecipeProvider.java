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

        // treats
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
        ShapedRecipeBuilder.shaped(ModItems.TREAT_BAG.get(), 1)
                .pattern("LCL")
                .pattern("LLL")
                .define('L', Items.LEATHER)
                .define('C', ModItems.CAT_TOY.get())
                .unlockedBy("has_leather", has(Items.LEATHER))
                .save(consumer);

        // modifiers
        ShapedRecipeBuilder.shaped(ModItems.CAT_SHEARS.get(), 1)
                .pattern(" X ")
                .pattern("XYX")
                .pattern(" X ")
                .define('X', ModItems.YARN.get())
                .define('Y', Items.SHEARS)
                .unlockedBy("has_shears", has(Items.SHEARS))
                .unlockedBy("has_yarn", has(ModItems.YARN.get()))
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

        // essentials
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
        SpecialRecipeBuilder.special(ModRecipeSerializers.CAT_TREE.get())
                .save(consumer, Util.getResourcePath("cat_tree"));
        SpecialRecipeBuilder.special(ModRecipeSerializers.CAT_TREE_DYED.get())
                .save(consumer, Util.getResourcePath("cat_tree_dyed"));

        // accessories
        ShapedRecipeBuilder.shaped(ModItems.WOOL_COLLAR.get(), 1)
                .pattern("SSS")
                .pattern("S S")
                .pattern("SSS")
                .define('S', ItemTags.WOOL)
                .unlockedBy("has_stick", has(ItemTags.WOOL))
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
        ShapelessRecipeBuilder.shapeless(ModItems.RADIO_COLLAR.get(), 1)
                .requires(ModItems.WOOL_COLLAR.get())
                .requires(Items.REDSTONE)
                .unlockedBy("has_redstone", has(Items.REDSTONE))
                .unlockedBy("has_wool_collar", has(ModItems.WOOL_COLLAR.get()))
                .save(consumer);

        // toys
        ShapedRecipeBuilder.shaped(ModItems.CAT_TOY.get(), 1)
                .pattern("SW")
                .pattern("WS")
                .define('W', ModItems.CATNIP.get())
                .define('S', Items.SUGAR)
                .unlockedBy("has_sugar", has(Items.SUGAR))
                .unlockedBy("has_catnip", has(ModItems.CATNIP.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModBlocks.CARDBOARD_BOX.get(), 1)
                .pattern("CCC")
                .pattern("C C")
                .pattern("CCC")
                .define('C', ModItems.CARDBOARD.get())
                .unlockedBy("has_cardboard", has(ModItems.CARDBOARD.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.YARN.get(), 1)
                .pattern("WW ")
                .pattern("WW ")
                .pattern("  W")
                .define('W', ItemTags.WOOL)
                .unlockedBy("has_wool", has(ItemTags.WOOL))
                .save(consumer);

// misc
        ShapelessRecipeBuilder.shapeless(ModItems.RADAR.get(), 1)
                .requires(Items.MAP, 1)
                .requires(Items.REDSTONE, 1)
                .requires(ModItems.RADIO_COLLAR.get(), 1)
                .unlockedBy("has_redstone", has(Items.REDSTONE))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ModItems.CARDBOARD.get(), 9)
                .pattern("PPP")
                .pattern("PPP")
                .define('P', Items.PAPER)
                .unlockedBy("has_paper", has(Items.PAPER))
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
        SpecialRecipeBuilder.special(ModRecipeSerializers.PET_DOOR.get())
                .save(consumer, Util.getResourcePath("pet_door"));

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
    }

    @Override
    protected void saveAdvancement(HashCache cache, JsonObject advancementJson, Path pathIn) {
        // NOOP - We don't replace any of the advancement things yet...
    }
}
