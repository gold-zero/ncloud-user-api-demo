package com.navercloud.openapi.dto.userapi.nas.res;

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
public class ChangeNasVolumeResponseDto {
    private ChangeNasVolumeRawResponseDto changeNasVolumeSizeResponse;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ChangeNasVolumeRawResponseDto {
    	private String returnCode;
    	private String returnMessage;
    }
}
