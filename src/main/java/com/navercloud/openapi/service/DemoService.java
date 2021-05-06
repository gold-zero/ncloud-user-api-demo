package com.navercloud.openapi.service;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.navercloud.openapi.dto.userapi.acg.req.CreateAccessControlGroupRequestDto;
import com.navercloud.openapi.dto.userapi.acg.req.GetAccessControlGroupDetailRequestDto;
import com.navercloud.openapi.dto.userapi.acg.res.CreateAccessControlGroupResponseDto;
import com.navercloud.openapi.dto.userapi.acg.res.GetAccessControlGroupDetailResponseDto;
import com.navercloud.openapi.dto.userapi.lgoinkey.req.CreateLoginKeyRequestDto;
import com.navercloud.openapi.dto.userapi.lgoinkey.res.CreateLoginKeyResponseDto;
import com.navercloud.openapi.dto.userapi.server.req.CreateVpcServerRequestDto;
import com.navercloud.openapi.dto.userapi.vpc.req.CreateSubnetRequestDto;
import com.navercloud.openapi.dto.userapi.vpc.req.CreateVpcRequestDto;
import com.navercloud.openapi.dto.userapi.vpc.req.GetNetworkAclListRequestDto;
import com.navercloud.openapi.dto.userapi.vpc.req.GetSubnetDetailRequestDto;
import com.navercloud.openapi.dto.userapi.vpc.req.GetVpcDetailRequestDto;
import com.navercloud.openapi.dto.userapi.vpc.res.CreateSubnetResponseDto;
import com.navercloud.openapi.dto.userapi.vpc.res.CreateVpcResponseDto;
import com.navercloud.openapi.dto.userapi.vpc.res.GetNetworkAclListResponseDto;
import com.navercloud.openapi.dto.userapi.vpc.res.GetSubnetDetailResponseDto;
import com.navercloud.openapi.dto.userapi.vpc.res.GetVpcDetailResponseDto;
import com.navercloud.openapi.service.userapi.AcgService;
import com.navercloud.openapi.service.userapi.LoginKeyService;
import com.navercloud.openapi.service.userapi.VpcCommonService;
import com.navercloud.openapi.service.userapi.VpcServerService;
import com.navercloud.openapi.service.userapi.VpcService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DemoService extends BaseService {
	private final VpcCommonService vpcCommonService;

	private final VpcService vpcService;

	private final VpcServerService vpcServerService;

	private final LoginKeyService loginKeyService;

	private final AcgService acgService;

	public void createVpcServer() throws InterruptedException {
		final CreateVpcRequestDto createVpcRequestDto = CreateVpcRequestDto.builder().regionCode("KR").vpcName("user-api").ipv4CidrBlock("10.0.0.0/16").build();

		System.out.println("##### VPC 생성요청.");
		final CreateVpcResponseDto.VpcInstanceDto vpcInstanceDto = vpcService.createVpc(createVpcRequestDto);

		final String vpcNo = vpcInstanceDto.getVpcNo();
		System.out.println("##### VPC No : " + vpcNo);

		final GetVpcDetailRequestDto getVpcDetailRequestDto = GetVpcDetailRequestDto.builder().vpcNo(vpcNo).build();
		for (int i=0; i<30; i++) {
			Thread.sleep(1000);

			final GetVpcDetailResponseDto getVpcDetailResponseDto = vpcService.getVpcDetail(getVpcDetailRequestDto);

			if ("RUN".equals(getVpcDetailResponseDto.getGetVpcDetailResponse().getVpcList().get(0).getVpcStatus().getCode())) {
				System.out.println("##### VPC 생성완료.");
				break;
			}
		}


		System.out.println("##### NetworkAcl 생성확인.");
		String networkAclNo = null;
		final GetNetworkAclListRequestDto getNetworkAclListRequestDto = GetNetworkAclListRequestDto.builder().vpcNo(vpcNo).networkAclName("default").build();
		for (int i=0; i<30; i++) {
			Thread.sleep(1000);

			final GetNetworkAclListResponseDto getNetworkAclListResponseDto = vpcService.getNetworkAclList(getNetworkAclListRequestDto);

			if ("RUN".equals(getNetworkAclListResponseDto.getGetNetworkAclListResponse().getNetworkAclList().get(0).getNetworkAclStatus().getCode())) {
				networkAclNo = getNetworkAclListResponseDto.getGetNetworkAclListResponse().getNetworkAclList().get(0).getNetworkAclNo();
				System.out.println("##### NetworkAcl No : " + networkAclNo);
				System.out.println("##### NetworkAcl 생성완료.");
				break;
			}
		}

		final CreateSubnetRequestDto createSubnetRequestDto = CreateSubnetRequestDto.builder().regionCode("KR").zoneCode("KR-2").vpcNo(vpcNo).subnetName("user-api").subnet("10.0.64.0/20").networkAclNo(networkAclNo).subnetTypeCode("PUBLIC").build();

		System.out.println("##### Subnet 생성요청.");
		final CreateSubnetResponseDto.SubnetInstanceDto subnetInstanceDto = vpcService.createSubnet(createSubnetRequestDto);

		final String subnetNo = subnetInstanceDto.getSubnetNo();
		System.out.println("##### Subnet No : " + subnetNo);

		final GetSubnetDetailRequestDto getSubnetDetailRequestDto = GetSubnetDetailRequestDto.builder().subnetNo(subnetNo).build();
		for (int i=0; i<30; i++) {
			Thread.sleep(1000);

			final GetSubnetDetailResponseDto.SubnetInstanceDto subnetDetail = vpcService.getSubnetDetail(getSubnetDetailRequestDto);

			if ("RUN".equals(subnetDetail.getSubnetStatus().getCode())) {
				System.out.println("##### Subnet 생성완료.");
				break;
			}
		}

		final CreateLoginKeyRequestDto createLoginKeyRequestDto = CreateLoginKeyRequestDto.builder().keyName("user-api-key").build();
		System.out.println("##### Login Key 생성요청.");
		final CreateLoginKeyResponseDto.CreateLoginKeyRawResponseDto createLoginKeyRawResponseDto = loginKeyService.createLoginKey(createLoginKeyRequestDto);
		System.out.println(createLoginKeyRawResponseDto.getPrivateKey());
		System.out.println("##### Login Key 생성완료.");

		System.out.println("##### ACG 생성요청.");
		final CreateAccessControlGroupRequestDto createAccessControlGroupRequestDto = CreateAccessControlGroupRequestDto.builder().vpcNo(vpcNo).accessControlGroupName("user-api-acg").build();
		final CreateAccessControlGroupResponseDto.AcgInstanceDto acgInstanceDto = acgService.createAccessControlGroup(createAccessControlGroupRequestDto);

		final String acgNo = acgInstanceDto.getAccessControlGroupNo();
		System.out.println("##### ACG No : " + acgNo);

		final GetAccessControlGroupDetailRequestDto getAccessControlGroupDetailRequestDto = GetAccessControlGroupDetailRequestDto.builder().accessControlGroupNo(acgNo).build();
		for (int i=0; i<30; i++) {
			Thread.sleep(1000);

			final GetAccessControlGroupDetailResponseDto.AcgInstanceDto acgDetail = acgService.getAccessControlGroupDetail(getAccessControlGroupDetailRequestDto);

			if ("RUN".equals(acgDetail.getAccessControlGroupStatus().getCode())) {
				System.out.println("##### Acg 생성완료.");
				break;
			}
		}

		System.out.println("##### Vpc Server 생성요청.");
		final CreateVpcServerRequestDto.NetworkInterface networkInterface = CreateVpcServerRequestDto.NetworkInterface.builder().accessControlGroupNoList(Arrays.asList(acgNo)).networkInterfaceOrder(0).build();

		final CreateVpcServerRequestDto createVpcServerRequestDto = CreateVpcServerRequestDto.builder()
			.regionCode("KR")
			.serverProductCode("SVR.VSVR.STAND.C002.M008.NET.HDD.B050.G002")
			.serverImageProductCode("SW.VSVR.OS.LNX64.CNTOS.0703.B050")
			.vpcNo(vpcNo)
			.subnetNo(subnetNo)
			.serverName("user-api-test-server")
			.loginKeyName("user-api-test-key")
			.networkInterfaceList(Arrays.asList(networkInterface))
			.build();

		vpcServerService.createServerInstances(createVpcServerRequestDto);

		System.out.println("##### Vpc Server 생성완료.");
	}
}
