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
public class GetSubnetDetailResponseDto {
    private GetSubnetDetailRawResponseDto getSubnetDetailResponse;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GetSubnetDetailRawResponseDto {
        private List<SubnetInstanceDto> subnetList;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SubnetInstanceDto {
        private String subnetNo;
        private String subnetName;
        private String subnet;
        private CommonCode subnetStatus;
    }
}
