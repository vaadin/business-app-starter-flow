package com.vaadin.starter.applayout.ui.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;

public class BrandExpression extends Div {

    private String CLASS_NAME = "brand-expression";

    private Image logo;

    public BrandExpression() {
        setClassName(CLASS_NAME);

        logo = new Image();
        logo.setSrc("frontend/styles/images/logo.svg");
        add(logo);
    }

}
