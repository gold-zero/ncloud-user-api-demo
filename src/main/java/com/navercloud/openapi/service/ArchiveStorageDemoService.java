package com.navercloud.openapi.service;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.navercloud.openapi.dto.userapi.archivestorage.param.CreateContainerParamDto;
import com.navercloud.openapi.dto.userapi.archivestorage.param.DeleteContainerParamDto;
import com.navercloud.openapi.dto.userapi.archivestorage.param.DeleteObjectParamDto;
import com.navercloud.openapi.dto.userapi.archivestorage.param.UploadArchiveObjectFileParamDto;
import com.navercloud.openapi.service.userapi.ArchiveStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArchiveStorageDemoService extends BaseService {
	public static String CONTAINER_NAME = "user-api-container";

	private final ArchiveStorageService archiveStorageService;

	public void createBucketAndUploadData() {
		final CreateContainerParamDto createContainerParamDto = CreateContainerParamDto.builder()
																						.containerName(CONTAINER_NAME)
																						.build();

		archiveStorageService.createContainer(createContainerParamDto);

		try {
			final File uploadFile = new File(new ClassPathResource("sample/test.txt").getURL().getPath());

			final UploadArchiveObjectFileParamDto uploadObjectFileParamDto = UploadArchiveObjectFileParamDto.builder().containerName(CONTAINER_NAME).directoryPath("sample").objectName("test.txt").file(uploadFile).build();

			archiveStorageService.uploadObjectFile(uploadObjectFileParamDto);
		} catch (IOException ioe) {
		}
	}

	public void deleteContainer() {
		final DeleteContainerParamDto deleteContainerParamDto = DeleteContainerParamDto.builder()
																						.containerName(CONTAINER_NAME)
																						.build();

		archiveStorageService.deleteContainer(deleteContainerParamDto);
	}
}
