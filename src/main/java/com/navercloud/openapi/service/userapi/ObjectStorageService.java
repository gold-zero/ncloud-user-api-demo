package com.navercloud.openapi.service.userapi;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AbortMultipartUploadRequest;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListMultipartUploadsRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.MultipartUpload;
import com.amazonaws.services.s3.model.MultipartUploadListing;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.navercloud.openapi.constant.RegionEnum;
import com.navercloud.openapi.dto.userapi.objectstorage.param.CreateBucketParamDto;
import com.navercloud.openapi.dto.userapi.objectstorage.param.CreateFolderParamDto;
import com.navercloud.openapi.dto.userapi.objectstorage.param.DeleteBucketParamDto;
import com.navercloud.openapi.dto.userapi.objectstorage.param.DeleteObjectFileParamDto;
import com.navercloud.openapi.dto.userapi.objectstorage.param.DownloadObjectFileParamDto;
import com.navercloud.openapi.dto.userapi.objectstorage.param.GetBucketListParamDto;
import com.navercloud.openapi.dto.userapi.objectstorage.param.GetObjectListParamDto;
import com.navercloud.openapi.dto.userapi.objectstorage.param.UploadObjectFileParamDto;
import com.navercloud.openapi.dto.userapi.objectstorage.res.ObjectListResponseDto;
import com.navercloud.openapi.service.BaseService;

@Service
public class ObjectStorageService extends BaseService {
	@Value("#{${open.api.obj.host}}")
	private Map<String, String> objectStorageHost;

	@Value("${open.api.host}")
	private String openApiHost;

	private AmazonS3 getAmazoneS3(final RegionEnum region) {
		return createAmazoneS3(region.getObjectStorageRegionName(), objectStorageHost.get(region.getObjectStorageRegionName()));
	}

	public void createBucket(final CreateBucketParamDto paramDto) {
		final AmazonS3 s3 = getAmazoneS3(paramDto.getRegion());
		final String bucketName = paramDto.getBucketName();

		try {
			if (s3.doesBucketExistV2(bucketName)) {
				throw new RuntimeException("Bucket "+ bucketName + " already exists.");
			} else {
				s3.createBucket(bucketName);
			}
		} catch (AmazonS3Exception e) {
			e.printStackTrace();
		} catch(SdkClientException e) {
			e.printStackTrace();
		}
	}

	public List<Bucket> getBucketList(final GetBucketListParamDto paramDto) {
		final AmazonS3 s3 = getAmazoneS3(paramDto.getRegion());

		return s3.listBuckets();
	}

	public void deleteBucket(final DeleteBucketParamDto paramDto) {
		final AmazonS3 s3 = getAmazoneS3(paramDto.getRegion());

		final String bucketName = paramDto.getBucketName();

		try {
			// delete bucket if the bucket exists
			if (s3.doesBucketExistV2(bucketName)) {
				// delete all objects
				ObjectListing objectListing = s3.listObjects(bucketName);
				while (true) {
					for (Iterator<?> iterator = objectListing.getObjectSummaries().iterator(); iterator.hasNext();) {
						S3ObjectSummary summary = (S3ObjectSummary)iterator.next();
						s3.deleteObject(bucketName, summary.getKey());
					}

					if (objectListing.isTruncated()) {
						objectListing = s3.listNextBatchOfObjects(objectListing);
					} else {
						break;
					}
				}

				// abort incomplete multipart uploads
				MultipartUploadListing multipartUploadListing = s3.listMultipartUploads(new ListMultipartUploadsRequest(bucketName));
				while (true) {
					for (Iterator<?> iterator = multipartUploadListing.getMultipartUploads().iterator(); iterator.hasNext();) {
						MultipartUpload multipartUpload = (MultipartUpload)iterator.next();
						s3.abortMultipartUpload(new AbortMultipartUploadRequest(bucketName, multipartUpload.getKey(), multipartUpload.getUploadId()));
					}

					if (multipartUploadListing.isTruncated()) {
						ListMultipartUploadsRequest listMultipartUploadsRequest = new ListMultipartUploadsRequest(bucketName);
						listMultipartUploadsRequest.withUploadIdMarker(multipartUploadListing.getNextUploadIdMarker());
						multipartUploadListing = s3.listMultipartUploads(listMultipartUploadsRequest);
					} else {
						break;
					}
				}

				s3.deleteBucket(bucketName);
			}
		} catch (AmazonS3Exception e) {
			e.printStackTrace();
		} catch(SdkClientException e) {
			e.printStackTrace();
		}
	}

