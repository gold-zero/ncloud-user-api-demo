package com.navercloud.openapi.service.userapi;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.navercloud.openapi.constant.OpenApiUrls;
import com.navercloud.openapi.dto.userapi.vpc.req.CreateSubnetRequestDto;
import com.navercloud.openapi.dto.userapi.vpc.req.CreateVpcRequestDto;
import com.navercloud.openapi.dto.userapi.vpc.req.GetNetworkAclListRequestDto;
import com.navercloud.openapi.dto.userapi.vpc.req.GetSubnetDetailRequestDto;
import com.navercloud.openapi.dto.userapi.vpc.req.GetVpcDetailRequestDto;
import com.navercloud.openapi.dto.userapi.vpc.res.CreateSubnetResponseDto;
import com.navercloud.openapi.dto.userapi.vpc.res.CreateVpcResponseDto;
import com.navercloud.openapi.dto.userapi.vpc.res.GetNetworkAclListResponseDto;
import com.navercloud.openapi.dto.userapi.vpc.res.GetSubnetDetailResponseDto;
import com.navercloud.openapi.dto.userapi.vpc.res.GetVpcDetailResponseDto;
import com.navercloud.openapi.service.BaseService;
import com.navercloud.openapi.utils.OpenApiUtils;

@Service
public class VpcService extends BaseService {
	@Value("${open.api.host}")
	private String openApiHost;

	public CreateVpcResponseDto.VpcInstanceDto createVpc(final CreateVpcRequestDto requestDto) {
		final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.CREATE_VPC, requestDto);

		final ResponseEntity<CreateVpcResponseDto> response = restTemplate.exchange(
			openApiHost + uri, HttpMethod.GET, new HttpEntity(getNcloudUserApiHeader(HttpMethod.GET, uri)), CreateVpcResponseDto.class);

		final CreateVpcResponseDto responseBody = response.getBody();

		if (ObjectUtils.isNotEmpty(responseBody.getCreateVpcResponse())) {
			if (CollectionUtils.isNotEmpty(responseBody.getCreateVpcResponse().getVpcList())) {
				return responseBody.getCreateVpcResponse().getVpcList().get(0);
			}
		}

		return null;
	}

	public GetVpcDetailResponseDto getVpcDetail(final GetVpcDetailRequestDto requestDto) {
		final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.GET_VPC_DETAIL, requestDto);

		return restTemplate.exchange(
			openApiHost + uri, HttpMethod.GET, new HttpEntity(getNcloudUserApiHeader(HttpMethod.GET, uri)), GetVpcDetailResponseDto.class).getBody();
	}

	public GetNetworkAclListResponseDto getNetworkAclList(final GetNetworkAclListRequestDto requestDto) {
		final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.GET_NETWORK_ACL_LIST, requestDto);

		return restTemplate.exchange(
			openApiHost + uri, HttpMethod.GET, new HttpEntity(getNcloudUserApiHeader(HttpMethod.GET, uri)), GetNetworkAclListResponseDto.class).getBody();
	}

	public CreateSubnetResponseDto.SubnetInstanceDto createSubnet(final CreateSubnetRequestDto requestDto) {
		final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.CREATE_VPC_SUBNET, requestDto);

		final ResponseEntity<CreateSubnetResponseDto> response = restTemplate.exchange(
			openApiHost + uri, HttpMethod.GET, new HttpEntity(getNcloudUserApiHeader(HttpMethod.GET, uri)), CreateSubnetResponseDto.class);

		final CreateSubnetResponseDto responseBody = response.getBody();

		if (ObjectUtils.isNotEmpty(responseBody.getCreateSubnetResponse())) {
			if (CollectionUtils.isNotEmpty(responseBody.getCreateSubnetResponse().getSubnetList())) {
				return responseBody.getCreateSubnetResponse().getSubnetList().get(0);
			}
		}

		return null;
	}

	public GetSubnetDetailResponseDto.SubnetInstanceDto getSubnetDetail(final GetSubnetDetailRequestDto requestDto) {
		final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.GET_SUBNET_DETAIL, requestDto);

		final ResponseEntity<GetSubnetDetailResponseDto> response = restTemplate.exchange(openApiHost + uri, HttpMethod.GET, new HttpEntity(getNcloudUserApiHeader(HttpMethod.GET, uri)), GetSubnetDetailResponseDto.class);

		final GetSubnetDetailResponseDto responseBody = response.getBody();

		if (ObjectUtils.isNotEmpty(responseBody.getGetSubnetDetailResponse())) {
			if (CollectionUtils.isNotEmpty(responseBody.getGetSubnetDetailResponse().getSubnetList())) {
				return responseBody.getGetSubnetDetailResponse().getSubnetList().get(0);
			}
		}

		return null;
	}
}
