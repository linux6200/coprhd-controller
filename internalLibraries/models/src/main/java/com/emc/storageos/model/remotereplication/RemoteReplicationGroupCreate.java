package com.emc.storageos.model.remotereplication;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.net.URI;

/**
 * Remote replication group create parameters
 */
@XmlRootElement(name = "remote_replication_group_create")
public class RemoteReplicationGroupCreate {

    // Type of storage systems in this replication group.
    private String storageSystemType;

    // Display name of this replication group (when provisioned by the system).
    private String displayName;

    // Project for this group
    private URI project;

    // Source storage system of this group
    private URI sourceSystem;

    // Target storage system of this group
    private URI targetSystem;

    // replication mode of this group
    private String replicationMode;

    // replication state of this group
    private String replicationState;

    // Defines if group consistency of link operations is enforced
    // When TRUE, link operations are supported only on group level for this group
    private Boolean isGroupConsistencyEnforced;

    @XmlElement(name = "storage_system_type")
    public String getStorageSystemType() {
        return storageSystemType;
    }

    public void setStorageSystemType(String storageSystemType) {
        this.storageSystemType = storageSystemType;
    }

    @XmlElement(name = "name")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @XmlElement(name = "source_system")
    public URI getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(URI sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    @XmlElement(name = "target_system")
    public URI getTargetSystem() {
        return targetSystem;
    }

    public void setTargetSystem(URI targetSystem) {
        this.targetSystem = targetSystem;
    }

    @XmlElement(name = "replication_mode")
    public String getReplicationMode() {
        return replicationMode;
    }

    public void setReplicationMode(String replicationMode) {
        this.replicationMode = replicationMode;
    }

    @XmlElement(name = "replication_state")
    public String getReplicationState() {
        return replicationState;
    }

    public void setReplicationState(String replicationState) {
        this.replicationState = replicationState;
    }

    @XmlElement(name = "is_group_consistency_enforced")
    public Boolean getIsGroupConsistencyEnforced() {
        return isGroupConsistencyEnforced;
    }

    public void setIsGroupConsistencyEnforced(Boolean isGroupConsistencyEnforced) {
        this.isGroupConsistencyEnforced = isGroupConsistencyEnforced;
    }

    @XmlElement(name = "project")
    public URI getProject() {
        return project;
    }

    public void setProject(URI project) {
        this.project = project;
    }
}