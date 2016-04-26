/*
 * @#MethodCallsHistoryDao.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDatabaseConstants;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.entities.MethodCallsHistory;

import java.sql.Timestamp;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.MethodCallsHistoryDao</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 23/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class MethodCallsHistoryDao extends AbstractBaseDao<MethodCallsHistory> {

    /**
     * Constructor with parameter
     *
     * @param dataBase
     */
    public MethodCallsHistoryDao(Database dataBase) {
        super(dataBase, CommunicationsNetworkNodeP2PDatabaseConstants.METHOD_CALLS_HISTORY_TABLE_NAME, CommunicationsNetworkNodeP2PDatabaseConstants.METHOD_CALLS_HISTORY_FIRST_KEY_COLUMN);
    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getEntityFromDatabaseTableRecord(DatabaseTableRecord)
     */
    @Override
    protected MethodCallsHistory getEntityFromDatabaseTableRecord(DatabaseTableRecord record) throws InvalidParameterException {

        MethodCallsHistory entity = new MethodCallsHistory();

        entity.setUuid(record.getUUIDValue(CommunicationsNetworkNodeP2PDatabaseConstants.METHOD_CALLS_HISTORY_UUID_COLUMN_NAME));
        entity.setCreateTimestamp(getTimestampFromLongValue(record.getLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.METHOD_CALLS_HISTORY_CREATE_TIMESTAMP_COLUMN_NAME)));
        entity.setMethodName(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.METHOD_CALLS_HISTORY_METHOD_NAME_COLUMN_NAME));
        entity.setParameters(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.METHOD_CALLS_HISTORY_PARAMETERS_COLUMN_NAME));
        entity.setProfileIdentityPublicKey(record.getStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.METHOD_CALLS_HISTORY_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME));

        return entity;
    }

    /**
     * (non-javadoc)
     * @see AbstractBaseDao#getDatabaseTableRecordFromEntity
     */
    @Override
    protected DatabaseTableRecord getDatabaseTableRecordFromEntity(MethodCallsHistory entity) {

        DatabaseTableRecord databaseTableRecord = getDatabaseTable().getEmptyRecord();

        databaseTableRecord.setUUIDValue(CommunicationsNetworkNodeP2PDatabaseConstants.METHOD_CALLS_HISTORY_UUID_COLUMN_NAME, entity.getUuid());
        databaseTableRecord.setLongValue(CommunicationsNetworkNodeP2PDatabaseConstants.METHOD_CALLS_HISTORY_CREATE_TIMESTAMP_COLUMN_NAME , getLongValueFromTimestamp(entity.getCreateTimestamp()));
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.METHOD_CALLS_HISTORY_METHOD_NAME_COLUMN_NAME, entity.getMethodName());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.METHOD_CALLS_HISTORY_PARAMETERS_COLUMN_NAME, entity.getParameters());
        databaseTableRecord.setStringValue(CommunicationsNetworkNodeP2PDatabaseConstants.METHOD_CALLS_HISTORY_CLIENT_IDENTITY_PUBLIC_KEY_COLUMN_NAME, entity.getProfileIdentityPublicKey());

        return databaseTableRecord;
    }

    @Override
    protected DatabaseTableRecord getDatabaseTableRecordForUpdate(MethodCallsHistory entity, DatabaseTableRecord databaseTableRecordLoad) throws InvalidParameterException {
        return null;
    }
}

