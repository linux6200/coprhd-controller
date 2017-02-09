package com.emc.storageos.volumecontroller.impl.externaldevice.taskcompleters;


import java.net.URI;
import java.util.Collections;
import java.util.List;

import com.emc.storageos.db.client.model.NamedURI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emc.storageos.db.client.DbClient;
import com.emc.storageos.db.client.model.Operation;
import com.emc.storageos.db.client.model.remotereplication.RemoteReplicationGroup;
import com.emc.storageos.db.client.model.remotereplication.RemoteReplicationPair;
import com.emc.storageos.db.client.util.CustomQueryUtility;
import com.emc.storageos.exceptions.DeviceControllerException;
import com.emc.storageos.storagedriver.model.remotereplication.RemoteReplicationSet;
import com.emc.storageos.svcs.errorhandling.model.ServiceCoded;
import com.emc.storageos.volumecontroller.TaskCompleter;
import com.emc.storageos.volumecontroller.impl.externaldevice.RemoteReplicationElement;

public class RemoteReplicationFailbackCompleter extends TaskCompleter {

    private static final Logger _logger = LoggerFactory.getLogger(RemoteReplicationFailbackCompleter.class);

    private DbClient dbClient;

    protected void setDbClient(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    protected DbClient getDbClient() {
        return dbClient;
    }

    private RemoteReplicationSet.ElementType elementType;
    private URI elementURI;
    /**
     * Constructor for failback completer
     *
     * @param remoteReplicationElement
     * @param opId
     */
    public RemoteReplicationFailbackCompleter(RemoteReplicationElement remoteReplicationElement, String opId) {
        elementType = remoteReplicationElement.getType();
        elementURI = remoteReplicationElement.getElementUri();

        setOpId(opId);
        addIds(Collections.singletonList(elementURI));
        switch (elementType) {
            case REPLICATION_GROUP:
                setType(RemoteReplicationGroup.class);
                break;
            case REPLICATION_PAIR:
                break;
            case REPLICATION_SET:
                break;
        }
    }


    @Override
    protected void complete(DbClient dbClient, Operation.Status status, ServiceCoded coded)
            throws DeviceControllerException {
        setDbClient(dbClient);

        _logger.info("Complete operation for {} with id {} and status {}", elementType, elementURI, status);
        try {
            if (status == Operation.Status.ready) {
                switch (elementType) {
                    case REPLICATION_GROUP:
                        RemoteReplicationGroup remoteReplicationGroup = dbClient.queryObject(RemoteReplicationGroup.class, elementURI);
                        _logger.info("Failed back group: {}", remoteReplicationGroup.getNativeId());
                        List<RemoteReplicationPair> rrPairs = CustomQueryUtility.queryActiveResourcesByRelation(dbClient, elementURI,
                                RemoteReplicationPair.class, "replicationGroup");
                        for (RemoteReplicationPair rrPair : rrPairs) {
                            rrPair.setReplicationState(RemoteReplicationSet.ReplicationState.ACTIVE.toString());
                            // change replication direction:
                            if (rrPair.getReplicationDirection() == RemoteReplicationPair.ReplicationDirection.SOURCE_TO_TARGET) {
                                rrPair.setReplicationDirection(RemoteReplicationPair.ReplicationDirection.TARGET_TO_SOURCE);
                            } else {
                                rrPair.setReplicationDirection(RemoteReplicationPair.ReplicationDirection.SOURCE_TO_TARGET);
                            }
                        }
                        remoteReplicationGroup.setReplicationState(RemoteReplicationSet.ReplicationState.ACTIVE.toString());
                        dbClient.updateObject(remoteReplicationGroup);
                        dbClient.updateObject(rrPairs);
                        _logger.info("Completed operation for {} with id {} and status {}", elementType, elementURI, status);
                        break;
                    case REPLICATION_PAIR:
                        break;
                    case REPLICATION_SET:
                        break;
                }
            }
        } catch (Exception e) {
            _logger.error(String.format(
                    "Failed to process failback completion for %s with Id: %s, OpId: %s",
                    elementType, elementURI, getOpId()), e);

        } finally {
            setStatus(dbClient, status, coded);
            updateWorkflowStatus(status, coded);
        }
    }
}
