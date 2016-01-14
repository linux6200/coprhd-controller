/*
 * Copyright (c) 2013-2014 EMC Corporation
 * All Rights Reserved
 */

package com.emc.storageos.svcs.errorhandling.resources;

import static com.emc.storageos.svcs.errorhandling.resources.ServiceCode.Action.FATAL;
import static com.emc.storageos.svcs.errorhandling.resources.ServiceCode.Action.NON_APPLICABLE;
import static com.emc.storageos.svcs.errorhandling.resources.ServiceCode.Action.RETRY;
import static com.emc.storageos.svcs.errorhandling.utils.Messages.localize;

import java.util.Locale;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import com.emc.storageos.svcs.errorhandling.annotations.MessageBundle;
import com.sun.jersey.api.client.ClientResponse;

/**
 * Enum to define all the service codes to be used in ViPR in error conditions.
 * 
 * If you are defining a new service code, remember to add the English message associated to the service code in ServiceCode.properties
 * 
 * For more information or to see an example, check the Developers Guide section in the Error Handling Wiki page:
 * http://confluence.lab.voyence.com/display/OS/Error+Handling+Framework+and+Exceptions+in+ViPR
 */
@MessageBundle
public enum ServiceCode {
    UNFORSEEN_ERROR(999),

    // *********************************************************************************
    // New style of Service codes
    // We are in the process of migrating the code to use the new style of
    // service codes.
    // If you are adding a new service code, please use the non deprecated
    // constructor
    // *********************************************************************************
    // API Bad Request Errors (1000s)
    API_PARAMETER_INVALID_URI(1002),
    API_UNSUPPORTED_MEDIA_TYPE(1003),
    API_PARAMETER_NOT_FOUND(1004),
    API_PARAMETER_MISSING(1005),
    API_PARAMETER_INACTIVE(1006),
    API_METHOD_NOT_SUPPORTED(1007),
    API_PARAMETER_INVALID(1008),
    API_BAD_HEADERS(1009),
    API_MARKED_FOR_DELETION(1010),
    API_PARAMETER_INVALID_VPOOL(1011),
    API_UNSUPPORTED_CHANGE(1012),
    API_BAD_REQUEST(1013),
    API_PARAMETER_INVALID_RANGE(1014),
    API_RESOURCE_EXISTS(1015),
    API_PARAMETER_INVALID_ZONE(1016),
    API_CANNOT_REGISTER(1017),
    API_NOT_REGISTERED(1018),
    API_RESOURCE_MISSING(1019),
    API_RESOURCE_BEING_REFERENCED(1020),
    API_NO_PLACEMENT_FOUND(1021),
    API_ALREADY_REGISTERED(1022),
    API_BAD_VERSION(1023),
    API_CANNOT_DELETE(1025),
    API_INSUFFICIENT_PERMISSIONS(1026),
    API_EXCEEDING_ASSIGNMENT_LIMIT(127),
    API_PARAMETER_INVALID_ROLE(1028),
    API_PARAMETER_INVALID_TIME_FORMAT(1029),
    API_VOLUME_VPOOL_CHANGE_DISRUPTIVE(1030),
    API_EXCEEDING_LIMIT(1031),
    API_INSUFFICIENT_QUOTA(1032),
    API_UNKNOWN_RP_CONFIGURATION(1033),
    API_PLACEMENT_ERROR(1034),
    API_BAD_ATTACHMENT(1035),
    API_INVALID_VOLUME_TYPE(1036),
    API_INVALID_PROTECTION_VPOOLS(1037),
    API_PARAMETER_INVALID_FSNAME(1038),
    API_INVALID_VARRAY_NETWORK_CONFIGURATION(1039),
    API_INVALID_VPOOL_FOR_INGESTION(1040),
    API_NO_DOWNLOAD_IN_PROGRESS(1041),
    API_UNSUPPORTED_INGESTED_VOLUME_OPERATION(1042),
    API_INVALID_MAX_CONTINUOUS_COPIES(1042),
    API_INVALID_HIGH_AVAILABILITY_FOR_MIRROR_VPOOL(1043),
    API_INVALID_VARARY_CONTINUOUS_COPIES_VPOOL(1044),
    API_INVALID_ACTION_FOR_VPLEX_MIRRORS(1045),
    API_VPOOL_IN_USE_AS_CONTINUOUS_COPIES_VPOOL(1046),
    API_NOT_INITIALIZED(1047),
    API_INVALID_CONTINUOUS_COPIES_VPOOL(1048),
    API_PARAMETER_REQUIRED(1049),
    API_CANNOT_DEREGISTER(1050),
    API_RESOURCE_CANNOT_BE_DELETE_DUE_TO_UNREACHABLE_VDC(1051),
    API_INVALID_OBJECT(1052),
    API_PARAMETER_INVALID_QDNAME(1053),
    API_AUTH_KEYSTONE_PROVIDER_CREATE_NOT_ALLOWED(1054),
    API_DUPLICATE_EXPORT_GROUP_NAME_SAME_PROJECT_AND_VARRAY(1054),
    API_DELETION_IN_PROGRESS(1055),
    API_TASK_EXECUTION_IN_PROGRESS(1056),
    API_PRECONDITION_FAILED(1057),
    // API Not Founds (2000s)
    API_URL_ENTITY_NOT_FOUND(2000),
    API_URL_ENTITY_INACTIVE(2001),

