package kittytalents.common.entity.ai;

import kittytalents.api.feature.EnumMode;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class BerserkerModeGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

    private final kittytalents.common.entity.CatEntity cat;

    public BerserkerModeGoal(kittytalents.common.entity.CatEntity catIn, Class<T> targetClassIn, boolean checkSight) {
        super(catIn, targetClassIn, checkSight, false);
        this.cat = catIn;
    }

    @Override
    public boolean canUse() {
        return this.cat.isMode(EnumMode.BERSERKER) && super.canUse();
    }
}