package kittytalents.common.entity.ai;

import kittytalents.api.feature.EnumMode;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;

public class GuardModeGoal extends NearestAttackableTargetGoal<Monster> {

    private final kittytalents.common.entity.CatEntity cat;
    private LivingEntity owner;

    public GuardModeGoal(kittytalents.common.entity.CatEntity dogIn, boolean checkSight) {
        super(dogIn, Monster.class, 0, checkSight, false, null);
        this.cat = dogIn;
    }

    @Override
    public boolean canUse() {
        LivingEntity owner = this.cat.getOwner();
        if (owner == null) {
            return false;
        }

        if (!this.cat.isMode(EnumMode.GUARD)) {
            return false;
        }

        this.owner = owner;

        if (super.canUse()) {
            this.owner = owner;
            return true;
        }

        return false;
    }

    @Override
    protected double getFollowDistance() {
        return 6D;
    }

    @Override
    protected void findTarget() {
       this.target = this.cat.level.getNearestEntity(this.targetType, this.targetConditions, this.cat, this.owner.getX(), this.owner.getEyeY(), this.owner.getZ(), this.getTargetSearchArea(this.getFollowDistance()));
    }
}
