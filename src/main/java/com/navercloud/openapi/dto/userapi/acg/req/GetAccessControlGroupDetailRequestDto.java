package com.navercloud.openapi.dto.userapi.acg.req;

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
public class GetAccessControlGroupDetailRequestDto {
	private String regionCode;
	private String accessControlGroupNo;
}