    // API Forbidden (3000s)
    SECURITY_INSUFFICIENT_PERMISSIONS(3000),
    LICENSE_OPERATION_FORBIDDEN(3001),

    // API Unauthorized (4000s)
    SECURITY_UNAUTHORIZED_OPERATION(4000),

    // API Method not allowed (5000s)

    // API Service unavailable (6000s)
    API_SERVICE_UNAVAILABLE(6000),
    API_VERSION_OF_IMAGE_UNKNOWN_SO_FAR(6001),

    /*
     * Nginx load balancer error codes. 6400-6507
     * These codes represent the standard HTTP status codes Nginx is capable of returning.
     * They will be used in the JSON/XML responses generated by Nginx for any HTTP errors it returns.
     */
    NGINX_BAD_REQUEST(6400),
    NGINX_UNAUTHORIZED(6401),
    NGINX_PAYMENT_REQUIRED(6402),
    NGINX_NOT_FOUND(6404), // 403 is merged with 404
    NGINX_METHOD_NOT_ALLOWED(6405),
    NGINX_NOT_ACCEPTABLE(6406),
    NGINX_CONFLICT(6409),
    NGINX_GONE(6410),
    NGINX_LENGTH_REQUIRED(6411),
    NGINX_PRECONDITION_FAILED(6412),
    NGINX_REQUEST_ENTITY_TOO_LARGE(6413),
    NGINX_REQUEST_URI_TOO_LONG(6414),
    NGINX_UNSUPPORTED_MEDIA(6415),
    NGINX_REQUESTED_RANGE_NOT_SATISFIABLE(6416),
    NGINX_REQUEST_HEADER_TOO_LARGE(6494),
    NGINX_CERT_ERROR(6495),
    NGINX_NO_CERT(6496),
    NGINX_HTTP_TO_HTTPS(6497),
    NGINX_INTERNAL_SERVER_ERROR(6500), // 504 is merged with 500
    NGINX_NOT_IMPLEMENTED(6501),
    NGINX_SERVICE_UNAVAILABLE(6503), // 502 is merged with 503
    NGINX_INSUFFICIENT_STORAGE(6507),

    // API Internal Server Errors (7000s)
    API_INTERNAL_SERVER_ERROR(7000),
    API_JAXB_CONTEXT_ERROR(7001),
    API_AUDIT_LOG_ERROR(7002),
    API_METERING_STAT_ERROR(7003),
    API_EVENT_RETRIEVER_ERROR(7004),
    API_INGESTION_ERROR(7005),
    API_RP_VOLUME_DELETE_ERROR(7006),
    API_RP_VOLUME_CREATE_ERROR(7007),
    DOWNLOAD_ERROR(7008),

    // Fatal Database Client Errors (8000 - 8499):
    DBSVC_ERROR(8000),
    DBSVC_ENTITY_NOT_FOUND(8001),
    DBSVC_SERIALIZATION_ERROR(8003),
    DBSVC_DESERIALIZATION_ERROR(8004),
    DBSVC_PURGE_ERROR(8005),
    DBSVC_QUERY_ERROR(8006),
    DBSVC_ANNOTATION_ERROR(8007),
    DBSVC_GEO_UPDATE_ERROR(8008),
    DBSVC_FIELD_LENGTH_ERROR(8009),
    
    // Retryable Database Client Errors (8500 - 8999):
    DBSVC_CONNECTION_ERROR(8500),
    DBSVC_DUMMY_ERROR(8501),

    // Fatal Coordinator Client Errors (9000 - 9499):
    COORDINATOR_UNABLE_TO_QUEUE_JOB(9000),
    COORDINATOR_ERROR(9001),
    COORDINATOR_DECODING_ERROR(9002),
    COORDINATOR_INVALID_REPO_INFO(9003),
    COORDINATOR_NOTCONNECTABLE_ERROR(9004),

    // Retryable Coordinator Client Errors (9500 - 9999):
    COORDINATOR_QUEUE_TOO_BUSY(9500),
    COORDINATOR_SVC_NOT_FOUND(9501),
    COORDINATOR_SITE_NOT_FOUND(9502),

    // Fatal Security Client Errors (10000 - 10499):
    SECURITY_ERROR(10000),
    SECURITY_AUTH_TOKEN_ENCODING_ERROR(10001),
    SECURITY_PARAMETER_MISSING(10004),
    SECURITY_AUTH_SERVICE_ENCODING_ERROR(10005),

    // Retryable Security Client Errors (10500 - 10999):
    SECURITY_REQUIRED_SERVICE_UNAVAILABLE(10500),
    SECURITY_KEYSTORE_UNAVAILABLE(10501),
    SECURITY_AUTH_TIMEOUT(10502),

