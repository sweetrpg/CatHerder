package kittytalents.common.talent;

import kittytalents.KittyItems;
import kittytalents.KittyTalents;
import kittytalents.api.feature.DataKey;
import kittytalents.api.feature.EnumMode;
import kittytalents.api.registry.Talent;
import kittytalents.api.registry.TalentInstance;
import kittytalents.common.util.EntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;

public class ShepherdCatTalent extends TalentInstance {

    private static DataKey<EntityAIShepherdDog> SHEPHERD_AI = DataKey.make();

    public ShepherdCatTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void init(kittytalents.api.inferface.AbstractCatEntity dogIn) {
        if (!dogIn.hasData(SHEPHERD_AI)) {
            EntityAIShepherdDog shepherdAI = new EntityAIShepherdDog(dogIn, 1.0D, 8F, entity -> !(entity instanceof TamableAnimal));
            dogIn.goalSelector.addGoal(7, shepherdAI);
            dogIn.setData(SHEPHERD_AI, shepherdAI);
        }
    }

    public static int getMaxFollowers(int level) {
        switch(level) {
        case 1:
            return 1;
        case 2:
            return 2;
        case 3:
            return 4;
        case 4:
            return 8;
        case 5:
            return 16;
        default:
            return 0;
        }
    }

    public static class EntityAIShepherdDog extends Goal {

        protected final kittytalents.api.inferface.AbstractCatEntity cat;
        private final Level world;
        private final double followSpeed;
        private final float maxDist;
        private final PathNavigation dogPathfinder;
        private final Predicate<ItemStack> holdingPred;
        private final Predicate<Animal> predicate;
        private final Comparator<Entity> sorter;


        private int timeToRecalcPath;
        private LivingEntity owner;
        protected List<Animal> targets;
        private float oldWaterCost;

        private int MAX_FOLLOW = 5;

        public EntityAIShepherdDog(kittytalents.api.inferface.AbstractCatEntity dogIn, double speedIn, float range, @Nullable Predicate<Animal> targetSelector) {
            this.cat = dogIn;
            this.world = dogIn.level;
            this.dogPathfinder = dogIn.getNavigation();
            this.followSpeed = speedIn;
            this.maxDist = range;
            this.predicate = (entity) -> {
                double d0 = EntityUtil.getFollowRange(this.cat);
                if (entity.isInvisible()) {
                    return false;
                }
                else if (targetSelector != null && !targetSelector.test(entity)) {
                    return false;
                } else {
                    return entity.distanceTo(this.cat) > d0 ? false : entity.hasLineOfSight(this.cat);
                }
            };
            this.holdingPred = (stack) -> {
                return stack.getItem() == KittyItems.WHISTLE.get(); // TODO
            };

            this.sorter = new EntityUtil.Sorter(dogIn);
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (this.cat.getMode() != EnumMode.DOCILE) {
                return false;
            } else if (this.cat.getDogLevel(KittyTalents.SHEPHERD_DOG) <= 0) {
                return false;
            } else {
                LivingEntity owner = this.cat.getOwner();
                if (owner == null) {
                   return false;
                } else if (owner instanceof Player && ((Player) owner).isSpectator()) {
                    return false;
                } else if (!EntityUtil.isHolding(owner, KittyItems.WHISTLE.get(), (nbt) -> nbt.contains("mode") && nbt.getInt("mode") == 4)) {
                    return false;
                } else {
                    List<Animal> list = this.world.getEntitiesOfClass(Animal.class, this.cat.getBoundingBox().inflate(12D, 4.0D, 12D), this.predicate);
                    Collections.sort(list, this.sorter);
                    if (list.isEmpty()) {
                        return false;
                    }
                    else {



                        this.MAX_FOLLOW = kittytalents.common.talent.ShepherdCatTalent.getMaxFollowers(this.cat.getDogLevel(KittyTalents.SHEPHERD_DOG));
                        this.targets = list.subList(0, Math.min(MAX_FOLLOW, list.size()));
                        this.owner = owner;
                        return true;
                    }
                }
            }
        }

