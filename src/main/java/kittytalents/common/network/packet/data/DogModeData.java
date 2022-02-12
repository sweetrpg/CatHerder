package kittytalents.common.network.packet.data;

import kittytalents.api.feature.EnumMode;

public class DogModeData extends DogData {

    public EnumMode mode;

    public DogModeData(int entityId, EnumMode modeIn) {
        super(entityId);
        this.mode = modeIn;
    }
}
