package com.navercloud.openapi.dto.userapi.ainaver.constant;

import lombok.Getter;

@Getter
public enum LangEnum {
	KO("ko"),
	EN("en"),
	JA("ja"),
	ZH_CN("zh-CN"),
	ZH_TW("zh-TW"),
	VI("vi"),
	ID("id"),
	TH("th"),
	DE("de"),
	RU("ru"),
	ES("es"),
	IT("it"),
	FR("fr")
    ;

	final private String lang;

	LangEnum(String lang) {
		this.lang = lang;
	}
}
