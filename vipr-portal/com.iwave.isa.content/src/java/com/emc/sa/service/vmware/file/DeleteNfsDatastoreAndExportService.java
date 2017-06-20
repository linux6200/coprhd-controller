/*
 * Copyright (c) 2012-2015 iWave Software LLC
 * All Rights Reserved
 */
package com.emc.sa.service.vmware.file;

import com.emc.sa.engine.service.Service;

@Service("VMware-DeleteNfsDatastoreAndExport")
public class DeleteNfsDatastoreAndExportService extends DeleteNfsDatastoreService {
    @Override
    public void execute() throws Exception {
        StringBuffer errorMsg = new StringBuffer();
        errorMsg.append("This operation is currently not supported. Please do the below steps:\r\n")
                .append("1. Remove all Virtual Machines and data from data store using Vmware vCenter\r\n")
                .append("2. Use ViPR Controller Catalog Services \"File Services for VMware vCenter->Delete VMware NFS Datastore\", \r\n")
                .append("   \"File Storage Services ->Remove NFS Export for File System\" \r\n")
                .append(" and \"File Storage Services ->Remove File System\"");
        Exception deleteDataStoreNotSupported = new Exception(errorMsg.toString());
        throw deleteDataStoreNotSupported;
        // For this patch commenting this code
        // COP-31252
        // super.execute();
        // FileStorageUtils.deleteFileSystem(fileSystem.getId(), FileControllerConstants.DeleteTypeEnum.FULL);
    }
}
