package com.vaadin.starter.responsiveapptemplate.ui.utils;

public enum ButtonSize {

	SMALL("small"),
	LARGE("large");

	private String style;

	ButtonSize(String style) {
		this.style = style;
	}

	public String getStyle() {
		return style;
	}

}
