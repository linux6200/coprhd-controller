/*
 * Copyright (c) 2017 Dell EMC
 * All Rights Reserved
 */
package com.emc.storageos.volumecontroller.impl.vmax;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emc.storageos.db.client.DbClient;
import com.emc.storageos.db.client.model.StorageSystem;
import com.emc.storageos.exceptions.DeviceControllerException;
import com.emc.storageos.volumecontroller.DefaultBlockStorageDevice;
import com.emc.storageos.volumecontroller.TaskCompleter;

public class VMAXStorageDevice extends DefaultBlockStorageDevice {
    private static final Logger logger = LoggerFactory.getLogger(VMAXStorageDevice.class);

    private DbClient dbClient;
    private VMAXMigrationOperations migrationOperations;

    public void setDbClient(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    public void setMigrationOperations(VMAXMigrationOperations migrationOperations) {
        this.migrationOperations = migrationOperations;
    }

    public void doCreateMigrationEnvironment(StorageSystem sourceSystem, StorageSystem targetSystem, TaskCompleter taskCompleter)
            throws DeviceControllerException {
        logger.info(VMAXMigrationConstants.CREATE_MIGRATION_ENV + " started");
        migrationOperations.createMigrationEnvironment(sourceSystem, targetSystem, taskCompleter);
        logger.info(VMAXMigrationConstants.CREATE_MIGRATION_ENV + " finished");
    }

    public void doRemoveMigrationEnvironment(StorageSystem sourceSystem, StorageSystem targetSystem, TaskCompleter taskCompleter)
            throws DeviceControllerException {
        logger.info(VMAXMigrationConstants.REMOVE_MIGRATION_ENV + " started");
        migrationOperations.removeMigrationEnvironment(sourceSystem, targetSystem, taskCompleter);
        logger.info(VMAXMigrationConstants.REMOVE_MIGRATION_ENV + " finished");
    }

    public void doCreateMigration(StorageSystem sourceSystem, URI cgURI, URI migrationURI,
            URI targetSystemURI, URI srp, Boolean enableCompression, TaskCompleter taskCompleter) throws DeviceControllerException {
        logger.info(VMAXMigrationConstants.CREATE_MIGRATION + " started");
        migrationOperations.createMigration(sourceSystem, cgURI, migrationURI, targetSystemURI, srp, enableCompression, taskCompleter);
        logger.info(VMAXMigrationConstants.CREATE_MIGRATION + " finished");
    }

    public void doCutoverMigration(StorageSystem sourceSystem, URI cgURI, URI migrationURI, TaskCompleter taskCompleter)
            throws DeviceControllerException {
        logger.info(VMAXMigrationConstants.CUTOVER_MIGRATION + " started");
        migrationOperations.cutoverMigration(sourceSystem, cgURI, migrationURI, taskCompleter);
        logger.info(VMAXMigrationConstants.CUTOVER_MIGRATION + " finished");
    }

    public void doReadyTgtMigration(StorageSystem sourceSystem, URI cgURI, URI migrationURI, TaskCompleter taskCompleter)
            throws DeviceControllerException {
        logger.info(VMAXMigrationConstants.READY_TGT_MIGRATION + " started");
        migrationOperations.readyTgtMigration(sourceSystem, cgURI, migrationURI, taskCompleter);
        logger.info(VMAXMigrationConstants.READY_TGT_MIGRATION + " finished");
    }

    public void doCommitMigration(StorageSystem sourceSystem, URI cgURI, URI migrationURI, TaskCompleter taskCompleter)
            throws DeviceControllerException {
        logger.info(VMAXMigrationConstants.COMMIT_MIGRATION + " started");
        migrationOperations.commitMigration(sourceSystem, cgURI, migrationURI, taskCompleter);
        logger.info(VMAXMigrationConstants.COMMIT_MIGRATION + " finished");
    }

    public void doCancelMigration(StorageSystem sourceSystem, URI cgURI, URI migrationURI, boolean cancelWithRevert,
            TaskCompleter taskCompleter)
            throws DeviceControllerException {
        logger.info(VMAXMigrationConstants.CANCEL_MIGRATION + " started");
        migrationOperations.cancelMigration(sourceSystem, cgURI, migrationURI, cancelWithRevert, taskCompleter);
        logger.info(VMAXMigrationConstants.CANCEL_MIGRATION + " finished");
    }

    public void doRefreshMigration(StorageSystem sourceSystem, URI cgURI, URI migrationURI, TaskCompleter taskCompleter)
            throws DeviceControllerException {
        logger.info(VMAXMigrationConstants.REFRESH_MIGRATION + " started");
        migrationOperations.refreshMigration(sourceSystem, cgURI, migrationURI, taskCompleter);
        logger.info(VMAXMigrationConstants.REFRESH_MIGRATION + " finished");
    }

    public void doRecoverMigration(StorageSystem sourceSystem, URI cgURI, URI migrationURI, boolean force, TaskCompleter taskCompleter)
            throws DeviceControllerException {
        logger.info(VMAXMigrationConstants.RECOVER_MIGRATION + " started");
        migrationOperations.recoverMigration(sourceSystem, cgURI, migrationURI, force, taskCompleter);
        logger.info(VMAXMigrationConstants.RECOVER_MIGRATION + " finished");
    }

    public void doSyncStopMigration(StorageSystem sourceSystem, URI cgURI, URI migrationURI, TaskCompleter taskCompleter)
            throws DeviceControllerException {
        logger.info(VMAXMigrationConstants.SYNCSTOP_MIGRATION + " started");
        migrationOperations.syncStopMigration(sourceSystem, cgURI, migrationURI, taskCompleter);
        logger.info(VMAXMigrationConstants.SYNCSTOP_MIGRATION + " finished");
    }

    public void doSyncStartMigration(StorageSystem sourceSystem, URI cgURI, URI migrationURI, TaskCompleter taskCompleter)
            throws DeviceControllerException {
        logger.info(VMAXMigrationConstants.SYNCSTART_MIGRATION + " started");
        migrationOperations.syncStartMigration(sourceSystem, cgURI, migrationURI, taskCompleter);
        logger.info(VMAXMigrationConstants.SYNCSTART_MIGRATION + " finished");
    }
}
