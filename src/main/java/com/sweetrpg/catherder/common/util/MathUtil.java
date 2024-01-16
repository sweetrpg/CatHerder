package com.sweetrpg.catherder.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;

public class MathUtil {

    public static BlockPos calculateCenter(BlockPos pos1, BlockPos pos2, BlockPos pos3) {
        final var centerX = (pos1.getX() + pos2.getX() + pos3.getX()) / 3.0f;
        final var centerY = (pos1.getY() + pos2.getY() + pos3.getY()) / 3.0f;
        final var centerZ = (pos1.getZ() + pos2.getZ() + pos3.getZ()) / 3.0f;

        return new BlockPos(centerX, centerY, centerZ);
    }

    public static double furthestDistance(BlockPos startPos, BlockPos... points) {
        double furthest = 0;

        for(var point : points) {
            double distance = startPos.distSqr(point);
            if(distance > furthest) {
                furthest = distance;
            }
        }

        return furthest;
    }
}
