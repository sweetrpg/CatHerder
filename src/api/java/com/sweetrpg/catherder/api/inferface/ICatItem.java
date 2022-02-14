package com.sweetrpg.catherder.api.inferface;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

public interface ICatItem {

    /**
     * Implement on item class called when player interacts with a cat
     * @param catIn The cat the item is being used on
     * @param worldIn The world the cat is in
     * @param playerIn The player who clicked
     * @param handIn The hand used
     * @return The result of the interaction
     */
    public InteractionResult processInteract(AbstractCatEntity catIn, Level worldIn, Player playerIn, InteractionHand handIn);

    @Deprecated
    default InteractionResult onInteractWithDog(AbstractCatEntity catIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        return processInteract(catIn, worldIn, playerIn, handIn);
    }
}
