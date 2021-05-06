package com.navercloud.openapi.service.userapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.navercloud.openapi.constant.OpenApiUrls;
import com.navercloud.openapi.dto.userapi.lgoinkey.req.CreateLoginKeyRequestDto;
import com.navercloud.openapi.dto.userapi.lgoinkey.res.CreateLoginKeyResponseDto;
import com.navercloud.openapi.service.BaseService;
import com.navercloud.openapi.utils.OpenApiUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoginKeyService extends BaseService {
    @Value("${open.api.host}")
    private String openApiHost;

    public CreateLoginKeyResponseDto.CreateLoginKeyRawResponseDto createLoginKey(CreateLoginKeyRequestDto requestDto) {
        final String uri = OpenApiUtils.getOpenApiUrl(OpenApiUrls.CREATE_LOGIN_KEY, requestDto);

        final CreateLoginKeyResponseDto responseDto = restTemplate.exchange(
	        openApiHost + uri, HttpMethod.GET, new HttpEntity(getNcloudUserApiHeader(HttpMethod.GET, uri)), CreateLoginKeyResponseDto.class).getBody();;

        return responseDto.getCreateLoginKeyResponse();
    }
}
