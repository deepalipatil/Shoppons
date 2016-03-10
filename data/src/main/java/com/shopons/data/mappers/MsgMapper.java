package com.shopons.data.mappers;

import com.shopons.data.entities.MsgEntity;
import com.shopons.domain.MsgFromServer;

/**
 * Created by deepali on 8/3/16.
 */
public class MsgMapper {

    public static MsgEntity transform(final MsgFromServer msgFromServer) {
        return new MsgEntity(msgFromServer.getMessage(), msgFromServer.isSuccess());
    }

    public static MsgFromServer transform(final MsgEntity msgEntity) {
        return new MsgFromServer(msgEntity.getMessage(), msgEntity.isSuccess());
    }
}
