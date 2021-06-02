package com.navercloud.openapi.dto.userapi.objectstorage.res;

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
public class ObjectListResponseDto {
    private List<FolderRawResponseDto> folderList;
    private List<FileRawResponseDto> fileList;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FolderRawResponseDto {
	    private String folderPath;
	    private Date lastModified;
    }

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class FileRawResponseDto {
		private String filePath;
		private Long size;
		private Date lastModified;
	}
}
