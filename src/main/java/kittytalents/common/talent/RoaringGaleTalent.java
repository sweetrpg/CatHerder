package kittytalents.common.talent;

import kittytalents.api.feature.DataKey;
import kittytalents.api.registry.Talent;
import kittytalents.api.registry.TalentInstance;

public class RoaringGaleTalent extends TalentInstance {

    public static DataKey<Integer> COOLDOWN = DataKey.make();

    public RoaringGaleTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }
}
