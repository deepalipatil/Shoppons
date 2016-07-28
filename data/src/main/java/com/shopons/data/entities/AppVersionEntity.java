package com.shopons.data.entities;

import com.google.gson.annotations.SerializedName;

public class AppVersionEntity {
    @SerializedName("force_update")
    private boolean forceUpdate;
    @SerializedName("update_available")
    private boolean updateAvailable;
    public AppVersionEntity(final boolean forceUpdate, final boolean updateAvailable) {
        this.forceUpdate = forceUpdate;
        this.updateAvailable = updateAvailable;
    }

    public AppVersionEntity(){}

    public boolean getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public boolean getUpdateAvailable() {
        return updateAvailable;
    }

    public void setUpdateAvailable(boolean updateAvailable) {
        this.updateAvailable = updateAvailable;
    }
}
