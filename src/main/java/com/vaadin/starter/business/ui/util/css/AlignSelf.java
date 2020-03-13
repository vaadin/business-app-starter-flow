package com.vaadin.starter.business.ui.util.css;

public enum AlignSelf {

	BASLINE("baseline"),
	CENTER("center"),
	END("end"),
	START("start"),
	STRETCH("stretch");

	private String value;

	AlignSelf(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
