package com.emc.storageos.model.file;

import java.net.URI;

public class MountInfo {
    private URI hostId;
    private URI fsId;
    private String mountPath;
    private String subDirectory;
    private String securityType;
    private String tag;

    public URI getHostId() {
        return hostId;
    }

    public void setHostId(URI hostId) {
        this.hostId = hostId;
    }

    public URI getFsId() {
        return fsId;
    }

    public void setFsId(URI fsId) {
        this.fsId = fsId;
    }

    public String getMountPath() {
        return mountPath;
    }

    public void setMountPath(String mountPath) {
        this.mountPath = mountPath;
    }

    public String getSubDirectory() {
        return subDirectory;
    }

    public void setSubDirectory(String subDirectory) {
        this.subDirectory = subDirectory;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}