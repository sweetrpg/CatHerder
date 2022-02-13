package kittytalents.common.network.packet.data;

import kittytalents.api.feature.EnumMode;

public class CatModeData extends CatData {

    public EnumMode mode;

    public CatModeData(int entityId, EnumMode modeIn) {
        super(entityId);
        this.mode = modeIn;
    }
}
