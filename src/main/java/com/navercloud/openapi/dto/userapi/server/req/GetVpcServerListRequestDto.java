package com.navercloud.openapi.dto.userapi.server.req;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetVpcServerListRequestDto {
	private String regionCode;
	private String vpcNo;
	private List<String> serverInstanceNoList;
	private String serverName;
	private String serverInstanceStatusCode;
	private String baseBlockStorageDiskTypeCode;
	private String baseBlockStorageDiskDetailTypeCode;
	private String ip;
	private List<String> placementGroupNoList;
}
