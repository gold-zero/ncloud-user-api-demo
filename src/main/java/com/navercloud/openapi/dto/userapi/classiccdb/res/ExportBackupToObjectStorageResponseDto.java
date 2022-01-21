package com.navercloud.openapi.dto.userapi.classiccdb.res;

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
public class ExportBackupToObjectStorageResponseDto {
	private ExportBackupToObjectStorageRawResponseDto exportBackupToObjectStorageResponse;

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ExportBackupToObjectStorageRawResponseDto {
		private String returnCode;
		private String returnMessage;
	}
}