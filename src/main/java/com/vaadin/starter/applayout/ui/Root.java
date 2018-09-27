package com.vaadin.starter.applayout.ui;

import com.vaadin.flow.component.ClickNotifier;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.components.*;
import com.vaadin.starter.applayout.ui.utils.NaviDrawerProvider;
import com.vaadin.starter.applayout.ui.utils.UIUtils;
import com.vaadin.starter.applayout.ui.views.Dashboard;
import com.vaadin.starter.applayout.ui.views.Personnel;
import com.vaadin.starter.applayout.ui.views.ReportsView;

@HtmlImport("frontend://styles/shared-styles.html")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class Root extends FlexLayout
        implements RouterLayout, PageConfigurator, BeforeEnterObserver {

    private String CLASS_NAME = "root";

    private AppHeaderOuter appHeaderOuter;
    private FlexLayout row;
    private NaviDrawer naviDrawer;
    private FlexLayout column;
    private AppHeaderInner appHeaderInner;
    private FlexLayout viewContainer;
    private AppFooterInner appFooterInner;
    private AppFooterOuter appFooterOuter;

    public Root() {
        setClassName(CLASS_NAME);

        // Initialise all the building blocks.
        init();

        // App specific stuff goes here.
        naviDrawer.addNaviItem(VaadinIcon.GRID_BIG, "Dashboard", Dashboard.class);
        naviDrawer.addNaviItem(VaadinIcon.FILE_TEXT, "Reports", ReportsView.class);
        naviDrawer.addNaviItem(VaadinIcon.USERS, "Personnel", Personnel.class);
    }

    /**
     * Initialise the required components and containers.
     */
    private void init() {
        naviDrawer = NaviDrawerProvider.getNaviDrawer();

        // TODO: Explore DOM event triggering/listening.
        // naviDrawer.getElement().addEventListener("my-event", e -> naviDrawer.toggle());
        // naviDrawer.getElement().addSynchronizedPropertyEvent("my-event");

        initViewContainer();

        column = UIUtils.createColumn(viewContainer);
        column.setClassName(CLASS_NAME + "__column");

        row = new FlexLayout(naviDrawer, column);
        row.setClassName(CLASS_NAME + "__row");
        add(row);

        configHeadersAndFooters();
    }

    /**
     * Configure the app's inner and outer headers and footers.
     */
    private void configHeadersAndFooters() {
        // setAppHeaderOuter(new Label("Outer header"));
        // setAppHeaderInner(new Label("Inner header"));
        // setAppFooterOuter(new Label("Outer footer"));
        // setAppFooterInner(new Label("Inner footer"));

        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.TABS)) {
            NaviTabs tabs = new NaviTabs();
            setAppHeaderInner(tabs);

            for (NaviItem item : naviDrawer.getNaviItems()) {
                ((ClickNotifier<Div>) item).addClickListener(event -> {
                    int tabCount = tabs.getTabCount();

                    // Shift-click to add a new tab.
                    if (event.getButton() == 0 && event.isShiftKey()) {
                        Tab tab = tabs.addClosableNaviTab(item.getText(), item.getNavigationTarget());
                        tabs.setSelectedTab(tab);
                    }

                    // Update the current tab, or create the first one.
                    else if (event.getButton() == 0) {
                        if (tabs.getTabCount() > 0) {
                            tabs.updateSelectedTab(item.getText(), item.getNavigationTarget());
                        } else {
                            Tab tab = tabs.addClosableNaviTab(item.getText(), item.getNavigationTarget());
                            tabs.setSelectedTab(tab);
                        }
                    }

                    // A selection change event isn't triggered when the first tab is added.
                    if (tabCount == 0) {
                        tabs.setSelectedTab(tabs.getSelectedTab());
                    }
                });
            }
        }
    }

    private void setAppHeaderOuter(Component component) {
        if (appHeaderOuter == null) {
            appHeaderOuter = new AppHeaderOuter();
            getElement().insertChild(0, appHeaderOuter.getElement());
        }

        appHeaderOuter.removeAll();
        appHeaderOuter.add(component);
    }

    private void setAppHeaderInner(Component component) {
        if (appHeaderInner == null) {
            appHeaderInner = new AppHeaderInner();
            column.getElement().insertChild(0, appHeaderInner.getElement());
        }

        appHeaderInner.removeAll();
        appHeaderInner.add(component);
    }

    private void initViewContainer() {
        viewContainer = new FlexLayout();
        viewContainer.setClassName(CLASS_NAME + "__view-container");
    }

    private void setAppFooterInner(Component component) {
        if (appFooterInner == null) {
            appFooterInner = new AppFooterInner();
            column.getElement().insertChild(column.getElement().getChildCount(), appFooterInner.getElement());
        }
        appFooterInner.removeAll();
        appFooterInner.add(component);
    }

    private void setAppFooterOuter(Component component) {
        if (appFooterOuter == null) {
            appFooterOuter = new AppFooterOuter();
            getElement().insertChild(getElement().getChildCount(), appFooterOuter.getElement());
        }
        appFooterOuter.removeAll();
        appFooterOuter.add(component);
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

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // Customise the app's headers and footers when changing views.
    }
}