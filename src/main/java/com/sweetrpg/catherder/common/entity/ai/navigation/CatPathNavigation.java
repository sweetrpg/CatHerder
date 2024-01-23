package com.sweetrpg.catherder.common.entity.ai.navigation;

import com.sweetrpg.catherder.common.entity.CatEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;

public class CatPathNavigation extends GroundPathNavigation {

    private CatEntity cat;

    public CatPathNavigation(CatEntity cat, Level level) {
        super(cat, level);
        this.cat = cat;
    }

    @Override
    protected void followThePath() {
        if (invalidateIfNextNodeIsTooHigh()) return;

        var currentPos = this.getTempMobPos();
        this.maxDistanceToWaypoint =
                this.mob.getBbWidth() > 0.15F ?
                        this.mob.getBbWidth() / 2.0F
                        : 0.15F - this.mob.getBbWidth() / 2.0F;

        var nextPos = this.path.getNextNodePos();
        double dx = Math.abs(this.mob.getX() - ((double)nextPos.getX() + 0.5));
        double dy = Math.abs(this.mob.getY() - (double)nextPos.getY());
        double dz = Math.abs(this.mob.getZ() - ((double)nextPos.getZ() + 0.5));

        boolean isCloseEnough =
                dx <= (double)this.maxDistanceToWaypoint
                        && dy < 1.0D
                        && dz <= (double)this.maxDistanceToWaypoint;
        boolean canCutCorner =
                this.canCutCorner(this.path.getNextNode().type)
                        && this.shouldTargetNextNodeInDirection(currentPos);

        if (isCloseEnough || canCutCorner) {
            this.path.advance();
        }

        this.doStuckDetection(currentPos);
    }

    protected boolean invalidateIfNextNodeIsTooHigh() {
        var path = this.path;
        if (path == null) return true;
        var nextPos = path.getNextNodePos();
        var dy = this.cat.getY() - (double)nextPos.getY();

        if (dy < -1.75) {
            this.stop();
            return true;
        }

        var nextNode = path.getNextNode();
        if (cat.getPathfindingMalus(nextNode.type) < 0) {
            this.stop();
            return true;
        }

        return false;
    }

    private boolean shouldTargetNextNodeInDirection(Vec3 currentPos) {
        var path = this.path;
        if (path == null) return false;
        if (path.getNextNodeIndex() + 1 >= path.getNodeCount()) {
            return false;
        }

        var nextPos = Vec3.atBottomCenterOf(path.getNextNodePos());
        if (!currentPos.closerThan(nextPos, 2.0D)) {
            return false;
        }

        Vec3 next2ndNode = Vec3.atBottomCenterOf(path.getNodePos(path.getNextNodeIndex() + 1));
        Vec3 vNextNext2nd = next2ndNode.subtract(nextPos);
        Vec3 vNextCurrent = currentPos.subtract(nextPos);
        //small alpha
        if (vNextNext2nd.dot(vNextCurrent) <= 0.0D) return false;

        Vec3 vCurrentNext2th = next2ndNode.subtract(currentPos);
        double vCurrentNext2ndLSqr = vCurrentNext2th.lengthSqr();
        if (vCurrentNext2ndLSqr < 1) return true;
        Vec3 vAdd = vCurrentNext2th.normalize();
        var checkB0 = BlockPos.containing(currentPos.add(vAdd));
        var type = WalkNodeEvaluator
                .getBlockPathTypeStatic(level, checkB0.mutable());
        return type == BlockPathTypes.WALKABLE;
    }

}
