package com.vaadin.starter.applayout.ui;

import com.vaadin.flow.component.ClickNotifier;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.shared.Registration;
import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.components.*;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.views.*;

@HtmlImport("frontend://styles/shared-styles.html")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class MainLayout extends FlexLayout
        implements RouterLayout, PageConfigurator, BeforeEnterObserver {

    private FlexLayout appHeaderOuter;
    private final FlexLayout appFooterOuter;

    private final FlexLayout wrapper;
    private final FlexLayout innerWrapper;
    private NavigationDrawer navigationDrawer;
    private final FlexLayout appHeaderInner;
    private final FlexLayout contentWrapper;
    private final FlexLayout content;
    //private final FlexLayout contentHeader;
    private final FlexLayout contentFooter;
    private final FlexLayout appFooterInner;


    private final AppBar appBar;

    private Registration reverseNavigation;
    private ContextMenu menu;

    public MainLayout() {
        addClassName("root");
        getElement().getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);

        Label testi = new Label("testi");
        setContentAppHeaderOuter(testi);

        wrapper = new FlexLayout();
        wrapper.addClassName("wrapper");
        wrapper.getElement().getStyle().set(CSSProperties.Flex.PROPERTY, "1");
        add(wrapper);

        // Navigation
        if (UIConfig.getNavigationMode().equals(UIConfig.NavigationMode.LINKS)) {
            initNavigationLinkDrawer();
        } else {
            initNavigationTabDrawer();
        }

        innerWrapper = new FlexLayout();
        innerWrapper.addClassName("inner-wrapper");
        innerWrapper.getElement().getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);
        innerWrapper.getElement().getStyle().set(CSSProperties.Flex.PROPERTY, "1");
        wrapper.add(innerWrapper);

        appHeaderInner = new FlexLayout();
        appHeaderInner.addClassName("app-header-inner");
        innerWrapper.add(appHeaderInner);

        // Header
        appBar = new AppBar("App Bar");
        appBar.getMenuNavigationIcon().addClickListener(appBarEvent -> navigationDrawer.toggle());
        appBar.getElement().getStyle().set(CSSProperties.Flex.PROPERTY, "1");
        appHeaderInner.add(appBar);

        if (UIConfig.getNavigationMode().equals(UIConfig.NavigationMode.TABS)) {
            createActionItems();
            appBar.setAddTabButtonVisible(true);
        }

        contentWrapper = new FlexLayout();
        contentWrapper.getElement().getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);
        contentWrapper.getElement().getStyle().set(CSSProperties.Flex.PROPERTY, "1");
        contentWrapper.addClassName("content-wrapper");
        innerWrapper.add(contentWrapper);

        // Content
        content = new FlexLayout();
        content.addClassName("content");
        content.getElement().getStyle().set(CSSProperties.Flex.PROPERTY, "1");

        FlexLayout contentHeader = new FlexLayout();
        contentHeader.addClassName("content-header");
        addDemoContent(contentHeader);
        contentWrapper.add(contentHeader);

        contentWrapper.add(content);

        contentFooter = new FlexLayout();
        contentFooter.addClassName("content-footer");
        addDemoContent(contentFooter);
        contentWrapper.add(contentFooter);

        appFooterInner = new FlexLayout();
        appFooterInner.addClassName("app-footer-inner");
        addDemoContent(appFooterInner);
        innerWrapper.add(appFooterInner);

        appFooterOuter = new FlexLayout();
        appFooterOuter.addClassName("app-footer-outer");
        addDemoContent(appFooterOuter);
        add(appFooterOuter);
    }

    //App Header Outer section
    private void initAppHeaderOuter() {
        appHeaderOuter = new FlexLayout();
        appHeaderOuter.addClassName("app-header-outer");
        add(appHeaderOuter);
    }

    private void setContentAppHeaderOuter(HasElement element) {
        if (appHeaderOuter == null) {
            initAppHeaderOuter();
        }
        this.getElement().appendChild(element.getElement());
    }

    //Demo content generator
    private void addDemoContent(FlexLayout target) {
        Label demo = new Label(target.getClassName().toString());
        demo.getElement().getStyle().set(CSSProperties.BackgroundColor.PROPERTY, "red");
        target.add(demo);
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
        // When using the navigation drawer with link items.
        if (UIConfig.getNavigationMode().equals(UIConfig.NavigationMode.LINKS)) {

            appBar.reset();
            Class<?> navigationTarget = beforeEnterEvent.getNavigationTarget();

            // TODO: Certain views sport a back button instead of a menu button. We remove the registration by default when a navigation change happens.
            if (reverseNavigation != null) {
                reverseNavigation.remove();
                reverseNavigation = null;
            }

            // TODO: Who is responsible for configuring the AppBar based on the selected view?
            if (navigationTarget == ReportsView.class || navigationTarget == IDVerifications.class) {
                createTabs();
                createActionItems();

            } else if (navigationTarget == ReportDetailsView.class) {
                appBar.setNavigationMode(AppBar.NavigationMode.CONTEXTUAL);
                appBar.setContextualNavigationIcon(VaadinIcon.ARROW_BACKWARD);
                reverseNavigation = appBar.getContextualNavigationIcon().addClickListener(e -> UI.getCurrent().navigate("reports"));
            }
        }
    }

    private void createActionItems() {
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
        for (String tab : new String[]{"Ongoing", "Upcoming", "Closed"}) {
            appBar.addTab(tab, Default.class);
        }
    }

    private void initNavigationLinkDrawer() {
        NavigationLinkDrawer navigationDrawer = new NavigationLinkDrawer();
        wrapper.add(navigationDrawer);

        navigationDrawer.addNavigationItem(VaadinIcon.GRID_BIG, "Dashboard", Dashboard.class);
        navigationDrawer.addNavigationItem(VaadinIcon.FILE_TEXT, "Reports", ReportsView.class);

        NavigationItem workflows = navigationDrawer.addNavigationItem(VaadinIcon.SITEMAP, "Workflows", View1.class);
        NavigationItem teams = navigationDrawer.addNavigationItem(workflows, "Teams", View2.class);
        navigationDrawer.addNavigationItem(teams, "Payments team", View3.class);
        navigationDrawer.addNavigationItem(teams, "Reporting team", View4.class);

        NavigationItem flowchart = navigationDrawer.addNavigationItem(workflows, "Data exchange", View5.class);
        navigationDrawer.addNavigationItem(flowchart, "Document", View6.class);
        navigationDrawer.addNavigationItem(flowchart, "Data", View7.class);
        navigationDrawer.addNavigationItem(flowchart, "System", View8.class);

        navigationDrawer.addNavigationItem(VaadinIcon.USER_CHECK, "ID Verifications", IDVerifications.class);

        this.navigationDrawer = navigationDrawer;
    }

    private void initNavigationTabDrawer() {
        NavigationTabDrawer navigationDrawer = new NavigationTabDrawer();
        wrapper.add(navigationDrawer);

        navigationDrawer.addNavigationItem(VaadinIcon.GRID_BIG, "Dashboard", Dashboard.class);
        navigationDrawer.addNavigationItem(VaadinIcon.FILE_TEXT, "Reports", ReportsView.class);

        NavigationItem workflows = navigationDrawer.addNavigationItem(VaadinIcon.SITEMAP, "Workflows", View1.class);
        NavigationItem teams = navigationDrawer.addNavigationItem(workflows, "Teams", View2.class);
        navigationDrawer.addNavigationItem(teams, "Payments team", View3.class);
        navigationDrawer.addNavigationItem(teams, "Reporting team", View4.class);

        NavigationItem flowchart = navigationDrawer.addNavigationItem(workflows, "Data exchange", View5.class);
        navigationDrawer.addNavigationItem(flowchart, "Document", View6.class);
        navigationDrawer.addNavigationItem(flowchart, "Data", View7.class);
        navigationDrawer.addNavigationItem(flowchart, "System", View8.class);

        navigationDrawer.addNavigationItem(VaadinIcon.USER_CHECK, "ID Verifications", IDVerifications.class);

        // Navigation listeners
        for (NavigationItem item : navigationDrawer.getNavigationItems()) {
            ((ClickNotifier<Div>) item).addClickListener(event -> {

                // BUG: A selection change event isn't triggered when the first tab is added.
                boolean firstTab = appBar.getTabCount() == 0;

                // Shift-click to add a new tab.
                if (event.getButton() == 0 && event.isShiftKey()) {
                    appBar.setSelectedTab(appBar.addClosableTab(item.getText(), item.getNavigationTarget()));
                }

                // Update the current tab, or create the first one.
                else if (event.getButton() == 0) {
                    if (appBar.getTabCount() > 0) {
                        appBar.updateSelectedTab(item.getText(), item.getNavigationTarget());
                    } else {
                        appBar.setSelectedTab(appBar.addClosableTab(item.getText(), item.getNavigationTarget()));
                    }
                }

                // Fix for the bug mentioned above.
                if (firstTab) {
                    appBar.navigateToSelectedTab();
                }
            });
        }

        this.navigationDrawer = navigationDrawer;
    }
}
