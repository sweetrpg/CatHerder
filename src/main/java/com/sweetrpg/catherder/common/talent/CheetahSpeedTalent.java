package com.sweetrpg.catherder.common.talent;

import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.registry.Talent;
import com.sweetrpg.catherder.api.registry.TalentInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class CheetahSpeedTalent extends TalentInstance {

    private static final UUID DASH_BOOST_ID = UUID.fromString("50671e49-1ded-4097-902b-78bb6b178772");

    public CheetahSpeedTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void init(AbstractCatEntity catIn) {
        catIn.setAttributeModifier(Attributes.MOVEMENT_SPEED, DASH_BOOST_ID, this::createSpeedModifier);
    }

    @Override
    public void set(AbstractCatEntity catIn, int level) {
        catIn.setAttributeModifier(Attributes.MOVEMENT_SPEED, DASH_BOOST_ID, this::createSpeedModifier);
    }

    public AttributeModifier createSpeedModifier(AbstractCatEntity catIn, UUID uuidIn) {
        if(this.level() > 0) {
            double speed = 0.03D * this.level();

            if(this.level() >= 5) {
                speed += 0.04D;
            }

            return new AttributeModifier(uuidIn, "Cheetah Speed", speed, AttributeModifier.Operation.ADDITION);
        }

        return null;
    }
}
