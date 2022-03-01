package com.sweetrpg.catherder.common.talent;

import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.registry.Talent;
import com.sweetrpg.catherder.api.registry.TalentInstance;
import com.sweetrpg.catherder.common.registry.ModItems;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BedFinderTalent extends TalentInstance {

    public BedFinderTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void livingTick(AbstractCatEntity cat) {

    }

    @Override
    public InteractionResult processInteract(AbstractCatEntity catIn, Level worldIn, Player playerIn, InteractionHand handIn) {
        if(this.level() > 0) {
            if(!playerIn.hasPassenger(catIn)) {
                if(playerIn.getItemInHand(handIn).getItem() == ModItems.YARN.get() && catIn.canInteract(playerIn)) {
                    if(catIn.startRiding(playerIn)) {
                        if(!catIn.level.isClientSide) {
                            catIn.setOrderedToSit(true);
                        }

                        playerIn.displayClientMessage(new TranslatableComponent("talent.catherder.bed_finder.mount", catIn.getGenderPronoun()), true);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            else {
                catIn.stopRiding();
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }
}
