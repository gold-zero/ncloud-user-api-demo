package com.navercloud.openapi.dto.userapi.classiccdb.res;

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
public class GetCloudDBInstanceListResponseDto {
	private GetCloudDBInstanceRawResponseDto getCloudDBInstanceListResponse;

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class GetCloudDBInstanceRawResponseDto {
		private List<CloudDBInstance> cloudDBInstanceList;
	}

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class CloudDBInstance {
		private String cloudDBInstanceNo;
		private String cloudDBServiceName;
	}
}