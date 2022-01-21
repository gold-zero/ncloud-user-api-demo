package com.navercloud.openapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.navercloud.openapi.service.CloudDbService;
import com.navercloud.openapi.service.DemoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
public class CloudDBController {
	private final CloudDbService cloudDbService;

	@GetMapping("exportTest")
	public void exportTest() {
		cloudDbService.exportTest();
	}
}
