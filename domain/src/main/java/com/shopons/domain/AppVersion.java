package com.shopons.domain;

/**
 * Created by komal on 11/2/16.
 */
public class AppVersion {

    private boolean forceUpdate;

    private boolean updateAvailable;

    public AppVersion(final boolean forceUpdate, final boolean updateAvailable) {
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
