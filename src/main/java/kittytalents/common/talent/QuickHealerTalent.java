package kittytalents.common.talent;

import kittytalents.api.registry.Talent;
import kittytalents.api.registry.TalentInstance;
import net.minecraft.world.InteractionResultHolder;

public class QuickHealerTalent extends TalentInstance {

    public QuickHealerTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public InteractionResultHolder<Integer> healingTick(kittytalents.api.inferface.AbstractCatEntity dogIn, int healingTick) {
        if (this.level() > 0) {
            if (dogIn.isInSittingPose() && this.level() >= 5) {
                if (dogIn.getNoActionTime() > 100) {
                    healingTick *= 15;
                } else {
                    healingTick *= 10;
                }
            } else {
                healingTick *= this.level();
            }

            return InteractionResultHolder.success(healingTick);
        }


        return InteractionResultHolder.pass(healingTick);
    }
}
