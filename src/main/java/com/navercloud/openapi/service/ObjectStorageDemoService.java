package com.navercloud.openapi.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.model.Bucket;
import com.navercloud.openapi.constant.RegionEnum;
import com.navercloud.openapi.dto.userapi.objectstorage.param.CreateBucketParamDto;
import com.navercloud.openapi.dto.userapi.objectstorage.param.CreateFolderParamDto;
import com.navercloud.openapi.dto.userapi.objectstorage.param.DeleteBucketParamDto;
import com.navercloud.openapi.dto.userapi.objectstorage.param.GetBucketListParamDto;
import com.navercloud.openapi.dto.userapi.objectstorage.param.UploadObjectFileParamDto;
import com.navercloud.openapi.service.userapi.ObjectStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ObjectStorageDemoService extends BaseService {
	public static String BUCKET_NAME = "user-api-test-bucket";

	private final ObjectStorageService objectStorageService;

	public void createBucketAndUploadData() {
		final CreateBucketParamDto createBucketParamDto = CreateBucketParamDto.builder().region(RegionEnum.KR).bucketName(BUCKET_NAME).build();
		objectStorageService.createBucket(createBucketParamDto);

		final GetBucketListParamDto getBucketListParamDto = GetBucketListParamDto.builder().region(RegionEnum.KR).build();
		final List<Bucket> bucketList = objectStorageService.getBucketList(getBucketListParamDto);

		final Long count = bucketList.stream().filter(bucket -> bucket.getName().equals(BUCKET_NAME)).count();
		if (count == 1) {
			System.out.println("Bucket 생성 완료");
		}

		try {
			final File uploadFile = new File(new ClassPathResource("sample/test.txt").getURL().getPath());

			final UploadObjectFileParamDto uploadObjectFileParamDto = UploadObjectFileParamDto.builder().region(RegionEnum.KR).bucketName(BUCKET_NAME).folderPath("create-folder/").objectName("test.txt").objectFile(uploadFile).build();

			objectStorageService.uploadObjectFile(uploadObjectFileParamDto);
		} catch (IOException ioe) {
		}
	}

	public void deleteBucket() {
		final DeleteBucketParamDto deleteBucketParamDto = DeleteBucketParamDto.builder().region(RegionEnum.KR).bucketName(BUCKET_NAME).build();
		objectStorageService.deleteBucket(deleteBucketParamDto);
	}
}
