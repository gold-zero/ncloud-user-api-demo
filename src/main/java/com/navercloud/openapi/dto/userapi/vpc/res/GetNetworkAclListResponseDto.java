package com.navercloud.openapi.dto.userapi.vpc.res;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.navercloud.openapi.dto.userapi.common.CommonCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetNetworkAclListResponseDto {
	private GetNetworkAclListRawResponseDto getNetworkAclListResponse;

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class GetNetworkAclListRawResponseDto {
		private List<NetworkAclInstanceDto> networkAclList;
	}

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class NetworkAclInstanceDto {
		private String networkAclNo;
		private String networkAclName;
		private String vpcNo;
		private CommonCode networkAclStatus;
		private Boolean isDefault;
	}
}
