/*
 * Copyright (c) 2015-2016 EMC Corporation
 * All Rights Reserved
 */
package com.emc.storageos.volumecontroller.impl.file;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emc.storageos.db.client.DbClient;
import com.emc.storageos.db.client.model.FileShare;
import com.emc.storageos.db.client.model.FileShare.MirrorStatus;
import com.emc.storageos.db.client.model.Operation.Status;
import com.emc.storageos.exceptions.DeviceControllerException;
import com.emc.storageos.services.OperationTypeEnum;
import com.emc.storageos.svcs.errorhandling.model.ServiceCoded;

public class MirrorFileStartTaskCompleter extends MirrorFileTaskCompleter {
    private static final Logger _log = LoggerFactory.getLogger(MirrorFileStartTaskCompleter.class);

    public MirrorFileStartTaskCompleter(Class clazz, List<URI> ids, String opId, URI storageUri) {
        super(clazz, ids, opId, storageUri);
    }

    public MirrorFileStartTaskCompleter(Class clazz, URI id, String opId) {
        super(clazz, id, opId);
    }

    public MirrorFileStartTaskCompleter(URI sourceURI, URI targetURI, String opId) {
        super(sourceURI, targetURI, opId);
    }

    @Override
    protected void complete(DbClient dbClient, Status status, ServiceCoded coded)
            throws DeviceControllerException {
        try {
            setDbClient(dbClient);
            recordMirrorOperation(dbClient, OperationTypeEnum.START_FILE_MIRROR, status, getSourceFileShare().getId().toString());

        } catch (Exception e) {
            _log.error("Failed updating status. MirrorStart {}, for task " + getOpId(), getId(), e);
        } finally {
            super.complete(dbClient, status, coded);
        }
    }

    @Override
    protected FileShare.MirrorStatus getFileMirrorStatusForSuccess(FileShare fs) {
        if(fs.getStorageDevice().equals(getStorageUri())) {
        	_log.info("start op is success - fs name {} and mirror state {}", fs.getName(), MirrorStatus.SYNCHRONIZED.name());
            return MirrorStatus.SYNCHRONIZED;
        } else {
        	MirrorStatus mirrorStatus = MirrorStatus.valueOf(fs.getMirrorStatus());
        	_log.info("start op is success - fs name {} and mirror state {}", fs.getName(), mirrorStatus.name());
            return mirrorStatus;
        }
    }
}