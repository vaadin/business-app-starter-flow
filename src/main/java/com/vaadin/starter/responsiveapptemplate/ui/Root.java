package com.vaadin.starter.responsiveapptemplate.ui;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.ErrorHandler;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.bar.TabBar;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.drawer.NaviDrawer;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.drawer.NaviLinkDrawer;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.drawer.NaviLinkItem;
import com.vaadin.starter.responsiveapptemplate.ui.layout.FlexBoxLayout;
import com.vaadin.starter.responsiveapptemplate.ui.layout.FlexDirection;
import com.vaadin.starter.responsiveapptemplate.ui.layout.Overflow;
import com.vaadin.starter.responsiveapptemplate.ui.utils.LumoStyles;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;
import com.vaadin.starter.responsiveapptemplate.ui.views.Home;
import com.vaadin.starter.responsiveapptemplate.ui.views.finance.Accounts;
import com.vaadin.starter.responsiveapptemplate.ui.views.finance.Payments;
import com.vaadin.starter.responsiveapptemplate.ui.views.finance.Statistics;
import com.vaadin.starter.responsiveapptemplate.ui.views.personnel.Accountants;
import com.vaadin.starter.responsiveapptemplate.ui.views.personnel.Managers;

@HtmlImport("frontend://styles/shared-styles.html")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class Root extends FlexBoxLayout
        implements RouterLayout, PageConfigurator, AfterNavigationObserver {

    private static final Logger log = LoggerFactory.getLogger(Root.class);
    private static final String CLASS_NAME = "root";

    private Div appHeaderOuter;

    private FlexBoxLayout row;
    private NaviDrawer naviDrawer;
    private FlexBoxLayout column;

    private Div appHeaderInner;
    private FlexBoxLayout viewContainer;
    private Div appFooterInner;

    private Div appFooterOuter;

    private TabBar tabBar;
    private boolean navigationTabs = false;
    private AppBar appBar;

    public Root() {
        VaadinSession.getCurrent()
                .setErrorHandler((ErrorHandler) errorEvent -> {
                    log.error("Uncaught UI exception",
                            errorEvent.getThrowable());
                    Notification.show(
                            "We are sorry, but an internal error occurred");
                });

        addClassName(CLASS_NAME);
        setBackgroundColor(LumoStyles.Color.Contrast._5);
        setFlexDirection(FlexDirection.COLUMN);
        setSizeFull();

        // Initialise the UI building blocks
        initStructure();

        // Populate the navigation drawer
        initNaviItems();

        // Configure the headers and footers (optional)
        initHeadersAndFooters();
    }

    /**
     * Initialise the required components and containers.
     */
    private void initStructure() {
        naviDrawer = new NaviLinkDrawer();

        viewContainer = new FlexBoxLayout();
        viewContainer.addClassName(CLASS_NAME + "__view-container");
        viewContainer.setOverflow(Overflow.HIDDEN);

        column = new FlexBoxLayout(viewContainer);
        column.addClassName(CLASS_NAME + "__column");
        column.setFlexDirection(FlexDirection.COLUMN);
        column.setFlexGrow(1, viewContainer);
        column.setOverflow(Overflow.HIDDEN);

        row = new FlexBoxLayout(naviDrawer, column);
        row.addClassName(CLASS_NAME + "__row");
        row.setFlexGrow(1, column);
        row.setOverflow(Overflow.HIDDEN);
        add(row);
        setFlexGrow(1, row);
    }

    /**
     * Initialise the navigation items.
     */
    private void initNaviItems() {
        naviDrawer.addNaviItem(VaadinIcon.HOME, "Home", Home.class);
        naviDrawer.addNaviItem(VaadinIcon.INSTITUTION, "Accounts",
                Accounts.class);
        naviDrawer.addNaviItem(VaadinIcon.CREDIT_CARD, "Payments",
                Payments.class);
        naviDrawer.addNaviItem(VaadinIcon.CHART, "Statistics",
                Statistics.class);

        NaviLinkItem personnel = naviDrawer.addNaviItem(VaadinIcon.USERS,
                "Personnel", null);
        naviDrawer.addNaviItem(personnel, "Accountants", Accountants.class);
        naviDrawer.addNaviItem(personnel, "Managers", Managers.class);
    }

    /**
     * Configure the app's inner and outer headers and footers.
     */
    private void initHeadersAndFooters() {
        // With tabs:
        // the title, avatar and menu button (small screens) go into the TabBar
        // Without:
        // all of them go into the AppBar

        appBar = new AppBar("");

        if (navigationTabs) {
            appBar.getAvatar().setVisible(false);
            appBar.getMenuNaviIcon().setVisible(false);
            tabBar = new TabBar();
            for (NaviLinkItem item : naviDrawer.getNaviItems()) {
                item.addClickListener(e -> {
                    // Shift-click to add a new tab
                    if (e.getButton() == 0 && e.isShiftKey()) {
                        tabBar.setSelectedTab(tabBar.addClosableNaviTab(
                                item.getText(), item.getNavigationTarget()));
                    }
                });
            }
            setAppHeaderInner(tabBar, appBar);
        } else {
            setAppHeaderInner(appBar);
        }
    }

    private void setAppHeaderOuter(Component... components) {
        if (appHeaderOuter == null) {
            appHeaderOuter = UIUtils
                    .createDiv(Collections.singleton("app-header-outer"));
            getElement().insertChild(0, appHeaderOuter.getElement());
        }

        appHeaderOuter.removeAll();
        appHeaderOuter.add(components);
    }

    private void setAppHeaderInner(Component... components) {
        if (appHeaderInner == null) {
            appHeaderInner = UIUtils
                    .createDiv(Collections.singleton("app-header-inner"));
            column.getElement().insertChild(0, appHeaderInner.getElement());
        }

        appHeaderInner.removeAll();
        appHeaderInner.add(components);
    }

    private void setAppFooterInner(Component... components) {
        if (appFooterInner == null) {
            appFooterInner = UIUtils
                    .createDiv(Collections.singleton("app-footer-inner"));
            column.getElement().insertChild(column.getElement().getChildCount(),
                    appFooterInner.getElement());
        }
        appFooterInner.removeAll();
        appFooterInner.add(components);
    }

    private void setAppFooterOuter(Component... components) {
        if (appFooterOuter == null) {
            appFooterOuter = UIUtils
                    .createDiv(Collections.singleton("app-footer-outer"));
            getElement().insertChild(getElement().getChildCount(),
                    appFooterOuter.getElement());
        }
        appFooterOuter.removeAll();
        appFooterOuter.add(components);
    }

    @Override
    public void configurePage(InitialPageSettings settings) {
        settings.addMetaTag("apple-mobile-web-app-capable", "yes");
        settings.addMetaTag("apple-mobile-web-app-status-bar-style", "black");

        settings.addFavIcon("icon", "frontend/styles/favicons/finance.ico",
                "256x256");
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        this.viewContainer.getElement().appendChild(content.getElement());
    }

    public NaviDrawer getNaviDrawer() {
        return naviDrawer;
    }

    public static Root get() {
        return (Root) UI.getCurrent().getChildren()
                .filter(component -> component.getClass() == Root.class)
                .findFirst().get();
    }

    public AppBar getAppBar() {
        return appBar;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        if (navigationTabs) {
            afterNavigationWithTabs(event);
        } else {
            afterNavigationWithoutTabs(event);
        }

    }

    private void afterNavigationWithTabs(AfterNavigationEvent e) {
        NaviLinkItem active = getActiveItem(e);
        if (active == null) {
            if (tabBar.getTabCount() == 0) {
                tabBar.addClosableNaviTab("", Home.class);
            }
        } else {
            if (tabBar.getTabCount() > 0) {
                tabBar.updateSelectedTab(active.getText(),
                        active.getNavigationTarget());
            } else {
                tabBar.addClosableNaviTab(active.getText(),
                        active.getNavigationTarget());
            }
        }
    }

    private NaviLinkItem getActiveItem(AfterNavigationEvent e) {
        for (NaviLinkItem item : naviDrawer.getNaviItems()) {
            if (item.isHighlighted(e)) {
                return item;
            }
        }

        return null;
    }

    private void afterNavigationWithoutTabs(AfterNavigationEvent e) {
        NaviLinkItem active = getActiveItem(e);
        getAppBar().setTitle(active.getText());
    }

}
