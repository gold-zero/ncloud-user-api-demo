package com.navercloud.openapi.service;

import java.nio.charset.Charset;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navercloud.openapi.constant.OpenApiHeader;

public class BaseService {
	@Value("${open.api.access.key}")
	private String accessKey;

	@Value("${open.api.secret.key}")
	private String secretKey;

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected RestTemplate restTemplate;

	protected HttpHeaders getNcloudUserApiHeader(HttpMethod method, String url) {
		try {
			MediaType mediaType = new MediaType("application", "json", Charset.forName("UTF-8"));

			String timeStamp = String.valueOf(System.currentTimeMillis());
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add(OpenApiHeader.X_NCP_APIGW_TIMESTAMP, timeStamp);
			httpHeaders.add(OpenApiHeader.X_NCP_IAM_ACCESS_KEY, accessKey);
			httpHeaders.add(OpenApiHeader.X_NCP_APIGW_SIGNATURE, makeSignature(timeStamp, method.name(), url));

			httpHeaders.setContentType(mediaType);

			return httpHeaders;
		} catch (Exception ex) {
			return null;
		}
	}

	private String makeSignature(String timeStamp, String method, String url) throws Exception {
		String message = new StringBuilder()
			.append(method)
			.append(" ")
			.append(url)
			.append("\n")
			.append(timeStamp)
			.append("\n")
			.append(accessKey)
			.toString();

		SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(signingKey);

		byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
		String encodeBase64String = Base64.encodeBase64String(rawHmac);

		return encodeBase64String;
	}
}
