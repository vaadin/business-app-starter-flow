package com.vaadin.starter.responsiveapptemplate.ui.utils;

public enum FontSize {

    XXS("font-size-xxs"), XS("font-size-xs"), S("font-size-s"), M(
            "font-size-m"), L("font-size-l"), XL("font-size-xl"), XXL(
                    "font-size-xxl"), XXXL("font-size-xxxl");

    private String style;

    FontSize(String style) {
        this.style = style;
    }

    public String getClassName() {
        return style;
    }

}
