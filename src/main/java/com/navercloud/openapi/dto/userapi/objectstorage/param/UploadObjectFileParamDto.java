package com.navercloud.openapi.dto.userapi.objectstorage.param;

import java.io.File;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.navercloud.openapi.constant.RegionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UploadObjectFileParamDto {
	@NotBlank
    private RegionEnum region;
	@NotBlank
    private String bucketName;
    private String folderPath;
	@NotBlank
    private String objectName;
	@NotBlank
    private File objectFile;

    public String getObjectFilePath() {
    	return (StringUtils.isEmpty(folderPath) ? objectName : folderPath+objectName);
    }
}
