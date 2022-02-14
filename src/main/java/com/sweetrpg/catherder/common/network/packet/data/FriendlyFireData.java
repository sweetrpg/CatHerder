package com.sweetrpg.catherder.common.network.packet.data;

public class FriendlyFireData extends CatData {

    public final boolean friendlyFire;

    public FriendlyFireData(int entityId, boolean friendlyFire) {
        super(entityId);
        this.friendlyFire = friendlyFire;
    }
}
