package com.navercloud.openapi.service;

import java.util.Arrays;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.navercloud.openapi.dto.userapi.nas.req.CreateNasRequestDto;
import com.navercloud.openapi.dto.userapi.nas.req.DeleteNasRequestDto;
import com.navercloud.openapi.dto.userapi.nas.req.GetNasListRequestDto;
import com.navercloud.openapi.dto.userapi.nas.res.GetNasListResponseDto;
import com.navercloud.openapi.service.userapi.NasService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NasStorageDemoService extends BaseService {
	private final NasService nasService;

	public void createNas() {
		final CreateNasRequestDto createNasRequestDto = CreateNasRequestDto.builder()
																			.regionCode("KR")
																			.zoneCode("KR-2")
																			.volumeName("userapitest")
																			.volumeSize(500)
																			.volumeAllotmentProtocolTypeCode("NFS")
																			.nasVolumeDescription("user-api-test")
																			.build();

		nasService.createNas(createNasRequestDto);
	}

	public void deleteNas() {
		final GetNasListRequestDto getNasListRequestDto = GetNasListRequestDto.builder().regionCode("KR").volumeName("userapitest").build();

		final GetNasListResponseDto.NasListRawResponseDto nasListRawResponseDto = nasService.getNasList(getNasListRequestDto);

		if (CollectionUtils.isNotEmpty(nasListRawResponseDto.getNasVolumeInstanceList())
			&& nasListRawResponseDto.getNasVolumeInstanceList().size() == 1) {

			final DeleteNasRequestDto deleteNasRequestDto = DeleteNasRequestDto.builder().nasVolumeInstanceNoList(
				Arrays.asList(nasListRawResponseDto.getNasVolumeInstanceList().get(0).getNasVolumeInstanceNo())
			).build();

			nasService.deleteNas(deleteNasRequestDto);
		}
	}
}
