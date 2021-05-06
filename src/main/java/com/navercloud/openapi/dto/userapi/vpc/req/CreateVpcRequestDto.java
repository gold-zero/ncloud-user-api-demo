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
public class CreateVpcRequestDto {
    private String regionCode;
    private String vpcName;
    private String ipv4CidrBlock;
}
