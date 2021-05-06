package com.navercloud.openapi.dto.userapi.networkinterface.req;

import java.util.List;

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
public class CreateNetworkInterfaceRequestDto {
    private String regionCode;
    private String vpcNo;
    private String subnetNo;
    private List<String> accessControlGroupNoList;
}
