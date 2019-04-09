package com.vaadin.starter.responsiveapptemplate.ui.components.navigation.drawer;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;

public class BrandExpression extends Composite<Div> {

    private String CLASS_NAME = "brand-expression";

    private Image logo;
    private Image railLogo;

    public BrandExpression() {
        getContent().setClassName(CLASS_NAME);

        logo = new Image();
        logo.addClassName(CLASS_NAME + "__logo");

        railLogo = new Image();
        railLogo.addClassName(CLASS_NAME + "__rail-logo");

        logo.setSrc(UIUtils.IMG_PATH + "logo.svg");
        railLogo.setSrc(UIUtils.IMG_PATH + "logo-only.svg");

        getContent().add(logo, railLogo);
    }

}
