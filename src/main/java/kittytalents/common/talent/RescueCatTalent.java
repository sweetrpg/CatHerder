package kittytalents.common.talent;

import kittytalents.api.registry.Talent;
import kittytalents.api.registry.TalentInstance;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class RescueCatTalent extends TalentInstance {

    public RescueCatTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void livingTick(kittytalents.api.inferface.AbstractCatEntity dogIn) {
        if (dogIn.level.isClientSide) {
            return;
        }

        if (this.level() > 0) {
            LivingEntity owner = dogIn.getOwner();

            //TODO add particles and check how far away cat is
            if (owner != null && owner.getHealth() <= 6) {
                int healCost = this.healCost(this.level());

                if (dogIn.getDogHunger() >= healCost) {
                    owner.heal(Mth.floor(this.level() * 1.5D));
                    dogIn.setDogHunger(dogIn.getDogHunger() - healCost);
                }
            }
        }
    }

    public int healCost(int level) {
        byte cost = 100;

        if (level >= 5) {
            cost = 80;
        }

        return cost;
    }

    @Override
    public boolean hasRenderer() {
        return true;
    }
}
