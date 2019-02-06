package com.vaadin.starter.responsiveapptemplate.ui.layout;

public enum BorderRadius {

	S("var(--lumo-border-radius-s)"),
	M("var(--lumo-border-radius-m)"),
	L("var(--lumo-border-radius-l)");

	private String value;

	BorderRadius(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
