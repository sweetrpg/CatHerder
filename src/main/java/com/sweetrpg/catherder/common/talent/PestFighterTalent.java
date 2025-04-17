package com.sweetrpg.catherder.common.talent;

import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.registry.Talent;
import com.sweetrpg.catherder.api.registry.TalentInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.monster.Silverfish;

import java.util.List;

public class PestFighterTalent extends TalentInstance {

    public PestFighterTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void livingTick(AbstractCatEntity catIn) {
        if(catIn.level.isClientSide || catIn.tickCount % 2 == 0) {
            return;
        }

        if(this.level() >= 0) {
            byte damage = 1;

            if(this.level() >= 5) {
                damage = 2;
            }

            List<Silverfish> list = catIn.level.getEntitiesOfClass(
                    Silverfish.class, catIn.getBoundingBox().inflate(this.level() * 3, 4D, this.level() * 3), EntitySelector.ENTITY_STILL_ALIVE
            );
            for(Silverfish silverfish : list) {
                if(catIn.getRandom().nextInt(10) == 0) {
                    silverfish.hurt(DamageSource.GENERIC, damage);
                }
            }
        }
    }
}
