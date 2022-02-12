package kittytalents.common.network.packet.data;

import kittytalents.api.registry.Talent;

public class DogTalentData extends DogData {

    public final Talent talent;

    public DogTalentData(int entityId, Talent talent) {
        super(entityId);
        this.talent = talent;
    }
}
