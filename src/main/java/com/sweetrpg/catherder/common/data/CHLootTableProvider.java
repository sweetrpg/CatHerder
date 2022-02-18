package com.sweetrpg.catherder.common.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.sweetrpg.catherder.common.registry.ModBlocks;
import com.sweetrpg.catherder.common.registry.ModEntityTypes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CHLootTableProvider extends LootTableProvider {

    public CHLootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    public String getName() {
        return "CatHerder LootTables";
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return ImmutableList.of(
                Pair.of(Blocks::new, LootContextParamSets.BLOCK),
                Pair.of(Entities::new, LootContextParamSets.ENTITY)
               );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationTracker) {}

    private static class Blocks extends BlockLoot {

        @Override
        protected void addTables() {
//            dropsSelf(CatBlocks.CAT_BATH);
            dropCatbed(ModBlocks.CAT_BED);
            dropsSelf(ModBlocks.FOOD_BOWL); // Drop with the name of the cat bowl
            dropsSelf(ModBlocks.LITTER_BOX);
            dropsSelf(ModBlocks.WILD_CATNIP);
            dropsSelf(ModBlocks.CATNIP_CROP);
            dropsSelf(ModBlocks.CARDBOARD_BOX);
        }

        private void dropCatbed(Supplier<? extends Block> block) {
            LootTable.Builder lootTableBuilder = LootTable.lootTable().withPool(applyExplosionCondition(block.get(),
                       LootPool.lootPool()
                         .setRolls(ConstantValue.exactly(1)))
                         .add(LootItem.lootTableItem(block.get())
                                 .apply(
                                         CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                                         .copy("casingId", "catherder.casingId")
                                         .copy("beddingId", "catherder.beddingId")
                                         .copy("ownerId", "catherder.ownerId")
                                         .copy("name", "catherder.name")
                                         .copy("ownerName", "catherder.ownerName")
                                 )));

            this.add(block.get(), lootTableBuilder);
        }

        private void dropsSelf(Supplier<? extends Block> block) {
            this.dropSelf(block.get());
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ModBlocks.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
        }
    }

    private static class Entities extends EntityLoot {

        @Override
        protected void addTables() {
            this.registerNoLoot(ModEntityTypes.CAT);
        }

        protected void registerNoLoot(Supplier<? extends EntityType<?>> type) {
           this.add(type.get(), LootTable.lootTable());
        }

        @Override
        protected Iterable<EntityType<?>> getKnownEntities() {
            return ModEntityTypes.ENTITIES.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
        }
    }
}
