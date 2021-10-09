package com.navercloud.openapi.service;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.navercloud.openapi.dto.userapi.ainaver.constant.LangEnum;
import com.navercloud.openapi.dto.userapi.ainaver.req.SearchTrendRequestDto;
import com.navercloud.openapi.dto.userapi.ainaver.req.TextSummaryRequestDto;
import com.navercloud.openapi.dto.userapi.ainaver.req.TranslationRequestDto;
import com.navercloud.openapi.dto.userapi.ainaver.res.SearchTrendResponseDto;
import com.navercloud.openapi.dto.userapi.ainaver.res.TextSummaryResponseDto;
import com.navercloud.openapi.dto.userapi.ainaver.res.TranslationResponseDto;
import com.navercloud.openapi.service.userapi.AiNaverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiNaverDemoService {
	private final AiNaverService aiNaverService;

	public void objectDetection() {
		aiNaverService.objectDetection();
	}

	public void translation() {
		final TranslationRequestDto requestDto = TranslationRequestDto.builder().sourceLang(LangEnum.KO).targetLang(
			LangEnum.EN).text("오늘도 좋은 하루되세요.").build();
		final TranslationResponseDto responseDto = aiNaverService.translation(requestDto);

		System.out.println("Plan Text : " + requestDto.getText());
		System.out.println("Translation Text : " + responseDto.getMessage().getResult().getTranslatedText());
	}

	public void textSummary() {
		final TextSummaryRequestDto requestDto = TextSummaryRequestDto.builder().document(
		TextSummaryRequestDto.DocumentRequestDto.builder().content(
			"넷플릭스 한국 드라마 '오징어 게임' 출연진이 미국 NBC 유명 토크쇼 '지미 팰런쇼' 출연을 예고한 가운데 진행자이자 유명 코미디언 지미 팰런이 달고나 게임에 직접 도전했다."
				+ "6일 지미 팰런은 자신의 사회관계망서비스(SNS)를 통해 'Squid game Cookie'라는 제목의 짧은 영상을 게재했다."
				+ "영상에 따르면 지미 팰런이 직접 프라이팬에 소다와 설탕을 붓고 달고나 제조한다. 이후 지미 팰런은 자신의 이름 이니셜인 'JF'가 새겨진 달고나를 직접 손에 들고 있다. 열심히 달고나를 핥아봤지만 바늘을 대는 순간 달고나가 부서지면서 총소리가 울린다. 이어 지미 팰런은 바닥에 쓰러지면서 영상은 마무리된다."
				+ "'오징어 게임'에서 주연 배우로 활약한 이정재, 박해수, 정호연, 위하준은 6일(현지시간 5일) 지미 팰런쇼 녹화에 참여했다. 다만 신종 코로나바이러스 감염증(코로나19) 상황을 고려해 한국과 미국을 화상으로 연결해 인터뷰하는 방식으로 진행됐다. 해당 방송은 7일 오후에 공개될 예정이다."
				+ "한편, 지난 9월 17일 공개된 오징어 게임은 456억 원의 상금이 걸린 의문의 서바이벌 게임에 참가한 사람들이 최후의 승자가 되기 위해 목숨을 걸고 극한의 게임에 도전하는 내용을 담고 있다. 연일 기준 글로벌 OTT 콘텐츠 순위 집계에서도 1위를 차지하며 흥행을 이어가고 있다."
				+ "'오징어 게임'의 흥행에 힘입어 출연 배우들의 인스타그램 팔로워는 폭증했다."
				+ "새벽 역의 정호연은 7일 9시 기준 1562만 명이 인스타그램을 팔로우했다. 이는 한국 여자 배우 중 팔로워 최고 기록이다. '오징어 게임' 공개 전 그의 팔로워는 약 40만명이었다."
				+ "이 놀라운 효과에 주인공 기훈 역의 이정재, 상우 역의 박해수도 뒤늦게 인스타그램 계정을 개설했다. 이정재 인스타그램은 개설 5일만에 500만 팔로우로 껑충 뛰었다."
		).build()).option(TextSummaryRequestDto.OptionRequestDto.builder().language("ko").build()).build();

		final TextSummaryResponseDto responseDto = aiNaverService.textSummary(requestDto);

		System.out.println("Plan Text : " + requestDto.getDocument().getContent());
		System.out.println("Summary Text : " + responseDto.getSummary());
	}

	public void searchTrend() {
		final SearchTrendRequestDto requestDto = SearchTrendRequestDto.builder()
																		.startDate("2021-09-09")
																		.endDate("2021-10-09")
																		.timeUnit("date")
																		.keywordGroups(Arrays.asList(
																			SearchTrendRequestDto.KeywordRequestDto.builder().groupName("오징어게임").keywords(Arrays.asList("오징어게임")).build()))
																		.build();

		final SearchTrendResponseDto responseDto = aiNaverService.searchTrend(requestDto);

		System.out.println("Search Trend Keywords : " +  requestDto.getKeywordGroups());
		System.out.println("Search Trend Result");
		System.out.println(responseDto.getResults().get(0).getData().toString().replaceAll("\\),", "\\)\n"));

	}
}
