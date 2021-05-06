package com.navercloud.openapi.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RestTemplateConfig {
	@Autowired
	private ObjectMapper objectMapper;

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(getBufferingClientHttpRequestFactory());
		restTemplate.getMessageConverters().stream()
			.filter(messageConverter -> messageConverter instanceof MappingJackson2HttpMessageConverter)
			.forEach(messageConverter -> ((MappingJackson2HttpMessageConverter)messageConverter).setObjectMapper(
				objectMapper));
		restTemplate.setInterceptors(getInterceptors());
		restTemplate.setErrorHandler(getEmptyResponseErrorHandler());
		return restTemplate;
	}

	private List<ClientHttpRequestInterceptor> getInterceptors() {
		return Arrays.asList(new ClientHttpRequestInterceptor() {
			@Override
			public ClientHttpResponse intercept(HttpRequest request, byte[] body,
				ClientHttpRequestExecution execution) throws IOException {
				traceRequest(request, body);
				ClientHttpResponse response = execution.execute(request, body);
				traceResponse(response);
				return response;
			}

			private void traceRequest(HttpRequest request, byte[] body) throws IOException {
				StringBuilder message = new StringBuilder().append("\n");
				message.append("=========================== request begin ===========================").append("\n");
				message.append("URI         : ").append(request.getURI()).append("\n");
				message.append("Method      : ").append(request.getMethod()).append("\n");
				message.append("Headers     : ").append(request.getHeaders()).append("\n");
				message.append("Request body: ").append(new String(body, "UTF-8")).append("\n");
				message.append("===========================  request end  ===========================");
			}

			private void traceResponse(ClientHttpResponse response) throws IOException {
				StringBuilder inputStringBuilder = new StringBuilder();

				try (InputStreamReader in = new InputStreamReader(response.getBody(), "UTF-8"); BufferedReader bufferedReader = new BufferedReader(in)) {
					String line = bufferedReader.readLine();
					while (line != null) {
						inputStringBuilder.append(line);
						inputStringBuilder.append('\n');
						line = bufferedReader.readLine();
					}

					StringBuilder message = new StringBuilder().append("\n");
					message.append("=========================== response begin ===========================").append("\n");
					message.append("Status code  : ").append(response.getStatusCode()).append("\n");
					message.append("Status text  : ").append(response.getStatusText()).append("\n");
					message.append("Headers      : ").append(response.getHeaders()).append("\n");
					message.append("Response body: ").append(inputStringBuilder.toString()).append("\n");
					message.append("===========================  response end  ===========================");
				}

			}
		});
	}

	private BufferingClientHttpRequestFactory getBufferingClientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory =
			new HttpComponentsClientHttpRequestFactory();
		httpComponentsClientHttpRequestFactory.setConnectTimeout(1000);
		httpComponentsClientHttpRequestFactory.setReadTimeout(30000);
		return new BufferingClientHttpRequestFactory(httpComponentsClientHttpRequestFactory);
	}

	private ResponseErrorHandler getEmptyResponseErrorHandler() {
		return new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse clientHttpResponse) {
				return false;
			}

			@Override
			public void handleError(ClientHttpResponse clientHttpResponse) {

			}
		};
	}
}
