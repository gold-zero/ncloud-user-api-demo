package com.navercloud.openapi.service.userapi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navercloud.openapi.dto.userapi.ainaver.req.SearchTrendRequestDto;
import com.navercloud.openapi.dto.userapi.ainaver.req.TextSummaryRequestDto;
import com.navercloud.openapi.dto.userapi.ainaver.req.TranslationRequestDto;
import com.navercloud.openapi.dto.userapi.ainaver.res.SearchTrendResponseDto;
import com.navercloud.openapi.dto.userapi.ainaver.res.TextSummaryResponseDto;
import com.navercloud.openapi.dto.userapi.ainaver.res.TranslationResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiNaverService {
	@Value("${open.api.ai.naver.host}")
	protected String aiNaverHost;

	@Value("${open.api.ai.naver.clientId}")
	protected String clientId;

	@Value("${open.api.ai.naver.secret}")
	protected String secret;

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected RestTemplate restTemplate;

	private HttpHeaders createHeader() {
		try {
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add("X-NCP-APIGW-API-KEY-ID", clientId);
			httpHeaders.add("X-NCP-APIGW-API-KEY", secret);
			httpHeaders.add("Content-Type", "application/json");

			return httpHeaders;
		} catch (Exception ex) {
			return null;
		}
	}

	public TranslationResponseDto translation(TranslationRequestDto requestDto) {
		final ResponseEntity<TranslationResponseDto> response = restTemplate.exchange(aiNaverHost + "/nmt/v1/translation",
			HttpMethod.POST, new HttpEntity(requestDto, createHeader()), TranslationResponseDto.class);

		return response.getBody();
	}

	public TextSummaryResponseDto textSummary(TextSummaryRequestDto requestDto) {
		final ResponseEntity<TextSummaryResponseDto> response = restTemplate.exchange(aiNaverHost + "/text-summary/v1/summarize",
			HttpMethod.POST, new HttpEntity(requestDto, createHeader()), TextSummaryResponseDto.class);

		return response.getBody();
	}

	public SearchTrendResponseDto searchTrend(SearchTrendRequestDto requestDto) {
		final ResponseEntity<SearchTrendResponseDto> response = restTemplate.exchange(aiNaverHost + "/datalab/v1/search",
			HttpMethod.POST, new HttpEntity(requestDto, createHeader()), SearchTrendResponseDto.class);

		return response.getBody();
	}

	public void objectDetection() {
		StringBuffer reqStr = new StringBuffer();

		try {
			String paramName = "image"; // 파라미터명은 image로 지정
			File uploadFile = new File(new ClassPathResource("sample/cat_and_dog.jpeg").getURL().getPath());
			String apiURL = "https://naveropenapi.apigw.ntruss.com/vision-obj/v1/detect"; // 객체 인식
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setUseCaches(false);
			con.setDoOutput(true);
			con.setDoInput(true);
			// multipart request
			String boundary = "---" + System.currentTimeMillis() + "---";
			con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
			con.setRequestProperty("X-NCP-APIGW-API-KEY", secret);
			OutputStream outputStream = con.getOutputStream();
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
			String LINE_FEED = "\r\n";
			// file 추가
			String fileName = uploadFile.getName();
			writer.append("--" + boundary).append(LINE_FEED);
			writer.append("Content-Disposition: form-data; name=\"" + paramName + "\"; filename=\"" + fileName + "\"").append(LINE_FEED);
			writer.append("Content-Type: "  + URLConnection.guessContentTypeFromName(fileName)).append(LINE_FEED);
			writer.append(LINE_FEED);
			writer.flush();
			FileInputStream inputStream = new FileInputStream(uploadFile);
			byte[] buffer = new byte[4096];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.flush();
			inputStream.close();
			writer.append(LINE_FEED).flush();
			writer.append("--" + boundary + "--").append(LINE_FEED);
			writer.close();
			BufferedReader br = null;
			int responseCode = con.getResponseCode();
			if(responseCode==200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {  // 오류 발생
				System.out.println("error!!!!!!! responseCode= " + responseCode);
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			}
			String inputLine;
			if(br != null) {
				StringBuffer response = new StringBuffer();
				while ((inputLine = br.readLine()) != null) {
					response.append(inputLine);
				}
				br.close();
				System.out.println(response.toString());
			} else {
				System.out.println("error !!!");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
