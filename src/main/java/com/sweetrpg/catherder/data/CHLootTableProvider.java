package com.sweetrpg.catherder.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.sweetrpg.catherder.common.block.CheeseWheelBlock;
import com.sweetrpg.catherder.common.registry.ModBlocks;
import com.sweetrpg.catherder.common.registry.ModEntityTypes;
import com.sweetrpg.catherder.common.registry.ModItems;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.ArrayList;
import java.util.Arrays;
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
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationTracker) {
    }

    private static class Blocks extends BlockLoot {

        @Override
        protected void addTables() {
            dropCatTree(ModBlocks.CAT_TREE);
            dropsSelf(ModBlocks.CAT_BOWL); // Drop with the name of the cat bowl
            dropsSelf(ModBlocks.LITTERBOX);
//            dropsWildCatnip(ModBlocks.WILD_CATNIP);
            dropsSelf(ModBlocks.CARDBOARD_BOX);
//            dropsCatnipCrop(ModBlocks.CATNIP_CROP);
            dropsMouseTrap(ModBlocks.MOUSE_TRAP);
            dropsCheeseWheel(ModBlocks.CHEESE_WHEEL);
            dropPetDoor(ModBlocks.PET_DOOR);
        }

        private void dropsCheeseWheel(Supplier<? extends Block> block) {
            LootTable.Builder lootTableBuilder = LootTable.lootTable()
                    .withPool(applyExplosionCondition(block.get(),
                            LootPool.lootPool().setRolls(ConstantValue.exactly(1)))
                            .setRolls(ConstantValue.exactly(1))
                            .add(AlternativesEntry.alternatives(
                                    LootItem.lootTableItem(ModItems.CHEESE_WEDGE.get())
                                            .when(() -> {
                                                return new LootItemBlockStatePropertyCondition.Builder(block.get())
                                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(CheeseWheelBlock.SERVINGS, 1)).build();
                                            })
                                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))),
                                    LootItem.lootTableItem(ModItems.CHEESE_WEDGE.get())
                                            .when(() -> {
                                                return new LootItemBlockStatePropertyCondition.Builder(block.get())
                                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(CheeseWheelBlock.SERVINGS, 2)).build();
                                            })
                                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))),
                                    LootItem.lootTableItem(ModItems.CHEESE_WEDGE.get())
                                            .when(() -> {
                                                return new LootItemBlockStatePropertyCondition.Builder(block.get())
                                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(CheeseWheelBlock.SERVINGS, 3)).build();
                                            })
                                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(3))),
                                    LootItem.lootTableItem(ModItems.CHEESE_WEDGE.get())
                                            .when(() -> {
                                                return new LootItemBlockStatePropertyCondition.Builder(block.get())
                                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(CheeseWheelBlock.SERVINGS, 4)).build();
                                            })
                                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))),
                                    LootItem.lootTableItem(ModItems.CHEESE_WEDGE.get())
                                            .when(() -> {
                                                return new LootItemBlockStatePropertyCondition.Builder(block.get())
                                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(CheeseWheelBlock.SERVINGS, 5)).build();
                                            })
                                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(5))),
                                    LootItem.lootTableItem(ModItems.CHEESE_WEDGE.get())
                                            .when(() -> {
                                                return new LootItemBlockStatePropertyCondition.Builder(block.get())
                                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(CheeseWheelBlock.SERVINGS, 6)).build();
                                            })
                                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(6))),
                                    LootItem.lootTableItem(ModItems.CHEESE_WEDGE.get())
                                            .when(() -> {
                                                return new LootItemBlockStatePropertyCondition.Builder(block.get())
                                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(CheeseWheelBlock.SERVINGS, 7)).build();
                                            })
                                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(7))),
                                    LootItem.lootTableItem(ModItems.CHEESE_WEDGE.get())
                                            .when(() -> {
                                                return new LootItemBlockStatePropertyCondition.Builder(block.get())
                                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(CheeseWheelBlock.SERVINGS, 8)).build();
                                            })
                                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(8)))
                            )))
                    .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(Items.BOWL)));

            this.add(block.get(), lootTableBuilder);
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

//        private void dropsWildCatnip(Supplier<? extends Block> block) {
//            LootTable.Builder lootTableBuilder = LootTable.lootTable()
//                    .withPool(applyExplosionCondition(block.get(),
//                            LootPool.lootPool().setRolls(UniformGenerator.between(0, 2)))
//                            .add(LootItem.lootTableItem(ModItems.CATNIP_SEEDS.get())))
//                    .withPool(applyExplosionCondition(block.get(),
//                            LootPool.lootPool()
//                                    .setRolls(ConstantValue.exactly(1))
//                                    .add(LootItem.lootTableItem(ModItems.CATNIP_SEEDS.get()))
//                                    .apply(EnchantmentPredicate.Builder.
//                                            BinomialDistributionGenerator.binomial(3, 0.5714286F)
//                                    )
//                                    .when(() -> {
//                                     return   new LootItemBlockStatePropertyCondition.Builder(block.get())
//                                                .setProperties(StatePropertiesPredicate.Builder.properties()
//                                                        .hasProperty(BlockStateProperties.STAGE, "5")).build();
//                                    })));
//
//            this.add(block.get(), lootTableBuilder);
//        }

//        private void dropsCatnipCrop(Supplier<? extends Block> block) {
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
                                                    .copy("colorId", "catherder.colorId")
//                                                                                                                .copy("beddingId", "catherder.beddingId")
                                                    .copy("ownerId", "catherder.ownerId")
                                                    .copy("name", "catherder.name")
                                                    .copy("ownerName", "catherder.ownerName"))));

            this.add(block.get(), lootTableBuilder);
        }

        private void dropPetDoor(Supplier<? extends Block> block) {
            LootTable.Builder lootTableBuilder = LootTable.lootTable()
                    .withPool(applyExplosionCondition(block.get(),
                            LootPool.lootPool().setRolls(ConstantValue.exactly(1)))
                            .add(LootItem.lootTableItem(block.get())
                                    .apply(
                                            CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                                                    .copy("structureId", "catherder.structureId"))));

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

//            for(EntityType type : Arrays.asList(EntityType.CAT, EntityType.COW, EntityType.SHEEP, EntityType.HORSE, EntityType.LLAMA, EntityType.DONKEY, EntityType.GOAT, EntityType.MULE, EntityType.MOOSHROOM, EntityType.OCELOT, EntityType.PIG)) {
//                this.add(type, LootTable.lootTable()
//                        .withPool(LootPool.lootPool()
//                                .setRolls(ConstantValue.exactly(0.1F))
//                                .add(LootItem.lootTableItem(ModItems.CAT_GUT.get())
//                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F))))));
//            this.add(ModEntityTypes.RODENT.get(), LootTable.lootTable()
//                                                     .withPool(LootPool.lootPool()
//                                                                       .setRolls(ConstantValue.exactly(1.0F))
//                                                                       .add(LootItem.lootTableItem(ModItems.RODENT.get())
//                                                                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F))))));
//            }
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