	public void createFolder(final CreateFolderParamDto paramDto) {
		if (paramDto.getFolderPath().lastIndexOf("/") == 0) {
			return;
		}

		final AmazonS3 s3 = getAmazoneS3(paramDto.getRegion());

		final String bucketName = paramDto.getBucketName();
		final String folderPath = paramDto.getFolderPath();

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(0L);
		objectMetadata.setContentType("application/x-directory");
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderPath, new ByteArrayInputStream(new byte[0]), objectMetadata);

		try {
			s3.putObject(putObjectRequest);
		} catch (AmazonS3Exception e) {
			e.printStackTrace();
		} catch(SdkClientException e) {
			e.printStackTrace();
		}
	}

	public void uploadObjectFile(final UploadObjectFileParamDto paramDto) {
		if (StringUtils.isNotEmpty(paramDto.getFolderPath())) {
			final CreateFolderParamDto uploadObjectFileParamDto = objectMapper.convertValue(paramDto, CreateFolderParamDto.class);
			createFolder(uploadObjectFileParamDto);
		}

		final AmazonS3 s3 = getAmazoneS3(paramDto.getRegion());
		try {
			s3.putObject(paramDto.getBucketName(), paramDto.getObjectFilePath(), paramDto.getObjectFile());
		} catch (AmazonS3Exception e) {
			e.printStackTrace();
		} catch(SdkClientException e) {
			e.printStackTrace();
		}
	}

	public ObjectListResponseDto getObjectList(final GetObjectListParamDto paramDto) {
		final List<ObjectListResponseDto.FolderRawResponseDto> folderList = new ArrayList<>();
		final List<ObjectListResponseDto.FileRawResponseDto> fileList = new ArrayList<>();

		final ObjectListResponseDto responseDto = ObjectListResponseDto.builder().folderList(folderList).fileList(fileList).build();

		final AmazonS3 s3 = getAmazoneS3(paramDto.getRegion());

		final String bucketName = paramDto.getBucketName();

		// list all in the bucket
		try {
			ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
				.withBucketName(bucketName)
				.withMaxKeys(300);

			ObjectListing objectListing = s3.listObjects(listObjectsRequest);

			System.out.println("Object List:");
			while (true) {
				for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
					if (objectSummary.getKey().lastIndexOf("/") > 0) {
						folderList.add(ObjectListResponseDto.FolderRawResponseDto.builder().folderPath(objectSummary.getKey()).lastModified(objectSummary.getLastModified()).build());
					} else {
						fileList.add(ObjectListResponseDto.FileRawResponseDto.builder().filePath(objectSummary.getKey()).size(objectSummary.getSize()).lastModified(objectSummary.getLastModified()).build());
					}
				}

				if (objectListing.isTruncated()) {
					objectListing = s3.listNextBatchOfObjects(objectListing);
				} else {
					break;
				}
			}
		} catch (AmazonS3Exception e) {
			e.printStackTrace();
		}

		return responseDto;
	}

	public void downloadObjectFile(final DownloadObjectFileParamDto paramDto) {
		final AmazonS3 s3 = getAmazoneS3(paramDto.getRegion());

		try {
			S3Object s3Object = s3.getObject(paramDto.getBucketName(), paramDto.getObjectPath());
			S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();

			OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(paramDto.getDownloadFilePath()));
			byte[] bytesArray = new byte[4096];
			int bytesRead = -1;
			while ((bytesRead = s3ObjectInputStream.read(bytesArray)) != -1) {
				outputStream.write(bytesArray, 0, bytesRead);
			}

			outputStream.close();
			s3ObjectInputStream.close();
		} catch (AmazonS3Exception e) {
			e.printStackTrace();
		} catch(SdkClientException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO
		}
	}

	public void deleteObjectFile(final DeleteObjectFileParamDto paramDto) {
		final AmazonS3 s3 = getAmazoneS3(paramDto.getRegion());

		try {
			s3.deleteObject(paramDto.getBucketName(), paramDto.getObjectPath());
		} catch (AmazonS3Exception e) {
			e.printStackTrace();
		} catch(SdkClientException e) {
			e.printStackTrace();
		}
	}
}
