package com.vaadin.starter.responsiveapptemplate.ui.utils;

public enum BadgeShape {

	NORMAL("normal"),
	PILL("pill");

	private String style;

	BadgeShape(String style) {
		this.style = style;
	}

	public String getThemeName() {
		return style;
	}

}