    // Fatal Controller Client Errors (11000 - 11499):
    CONTROLLER_CLIENT_UNABLE_TO_SCHEDULE_JOB(11000),
    CONTROLLER_CLIENT_UNABLE_TO_LOCATE_DEVICE_CONTROLLER(11001),
    CONTROLLER_CLIENT_UNABLE_TO_SCAN_JOB(11002),
    CONTROLLER_CLIENT_UNABLE_TO_MONITOR_JOB(11003),

    // Fatal File Error (5001 - 5999)
    FILE_CONTROLLER_ERROR(5001),
    FILE_CONNECTION_ERROR(5002),
    BLOCK_CONTROLLER_ERROR(5101),

    // Retryable Controller Client Errors (11500 - 11999):

    // Device Controller Errors (12000 - 12999)
    CONTROLLER_JOB_ERROR(12000),
    DISCOVERY_ERROR(12001),
    METERING_STORAGE_ERROR(12002),
    MONITORING_STORAGE_ERROR(12003),
    CONTROLLER_UNABLE_DELETE_INITIATOR_GROUPS(12004),
    CONTROLLER_INVALID_URI(12005),
    CONTROLLER_ENTITY_INACTIVE(12006),
    CONTROLLER_ENDPOINTS_ERROR(12007),
    TRANSPORT_ZONE_ERROR(12008),
    CONTROLLER_ENTITY_NOT_FOUND(12009),
    CONTROLLER_VOLUME_REUSE_ERROR(12010),
    CONTROLLER_UNEXPECTED_VOLUME(12011),
    VOLUME_CAN_NOT_BE_EXPANDED(12014),
    CONTROLLER_INVALID_SYSTEM_TYPE(12015),
    CONTROLLER_INITIATORS_WITH_DIFFERENT_OSTYPE(12016),
    CONTROLLER_MIXING_CLUSTERED_AND_NON_CLUSTERED_INITIATORS(12017),
    CONTROLLER_NON_CLUSTER_EXPORT_WITH_INITIATORS_IN_DIFFERENT_IGS(12018),
    CONTROLLER_EXISTING_IG_HAS_DIFFERENT_PORTS(12019),
    CONTROLLER_EXISTING_IG_DOES_NOT_HAVE_SAME_PORTS(12020),
    CONTROLLER_VMAX_STORAGE_GROUP_NOT_FOUND(12021),
    CONTROLLER_VMAX_MULTIPLE_MATCHING_COMPUTE_RESOURCE_MASKS(12022),
    CONTROLLER_VMAX_EXPORT_GROUP_CREATE_ERROR(12023),
    CONTROLLER_ERROR_ASSIGNING_STORAGE_PORTS(12024),
    CONTROLLER_VMAX_FAST_EXPORT_STORAGE_GROUP_ALREADY_IN_MASKINGVIEW(12025),
    CONTROLLER_VMAX_CONCURRENT_REMOVE_FROM_SG_CAUSES_EMPTY_SG(12026),
    CONTROLLER_VMAX_MASK_SUPPORTS_SINGLE_HOST_ERROR(12027),
    VCENTER_CONTROLLER_ERROR(12028),
    CONTROLLER_JOB_ABORTED(12029),
    CONTROLLER_LOCK_RETRY_EXCEPTION(12030),

    // Isilon errors (13000 - 13999):
    ISILON_ERROR(13000),
    ISILON_DIR_ERROR(13001),
    ISILON_CONNECTION_ERROR(13002),
    ISILON_INFO_ERROR(13003),
    ISILON_RESOURCE_ERROR(13004),
    ISILON_STATS_ERROR(13005),

    // Workflow errors (14000 - 14999):
    WORKFLOW_STEP_ERROR(14000),
    WORKFLOW_TERMINATED_ABNORMALLY(14001),
    WORKFLOW_STEP_CANCELLED(14002),
    WORKFLOW_NOT_FOUND(14003),
    WORKFLOW_IN_WRONG_STATE(14004),
    WORKFLOW_CANNOT_BE_ROLLED_BACK(14005),

    // Dispatcher errors (15000 - 15999):
    DISPATCHER_UNABLE_FIND_CONTROLLER(15000),

    // Smis errors (16000 - 16999):
    SMIS_COMMAND_ERROR(16000),
    STORAGE_PROVIDER_UNAVAILABLE(16001),

    // NetApp errors (17000 - 17999):
    NETAPP_ERROR(17000),
    NETAPP_FS_CREATE_ERROR(17001),
    NETAPP_FS_DELETE_ERROR(17002),
    NETAPP_SHARE_CREATE_ERROR(17003),
    NETAPP_SHARE_DELETE_ERROR(17004),
    NETAPP_FS_EXPAND_ERROR(17005),
    NETAPP_SNAPSHOT_CREATE_ERROR(17006),
    NETAPP_FS_RESTORE_ERROR(17007),
    NETAPP_FS_EXPORT_ERROR(17008),
    NETAPP_FS_UNEXPORT_ERROR(17009),
    NETAPP_QTREE_CREATE_ERROR(17010),
    NETAPP_QTREE_DELETE_ERROR(17011),
    NETAPP_QTREE_UPDATE_ERROR(17012),
    NETAPP_CIFS_SHARE_ACL_UPDATE_ERROR(17013),
    NETAPP_CIFS_SHARE_ACL_DELETE_ERROR(17014),
    NETAPP_INVALID_OPERATION(17015),

