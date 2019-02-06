package com.vaadin.starter.responsiveapptemplate.ui.utils;

public enum TextColor {

	HEADER("header-text"),
	BODY("body-text"),
	SECONDARY("secondary-text"),
	TERTIARY("tertiary-text"),
	DISABLED("disabled-text"),
	PRIMARY("primary-text"),
	ERROR("error-text"),
	SUCCESS("success-text");

	private String style;

	TextColor(String style) {
		this.style = style;
	}

	public String getClassName() {
		return style;
	}

}
