package com.vaadin.starter.applayout.ui.utils;

import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.components.navigation.drawer.NaviDrawer;
import com.vaadin.starter.applayout.ui.components.navigation.drawer.NaviLinkDrawer;
import com.vaadin.starter.applayout.ui.components.navigation.drawer.NaviTabDrawer;

public class NaviDrawerProvider {

    private static final String NAVI_DRAWER = "navi-drawer";

    public static NaviDrawer getNaviDrawer() {
        if (ComponentUtil.getData(UI.getCurrent(), NAVI_DRAWER) == null) {
            if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
                ComponentUtil.setData(UI.getCurrent(), NAVI_DRAWER, new NaviLinkDrawer());
            } else {
                ComponentUtil.setData(UI.getCurrent(), NAVI_DRAWER, new NaviTabDrawer());
            }
        }
        return (NaviDrawer) ComponentUtil.getData(UI.getCurrent(), NAVI_DRAWER);
    }

}
