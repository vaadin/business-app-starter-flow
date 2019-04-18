package com.vaadin.starter.business.ui.layout.size;

public enum Left implements Size {

    AUTO("auto", null),

    XS("var(--lumo-space-xs)", "spacing-l-xs"), S("var(--lumo-space-s)",
            "spacing-l-s"), M("var(--lumo-space-m)", "spacing-l-m"), L(
                    "var(--lumo-space-l)",
                    "spacing-l-l"), XL("var(--lumo-space-xl)", "spacing-l-xl"),

    RESPONSIVE_M("var(--lumo-space-r-m)", null), RESPONSIVE_L(
            "var(--lumo-space-r-l)",
            null), RESPONSIVE_X("var(--lumo-space-r-x)", null);

    private String variable;
    private String spacingClassName;

    Left(String variable, String spacingClassName) {
        this.variable = variable;
        this.spacingClassName = spacingClassName;
    }

    @Override
    public String[] getMarginAttributes() {
        return new String[] { "margin-left" };
    }

    @Override
    public String[] getPaddingAttributes() {
        return new String[] { "padding-left" };
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
