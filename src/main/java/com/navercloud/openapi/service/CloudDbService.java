package com.navercloud.openapi.service;

import org.springframework.stereotype.Service;

import com.navercloud.openapi.dto.userapi.classiccdb.req.ExportBackupToObjectStorageRequestDto;
import com.navercloud.openapi.dto.userapi.classiccdb.req.GetCloudDBBackupDetailListRequestDto;
import com.navercloud.openapi.dto.userapi.classiccdb.req.GetCloudDBInstanceListRequestDto;
import com.navercloud.openapi.dto.userapi.classiccdb.res.ExportBackupToObjectStorageResponseDto;
import com.navercloud.openapi.dto.userapi.classiccdb.res.GetCloudDBBackupDetailListResponseDto;
import com.navercloud.openapi.dto.userapi.classiccdb.res.GetCloudDBInstanceListResponseDto;
import com.navercloud.openapi.service.userapi.ClassicCloudDbService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CloudDbService extends BaseService {

	private final ClassicCloudDbService cloudDbService;


	public void exportTest() {
		final GetCloudDBInstanceListResponseDto dbInstanceList = cloudDbService.getCloudDBInstanceList(
			GetCloudDBInstanceListRequestDto.builder().dbKindCode("MYSQL").build());

		String instanceNo = dbInstanceList.getGetCloudDBInstanceListResponse().getCloudDBInstanceList().get(0).getCloudDBInstanceNo();


		final GetCloudDBBackupDetailListResponseDto backupDetailList = cloudDbService.getCloudDBBackupDetailList(GetCloudDBBackupDetailListRequestDto.builder().cloudDBInstanceNo(instanceNo).dbKindCode("MYSQL").build());

		String fileName = backupDetailList.getGetCloudDBBackupDetailListResponse().getCloudDBBackupDetailList().get(0).getFileName();


		final ExportBackupToObjectStorageResponseDto exportBackupToObjectStorageResponseDto = cloudDbService.exportBackupToObjectStorage(ExportBackupToObjectStorageRequestDto.builder().bucketName("bucketName").dbKindCode("MYSQL").cloudDBInstanceNo(instanceNo).fileName(fileName).build());

		if (!"success".equals(exportBackupToObjectStorageResponseDto.getExportBackupToObjectStorageResponse().getReturnCode())) {
			log.info("Fail");
		}
	}
}
