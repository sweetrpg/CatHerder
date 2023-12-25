package com.sweetrpg.catherder.data;

import com.google.common.collect.Maps;
import com.sweetrpg.catherder.common.registry.ModItems;
import com.sweetrpg.catherder.common.util.Util;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class CHAdvancements implements Consumer<Consumer<Advancement>> {

    @Override
    public void accept(Consumer<Advancement> register) {
        Advancement advancement = Advancement.Builder.advancement().display(ModItems.TRAINING_TREAT.get(), new TranslatableComponent("advancements.cat.root.title"), new TranslatableComponent("advancements.cat.root.description"), new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"), FrameType.TASK, false, false, false).addCriterion("crafting_table", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.CRAFTING_TABLE)).save(register, Util.getResourcePath("cat/find_cat"));
        Advancement advancement1 = Advancement.Builder.advancement().parent(advancement).display(Items.WOODEN_PICKAXE, new TranslatableComponent("advancements.cat.mine_stone.title"), new TranslatableComponent("advancements.cat.mine_stone.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).addCriterion("get_stone", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.COBBLESTONE)).save(register, Util.getResourcePath("cat/level_talent"));
        Advancement advancement2 = Advancement.Builder.advancement().parent(advancement1).display(ModItems.CAPE.get(), new TranslatableComponent("advancements.cat.upgrade_tools.title"), new TranslatableComponent("advancements.cat.upgrade_tools.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).addCriterion("stone_pickaxe", InventoryChangeTrigger.TriggerInstance.hasItems(Items.STONE_PICKAXE)).save(register, Util.getResourcePath("cat/accessorise"));
        Advancement advancement3 = Advancement.Builder.advancement().parent(advancement2).display(ModItems.RADIO_COLLAR.get(), new TranslatableComponent("advancements.cat.smelt_iron.title"), new TranslatableComponent("advancements.cat.smelt_iron.description"), (ResourceLocation)null, FrameType.TASK, true, true, false).addCriterion("iron", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT)).save(register, Util.getResourcePath("cat/radio_collar"));
    }

    public static class Builder {
        private ResourceLocation parentId;
        private Advancement parent;
        private DisplayInfo display;
        private AdvancementRewards rewards = AdvancementRewards.EMPTY;
        private Map<String, Criterion> criteria = Maps.newLinkedHashMap();
        private String[][] requirements;
        private RequirementsStrategy requirementsStrategy = RequirementsStrategy.AND;

        private Builder(@Nullable ResourceLocation parentIdIn, @Nullable DisplayInfo displayIn, AdvancementRewards rewardsIn, Map<String, Criterion> criteriaIn, String[][] requirementsIn) {
           this.parentId = parentIdIn;
           this.display = displayIn;
           this.rewards = rewardsIn;
           this.criteria = criteriaIn;
           this.requirements = requirementsIn;
        }

        private Builder() {
        }

        public static CHAdvancements.Builder builder() {
           return new CHAdvancements.Builder();
        }

        public CHAdvancements.Builder withParent(Advancement parentIn) {
           this.parent = parentIn;
           return this;
        }

        public CHAdvancements.Builder withParentId(ResourceLocation parentIdIn) {
           this.parentId = parentIdIn;
           return this;
        }

        public CHAdvancements.Builder withDisplay(ItemStack stack, Component title, Component description, @Nullable ResourceLocation background, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
           return this.withDisplay(new DisplayInfo(stack, title, description, background, frame, showToast, announceToChat, hidden));
        }

        public CHAdvancements.Builder withDisplay(ItemLike itemIn, Component title, Component description, @Nullable ResourceLocation background, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
           return this.withDisplay(new DisplayInfo(new ItemStack(itemIn.asItem()), title, description, background, frame, showToast, announceToChat, hidden));
        }

        public CHAdvancements.Builder withDisplay(DisplayInfo displayIn) {
           this.display = displayIn;
           return this;
        }

        public CHAdvancements.Builder withRewards(AdvancementRewards.Builder rewardsBuilder) {
           return this.withRewards(rewardsBuilder.build());
        }

        public CHAdvancements.Builder withRewards(AdvancementRewards rewards) {
           this.rewards = rewards;
           return this;
        }

        /**
         * Adds a criterion to the list of criteria
         */
        public CHAdvancements.Builder withCriterion(String key, CriterionTriggerInstance criterionIn) {
           return this.withCriterion(key, new Criterion(criterionIn));
        }

        /**
         * Adds a criterion to the list of criteria
         */
        public CHAdvancements.Builder withCriterion(String key, Criterion criterionIn) {
           if (this.criteria.containsKey(key)) {
              throw new IllegalArgumentException("Duplicate criterion " + key);
           } else {
              this.criteria.put(key, criterionIn);
              return this;
           }
        }

        public CHAdvancements.Builder withRequirementsStrategy(RequirementsStrategy strategy) {
           this.requirementsStrategy = strategy;
           return this;
        }

        /**
         * Tries to resolve the parent of this advancement, if possible. Returns true on success.
         */
        public boolean resolveParent(Function<ResourceLocation, Advancement> lookup) {
           if (this.parentId == null) {
              return true;
           } else {
              if (this.parent == null) {
                 this.parent = lookup.apply(this.parentId);
              }

              return this.parent != null;
           }
        }

        public Advancement build(ResourceLocation id) {
           if (!this.resolveParent((parentID) -> {
              return null;
           })) {
              throw new IllegalStateException("Tried to build incomplete advancement!");
           } else {
              if (this.requirements == null) {
                 this.requirements = this.requirementsStrategy.createRequirements(this.criteria.keySet());
              }

              return new Advancement(id, this.parent, this.display, this.rewards, this.criteria, this.requirements);
           }
        }

        public Advancement register(Consumer<Advancement> consumer, String id) {
            Advancement advancement = this.build(new ResourceLocation(id));
            consumer.accept(advancement);
            return advancement;
         }
    }
}
