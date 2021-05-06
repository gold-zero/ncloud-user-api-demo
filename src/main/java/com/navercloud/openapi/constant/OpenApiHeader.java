package com.navercloud.openapi.constant;

public class OpenApiHeader {
	private OpenApiHeader() {
		throw new IllegalStateException("Utility class");
	}

	public static final String X_NCP_APIGW_TIMESTAMP = "x-ncp-apigw-timestamp";
	public static final String X_NCP_IAM_ACCESS_KEY = "x-ncp-iam-access-key";
	public static final String X_NCP_APIGW_SIGNATURE = "x-ncp-apigw-signature-v2";
}
