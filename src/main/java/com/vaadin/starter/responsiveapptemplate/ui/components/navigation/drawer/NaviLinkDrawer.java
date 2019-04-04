package com.vaadin.starter.responsiveapptemplate.ui.components.navigation.drawer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;

public class NaviLinkDrawer extends NaviDrawer {

    public NaviLinkDrawer() {
        super();
    }

    public NaviLinkItem addNaviItem(VaadinIcon icon, String text,
            Class<? extends Component> navigationTarget) {
        NaviLinkItem item = new NaviLinkItem(icon, text, navigationTarget);
        addNaviItem(item);
        return item;
    }

    public NaviLinkItem addNaviItem(Image image, String text,
            Class<? extends Component> navigationTarget) {
        NaviLinkItem item = new NaviLinkItem(image, text, navigationTarget);
        addNaviItem(item);
        return item;
    }

    public NaviLinkItem addNaviItem(String path, String text,
            Class<? extends Component> navigationTarget) {
        NaviLinkItem item = new NaviLinkItem(path, text, navigationTarget);
        addNaviItem(item);
        return item;
    }

    public NaviLinkItem addNaviItem(NaviItem parent, String text,
            Class<? extends Component> navigationTarget) {
        NaviLinkItem item = new NaviLinkItem(text, navigationTarget);
        addNaviItem(parent, item);
        return item;
    }
}
