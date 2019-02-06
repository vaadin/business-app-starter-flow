package com.vaadin.starter.responsiveapptemplate.ui.layout.size;

public enum Horizontal implements Size {

	XS("var(--lumo-space-xs)", "spacing-h-xs"),
	S("var(--lumo-space-s)", "spacing-h-s"),
	M("var(--lumo-space-m)", "spacing-h-m"),
	L("var(--lumo-space-l)", "spacing-h-l"),
	XL("var(--lumo-space-xl)", "spacing-h-xl"),
	RESPONSIVE_M("var(--lumo-space-r-m)", "spacing-h-r-m"),
	RESPONSIVE_L("var(--lumo-space-r-l)", "spacing-h-r-l");

	private String variable;
	private String spacingClassName;

	Horizontal(String variable, String spacingClassName) {
		this.variable = variable;
		this.spacingClassName = spacingClassName;
	}

	@Override
	public String[] getMarginAttributes() {
		return new String[] { "margin-left", "margin-right" };
	}

	@Override
	public String[] getPaddingAttributes() {
		return new String[] { "padding-left", "padding-right" };
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
