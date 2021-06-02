package com.navercloud.openapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.navercloud.openapi.service.ArchiveStorageDemoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
public class ArchiveStorageDemoController {
	private final ArchiveStorageDemoService archiveStorageDemoService;

	@GetMapping("createContainerAndUploadData")
	public void createBucketAndUploadData() {
		archiveStorageDemoService.createBucketAndUploadData();
	}

	@GetMapping("deleteContainer")
	public void deleteBucket() {
		archiveStorageDemoService.deleteContainer();
	}
}
