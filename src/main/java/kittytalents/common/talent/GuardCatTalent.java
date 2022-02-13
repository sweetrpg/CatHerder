package kittytalents.common.talent;

import kittytalents.api.registry.Talent;
import kittytalents.api.registry.TalentInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public class GuardCatTalent extends TalentInstance {

    private int cooldown;

    public GuardCatTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public TalentInstance copy() {
        kittytalents.common.talent.GuardCatTalent inst = new kittytalents.common.talent.GuardCatTalent(this.getTalent(), this.level);
        inst.cooldown = this.cooldown;
        return inst;
    }

    @Override
    public void init(kittytalents.api.inferface.AbstractCatEntity catIn) {
        this.cooldown = catIn.tickCount;
    }

    @Override
    public void writeToNBT(kittytalents.api.inferface.AbstractCatEntity catIn, CompoundTag compound) {
        super.writeToNBT(catIn, compound);
        int timeLeft = this.cooldown - catIn.tickCount;
        compound.putInt("guardtime", timeLeft);
    }

    @Override
    public void readFromNBT(kittytalents.api.inferface.AbstractCatEntity catIn, CompoundTag compound) {
        super.readFromNBT(catIn, compound);
        this.cooldown = catIn.tickCount + compound.getInt("guardtime");
    }

    @Override
    public InteractionResultHolder<Float> attackEntityFrom(kittytalents.api.inferface.AbstractCatEntity catIn, DamageSource damageSource, float damage) {
        if (catIn.level.isClientSide) {
            return InteractionResultHolder.pass(damage);
        }

        Entity entity = damageSource.getEntity();

        if (entity != null) {
            int timeLeft =  this.cooldown - catIn.tickCount;
            if (timeLeft <= 0) {
                int blockChance = this.level() + (this.level() >= 5 ? 1 : 0);

                if (catIn.getRandom().nextInt(12) < blockChance) {
                    this.cooldown = catIn.tickCount + 10;
                    catIn.playSound(SoundEvents.ITEM_BREAK, catIn.getSoundVolume() / 2, (catIn.getRandom().nextFloat() - catIn.getRandom().nextFloat()) * 0.2F + 1.0F);
                    return InteractionResultHolder.fail(0F);
                }
            }
        }

        return InteractionResultHolder.pass(damage);
    }

}
