package com.sweetrpg.catherder.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.sweetrpg.catherder.common.registry.ModBlocks;
import com.sweetrpg.catherder.common.registry.ModEntityTypes;
import com.sweetrpg.catherder.common.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.models.blockstates.Condition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

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
        return ImmutableList.of(Pair.of(Blocks::new, LootContextParamSets.BLOCK), Pair.of(Entities::new, LootContextParamSets.ENTITY));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationTracker) {}

    private static class Blocks extends BlockLoot {

        @Override
        protected void addTables() {
            dropCatTree(ModBlocks.CAT_TREE);
            dropsSelf(ModBlocks.CAT_BOWL); // Drop with the name of the cat bowl
            dropsSelf(ModBlocks.LITTER_BOX);
            dropCatnip(ModBlocks.WILD_CATNIP);
            dropsSelf(ModBlocks.CARDBOARD_BOX);
            dropsSelf(ModBlocks.CATNIP_CROP);
            dropsMouseTrap(ModBlocks.MOUSE_TRAP);
            dropsSelf(ModBlocks.CHEESE_WHEEL);
            dropsSelf(ModBlocks.PET_DOOR);
        }

        private void dropsMouseTrap(Supplier<? extends Block> block) {
            LootTable.Builder lootTableBuilder = LootTable.lootTable()
//                                                         .withPool(applyExplosionCondition(block.get(),
//                                                                                           LootPool.lootPool().when(() -> {
//                                                                                               BlockStateProperties.TRIGGERED.getValue()
//                                                                                           }))
//                                                                           .add(LootItem.lootTableItem(ModBlocks.CHEESE_WHEEL.get())))
                                                         .withPool(applyExplosionCondition(block.get(),
                                                                                           LootPool.lootPool().setRolls(ConstantValue.exactly(1)))
                                                                           .add(LootItem.lootTableItem(block.get())));
            
            this.add(block.get(), lootTableBuilder);
        }

            private void dropCatnip(Supplier<? extends Block> block) {
            LootTable.Builder lootTableBuilder = LootTable.lootTable()
                    .withPool(applyExplosionCondition(block.get(),
                                                      LootPool.lootPool().setRolls(UniformGenerator.between(0, 2)))
                                                              .add(LootItem.lootTableItem(ModItems.CATNIP_SEEDS.get())));

            this.add(block.get(), lootTableBuilder);
        }

//        private void dropCatnipCrop(Supplier<? extends Block> block) {
//            LootTable.Builder builder = LootTable.lootTable()
//                                                 .withPool(LootPool.lootPool()
//                                                                   .setRolls(ConstantValue.exactly(1))
//                                                                   .add(LootItem.lootTableItem(ModItems.CATNIP_SEEDS.get()))
//                                                                   .add(LootItem.lootTableItem(ModItems.CATNIP.get())
//                                                                                .when(() -> { return new LootItemBlockStatePropertyCondition.Builder(block.get())
//                                                                                              .setProperties(StatePropertiesPredicate.Builder.properties()
//                                                                                                                     .hasProperty(BlockStateProperties.AGE_7)); })))
//                                                 .withPool(LootPool.lootPool()
//                                                                   .setRolls(ConstantValue.exactly(1))
//                                                                   .add(LootItem.lootTableItem(ModItems.CATNIP_SEEDS.get()))
//                                                                   .apply(EnchantmentPredicate()
//                                                                          BinomialDistributionGenerator.binomial(3, 0.5714286F)
//                                                                   )
//                                                                   .when());
//
//            this.add(block.get(), builder);
//        }

        private void dropCatTree(Supplier<? extends Block> block) {
            LootTable.Builder lootTableBuilder = LootTable.lootTable()
                                                          .withPool(applyExplosionCondition(block.get(),
                                                                                            LootPool.lootPool().setRolls(ConstantValue.exactly(1)))
                                                                            .add(LootItem.lootTableItem(block.get())
                                                                                         .apply(
                                                                                                 CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                                                                                                                .copy("casingId", "catherder.casingId")
                                                                                                                .copy("beddingId", "catherder.beddingId")
                                                                                                                .copy("ownerId", "catherder.ownerId")
                                                                                                                .copy("name", "catherder.name")
                                                                                                                .copy("ownerName", "catherder.ownerName"))));

            this.add(block.get(), lootTableBuilder);
        }

        private void dropsSelf(Supplier<? extends Block> block) {
            dropSelf(block.get());
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
            this.add(EntityType.CAT, LootTable.lootTable()
                                                     .withPool(LootPool.lootPool()
                                                                       .setRolls(ConstantValue.exactly(1.0F))
                                                                       .add(LootItem.lootTableItem(ModItems.CAT_GUT.get())
                                                                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F))))));
//            this.add(ModEntityTypes.RODENT.get(), LootTable.lootTable()
//                                                     .withPool(LootPool.lootPool()
//                                                                       .setRolls(ConstantValue.exactly(1.0F))
//                                                                       .add(LootItem.lootTableItem(ModItems.RODENT.get())
//                                                                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F))))));
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
