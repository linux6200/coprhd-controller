/*
 * Copyright (c) 2017 EMC Corporation
 * All Rights Reserved
 */
package com.emc.storageos.db.client.model.remotereplication;

import java.net.URI;

import com.emc.storageos.db.client.model.Cf;
import com.emc.storageos.db.client.model.DataObject;
import com.emc.storageos.db.client.model.FileShare;
import com.emc.storageos.db.client.model.Name;
import com.emc.storageos.db.client.model.NamedRelationIndex;
import com.emc.storageos.db.client.model.NamedURI;
import com.emc.storageos.db.client.model.Project;
import com.emc.storageos.db.client.model.ProjectResource;
import com.emc.storageos.db.client.model.RelationIndex;
import com.emc.storageos.db.client.model.Volume;

import javax.xml.bind.annotation.XmlTransient;

@Cf("RemoteReplicationPair")
public class RemoteReplicationPair extends DataObject implements ProjectResource {

    public enum ElementType {
        VOLUME,
        FILE_SYSTEM
    }

    public enum ReplicationDirection {
        SOURCE_TO_TARGET,
        TARGET_TO_SOURCE
    }

    // Element type (block or file element)
    private ElementType elementType;

    // Tenant who owns this pair
    private NamedURI tenant;

    // Project this pair is associated with
    private NamedURI project;

    // Replication direction for the pair
    private ReplicationDirection replicationDirection = ReplicationDirection.SOURCE_TO_TARGET;

    // Device nativeId of replication pair.
    private String nativeId;

    // If replication pair is part of replication group should be set to replication group URI, otherwise null.
    private URI replicationGroup;

    // Either direct replication set parent or replication set of the replication group parent.
    private URI replicationSet;

    // Replication mode of this pair.
    private String replicationMode;

    // Replication state of this pair.
    private String replicationState;

    // Replication pair source element.
    // name: ElementType.VOLUME, Element.FILE_SYSTEM
    private NamedURI sourceElement;

    // Replication pair target element.
    // name: ElementType.VOLUME, Element.FILE_SYSTEM
    private NamedURI targetElement;

    @Name("nativeId")
    public String getNativeId() {
        return nativeId;
    }

    public void setNativeId(String nativeId) {
        this.nativeId = nativeId;
        setChanged("nativeId");
    }

    @RelationIndex(cf = "RelationIndex", type = RemoteReplicationGroup.class)
    @Name("replicationGroup")
    public URI getReplicationGroup() {
        return replicationGroup;
    }

    public void setReplicationGroup(URI replicationGroup) {
        this.replicationGroup = replicationGroup;
        setChanged("replicationGroup");
    }

    @RelationIndex(cf = "RelationIndex", type = RemoteReplicationSet.class)
    @Name("replicationSet")
    public URI getReplicationSet() {
        return replicationSet;
    }

    public void setReplicationSet(URI replicationSet) {
        this.replicationSet = replicationSet;
        setChanged("replicationSet");
    }


    @NamedRelationIndex(cf = "NamedRelation", type = Project.class)
    @Name("project")
    public NamedURI getProject() {
        return project;
    }

    public void setProject(NamedURI project) {
        this.project = project;
        setChanged("project");
    }

    @Override
    @XmlTransient
    @NamedRelationIndex(cf = "NamedRelation")
    @Name("tenant")
    public NamedURI getTenant() {
        return tenant;
    }

    public void setTenant(NamedURI tenant) {
        this.tenant = tenant;
        setChanged("tenant");
    }

    @Name("replicationMode")
    public String getReplicationMode() {
        return replicationMode;
    }

    public void setReplicationMode(String replicationMode) {
        this.replicationMode = replicationMode;
        setChanged("replicationMode");
    }

    @Name("replicationState")
    public String getReplicationState() {
        return replicationState;
    }

    public void setReplicationState(String replicationState) {
        this.replicationState = replicationState;
        setChanged("replicationState");
    }

    @NamedRelationIndex(cf = "SourceElementOfReplicationPairIndex", type = DataObject.class, types = {Volume.class, FileShare.class})
    @Name("sourceElement")
    public NamedURI getSourceElement() {
        return sourceElement;
    }

    public void setSourceElement(NamedURI sourceElement) {
        this.sourceElement = sourceElement;
        setChanged("sourceElement");
    }

    @NamedRelationIndex(cf = "TargetElementOfReplicationPairIndex", type = DataObject.class, types = {Volume.class, FileShare.class})
    @Name("targetElement")
    public NamedURI getTargetElement() {
        return targetElement;
    }

    public void setTargetElement(NamedURI targetElement) {
        this.targetElement = targetElement;
        setChanged("targetElement");
    }

    @Name("elementType")
    public ElementType getElementType() {
        return elementType;
    }

    public void setElementType(ElementType elementType) {
        this.elementType = elementType;
        setChanged("elementType");
    }


    @Name("replicationDirection")
    public ReplicationDirection getReplicationDirection() {
        return replicationDirection;
    }

    public void setReplicationDirection(ReplicationDirection replicationDirection) {
        this.replicationDirection = replicationDirection;
    }

    @Override
    public String toString() {
        return String.format("RR Pair id: %s, Source volume id: %s, target volume id: %s", _id, sourceElement, targetElement);
    }
}