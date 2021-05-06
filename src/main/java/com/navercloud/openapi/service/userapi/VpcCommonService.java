package com.navercloud.openapi.service.userapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.navercloud.openapi.constant.OpenApiUrls;
import com.navercloud.openapi.dto.userapi.common.req.GetServerImageProductListRequestDto;
import com.navercloud.openapi.dto.userapi.common.req.GetServerProductListRequestDto;
import com.navercloud.openapi.dto.userapi.common.req.GetZoneListRequestDto;
import com.navercloud.openapi.dto.userapi.common.res.GetRegionListResponseDto;
import com.navercloud.openapi.dto.userapi.common.res.GetServerImageProductListResponseDto;
import com.navercloud.openapi.dto.userapi.common.res.GetServerProductListResponseDto;
import com.navercloud.openapi.dto.userapi.common.res.GetZoneListResponseDto;
import com.navercloud.openapi.service.BaseService;
import com.navercloud.openapi.utils.OpenApiUtils;

@Service
public class VpcCommonService extends BaseService {
	@Value("${open.api.host}")
	private String openApiHost;

	public GetRegionListResponseDto getRegionList() {
		final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.GET_VPC_REGION_LIST);

		return restTemplate.exchange(openApiHost + uri, HttpMethod.GET, new HttpEntity(getNcloudUserApiHeader(HttpMethod.GET, uri)), GetRegionListResponseDto.class).getBody();
	}

	public GetZoneListResponseDto getZoneList(final GetZoneListRequestDto requestDto) {
		final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.GET_VPC_ZONE_LIST, requestDto);

		return restTemplate.exchange(openApiHost + uri, HttpMethod.GET, new HttpEntity(getNcloudUserApiHeader(HttpMethod.GET, uri)), GetZoneListResponseDto.class).getBody();
	}

	public GetServerImageProductListResponseDto getServerImageProductList(final GetServerImageProductListRequestDto requestDto) {
		final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.GET_VPC_SERVER_IMAGE_PRODUCT_LIST, requestDto);

		return restTemplate.exchange(openApiHost + uri, HttpMethod.GET, new HttpEntity(getNcloudUserApiHeader(HttpMethod.GET, uri)), GetServerImageProductListResponseDto.class).getBody();
	}

	public GetServerProductListResponseDto getServerProductList(final GetServerProductListRequestDto requestDto) {
		final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.GET_VPC_SERVER_PRODUCT_LIST, requestDto);

		return restTemplate.exchange(openApiHost + uri, HttpMethod.GET, new HttpEntity(getNcloudUserApiHeader(HttpMethod.GET, uri)), GetServerProductListResponseDto.class).getBody();
	}
}
