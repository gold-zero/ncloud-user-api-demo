package com.navercloud.openapi.dto.userapi.objectstorage.param;

import javax.validation.constraints.NotBlank;

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
public class DeleteBucketParamDto {
	@NotBlank
    private RegionEnum region;
	@NotBlank
    private String bucketName;
}
