package com.vaadin.starter.applayout.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServlet;
import com.vaadin.flow.server.VaadinServletConfiguration;
import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.components.navigation.drawer.NaviDrawer;
import com.vaadin.starter.applayout.ui.components.navigation.drawer.NaviLinkDrawer;
import com.vaadin.starter.applayout.ui.components.navigation.drawer.NaviTabDrawer;

import javax.servlet.annotation.WebServlet;

/**
 * @author mavi
 */
public class AppTemplateUI extends UI {
    private final NaviDrawer naviDrawer = UIConfig.getNaviMode() == UIConfig.NaviMode.LINKS ?
            new NaviLinkDrawer() : new NaviTabDrawer();

    public static NaviDrawer getNaviDrawer() {
        return ((AppTemplateUI) UI.getCurrent()).naviDrawer;
    }

    @VaadinServletConfiguration(productionMode = false, ui = AppTemplateUI.class)
    @WebServlet(urlPatterns = {"/*"})
    public static class AppServlet extends VaadinServlet {
    }
}
