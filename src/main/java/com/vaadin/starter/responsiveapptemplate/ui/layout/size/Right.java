package com.vaadin.starter.responsiveapptemplate.ui.layout.size;

public enum Right implements Size {

	XS("var(--lumo-space-xs)", "spacing-r-xs"),
	S("var(--lumo-space-s)", "spacing-r-s"),
	M("var(--lumo-space-m)", "spacing-r-m"),
	L("var(--lumo-space-l)", "spacing-r-l"),
	XL("var(--lumo-space-xl)", "spacing-r-xl");

	private String variable;
	private String spacingClassName;

	Right(String variable, String spacingClassName) {
		this.variable = variable;
		this.spacingClassName = spacingClassName;
	}

	@Override
	public String[] getMarginAttributes() {
		return new String[] { "margin-right" };
	}

	@Override
	public String[] getPaddingAttributes() {
		return new String[] { "padding-right" };
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
