package com.navercloud.openapi.service.userapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.navercloud.openapi.constant.OpenApiUrls;
import com.navercloud.openapi.dto.userapi.vpc.req.CreateVpcRequestDto;
import com.navercloud.openapi.dto.userapi.vpc.res.CreateVpcResponseDto;
import com.navercloud.openapi.service.BaseService;
import com.navercloud.openapi.utils.OpenApiUtils;

@Service
public class VpcNetworkInterfaceService extends BaseService {
	@Value("${open.api.host}")
	private String openApiHost;

	public CreateVpcResponseDto createNetworkInterface(final CreateVpcRequestDto requestDto) {
		final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.CREATE_VPC_NETWORK_INTERFACE, requestDto);

		return restTemplate.exchange(
			openApiHost + uri, HttpMethod.GET, new HttpEntity(getNcloudUserApiHeader(HttpMethod.GET, uri)), CreateVpcResponseDto.class).getBody();
	}
}
