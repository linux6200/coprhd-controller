package com.emc.storageos.model.block.export;

import java.net.URI;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;

import com.emc.storageos.model.valid.Range;
import com.google.common.base.Joiner;

@XmlRootElement(name = "export_path_policy")
public class ExportPathPolicy {
    private Integer maxPaths;
    private Integer pathsPerInitiator;
    private Integer minPaths;
    private List<URI> storagePorts;
    private String description;
    private String name;
    private Integer maxInitiatorsPerPort;
    
    @XmlElement(name = "max_paths")
    @Range(min = 1, max = 65535)
    /**
     * The maximum number of storage paths (ports) that will be provisioned.
     */
    public Integer getMaxPaths() {
        return maxPaths;
    }

    public void setMaxPaths(Integer maxPaths) {
        this.maxPaths = maxPaths;
    }

    @XmlElement(name = "paths_per_initiator")
    @Range(min = 1, max = 65535)
    /**
     * The number of storage paths (ports) that will be assigned and zoned to each Initiator.
     */
    public Integer getPathsPerInitiator() {
        return pathsPerInitiator;
    }

    public void setPathsPerInitiator(Integer pathsPerInitiator) {
        this.pathsPerInitiator = pathsPerInitiator;
    }

    @XmlElement(name = "min_paths")
    @Range(min = 1, max = 65535)
    /**
     * The minimum number of storage paths that must be
     * provisioned for a successful export.
     */
    public Integer getMinPaths() {
        return minPaths;
    }

    public void setMinPaths(Integer minPaths) {
        this.minPaths = minPaths;
    }

    @XmlElementWrapper(name = "storage_ports", required = false)
    /**
     * Optional list of storage ports to be used for the export.
     * Any ports that are listed must also be available in the applicable
     * virtual array(s) for the export group in order to be considered
     * for allocation.
     */
    @XmlElement(name = "storage_port")
    public List<URI> getStoragePorts() {
        return storagePorts;
    }

    public void setStoragePorts(List<URI> storagePorts) {
        this.storagePorts = storagePorts;
    }
    public void log(Logger log) {
        String maxPathsString = getMaxPaths() != null ? getMaxPaths().toString() : "null";
        String minPathsString = getMinPaths() != null ? getMinPaths().toString() : "null";
        String pathsPerInitiatorString = getPathsPerInitiator() != null ? getPathsPerInitiator().toString() : "null";
        log.info(String.format("max_paths %s min_paths %s paths_per_initiator %s", maxPathsString, minPathsString, pathsPerInitiatorString));
        if (getStoragePorts() != null && !getStoragePorts().isEmpty()) {
            String ports = Joiner.on(" ").join(getStoragePorts());
            log.info("Ports: " + ports);
        } else {
            log.info("Ports not specified");
        }
        
    }

    @XmlElement(name = "description")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    @XmlElement(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * The number of Initiators that will be assigned and zoned to each Port.
     */
    @XmlElement(name = "max_initiators_per_port", required = false)
    @Range(min = 1, max = 65535)
    public Integer getMaxInitiatorsPerPort() {
        return maxInitiatorsPerPort;
    }
    public void setMaxInitiatorsPerPort(Integer maxInitiatorsPerPort) {
        this.maxInitiatorsPerPort = maxInitiatorsPerPort;
    }

}