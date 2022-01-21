package com.navercloud.openapi.dto.userapi.classiccdb.req;

import javax.validation.constraints.NotBlank;

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
public class ExportBackupToObjectStorageRequestDto {
	private String regionNo;
	@NotBlank
	private String dbKindCode; // MYSQL , MSSQL , REDIS
	@NotBlank
	private String fileName;
	@NotBlank
	private String bucketName;
	@NotBlank
	private String cloudDBInstanceNo;
}
