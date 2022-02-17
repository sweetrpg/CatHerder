package com.sweetrpg.catherder.common.storage;

import java.util.UUID;

public interface ICatData {

    public UUID getCatId();

    public UUID getOwnerId();

    public String getCatName();

    public String getOwnerName();
}
