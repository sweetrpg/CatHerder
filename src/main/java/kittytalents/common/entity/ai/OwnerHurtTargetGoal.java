package kittytalents.common.entity.ai;

import kittytalents.api.feature.EnumMode;

public class OwnerHurtTargetGoal extends net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal {

    private kittytalents.common.entity.CatEntity cat;

    public OwnerHurtTargetGoal(kittytalents.common.entity.CatEntity catIn) {
        super(catIn);
        this.cat = catIn;
    }

    @Override
    public boolean canUse() {
         return this.cat.isMode(EnumMode.AGGRESIVE, EnumMode.BERSERKER, EnumMode.TACTICAL) && super.canUse();
    }
}
