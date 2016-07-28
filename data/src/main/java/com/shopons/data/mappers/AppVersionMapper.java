package com.shopons.data.mappers;

import com.shopons.data.entities.AppVersionEntity;
import com.shopons.domain.AppVersion;

public class AppVersionMapper {
    public static AppVersion transform(final AppVersionEntity appVersionEntity) {
        return new AppVersion(appVersionEntity.getForceUpdate(), appVersionEntity.getUpdateAvailable());
    }

    public static AppVersionEntity transform(final AppVersion appVersion) {
        return new AppVersionEntity(appVersion.isForceUpdate(), appVersion.isUpdateAvailable());
    }
}
