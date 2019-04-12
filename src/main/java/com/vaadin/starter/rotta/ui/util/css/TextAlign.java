package com.vaadin.starter.rotta.ui.util.css;

public enum TextAlign {

    CENTER("center"), JUSTIFY("justify"), LEFT("left"), RIGHT("right");

    private String value;

    TextAlign(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
