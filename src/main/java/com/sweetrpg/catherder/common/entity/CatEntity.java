package com.sweetrpg.catherder.common.entity;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.api.enu.WetSource;
import com.sweetrpg.catherder.api.feature.*;
import com.sweetrpg.catherder.api.feature.CatLevel.Type;
import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.inferface.ICatAlteration;
import com.sweetrpg.catherder.api.inferface.ICatFoodHandler;
import com.sweetrpg.catherder.api.inferface.IThrowableItem;
import com.sweetrpg.catherder.api.registry.*;
import com.sweetrpg.catherder.client.screen.CatInfoScreen;
import com.sweetrpg.catherder.common.config.ConfigHandler;
import com.sweetrpg.catherder.common.entity.ai.BreedGoal;
import com.sweetrpg.catherder.common.entity.ai.CatLieOnBedGoal;
import com.sweetrpg.catherder.common.entity.ai.CatSitOnBlockGoal;
import com.sweetrpg.catherder.common.entity.ai.*;
import com.sweetrpg.catherder.common.entity.misc.DimensionDependentArg;
import com.sweetrpg.catherder.common.entity.stats.StatsTracker;
import com.sweetrpg.catherder.common.lib.Constants;
import com.sweetrpg.catherder.common.registry.*;
import com.sweetrpg.catherder.common.storage.CatLocationStorage;
import com.sweetrpg.catherder.common.storage.CatRespawnStorage;
import com.sweetrpg.catherder.common.util.Cache;
import com.sweetrpg.catherder.common.util.NBTUtil;
import com.sweetrpg.catherder.common.util.Util;
import com.sweetrpg.catherder.common.util.WorldUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.DataItem;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CatEntity extends AbstractCatEntity {

    private static final EntityDataAccessor<Optional<Component>> LAST_KNOWN_NAME = SynchedEntityData.defineId(CatEntity.class, EntityDataSerializers.OPTIONAL_COMPONENT);
    private static final EntityDataAccessor<Byte> CAT_FLAGS = SynchedEntityData.defineId(CatEntity.class, EntityDataSerializers.BYTE);

    private static final EntityDataAccessor<Float> HUNGER_INT = SynchedEntityData.defineId(CatEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<String> CUSTOM_SKIN = SynchedEntityData.defineId(CatEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Integer> ORIGINAL_BREED_INT = SynchedEntityData.defineId(CatEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Byte> SIZE = SynchedEntityData.defineId(CatEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<ItemStack> TOY_VARIANT = SynchedEntityData.defineId(CatEntity.class, EntityDataSerializers.ITEM_STACK);
    //    private static final EntityDataAccessor<Boolean> IS_LYING = SynchedEntityData.defineId(CatEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RELAX_STATE_ONE = SynchedEntityData.defineId(CatEntity.class, EntityDataSerializers.BOOLEAN);

    // Use Cache.make to ensure static fields are not initialised too early (before Serializers have been registered)
    private static final Cache<EntityDataAccessor<List<AccessoryInstance>>> ACCESSORIES = Cache.make(() -> (EntityDataAccessor<List<AccessoryInstance>>) SynchedEntityData.defineId(CatEntity.class, ModSerializers.ACCESSORY_SERIALIZER.get().getSerializer()));
    private static final Cache<EntityDataAccessor<List<TalentInstance>>> TALENTS = Cache.make(() -> (EntityDataAccessor<List<TalentInstance>>) SynchedEntityData.defineId(CatEntity.class, ModSerializers.TALENT_SERIALIZER.get().getSerializer()));
    private static final Cache<EntityDataAccessor<CatLevel>> CAT_LEVEL = Cache.make(() -> (EntityDataAccessor<CatLevel>) SynchedEntityData.defineId(CatEntity.class, ModSerializers.CAT_LEVEL_SERIALIZER.get().getSerializer()));
    private static final Cache<EntityDataAccessor<Gender>> GENDER = Cache.make(() -> (EntityDataAccessor<Gender>) SynchedEntityData.defineId(CatEntity.class, ModSerializers.GENDER_SERIALIZER.get().getSerializer()));
    private static final Cache<EntityDataAccessor<Mode>> MODE = Cache.make(() -> (EntityDataAccessor<Mode>) SynchedEntityData.defineId(CatEntity.class, ModSerializers.MODE_SERIALIZER.get().getSerializer()));
    private static final Cache<EntityDataAccessor<DimensionDependentArg<Optional<BlockPos>>>> CAT_TREE_LOCATION = Cache.make(() -> (EntityDataAccessor<DimensionDependentArg<Optional<BlockPos>>>) SynchedEntityData.defineId(CatEntity.class, ModSerializers.CAT_TREE_LOC_SERIALIZER.get().getSerializer()));
    private static final Cache<EntityDataAccessor<DimensionDependentArg<Optional<BlockPos>>>> CAT_BOWL_LOCATION = Cache.make(() -> (EntityDataAccessor<DimensionDependentArg<Optional<BlockPos>>>) SynchedEntityData.defineId(CatEntity.class, ModSerializers.CAT_TREE_LOC_SERIALIZER.get().getSerializer()));
    private static final Cache<EntityDataAccessor<DimensionDependentArg<Optional<BlockPos>>>> LITTERBOX_LOCATION = Cache.make(() -> (EntityDataAccessor<DimensionDependentArg<Optional<BlockPos>>>) SynchedEntityData.defineId(CatEntity.class, ModSerializers.CAT_TREE_LOC_SERIALIZER.get().getSerializer()));
    private static final Cache<EntityDataAccessor<Integer>> ORIGINAL_BREED = Cache.make(() -> (EntityDataAccessor<Integer>) SynchedEntityData.defineId(CatEntity.class, ModSerializers.ORIGINAL_BREED_SERIALIZER.get().getSerializer()));

    public final Map<Integer, Object> objects = new HashMap<>();
    public final StatsTracker statsTracker = new StatsTracker();
    // Cached values
    private final Cache<Integer> spendablePoints = Cache.make(this::getSpendablePointsInternal);
    private final List<ICatAlteration> alterations = new ArrayList<>(4);
    private final List<ICatFoodHandler> foodHandlers = new ArrayList<>(4);
    protected boolean catJumping;
    protected float jumpPower;
    protected BlockPos targetBlock;
    private int hungerTick;
    private int prevHungerTick;
    private int healingTick;
    private int prevHealingTick;
    private float headRotationCourse;
    private float headRotationCourseOld;
    private WetSource wetSource;
    private boolean isShaking;
    private float timeCatIsShaking;
    private float prevTimeCatIsShaking;
    private int catnipTick;
    private int catnipCooldown;
    private Goal catnipGoal;
    private int litterboxCooldown;
    public static final int MIN_CAT_SIZE = 1;
    public static final int MAX_CAT_SIZE = 5;

    public CatEntity(EntityType<? extends CatEntity> type, Level worldIn) {
        super(type, worldIn);
        this.setTame(false);
        this.setGender(Gender.random(this.getRandom()));
    }

    public void setRelaxStateOne(boolean p_28186_) {
        this.entityData.set(RELAX_STATE_ONE, p_28186_);
    }

    public boolean isRelaxStateOne() {
        return this.entityData.get(RELAX_STATE_ONE);
    }

    public static void initDataParameters() {
        ACCESSORIES.get();
        TALENTS.get();
        CAT_LEVEL.get();
        GENDER.get();
        MODE.get();
        CAT_TREE_LOCATION.get();
        CAT_BOWL_LOCATION.get();
        LITTERBOX_LOCATION.get();
        ORIGINAL_BREED.get();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ACCESSORIES.get(), new ArrayList<>(4));
        this.entityData.define(TALENTS.get(), new ArrayList<>(4));
        this.entityData.define(LAST_KNOWN_NAME, Optional.empty());
        this.entityData.define(CAT_FLAGS, (byte) 0);
        this.entityData.define(GENDER.get(), Gender.UNISEX);
        this.entityData.define(MODE.get(), Mode.DOCILE);
        this.entityData.define(HUNGER_INT, 60F);
        this.entityData.define(CUSTOM_SKIN, "");
        this.entityData.define(CAT_LEVEL.get(), new CatLevel(0, 0));
        this.entityData.define(SIZE, (byte) 3);
        this.entityData.define(ORIGINAL_BREED_INT, 0);
        this.entityData.define(TOY_VARIANT, ItemStack.EMPTY);
        this.entityData.define(CAT_TREE_LOCATION.get(), new DimensionDependentArg<>(() -> EntityDataSerializers.OPTIONAL_BLOCK_POS));
        this.entityData.define(CAT_BOWL_LOCATION.get(), new DimensionDependentArg<>(() -> EntityDataSerializers.OPTIONAL_BLOCK_POS));
        this.entityData.define(LITTERBOX_LOCATION.get(), new DimensionDependentArg<>(() -> EntityDataSerializers.OPTIONAL_BLOCK_POS));
//        this.entityData.define(IS_LYING, false);
        this.entityData.define(RELAX_STATE_ONE, false);
    }

    @Override
    protected void registerGoals() {
        // personal goals
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));

        this.goalSelector.addGoal(2, new CatEntity.CatRelaxOnOwnerGoal(this));

        this.goalSelector.addGoal(3, new TemptGoal(this, 1.5D, Ingredient.of(ModItems.CATNIP.get()), false));

        this.goalSelector.addGoal(4, new TemptGoal(this, 1.0D, Ingredient.of(ItemTags.FISHES), false));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.0D, Ingredient.of(ModTags.MEAT), false));

        this.goalSelector.addGoal(5, new PlayInCardboardBoxGoal<>(this, 1.1F, 16));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(5, new com.sweetrpg.catherder.common.entity.ai.MoveToBlockGoal(this));
        this.goalSelector.addGoal(5, new SkittishModeGoal<>(this));

        this.goalSelector.addGoal(6, new FetchGoal(this, 1.3D, 32.0F));
        this.goalSelector.addGoal(6, new CatDomesticWanderGoal(this, 1.0D));
//        this.goalSelector.addGoal(6, new CatWanderGoal(this, 1.0D, ConfigHandler.CLIENT.MAX_WANDER_DISTANCE.get()));

        this.goalSelector.addGoal(7, new CatFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));

        this.goalSelector.addGoal(9, new CatLieOnBedGoal<>(this, 1.1F, 16));
        this.goalSelector.addGoal(9, new CatSitOnBlockGoal<>(this, 0.8F));

        this.goalSelector.addGoal(10, new UseLitterboxGoal<>(this, 20));

        this.goalSelector.addGoal(12, new BreedGoal(this, 1.0D));

        this.goalSelector.addGoal(15, new WaterAvoidingRandomStrollGoal(this, 1.0D));

        this.goalSelector.addGoal(20, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(20, new RandomLookAroundGoal(this));

        // target-based goals
        this.targetSelector.addGoal(1, new NonTameRandomTargetGoal<>(this, Rabbit.class, false, (Predicate<LivingEntity>) null));
        this.targetSelector.addGoal(6, new AttackModeGoal<>(this, Monster.class, false));
        this.targetSelector.addGoal(6, new GuardModeGoal(this, false));
    }

