package com.vaadin.starter.responsiveapptemplate.ui.layout;

public enum BoxSizing {

    BORDER_BOX("border-box"), CONTENT_BOX("content-box");

    private String value;

    BoxSizing(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
