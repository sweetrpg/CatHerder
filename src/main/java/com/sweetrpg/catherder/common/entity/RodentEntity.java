package com.sweetrpg.catherder.common.entity;

import com.sweetrpg.catherder.common.registry.ModEntityTypes;
import com.sweetrpg.catherder.common.registry.ModItems;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nullable;
import java.util.Random;

//public class RodentEntity extends Animal {
//    public static final double STROLL_SPEED_MOD = 0.6D;
//    public static final double BREED_SPEED_MOD = 0.8D;
//    public static final double FOLLOW_SPEED_MOD = 1.0D;
//    public static final double FLEE_SPEED_MOD = 2.2D;
//    public static final double ATTACK_SPEED_MOD = 1.4D;
//    private static final EntityDataAccessor<Integer> DATA_TYPE_ID = SynchedEntityData.defineId(RodentEntity.class, EntityDataSerializers.INT);
//    public static final int TYPE_MOUSE = 0;
//    public static final int TYPE_RAT = 1;
//    public static final int TYPE_ROUS = 9;
//
//    public RodentEntity(EntityType<? extends Animal> p_27557_, Level p_27558_) {
//        super(p_27557_, p_27558_);
//    }
//
//    @Override
//    protected void registerGoals() {
//        this.goalSelector.addGoal(1, new FloatGoal(this));
//        this.goalSelector.addGoal(1, new RodentPanicGoal(this, 2.2D));
//        this.goalSelector.addGoal(2, new BreedGoal(this, 0.8D));
//        this.goalSelector.addGoal(3, new TemptGoal(this, 3.0D, Ingredient.of(ModItems.CHEESE_WEDGE.get()), true));
//        this.goalSelector.addGoal(4, new RodentAvoidEntityGoal<>(this, Player.class, 8.0F, 2.2D, 2.2D));
//        this.goalSelector.addGoal(4, new RodentAvoidEntityGoal<>(this, CatEntity.class, 10.0F, 2.2D, 2.2D));
//        this.goalSelector.addGoal(4, new RodentAvoidEntityGoal<>(this, Monster.class, 4.0F, 2.2D, 2.2D));
//        this.goalSelector.addGoal(5, new InfestFoodStoresGoal(this));
//        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.6D));
//    }
//
//    public void setSpeedModifier(double pNewSpeed) {
//        this.getNavigation().setSpeedModifier(pNewSpeed);
//        this.moveControl.setWantedPosition(this.moveControl.getWantedX(), this.moveControl.getWantedY(), this.moveControl.getWantedZ(), pNewSpeed);
//    }
//
//    protected void defineSynchedData() {
//        super.defineSynchedData();
//        this.entityData.define(DATA_TYPE_ID, TYPE_MOUSE);
//    }
//
//    public boolean canSpawnSprintParticle() {
//        return false;
//    }
//
//    public static AttributeSupplier.Builder createAttributes() {
//        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0D).add(Attributes.MOVEMENT_SPEED, (double)0.3F);
//    }
//
//    public void addAdditionalSaveData(CompoundTag pCompound) {
//        super.addAdditionalSaveData(pCompound);
//        pCompound.putInt("RodentType", this.getRodentType());
////        pCompound.putInt("MoreCarrotTicks", this.moreCarrotTicks);
//    }
//
//    /**
//     * (abstract) Protected helper method to read subclass entity data from NBT.
//     */
//    public void readAdditionalSaveData(CompoundTag pCompound) {
//        super.readAdditionalSaveData(pCompound);
//        this.setRodentType(pCompound.getInt("RodentType"));
////        this.moreCarrotTicks = pCompound.getInt("MoreCarrotTicks");
//    }
//
//    protected SoundEvent getAmbientSound() {
//        return SoundEvents.RABBIT_AMBIENT;
//    }
//
//    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
//        return SoundEvents.RABBIT_HURT;
//    }
//
//    protected SoundEvent getDeathSound() {
//        return SoundEvents.RABBIT_DEATH;
//    }
//
//    public boolean doHurtTarget(Entity pEntity) {
//        if (this.getRodentType() == TYPE_ROUS) {
//            this.playSound(SoundEvents.RABBIT_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
//            return pEntity.hurt(DamageSource.mobAttack(this), 8.0F);
//        }
//        else {
//            return pEntity.hurt(DamageSource.mobAttack(this), 3.0F);
//        }
//    }
//
//    public SoundSource getSoundSource() {
//        return this.getRodentType() == 99 ? SoundSource.HOSTILE : SoundSource.NEUTRAL;
//    }
//
//    public RodentEntity getBreedOffspring(ServerLevel level, AgeableMob mob) {
//        RodentEntity rodent = ModEntityTypes.RODENT.get().create(level);
//////        int i = this.getRandomRType(level);
//////        if (this.random.nextInt(20) != 0) {
//////            if (mob instanceof RodentEntity && this.random.nextBoolean()) {
//////                i = ((RodentEntity)mob).getRodentType();
//////            }
//////            else {
//////                i = this.getRodentType();
//////            }
//////        }
////
////        rodent.setRodentType(i);
//        return rodent;
//    }
//
//    /**
//     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
//     * the animal type)
//     */
//    public boolean isFood(ItemStack pStack) {
//        return true; /*isTemptingItem(pStack);*/
//    }
//
//    public int getRodentType() {
//        return this.entityData.get(DATA_TYPE_ID);
//    }
//
//    public void setRodentType(int rodentType) {
////        if (pRabbitTypeId == 99) {
////            this.getAttribute(Attributes.ARMOR).setBaseValue(8.0D);
////            this.goalSelector.addGoal(4, new Rabbit.EvilRabbitAttackGoal(this));
////            this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
////            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
////            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Wolf.class, true));
////            if (!this.hasCustomName()) {
////                this.setCustomName(new TranslatableComponent(Util.makeDescriptionId("entity", KILLER_BUNNY)));
////            }
////        }
//
//        this.entityData.set(DATA_TYPE_ID, rodentType);
//    }
//
//    @Nullable
//    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
//        int i = this.getRandomRabbitType(pLevel);
////        if (pSpawnData instanceof Rabbit.RabbitGroupData) {
////            i = ((Rodent)pSpawnData).rodentType;
////        }
////        else {
////            pSpawnData = new Rabbit.RabbitGroupData(i);
////        }
//
//        this.setRodentType(i);
//        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
//    }
//
//    private int getRandomRabbitType(LevelAccessor p_29676_) {
//        Biome biome = p_29676_.getBiome(this.blockPosition());
//        int i = this.random.nextInt(100);
//        if (biome.getPrecipitation() == Biome.Precipitation.SNOW) {
//            return i < 80 ? 1 : 3;
//        }
//        else if (biome.getBiomeCategory() == Biome.BiomeCategory.DESERT) {
//            return 4;
//        }
//        else {
//            return i < 50 ? 0 : (i < 90 ? 5 : 2);
//        }
//    }
//
//    public static boolean checkRabbitSpawnRules(EntityType<RodentEntity> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, Random random) {
//        return level.getBlockState(pos.below()).is(BlockTags.RABBITS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
//    }
//
//    public void handleEntityEvent(byte pId) {
//        if (pId == 1) {
//            this.spawnSprintParticle();
////            this.jumpDuration = 10;
////            this.jumpTicks = 0;
//        }
//        else {
//            super.handleEntityEvent(pId);
//        }
//
//    }
//
//    public boolean wantsMoreFood() {
//        return true; /*this.moreCarrotTicks == 0;*/
//    }
//}