    // VPlex errors (18000 - 18999):
    VPLEX_API_ERROR(18000),
    VPLEX_UNSUPPORTED_ARRAY(18001),
    VPLEX_VARRAY_HAS_MIXED_CLUSTERS(18002),
    VPLEX_DATA_COLLECTION_EXCEPTION(18003),
    VPLEX_UNMANAGED_VOLUME_DISCOVERY_EXCEPTION(18004),
    VPLEX_UNMANAGED_VOLUME_INGEST_EXCEPTION(18005),
    VPLEX_CANT_FIND_REQUESTED_VOLUME(18006),
    VPLEX_UNMANAGED_EXPORT_MASK_EXCEPTION(18007),
    VPLEX_API_CONCURRENCY_ERROR(18008),

    // Recover Point errors (19000 - 19999):
    RECOVER_POINT_ERROR(19000),
    RECOVER_POINT_LICENSE_ERROR(19001),

    // VNX errors (20000 - 20999):
    VNX_ERROR(20000),
    VNXFILE_COMM_ERROR(20001),
    VNXFILE_FILESYSTEM_ERROR(20002),
    VNXFILE_SNAPSHOT_ERROR(20003),
    VNXFILE_EXPORT_ERROR(20004),
    VNXFILE_QUOTA_DIR_ERROR(20005),
    VNXFILE_SHARE_ERROR(20006),

    // CIM Adapter errors (21000 - 21999):
    CIM_ADAPTER_ERROR(21000),
    CIM_CONNECTION_MANAGER_ERROR(21001),

    // Network Device Controller (22000 - 22999):
    CONTROLLER_NETWORK_SESSION_LOCKED(22001),
    CONTROLLER_NETWORK_SESSION_TIMEOUT(22002),
    CONTROLLER_NETWORK_ERROR(22003),
    CONTROLLER_NETWORK_OBJ_ERROR(22004),
    CONTROLLER_NETWORK_DB_ERROR(22005),
    CONTROLLER_CANNOTLOCATEPORTS(22006),

    // Placement errors (23000 - 23999)
    // NOTE: these are called from both the api and controller paths
    PLACEMENT_NUMPATHSLTNETWORKS(23000),
    PLACEMENT_CANNOTALLOCATEPORTS(23001),
    PLACEMENT_NOSTORAGEPORTSINNETWORK(23002),
    PLACEMENT_CANNOTALLOCATEMINPATHS(23003),
    PLACEMENT_HOSTHASFEWERTHANMINPATHS(23004),
    PLACEMENT_HOSTHASUNUSEDINITIATORS(23005),
    PLACEMENT_INSUFFICENTREDUNDANCY(23006),

    // Device Data Collection (Discovery/Scan/Metering/etc) (24000-24999)
    CONTROLLER_DATA_COLLECTION_ERROR(24001),

    // HDS Errors (25000-25999)
    HDS_INVALID_RESPONSE(25000),
    ERROR_RESPONSE_RECEIVED(25001),
    HDS_ASYNC_TASK_INVALID_RESPONSE(25002),
    HDS_ASYNC_TASK_MAXIMUM_RETRIES_EXCEED(25003),
    HDS_ASYNC_TASK_WITH_ERROR_RESPONSE(25004),
    HDS_COMMAND_ERROR(25005),
    HDS_RESPONSE_PARSING_FAILED(25006),
    HDS_SCAN_FAILED(25007),

    // HDS Provisioning Errors
    HDS_VOLUME_CREATION_FAILED(25054),
    HDS_NOT_ABLE_TO_ADD_INITIATOR(25055),
    HDS_NOT_ABLE_TO_ADD_HSD(25056),
    HDS_NOT_ABLE_TO_ADD_VOLUME_TO_HSD(25057),
    HDS_NOT_ABLE_TO_GET_FREE_LUN_INFO(25058),
    HDS_NOT_ABLE_ADD_HOST(25059),
    HDS_HSD_ALREADY_EXISTS_WITH_SAME_INITIATORS(25060),
    HDS_FAILED_TO_REGISTER_HOST(25061),
    HDS_FAILED_TO_GET_HOST_ONFO(25062),
    HDS_VOLUME_DELETION_FAILED(25063),
    HDS_VOLUME_INFO_FAILED(25064),
    HDS_UNSUPPORTED_HOST_WITH_BOTH_FC_ISCSI_INITIATORS(25065),
    HDS_NO_TARGET_PORTS_AVAILABLE(25066),
    UNABLE_TO_GENERATE_INPUT_XML(25067),
    UNABLE_TO_GENERATE_INPUT_XML_DUE_TO_NO_OPERATIONS(25068),
    UNABLE_TO_GENERATE_INPUT_XML_DUE_TO_UNSUPPORTED_MODEL(25069),
    HDS_UNSUPPORTED_OPERATION(25070),
    UNABLE_TO_PROCESS_REQUEST_DUE_TO_UNAVAILABLE_FREE_LUNS(25071),
    XTREMIO_API_ERROR(25072),
    XTREMIO_DISCOVERY_ERROR(25073),
    XTREMIO_IG_NOT_FOUND(25074),
    HDS_REPLICATION_CONFIGURATION_PROBLEM(25075),
    HDS_EXPORT_GROUP_UPDATE_FAILURE(25076),

