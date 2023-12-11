package com.sweetrpg.catherder.api.inferface;

import com.sweetrpg.catherder.api.enu.WetSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public interface ICatAlteration {

    /**
     * Called when ever this instance is first added to a cat, this is called when
     * the level is first set on the cat or when it is loaded from NBT and when the
     * talents are synced to the client
     *
     * @param catIn The cat
     */
    default void init(AbstractCatEntity catIn) {

    }

    default void remove(AbstractCatEntity catIn) {

    }

    default void onWrite(AbstractCatEntity catIn, CompoundTag compound) {

    }

    default void onRead(AbstractCatEntity catIn, CompoundTag compound) {

    }

    /**
     * Called at the end of tick
     */
    default void tick(AbstractCatEntity catIn) {

    }

    /**
     * Called at the end of livingTick
     */
    default void livingTick(AbstractCatEntity catIn) {

    }

    default InteractionResultHolder<Integer> hungerTick(AbstractCatEntity catIn, int hungerTick) {
        return InteractionResultHolder.pass(hungerTick);
    }

    default InteractionResultHolder<Integer> healingTick(AbstractCatEntity catIn, int healingTick) {
        return InteractionResultHolder.pass(healingTick);
    }

    default InteractionResult processInteract(AbstractCatEntity catIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        return InteractionResult.PASS;
    }

    default InteractionResult canBeRiddenInWater(AbstractCatEntity catIn, Entity rider) {
        return InteractionResult.PASS;
    }

    default InteractionResult canTrample(AbstractCatEntity catIn, BlockState state, BlockPos pos, float fallDistance) {
        return InteractionResult.PASS;
    }

    default InteractionResultHolder<Float> calculateFallDistance(AbstractCatEntity catIn, float distance) {
        return InteractionResultHolder.pass(0F);
    }

    default InteractionResult canBreatheUnderwater(AbstractCatEntity catIn) {
        return InteractionResult.PASS;
    }

    default InteractionResult canAttack(AbstractCatEntity catIn, LivingEntity target) {
        return InteractionResult.PASS;
    }

    default InteractionResult canAttack(AbstractCatEntity catIn, EntityType<?> entityType) {
        return InteractionResult.PASS;
    }

    default InteractionResult shouldAttackEntity(AbstractCatEntity cat, LivingEntity target, LivingEntity owner) {
        return InteractionResult.PASS;
    }

    default InteractionResult hitByEntity(AbstractCatEntity cat, Entity entity) {
        return InteractionResult.PASS;
    }

    default InteractionResult attackEntityAsMob(AbstractCatEntity catIn, Entity target) {
        return InteractionResult.PASS;
    }


    default InteractionResultHolder<Float> attackEntityFrom(AbstractCatEntity cat, DamageSource source, float damage) {
        return InteractionResultHolder.pass(damage);
    }

    default InteractionResult canBlockDamageSource(AbstractCatEntity cat, DamageSource source) {
        return InteractionResult.PASS;
    }

    default void onDeath(AbstractCatEntity cat, DamageSource source) {

    }

    default void spawnDrops(AbstractCatEntity cat, DamageSource source) {

    }

    default void dropLoot(AbstractCatEntity cat, DamageSource source, boolean recentlyHitIn) {

    }

    default void dropInventory(AbstractCatEntity catIn) {

    }

    default InteractionResultHolder<Float> attackEntityFrom(AbstractCatEntity catIn, float distance, float damageMultiplier) {
        return InteractionResultHolder.pass(distance);
    }

    default InteractionResultHolder<Integer> decreaseAirSupply(AbstractCatEntity catIn, int air) {
        return InteractionResultHolder.pass(air);
    }

    default InteractionResultHolder<Integer> determineNextAir(AbstractCatEntity catIn, int currentAir) {
        return InteractionResultHolder.pass(currentAir);
    }

    default InteractionResultHolder<Integer> setFire(AbstractCatEntity catIn, int second) {
        return InteractionResultHolder.pass(second);
    }

    default InteractionResult isImmuneToFire(AbstractCatEntity catIn) {
        return InteractionResult.PASS;
    }

    default InteractionResult isInvulnerableTo(AbstractCatEntity catIn, DamageSource source) {
        return InteractionResult.PASS;
    }

    default InteractionResult isInvulnerable(AbstractCatEntity catIn) {
        return InteractionResult.PASS;
    }

    default InteractionResult onLivingFall(AbstractCatEntity catIn, float distance, float damageMultiplier) {
        return InteractionResult.PASS;
    }

    default <T> LazyOptional<T> getCapability(AbstractCatEntity catIn, Capability<T> cap, Direction side) {
        return null;
    }

    default void invalidateCapabilities(AbstractCatEntity catIn) {

    }

    default InteractionResultHolder<Float> getMaxHunger(AbstractCatEntity catIn, float currentMax) {
        return InteractionResultHolder.pass(currentMax);
    }

    default InteractionResultHolder<Float> setCatHunger(AbstractCatEntity catIn, float hunger, float diff) {
        return InteractionResultHolder.pass(hunger);
    }

    default InteractionResult isPotionApplicable(AbstractCatEntity catIn, MobEffectInstance effectIn) {
        return InteractionResult.PASS;
    }

    /**
     * Only called serverside
     * @param catIn The cat
     * @param source How the cat initially got wet
     */
    default void onShakingDry(AbstractCatEntity catIn, WetSource source) {

    }
}