//    @Override
//    public void playStepSound(BlockPos pos, BlockState blockIn) {
//        // this.playSound(SoundEvents.CATWOLF_STEP, 0.15F, 1.0F);
//    }

    @Override
    protected SoundEvent getAmbientSound() {
        if(this.catnipTick > 0) {
            return SoundEvents.CAT_PURR;
        }
        else if(this.random.nextInt(3) == 0) {
            return this.getHealth() < 10.0F ? SoundEvents.CAT_BEG_FOR_FOOD : SoundEvents.CAT_PURREOW;
        }
        else {
            return SoundEvents.CAT_AMBIENT;
        }
    }

    public void hiss() {
        this.playSound(SoundEvents.CAT_HISS, this.getSoundVolume(), this.getVoicePitch());
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.CAT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.CAT_DEATH;
    }

    @Override
    public float getSoundVolume() {
        return 0.4F;
    }

    public boolean isCatWet() {
        return this.wetSource != null;
    }

    @OnlyIn(Dist.CLIENT)
    public float getShadingWhileWet(float partialTicks) {
        return Math.min(0.5F + Mth.lerp(partialTicks, this.prevTimeCatIsShaking, this.timeCatIsShaking) / 2.0F * 0.5F, 1.0F);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public float getShakeAngle(float partialTicks, float offset) {
        float f = (Mth.lerp(partialTicks, this.prevTimeCatIsShaking, this.timeCatIsShaking) + offset) / 1.8F;
        if(f < 0.0F) {
            f = 0.0F;
        }
        else if(f > 1.0F) {
            f = 1.0F;
        }

        return Mth.sin(f * (float) Math.PI) * Mth.sin(f * (float) Math.PI * 11.0F) * 0.15F * (float) Math.PI;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public float getInterestedAngle(float partialTicks) {
        return Mth.lerp(partialTicks, this.headRotationCourseOld, this.headRotationCourse) * 0.15F * (float) Math.PI;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if(id == Constants.EntityState.CAT_START_SHAKING) {
            this.startShaking();
        }
        else if(id == Constants.EntityState.CAT_INTERRUPT_SHAKING) {
            this.finishShaking();
        }
        else {
            super.handleEntityEvent(id);
        }
    }

    public float getTailRotation() {
//        return this.isTame() ? (0.55F - (this.getMaxHealth() - this.getHealth()) * 0.02F) * (float) Math.PI : ((float) Math.PI / 5F);
        return ((float) Math.PI / 5F);
    }

    @Override
    public float getWagAngle(float limbSwing, float limbSwingAmount, float partialTickTime) {
//        return Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        return Mth.cos(limbSwing * 0.6662F) * 0.9F * limbSwingAmount;
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return sizeIn.height * 0.8F;
    }

    @Override
    public Vec3 getLeashOffset() {
        return new Vec3(0.0D, 0.6F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }

    @Override
    public int getMaxHeadXRot() {
        return this.isInSittingPose() ? 20 : super.getMaxHeadXRot();
    }

    @Override
    public double getMyRidingOffset() {
        return this.getVehicle() instanceof Player ? 0.5D : 0.0D;
    }

    @Override
    public void tick() {
        super.tick();

        if(this.isAlive()) {
            this.headRotationCourseOld = this.headRotationCourse;
            if(this.isBegging()) {
                this.headRotationCourse += (1.0F - this.headRotationCourse) * 0.4F;
            }
            else {
                this.headRotationCourse += (0.0F - this.headRotationCourse) * 0.4F;
            }

            boolean inWater = this.isInWater();
            // If inWater is false then isInRain is true in the or statement
            boolean inRain = !inWater && this.isInWaterOrRain();
            boolean inBubbleColumn = this.isInBubbleColumn();

            if(inWater || inRain || inBubbleColumn) {
                if(this.wetSource == null) {
                    this.wetSource = WetSource.of(inWater, inBubbleColumn, inRain);
                }
                if(this.isShaking && !this.level.isClientSide) {
                    this.finishShaking();
                    this.level.broadcastEntityEvent(this, Constants.EntityState.CAT_INTERRUPT_SHAKING);
                }
            }
            else if((this.wetSource != null || this.isShaking) && this.isShaking) {
                if(this.timeCatIsShaking == 0.0F) {
//                    this.playSound(SoundEvents.WOLF_SHAKE, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                }

                this.prevTimeCatIsShaking = this.timeCatIsShaking;
                this.timeCatIsShaking += 0.05F;
                if(this.prevTimeCatIsShaking >= 2.0F) {

                    //TODO check if only called server side
                    if(this.wetSource != null) {
                        for(ICatAlteration alter : this.alterations) {
                            alter.onShakingDry(this, this.wetSource);
                        }
                    }

                    this.wetSource = null;
                    this.finishShaking();
                }

                if(this.timeCatIsShaking > 0.4F) {
                    float f = (float) this.getY();
                    int i = (int) (Mth.sin((this.timeCatIsShaking - 0.4F) * (float) Math.PI) * 7.0F);
                    Vec3 vec3d = this.getDeltaMovement();

                    for(int j = 0; j < i; ++j) {
                        float f1 = (this.random.nextFloat() * 2.0F - 1.0F) * this.getBbWidth() * 0.5F;
                        float f2 = (this.random.nextFloat() * 2.0F - 1.0F) * this.getBbWidth() * 0.5F;
                        this.level.addParticle(ParticleTypes.SPLASH, this.getX() + f1, f + 0.8F, this.getZ() + f2, vec3d.x, vec3d.y, vec3d.z);
                    }
                }
            }

            // On server side
            if(!this.level.isClientSide) {
                if(this.catnipTick > 0) {
                    this.catnipTick--;
                }
                else {
                    if(this.catnipGoal != null) {
                        this.goalSelector.removeGoal(this.catnipGoal);
                        this.catnipGoal = null;
                        // TODO: add goal to nap afterward
                    }
                }
                if(this.catnipCooldown > 0) {
                    this.catnipCooldown--;
                }
                if(this.litterboxCooldown > 0) {
                    this.litterboxCooldown--;
                }

                // Every 2 seconds
                if(this.tickCount % 40 == 0) {
                    CatLocationStorage.get(this.level).getOrCreateData(this).update(this);

                    if(this.getOwner() != null) {
                        this.setOwnersName(this.getOwner().getName());
                    }
                }
            }
        }

        this.alterations.forEach((alter) -> alter.tick(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if(!this.level.isClientSide && this.wetSource != null && !this.isShaking && !this.isPathFinding() && this.isOnGround()) {
            this.startShaking();
            this.level.broadcastEntityEvent(this, Constants.EntityState.CAT_START_SHAKING);
        }

        if(!this.level.isClientSide) {
            if(!ConfigHandler.SERVER.DISABLE_HUNGER.get()) {
                this.prevHungerTick = this.hungerTick;

                if(!this.isVehicle() && !this.isInSittingPose()) {
                    this.hungerTick += 1;
                }

                for(ICatAlteration alter : this.alterations) {
                    InteractionResultHolder<Integer> result = alter.hungerTick(this, this.hungerTick - this.prevHungerTick);

                    if(result.getResult().shouldSwing()) {
                        this.hungerTick = result.getObject() + this.prevHungerTick;
                    }
                }

                if(this.hungerTick > 400) {
                    this.setCatHunger(this.getCatHunger() - 1);
                    this.hungerTick -= 400;
                }
            }

            this.prevHealingTick = this.healingTick;
            this.healingTick += 8;

            if(this.isInSittingPose()) {
                this.healingTick += 4;
            }

            for(ICatAlteration alter : this.alterations) {
                InteractionResultHolder<Integer> result = alter.healingTick(this, this.healingTick - this.prevHealingTick);

                if(result.getResult().shouldSwing()) {
                    this.healingTick = result.getObject() + this.prevHealingTick;
                }
            }

            if(this.healingTick >= 6000) {
                if(this.getHealth() < this.getMaxHealth()) {
                    this.heal(1);
                }

                this.healingTick = 0;
            }
        }

        if(ConfigHandler.CLIENT.DIRE_PARTICLES.get() && this.level.isClientSide && this.getCatLevel().isDireCat()) {
            for(int i = 0; i < 2; i++) {
                this.level.addParticle(ParticleTypes.PORTAL, this.getRandomX(0.5D), this.getRandomY() - 0.25D, this.getRandomZ(0.5D), (this.random.nextDouble() - 0.5D) * 2D, -this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2D);
            }
        }

        // Check if cat bowl still exists every 50t/2.5s, if not remove
        if(this.tickCount % 50 == 0) {
            ResourceKey<Level> dimKey = this.level.dimension();
            Optional<BlockPos> bowlPos = this.getBowlPos(dimKey);

            // If the cat has a cat bowl in this dimension then check if it is still there
            // Only check if the chunk it is in is loaded
            if(bowlPos.isPresent() && this.level.hasChunkAt(bowlPos.get()) && !this.level.getBlockState(bowlPos.get()).is(ModBlocks.CAT_BOWL.get())) {
                this.setBowlPos(dimKey, Optional.empty());
            }
        }

        this.alterations.forEach((alter) -> alter.livingTick(this));
    }

    public InteractionResult consumeCatnip(Player player, InteractionHand hand) {
        if(this.catnipTick > 0 || this.catnipCooldown > 0) {
            this.level.broadcastEntityEvent(this, Constants.EntityState.CAT_SMOKE);
            return InteractionResult.FAIL;
        }

        this.catnipCooldown = 4000 + ((this.getRandom().nextInt() % 10) * 100);
        this.catnipTick = 400 + ((this.getRandom().nextInt() % 3) * 100);
//        this.setSpeed(2);
//        this.setSprinting(true);
//        this.level.broadcastEntityEvent(this, Constants.EntityState.CAT_HEARTS);
        this.catnipGoal = new CatnipGoal(this, 4);
        this.goalSelector.addGoal(1, this.catnipGoal);

        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);

        if(this.isTame()) {
            if(stack.getItem() == Items.STRING && this.canInteract(player)) {

                if(this.level.isClientSide) {
                    CatInfoScreen.open(this);
                }

                return InteractionResult.SUCCESS;
            }
        }
        else { // Not tamed
            if(/*stack.getItem() == ModItems. ||*/ stack.getItem() == ModItems.TRAINING_TREAT.get()) {

                if(!this.level.isClientSide) {
                    this.usePlayerItem(player, hand, stack);

                    if(stack.getItem() == ModItems.TRAINING_TREAT.get() || this.random.nextInt(3) == 0) {
                        this.tame(player);
                        this.navigation.stop();
                        this.setTarget(null);
                        this.setOrderedToSit(true);
                        this.setHealth(20.0F);
                        this.level.broadcastEntityEvent(this, Constants.EntityState.CAT_HEARTS);
                    }
                    else {
                        this.level.broadcastEntityEvent(this, Constants.EntityState.CAT_SMOKE);
                    }
                }

                return InteractionResult.SUCCESS;
            }
        }

        Optional<ICatFoodHandler> foodHandler = FoodHandler.getMatch(this, stack, player);

        if(foodHandler.isPresent()) {
            return foodHandler.get().consume(this, stack, player);
        }

        InteractionResult interactResult = InteractHandler.getMatch(this, stack, player, hand);

        if(interactResult != InteractionResult.PASS) {
            return interactResult;
        }

        for(ICatAlteration alter : this.alterations) {
            InteractionResult result = alter.processInteract(this, this.level, player, hand);
            if(result != InteractionResult.PASS) {
                return result;
            }
        }

        InteractionResult actionResultType = super.mobInteract(player, hand);
        if((!actionResultType.consumesAction() || this.isBaby()) && this.canInteract(player)) {
            this.setOrderedToSit(!this.isOrderedToSit());
            this.jumping = false;
            this.navigation.stop();
            this.setTarget(null);
            return InteractionResult.SUCCESS;
        }

        return actionResultType;
    }

    @Override
    public boolean canBeRiddenInWater(Entity rider) {
        for(ICatAlteration alter : this.alterations) {
            InteractionResult result = alter.canBeRiddenInWater(this, rider);

            if(result.shouldSwing()) {
                return true;
            }
            else if(result == InteractionResult.FAIL) {
                return false;
            }
        }

        return super.canBeRiddenInWater(rider);
    }

    @Override
    public boolean canTrample(BlockState state, BlockPos pos, float fallDistance) {
        return false;
//        for(ICatAlteration alter : this.alterations) {
//            InteractionResult result = alter.canTrample(this, state, pos, fallDistance);
//
//            if(result.shouldSwing()) {
//                return true;
//            }
//            else if(result == InteractionResult.FAIL) {
//                return false;
//            }
//        }
//
//        return super.canTrample(state, pos, fallDistance);
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {
//        for(ICatAlteration alter : this.alterations) {
//            InteractionResult result = alter.onLivingFall(this, distance, damageMultiplier); // TODO pass source
//
//            if(result.shouldSwing()) {
//                return true;
//            }
//            else if(result == InteractionResult.FAIL) {
//                return false;
//            }
//        }
//
//        // Start: Logic copied from the super call and altered to apply the reduced fall damage to passengers too. #358
//        float[] ret = net.minecraftforge.common.ForgeHooks.onLivingFall(this, distance, damageMultiplier);
//        if(ret == null) {
//            return false;
//        }
//        distance = ret[0];
//        damageMultiplier = ret[1];
//
//        int i = this.calculateFallDamage(distance, damageMultiplier);
//
//        if(i > 0) {
//            if(this.isVehicle()) {
//                for(Entity e : this.getPassengers()) {
//                    e.hurt(DamageSource.FALL, i);
//                }
//            }
//
//            // Sound selection is copied from Entity#getFallDamageSound()
//            this.playSound(i > 4 ? this.getFallSounds().big() : this.getFallSounds().small(), 1.0F, 1.0F);
//            this.playBlockFallSound();
//            this.hurt(DamageSource.FALL, (float) i);
//            return true;
//        }
//        else {
//            return false;
//        }
//        // End: Logic copied from the super call and altered to apply the reduced fall damage to passengers too. #358
        return false;
    }

    // TODO
    @Override
    public int getMaxFallDistance() {
        return super.getMaxFallDistance();
    }

    @Override
    protected int calculateFallDamage(float distance, float damageMultiplier) {
        return 0;
//        MobEffectInstance effectInst = this.getEffect(MobEffects.JUMP);
//        float f = effectInst == null ? 0.0F : effectInst.getAmplifier() + 1;
//        distance -= f;
//
//        for(ICatAlteration alter : this.alterations) {
//            InteractionResultHolder<Float> result = alter.calculateFallDistance(this, distance);
//
//            if(result.getResult().shouldSwing()) {
//                distance = result.getObject();
//                break;
//            }
//        }
//
//        return Mth.ceil((distance - 3.0F - f) * damageMultiplier);
    }

    @Override
    public boolean canBreatheUnderwater() {
        for(ICatAlteration alter : this.alterations) {
            InteractionResult result = alter.canBreatheUnderwater(this);

            if(result.shouldSwing()) {
                return true;
            }
            else if(result == InteractionResult.FAIL) {
                return false;
            }
        }

        return super.canBreatheUnderwater();
    }

    @Override
    protected int decreaseAirSupply(int air) {
        for(ICatAlteration alter : this.alterations) {
            InteractionResultHolder<Integer> result = alter.decreaseAirSupply(this, air);

            if(result.getResult().shouldSwing()) {
                return result.getObject();
            }
        }

        return super.decreaseAirSupply(air);
    }

    @Override
    protected int increaseAirSupply(int currentAir) {
        currentAir += 4;
        for(ICatAlteration alter : this.alterations) {
            InteractionResultHolder<Integer> result = alter.determineNextAir(this, currentAir);

            if(result.getResult().shouldSwing()) {
                currentAir = result.getObject();
                break;
            }
        }

        return Math.min(currentAir, this.getMaxAirSupply());
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        if(this.isMode(Mode.DOCILE)) {
            return false;
        }

        for(ICatAlteration alter : this.alterations) {
            InteractionResult result = alter.canAttack(this, target);

            if(result.shouldSwing()) {
                return true;
            }
            else if(result == InteractionResult.FAIL) {
                return false;
            }
        }

        // Stop cats being able to attack creepers. If the cat has lvl 5 creeper
        // sweeper then we will return true in the for loop above.
        if(target instanceof Creeper) {
            return false;
        }

        return super.canAttack(target);
    }

    @Override
    public boolean canAttackType(EntityType<?> entityType) {
        if(this.isMode(Mode.DOCILE)) {
            return false;
        }

        for(ICatAlteration alter : this.alterations) {
            InteractionResult result = alter.canAttack(this, entityType);

            if(result.shouldSwing()) {
                return true;
            }
            else if(result == InteractionResult.FAIL) {
                return false;
            }
        }

        // Stop cats being able to attack creepers. If the cat has lvl 5 creeper
        // sweeper then we will return true in the for loop above.
        if(entityType == EntityType.CREEPER) {
            return false;
        }

        return super.canAttackType(entityType);
    }

    @Override
    public boolean wantsToAttack(LivingEntity target, LivingEntity owner) {
        if(this.isMode(Mode.DOCILE)) {
            return false;
        }

        for(ICatAlteration alter : this.alterations) {
            InteractionResult result = alter.shouldAttackEntity(this, target, owner);

            if(result.shouldSwing()) {
                return true;
            }
            else if(result == InteractionResult.FAIL) {
                return false;
            }
        }

        // Stop cats being able to attack creepers. If the cat has lvl 5 creeper
        // sweeper then we will return true in the for loop above.
        if(target instanceof Creeper || target instanceof Ghast) {
            return false;
        }

        if(target instanceof Cat) {
            Cat catEntity = (Cat) target;
            return !catEntity.isTame() || catEntity.getOwner() != owner;
        }
        else if(target instanceof CatEntity) {
            CatEntity catEntity = (CatEntity) target;
            return !catEntity.isTame() || catEntity.getOwner() != owner;
        }
        else if(target instanceof Player && owner instanceof Player && !((Player) owner).canHarmPlayer((Player) target)) {
            return false;
        }
//        else if(target instanceof AbstractHorse && ((AbstractHorse) target).isTamed()) {
//            return false;
//        }
        else {
            return !(target instanceof TamableAnimal) || !((TamableAnimal) target).isTame();
        }
    }

    // TODO
    //@Override
//    public boolean canAttack(LivingEntity livingentityIn, EntityPredicate predicateIn) {
//        return predicateIn.canTarget(this, livingentityIn);
//     }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        for(ICatAlteration alter : this.alterations) {
            InteractionResultHolder<Float> result = alter.attackEntityFrom(this, source, amount);

            // TODO
            if(result.getResult() == InteractionResult.FAIL) {
                return false;
            }
            else {
                amount = result.getObject();
            }
        }

        if(this.isInvulnerableTo(source)) {
            return false;
        }
        else {
            Entity entity = source.getEntity();
            // Must be checked here too as hitByEntity only applies to when the cat is
            // directly hit not indirect damage like sweeping effect etc
            if(entity instanceof Player && !this.canPlayersAttack()) {
                return false;
            }

            this.setOrderedToSit(false);

            if(entity != null && !(entity instanceof Player) && !(entity instanceof AbstractArrow)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.hurt(source, amount);
        }
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        for(ICatAlteration alter : this.alterations) {
            InteractionResult result = alter.attackEntityAsMob(this, target);

            if(result.shouldSwing()) {
                return true;
            }
            else if(result == InteractionResult.FAIL) {
                return false;
            }
        }

        AttributeInstance attackDamageInst = this.getAttribute(Attributes.ATTACK_DAMAGE);

        Set<AttributeModifier> critModifiers = null;

        if(this.getAttribute(ModAttributes.CRIT_CHANCE.get()).getValue() > this.getRandom().nextDouble()) {
            critModifiers = this.getAttribute(ModAttributes.CRIT_BONUS.get()).getModifiers();
            critModifiers.forEach(attackDamageInst::addTransientModifier);
        }

        int damage = ((int) attackDamageInst.getValue());
        if(critModifiers != null) {
            critModifiers.forEach(attackDamageInst::removeModifier);
        }

        boolean flag = target.hurt(DamageSource.mobAttack(this), damage);
        if(flag) {
            this.doEnchantDamageEffects(this, target);
            this.statsTracker.increaseDamageDealt(damage);

            if(critModifiers != null) {
                // TODO Might want to make into a packet
                DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> Minecraft.getInstance().particleEngine.createTrackingEmitter(target, ParticleTypes.CRIT));
            }
        }

        return flag;
    }

    @Override
    public void awardKillScore(Entity killed, int scoreValue, DamageSource damageSource) {
        super.awardKillScore(killed, scoreValue, damageSource);
        this.statsTracker.incrementKillCount(killed);
    }

    @Override
    public boolean isDamageSourceBlocked(DamageSource source) {
        for(ICatAlteration alter : this.alterations) {
            InteractionResult result = alter.canBlockDamageSource(this, source);

            if(result.shouldSwing()) {
                return true;
            }
            else if(result == InteractionResult.FAIL) {
                return false;
            }
        }

        return super.isDamageSourceBlocked(source);
    }

    @Override
    public void setSecondsOnFire(int second) {
        for(ICatAlteration alter : this.alterations) {
            InteractionResultHolder<Integer> result = alter.setFire(this, second);

            if(result.getResult().shouldSwing()) {
                second = result.getObject();
            }
        }

        super.setSecondsOnFire(second);
    }

    @Override
    public boolean fireImmune() {
        for(ICatAlteration alter : this.alterations) {
            InteractionResult result = alter.isImmuneToFire(this);

            if(result.shouldSwing()) {
                return true;
            }
            else if(result == InteractionResult.FAIL) {
                return false;
            }
        }

        return super.fireImmune();
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        for(ICatAlteration alter : this.alterations) {
            InteractionResult result = alter.isInvulnerableTo(this, source);

            if(result.shouldSwing()) {
                return true;
            }
            else if(result == InteractionResult.FAIL) {
                return false;
            }
        }

        return super.isInvulnerableTo(source);
    }

    @Override
    public boolean isInvulnerable() {
        for(ICatAlteration alter : this.alterations) {
            InteractionResult result = alter.isInvulnerable(this);

            if(result.shouldSwing()) {
                return true;
            }
            else if(result == InteractionResult.FAIL) {
                return false;
            }
        }

        return super.isInvulnerable();
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effectIn) {
        for(ICatAlteration alter : this.alterations) {
            InteractionResult result = alter.isPotionApplicable(this, effectIn);

            if(result.shouldSwing()) {
                return true;
            }
            else if(result == InteractionResult.FAIL) {
                return false;
            }
        }

        return super.canBeAffected(effectIn);
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return this.canInteract(player) && super.canBeLeashed(player);
    }

    @Override
    public void setUUID(UUID uniqueIdIn) {

        // If the UUID is changed remove old one and add new one
        UUID oldUniqueId = this.getUUID();

        if(uniqueIdIn.equals(oldUniqueId)) {
            return; // No change do nothing
        }

        super.setUUID(uniqueIdIn);

        if(this.level != null && !this.level.isClientSide) {
            CatLocationStorage.get(this.level).remove(oldUniqueId);
            CatLocationStorage.get(this.level).getOrCreateData(this).update(this);
        }
    }

    @Override
    public void tame(Player player) {
        super.tame(player);
        // When tamed by player cache their display name
        this.setOwnersName(player.getName());
    }

    @Override
    public void setTame(boolean tamed) {
        super.setTame(tamed);
        if(tamed) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
            this.setHealth(20.0F);
        }
        else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
        }

        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    @Override
    public void setOwnerUUID(@Nullable UUID uuid) {
        super.setOwnerUUID(uuid);

        if(uuid == null) {
            this.setOwnersName((Component) null);
        }
    }

    @Override // blockAttackFromPlayer
    public boolean skipAttackInteraction(Entity entityIn) {
        if(entityIn instanceof Player && !this.canPlayersAttack()) {
            return true;
        }

        for(ICatAlteration alter : this.alterations) {
            InteractionResult result = alter.hitByEntity(this, entityIn);

            if(result.shouldSwing()) {
                return true;
            }
            else if(result == InteractionResult.FAIL) {
                return false;
            }
        }

        return false;
    }

    @Override
    public ItemStack getPickedResult(HitResult target) {
        return new ItemStack(ModItems.CAT_CHARM.get());
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ModTags.BREEDING_ITEMS);
    }

    @Override
    public boolean canMate(Animal otherAnimal) {
        if(otherAnimal == this) {
            return false;
        }
        else if(!this.isTame()) {
            return false;
        }
        else if(!(otherAnimal instanceof CatEntity)) {
            return false;
        }
        else {
            CatEntity entityCat = (CatEntity) otherAnimal;
            if(!entityCat.isTame()) {
                return false;
            }
            else if(entityCat.isInSittingPose()) {
                return false;
            }
            else if(ConfigHandler.SERVER.CAT_GENDER.get() && !this.getGender().canMateWith(entityCat.getGender())) {
                return false;
            }
            else {
                return this.isInLove() && entityCat.isInLove();
            }
        }
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel worldIn, AgeableMob partner) {
        CatEntity child = ModEntityTypes.CAT.get().create(worldIn);
        UUID uuid = this.getOwnerUUID();

        if(uuid != null) {
            child.setOwnerUUID(uuid);
            child.setTame(true);
        }

        if(partner instanceof CatEntity && ConfigHandler.SERVER.KITTENS_GET_PARENT_LEVELS.get()) {
            child.setLevel(this.getCatLevel().combine(((CatEntity) partner).getCatLevel()));
        }

        return child;
    }

    @Override
    public boolean shouldShowName() {
        return (ConfigHandler.ALWAYS_SHOW_CAT_NAME && this.hasCustomName()) || super.shouldShowName();
    }

    @Override
    public float getScale() {
        if(this.isBaby()) {
            return 0.5F;
        }
        else {
            return this.getCatSize() * 0.3F + 0.1F;
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        // Any mod that tries to access capabilities from entity size/entity
        // creation event will crash here because of the order java inits the
        // classes fields and so will not have been initialised and are
        // accessed during the classes super() call.
        // Since this.alterations will be empty anyway as we have not read
        // NBT data at this point just avoid silent error
        // CatHerder#295, CatHerder#296
        if(this.alterations == null) {
            return super.getCapability(cap, side);
        }

        for(ICatAlteration alter : this.alterations) {
            LazyOptional<T> result = alter.getCapability(this, cap, side);

            if(result != null) {
                return result;
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public Entity changeDimension(ServerLevel worldIn, ITeleporter teleporter) {
        Entity transportedEntity = super.changeDimension(worldIn, teleporter);
        if(transportedEntity instanceof CatEntity) {
            CatLocationStorage.get(this.level).getOrCreateData(this).update((CatEntity) transportedEntity);
        }
        return transportedEntity;
    }

    @Override
    public void remove(Entity.RemovalReason removalReason) {
        super.remove(removalReason);

        if(removalReason == RemovalReason.DISCARDED || removalReason == RemovalReason.KILLED) {
            if(this.level != null && !this.level.isClientSide) {
                CatRespawnStorage.get(this.level).putData(this);
                CatLocationStorage.get(this.level).remove(this);
            }
        }
    }

    @Override
    protected void tickDeath() {
        if(this.deathTime == 19) { // 1 second after death
            if(this.level != null && !this.level.isClientSide) {
//                catrespawnStorage.get(this.world).putData(this);
//                CatHerder.LOGGER.debug("Saved cat as they died {}", this);
//
//                CatLocationStorage.get(this.world).remove(this);
//                CatHerder.LOGGER.debug("Removed cat location as they were removed from the world {}", this);
            }
        }

        super.tickDeath();
    }

    private void startShaking() {
        this.isShaking = true;
        this.timeCatIsShaking = 0.0F;
        this.prevTimeCatIsShaking = 0.0F;
    }

    private void finishShaking() {
        this.isShaking = false;
        this.timeCatIsShaking = 0.0F;
        this.prevTimeCatIsShaking = 0.0F;
    }

    @Override
    public void die(DamageSource cause) {
        this.wetSource = null;
        this.finishShaking();

        this.alterations.forEach((alter) -> alter.onDeath(this, cause));
        super.die(cause);
    }

    @Override
    public void dropEquipment() {
        super.dropEquipment();

        this.alterations.forEach((alter) -> alter.dropInventory(this));
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.alterations.forEach((alter) -> alter.invalidateCapabilities(this));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        ListTag talentList = new ListTag();
        List<TalentInstance> talents = this.getTalentMap();

        for(TalentInstance talent : talents) {
            CompoundTag talentTag = new CompoundTag();
            talent.writeInstance(this, talentTag);
            talentList.add(talentTag);
        }

        compound.put("talents", talentList);

        ListTag accessoryList = new ListTag();
        List<AccessoryInstance> accessories = this.getAccessories();

        for(AccessoryInstance accessory : accessories) {
            CompoundTag accessoryTag = new CompoundTag();
            accessory.writeInstance(accessoryTag);
            accessoryList.add(accessoryTag);
        }

        compound.put("accessories", accessoryList);

        compound.putString("mode", this.getMode().getSaveName());
        compound.putString("catGender", this.getGender().getSaveName());
        compound.putFloat("catHunger", this.getCatHunger());
        this.getOwnersName().ifPresent((comp) -> {
            NBTUtil.putTextComponent(compound, "lastKnownOwnerName", comp);
        });

        compound.putString("customSkinHash", this.getSkinHash());
        compound.putBoolean("willObey", this.willObeyOthers());
        compound.putBoolean("friendlyFire", this.canPlayersAttack());
        compound.putInt("catSize", this.getCatSize());
        compound.putInt("level_normal", this.getCatLevel().getLevel(Type.NORMAL));
        compound.putInt("level_dire", this.getCatLevel().getLevel(Type.WILD));
        compound.putInt("original_breed", this.getOriginalBreed());
        NBTUtil.writeItemStack(compound, "fetchItem", this.getToyVariant());

        DimensionDependentArg<Optional<BlockPos>> bedsData = this.entityData.get(CAT_TREE_LOCATION.get());

        if(!bedsData.isEmpty()) {
            ListTag bedsList = new ListTag();

            for(Entry<ResourceKey<Level>, Optional<BlockPos>> entry : bedsData.entrySet()) {
                CompoundTag bedNBT = new CompoundTag();
                NBTUtil.putResourceLocation(bedNBT, "dim", entry.getKey().location());
                NBTUtil.putBlockPos(bedNBT, "pos", entry.getValue());
                bedsList.add(bedNBT);
            }

            compound.put("beds", bedsList);
        }

        DimensionDependentArg<Optional<BlockPos>> bowlsData = this.entityData.get(CAT_BOWL_LOCATION.get());

        if(!bowlsData.isEmpty()) {
            ListTag bowlsList = new ListTag();

            for(Entry<ResourceKey<Level>, Optional<BlockPos>> entry : bowlsData.entrySet()) {
                CompoundTag bowlsNBT = new CompoundTag();
                NBTUtil.putResourceLocation(bowlsNBT, "dim", entry.getKey().location());
                NBTUtil.putBlockPos(bowlsNBT, "pos", entry.getValue());
                bowlsList.add(bowlsNBT);
            }

            compound.put("bowls", bowlsList);
        }

        this.statsTracker.writeAdditional(compound);

        this.alterations.forEach((alter) -> alter.onWrite(this, compound));
    }

    @Override
    public void load(CompoundTag compound) {

        // DataFix uuid entries and attribute ids
        try {
            if(NBTUtil.hasOldUniqueId(compound, "UUID")) {
                UUID entityUUID = NBTUtil.getOldUniqueId(compound, "UUID");

                compound.putUUID("UUID", entityUUID);
                NBTUtil.removeOldUniqueId(compound, "UUID");
            }

            if(compound.contains("OwnerUUID", Tag.TAG_STRING)) {
                UUID ownerUUID = UUID.fromString(compound.getString("OwnerUUID"));

                compound.putUUID("Owner", ownerUUID);
                compound.remove("OwnerUUID");
            }
            else if(compound.contains("Owner", Tag.TAG_STRING)) {
                UUID ownerUUID = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), compound.getString("Owner"));

                compound.putUUID("Owner", ownerUUID);
            }

            if(NBTUtil.hasOldUniqueId(compound, "LoveCause")) {
                UUID entityUUID = NBTUtil.getOldUniqueId(compound, "LoveCause");

                compound.putUUID("LoveCause", entityUUID);
                NBTUtil.removeOldUniqueId(compound, "LoveCause");
            }
        }
        catch (Exception e) {
            CatHerder.LOGGER.error("Failed to data fix UUIDs: " + e.getMessage());
        }

        try {
            if(compound.contains("Attributes", Tag.TAG_LIST)) {
                ListTag attributeList = compound.getList("Attributes", Tag.TAG_COMPOUND);
                for(int i = 0; i < attributeList.size(); i++) {
                    CompoundTag attributeData = attributeList.getCompound(i);
                    String namePrev = attributeData.getString("Name");
                    Object name = namePrev;

                    switch(namePrev) {
                        case "forge.swimSpeed":
                            name = ForgeMod.SWIM_SPEED;
                            break;
                        case "forge.nameTagDistance":
                            name = ForgeMod.NAMETAG_DISTANCE;
                            break;
                        case "forge.entity_gravity":
                            name = ForgeMod.ENTITY_GRAVITY;
                            break;
                        case "forge.reachDistance":
                            name = ForgeMod.REACH_DISTANCE;
                            break;
                        case "generic.maxHealth":
                            name = Attributes.MAX_HEALTH;
                            break;
                        case "generic.knockbackResistance":
                            name = Attributes.KNOCKBACK_RESISTANCE;
                            break;
                        case "generic.movementSpeed":
                            name = Attributes.MOVEMENT_SPEED;
                            break;
                        case "generic.armor":
                            name = Attributes.ARMOR;
                            break;
                        case "generic.armorToughness":
                            name = Attributes.ARMOR_TOUGHNESS;
                            break;
                        case "generic.followRange":
                            name = Attributes.FOLLOW_RANGE;
                            break;
                        case "generic.attackKnockback":
                            name = Attributes.ATTACK_KNOCKBACK;
                            break;
                        case "generic.attackDamage":
                            name = Attributes.ATTACK_DAMAGE;
                            break;
                        case "generic.jumpStrength":
                            name = ModAttributes.JUMP_POWER;
                            break;
                        case "generic.critChance":
                            name = ModAttributes.CRIT_CHANCE;
                            break;
                        case "generic.critBonus":
                            name = ModAttributes.CRIT_BONUS;
                            break;
                    }

                    ResourceLocation attributeRL = Util.getRegistryId(name);

                    if(attributeRL != null && ForgeRegistries.ATTRIBUTES.containsKey(attributeRL)) {
                        attributeData.putString("Name", attributeRL.toString());
                        ListTag modifierList = attributeData.getList("Modifiers", Tag.TAG_COMPOUND);
                        for(int j = 0; j < modifierList.size(); j++) {
                            CompoundTag modifierData = modifierList.getCompound(j);
                            if(NBTUtil.hasOldUniqueId(modifierData, "UUID")) {
                                UUID entityUUID = NBTUtil.getOldUniqueId(modifierData, "UUID");

                                modifierData.putUUID("UUID", entityUUID);
                                NBTUtil.removeOldUniqueId(modifierData, "UUID");
                            }
                        }
                    }
                    else {
                        CatHerder.LOGGER.warn("Failed to data fix '" + namePrev + "'");
                    }
                }
            }
        }
        catch (Exception e) {
            CatHerder.LOGGER.error("Failed to data fix attribute IDs: " + e.getMessage());
        }

        super.load(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        List<TalentInstance> talentMap = this.getTalentMap();
        talentMap.clear();

        if(compound.contains("talents", Tag.TAG_LIST)) {
            ListTag talentList = compound.getList("talents", Tag.TAG_COMPOUND);

            for(int i = 0; i < talentList.size(); i++) {
                // Add directly so that nothing is lost, if number allowed on changes
                TalentInstance.readInstance(this, talentList.getCompound(i)).ifPresent(talentMap::add);
            }
        }

        this.markDataParameterDirty(TALENTS.get(), false); // Mark dirty so data is synced to client

        List<AccessoryInstance> accessories = this.getAccessories();
        accessories.clear();

        if(compound.contains("accessories", Tag.TAG_LIST)) {
            ListTag accessoryList = compound.getList("accessories", Tag.TAG_COMPOUND);

            for(int i = 0; i < accessoryList.size(); i++) {
                // Add directly so that nothing is lost, if number allowed on changes
                AccessoryInstance.readInstance(accessoryList.getCompound(i)).ifPresent(accessories::add);
            }
        }

        this.markDataParameterDirty(ACCESSORIES.get(), false); // Mark dirty so data is synced to client

        // Does what notifyDataManagerChange would have done but this way only does it once
        this.recalculateAlterationsCache();
        this.spendablePoints.markForRefresh();

        try {
            for(ICatAlteration inst : this.alterations) {
                inst.init(this);
            }
        }
        catch (Exception e) {
            CatHerder.LOGGER.error("Failed to init alteration: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            this.setGender(Gender.bySaveName(compound.getString("catGender")));

            if(compound.contains("mode", Tag.TAG_STRING)) {
                this.setMode(Mode.bySaveName(compound.getString("mode")));
            }

            if(compound.contains("customSkinHash", Tag.TAG_STRING)) {
                this.setSkinHash(compound.getString("customSkinHash"));
            }

            if(compound.contains("fetchItem", Tag.TAG_COMPOUND)) {
                this.setToyVariant(NBTUtil.readItemStack(compound, "fetchItem"));
            }

            this.setHungerDirectly(compound.getFloat("catHunger"));
            this.setOwnersName(NBTUtil.getTextComponent(compound, "lastKnownOwnerName"));
            this.setWillObeyOthers(compound.getBoolean("willObey"));
            this.setCanPlayersAttack(compound.getBoolean("friendlyFire"));
            if(compound.contains("catSize", Tag.TAG_ANY_NUMERIC)) {
                this.setCatSize(compound.getInt("catSize"));
            }
            this.setOriginalBreed(compound.getInt("original_breed"));
        }
        catch (Exception e) {
            CatHerder.LOGGER.error("Failed to load info: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            if(compound.contains("level_normal", Tag.TAG_ANY_NUMERIC)) {
                this.getCatLevel().setLevel(Type.NORMAL, compound.getInt("level_normal"));
                this.markDataParameterDirty(CAT_LEVEL.get());
            }

            if(compound.contains("level_dire", Tag.TAG_ANY_NUMERIC)) {
                this.getCatLevel().setLevel(Type.WILD, compound.getInt("level_dire"));
                this.markDataParameterDirty(CAT_LEVEL.get());
            }
        }
        catch (Exception e) {
            CatHerder.LOGGER.error("Failed to load levels: " + e.getMessage());
            e.printStackTrace();
        }

        // cat tree
        DimensionDependentArg<Optional<BlockPos>> bedsData = this.entityData.get(CAT_TREE_LOCATION.get()).copyEmpty();

        try {
            if(compound.contains("beds", Tag.TAG_LIST)) {
                ListTag bedsList = compound.getList("beds", Tag.TAG_COMPOUND);

                for(int i = 0; i < bedsList.size(); i++) {
                    CompoundTag bedNBT = bedsList.getCompound(i);
                    ResourceLocation loc = NBTUtil.getResourceLocation(bedNBT, "dim");
                    ResourceKey<Level> type = ResourceKey.create(Registry.DIMENSION_REGISTRY, loc);
                    Optional<BlockPos> pos = NBTUtil.getBlockPos(bedNBT, "pos");
                    bedsData.put(type, pos);
                }
            }
        }
        catch (Exception e) {
            CatHerder.LOGGER.error("Failed to load beds: " + e.getMessage());
            e.printStackTrace();
        }

        this.entityData.set(CAT_TREE_LOCATION.get(), bedsData);

        // cat bowl
        DimensionDependentArg<Optional<BlockPos>> bowlsData = this.entityData.get(CAT_BOWL_LOCATION.get()).copyEmpty();

        try {
            if(compound.contains("bowls", Tag.TAG_LIST)) {
                ListTag bowlsList = compound.getList("bowls", Tag.TAG_COMPOUND);

                for(int i = 0; i < bowlsList.size(); i++) {
                    CompoundTag bowlsNBT = bowlsList.getCompound(i);
                    ResourceLocation loc = NBTUtil.getResourceLocation(bowlsNBT, "dim");
                    ResourceKey<Level> type = ResourceKey.create(Registry.DIMENSION_REGISTRY, loc);
                    Optional<BlockPos> pos = NBTUtil.getBlockPos(bowlsNBT, "pos");
                    bowlsData.put(type, pos);
                }
            }
        }
        catch (Exception e) {
            CatHerder.LOGGER.error("Failed to load bowls: " + e.getMessage());
            e.printStackTrace();
        }

        this.entityData.set(CAT_BOWL_LOCATION.get(), bowlsData);

        // litterbox
        DimensionDependentArg<Optional<BlockPos>> litterboxData = this.entityData.get(LITTERBOX_LOCATION.get()).copyEmpty();

        try {
            if(compound.contains("litterbox", Tag.TAG_LIST)) {
                ListTag litterboxList = compound.getList("litterbox", Tag.TAG_COMPOUND);

                for(int i = 0; i < litterboxList.size(); i++) {
                    CompoundTag litterboxNBT = litterboxList.getCompound(i);
                    ResourceLocation loc = NBTUtil.getResourceLocation(litterboxNBT, "dim");
                    ResourceKey<Level> type = ResourceKey.create(Registry.DIMENSION_REGISTRY, loc);
                    Optional<BlockPos> pos = NBTUtil.getBlockPos(litterboxNBT, "pos");
                    litterboxData.put(type, pos);
                }
            }
        }
        catch (Exception e) {
            CatHerder.LOGGER.error("Failed to load litterboxes: " + e.getMessage());
            e.printStackTrace();
        }

        this.entityData.set(LITTERBOX_LOCATION.get(), litterboxData);

        try {
            this.statsTracker.readAdditional(compound);
        }
        catch (Exception e) {
            CatHerder.LOGGER.error("Failed to load stats tracker: " + e.getMessage());
            e.printStackTrace();
        }
        this.alterations.forEach((alter) -> {
            try {
                alter.onRead(this, compound);
            }
            catch (Exception e) {
                CatHerder.LOGGER.error("Failed to load alteration: " + e.getMessage());
                e.printStackTrace();
            }
        });

    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if(TALENTS.get().equals(key) || ACCESSORIES.get().equals(key)) {
            this.recalculateAlterationsCache();

            for(ICatAlteration inst : this.alterations) {
                inst.init(this);
            }
        }

        if(TALENTS.get().equals(key)) {
            this.spendablePoints.markForRefresh();
        }

        if(CAT_LEVEL.get().equals(key)) {
            this.spendablePoints.markForRefresh();
        }

        if(ACCESSORIES.get().equals(key)) {
            // If client sort accessories
            if(this.level.isClientSide) {
                // Does not recall this notifyDataManagerChange as list object is
                // still the same, maybe in future MC versions this will change so need to watch out
                this.getAccessories().sort(AccessoryInstance.RENDER_SORTER);
            }
        }

        if(SIZE.equals(key)) {
            this.refreshDimensions();
        }
    }

    public void recalculateAlterationsCache() {
        this.alterations.clear();
        this.foodHandlers.clear();

        for(AccessoryInstance inst : this.getAccessories()) {
            if(inst instanceof ICatAlteration) {
                this.alterations.add((ICatAlteration) inst);
            }

            if(inst instanceof ICatFoodHandler) {
                this.foodHandlers.add((ICatFoodHandler) inst);
            }
        }

        List<TalentInstance> talents = this.getTalentMap();
        this.alterations.addAll(talents);
        for(TalentInstance inst : talents) {
            if(inst instanceof ICatFoodHandler) {
                this.foodHandlers.add((ICatFoodHandler) inst);
            }
        }
    }

    /**
     * If the entity can make changes to the cat
     *
     * @param livingEntity The entity
     */
    @Override
    public boolean canInteract(LivingEntity livingEntity) {
        return this.willObeyOthers() || this.isOwnedBy(livingEntity);
    }

    @Override
    public List<AccessoryInstance> getAccessories() {
        return this.entityData.get(ACCESSORIES.get());
    }

    @Override
    public boolean addAccessory(@Nonnull AccessoryInstance accessoryInst) {
        List<AccessoryInstance> accessories = this.getAccessories();
        AccessoryType type = accessoryInst.getAccessory().getType();

        // Gets accessories of the same type
        List<AccessoryInstance> filtered = accessories.stream().filter((inst) -> {
            return type == inst.getAccessory().getType();
        }).collect(Collectors.toList());

//        while (!filtered.isEmpty() && filtered.size() >= type.numberToPutOn()) {
//            accessories.remove(filtered.get(0));
//            filtered.remove(0);
//        }

        if(filtered.size() >= type.numberToPutOn()) {
            return false;
        }

        accessories.add(accessoryInst);

        this.markDataParameterDirty(ACCESSORIES.get());

        return true;
    }

    @Override
    public List<AccessoryInstance> removeAccessories() {
        List<AccessoryInstance> removed = new ArrayList<>(this.getAccessories());

        for(AccessoryInstance inst : removed) {
            if(inst instanceof ICatAlteration) {
                ((ICatAlteration) inst).remove(this);
            }
        }

        this.getAccessories().clear();
        this.markDataParameterDirty(ACCESSORIES.get());
        return removed;
    }

    public Optional<AccessoryInstance> getAccessory(AccessoryType typeIn) {
        List<AccessoryInstance> accessories = this.getAccessories();

        for(AccessoryInstance inst : accessories) {
            if(inst.getAccessory().getType() == typeIn) {
                return Optional.of(inst);
            }
        }

        return Optional.empty();
    }

    public Optional<AccessoryInstance> getAccessory(Accessory typeIn) {
        List<AccessoryInstance> accessories = this.getAccessories();

        for(AccessoryInstance inst : accessories) {
            if(inst.getAccessory() == typeIn) {
                return Optional.of(inst);
            }
        }

        return Optional.empty();
    }

    public Optional<Component> getOwnersName() {
        return this.entityData.get(LAST_KNOWN_NAME);
    }

    public void setOwnersName(@Nullable Component comp) {
        this.setOwnersName(Optional.ofNullable(comp));
    }

    public void setOwnersName(Optional<Component> collar) {
        this.entityData.set(LAST_KNOWN_NAME, collar);
    }

    public Gender getGender() {
        return this.entityData.get(GENDER.get());
    }

    public void setGender(Gender collar) {
        this.entityData.set(GENDER.get(), collar);
    }

    @Override
    public Mode getMode() {
        return this.entityData.get(MODE.get());
    }

    public void setMode(Mode collar) {
        this.entityData.set(MODE.get(), collar);
    }

    public boolean isMode(Mode... modes) {
        Mode mode = this.getMode();
        for(Mode test : modes) {
            if(mode == test) {
                return true;
            }
        }

        return false;
    }

    public Optional<BlockPos> getCatTreePos() {
        return this.getCatTreePos(this.level.dimension());
    }

    public void setCatTreePos(@Nullable BlockPos pos) {
        this.setCatTreePos(this.level.dimension(), pos);
    }

    public Optional<BlockPos> getCatTreePos(ResourceKey<Level> registryKey) {
        return this.entityData.get(CAT_TREE_LOCATION.get()).getOrDefault(registryKey, Optional.empty());
    }

    public void setCatTreePos(ResourceKey<Level> registryKey, @Nullable BlockPos pos) {
        this.setCatTreePos(registryKey, WorldUtil.toImmutable(pos));
    }

    public void setCatTreePos(ResourceKey<Level> registryKey, Optional<BlockPos> pos) {
        this.entityData.set(CAT_TREE_LOCATION.get(), this.entityData.get(CAT_TREE_LOCATION.get()).copy().set(registryKey, pos));
    }

    public Optional<BlockPos> getBowlPos() {
        return this.getBowlPos(this.level.dimension());
    }

    public void setBowlPos(@Nullable BlockPos pos) {
        this.setBowlPos(this.level.dimension(), pos);
    }

    public Optional<BlockPos> getBowlPos(ResourceKey<Level> registryKey) {
        return this.entityData.get(CAT_BOWL_LOCATION.get()).getOrDefault(registryKey, Optional.empty());
    }

    public void setBowlPos(ResourceKey<Level> registryKey, @Nullable BlockPos pos) {
        this.setBowlPos(registryKey, WorldUtil.toImmutable(pos));
    }

    public void setBowlPos(ResourceKey<Level> registryKey, Optional<BlockPos> pos) {
        this.entityData.set(CAT_BOWL_LOCATION.get(), this.entityData.get(CAT_BOWL_LOCATION.get()).copy().set(registryKey, pos));
    }

    public Optional<BlockPos> getLitterboxPos() {
        return this.getLitterboxPos(this.level.dimension());
    }

    public void setLitterboxPos(@Nullable BlockPos pos) {
        this.setLitterboxPos(this.level.dimension(), pos);
    }

    public Optional<BlockPos> getLitterboxPos(ResourceKey<Level> registryKey) {
        return this.entityData.get(LITTERBOX_LOCATION.get()).getOrDefault(registryKey, Optional.empty());
    }

    public void setLitterboxPos(ResourceKey<Level> registryKey, @Nullable BlockPos pos) {
        this.setLitterboxPos(registryKey, WorldUtil.toImmutable(pos));
    }

    public void setLitterboxPos(ResourceKey<Level> registryKey, Optional<BlockPos> pos) {
        this.entityData.set(LITTERBOX_LOCATION.get(), this.entityData.get(LITTERBOX_LOCATION.get()).copy().set(registryKey, pos));
    }

    @Override
    public float getMaxHunger() {
        float maxHunger = ConfigHandler.DEFAULT_MAX_HUNGER;

        for(ICatAlteration alter : this.alterations) {
            InteractionResultHolder<Float> result = alter.getMaxHunger(this, maxHunger);

            if(result.getResult().shouldSwing()) {
                maxHunger = result.getObject();
            }
        }

        return maxHunger;
    }

    @Override
    public float getCatHunger() {
        return this.entityData.get(HUNGER_INT);
    }

    @Override
    public void setCatHunger(float hunger) {
        float diff = hunger - this.getCatHunger();

        for(ICatAlteration alter : this.alterations) {
            InteractionResultHolder<Float> result = alter.setCatHunger(this, hunger, diff);

            if(result.getResult().shouldSwing()) {
                hunger = result.getObject();
                diff = hunger - this.getCatHunger();
            }
        }

        this.setHungerDirectly(Mth.clamp(hunger, 0, this.getMaxHunger()));
    }

    @Override
    public void addHunger(float add) {
        this.setCatHunger(this.getCatHunger() + add);
    }

    private void setHungerDirectly(float hunger) {
        this.entityData.set(HUNGER_INT, hunger);
    }

    public boolean hasCustomSkin() {
        return !Strings.isNullOrEmpty(this.getSkinHash());
    }

    public String getSkinHash() {
        return this.entityData.get(CUSTOM_SKIN);
    }

    public void setSkinHash(String hash) {
        if(hash == null) {
            hash = "";
        }
        this.entityData.set(CUSTOM_SKIN, hash);
    }

    @Override
    public CatLevel getCatLevel() {
        return this.entityData.get(CAT_LEVEL.get());
    }

    public void setLevel(CatLevel level) {
        this.entityData.set(CAT_LEVEL.get(), level);
    }

    @Override
    public void increaseLevel(CatLevel.Type typeIn) {
        this.getCatLevel().incrementLevel(typeIn);
        this.markDataParameterDirty(CAT_LEVEL.get());
    }

    @Override
    public int getCatSize() {
        return this.entityData.get(SIZE);
    }

    @Override
    public void setCatSize(int value) {
        this.entityData.set(SIZE, (byte) Math.min(MAX_CAT_SIZE, Math.max(MIN_CAT_SIZE, value)));
    }

    public ItemStack getToyVariant() {
        return this.entityData.get(TOY_VARIANT);
    }

    public void setToyVariant(ItemStack stack) {
        this.entityData.set(TOY_VARIANT, stack);
    }

    @Nullable
    public IThrowableItem getThrowableItem() {
        Item item = this.entityData.get(TOY_VARIANT).getItem();
        return item instanceof IThrowableItem ? (IThrowableItem) item : null;
    }

    public boolean hasToy() {
        return !this.getToyVariant().isEmpty();
    }

    private boolean getCatFlag(int bit) {
        return (this.entityData.get(CAT_FLAGS) & bit) != 0;
    }

    private void setCatFlag(int bits, boolean flag) {
        byte c = this.entityData.get(CAT_FLAGS);
        this.entityData.set(CAT_FLAGS, (byte) (flag ? c | bits : c & ~bits));
    }

    public boolean isBegging() {
        return this.getCatFlag(1);
    }

    public void setBegging(boolean begging) {
        this.setCatFlag(1, begging);
    }

    public void setWillObeyOthers(boolean obeyOthers) {
        this.setCatFlag(2, obeyOthers);
    }

    public boolean willObeyOthers() {
        return this.getCatFlag(2);
    }

    public void setCanPlayersAttack(boolean flag) {
        this.setCatFlag(4, flag);
    }

    public boolean canPlayersAttack() {
        return this.getCatFlag(4);
    }

    public boolean get8Flag() {
        return this.getCatFlag(8);
    }

    public void set8Flag(boolean collar) {
        this.setCatFlag(8, collar);
    }

    public void setHasSunglasses(boolean sunglasses) {
        this.setCatFlag(16, sunglasses);
    }

    public boolean hasSunglasses() {
        return this.getCatFlag(16);
    }

    public boolean isLyingDown() {
        return this.getCatFlag(32);
    }

    public void setLyingDown(boolean lying) {
        this.setCatFlag(32, lying);
    }

    public boolean get64Flag() {
        return this.getCatFlag(64);
    }

    public void set64Flag(boolean lying) {
        this.setCatFlag(64, lying);
    }

    public List<TalentInstance> getTalentMap() {
        return this.entityData.get(TALENTS.get());
    }

    public void setTalentMap(List<TalentInstance> map) {
        this.entityData.set(TALENTS.get(), map);
    }

    public InteractionResult adjustTalentLevel(Talent talent, int adjustment) {

        int currentLevel = this.getCatLevel(talent);
        int newLevel = currentLevel + adjustment;

        int levelCost = 0;
        if(adjustment < 0) {
            // handle refund of points
            levelCost = talent.getLevelCost(currentLevel);
            if(newLevel < 0) {
                return InteractionResult.FAIL;
            }
        }
        else {
            levelCost = talent.getLevelCost(newLevel);
            if(newLevel > talent.getMaxLevel() ||
                    !this.canSpendPoints(levelCost)) {
                return InteractionResult.FAIL;
            }
        }

        List<TalentInstance> activeTalents = this.getTalentMap();

        // find an instance of the talent
        TalentInstance inst = null;
        for(TalentInstance activeInst : activeTalents) {
            if(activeInst.of(talent)) {
                inst = activeInst;
                break;
            }
        }

        // if there is no instance...
        if(inst == null) {
            if(newLevel == 0) {
                // ... do nothing, because the level is 0
                return InteractionResult.PASS;
            }

            // create the talent and add it to the list of active talents
            inst = talent.getDefault(newLevel);
            activeTalents.add(inst);
            inst.init(this);
        }
        else {
            // get the current level of the talent
            int previousLevel = inst.level();
            if(previousLevel == newLevel) {
                // if the level isn't changing, do nothing
                return InteractionResult.PASS;
            }

            // set the new level
            inst.setLevel(newLevel);
            inst.set(this, previousLevel);

            if(newLevel == 0) {
                // remove the talent if the level becomes 0
                activeTalents.remove(inst);
            }
        }

        this.markDataParameterDirty(TALENTS.get());
        return InteractionResult.SUCCESS;
    }

    public int getLitterboxCooldown() {
        return litterboxCooldown;
    }

    public void setLitterboxCooldown(int litterboxCooldown) {
        this.litterboxCooldown = litterboxCooldown;
    }

    public <T> void markDataParameterDirty(EntityDataAccessor<T> key) {
        this.markDataParameterDirty(key, true);
    }

    public <T> void markDataParameterDirty(EntityDataAccessor<T> key, boolean notify) {
        if(notify) {
            this.onSyncedDataUpdated(key);
        }

        // Force the entry to update
        DataItem<T> dataentry = this.entityData.getItem(key);
        dataentry.setDirty(true);
        this.entityData.isDirty = true;
    }

    @Override
    public void markAccessoriesDirty() {
        this.markDataParameterDirty(ACCESSORIES.get());
    }

    @Override
    public Optional<TalentInstance> getTalent(Talent talentIn) {
        List<TalentInstance> activeTalents = this.getTalentMap();

        for(TalentInstance activeInst : activeTalents) {
            if(activeInst.of(talentIn)) {
                return Optional.of(activeInst);
            }
        }

        return Optional.empty();
    }

    @Override
    public int getCatLevel(Talent talentIn) {
        return this.getTalent(talentIn).map(TalentInstance::level).orElse(0);
    }

    @Override
    public <T> void setData(DataKey<T> key, T value) {
        if(key.isFinal() && this.hasData(key)) {
            throw new RuntimeException("Key is final but was tried to be set again.");
        }
        this.objects.put(key.getIndex(), value);
    }

    /**
     * Tries to put the object in the map, does nothing if the key already exists
     */
    @Override
    public <T> void setDataIfEmpty(DataKey<T> key, T value) {
        if(!this.hasData(key)) {
            this.objects.put(key.getIndex(), value);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getData(DataKey<T> key) {
        return (T) this.objects.get(key.getIndex());
    }

    @Override
    public <T> T getDataOrGet(DataKey<T> key, Supplier<T> other) {
        if(this.hasData(key)) {
            return this.getData(key);
        }
        return other.get();
    }

    @Override
    public <T> T getDataOrDefault(DataKey<T> key, T other) {
        if(this.hasData(key)) {
            return this.getData(key);
        }
        return other;
    }

    @Override
    public <T> boolean hasData(DataKey<T> key) {
        return this.objects.containsKey(key.getIndex());
    }

    @Override
    public void untame() {
        this.setTame(false);
        this.navigation.stop();
        this.setOrderedToSit(false);
        this.setHealth(8);

        this.getTalentMap().clear();
        this.markDataParameterDirty(TALENTS.get());

        this.setOwnerUUID(null);
        this.setWillObeyOthers(false);
        this.setMode(Mode.DOCILE);
    }

    public boolean canSpendPoints(int amount) {
        return this.getSpendablePoints() >= amount || this.getAccessory(ModAccessories.GOLDEN_COLLAR.get()).isPresent();
    }

    // When this method is changed the cache may need to be updated at certain points
    private final int getSpendablePointsInternal() {
        int totalPoints = 15 + this.getCatLevel().getLevel(Type.NORMAL) + this.getCatLevel().getLevel(Type.WILD);
        for(TalentInstance entry : this.getTalentMap()) {
            totalPoints -= entry.getTalent().getCumulativeCost(entry.level());
        }
        return totalPoints;
    }

    public int getSpendablePoints() {
        return this.spendablePoints.get();
    }

    @Override
    public boolean canRiderInteract() {
        return true;
    }

    @Override
    public Entity getControllingPassenger() {
        // Gets the first passenger which is the controlling passenger
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    @Override
    public boolean canBeControlledByRider() {
        return this.getControllingPassenger() instanceof LivingEntity;
    }

    //TODO
    @Override
    public boolean isPickable() {
        return super.isPickable();
    }

    @Override
    public boolean isPushable() {
        return !this.isVehicle() && super.isPushable();
    }

    @Override
    public boolean isControlledByLocalInstance() {
        // Super calls canBeSteered so controlling passenger can be guaranteed to be LivingEntity
        return super.isControlledByLocalInstance() && this.canInteract((LivingEntity) this.getControllingPassenger());
    }

    public boolean isCatJumping() {
        return this.catJumping;
    }

    public void setCatJumping(boolean jumping) {
        this.catJumping = jumping;
    }

//    public double getCatJumpStrength() {
//        float verticalVelocity = 0.42F + 0.06F * this.TALENTS.getLevel(ModTalents.WOLF_MOUNT);
//        if (this.TALENTS.getLevel(ModTalents.WOLF_MOUNT) == 5) verticalVelocity += 0.04F;
//        return verticalVelocity;
//    }

    // 0 - 100 input
    public void setJumpPower(int jumpPowerIn) {
        // if (this.TALENTS.getLevel(ModTalents.WOLF_MOUNT) > 0) {
        this.jumpPower = 1.0F;
        // }
    }

    public boolean canJump() {
        return true;
        //TODO return this.TALENTS.getLevel(ModTalents.WOLF_MOUNT) > 0;
    }

    @Override
    public void travel(Vec3 positionIn) {
        if(this.isAlive()) {
            if(this.isVehicle() && this.canBeControlledByRider()) {
                LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();

                // Face the cat in the direction of the controlling passenger
                this.setYRot(livingentity.getYRot());
                this.yRotO = this.getYRot();
                this.setXRot(livingentity.getXRot() * 0.5F);
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;

                this.maxUpStep = 1.0F;

                float straf = livingentity.xxa * 0.7F;
                float foward = livingentity.zza;

                // If moving backwards half the speed
                if(foward <= 0.0F) {
                    foward *= 0.5F;
                }

                if(this.jumpPower > 0.0F && !this.isCatJumping() && this.isOnGround()) {

                    // Calculate jump value based of jump strength, power this jump and jump boosts
                    double jumpValue = this.getAttribute(ModAttributes.JUMP_POWER.get()).getValue() * this.getBlockJumpFactor() * this.jumpPower; //TODO do we want getJumpFactor?
                    if(this.hasEffect(MobEffects.JUMP)) {
                        jumpValue += (this.getEffect(MobEffects.JUMP).getAmplifier() + 1) * 0.1F;
                    }

                    // Apply jump
                    Vec3 vec3d = this.getDeltaMovement();
                    this.setDeltaMovement(vec3d.x, jumpValue, vec3d.z);
                    this.setCatJumping(true);
                    this.hasImpulse = true;

                    // If moving forward, propel further in the direction
                    if(foward > 0.0F) {
                        final float amount = 0.4F; // TODO Allow people to change this value
                        float compX = Mth.sin(this.getYRot() * ((float) Math.PI / 180F));
                        float compZ = Mth.cos(this.getYRot() * ((float) Math.PI / 180F));
                        this.setDeltaMovement(this.getDeltaMovement().add(-amount * compX * this.jumpPower, 0.0D, amount * compZ * this.jumpPower));
                        //this.playJumpSound();
                    }

                    // Mark as unable jump until reset
                    this.jumpPower = 0.0F;
                }

                this.flyingSpeed = this.getSpeed() * 0.1F;
                if(this.isControlledByLocalInstance()) {
                    // Set the move speed and move the cat in the direction of the controlling entity
                    this.setSpeed((float) this.getAttribute(Attributes.MOVEMENT_SPEED).getValue() * 0.5F);
                    super.travel(new Vec3(straf, positionIn.y, foward));
                    this.lerpSteps = 0;
                }
                else if(livingentity instanceof Player) {
                    // A player is riding and can not control then
                    this.setDeltaMovement(Vec3.ZERO);
                }

                // Once the entity reaches the ground again allow it to jump again
                if(this.isOnGround()) {
                    this.jumpPower = 0.0F;
                    this.setCatJumping(false);
                }

                //
                this.animationSpeedOld = this.animationSpeed;
                double changeX = this.getX() - this.xo;
                double changeY = this.getZ() - this.zo;
                float f4 = Mth.sqrt((float) (changeX * changeX + changeY * changeY)) * 4.0F;

                if(f4 > 1.0F) {
                    f4 = 1.0F;
                }

                this.animationSpeed += (f4 - this.animationSpeed) * 0.4F;
                this.animationPosition += this.animationSpeed;

                if(this.onClimbable()) {
                    this.fallDistance = 0.0F;
                }
            }
            else {
                this.maxUpStep = 0.5F; // Default
                this.flyingSpeed = 0.02F; // Default
                super.travel(positionIn);
            }

            this.addMovementStat(this.getX() - this.xo, this.getY() - this.yo, this.getZ() - this.zo);
        }
    }

    public void addMovementStat(double xD, double yD, double zD) {
        if(this.isVehicle()) {
            int j = Math.round(Mth.sqrt((float) (xD * xD + zD * zD)) * 100.0F);
            this.statsTracker.increaseDistanceRidden(j);
        }
        if(!this.isPassenger()) {
            if(this.isEyeInFluid(FluidTags.WATER)) {
                int j = Math.round(Mth.sqrt((float) (xD * xD + yD * yD + zD * zD)) * 100.0F);
                if(j > 0) {
                    this.statsTracker.increaseDistanceOnWater(j);
                }
            }
            else if(this.isInWater()) {
                int k = Math.round(Mth.sqrt((float) (xD * xD + zD * zD)) * 100.0F);
                if(k > 0) {
                    this.statsTracker.increaseDistanceInWater(k);
                }
            }
            else if(this.isOnGround()) {
                int l = Math.round(Mth.sqrt((float) (xD * xD + zD * zD)) * 100.0F);
                if(l > 0) {
                    if(this.isSprinting()) {
                        this.statsTracker.increaseDistanceSprint(l);
                    }
                    else if(this.isCrouching()) {
                        this.statsTracker.increaseDistanceSneaking(l);
                    }
                    else {
                        this.statsTracker.increaseDistanceWalk(l);
                    }
                }
            }
            else { // Time in air
                int j1 = Math.round(Mth.sqrt((float) (xD * xD + zD * zD)) * 100.0F);
                //this.STATS.increaseDistanceInWater(k);
            }


        }
    }

    @Override
    public TranslatableComponent getTranslationKey(Function<Gender, String> function) {
        return new TranslatableComponent(function.apply(ConfigHandler.SERVER.CAT_GENDER.get() ? this.getGender() : Gender.UNISEX));
    }

    public boolean isHungry() {
        // Happy Eater is hungry at 25%
        // all others at 40%
        float hungryThreshold = 40;
        Optional<TalentInstance> happyEater = this.getTalent(ModTalents.HAPPY_EATER.get());
        if(happyEater.isPresent()) {
            hungryThreshold = 25f - (happyEater.get().level() * 2.5f);
        }

        return ((getCatHunger() / getMaxHunger()) * 100) < hungryThreshold;
    }

    @Override
    public boolean isLying() {
        LivingEntity owner = this.getOwner();
        boolean ownerSleeping = owner != null && owner.isSleeping();
        if(ownerSleeping) {
            return true;
        }

        BlockState blockBelow = this.level.getBlockState(this.blockPosition().below());
        boolean onBed = /* TODO: remove temporarily blockBelow.is(ModBlocks.CAT_TREE.get()) || */ blockBelow.is(BlockTags.BEDS);
        return onBed;
    }

    @Override
    public List<ICatFoodHandler> getFoodHandlers() {
        return this.foodHandlers;
    }

    public BlockPos getTargetBlock() {
        return this.targetBlock;
    }

    public void setTargetBlock(BlockPos pos) {
        this.targetBlock = pos;
    }

    public int getOriginalBreed() {
        return this.entityData.get(ORIGINAL_BREED_INT);
    }

    public void setOriginalBreed(int originalBreed) {
        this.entityData.set(ORIGINAL_BREED_INT, originalBreed);
    }

    static class CatRelaxOnOwnerGoal extends Goal {
        private final CatEntity cat;
        @Nullable
        private Player ownerPlayer;
        @Nullable
        private BlockPos goalPos;
        private int onBedTicks;

        public CatRelaxOnOwnerGoal(CatEntity cat) {
            this.cat = cat;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            /* if (!this.cat.isTame()) {
                return false;
            }
            else */
            if(this.cat.isOrderedToSit()) {
                return false;
            }
            else {
                LivingEntity livingentity = this.cat.getOwner();
                if(livingentity instanceof Player) {
                    this.ownerPlayer = (Player) livingentity;
                    if(!livingentity.isSleeping()) {
                        return false;
                    }

                    if(this.cat.distanceToSqr(this.ownerPlayer) > 100.0D) {
                        return false;
                    }

                    BlockPos blockPos = this.ownerPlayer.blockPosition();
                    BlockState blockState = this.cat.level.getBlockState(blockPos);
                    if(blockState.is(BlockTags.BEDS)) {
                        this.goalPos = blockState.getOptionalValue(BedBlock.FACING).map((p_28209_) -> {
                            return blockPos.relative(p_28209_.getOpposite());
                        }).orElseGet(() -> {
                            return new BlockPos(blockPos);
                        });
                        return !this.spaceIsOccupied();
                    }
                }

                return false;
            }
        }

        private boolean spaceIsOccupied() {
            for(CatEntity cat : this.cat.level.getEntitiesOfClass(CatEntity.class, (new AABB(this.goalPos)).inflate(2.0D))) {
                if(cat != this.cat && (cat.isLying() || cat.isRelaxStateOne())) {
                    return true;
                }
            }

            return false;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return this.cat.isTame() &&
                    !this.cat.isOrderedToSit() &&
                    this.ownerPlayer != null &&
                    this.ownerPlayer.isSleeping() &&
                    this.goalPos != null && !this.spaceIsOccupied();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            if(this.goalPos != null) {
                this.cat.setInSittingPose(false);
                this.cat.getNavigation().moveTo(this.goalPos.getX(), this.goalPos.getY(), this.goalPos.getZ(), 1.1F);
            }

        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
//            this.cat.setLying(false);
            float f = this.cat.level.getTimeOfDay(1.0F);
            if(this.ownerPlayer.getSleepTimer() >= 100 && (double) f > 0.77D && (double) f < 0.8D && (double) this.cat.level.getRandom().nextFloat() < 0.7D) {
                this.giveMorningGift();
            }

            this.onBedTicks = 0;
            this.cat.setRelaxStateOne(false);
            this.cat.getNavigation().stop();
        }

        private void giveMorningGift() {
            Random random = this.cat.getRandom();
            BlockPos.MutableBlockPos blockPos$mutableBlockPos = new BlockPos.MutableBlockPos();
            blockPos$mutableBlockPos.set(this.cat.blockPosition());
            this.cat.randomTeleport(blockPos$mutableBlockPos.getX() + random.nextInt(11) - 5, blockPos$mutableBlockPos.getY() + random.nextInt(5) - 2, blockPos$mutableBlockPos.getZ() + random.nextInt(11) - 5, false);
            blockPos$mutableBlockPos.set(this.cat.blockPosition());
            LootTable loottable = this.cat.level.getServer().getLootTables().get(BuiltInLootTables.CAT_MORNING_GIFT);
            LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerLevel) this.cat.level)).withParameter(LootContextParams.ORIGIN, this.cat.position()).withParameter(LootContextParams.THIS_ENTITY, this.cat).withRandom(random);

            for(ItemStack itemstack : loottable.getRandomItems(lootcontext$builder.create(LootContextParamSets.GIFT))) {
                this.cat.level.addFreshEntity(new ItemEntity(this.cat.level, (double) blockPos$mutableBlockPos.getX() - (double) Mth.sin(this.cat.yBodyRot * ((float) Math.PI / 180F)), blockPos$mutableBlockPos.getY(), (double) blockPos$mutableBlockPos.getZ() + (double) Mth.cos(this.cat.yBodyRot * ((float) Math.PI / 180F)), itemstack));
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if(this.ownerPlayer != null && this.goalPos != null) {
                this.cat.setInSittingPose(false);
                this.cat.getNavigation().moveTo(this.goalPos.getX(), this.goalPos.getY(), this.goalPos.getZ(), 1.1F);
                if(this.cat.distanceToSqr(this.ownerPlayer) < 2.5D) {
                    ++this.onBedTicks;
                    if(this.onBedTicks > this.adjustedTickDelay(16)) {
//                        this.cat.setLying(true);
                        this.cat.setRelaxStateOne(false);
                    }
                    else {
                        this.cat.lookAt(this.ownerPlayer, 45.0F, 45.0F);
                        this.cat.setRelaxStateOne(true);
                    }
                }
                else {
//                    this.cat.setLying(false);
                }
            }

        }
    }

}