    // DataDomain errors
    DATADOMAIN_API_ERROR(25100),
    DATADOMAIN_RESOURCE_NOT_FOUND(25101),
    DATADOMAIN_INVALID_PARAMETER(25102),
    DATADOMAIN_INVALID_OPERATION(25103),

    CONTROLLER_COMPUTESYSTEM_ERROR(26000),

    // Syssvc Errors (30000 - 30999)
    SYS_CLUSTER_STATE_NOT_STABLE(30000),
    SYS_RELEASE_LOCK_ERROR(30001),
    SYS_IS_NULL_OR_EMPTY(30002),
    SYS_IO_WRITE_ERROR(30003),
    SYS_IO_READ_ERROR(30004),
    SYS_CREATE_OBJECT_ERROR(30005),
    SYS_GET_OBJECT_ERROR(30006),
    SYS_SET_OBJECT_ERROR(30007),
    SYS_UPDATE_OBJECT_ERROR(30008),
    SYS_WAIT_TO_COMPLETE_ERROR(30009),
    SYS_SELF_TEST_ERROR(30010),
    SYS_UPLOAD_INSTALL_ERROR(30011),
    SYS_WAKEUP_ERROR(30012),
    SYS_CONNECTEMC_NOT_CONFIGURED(30013),
    SYS_INITIALIZE_SSL_CONTENT_ERROR(30014),
    SYS_NO_NODES_AVAILABLE(30015),
    SYS_INVALID_OBJECT(30016),
    SYS_DOWNLOAD_IMAGE_ERROR(30017),
    SYS_INTERNAL_INVALID_LOCK_OWNER(30018),
    SYS_INTERNAL_INVALID_SOFTWARE_VERSION(30020),
    SYS_INTERNAL_LOCAL_REPO_ERROR(30021),
    SYS_INTERNAL_REMOTE_REPO_ERROR(30022),
    SYS_INTERNAL_SYS_CLIENT_ERROR(30023),
    SYS_INTERNAL_ERROR(30024),
    SYS_INTERNAL_COORDINATOR_ERROR(30025),
    SYS_SERVICE_BUSY(30026),
    SYS_POWEROFF_ERROR(30027),
    SYS_IMAGE_DOWNLOAD_FAILED(30028),
    SYS_INTERNAL_SERVICE_RESTART(30029),
    SYS_DATANODE_FAILCONNECT_CONTROLLER(30030),
    SYS_RECOVERY_TRIGGER_FAILED(30031),
    SYS_RECOVERY_REPAIR_FAILED(30032),
    SYS_RECOVERY_REBUILD_FAILED(30033),
    SYS_RECOVERY_ADD_LISTENER_FAILED(30034),
    SYS_RECOVERY_GET_LOCK_FAILED(30035),
    SYS_RECOVERY_NEW_NODE_FAILURE(30036),
    SYS_BACKUP_LIST_EXTERNAL_FAILED(30037),
    SYS_BACKUP_QUERY_EXTERNAL_FAILED(30038),
    SYS_IPRECONFIG_TRIGGER_FAILED(30040),

    SYS_DR_ADD_STANDBY_PRECHECK_FAILED(30041),
    SYS_DR_NAT_CHECK_FAILED(30042),
    SYS_DR_ADD_STANDBY_FAILED(30043),
    SYS_DR_ADD_STANDBY_TIMEOUT(30044),
    SYS_DR_CONFIG_STANDBY_FAILED(30045),
    SYS_DR_REMOVE_STANDBY_PRECHECK_FAILED(30046),
    SYS_DR_REMOVE_STANDBY_FAILED(30047),
    SYS_DR_REMOVE_STANDBY_RECONFIG_FAILED(30048),
    SYS_DR_PAUSE_STANDBY_FAILED(30049),
    SYS_DR_PAUSE_STANDBY_TIMEOUT(30050),
    SYS_DR_PAUSE_STANDBY_PRECHECK_FAILED(30051),
    SYS_DR_PAUSE_STANDBY_RECONFIG_FAILED(30052),
    SYS_DR_RESUME_STANDBY_FAILED(30053),
    SYS_DR_RESUME_STANDBY_TIMEOUT(30054),
    SYS_DR_RESUME_STANDBY_PRECHECK_FAILED(30055),
    SYS_DR_RESUME_STANDBY_RECONFIG_FAILED(30056),
    SYS_DR_DATA_SYNC_TIMEOUT(30057),
    SYS_DR_SWITCHOVER_PRECHECK_FAILED(30058),
    SYS_DR_SWITCHOVER_FAILED(30059),
    SYS_DR_SWITCHOVER_ACTIVE_FAILED_TIMEOUT(30060),
    SYS_DR_SWITCHOVER_STANDBY_FAILED_TIMEOUT(30061),
    SYS_DR_ACQUIRE_OPERATION_LOCK_FAILED(30062),
    SYS_DR_CONCURRENT_OPERATION_NOT_ALLOWED(30063),
    SYS_DR_FAILOVER_FAILED(30064),
    SYS_DR_FAILOVER_PRECHECK_FAILED(30065),
    SYS_DR_FAILOVER_RECONFIG_FAIL(30066),

