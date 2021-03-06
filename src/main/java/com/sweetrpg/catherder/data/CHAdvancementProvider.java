package com.sweetrpg.catherder.data;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sweetrpg.catherder.common.registry.ModEntityTypes;
import com.sweetrpg.catherder.common.registry.ModItems;
import com.sweetrpg.catherder.common.util.Util;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.TameAnimalTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
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
                catch(IOException ioexception) {
                    LOGGER.error("Couldn't save advancement {}", path1, ioexception);
                }
            }
        };

        // Disable advancements for now
        if(true) {return;}

        Advancement advancement = Advancement.Builder.advancement()
                                                     .display(DisplayInfoBuilder.create().icon(ModItems.TRAINING_TREAT).frame(FrameType.TASK).translate("cat.root").background("stone.png").noToast().noAnnouncement().build())
                                                     .addCriterion("tame_cat", TameAnimalTrigger.TriggerInstance.tamedAnimal(EntityPredicate.Builder.entity().of(ModEntityTypes.CAT.get()).build()))
                                                     //.withCriterion("get_cat", ItemUseTrigger.TameAnimalTrigger.Instance.create(EntityPredicate.Builder.create().type(CatEntityTypes.CAT.get()).build()))
                                                     .requirements(RequirementsStrategy.OR)
                                                     .save(consumer, Util.getResourcePath("default/tame_cat"));

        Advancement advancement1 = Advancement.Builder.advancement()
                                                      .parent(advancement)
                                                      .display(DisplayInfoBuilder.create().icon(Items.WOODEN_PICKAXE).frame(FrameType.TASK).translate("cat.level_talent").build())
                                                      .addCriterion("level_talent", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.COBBLESTONE))
                                                      .save(consumer, Util.getResourcePath("default/level_talent"));
        Advancement advancement2 = Advancement.Builder.advancement()
                                                      .parent(advancement1)
                                                      .display(DisplayInfoBuilder.create().icon(ModItems.CAPE).frame(FrameType.TASK).translate("cat.accessorise").build())
                                                      .addCriterion("accessorise", InventoryChangeTrigger.TriggerInstance.hasItems(Items.STONE_PICKAXE))
                                                      .save(consumer, Util.getResourcePath("default/accessorise"));

        Advancement advancement3 = Advancement.Builder.advancement()
                                                      .parent(advancement2)
                                                      .display(DisplayInfoBuilder.create().icon(ModItems.RADIO_COLLAR).frame(FrameType.TASK).translate("cat.radio_collar").build())
                                                      .addCriterion("iron", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
                                                      .save(consumer, Util.getResourcePath("default/radio_collar"));
    }
}
