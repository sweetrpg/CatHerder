package com.sweetrpg.catherder.data;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sweetrpg.catherder.common.registry.ModBlocks;
import com.sweetrpg.catherder.common.registry.ModEntityTypes;
import com.sweetrpg.catherder.common.registry.ModItems;
import com.sweetrpg.catherder.common.registry.ModTags;
import com.sweetrpg.catherder.common.util.CatTreeUtil;
import com.sweetrpg.catherder.common.util.PetDoorUtil;
import com.sweetrpg.catherder.common.util.Util;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Consumer;

public class CHAdvancementProvider extends AdvancementProvider {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final DataGenerator generator;

    public CHAdvancementProvider(DataGenerator generatorIn) {
        super(generatorIn);
        this.generator = generatorIn;
    }

    private static Path getPath(Path pathIn, Advancement advancementIn) {
        return pathIn.resolve("data/" + advancementIn.getId().getNamespace() + "/advancements/" + advancementIn.getId().getPath() + ".json");
    }

    @Override
    public String getName() {
        return "CatHerder Advancements";
    }

    @Override
    public void run(HashCache cache) {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<Advancement> consumer = (advancement) -> {
            if(!set.add(advancement.getId())) {
                throw new IllegalStateException("Duplicate advancement " + advancement.getId());
            }
            else {
                Path path1 = getPath(path, advancement);

                try {
                    DataProvider.save(GSON, cache, advancement.deconstruct().serializeToJson(), path1);
                }
                catch (IOException ioexception) {
                    LOGGER.error("Couldn't save advancement {}", path1, ioexception);
                }
            }
        };

        // training
        Advancement trainCat = Advancement.Builder.advancement()
//                .parent(Util.mcLoc("tame_animal"))
                .display(DisplayInfoBuilder.create().icon(ModItems.TRAINING_TREAT).frame(FrameType.TASK).translate("catherder.main.train_cat").background("stone.png").build())
                .addCriterion("tame_cat", TameAnimalTrigger.TriggerInstance.tamedAnimal(EntityPredicate.Builder.entity().of(ModEntityTypes.CAT.get()).build()))
                //.withCriterion("get_cat", ItemUseTrigger.TameAnimalTrigger.Instance.create(EntityPredicate.Builder.create().type(CatEntityTypes.CAT.get()).build()))
                .requirements(RequirementsStrategy.OR)
                .save(consumer, Util.getResourcePath("main/tame_cat"));
        // max level

        // litterbox
        Advancement litterbox = Advancement.Builder.advancement()
                .parent(trainCat)
                .display(DisplayInfoBuilder.create().icon(ModBlocks.LITTERBOX).frame(FrameType.TASK).translate("catherder.main.place_litterbox").build())
                .addCriterion("place_litterbox", PlacedBlockTrigger.TriggerInstance.placedBlock(ModBlocks.LITTERBOX.get()))
                .save(consumer, Util.getResourcePath("main/place_litterbox"));
        Advancement cleanLitterbox = Advancement.Builder.advancement()
                .parent(litterbox)
                .display(DisplayInfoBuilder.create().icon(ModItems.LITTER_SCOOP).frame(FrameType.TASK).translate("catherder.main.clean_litterbox").build())
                .addCriterion("clean_litterbox", UsingItemTrigger.TriggerInstance.lookingAt(EntityPredicate.Builder.entity().of(ModBlocks.LITTERBOX.getId()), ItemPredicate.Builder.item().of(ModItems.LITTER_SCOOP.get())))
                .save(consumer, Util.getResourcePath("main/clean_litterbox"));

        // cardboard box
        Advancement cardboardBox = Advancement.Builder.advancement()
                .parent(trainCat)
                .display(DisplayInfoBuilder.create().icon(ModBlocks.CARDBOARD_BOX).frame(FrameType.TASK).translate("catherder.main.place_cardboard_box").build())
                .addCriterion("place_cardboard_box", PlacedBlockTrigger.TriggerInstance.placedBlock(ModBlocks.CARDBOARD_BOX.get()))
                .save(consumer, Util.getResourcePath("main/place_cardboard_box"));

        // mousetrap
        Advancement mousetrap = Advancement.Builder.advancement()
                .parent(trainCat)
                .display(DisplayInfoBuilder.create().icon(ModBlocks.MOUSE_TRAP).frame(FrameType.TASK).translate("catherder.main.place_mousetrap").build())
                .addCriterion("place_mousetrap", PlacedBlockTrigger.TriggerInstance.placedBlock(ModBlocks.MOUSE_TRAP.get()))
                .save(consumer, Util.getResourcePath("main/place_mousetrap"));

        // catnip
        Advancement giveCatnip = Advancement.Builder.advancement()
                .parent(trainCat)
                .display(DisplayInfoBuilder.create().icon(ModItems.CATNIP).frame(FrameType.TASK).translate("catherder.main.give_catnip").build())
                .addCriterion("give_catnip", UsingItemTrigger.TriggerInstance.lookingAt(EntityPredicate.Builder.entity().of(ModEntityTypes.CAT.get()), ItemPredicate.Builder.item().of(ModItems.CATNIP.get())))
                .save(consumer, Util.getResourcePath("main/give_catnip"));

        // pet door
        Advancement petDoor = Advancement.Builder.advancement()
                .parent(trainCat)
                .display(DisplayInfoBuilder.create().icon(PetDoorUtil.createRandomDoor()).frame(FrameType.TASK).translate("catherder.main.craft_pet_door").build())
                .addCriterion("craft_pet_door", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.PET_DOOR.get()))
                .save(consumer, Util.getResourcePath("main/craft_pet_door"));

