package com.navercloud.openapi.service.userapi;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.navercloud.openapi.constant.OpenApiUrls;
import com.navercloud.openapi.dto.userapi.nas.req.ChangeNasVolumeRequestDto;
import com.navercloud.openapi.dto.userapi.nas.req.CreateNasRequestDto;
import com.navercloud.openapi.dto.userapi.nas.req.DeleteNasRequestDto;
import com.navercloud.openapi.dto.userapi.nas.req.GetNasListRequestDto;
import com.navercloud.openapi.dto.userapi.nas.res.ChangeNasVolumeResponseDto;
import com.navercloud.openapi.dto.userapi.nas.res.CreateNasResponseDto;
import com.navercloud.openapi.dto.userapi.nas.res.DeleteNasResponseDto;
import com.navercloud.openapi.dto.userapi.nas.res.GetNasListResponseDto;
import com.navercloud.openapi.service.BaseService;
import com.navercloud.openapi.utils.OpenApiUtils;

@Service
public class NasService extends BaseService {
	@Value("${open.api.host}")
	private String openApiHost;

	public GetNasListResponseDto.NasListRawResponseDto getNasList(final GetNasListRequestDto requestDto) {
		final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.GET_NAS_LIST, requestDto);

		final GetNasListResponseDto responseDto = restTemplate.exchange(
			openApiHost + uri, HttpMethod.GET, new HttpEntity<>(getNcloudUserApiHeader(HttpMethod.GET, uri)), GetNasListResponseDto.class).getBody();

		return responseDto.getGetNasVolumeInstanceListResponse();
	}

	public CreateNasResponseDto.NasListRawResponseDto createNas(final CreateNasRequestDto requestDto) {
		final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.CREATE_NAS, requestDto);

		final CreateNasResponseDto responseDto = restTemplate.exchange(
			openApiHost + uri, HttpMethod.GET, new HttpEntity<>(getNcloudUserApiHeader(HttpMethod.GET, uri)), CreateNasResponseDto.class).getBody();

		return responseDto.getCreateNasVolumeInstanceResponse();
	}

	public void deleteNas(final DeleteNasRequestDto requestDto) {
		final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.DELETE_NAS, requestDto);

		final DeleteNasResponseDto responseDto = restTemplate.exchange(
			openApiHost + uri, HttpMethod.GET, new HttpEntity<>(getNcloudUserApiHeader(HttpMethod.GET, uri)), DeleteNasResponseDto.class).getBody();

		if (!StringUtils.equals("0", responseDto.getDeleteNasVolumeInstancesResponse().getReturnCode())) {
			throw new RuntimeException("Failed : Delete nas");
		}
	}

	public void changeNasVolumeSize(final ChangeNasVolumeRequestDto requestDto) {
		final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.CHANGE_NAS_VOLUME_SIZE, requestDto);

		final ChangeNasVolumeResponseDto responseDto = restTemplate.exchange(
			openApiHost + uri, HttpMethod.GET, new HttpEntity<>(getNcloudUserApiHeader(HttpMethod.GET, uri)), ChangeNasVolumeResponseDto.class).getBody();

		if (!StringUtils.equals("0", responseDto.getChangeNasVolumeSizeResponse().getReturnCode())) {
			throw new RuntimeException("Failed : Change nas volume size");
		}
	}
}
