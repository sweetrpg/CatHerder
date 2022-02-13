package kittytalents.common.talent;

import kittytalents.api.registry.Talent;
import kittytalents.api.registry.TalentInstance;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public class HellBeastTalent extends TalentInstance {

    public HellBeastTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public InteractionResultHolder<Integer> setFire(kittytalents.api.inferface.AbstractCatEntity catIn, int second) {
        return InteractionResultHolder.success(this.level() > 0 ? second / this.level() : second);
    }

    @Override
    public InteractionResult isImmuneToFire(kittytalents.api.inferface.AbstractCatEntity catIn) {
        return this.level() >= 5 ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public InteractionResult isInvulnerableTo(kittytalents.api.inferface.AbstractCatEntity catIn, DamageSource source) {
        if (source.isFire()) {
            return this.level() >= 5 ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult attackEntityAsMob(kittytalents.api.inferface.AbstractCatEntity catIn, Entity entity) {
        if (this.level() > 0) {
            entity.setSecondsOnFire(this.level());
            return InteractionResult.PASS;
        }

        return InteractionResult.PASS;
    }
}
