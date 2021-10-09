package com.navercloud.openapi.dto.userapi.ainaver.res;

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
public class SearchTrendResponseDto {
    private String startDate;
    private String endDate;
    private String timeUnit;
    private List<SearchTrendResultResponseDto> results;

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class SearchTrendResultResponseDto {
		private String title;
		private List<String> keywords;
		private List<SearchTrendDateResultResponseDto> data;
	}

	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class SearchTrendDateResultResponseDto {
		private String period;
		private Double ratio;
	}
}
