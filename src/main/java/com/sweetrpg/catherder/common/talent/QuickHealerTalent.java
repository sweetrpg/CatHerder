package com.sweetrpg.catherder.common.talent;

import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.registry.Talent;
import com.sweetrpg.catherder.api.registry.TalentInstance;
import net.minecraft.world.InteractionResultHolder;

public class QuickHealerTalent extends TalentInstance {

    public QuickHealerTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public InteractionResultHolder<Integer> healingTick(AbstractCatEntity catIn, int healingTick) {
        if(this.level() > 0) {
            if(catIn.isInSittingPose() && this.level() >= 5) {
                if(catIn.getNoActionTime() > 100) {
                    healingTick *= 15;
                }
                else {
                    healingTick *= 10;
                }
            }
            else {
                healingTick *= this.level();
            }

            return InteractionResultHolder.success(healingTick);
        }


        return InteractionResultHolder.pass(healingTick);
    }
}
