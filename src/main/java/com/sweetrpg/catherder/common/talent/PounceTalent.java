package com.sweetrpg.catherder.common.talent;

import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import com.sweetrpg.catherder.api.registry.Talent;
import com.sweetrpg.catherder.api.registry.TalentInstance;
import com.sweetrpg.catherder.common.registry.ModAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class PounceTalent extends TalentInstance {

    private static final UUID POUNCE_DAMAGE_ID = UUID.fromString("9abeafa9-3913-4b4c-b46e-0f1548fb19b3");
    private static final UUID POUNCE_CRIT_CHANCE_ID = UUID.fromString("f07b5d39-a8cc-4d32-b458-6efdf1dc6836");
    private static final UUID POUNCE_CRIT_BONUS_ID = UUID.fromString("e19e0d42-6ee3-4ee1-af1c-7519af4354cd");

    public PounceTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void init(AbstractCatEntity catIn) {
        catIn.setAttributeModifier(Attributes.ATTACK_DAMAGE, POUNCE_DAMAGE_ID, this::createPounceModifier);
        catIn.setAttributeModifier(ModAttributes.CRIT_CHANCE.get(), POUNCE_CRIT_CHANCE_ID, this::createPounceCritChance);
        catIn.setAttributeModifier(ModAttributes.CRIT_BONUS.get(), POUNCE_CRIT_BONUS_ID, this::createPounceCritBonus);
    }

    @Override
    public void set(AbstractCatEntity dogIn, int levelBefore) {
        dogIn.setAttributeModifier(Attributes.ATTACK_DAMAGE, POUNCE_DAMAGE_ID, this::createPounceModifier);
        dogIn.setAttributeModifier(ModAttributes.CRIT_CHANCE.get(), POUNCE_CRIT_CHANCE_ID, this::createPounceCritChance);
        dogIn.setAttributeModifier(ModAttributes.CRIT_BONUS.get(), POUNCE_CRIT_BONUS_ID, this::createPounceCritBonus);
    }

    public AttributeModifier createPounceModifier(AbstractCatEntity catIn, UUID uuidIn) {
        if(this.level() > 0) {
            double damageBonus = this.level();

            if(this.level() >= 5) {
                damageBonus += 2;
            }

            return new AttributeModifier(uuidIn, "Pounce", damageBonus, AttributeModifier.Operation.ADDITION);
        }

        return null;
    }

    public AttributeModifier createPounceCritChance(AbstractCatEntity catIn, UUID uuidIn) {
        if(this.level() <= 0) {
            return null;
        }

        double damageBonus = 0.15D * this.level();

        if(this.level() >= 5) {
            damageBonus = 1D;
        }

        return new AttributeModifier(uuidIn, "Pounce Crit Chance", damageBonus, AttributeModifier.Operation.ADDITION);
    }

    public AttributeModifier createPounceCritBonus(AbstractCatEntity catIn, UUID uuidIn) {
        if(this.level() <= 0) {
            return null;
        }

        return new AttributeModifier(uuidIn, "Pounce Crit Bonus", 1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
