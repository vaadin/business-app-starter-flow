package com.vaadin.starter.applayout.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.shared.Registration;
import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.components.*;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

@HtmlImport("frontend://styles/shared-styles.html")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class Root extends FlexLayout
        implements RouterLayout, PageConfigurator {

    private String CLASS_NAME = "root";

    private AppHeaderOuter appHeaderOuter;
    private AppHeaderInner appHeaderInner;

    private AppFooterOuter appFooterOuter;
    private AppFooterInner appFooterInner;

    private NaviDrawer naviDrawer;

    private FlexLayout viewContainer;

    // App specific fields goes here.
    private Registration contextualNaviListener;

    public Root() {
        setClassName(CLASS_NAME);

        // Initialise all the building blocks.
        init();

        // App specific stuff goes here.

    }

    private void init() {
        initAppHeaderOuter();

        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            initNaviLinkDrawer();
        } else {
            initNaviTabDrawer();
        }

        initAppHeaderInner();
        initViewContainer();
        initAppFooterInner();
        initAppFooterOuter();

        // Add 'em all together.
        FlexLayout column = UIUtils.createColumn(appHeaderInner, viewContainer, appFooterInner);
        column.setClassName(CLASS_NAME + "__column");

        FlexLayout row = new FlexLayout(naviDrawer, column);
        row.setClassName(CLASS_NAME + "__row");

        add(appHeaderOuter, row, appFooterOuter);
    }

    private void initAppHeaderOuter() {
        appHeaderOuter = new AppHeaderOuter();
    }

    private void setAppHeaderOuter(Component component) {
        appHeaderOuter.removeAll();
        appHeaderOuter.add(component);
    }

    private void initAppHeaderInner() {
        appHeaderInner = new AppHeaderInner();
    }

    private void setAppHeaderInner(Component component) {
        appHeaderInner.removeAll();
        appHeaderInner.add(component);
    }

    private void initNaviLinkDrawer() {
        naviDrawer = new NaviLinkDrawer();
    }

    private void initNaviTabDrawer() {
        naviDrawer = new NaviTabDrawer();
    }

    private void initViewContainer() {
        viewContainer = new FlexLayout();
        viewContainer.setClassName(CLASS_NAME + "__view-container");
    }

    private void initAppFooterOuter() {
        appFooterOuter = new AppFooterOuter();
    }

    private void setAppFooterOuter(Component component) {
        appFooterOuter.removeAll();
        appFooterOuter.add(component);
    }

    private void initAppFooterInner() {
        appFooterInner = new AppFooterInner();
    }

    private void setAppFooterInner(Component component) {
        appFooterInner.removeAll();
        appFooterInner.add(component);
    }

    @Override
    public void configurePage(InitialPageSettings settings) {
        settings.addMetaTag("apple-mobile-web-app-capable", "yes");
        settings.addMetaTag("apple-mobile-web-app-status-bar-style", "black");
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        this.viewContainer.getElement().appendChild(content.getElement());
    }
}