    SYS_INTERNAL_SERVICE_NAME_NOT_FOUND(30067),
    SYS_DR_CREATE_VIPR_CLIENT_FAILED(30068),
    SYS_DR_UPDATE_SITE_FAILED(30069),

    // Objsvc errors (40000 - 40999)
    OBJ_DATASTORE_CREATE_ERROR(40000),
    OBJ_DATASTORE_DELETE_ERROR(40001),
    OBJ_PROJECT_INVALID(40002),
    OBJ_PROJECT_NOT_FOUND_FOR_NAMESPACE(40003),
    OBJ_VPOOL_INVALID(40004),
    OBJ_VPOOL_NOT_FOUND_FOR_NAMESPACE(40005),
    OBJ_VPOOL_NOT_COMPATIBLE(40006),
    OBJ_VPOOL_EMPTY(40007),
    OBJ_BUCKET_EXISTS(40008),
    OBJ_BUCKETNAME_INVALID(40009),
    OBJ_NOT_BUCKT_OWNER(40010),
    OBJ_VPOOL_TYPE_INVALID(40011),
    DATASERVICE_INVALID_VARRAY(40012),
    OBJ_SYSVARRAY_NOT_DEFINED(40013),
    OBJ_SYSTABLE_NOT_CREATED_YET(40014),
    OBJ_VPOOL_LISTS_NOT_MUTUALLY_EXCLUSIVE(40015),

    // Cinder errors ( 41000 - 41999)
    CINDER_OPERATION_FAILED(41000),
    CINDER_JOB_FAILED(41001),
    CINDER_VOLUME_NOT_FOUND(41002),
    CINDER_VOLUME_CREATE_FAILED(41003),
    CINDER_SNAPSHOT_NOT_FOUND(41004),
    CINDER_SNAPSHOT_CREATE_FAILED(41005),
    CINDER_VOLUME_CLONE_FAILED(41006),
    CINDER_CREATE_VOLUME_FROM_SNAPSHOT_FAILED(41007),
    CINDER_VOLUME_ATTACH_FAILED(41008),
    CINDER_VOLUME_DETACH_FAILED(41009),
    CINDER_VOLUME_EXPAND_FAILED(41010),
    CINDER_VOLUME_DELETE_FAILED(41011),
    CINDER_SNAPSHOT_DELETE_FAILED(41012),

    // Vnxe errors (42000 - 42999):
    VNXE_COMMAND_ERROR(42000),
    VNXE_UNEXPECTED_DATA(42001),
    VNXE_DISCOVERY_ERROR(42002),
    IMAGE_SERVER_CONTROLLER_ERROR(49000),

    // Geosvc errors (50000 - 60000):
    GEOSVC_VDC_CONNECT_ERROR(50000),
    GEOSVC_INTERNAL_ERROR(50001),
    GEOSVC_ACQUIRED_LOCK_FAIL(50002),
    GEOSVC_VDC_VERSION_INCOMPATIBLE(50003),
    GEOSVC_FEDERATION_UNSTABLE(50004),
    GEOSVC_GEODB_CONFIG_FAILED(50005),
    GEOSVC_UNSTABLE_VDC_ERROR(50006),
    GEOSVC_VESION_ERROR(50007),
    GEOSVC_WRONG_STATE(50008),
    GEOSVC_PRECHECK_ERROR(50009),
    GEOSVC_SECURITY_ERROR(50010),
    GEOSVC_POSTCHECK_ERROR(50011),
    GEOSVC_INVALID_ENDPOINT(50012),
    GEOSVC_CONNECTIVITY_ERROR(50013),
    GEOSVC_REMOTEVDC_EXCEPTION(50014),
    GEOSVC_CONNECTVDC_INVALID_STATUS(51001),
    GEOSVC_CONNECTVDC_SYNC_CERT_ERROR(51004),
    GEOSVC_CONNECTVDC_GEN_CERT_CHAIN_ERROR(51005),
    GEOSVC_CONNECTVDC_STATUS_UPDATE_ERROR(51008),
    GEOSVC_CONNECTVDC_REMOVE_ROOT_ROLES_ERROR(51009),
    GEOSVC_REMOVEVDC_SYNC_CONFIG_ERROR(52002),
    GEOSVC_REMOVEVDC_INVALID_STATUS(52004),
    GEOSVC_UPDATEVDC_ERROR(53001),
    GEOSVC_UPDATEVDC_INVALID_STATUS(53002),
    GEOSVC_DISCONNECTVDC_INVALID_STATUS(54001),
    GEOSVC_DISCONNECTVDC_STILL_REACHABLE(54002),
    GEOSVC_DISCONNECTVDC_CONCURRENT(54003),
    GEOSVC_DISCONNECTVDC_FAILED(54004),
    GEOSVC_RECONNECTVDC_FAILED(55001),
    GEOSVC_RECONNECTVDC_INVALID_STATUS(55002),
    GEOSVC_RECONNECTVDC_NODE_REPAIR_FAILED(55003),
    GEOSVC_RECONNECTVDC_UNREACHABLE(55004),

