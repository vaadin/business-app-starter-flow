package com.vaadin.starter.responsiveapptemplate.ui.util;

public enum BadgeShape {

    NORMAL("normal"), PILL("pill");

    private String style;

    BadgeShape(String style) {
        this.style = style;
    }

    public String getThemeName() {
        return style;
    }

}
