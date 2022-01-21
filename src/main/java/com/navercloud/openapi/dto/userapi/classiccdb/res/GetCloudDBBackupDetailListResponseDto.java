package com.navercloud.openapi.dto.userapi.classiccdb.res;

import java.util.Date;
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
public class GetCloudDBBackupDetailListResponseDto {
	private GetCloudDBBackupDetailRawResponseDto getCloudDBBackupDetailListResponse;

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class GetCloudDBBackupDetailRawResponseDto {
		private List<CloudDBBackupDetail> cloudDBBackupDetailList;
	}

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class CloudDBBackupDetail {
		private String fileName;
		private Date startTime;
		private Date endTime;
		private Long backupSize;
	}
}