        @Override
        public boolean canContinueToUse() {
            if (this.cat.getMode() != EnumMode.DOCILE) {
                return false;
            } else if (this.cat.getDogLevel(KittyTalents.SHEPHERD_DOG) <= 0) {
                return false;
            } else if (!EntityUtil.isHolding(owner, KittyItems.WHISTLE.get(), (nbt) -> nbt.contains("mode") && nbt.getInt("mode") == 4)) {
                return false;
            } else if (this.targets.isEmpty()) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public void start() {
            this.timeToRecalcPath = 0;
            this.oldWaterCost = this.cat.getPathfindingMalus(BlockPathTypes.WATER);
            this.cat.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        }

        @Override
        public void tick() {
            if (!this.cat.isInSittingPose()) {

                if (--this.timeToRecalcPath <= 0) {
                    this.timeToRecalcPath = 10;

                    // Pick up more animals
                    if (this.targets.size() < MAX_FOLLOW) {
                        List<Animal> list = this.world.getEntitiesOfClass(Animal.class,
                                this.cat.getBoundingBox().inflate(16, 4.0D, 16), this.predicate);
                        list.removeAll(this.targets);
                        Collections.sort(list, this.sorter);

                        this.targets.addAll(list.subList(0, Math.min(MAX_FOLLOW - this.targets.size(), list.size())));
                    }

                    Collections.sort(this.targets, this.sorter);
                    boolean teleport = this.owner.distanceTo(this.targets.get(0)) > 16;

                    for (Animal target : this.targets) {
                        //target.goalSelector.addGoal(0, new );

                        double distanceAway = target.distanceTo(this.owner);
                        target.getLookControl().setLookAt(this.owner, 10.0F, target.getMaxHeadXRot());
                        if (teleport) {
                            if (!target.isLeashed() && !target.isPassenger()) {
                                EntityUtil.tryToTeleportNearEntity(target, target.getNavigation(), this.owner, 4);
                            }
                        }
                        else if (distanceAway >= 5) {
                            if (!target.getNavigation().moveTo(this.owner, 1.2D)) {
                                if (!target.isLeashed() && !target.isPassenger() && distanceAway >= 20) {
                                    EntityUtil.tryToTeleportNearEntity(target, target.getNavigation(), this.owner, 4);
                                }
                            }
                        }
                        else {
                            target.getNavigation().stop();
                        }
                    }

                    Vec3 vec = Vec3.ZERO;

                    // Calculate average pos of targets
                    for (Animal target : this.targets) {
                        vec = vec.add(target.position());
                    }

                    vec = vec.scale(1D / this.targets.size());

                    double dPosX = vec.x - this.owner.getX();
                    double dPosZ = vec.z - this.owner.getZ();
                    double size = Math.sqrt(dPosX * dPosX + dPosZ * dPosZ);
                    double j3 = vec.x + dPosX / size * (2 + this.targets.size() / 16);
                    double k3 = vec.z + dPosZ / size * (2 + this.targets.size() / 16);

                    if (teleport) {
                        EntityUtil.tryToTeleportNearEntity(this.cat, this.dogPathfinder, new BlockPos(j3, this.cat.getY(), k3), 1);
                    }

                    this.cat.getLookControl().setLookAt(this.owner, 10.0F, this.cat.getMaxHeadXRot());
                    if (!this.dogPathfinder.moveTo(j3, this.owner.getBoundingBox().minY, k3, this.followSpeed)) {
                        if (this.cat.distanceToSqr(j3, this.owner.getBoundingBox().minY, k3) > 144D) {
                            if (!this.cat.isLeashed() && !this.cat.isPassenger()) {
                                EntityUtil.tryToTeleportNearEntity(this.cat, this.dogPathfinder, new BlockPos(j3, this.cat.getY(), k3), 4);
                            }
                        }
                    }

                    if (this.cat.distanceTo(this.owner) > 40) {
                        EntityUtil.tryToTeleportNearEntity(this.cat, this.dogPathfinder, this.owner, 2);
                    }
                    // Play woof sound
                    if (this.cat.getRandom().nextFloat() < 0.15F) {
                        this.cat.playSound(SoundEvents.WOLF_AMBIENT, this.cat.getSoundVolume() + 1.0F, (this.cat.getRandom().nextFloat() - this.cat.getRandom().nextFloat()) * 0.1F + 0.9F);
                    }

                    // Remove dead or faraway entities
                    List<Animal> toRemove = new ArrayList<>();
                    for (Animal target : this.targets) {
                        if (!target.isAlive() || target.distanceTo(this.cat) > 25D)
                            toRemove.add(target);
                    }
                    this.targets.removeAll(toRemove);
                }
            }
        }

        @Override
        public void stop() {
            this.owner = null;
            for (Animal target : this.targets) {
                target.getNavigation().stop();
            }
            this.dogPathfinder.stop();
            this.cat.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
        }
    }
}
