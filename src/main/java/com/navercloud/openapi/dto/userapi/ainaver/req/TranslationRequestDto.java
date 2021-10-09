package com.navercloud.openapi.dto.userapi.ainaver.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.navercloud.openapi.dto.userapi.ainaver.constant.LangEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TranslationRequestDto {
    private LangEnum sourceLang;
    private LangEnum targetLang;
    private String text;

    public String getSource() {
    	return sourceLang.getLang();
    }

    public String getTarget() {
    	return targetLang.getLang();
    }
}
