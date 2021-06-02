package com.navercloud.openapi.dto.userapi.nas.res;

import java.util.List;
import java.util.Map;

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
public class CreateNasResponseDto {
    private NasListRawResponseDto createNasVolumeInstanceResponse;
    private Map<String, Object> responseError;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NasListRawResponseDto {
	    private List<NasInstanceDto> nasVolumeInstanceList;
    }

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class NasInstanceDto {
		private String nasVolumeInstanceNo;
		private CommonCode nasVolumeInstanceStatus;
		private CommonCode nasVolumeInstanceOperation;
		private String nasVolumeInstanceStatusName;
	}
}