        // find wild catnip
        Advancement findCatnip = Advancement.Builder.advancement()
//                .parent(trainCat)
                .display(DisplayInfoBuilder.create().icon(ModBlocks.WILD_CATNIP).frame(FrameType.TASK).translate("catherder.main.find_catnip").build())
                .addCriterion("find_catnip",  EnterBlockTrigger.TriggerInstance.entersBlock(ModBlocks.WILD_CATNIP.get()))
                .save(consumer, Util.getResourcePath("main/find_catnip"));

        // place radio collar on cat
        Advancement radioCollar = Advancement.Builder.advancement()
                .parent(trainCat)
                .display(DisplayInfoBuilder.create().icon(ModItems.RADIO_COLLAR).frame(FrameType.TASK).translate("catherder.main.radio_collar").build())
                .addCriterion("radio_collar", UsingItemTrigger.TriggerInstance.lookingAt(EntityPredicate.Builder.entity().of(ModEntityTypes.CAT.get()), ItemPredicate.Builder.item().of(ModItems.RADIO_COLLAR.get())))
                .save(consumer, Util.getResourcePath("main/radio_collar"));

        // place sunglasses on cat
        Advancement sunglasses = Advancement.Builder.advancement()
                .parent(trainCat)
                .display(DisplayInfoBuilder.create().icon(ModItems.SUNGLASSES).frame(FrameType.TASK).translate("catherder.main.sunglasses").build())
                .addCriterion("sunglasses", UsingItemTrigger.TriggerInstance.lookingAt(EntityPredicate.Builder.entity().of(ModEntityTypes.CAT.get()), ItemPredicate.Builder.item().of(ModItems.SUNGLASSES.get())))
                .save(consumer, Util.getResourcePath("main/sunglasses"));

        // use change owner on cat
        Advancement changeOwner = Advancement.Builder.advancement()
                .parent(trainCat)
                .display(DisplayInfoBuilder.create().icon(ModItems.OWNER_CHANGE).frame(FrameType.TASK).translate("catherder.main.change_owner").build())
                .addCriterion("change_owner", UsingItemTrigger.TriggerInstance.lookingAt(EntityPredicate.Builder.entity().of(ModEntityTypes.CAT.get()), ItemPredicate.Builder.item().of(ModItems.OWNER_CHANGE.get())))
                .save(consumer, Util.getResourcePath("main/change_owner"));

        // Celestial Exploration
        // TODO

        // cat tree
        Advancement catTree = Advancement.Builder.advancement()
                .parent(trainCat)
                .display(DisplayInfoBuilder.create().icon(CatTreeUtil.createRandomTree()).frame(FrameType.TASK).translate("catherder.main.cat_tree").build())
                .addCriterion("cat_tree", PlacedBlockTrigger.TriggerInstance.placedBlock(ModBlocks.CAT_TREE.get()))
                .save(consumer, Util.getResourcePath("main/cat_tree"));

        // throw toy
        Advancement throwToy = Advancement.Builder.advancement()
                .parent(trainCat)
                .display(DisplayInfoBuilder.create().icon(ModItems.CAT_TOY).frame(FrameType.TASK).translate("catherder.main.toy").build())
                .addCriterion("play_with_cat", ItemPickedUpByEntityTrigger.TriggerInstance.itemPickedUpByEntity(EntityPredicate.Composite.ANY,
                                ItemPredicate.Builder.item().of(ModTags.TOYS),
                                EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(ModEntityTypes.CAT.get())).build())))
                .save(consumer, Util.getResourcePath("main/play_with_cat"));

        // Nermal
    }
}
