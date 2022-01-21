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
public class GetCloudDBBackupDetailListRequestDto {
	private String regionNo;
	@NotBlank
	private String dbKindCode; // MYSQL , MSSQL , REDIS
	@NotBlank
	private String cloudDBInstanceNo;
}
