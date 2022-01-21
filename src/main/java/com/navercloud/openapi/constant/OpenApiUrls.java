package com.navercloud.openapi.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OpenApiUrls {
	// VPC
	private static final String VPC = "/vpc/v2";

	public static final String CREATE_VPC = VPC + "/createVpc";
	public static final String GET_VPC_DETAIL = VPC + "/getVpcDetail";
	public static final String GET_NETWORK_ACL_LIST = VPC + "/getNetworkAclList";
	public static final String CREATE_VPC_SUBNET = VPC + "/createSubnet";
	public static final String GET_SUBNET_DETAIL = VPC + "/getSubnetDetail";

	// VPC Server
	private static final String VPC_SERVER = "/vserver/v2";

	// VPC Common
	public static final String GET_VPC_REGION_LIST = VPC_SERVER + "/getRegionList";
	public static final String GET_VPC_ZONE_LIST = VPC_SERVER + "/getZoneList";
	public static final String GET_VPC_SERVER_IMAGE_PRODUCT_LIST = VPC_SERVER + "/getServerImageProductList";
	public static final String GET_VPC_SERVER_PRODUCT_LIST = VPC_SERVER + "/getServerProductList";

	// VPC Server
	public static final String GET_SERVER_INSTANCE_LIST = VPC_SERVER + "/getServerInstanceList";
	public static final String GET_SERVER_INSTANCE_DETAIL = VPC_SERVER + "/getServerInstanceDetail";
	public static final String CREATE_VPC_SERVER_INSTANCES = VPC_SERVER + "/createServerInstances";
	public static final String START_VPC_SERVER_INSTANCES = VPC_SERVER + "/startServerInstances";
	public static final String REBOOT_VPC_SERVER_INSTANCES = VPC_SERVER + "/rebootServerInstances";
	public static final String STOP_VPC_SERVER_INSTANCES = VPC_SERVER + "/stopServerInstances";
	public static final String TERMINATE_VPC_SERVER_INSTANCES = VPC_SERVER + "/terminateServerInstances";

	// LoginKey
	public static final String CREATE_LOGIN_KEY = VPC_SERVER + "/createLoginKey";

	// Network Interface
	public static final String CREATE_VPC_NETWORK_INTERFACE = VPC_SERVER + "/createNetworkInterface";

	// ACG
	public static final String GET_ACG_DETAIL = VPC_SERVER + "/getAccessControlGroupDetail";
	public static final String CREATE_ACG = VPC_SERVER + "/createAccessControlGroup";

	// NAS
	private static final String VPC_NAS = "/vnas/v2";
	public static final String GET_NAS_LIST = VPC_NAS + "/getNasVolumeInstanceList";
	public static final String GET_NAS_DETAIL = VPC_NAS + "/getNasVolumeInstanceDetail";
	public static final String CREATE_NAS = VPC_NAS + "/createNasVolumeInstance";
	public static final String DELETE_NAS = VPC_NAS + "/deleteNasVolumeInstances";
	public static final String CHANGE_NAS_VOLUME_SIZE = VPC_NAS + "/changeNasVolumeSize";

	// CloudDB
	private static final String CLASSIC_CLOUD_DB = "/clouddb/v2";
	public static final String GET_CLOUD_DB_INSTANCE_LIST = CLASSIC_CLOUD_DB + "/getCloudDBInstanceList";
	public static final String EXPORT_BACKUP_TO_OBJECT_STORAGE = CLASSIC_CLOUD_DB + "/exportBackupToObjectStorage";
	public static final String GET_CLOUD_DB_BACKUP_DETAIL_LIST = CLASSIC_CLOUD_DB + "/getCloudDBBackupDetailList";
}
