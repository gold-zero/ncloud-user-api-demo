package com.navercloud.openapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.navercloud.openapi.service.DemoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
public class DemoController {
	private final DemoService testService;

	@GetMapping("createVpcServer")
	public void createVpcServer() throws InterruptedException {
		testService.createVpcServer();
	}
}
