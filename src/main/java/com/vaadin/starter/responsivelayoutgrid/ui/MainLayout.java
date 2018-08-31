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
import com.vaadin.starter.responsivelayoutgrid.backend.UIConfig;
import com.vaadin.starter.responsivelayoutgrid.ui.components.*;
import com.vaadin.starter.responsivelayoutgrid.ui.views.*;

@HtmlImport("frontend://styles/shared-styles.html")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class MainLayout extends FlexLayout
		implements RouterLayout, PageConfigurator, BeforeEnterObserver {

	private NavigationDrawer navigationDrawer;

	private final FlexLayout content;
	private final AppBar appBar;

	private Registration reverseNavigation;
	private ContextMenu menu;

	public MainLayout() {
		addClassName("root");

		// Navigation
		if (UIConfig.getNavigationMode().equals(UIConfig.NavigationMode.LINKS)) {
			initNavigationLinkDrawer();
		} else {
			initNavigationTabDrawer();
		}

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
		if (UIConfig.getNavigationMode().equals(UIConfig.NavigationMode.LINKS)) {

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
			appBar.addTab(tab, Default.class);
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
		navigationDrawer.addNavigationItem(VaadinIcon.GRID_BIG, "Dashboard", Dashboard.class);

		NavigationItem charts = navigationDrawer.addNavigationItem(VaadinIcon.CHART, "Charts", View1.class);

		NavigationItem pieCharts = navigationDrawer.addNavigationItem(charts, "Pie Charts", View2.class);
		navigationDrawer.addNavigationItem(pieCharts, "Doughnut", View3.class);
		navigationDrawer.addNavigationItem(pieCharts, "Spie", View4.class);

		NavigationItem flowchart = navigationDrawer.addNavigationItem(charts, "Flowchart", View5.class);
		navigationDrawer.addNavigationItem(flowchart, "Document", View6.class);
		navigationDrawer.addNavigationItem(flowchart, "Data", View7.class);
		navigationDrawer.addNavigationItem(flowchart, "System", View8.class);

		// Workflows
		navigationDrawer.addNavigationItem(VaadinIcon.SITEMAP, "Workflows", View9.class);

		// Open in current or new tab
		for (NavigationItem item : navigationDrawer.getNavigationItems()) {
			((ClickNotifier<Div>) item).addClickListener(event -> {

				// New tab
				if (event.getButton() == 0 && event.isShiftKey()) {
					appBar.setSelectedTab(appBar.addClosableTab(item.getText(), item.getNavigationTarget()));
				}

				// Current or first tab
				else if (event.getButton() == 0) {
					if (appBar.getTabCount() > 0) {
						appBar.updateSelectedTab(item.getText(), item.getNavigationTarget());
					} else {
						appBar.setSelectedTab(appBar.addClosableTab(item.getText(), item.getNavigationTarget()));
					}
				}
			});
		}

		this.navigationDrawer = navigationDrawer;
	}
}
