package com.vaadin.starter.applayout.ui.components.navigation.drawer;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

public class BrandExpression extends Div {

    private String CLASS_NAME = "brand-expression";

    private Image logo;
    private Image railLogo;

    public BrandExpression() {
        setClassName(CLASS_NAME);

        logo = new Image();
        logo.addClassName(CLASS_NAME + "__logo");

        railLogo = new Image();
        railLogo.addClassName(CLASS_NAME + "__rail-logo");

        if (UIConfig.getShowcase().equals(UIConfig.Showcase.DEMO)) {
            logo.setSrc(UIUtils.IMG_PATH + "logo.svg");
            railLogo.setSrc(UIUtils.IMG_PATH + "logo-only.svg");
        }
        if (UIConfig.getShowcase().equals(UIConfig.Showcase.FINANCE)) {
            logo.setSrc(UIUtils.IMG_PATH + "finance-logo.svg");
            railLogo.setSrc(UIUtils.IMG_PATH + "finance-logo-only.svg");
        }
        if (UIConfig.getShowcase().equals(UIConfig.Showcase.INVENTORY)) {
            logo.setSrc(UIUtils.IMG_PATH + "inventory-logo.svg");
            railLogo.setSrc(UIUtils.IMG_PATH + "inventory-logo-only.svg");
        }

        add(logo, railLogo);
    }

}
