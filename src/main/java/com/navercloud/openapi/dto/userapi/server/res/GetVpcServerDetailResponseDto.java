package com.navercloud.openapi.dto.userapi.server.res;

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
public class GetVpcServerDetailResponseDto {
	private GetServerInstanceRawResponseDto getServerInstanceDetailResponse;

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class GetServerInstanceRawResponseDto {
		private List<ServerInstanceDto> serverInstanceList;
	}

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ServerInstanceDto {
		private String serverInstanceNo;
		private String serverName;
		private String serverInstanceStatusName;
	}
}