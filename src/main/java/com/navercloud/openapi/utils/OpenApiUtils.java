package com.navercloud.openapi.utils;

import java.util.List;
import java.util.Map;

import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;

public class OpenApiUtils {
	public static String getOpenApiUrl(String uri) {
		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(uri);

		return uriBuilder.toUriString() + "?responseFormatType=json";
	}

	public static String getOpenApiUrl(String uri, Object requestDto) {
		final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(uri);

		return uriAndParamMerge(uriBuilder, "", requestDto).toUriString() + "&responseFormatType=json";
	}

	private static UriComponentsBuilder uriAndParamMerge(UriComponentsBuilder uriBuilder, String keyPrefix, final Object getParameters) {
		final Map<String, Object> map = ApplicationContextUtils.getObjectMapper().convertValue(getParameters, new TypeReference<Map>() {
		});
		for (final Map.Entry<String, Object> entry : map.entrySet()) {
			final Object value = entry.getValue();
			if (value instanceof List) {
				final List<Map> list = ApplicationContextUtils.getObjectMapper().convertValue(value, new TypeReference<List>() {
				});

				for (int i=0; i < list.size(); i++) {
					if (list.get(i) instanceof Map) {
						uriBuilder = uriAndParamMerge(uriBuilder, keyPrefix + entry.getKey() + "." + (i+1) + ".", list.get(i));
					} else {
						uriBuilder = uriBuilder.replaceQueryParam(keyPrefix + entry.getKey() + "." + (i+1), list.get(i));
					}
				}
			} else {
				uriBuilder = uriBuilder.replaceQueryParam(keyPrefix + entry.getKey(), value);
			}
		}
		return uriBuilder;
	}
}
