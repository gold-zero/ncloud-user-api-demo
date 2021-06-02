package com.navercloud.openapi.service.userapi;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.openstack4j.api.OSClient;
import org.openstack4j.api.storage.ObjectStorageContainerService;
import org.openstack4j.api.storage.ObjectStorageObjectService;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.common.Payloads;
import org.openstack4j.model.identity.v3.Token;
import org.openstack4j.model.storage.object.SwiftContainer;
import org.openstack4j.model.storage.object.SwiftObject;
import org.openstack4j.model.storage.object.options.ContainerListOptions;
import org.openstack4j.model.storage.object.options.CreateUpdateContainerOptions;
import org.openstack4j.model.storage.object.options.ObjectListOptions;
import org.openstack4j.model.storage.object.options.ObjectPutOptions;
import org.openstack4j.openstack.OSFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.navercloud.openapi.dto.userapi.archivestorage.param.CreateContainerParamDto;
import com.navercloud.openapi.dto.userapi.archivestorage.param.CreateDirectoryParamDto;
import com.navercloud.openapi.dto.userapi.archivestorage.param.DeleteContainerParamDto;
import com.navercloud.openapi.dto.userapi.archivestorage.param.DeleteObjectParamDto;
import com.navercloud.openapi.dto.userapi.archivestorage.param.GetArchiveObjectListParamDto;
import com.navercloud.openapi.dto.userapi.archivestorage.param.UploadArchiveObjectFileParamDto;
import com.navercloud.openapi.service.BaseService;

@Service
public class ArchiveStorageService extends BaseService {
	@Value("${open.api.archive.certificate.host}")
	private String certificateHost;

	@Value("${open.api.archive.host}")
	private String archiveHost;

	@Value("${open.api.archive.domainId}")
	private String archiveDomainId;

	@Value("${open.api.archive.projectId}")
	private String archiveProjectId;

	private OSClient.OSClientV3 createClient() {
		final Token token = OSFactory.builderV3().endpoint(certificateHost+"/v3")
											.credentials(accessKey, secretKey, Identifier.byId(archiveDomainId))
											.scopeToProject(Identifier.byId(archiveProjectId), Identifier.byId(archiveDomainId))
											.authenticate().getToken();

		return OSFactory.clientFromToken(token);
	}

	public List<? extends SwiftContainer> getContainerList() {
		final OSClient.OSClientV3 client = createClient();

		final ObjectStorageContainerService containerService = client.objectStorage().containers();

		return containerService.list(ContainerListOptions.create().limit(100));
	}

	public void createContainer(CreateContainerParamDto paramDto) {
		final OSClient.OSClientV3 client = createClient();

		final ObjectStorageContainerService containerService = client.objectStorage().containers();

		final CreateUpdateContainerOptions createUpdateContainerOptions = CreateUpdateContainerOptions.create();

		if (ObjectUtils.isNotEmpty(paramDto.getMetadata())) {
			createUpdateContainerOptions.metadata(paramDto.getMetadata());
		}

		final ActionResponse response = containerService.create(paramDto.getContainerName(), createUpdateContainerOptions);

		if (!response.isSuccess()) {
			throw new RuntimeException("생성 실패");
		} else if (response.getCode() == 202) {
			throw new RuntimeException("이미 존재하는 컨테이너 입니다.");
		}
	}

	public void deleteContainer(DeleteContainerParamDto paramDto) {
		deleteObject(DeleteObjectParamDto.builder().containerName(paramDto.getContainerName()).objectPath("").build());

		final OSClient.OSClientV3 client = createClient();

		final ObjectStorageContainerService containerService = client.objectStorage().containers();

		final ActionResponse response = containerService.delete(paramDto.getContainerName());

		if (!response.isSuccess()) {
			throw new RuntimeException("삭제 실패");
		}
	}

	public List<? extends SwiftObject> getObjectList(GetArchiveObjectListParamDto paramDto) {
		final OSClient.OSClientV3 client = createClient();

		final ObjectStorageObjectService objectService = client.objectStorage().objects();

		return objectService.list(paramDto.getContainerName(), ObjectListOptions.create().path(paramDto.getDirectoryPath()));
	}

	// Object 중복시 덮어쓰기
	public void uploadObjectFile(UploadArchiveObjectFileParamDto paramDto) {
		final OSClient.OSClientV3 client = createClient();

		final ObjectStorageObjectService objectService = client.objectStorage().objects();

		final ObjectPutOptions objectPutOptions =  ObjectPutOptions.create();
		if (ObjectUtils.isNotEmpty(paramDto.getMetaData())) {
			objectPutOptions.metadata(paramDto.getMetaData());
		}

		if (StringUtils.isNotEmpty(paramDto.getContentType())) {
			objectPutOptions.contentType(paramDto.getContentType());
		}

		final CreateDirectoryParamDto createDirectoryParamDto = objectMapper.convertValue(paramDto, CreateDirectoryParamDto.class);
		final String directoryPath = createDirectory(createDirectoryParamDto);

		objectService.put(paramDto.getContainerName(), directoryPath + paramDto.getObjectName(), Payloads.create(paramDto.getFile()),
			objectPutOptions);
	}

	public void deleteObject(DeleteObjectParamDto paramDto) {
		final OSClient.OSClientV3 client = createClient();

		final ObjectStorageObjectService objectService = client.objectStorage().objects();

		final List<SwiftObject> objectList = getAllObjectList(paramDto);

		final List<String> deleteFailList = new ArrayList<>();
		for (SwiftObject object : objectList) {
			final ActionResponse response = objectService.delete(paramDto.getContainerName(), object.getName());

			// 204 - 삭제됨
			if (!response.isSuccess()) {
				deleteFailList.add(object.getName());
			}
		}

		if (CollectionUtils.isNotEmpty(deleteFailList)) {
			throw new RuntimeException("일부 데이터 삭제에 실패 했습니다.");
		}
	}

	private List<SwiftObject> getAllObjectList(DeleteObjectParamDto paramDto) {
		final OSClient.OSClientV3 client = createClient();

		final ObjectStorageObjectService objectService = client.objectStorage().objects();

		final List<? extends SwiftObject> objects = objectService.list(paramDto.getContainerName());

		return objects.stream().filter(t -> t.getName().startsWith(paramDto.getObjectPath())).collect(Collectors.toCollection(ArrayList::new));
	}

	public String createDirectory(CreateDirectoryParamDto paramDto) {
		final OSClient.OSClientV3 client = createClient();

		final ObjectStorageContainerService  containerService = client.objectStorage().containers();

		final String[] directoryPathList = paramDto.getDirectoryPath().split("/");

		String path = "";
		for (String directoryName : directoryPathList) {
			path += directoryName + "/";

			containerService.createPath(paramDto.getContainerName(), path);
		}

		return path;
	}
}
