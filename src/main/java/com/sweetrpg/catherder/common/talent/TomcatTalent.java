package com.sweetrpg.catherder.common.talent;

import com.sweetrpg.catherder.CatAttributes;
import com.sweetrpg.catherder.api.registry.Talent;
import com.sweetrpg.catherder.api.registry.TalentInstance;
import com.sweetrpg.catherder.api.inferface.AbstractCatEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class TomcatTalent extends TalentInstance {

    private static final UUID TOMCAT_DAMAGE_ID = UUID.fromString("9abeafa9-3913-4b4c-b46e-0f1548fb19b3");
    private static final UUID TOMCAT_CRIT_CHANCE_ID = UUID.fromString("f07b5d39-a8cc-4d32-b458-6efdf1dc6836");
    private static final UUID TOMCAT_CRIT_BONUS_ID = UUID.fromString("e19e0d42-6ee3-4ee1-af1c-7519af4354cd");

    public TomcatTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void init(AbstractCatEntity catIn) {
        catIn.setAttributeModifier(Attributes.ATTACK_DAMAGE, TOMCAT_DAMAGE_ID, this::createPeltModifier);
        catIn.setAttributeModifier(CatAttributes.CRIT_CHANCE.get(), TOMCAT_CRIT_CHANCE_ID, this::createPeltCritChance);
        catIn.setAttributeModifier(CatAttributes.CRIT_BONUS.get(), TOMCAT_CRIT_BONUS_ID, this::createPeltCritBonus);
    }

    @Override
    public void set(AbstractCatEntity catIn, int levelBefore) {
        catIn.setAttributeModifier(Attributes.ATTACK_DAMAGE, TOMCAT_DAMAGE_ID, this::createPeltModifier);
        catIn.setAttributeModifier(CatAttributes.CRIT_CHANCE.get(), TOMCAT_CRIT_CHANCE_ID, this::createPeltCritChance);
        catIn.setAttributeModifier(CatAttributes.CRIT_BONUS.get(), TOMCAT_CRIT_BONUS_ID, this::createPeltCritBonus);
    }

    public AttributeModifier createPeltModifier(AbstractCatEntity catIn, UUID uuidIn) {
        if (this.level() > 0) {
            double damageBonus = this.level();

            if (this.level() >= 5) {
                damageBonus += 2;
            }

            return new AttributeModifier(uuidIn, "Black Pelt", damageBonus, AttributeModifier.Operation.ADDITION);
        }

        return null;
    }

    public AttributeModifier createPeltCritChance(AbstractCatEntity catIn, UUID uuidIn) {
        if (this.level() <= 0) {
            return null;
        }

        double damageBonus = 0.15D * this.level();

        if (this.level() >= 5) {
            damageBonus = 1D;
        }

        return new AttributeModifier(uuidIn, "Black Pelt Crit Chance", damageBonus, AttributeModifier.Operation.ADDITION);
    }

    public AttributeModifier createPeltCritBonus(AbstractCatEntity catIn, UUID uuidIn) {
        if (this.level() <= 0) {
            return null;
        }

        return new AttributeModifier(uuidIn, "Black Pelt Crit Bonus", 1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
