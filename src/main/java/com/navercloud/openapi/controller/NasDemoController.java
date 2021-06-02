package com.navercloud.openapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.navercloud.openapi.service.NasStorageDemoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
public class NasDemoController {
	private final NasStorageDemoService nasStorageDemoService;

	@GetMapping("createNas")
	public void createNas() {
		nasStorageDemoService.createNas();
	}

	@GetMapping("deleteNas")
	public void deleteNas() {
		nasStorageDemoService.deleteNas();
	}
}
