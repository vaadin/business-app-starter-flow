package com.vaadin.starter.responsiveapptemplate.ui.layout.size;

public enum Bottom implements Size {

	XS("var(--lumo-space-xs)", "spacing-b-xs"),
	S("var(--lumo-space-s)", "spacing-b-s"),
	M("var(--lumo-space-m)", "spacing-b-m"),
	L("var(--lumo-space-l)", "spacing-b-l"),
	XL("var(--lumo-space-xl)", "spacing-b-xl");

	private String variable;
	private String spacingClassName;

	Bottom(String variable, String spacingClassName) {
		this.variable = variable;
		this.spacingClassName = spacingClassName;
	}

	@Override
	public String[] getMarginAttributes() {
		return new String[] { "margin-bottom" };
	}

	@Override
	public String[] getPaddingAttributes() {
		return new String[] { "padding-bottom" };
	}

	@Override
	public String getSpacingClassName() {
		return this.spacingClassName;
	}

	@Override
	public String getVariable() {
		return this.variable;
	}
}