    // Backup errors (61000 - 61099)
    BACKUP_INTERNAL_ERROR(61000),
    BACKUP_CREATE_FAILED(61001),
    BACKUP_CREATE_EXSIT(61002),
    BACKUP_DELETE_FAILED(61003),
    BACKUP_LIST_FAILED(61004),
    BACKUP_RESTORE_FAILED(61005),
    BACKUP_CONNECTION_FAILED(61006),
    BACKUP_GET_LOCK_ERROR(61007),
    BACKUP_LOCK_OCCUPIED(61008),
    BACKUP_INTERNAL_NOT_LEADER(61009),
    BACKUP_PURGE_FAILED(61010),
    BACKUP_DISABLED_AS_DISK_FULL(61011),

    // ScaleIO errors (60000 - 60999)
    SCALEIO_UNSUPPORTED_OPERATION(60000),
    SCALEIO_OPERATION_EXCEPTION(60001),
    SCALEIO_CREATE_VOLUME_ERROR(60002),
    SCALEIO_DELETE_VOLUME_ERROR(60003),
    SCALEIO_MODIFY_VOLUME_CAPACITY_ERROR(60004),
    SCALEIO_MAP_ERROR(60005),
    SCALEIO_UNMAP_ERROR(60006),
    SCALEIO_CREATE_SNAPSHOT_ERROR(60007),
    SCALEIO_DELETE_SNAPSHOT_ERROR(60008),
    SCALEIO_CREATE_FULL_COPY_ERROR(60009),
    SCALEIO_SCAN_FAILED(60010),
    SCALEIO_CLI_NEEDS_TO_SPECIFY_MDM_CREDS(60011),
    SCALEIO_CLI_INIT_WAS_NOT_CALLED(60012),
    SCALEIO_API_FAILURE(60013),

    // customConfig controller errors (62000 - 62099)
    CONTROLLER_CUSTOMCONFIG_ERROR(62000),
    KEYSTONE_API_ERROR(62100),
    KEYSTONE_REQUEST_PARSE_ERRORS(62101),
    KEYSTONE_RESPONSE_PARSE_ERROR(62102),

    // NetApp Cluster errors (63000 - 63099):
    NETAPPC_ERROR(63000),
    NETAPPC_FS_CREATE_ERROR(63001),
    NETAPPC_FS_EXPAND_ERROR(63002),
    NETAPPC_FS_DELETE_ERROR(63003),
    NETAPPC_SHARE_CREATE_ERROR(63004),
    NETAPPC_SHARE_DELETE_ERROR(63005),
    NETAPPC_SNAPSHOT_CREATE_ERROR(63006),
    NETAPPC_SNAPSHOT_DELETE_ERROR(63007),
    NETAPPC_FS_RESTORE_ERROR(63008),
    NETAPPC_FS_EXPORT_ERROR(63009),
    NETAPPC_FS_UNEXPORT_ERROR(63010),
    NETAPPC_SNAPSHOT_EXPORT_ERROR(63011),
    NETAPPC_SNAPSHOT_UNEXPORT_ERROR(63012),
    NETAPPC_QTREE_CREATE_ERROR(63013),
    NETAPPC_QTREE_DELETE_ERROR(63014),
    NETAPPC_QTREE_UPDATE_ERROR(63015),
    NETAPPC_CIFS_SHARE_ACL_UPDATE_ERROR(63016),
    NETAPPC_CIFS_SHARE_ACL_DELETE_ERROR(63017),
    NETAPPC_INVALID_OPERATION(63018),

    // Unmanaged Volume Errors (64000 - 64999)
    UNMANAGED_VOLUME_INGESTION_EXCEPTION(64000),

    ECS_BASEURI(65000),
    // ECS erros (65000 - 65999)
    ECS_CONNECTION_ERROR(65000),
    ECS_RETURN_PARAM_ERROR(65001),
    ECS_LOGINVALIDATE_ERROR(65002),
    ECS_STORAGEPOOL_ERROR(650003),
    ECS_STATS_ERROR(65004),
    ECS_NON_SYSTEM_ADMIN_ERROR(65005),
    ECS_BUCKET_UPDATE_ERROR(65010),
    ECS_BUCKET_DELETE_ERROR(65011),
    ECS_BUCKET_GET_OWNER_ERROR(65012),

    // ****************************
    // Old style of Service codes
    // ****************************

    // API Errors:
    @Deprecated
    API_BAD_PARAMETERS(30, FATAL),
    @Deprecated
    API_UNAUTHORIZED_OPERATION(20, FATAL),
    @Deprecated
    API_ERROR(70, FATAL),

