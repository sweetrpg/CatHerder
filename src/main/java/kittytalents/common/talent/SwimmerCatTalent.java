package kittytalents.common.talent;

import kittytalents.api.registry.Talent;
import kittytalents.api.registry.TalentInstance;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class SwimmerCatTalent extends TalentInstance {

    //TODO add ai goal to follow owner through water

    public SwimmerCatTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void livingTick(kittytalents.api.inferface.AbstractCatEntity dogIn) {
        if (this.level() >= 5 && dogIn.isVehicle() && dogIn.canBeControlledByRider()) {
            // canBeSteered checks entity is LivingEntity
            LivingEntity rider = (LivingEntity) dogIn.getControllingPassenger();
            if (rider.isInWater()) {
                rider.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 80, 1, true, false));
            }
        }
    }

    @Override
    public InteractionResult canBeRiddenInWater(kittytalents.api.inferface.AbstractCatEntity dogIn, Entity rider) {
        return this.level() >= 5 ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public InteractionResult canBreatheUnderwater(kittytalents.api.inferface.AbstractCatEntity dogIn) {
        return this.level() >= 5 ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<Integer> decreaseAirSupply(kittytalents.api.inferface.AbstractCatEntity dogIn, int air) {
        if (this.level() > 0 && dogIn.getRandom().nextInt(this.level() + 1) > 0) {
            return InteractionResultHolder.success(air);
        }

        return InteractionResultHolder.pass(air);
    }

    @Override
    public InteractionResultHolder<Integer> determineNextAir(kittytalents.api.inferface.AbstractCatEntity dogIn, int currentAir) {
        if (this.level() > 0) {
            return InteractionResultHolder.pass(currentAir + this.level());
        }

        return InteractionResultHolder.pass(currentAir);
    }
}
