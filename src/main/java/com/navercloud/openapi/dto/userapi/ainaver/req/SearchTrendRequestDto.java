package com.navercloud.openapi.dto.userapi.ainaver.req;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchTrendRequestDto {
    private String startDate; // yyyy-mm-dd
    private String endDate; // yyyy-mm-dd
    private String timeUnit; // date, week, month
	private List<KeywordRequestDto> keywordGroups;
	private String device; // pc / mo
	private String gender; // m / f
	private List<String> ages; // 1~11

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class KeywordRequestDto {
		private String groupName;
		private List<String> keywords;
	}
}
