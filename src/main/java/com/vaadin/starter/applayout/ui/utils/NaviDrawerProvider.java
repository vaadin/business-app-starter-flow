package com.vaadin.starter.applayout.ui.utils;

import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.components.NaviDrawer;
import com.vaadin.starter.applayout.ui.components.NaviLinkDrawer;
import com.vaadin.starter.applayout.ui.components.NaviTabDrawer;

public class NaviDrawerProvider {

    private static NaviDrawer naviDrawer;

    public static NaviDrawer getNaviDrawer() {
        if (naviDrawer == null) {
            if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
                naviDrawer = new NaviLinkDrawer();
            } else {
                naviDrawer = new NaviTabDrawer();
            }
        }
        return naviDrawer;
    }

}
