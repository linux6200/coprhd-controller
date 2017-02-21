/**
 * Copyright (c) 2015 EMC Corporation
 * All Rights Reserved
 */
package com.emc.storageos.scaleio.api.restapi.response;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Fault Set attributes
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScaleIOFaultSet {
    private String id;
    private String name;
    private String protectionDomain;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProtectionDomain() {
        return protectionDomain;
    }

    public void setProtectionDomain(String protectionDomain) {
        this.protectionDomain = protectionDomain;
    }

}
