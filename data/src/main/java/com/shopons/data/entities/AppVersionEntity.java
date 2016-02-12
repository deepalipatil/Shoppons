package com.shopons.data.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by komal on 11/2/16.
 */
public class AppVersionEntity {
    @SerializedName("force_update")
    private boolean forceUpdate;
    @SerializedName("update_available")
    private boolean updateAvailable;

    public AppVersionEntity(final boolean forceUpdate, final boolean updateAvailable) {
        this.forceUpdate = forceUpdate;
        this.updateAvailable = updateAvailable;
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public boolean isUpdateAvailable() {
        return updateAvailable;
    }

    public void setUpdateAvailable(boolean updateAvailable) {
        this.updateAvailable = updateAvailable;
    }
}
