package com.navercloud.openapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.navercloud.openapi.service.AiNaverDemoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
public class AiNaverDemoController {
	private final AiNaverDemoService aiNaverDemoService;

	@GetMapping("objectDetection")
	public void objectDetection() {
		aiNaverDemoService.objectDetection();
	}

	@GetMapping("translation")
	public void translation() {
		aiNaverDemoService.translation();
	}

	@GetMapping("textSummary")
	public void textSummary() {
		aiNaverDemoService.textSummary();
	}

	@GetMapping("searchTrend")
	public void searchTrend() {
		aiNaverDemoService.searchTrend();
	}
}
