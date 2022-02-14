package com.sweetrpg.catherder.client;

public enum SkinRequest {

    UNREQUESTED,
    REQUESTED,
    RECEIVED,
    FAILED;

    boolean requested() {
        return this != UNREQUESTED;
    }
}
