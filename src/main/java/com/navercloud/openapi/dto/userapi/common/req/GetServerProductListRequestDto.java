package com.navercloud.openapi.dto.userapi.common.req;

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
public class GetServerProductListRequestDto {
	private String regionCode;
	private String zoneCode;
	private String serverImageProductCode;
	private String exclusionProductCode;
	private String productCode;
	private String generationCode; // G1 | G2
}
