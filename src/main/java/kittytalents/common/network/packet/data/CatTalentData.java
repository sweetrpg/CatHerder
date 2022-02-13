package kittytalents.common.network.packet.data;

import kittytalents.api.registry.Talent;

public class CatTalentData extends CatData {

    public final Talent talent;

    public CatTalentData(int entityId, Talent talent) {
        super(entityId);
        this.talent = talent;
    }
}
