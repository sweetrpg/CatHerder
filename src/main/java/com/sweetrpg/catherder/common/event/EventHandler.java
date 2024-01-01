package com.sweetrpg.catherder.common.event;

import com.sweetrpg.catherder.api.CatHerderAPI;
import com.sweetrpg.catherder.common.config.ConfigHandler;
import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.registry.ModEntityTypes;
import com.sweetrpg.catherder.common.registry.ModItems;
import com.sweetrpg.catherder.common.talent.TomcatTalent;
import com.sweetrpg.catherder.common.world.gen.WildCropGeneration;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CatHerderAPI.MOD_ID)
public class EventHandler {

    @SubscribeEvent
    public void rightClickEntity(final PlayerInteractEvent.EntityInteract event) {
        Level world = event.getWorld();

        ItemStack stack = event.getItemStack();
        Entity target = event.getTarget();

        if(target.getType() == EntityType.CAT && target instanceof TamableAnimal && stack.getItem() == ModItems.TRAINING_TREAT.get()) {
            event.setCanceled(true);

            TamableAnimal vanillaCat = (TamableAnimal) target;

            Player player = event.getPlayer();

            if(vanillaCat.isAlive() && vanillaCat.isTame() && vanillaCat.isOwnedBy(player)) {
                if(!world.isClientSide) {
                    if(!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }

                    CatEntity cat = ModEntityTypes.CAT.get().create(world);
                    cat.tame(player);
                    cat.setHealth(cat.getMaxHealth());
                    cat.setOrderedToSit(false);
                    cat.setAge(vanillaCat.getAge());
                    cat.absMoveTo(vanillaCat.getX(), vanillaCat.getY(), vanillaCat.getZ(), vanillaCat.getYRot(), vanillaCat.getXRot());
                    cat.setOriginalBreed(((net.minecraft.world.entity.animal.Cat) vanillaCat).getCatType());

                    world.addFreshEntity(cat);

                    vanillaCat.discard();
                }

                event.setCancellationResult(InteractionResult.SUCCESS);
            }
            else {
                event.setCancellationResult(InteractionResult.FAIL);
            }
        }
    }

    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder builder = event.getGeneration();
        Biome.ClimateSettings climate = event.getClimate();

        if((event.getCategory().equals(Biome.BiomeCategory.PLAINS) ||
                event.getCategory().equals(Biome.BiomeCategory.EXTREME_HILLS) ||
                event.getCategory().equals(Biome.BiomeCategory.FOREST) ||
                event.getCategory().equals(Biome.BiomeCategory.SAVANNA) ||
                event.getCategory().equals(Biome.BiomeCategory.MUSHROOM) ||
                event.getCategory().equals(Biome.BiomeCategory.TAIGA) ||
                event.getCategory().equals(Biome.BiomeCategory.MOUNTAIN) ||
                event.getCategory().equals(Biome.BiomeCategory.JUNGLE)) &&
                (climate.temperature >= 0.2F && climate.temperature < 1.5F)) {
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WildCropGeneration.PATCH_WILD_CATNIP);
        }
    }

    @SubscribeEvent
    public void onEntitySpawn(final EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();

        if(entity instanceof Creeper) {
            Creeper creeper = (Creeper) entity;
            creeper.goalSelector.addGoal(3, new AvoidEntityGoal<>(creeper, CatEntity.class, 6.0F, 1.0D, 1.2D)); // Same goal as in AbstractSkeletonEntity
        }
    }

    @SubscribeEvent
    public void playerLoggedIn(final PlayerLoggedInEvent event) {
        if(ConfigHandler.SERVER.STARTING_ITEMS.get()) {
            Player player = event.getPlayer();
            CompoundTag tag = player.getPersistentData();

            if(!tag.contains(Player.PERSISTED_NBT_TAG)) {
                tag.put(Player.PERSISTED_NBT_TAG, new CompoundTag());
            }

            CompoundTag persistTag = tag.getCompound(Player.PERSISTED_NBT_TAG);

            if(!persistTag.getBoolean("gotCHStartingItems")) {
                persistTag.putBoolean("gotCHStartingItems", true);

                player.getInventory().add(new ItemStack(ModItems.CAT_CHARM.get()));
//                player.getInventory().add(new ItemStack(CatItems.WHISTLE.get()));
            }
        }
    }

    @SubscribeEvent
    public void onLootDrop(final LootingLevelEvent event) {
        TomcatTalent.onLootDrop(event);
    }
}
