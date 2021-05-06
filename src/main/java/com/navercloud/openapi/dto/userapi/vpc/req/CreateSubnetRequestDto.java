package com.navercloud.openapi.dto.userapi.vpc.req;

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
public class CreateSubnetRequestDto {
	private String regionCode;
	private String zoneCode;
	private String vpcNo;
	private String subnetName;
	private String subnet;
	private String networkAclNo;
	private String subnetTypeCode; // PUBLIC | PRIVATE
	private String usageTypeCode;  // GEN
}
