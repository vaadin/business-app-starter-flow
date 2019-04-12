package com.vaadin.starter.business.ui.util;

public enum TextColor {

    HEADER("header-text"), BODY("body-text"), SECONDARY(
            "secondary-text"), TERTIARY("tertiary-text"), DISABLED(
                    "disabled-text"), PRIMARY("primary-text"), PRIMARY_CONTRAST(
                            "primary-contrast-text"), ERROR(
                                    "error-text"), ERROR_CONTRAST(
                                            "error-contrast-text"), SUCCESS(
                                                    "success-text"), SUCCESS_CONTRAST(
                                                            "success-contrast-text");

    private String style;

    TextColor(String style) {
        this.style = style;
    }

    public String getClassName() {
        return style;
    }

}
