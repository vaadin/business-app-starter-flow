package com.vaadin.starter.responsivelayoutgrid.ui;

import com.vaadin.flow.component.ClickEvent;
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
import com.vaadin.starter.responsivelayoutgrid.ui.views.ICODetailsView;
import com.vaadin.starter.responsivelayoutgrid.ui.views.ICOMasterView;
import com.vaadin.starter.responsivelayoutgrid.ui.views.IDVerifications;

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

        // Content
        content = new FlexLayout();
        content.addClassName("content");
        add(content);
        setFlexGrow(1, content);

        // Header
        appBar = new AppBar("App Bar");
        appBar.getNavigationIcon().addClickListener(appBarEvent -> navigationDrawer.toggle());
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
        // TODO: Who is responsible for configuring the AppBar based on the selected view?

        Class<?> navigationTarget = beforeEnterEvent.getNavigationTarget();

        appBar.resetNavigationIcon();

        // TODO: Certain views sport a back button instead of a menu button. We remove the registration by default when a navigation change happens.
        if (reverseNavigation != null) {
            reverseNavigation.remove();
            reverseNavigation = null;
        }

        appBar.removeAllTabs();
        appBar.setTabsVisible(false);

        appBar.removeAllActionItems();
        appBar.setActionItemsVisible(false);

        if (navigationTarget == ICOMasterView.class) {
            appBar.setTabsVisible(true);
            appBar.addTab(new Tab("Ongoing"));
            appBar.addTab(new Tab("Upcoming"));
            appBar.addTab(new Tab("Closed"));

            appBar.setActionItemsVisible(true);
            appBar.addActionItem(VaadinIcon.SEARCH).addClickListener(e -> appBar.searchModeOn());
            appBar.addActionItem(VaadinIcon.FILTER);
            Button menuButton = appBar.addActionItem(VaadinIcon.ELLIPSIS_DOTS_V);

            ContextMenu contextMenu = new ContextMenu(menuButton);
            contextMenu.setOpenOnClick(true);
            contextMenu.addItem("Share", e -> System.out.println("Testing..."));
            contextMenu.addItem("Download", e -> System.out.println("Testing..."));

        } else if (navigationTarget == IDVerifications.class) {
            appBar.setTabsVisible(true);
            appBar.addTab(new Tab("Pending"));
            appBar.addTab(new Tab("Approved"));
            appBar.addTab(new Tab("Denied"));

            appBar.setActionItemsVisible(true);
            appBar.addActionItem(VaadinIcon.SEARCH).addClickListener(e -> appBar.searchModeOn());
            appBar.addActionItem(VaadinIcon.FILTER);
            Button menuButton = appBar.addActionItem(VaadinIcon.ELLIPSIS_DOTS_V);

            ContextMenu contextMenu = new ContextMenu(menuButton);
            contextMenu.setOpenOnClick(true);
            contextMenu.addItem("Share", e -> System.out.println("Testing..."));
            contextMenu.addItem("Download", e -> System.out.println("Testing..."));

        } else if (navigationTarget == ICODetailsView.class) {
            appBar.setNavigationIcon(VaadinIcon.ARROW_BACKWARD);
            appBar.setNavigationIconVisible(true);

            reverseNavigation = appBar.getNavigationIcon().addClickListener(e -> UI.getCurrent().navigate(""));
        }
    }
}
