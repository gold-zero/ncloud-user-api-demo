package com.navercloud.openapi.dto.userapi.acg.res;

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
public class GetAccessControlGroupDetailResponseDto {
	private GetAcgDetailRawResponseDto getAccessControlGroupDetailResponse;

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class GetAcgDetailRawResponseDto {
		private List<AcgInstanceDto> accessControlGroupList;
	}

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class AcgInstanceDto {
		private String accessControlGroupNo;
		private String accessControlGroupName;
		private CommonCode accessControlGroupStatus;
	}
}
