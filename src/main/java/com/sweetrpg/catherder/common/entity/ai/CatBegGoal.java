package com.sweetrpg.catherder.common.entity.ai;

import com.sweetrpg.catherder.api.feature.FoodHandler;
import com.sweetrpg.catherder.common.entity.CatEntity;
import com.sweetrpg.catherder.common.registry.ModTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.EnumSet;

public class CatBegGoal extends Goal {

    private final CatEntity cat;
    private Player player;
    private final Level world;
    private final float minPlayerDistance;
    private int timeoutCounter;
    private final TargetingConditions playerPredicate;

    public CatBegGoal(CatEntity vanillaCat, float minDistance) {
        this.cat = vanillaCat;
        this.world = vanillaCat.level;
        this.minPlayerDistance = minDistance;
        this.playerPredicate = TargetingConditions.forNonCombat().range(minDistance); // TODO check
        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        this.player = this.world.getNearestPlayer(this.playerPredicate, this.cat);
        return this.player != null && this.hasTemptationItemInHand(this.player);
    }

    @Override
    public boolean canContinueToUse() {
        if(!this.player.isAlive()) {
            return false;
        }
        else if(this.cat.distanceToSqr(this.player) > this.minPlayerDistance * this.minPlayerDistance) {
            return false;
        }
        else {
            return this.timeoutCounter > 0 && this.hasTemptationItemInHand(this.player);
        }
    }

    @Override
    public void start() {
        this.cat.setBegging(true);
        this.timeoutCounter = 40 + this.cat.getRandom().nextInt(40);
    }

    @Override
    public void stop() {
        this.cat.setBegging(false);
        this.player = null;
    }

    @Override
    public void tick() {
        this.cat.getLookControl().setLookAt(this.player.getX(), this.player.getEyeY(), this.player.getZ(), 10.0F, this.cat.getMaxHeadXRot());
        --this.timeoutCounter;
    }

    private boolean hasTemptationItemInHand(Player player) {
        for(InteractionHand hand : InteractionHand.values()) {
            ItemStack itemstack = player.getItemInHand(hand);
            if(itemstack.is(this.cat.isTame() ? ModTags.BEG_ITEMS_TAMED : ModTags.BEG_ITEMS_UNTAMED)) {
                return true;
            }

            if(itemstack.is(ModTags.TREATS)) {
                return true;
            }

            if(FoodHandler.isFood(itemstack).isPresent()) {
                return true;
            }

            if(this.cat.isFood(itemstack)) {
                return true;
            }
        }

        return false;
    }
}
