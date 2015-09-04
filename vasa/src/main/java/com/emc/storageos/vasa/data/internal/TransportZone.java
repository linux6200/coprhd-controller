/*
 * Copyright 2015 EMC Corporation
 * All Rights Reserved
 */
/* 
 Copyright (c) 2012 EMC Corporation
 All Rights Reserved

 This software contains the intellectual property of EMC Corporation
 or is licensed to EMC Corporation from third parties.  Use of this
 software and the intellectual property contained therein is expressly
 imited to the terms and conditions of the License Agreement under which
 it is provided by or on behalf of EMC.
 */
package com.emc.storageos.vasa.data.internal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TransportZone")
public class TransportZone {
    @XmlElement
    String id;
    @XmlElement
    boolean inactive;
    @XmlElement
    String label;
    @XmlElement
    Endpoint endpoints;
    @XmlElement
    String neighborhood;
    @XmlElement
    String transportType;

    @Override
    public String toString() {
        String s = new String();

        s += "TransportZone" + "\n";
        s += "\tid: " + id + "\n";
        s += "\tinactive: " + Boolean.toString(inactive) + "\n";
        s += "\tlabel: " + label + "\n";
        if (endpoints != null)
            s += "\tendpoints:" + endpoints.toString() + "\n";
        s += "\tneighborhood: " + neighborhood + "\n";
        s += "\ttransportType: " + transportType + "\n";
        return s;
    }

}
