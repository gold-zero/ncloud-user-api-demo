package com.navercloud.openapi.service.userapi;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.navercloud.openapi.constant.OpenApiUrls;
import com.navercloud.openapi.dto.userapi.server.req.CreateVpcServerRequestDto;
import com.navercloud.openapi.dto.userapi.server.req.GetVpcServerDetailRequestDto;
import com.navercloud.openapi.dto.userapi.server.req.GetVpcServerListRequestDto;
import com.navercloud.openapi.dto.userapi.server.req.OperateVpcServersRequestDto;
import com.navercloud.openapi.dto.userapi.server.res.CreateVpcServerResponseDto;
import com.navercloud.openapi.dto.userapi.server.res.GetVpcServerDetailResponseDto;
import com.navercloud.openapi.dto.userapi.server.res.GetVpcServerListResponse;
import com.navercloud.openapi.service.BaseService;
import com.navercloud.openapi.utils.OpenApiUtils;

@Service
public class VpcServerService extends BaseService {
	@Value("${open.api.host}")
	private String openApiHost;

	public GetVpcServerListResponse getServerInstanceList(final GetVpcServerListRequestDto requestDto) {
		final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.GET_SERVER_INSTANCE_LIST, requestDto);

		return restTemplate.exchange(openApiHost + uri, HttpMethod.GET, new HttpEntity(getNcloudUserApiHeader(HttpMethod.GET, uri)), GetVpcServerListResponse.class).getBody();
	}

	public GetVpcServerDetailResponseDto.ServerInstanceDto getServerInstanceDetail(final GetVpcServerDetailRequestDto requestDto) {
		final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.GET_SERVER_INSTANCE_DETAIL, requestDto);

		final ResponseEntity<GetVpcServerDetailResponseDto> response = restTemplate.exchange(openApiHost + uri, HttpMethod.GET, new HttpEntity(getNcloudUserApiHeader(HttpMethod.GET, uri)), GetVpcServerDetailResponseDto.class);

		final GetVpcServerDetailResponseDto responseBody = response.getBody();

		if (ObjectUtils.isNotEmpty(responseBody.getGetServerInstanceDetailResponse())) {
			if (CollectionUtils.isNotEmpty(responseBody.getGetServerInstanceDetailResponse().getServerInstanceList())) {
				return responseBody.getGetServerInstanceDetailResponse().getServerInstanceList().get(0);
			}
		}

		return null;
	}

	public CreateVpcServerResponseDto.ServerInstanceDto createServerInstances(CreateVpcServerRequestDto requestDto) {

		final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.CREATE_VPC_SERVER_INSTANCES, requestDto);

		final ResponseEntity<CreateVpcServerResponseDto> responseEntity = restTemplate.exchange(openApiHost + uri, HttpMethod.GET, new HttpEntity(getNcloudUserApiHeader(HttpMethod.GET, uri)), CreateVpcServerResponseDto.class);

		if(!responseEntity.getStatusCode().is2xxSuccessful()){
			if(ObjectUtils.isNotEmpty(responseEntity.getBody()) && ObjectUtils.isNotEmpty(responseEntity.getBody().getResponseError())) {
				throw new RuntimeException(responseEntity.getBody().getResponseError().toString());
			}

			throw new RuntimeException("Failed create vpc server");
		}

		return responseEntity.getBody().getCreateServerInstancesResponse().getServerInstanceList().get(0);
	}

	public void stopServerInstances(final OperateVpcServersRequestDto requestDto) {
		final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.STOP_VPC_SERVER_INSTANCES, requestDto);

		final ResponseEntity<String> response = restTemplate.exchange(openApiHost + uri, HttpMethod.GET, new HttpEntity(getNcloudUserApiHeader(HttpMethod.GET, uri)), String.class);

		if (!response.getStatusCode().is2xxSuccessful()) {
			throw new RuntimeException("Failed stop vpc servers");
		}
	}

	public void terminateServerInstances(final OperateVpcServersRequestDto requestDto) {
		final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.TERMINATE_VPC_SERVER_INSTANCES, requestDto);

		final ResponseEntity<String> response = restTemplate.exchange(openApiHost + uri, HttpMethod.GET, new HttpEntity(getNcloudUserApiHeader(HttpMethod.GET, uri)), String.class);

		if (!response.getStatusCode().is2xxSuccessful()) {
			throw new RuntimeException("Failed terminate vpc servers");
		}
	}

	public void startServerInstances(final OperateVpcServersRequestDto requestDto) {
		final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.START_VPC_SERVER_INSTANCES, requestDto);

		final ResponseEntity<String> response = restTemplate.exchange(openApiHost + uri, HttpMethod.GET, new HttpEntity(getNcloudUserApiHeader(HttpMethod.GET, uri)), String.class);

		if (!response.getStatusCode().is2xxSuccessful()) {
			throw new RuntimeException("Failed start vpc servers");
		}
	}

	public void rebootServerInstances(final OperateVpcServersRequestDto requestDto) {
		final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.REBOOT_VPC_SERVER_INSTANCES, requestDto);

		final ResponseEntity<String> response = restTemplate.exchange(openApiHost + uri, HttpMethod.GET, new HttpEntity(getNcloudUserApiHeader(HttpMethod.GET, uri)), String.class);

		if (!response.getStatusCode().is2xxSuccessful()) {
			throw new RuntimeException("Failed reboot vpc servers");
		}
	}
}
