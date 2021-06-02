package com.navercloud.openapi.dto.userapi.archivestorage.param;

import java.io.File;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class UploadArchiveObjectFileParamDto {
	@NotBlank
	private String containerName;

	@Builder.Default
	private String directoryPath = "";

	@NotBlank
	private String objectName;

	private String contentType;

	@NotNull
	private File file;

	private Map<String, String> metaData;
}
