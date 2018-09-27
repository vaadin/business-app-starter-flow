package com.vaadin.starter.applayout.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class NaviLinkItem extends NaviItem {

    private RouterLink link;

    public NaviLinkItem(VaadinIcon icon, String text, Class<? extends Component> navigationTarget) {
        super(icon, text, navigationTarget);

        link = new RouterLink(null, navigationTarget);
        if (icon != null) {
            link.add(new Icon(icon), new Label(text));
        } else {
            link.add(new Label(text));
        }
        link.setHighlightCondition(HighlightConditions.sameLocation());
        link.setClassName(CLASS_NAME + "__link");

        getElement().insertChild(0, link.getElement());
    }

    public NaviLinkItem(String text, Class<? extends Component> navigationTarget) {
        this(null, text, navigationTarget);
    }
}