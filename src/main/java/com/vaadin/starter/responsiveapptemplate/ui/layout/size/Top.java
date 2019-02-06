package com.vaadin.starter.responsiveapptemplate.ui.layout.size;

public enum Top implements Size {

	AUTO("auto", null),
	XS("var(--lumo-space-xs)", "spacing-t-xs"),
	S("var(--lumo-space-s)", "spacing-t-s"),
	M("var(--lumo-space-m)", "spacing-t-m"),
	L("var(--lumo-space-l)", "spacing-t-l"),
	XL("var(--lumo-space-xl)", "spacing-t-xl");

	private String variable;
	private String spacingClassName;

	Top(String variable, String spacingClassName) {
		this.variable = variable;
		this.spacingClassName = spacingClassName;
	}

	@Override
	public String[] getMarginAttributes() {
		return new String[]{"margin-top"};
	}

	@Override
	public String[] getPaddingAttributes() {
		return new String[]{"padding-top"};
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
