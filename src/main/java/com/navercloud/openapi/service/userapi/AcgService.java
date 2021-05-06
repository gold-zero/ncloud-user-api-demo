package com.navercloud.openapi.service.userapi;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.navercloud.openapi.constant.OpenApiUrls;
import com.navercloud.openapi.dto.userapi.acg.req.CreateAccessControlGroupRequestDto;
import com.navercloud.openapi.dto.userapi.acg.req.GetAccessControlGroupDetailRequestDto;
import com.navercloud.openapi.dto.userapi.acg.res.CreateAccessControlGroupResponseDto;
import com.navercloud.openapi.dto.userapi.acg.res.GetAccessControlGroupDetailResponseDto;
import com.navercloud.openapi.service.BaseService;
import com.navercloud.openapi.utils.OpenApiUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AcgService extends BaseService {
    @Value("${open.api.host}")
    private String openApiHost;

    public CreateAccessControlGroupResponseDto.AcgInstanceDto createAccessControlGroup(CreateAccessControlGroupRequestDto requestDto) {
        final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.CREATE_ACG, requestDto);

        final CreateAccessControlGroupResponseDto responseDto = restTemplate.exchange(openApiHost + uri, HttpMethod.GET, new HttpEntity(getNcloudUserApiHeader(HttpMethod.GET, uri)), CreateAccessControlGroupResponseDto.class).getBody();

	    if (ObjectUtils.isNotEmpty(responseDto.getCreateAccessControlGroupResponse())) {
		    if (CollectionUtils.isNotEmpty(responseDto.getCreateAccessControlGroupResponse().getAccessControlGroupList())) {
			    return responseDto.getCreateAccessControlGroupResponse().getAccessControlGroupList().get(0);
		    }
	    }

	    return null;
    }

    public GetAccessControlGroupDetailResponseDto.AcgInstanceDto getAccessControlGroupDetail(GetAccessControlGroupDetailRequestDto requestDto) {
        final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.GET_ACG_DETAIL, requestDto);

        final GetAccessControlGroupDetailResponseDto responseDto = restTemplate.exchange(openApiHost + uri, HttpMethod.GET, new HttpEntity(getNcloudUserApiHeader(HttpMethod.GET, uri)), GetAccessControlGroupDetailResponseDto.class).getBody();

	    if (ObjectUtils.isNotEmpty(responseDto.getGetAccessControlGroupDetailResponse())) {
		    if (CollectionUtils.isNotEmpty(responseDto.getGetAccessControlGroupDetailResponse().getAccessControlGroupList())) {
			    return responseDto.getGetAccessControlGroupDetailResponse().getAccessControlGroupList().get(0);
		    }
	    }

	    return null;
    }
}
