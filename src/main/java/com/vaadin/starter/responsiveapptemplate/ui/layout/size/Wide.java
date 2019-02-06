package com.vaadin.starter.responsiveapptemplate.ui.layout.size;

public enum Wide implements Size {

	XS("var(--lumo-space-xs)", "spacing-xs"),
	S("var(--lumo-space-s)", "spacing-s"),
	M("var(--lumo-space-m)", "spacing-m"),
	L("var(--lumo-space-l)", "spacing-l"),
	XL("var(--lumo-space-xl)", "spacing-xl");

	private String variable;
	private String spacingClassName;

	Wide(String variable, String spacingClassName) {
		this.variable = variable;
		this.spacingClassName = spacingClassName;
	}

	@Override
	public String[] getMarginAttributes() {
		return new String[] { "margin" };
	}

	@Override
	public String[] getPaddingAttributes() {
		return new String[] { "padding" };
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
