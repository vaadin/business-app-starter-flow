package com.vaadin.starter.applayout.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;

public class NaviTabDrawer extends NaviDrawer {

    public NaviTabDrawer() {
        super();
    }

    public NaviTabItem addNaviItem(VaadinIcon icon, String text, Class<? extends Component> navigationTarget) {
        NaviTabItem item = new NaviTabItem(icon, text, navigationTarget);
        addNaviItem(item);
        return item;
    }

    public NaviTabItem addNaviItem(NaviItem parent, String text, Class<? extends Component> navigationTarget) {
        NaviTabItem item = new NaviTabItem(text, navigationTarget);
        addNaviItem(parent, item);
        return item;
    }
}