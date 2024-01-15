package com.sweetrpg.catherder.data;

import com.sweetrpg.catherder.common.block.CheeseWheelBlock;
import com.sweetrpg.catherder.common.registry.ModBlocks;
import com.sweetrpg.catherder.common.registry.ModEntityTypes;
import com.sweetrpg.catherder.common.registry.ModItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.commands.CommandFunction;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CHLootTableProvider extends LootTableProvider {

    public CHLootTableProvider(PackOutput packOutput) {
        super(packOutput, BuiltInLootTables.all(), List.of(new LootTableProvider.SubProviderEntry(Blocks::new, LootContextParamSets.BLOCK), new LootTableProvider.SubProviderEntry(Entities::new, LootContextParamSets.ENTITY)));
    }

//    @Override
//    public String getName() {
//        return "CatHerder LootTables";
//    }

//    @Override
//    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
//        return ImmutableList.of(Pair.of(Blocks::new, LootContextParamSets.BLOCK), Pair.of(Entities::new, LootContextParamSets.ENTITY));
//    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationTracker) {
    }

    private static class Blocks extends BlockLootSubProvider {

        protected Blocks() {
            super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected void generate() {
            dropCatTree(ModBlocks.CAT_TREE);
            dropsSelf(ModBlocks.CAT_BOWL); // Drop with the name of the cat bowl
            dropsSelf(ModBlocks.LITTERBOX);
            dropWildCatnip(ModBlocks.WILD_CATNIP);
            dropsSelf(ModBlocks.CARDBOARD_BOX);
            dropsCatnipCrop(ModBlocks.CATNIP_CROP);
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

        private void dropsCatnipCrop(Supplier<? extends Block> block) {
//             dropsSelf(block);

            final var CATNIP_LOOTITEM = LootItem.lootTableItem(ModItems.CATNIP.get())
                    .when(() -> {
                        return new LootItemBlockStatePropertyCondition.Builder(ModBlocks.CATNIP_CROP.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_5, "5")).build();
                    });
            final var CATNIP_SEEDS_LOOTITEM = LootItem.lootTableItem(ModItems.CATNIP_SEEDS.get());
             final var NORMAL_POOL = LootPool.lootPool()
                     .setRolls(ConstantValue.exactly(1))
                     .add(AlternativesEntry.alternatives(CATNIP_LOOTITEM, CATNIP_SEEDS_LOOTITEM));
             final var FORTUNE_POOL = LootPool.lootPool()
                     .setRolls(ConstantValue.exactly(1))
                     .add(LootItem.lootTableItem(ModItems.CATNIP_SEEDS.get()).apply(
                             ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.5714286f, 3)
                     ))
                     .when(() -> {
                         return new LootItemBlockStatePropertyCondition.Builder(ModBlocks.CATNIP_CROP.get())
                                 .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.AGE_5, "5")).build();
                     });
            LootTable.Builder lootTableBuilder = LootTable.lootTable()
                    .withPool(NORMAL_POOL)
                    .withPool(FORTUNE_POOL)
//                    .withPool(applyExplosionCondition(block.get(),
//                            LootPool.lootPool().setRolls(UniformGenerator.between(0, 2)))
//                            .add(LootItem.lootTableItem(ModItems.CATNIP_SEEDS.get())));
//
                    .apply(ApplyExplosionDecay.explosionDecay())
            ;

            this.add(block.get(), lootTableBuilder);
        }

        private void dropWildCatnip(Supplier<? extends Block> block) {
            final var CATNIP_LOOTPOOL = LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(ModItems.CATNIP.get())
                                    .when(() -> {
                                        return LootItemRandomChanceCondition.randomChance(0.5f).build();
                                    })
                                    .when(() -> {
                                        return MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS)).invert().build();
                                    })
                    );
            final var WILD_CATNIP_LOOTITEM = LootItem.lootTableItem(ModItems.WILD_CATNIP.get())
                    .when(() -> {
                        return MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS)).invert().build();
                    });
            final var CATNIP_SEEDS_LOOTITEM = LootItem.lootTableItem(ModItems.CATNIP_SEEDS.get())
                    .apply(
                            ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 2)
                    );
            final var WILD_CATNIP_LOOTPOOL = LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(AlternativesEntry.alternatives(WILD_CATNIP_LOOTITEM, CATNIP_SEEDS_LOOTITEM));

            LootTable.Builder builder = LootTable.lootTable()
                    .withPool(CATNIP_LOOTPOOL)
                    .withPool(WILD_CATNIP_LOOTPOOL);

            this.add(block.get(), builder);
        }

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

    private static class Entities extends EntityLootSubProvider {

        protected Entities() {
            super(FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        public void generate() {
            this.registerNoLoot(ModEntityTypes.CAT);
        }

//        @Override
//        protected void addTables() {
//
//
//            for(EntityType type : Arrays.asList(EntityType.CAT, EntityType.COW, EntityType.SHEEP, EntityType.HORSE, EntityType.LLAMA, EntityType.DONKEY, EntityType.GOAT, EntityType.MULE, EntityType.MOOSHROOM, EntityType.OCELOT, EntityType.PIG)) {
//                this.add(type, LootTable.lootTable()
//                        .withPool(LootPool.lootPool()
//                                .setRolls(ConstantValue.exactly(0.1F))
//                                .add(LootItem.lootTableItem(ModItems.CAT_GUT.get())
//                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F))))));
////            this.add(ModEntityTypes.RODENT.get(), LootTable.lootTable()
////                                                     .withPool(LootPool.lootPool()
////                                                                       .setRolls(ConstantValue.exactly(1.0F))
////                                                                       .add(LootItem.lootTableItem(ModItems.RODENT.get())
////                                                                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F))))));
//            }
//        }

        protected void registerNoLoot(Supplier<? extends EntityType<?>> type) {
            this.add(type.get(), LootTable.lootTable());
        }

        @Override
        protected Stream<EntityType<?>> getKnownEntityTypes() {
            return ModEntityTypes.ENTITIES.getEntries().stream().map(Supplier::get);
        }
    }
}
