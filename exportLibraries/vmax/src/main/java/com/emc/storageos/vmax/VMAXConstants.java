/*
 * Copyright (c) 2017 Dell EMC
 * All Rights Reserved
 */
package com.emc.storageos.vmax;

import java.net.URI;

public interface VMAXConstants {

    static final String AUTH_TOKEN = "X-XIO-AUTH-TOKEN";
    static final String AUTH_TOKEN_HEADER = "X-XIO-AUTH-TOKEN-HEADER";
    static final String HTTPS_URL = "https";
    static final String HTTP_URL = "http";

    static final String UNIVMAX_BASE_URI = "/univmax/restapi";
    static final String UNIVMAX_SYSTEM_BASE_URI = UNIVMAX_BASE_URI + "/system";
    static final String UNIVMAX_SYSTEM_VERSION_URI = UNIVMAX_SYSTEM_BASE_URI + "/version";
    static final String UNIVMAX_SYSTEM_SYMM_LIST_URI = UNIVMAX_SYSTEM_BASE_URI + "/symmetrix";
    static final String UNIVMAX_SYSTEM_SYMM_GET_URI = UNIVMAX_SYSTEM_SYMM_LIST_URI + "/%1$s";

    static final String UNIVMAX_MIGRATION_BASE_URI = UNIVMAX_BASE_URI + "/84/migration/symmetrix";
    static final String VALIDATE_ENVIRONMENT_URI = UNIVMAX_MIGRATION_BASE_URI + "/%1$s/environment/%2$s";
    static final String GET_MIGRATION_ENVIRONMENT_URI = UNIVMAX_MIGRATION_BASE_URI + "/%1$s/environment";

    static final String CREATE_MIGRATION_ENVIRONMENT_URI = UNIVMAX_MIGRATION_BASE_URI + "/%1$s";

    static final String GET_MIGRATION_STORAGEGROUPS_URI = UNIVMAX_MIGRATION_BASE_URI + "/%1$s/storagegroup";
    static final String GET_MIGRATION_STORAGEGROUP_URI = UNIVMAX_MIGRATION_BASE_URI + "/%1$s/storagegroup/%2$s";

    public static URI getValidateEnvironmentURI(String sourceArraySerialNumber, String targetArraySerialNumber) {
        return URI.create(String.format(VALIDATE_ENVIRONMENT_URI, sourceArraySerialNumber, targetArraySerialNumber));
    }

    public static URI getBaseURI(String ipAddress, int port, boolean isSSL) {
        return URI.create(String.format("%1$s://%2$s:%3$d", isSSL ? HTTPS_URL : HTTP_URL, ipAddress, port));
    }

    public static URI getMigrationEnvironmentURI(String sourceArraySerialNumber) {
        return URI.create(String.format(GET_MIGRATION_ENVIRONMENT_URI, sourceArraySerialNumber));
    }

    public static URI createMigrationEnvornmentURI(String sourceArraySerialNumber) {
        return URI.create(String.format(CREATE_MIGRATION_ENVIRONMENT_URI, sourceArraySerialNumber));
    }

    public static URI getMigrationStorageGroupsURI(String sourceArraySerialNumber) {
        return URI.create(String.format(GET_MIGRATION_STORAGEGROUPS_URI, sourceArraySerialNumber));
    }

    public static URI getMigrationStorageGroupURI(String sourceSymmetrixId, String storageGroupName) {
        return URI.create(String.format(GET_MIGRATION_STORAGEGROUP_URI, sourceSymmetrixId, storageGroupName));
    }

    public static URI getVersionURI() {
        return URI.create(UNIVMAX_SYSTEM_VERSION_URI);
    }

    public static URI getSystemListURI() {
        return URI.create(UNIVMAX_SYSTEM_SYMM_LIST_URI);
    }

    public static URI getSystemGetURI(String symmId) {
        return URI.create(String.format(UNIVMAX_SYSTEM_SYMM_GET_URI, symmId));
    }

}