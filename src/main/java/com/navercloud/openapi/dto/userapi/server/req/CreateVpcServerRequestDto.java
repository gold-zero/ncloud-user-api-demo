package com.navercloud.openapi.dto.userapi.server.req;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class CreateVpcServerRequestDto {
	private String regionCode;
	private String vpcNo;
	private String subnetNo;
	private String serverName;
	private String serverProductCode;
	private String serverImageProductCode;
	private String memberServerImageInstanceNo;
	private Integer serverCreateCount;
	private String serverDescription;
	private String loginKeyName;
	private Boolean associateWithPublicIp;
	private List<NetworkInterface> networkInterfaceList;

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class NetworkInterface {
		private Integer networkInterfaceOrder;
		private List<String> accessControlGroupNoList;
	}
}
