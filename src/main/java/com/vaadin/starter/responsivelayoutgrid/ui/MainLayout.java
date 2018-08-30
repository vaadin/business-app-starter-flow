package com.vaadin.starter.responsivelayoutgrid.ui;

import com.vaadin.flow.component.ClickNotifier;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.shared.Registration;
import com.vaadin.starter.responsivelayoutgrid.ui.components.*;
import com.vaadin.starter.responsivelayoutgrid.ui.views.*;

@HtmlImport("frontend://styles/shared-styles.html")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class MainLayout extends FlexLayout
		implements RouterLayout, PageConfigurator, BeforeEnterObserver {

	private final FlexLayout content;
	private final AppBar appBar;

	Registration reverseNavigation;
	private ContextMenu menu;
	private NavigationDrawer navigationDrawer;

	public MainLayout() {
		addClassName("root");

		// Navigation
		// initNavigationLinkDrawer();
		initNavigationTabDrawer();

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

		// When using the navigation drawer with link items.
		if (navigationDrawer instanceof NavigationLinkDrawer) {

			appBar.reset();
			Class<?> navigationTarget = beforeEnterEvent.getNavigationTarget();

			// TODO: Certain views sport a back button instead of a menu button. We remove the registration by default when a navigation change happens.
			if (reverseNavigation != null) {
				reverseNavigation.remove();
				reverseNavigation = null;
			}

			// TODO: Who is responsible for configuring the AppBar based on the selected view?
			if (navigationTarget == ICOMasterView.class || navigationTarget == IDVerifications.class) {
				createTabs();
				createActionItems();

			} else if (navigationTarget == ICODetailsView.class) {
				appBar.setNavigationMode(AppBar.NavigationMode.CONTEXTUAL);
				appBar.setContextualNavigationIcon(VaadinIcon.ARROW_BACKWARD);
				reverseNavigation = appBar.getContextualNavigationIcon().addClickListener(e -> UI.getCurrent().navigate("initial-coin-offerings"));
			}

			// When using the navigation drawer with tab items.
		} else {
			appBar.setAddTabButtonVisible(true);
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
			appBar.addTab(tab);
		}
	}

	private void initNavigationLinkDrawer() {
		NavigationLinkDrawer navigationDrawer = new NavigationLinkDrawer();
		add(navigationDrawer);

		navigationDrawer.addNavigationItem(VaadinIcon.GRID, "Initial Coin Offerings", ICOMasterView.class);
		navigationDrawer.addNavigationItem(VaadinIcon.USER_CHECK, "ID Verifications", IDVerifications.class);

		NavigationItem view1 = navigationDrawer.addNavigationItem(VaadinIcon.RANDOM, "View 1", View1.class);
		navigationDrawer.addNavigationItem(view1, "View 2", View2.class);

		NavigationItem view3 = navigationDrawer.addNavigationItem(view1, "View 3", View3.class);
		navigationDrawer.addNavigationItem(view3, "View 4", View4.class);
		navigationDrawer.addNavigationItem(view3, "View 5", View5.class);

		this.navigationDrawer = navigationDrawer;
	}

	private void initNavigationTabDrawer() {
		NavigationTabDrawer navigationDrawer = new NavigationTabDrawer();
		add(navigationDrawer);

		// Dashboard
		navigationDrawer.addNavigationItem(VaadinIcon.GRID_BIG, "Dashboard");

		NavigationItem charts = navigationDrawer.addNavigationItem(VaadinIcon.CHART, "Charts");

		NavigationItem pieCharts = navigationDrawer.addNavigationItem(charts, "Pie Charts");
		navigationDrawer.addNavigationItem(pieCharts, "Doughnut");
		navigationDrawer.addNavigationItem(pieCharts, "Spie");

		NavigationItem flowchart = navigationDrawer.addNavigationItem(charts, "Flowchart");
		navigationDrawer.addNavigationItem(flowchart, "Document");
		navigationDrawer.addNavigationItem(flowchart, "Data");
		navigationDrawer.addNavigationItem(flowchart, "System");

		// Workflows
		navigationDrawer.addNavigationItem(VaadinIcon.SITEMAP, "Workflows");

		// Open in current or new tab
		for (NavigationItem item : navigationDrawer.getNavigationItems()) {
			((ClickNotifier<Div>) item).addClickListener(event -> {

				// New tab
				if (event.getButton() == 0 && event.isShiftKey()) {
					appBar.setSelectedTab(appBar.addTab(item.getText()));
				}

				// Current tab
				else if (event.getButton() == 0) {
					if (appBar.getTabCount() > 0) {
						appBar.getTabs().getSelectedTab().setLabel(item.getText());
					} else {
						appBar.setSelectedTab(appBar.addTab(item.getText()));
					}
				}
			});
		}

		this.navigationDrawer = navigationDrawer;
	}
}
