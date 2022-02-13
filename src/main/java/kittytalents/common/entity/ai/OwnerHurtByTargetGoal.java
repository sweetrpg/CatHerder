package kittytalents.common.entity.ai;

import kittytalents.api.feature.EnumMode;

public class OwnerHurtByTargetGoal extends net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal {

    private kittytalents.common.entity.CatEntity cat;

    public OwnerHurtByTargetGoal(kittytalents.common.entity.CatEntity catIn) {
        super(catIn);
        this.cat = catIn;
    }

    @Override
    public boolean canUse() {
         return this.cat.isMode(EnumMode.AGGRESIVE, EnumMode.BERSERKER) && super.canUse();
    }
}