    // Controller Errors:
    @Deprecated
    CONTROLLER_ERROR(160, FATAL),
    @Deprecated
    CONTROLLER_STORAGE_ERROR(180, FATAL),
    @Deprecated
    CONTROLLER_OBJECT_ERROR(190, FATAL),
    @Deprecated
    CONTROLLER_NOT_FOUND(200, FATAL),
    @Deprecated
    CONTROLLER_WORKFLOW_ERROR(210, FATAL),

    // Device Controller Errors (Asynchronous aspect of controllers):
    @Deprecated
    WORKFLOW_ERROR(240, FATAL),
    @Deprecated
    WORKFLOW_RESTARTED_ERROR(250, FATAL),

    // Token encoding errors:
    @Deprecated
    AUTH_TOKEN_ENCODING_ERROR(290, FATAL),

    // General errors:
    @Deprecated
    IO_ERROR(320, FATAL);

    @Deprecated
    static enum Action {
        @Deprecated
        RETRY,
        @Deprecated
        FATAL,
        @Deprecated
        NON_APPLICABLE
    };

    private final int _serviceCode;

    private final Action _action;

    private ServiceCode(int code) {
        this(code, NON_APPLICABLE);
    }

    private ServiceCode(int code, Action action) {
        _serviceCode = code;
        _action = action;
    }

    /**
     * Get the internal service code
     * 
     * @return the service code
     */
    public int getCode() {
        return _serviceCode;
    }

    /**
     * Get whether the action can be retried
     * 
     * @return whether the action is retryable or not
     */
    public boolean isRetryable() {
        return _action == RETRY;
    }

    /**
     * Get whether the error is fatal or not
     * 
     * @return whether the error is fatal
     */
    public boolean isFatal() {
        return _action == FATAL;
    }

    /**
     * Get the appropriate HTTP status for the service code
     * 
     * @return the HTTP status
     */
    public StatusType getHTTPStatus() {
        switch (this) {
            case API_BAD_HEADERS:
            case API_BAD_REQUEST:
            case API_NO_PLACEMENT_FOUND:
            case API_PLACEMENT_ERROR:
            case API_INSUFFICIENT_QUOTA:
            case API_UNKNOWN_RP_CONFIGURATION:
            case API_PARAMETER_INVALID:
            case API_INVALID_VARRAY_NETWORK_CONFIGURATION:
            case API_INVALID_VPOOL_FOR_INGESTION:
                return Status.BAD_REQUEST;
            case SECURITY_AUTH_SERVICE_ENCODING_ERROR:
            case SECURITY_UNAUTHORIZED_OPERATION:
                return Status.UNAUTHORIZED;
            case SECURITY_INSUFFICIENT_PERMISSIONS:
                return Status.FORBIDDEN;
            case API_METHOD_NOT_SUPPORTED:
                return ClientResponse.Status.METHOD_NOT_ALLOWED;
            case API_SERVICE_UNAVAILABLE:
                return Status.SERVICE_UNAVAILABLE;
            case DBSVC_ENTITY_NOT_FOUND:
            case API_URL_ENTITY_NOT_FOUND:
                return Status.NOT_FOUND;
            case API_UNSUPPORTED_MEDIA_TYPE:
                return Status.UNSUPPORTED_MEDIA_TYPE;
            default:
                return isRetryable() ? Status.SERVICE_UNAVAILABLE : Status.INTERNAL_SERVER_ERROR;
        }
    }

    public static ServiceCode fromHTTPStatus(final int status) {
        switch (status) {
            case 400:// Status.BAD_REQUEST
                return ServiceCode.API_BAD_REQUEST;
            case 401:// Status.UNAUTHORIZED
                return ServiceCode.SECURITY_UNAUTHORIZED_OPERATION;
            case 403:// Status.FORBIDDEN
                return ServiceCode.SECURITY_INSUFFICIENT_PERMISSIONS;
            case 404:// Status.NOT_FOUND
                return ServiceCode.API_URL_ENTITY_NOT_FOUND;
            case 405:
                return ServiceCode.API_METHOD_NOT_SUPPORTED;
            case 415:
                return ServiceCode.API_UNSUPPORTED_MEDIA_TYPE;
            case 503:// Status.SERVICE_UNAVAILABLE
                return ServiceCode.API_SERVICE_UNAVAILABLE;
            default:
                return ServiceCode.UNFORSEEN_ERROR;
        }
    }

    /**
     * Converts a numerical service code into the corresponding ServiceCode
     * 
     * @param code the numerical service code
     * @return the matching ServiceCode
     */
    public static ServiceCode toServiceCode(int code) {
        for (ServiceCode svcCode : ServiceCode.values()) {
            if (svcCode.getCode() == code) {
                return svcCode;
            }
        }

        return ServiceCode.UNFORSEEN_ERROR;
    }

    public String getSummary(final Locale locale) {
        return localize(locale, this);
    }

    public String getSummary() {
        return localize(Locale.ENGLISH, this);
    }
}
