package com.vaadin.starter.responsiveapptemplate.ui.utils;

public enum IconStyle {

	PRIMARY("primary"),
	TERTIARY("tertiary"),
	TERTIARY_INLINE("tertiary-inline"),
	SUCCESS("success"),
	SUCCESS_PRIMARY("success primary"),
	ERROR("error"),
	ERROR_PRIMARY("error primary"),
	CONTRAST("contrast"),
	CONTRAST_PRIMARY("contrast primary"),
	ICON("icon");

	private String style;

	IconStyle(String style) {
		this.style = style;
	}

	public String getStyle() {
		return style;
	}

}
