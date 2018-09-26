package com.vaadin.starter.applayout.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;

public class NaviLinkDrawer extends NaviDrawer {

    public NaviLinkDrawer() {
        super();
    }

    public NaviLinkItem addNaviItem(VaadinIcon icon, String text, Class<? extends Component> navigationTarget) {
        NaviLinkItem item = new NaviLinkItem(icon, text, navigationTarget);
        addNaviItem(item);
        return item;
    }

    public NaviLinkItem addNaviItem(NaviItem parent, String text, Class<? extends Component> navigationTarget) {
        NaviLinkItem item = new NaviLinkItem(text, navigationTarget);
        addNaviItem(parent, item);
        return item;
    }
}
