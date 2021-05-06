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
public class CreateAccessControlGroupRequestDto {
    private String regionCode;
    private String vpcNo;
    private String accessControlGroupName;
    private String accessControlGroupDescription;
}
