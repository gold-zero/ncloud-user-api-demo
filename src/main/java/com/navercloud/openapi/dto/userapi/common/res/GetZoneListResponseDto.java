package com.navercloud.openapi.dto.userapi.common.res;

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
public class GetZoneListResponseDto {
    private ZoneListRawResponseDto getZoneListResponse;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ZoneListRawResponseDto {
        private String requestId;
        private String returnCode;
        private String returnMessage;
        private List<ZoneDto> zoneList;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ZoneDto {
        private String zoneName;
        private String zoneCode;
        private String regionCode;
        private String zoneDescription;
    }
}
