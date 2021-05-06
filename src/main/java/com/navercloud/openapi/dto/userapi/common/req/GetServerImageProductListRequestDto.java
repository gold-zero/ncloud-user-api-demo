package com.navercloud.openapi.dto.userapi.common.req;

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
public class GetServerImageProductListRequestDto {
	private String regionCode;
	private String blockStorageSize;
	private String exclusionProductCode;
	private String productCode;
	private List<String> platformTypeCodeList;
}
