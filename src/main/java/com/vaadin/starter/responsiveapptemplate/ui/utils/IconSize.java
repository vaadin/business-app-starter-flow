package com.vaadin.starter.responsiveapptemplate.ui.utils;

public enum IconSize {

	SMALL("size-s"),
	LARGE("size-l");

	private String style;

	IconSize(String style) {
		this.style = style;
	}

	public String getStyle() {
		return style;
	}

}
