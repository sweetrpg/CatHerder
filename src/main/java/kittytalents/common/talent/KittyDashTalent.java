package kittytalents.common.talent;

import kittytalents.api.registry.Talent;
import kittytalents.api.registry.TalentInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class KittyDashTalent extends TalentInstance {

    private static final UUID DASH_BOOST_ID = UUID.fromString("50671e49-1ded-4097-902b-78bb6b178772");

    public KittyDashTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void init(kittytalents.api.inferface.AbstractCatEntity catIn) {
        catIn.setAttributeModifier(Attributes.MOVEMENT_SPEED, DASH_BOOST_ID, this::createSpeedModifier);
    }

    @Override
    public void set(kittytalents.api.inferface.AbstractCatEntity catIn, int level) {
        catIn.setAttributeModifier(Attributes.MOVEMENT_SPEED, DASH_BOOST_ID, this::createSpeedModifier);
    }

    public AttributeModifier createSpeedModifier(kittytalents.api.inferface.AbstractCatEntity catIn, UUID uuidIn) {
        if (this.level() > 0) {
            double speed = 0.03D * this.level();

            if (this.level() >= 5) {
                speed += 0.04D;
            }

            return new AttributeModifier(uuidIn, "Kitty Dash", speed, AttributeModifier.Operation.ADDITION);
        }

        return null;
    }
}
