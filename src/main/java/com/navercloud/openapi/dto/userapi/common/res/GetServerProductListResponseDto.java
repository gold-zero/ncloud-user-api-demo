package com.navercloud.openapi.dto.userapi.common.res;

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
public class GetServerProductListResponseDto {
	private ServerProductListRawResponseDto getServerProductListResponse;

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ServerProductListRawResponseDto {
		private String requestId;
		private String returnCode;
		private String returnMessage;
		private List<ProductDto> productList;
	}

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ProductDto {
		private String productCode;
		private String productName;
		private CommonCode productType;
		private String productDescription;
		private CommonCode infraResourceType;
		private CommonCode infraResourceDetailType;
		private Integer cpuCount;
		private Long memorySize;
		private Long baseBlockStorageSize;
		private CommonCode platformType;
		private String osInformation;
		private CommonCode diskType;
		private String dbKindCode;
		private Long addBlockStorageSize;
		private String generationCode;
	}
}
