package com.sweetrpg.catherder.common.talent;

import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.registry.Talent;
import com.sweetrpg.catherder.api.registry.TalentInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;

import java.util.List;

public class CreeperSweeperTalent extends TalentInstance {

    private int cooldown;

    public CreeperSweeperTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void init(AbstractCatEntity catIn) {
        this.cooldown = catIn.tickCount;
    }

    @Override
    public void tick(AbstractCatEntity catIn) {
        if(this.level() > 0) {
            int timeLeft = this.cooldown - catIn.tickCount;

            if(timeLeft <= 0 && !catIn.isInSittingPose()) {
                List<Creeper> list = catIn.level.getEntitiesOfClass(Creeper.class, catIn.getBoundingBox().inflate(this.level() * 5, this.level() * 2, this.level() * 5));

                if(!list.isEmpty()) {
                    catIn.playSound(SoundEvents.CAT_HISS, catIn.getSoundVolume(), (catIn.getRandom().nextFloat() - catIn.getRandom().nextFloat()) * 0.2F + 1.0F);
                    this.cooldown = catIn.tickCount + 60 + catIn.getRandom().nextInt(40);
                }
            }

            if(catIn.getTarget() instanceof Creeper) {
                Creeper creeper = (Creeper) catIn.getTarget();
                creeper.setSwellDir(-1);
            }
        }
    }

    @Override
    public InteractionResult canAttack(AbstractCatEntity cat, EntityType<?> entityType) {
        return entityType == EntityType.CREEPER && this.level() >= 5 ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public InteractionResult canAttack(AbstractCatEntity cat, LivingEntity entity) {
        return entity instanceof Creeper && this.level() >= 5 ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public InteractionResult shouldAttackEntity(AbstractCatEntity cat, LivingEntity target, LivingEntity owner) {
        return target instanceof Creeper && this.level() >= 5 ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }
}
