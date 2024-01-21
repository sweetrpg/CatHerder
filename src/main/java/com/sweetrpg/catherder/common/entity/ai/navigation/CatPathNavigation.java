package com.sweetrpg.catherder.common.entity.ai.navigation;

import com.sweetrpg.catherder.CatHerder;
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
        CatHerder.LOGGER.debug("#followThePath");
        if (invalidateIfNextNodeIsTooHigh()) return;

        var currentPos = this.getTempMobPos();
        CatHerder.LOGGER.debug("currentPos = {}", currentPos);
        this.maxDistanceToWaypoint =
                this.mob.getBbWidth() > 0.5625F ?
                        this.mob.getBbWidth() / 2.0F
                        : 0.5625F - this.mob.getBbWidth() / 2.0F;
        CatHerder.LOGGER.debug("maxDistanceToWaypoint = {}", maxDistanceToWaypoint);

        var nextPos = this.path.getNextNodePos();
        CatHerder.LOGGER.debug("nextPos = {}", nextPos);
        double dx = Math.abs(this.mob.getX() - ((double)nextPos.getX() + 0.5));
        double dy = Math.abs(this.mob.getY() - (double)nextPos.getY());
        double dz = Math.abs(this.mob.getZ() - ((double)nextPos.getZ() + 0.5));
        CatHerder.LOGGER.debug("dx = {}, dy = {}, dz = {}", dx, dy, dz);

        boolean isCloseEnough =
                dx <= (double)this.maxDistanceToWaypoint
                        && dy < 1.0D
                        && dz <= (double)this.maxDistanceToWaypoint;
        CatHerder.LOGGER.debug("isCloseEnough = {}", isCloseEnough);
        boolean canCutCorner =
                this.canCutCorner(this.path.getNextNode().type)
                        && this.shouldTargetNextNodeInDirection(currentPos);
        CatHerder.LOGGER.debug("canCutCorner = {}", canCutCorner);

        if (isCloseEnough || canCutCorner) {
            CatHerder.LOGGER.debug("is close enough or can cut corner, advance the path");
            this.path.advance();
        }

        CatHerder.LOGGER.debug("do stuck detection");
        this.doStuckDetection(currentPos);
    }

    protected boolean invalidateIfNextNodeIsTooHigh() {
        CatHerder.LOGGER.debug("#invalidateIfNextNodeIsTooHigh");
        var path = this.path;
        CatHerder.LOGGER.debug("path = {}", path);
        if (path == null) return true;
        var nextPos = path.getNextNodePos();
        CatHerder.LOGGER.debug("nextPos = {}", nextPos);
        var dy = this.cat.getY() - (double)nextPos.getY();
        CatHerder.LOGGER.debug("dy = {}", dy);

        if (dy < -1.75) {
            CatHerder.LOGGER.debug("dy < -1.75");
            this.stop();
            return true;
        }

        var nextNode = path.getNextNode();
        CatHerder.LOGGER.debug("nextNode = {}", nextNode);
        if (cat.getPathfindingMalus(nextNode.type) < 0) {
            CatHerder.LOGGER.debug("getPathfindingMalus({}) < 0", nextNode.type);
            this.stop();
            return true;
        }

        CatHerder.LOGGER.debug("keep going");
        return false;
    }

    private boolean shouldTargetNextNodeInDirection(Vec3 current_pos) {
        CatHerder.LOGGER.debug("#shouldTargetNextNodeInDirection({})", current_pos);
        var path = this.path;
        CatHerder.LOGGER.debug("path = {}", path);
        if (path == null) return false;
        if (path.getNextNodeIndex() + 1 >= path.getNodeCount()) {
            CatHerder.LOGGER.debug("next node index + 1 is > node count");
            return false;
        }

        var next_pos = Vec3.atBottomCenterOf(path.getNextNodePos());
        CatHerder.LOGGER.debug("next_pos = {}", next_pos);
        if (!current_pos.closerThan(next_pos, 2.0D)) {
            CatHerder.LOGGER.debug("current_pos<->next_pos closer than 2.0");
            return false;
        }

        Vec3 next2th_node = Vec3.atBottomCenterOf(path.getNodePos(path.getNextNodeIndex() + 1));
        Vec3 v_next_next2th = next2th_node.subtract(next_pos);
        Vec3 v_next_current = current_pos.subtract(next_pos);
        CatHerder.LOGGER.debug("next2th_node = {}, v_next_next2th = {}, v_next_current = {}", next2th_node, v_next_next2th, v_next_current);
        //small alpha
        if (v_next_next2th.dot(v_next_current) <= 0.0D) return false;

        Vec3 v_current_next2th = next2th_node.subtract(current_pos);
        CatHerder.LOGGER.debug("v_current_next2th = {}", v_current_next2th);
        double v_current_next2th_lSqr = v_current_next2th.lengthSqr();
        CatHerder.LOGGER.debug("v_current_next2th_lSqr = {}", v_current_next2th_lSqr);
        if (v_current_next2th_lSqr < 1) return true;
        Vec3 v_add = v_current_next2th.normalize();
        CatHerder.LOGGER.debug("v_add = {}", v_add);
        var check_b0 = BlockPos.containing(current_pos.add(v_add));
        CatHerder.LOGGER.debug("check_b0 = {}", check_b0);
        var type = WalkNodeEvaluator
                .getBlockPathTypeStatic(level, check_b0.mutable());
        CatHerder.LOGGER.debug("type = {}", type);
        return type == BlockPathTypes.WALKABLE;
    }

}
