package com.navercloud.openapi.service.userapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.navercloud.openapi.constant.OpenApiUrls;
import com.navercloud.openapi.dto.userapi.classiccdb.req.ExportBackupToObjectStorageRequestDto;
import com.navercloud.openapi.dto.userapi.classiccdb.req.GetCloudDBBackupDetailListRequestDto;
import com.navercloud.openapi.dto.userapi.classiccdb.req.GetCloudDBInstanceListRequestDto;
import com.navercloud.openapi.dto.userapi.classiccdb.res.ExportBackupToObjectStorageResponseDto;
import com.navercloud.openapi.dto.userapi.classiccdb.res.GetCloudDBBackupDetailListResponseDto;
import com.navercloud.openapi.dto.userapi.classiccdb.res.GetCloudDBInstanceListResponseDto;
import com.navercloud.openapi.service.BaseService;
import com.navercloud.openapi.utils.OpenApiUtils;

@Service
public class ClassicCloudDbService extends BaseService {
	@Value("${open.api.host}")
	private String openApiHost;

	public ExportBackupToObjectStorageResponseDto exportBackupToObjectStorage(final ExportBackupToObjectStorageRequestDto requestDto) {
		final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.EXPORT_BACKUP_TO_OBJECT_STORAGE, requestDto);

		return restTemplate.exchange(openApiHost + uri, HttpMethod.POST, new HttpEntity<>(getNcloudUserApiHeader(HttpMethod.POST, uri)), ExportBackupToObjectStorageResponseDto.class).getBody();
	}
	public GetCloudDBBackupDetailListResponseDto getCloudDBBackupDetailList(final GetCloudDBBackupDetailListRequestDto requestDto) {
		final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.GET_CLOUD_DB_BACKUP_DETAIL_LIST, requestDto);

		return restTemplate.exchange(openApiHost + uri, HttpMethod.GET, new HttpEntity(getNcloudUserApiHeader(HttpMethod.GET, uri)), GetCloudDBBackupDetailListResponseDto.class).getBody();
	}
	public GetCloudDBInstanceListResponseDto getCloudDBInstanceList(final GetCloudDBInstanceListRequestDto requestDto) {
		final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.GET_CLOUD_DB_INSTANCE_LIST, requestDto);

		return restTemplate.exchange(openApiHost + uri, HttpMethod.GET, new HttpEntity(getNcloudUserApiHeader(HttpMethod.GET, uri)), GetCloudDBInstanceListResponseDto.class).getBody();
	}

}
