package com.vaadin.starter.responsivelayoutgrid.ui;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.shared.Registration;
import com.vaadin.starter.responsivelayoutgrid.ui.components.AppBar;
import com.vaadin.starter.responsivelayoutgrid.ui.components.NavigationDrawer;
import com.vaadin.starter.responsivelayoutgrid.ui.components.NavigationItem;
import com.vaadin.starter.responsivelayoutgrid.ui.views.*;

@HtmlImport("frontend://styles/shared-styles.html")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class MainLayout extends FlexLayout
        implements RouterLayout, PageConfigurator, BeforeEnterObserver {

    private final FlexLayout content;
    private final AppBar appBar;

    Registration reverseNavigation;
    private ContextMenu menu;

    public MainLayout() {
        addClassName("root");

        // Navigation
        NavigationDrawer navigationDrawer = new NavigationDrawer();
        add(navigationDrawer);

        // Views
        navigationDrawer.addNavigationItem(VaadinIcon.GRID, "Initial Coin Offerings", ICOMasterView.class);
        navigationDrawer.addNavigationItem(VaadinIcon.USER_CHECK, "ID Verifications", IDVerifications.class);

        // Sub-views
        NavigationItem dashboard = navigationDrawer.addNavigationItem(VaadinIcon.DASHBOARD, "Dashboard", Dashboard.class);
        NavigationItem v1 = navigationDrawer.addNavigationSubItem(dashboard, "First level", View1.class);

        NavigationItem v2 = navigationDrawer.addNavigationSubItem(v1, "Second level", View2.class);
        navigationDrawer.addNavigationSubItem(v2, "Third level", View3.class);
        navigationDrawer.addNavigationSubItem(v2, "Third level", View4.class);
        navigationDrawer.addNavigationSubItem(v2, "Third level", View5.class);

        NavigationItem v3 = navigationDrawer.addNavigationSubItem(v1, "Second level", View6.class);
        navigationDrawer.addNavigationSubItem(v3, "Third level", View7.class);
        navigationDrawer.addNavigationSubItem(v3, "Third level", View8.class);
        navigationDrawer.addNavigationSubItem(v3, "Third level", View9.class);

        // Content
        content = new FlexLayout();
        content.addClassName("content");
        add(content);
        setFlexGrow(1, content);

        // Header
        appBar = new AppBar("App Bar");
        appBar.getMenuNavigationIcon().addClickListener(appBarEvent -> navigationDrawer.toggle());
        content.add(appBar);

    }

    @Override
    public void configurePage(InitialPageSettings settings) {
        settings.addMetaTag("apple-mobile-web-app-capable", "yes");
        settings.addMetaTag("apple-mobile-web-app-status-bar-style", "black");
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        this.content.getElement().appendChild(content.getElement());
    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Class<?> navigationTarget = beforeEnterEvent.getNavigationTarget();

        // TODO: Certain views sport a back button instead of a menu button. We remove the registration by default when a navigation change happens.
        if (reverseNavigation != null) {
            reverseNavigation.remove();
            reverseNavigation = null;
        }

        appBar.setNavigationMode(AppBar.NavigationMode.MENU);

        appBar.removeAllTabs();
        appBar.setTabsVisible(false);

        appBar.removeAllActionItems();
        appBar.setActionItemsVisible(false);

        // TODO: Who is responsible for configuring the AppBar based on the selected view?
        if (navigationTarget == ICOMasterView.class || navigationTarget == IDVerifications.class) {
            createTabs();
            createActionItems();

        } else if (navigationTarget == ICODetailsView.class) {
            appBar.setNavigationMode(AppBar.NavigationMode.CONTEXTUAL);
            appBar.setContextualNavigationIcon(VaadinIcon.ARROW_BACKWARD);
            reverseNavigation = appBar.getContextualNavigationIcon().addClickListener(e -> UI.getCurrent().navigate(""));
        }
    }

    private void createActionItems() {
        appBar.setActionItemsVisible(true);
        appBar.addActionItem(VaadinIcon.SEARCH).addClickListener(e -> appBar.searchModeOn());
        appBar.addActionItem(VaadinIcon.FILTER);
        createContextMenu(appBar.addActionItem(VaadinIcon.ELLIPSIS_DOTS_V));
    }

    private void createContextMenu(Button button) {
        ContextMenu contextMenu = new ContextMenu(button);
        contextMenu.setOpenOnClick(true);
        contextMenu.addItem("Share", e -> System.out.println("Testing..."));
        contextMenu.addItem("Download", e -> System.out.println("Testing..."));
    }

    private void createTabs() {
        appBar.setTabsVisible(true);
        for (String tab : new String[]{"Ongoing", "Upcoming", "Closed"}) {
            appBar.addTab(new Tab(tab));
        }
    }
}
