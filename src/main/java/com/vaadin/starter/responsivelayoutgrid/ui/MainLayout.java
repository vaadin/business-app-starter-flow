package com.vaadin.starter.responsivelayoutgrid.ui;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.shared.Registration;
import com.vaadin.starter.responsivelayoutgrid.ui.components.AppBar;
import com.vaadin.starter.responsivelayoutgrid.ui.components.NavigationDrawer;
import com.vaadin.starter.responsivelayoutgrid.ui.components.NavigationItem;
import com.vaadin.starter.responsivelayoutgrid.ui.views.Dashboard;
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

		// Content
		content = new FlexLayout();
		content.addClassName("content");
		add(content);
		setFlexGrow(1, content);

		// Header
		appBar = new AppBar("App Bar");
		appBar.getMenuNavigationIcon().addClickListener(appBarEvent -> navigationDrawer.toggle());
		content.add(appBar);

		// Views
		navigationDrawer.addNavigationItem(VaadinIcon.GRID, "Initial Coin Offerings", ICOMasterView.class);
		navigationDrawer.addNavigationItem(VaadinIcon.USER_CHECK, "ID Verifications", IDVerifications.class);
		NavigationItem dashboard = navigationDrawer.addNavigationItem(VaadinIcon.DASHBOARD, "Dashboard", Dashboard.class);

		// Sub-views
		NavigationItem charts = navigationDrawer.addNavigationItem(dashboard, "Charts");

		NavigationItem pieCharts = navigationDrawer.addNavigationItem(charts, "Pie Charts");
		NavigationItem doughtnut = navigationDrawer.addNavigationItem(pieCharts, "Doughnut");
		NavigationItem spie = navigationDrawer.addNavigationItem(pieCharts, "Spie");

		NavigationItem flowchart = navigationDrawer.addNavigationItem(charts, "Flowchart");
		NavigationItem document = navigationDrawer.addNavigationItem(flowchart, "Document");
		NavigationItem data = navigationDrawer.addNavigationItem(flowchart, "Data");
		NavigationItem system = navigationDrawer.addNavigationItem(flowchart, "System");

		for (NavigationItem item : new NavigationItem[]{charts, pieCharts, doughtnut, spie, flowchart, document, data, system}) {
			item.addClickListener(e -> {
				appBar.addClosableTab(item.getText());
			});
		}
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

		appBar.reset();

		// TODO: Who is responsible for configuring the AppBar based on the selected view?
		if (navigationTarget == ICOMasterView.class || navigationTarget == IDVerifications.class) {
			createTabs();
			createActionItems();

		} else if (navigationTarget == ICODetailsView.class) {
			appBar.setNavigationMode(AppBar.NavigationMode.CONTEXTUAL);
			appBar.setContextualNavigationIcon(VaadinIcon.ARROW_BACKWARD);
			reverseNavigation = appBar.getContextualNavigationIcon().addClickListener(e -> UI.getCurrent().navigate(""));

		} else if (navigationTarget == Dashboard.class) {
			appBar.setTabsVisible(true);
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
			appBar.addTab(tab);
		}
	}
}
