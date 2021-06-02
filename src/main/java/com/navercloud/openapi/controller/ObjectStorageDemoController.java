package com.navercloud.openapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.navercloud.openapi.service.ObjectStorageDemoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
public class ObjectStorageDemoController {
	private final ObjectStorageDemoService objectStorageDemoService;

	@GetMapping("createBucketAndUploadData")
	public void createBucketAndUploadData() {
		objectStorageDemoService.createBucketAndUploadData();
	}

	@GetMapping("deleteBucket")
	public void deleteBucket() {
		objectStorageDemoService.deleteBucket();
	}
}
