package com.navercloud.openapi.dto.userapi.server.req;

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
public class OperateVpcServersRequestDto {
	private String regionCode;
	private List<String> serverInstanceNoList;
}
