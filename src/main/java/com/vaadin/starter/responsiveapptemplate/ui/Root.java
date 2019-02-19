package com.vaadin.starter.responsiveapptemplate.ui;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ClickNotifier;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.ErrorHandler;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.starter.responsiveapptemplate.backend.UIConfig;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.bar.TabBar;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.drawer.NaviDrawer;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.drawer.NaviItem;
import com.vaadin.starter.responsiveapptemplate.ui.layout.FlexBoxLayout;
import com.vaadin.starter.responsiveapptemplate.ui.layout.FlexDirection;
import com.vaadin.starter.responsiveapptemplate.ui.layout.Overflow;
import com.vaadin.starter.responsiveapptemplate.ui.utils.LumoStyles;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;
import com.vaadin.starter.responsiveapptemplate.ui.views.Default;
import com.vaadin.starter.responsiveapptemplate.ui.views.demo.*;
import com.vaadin.starter.responsiveapptemplate.ui.views.finance.Accounts;
import com.vaadin.starter.responsiveapptemplate.ui.views.finance.Payments;
import com.vaadin.starter.responsiveapptemplate.ui.views.finance.Statistics;
import com.vaadin.starter.responsiveapptemplate.ui.views.inventory.Catalogue;
import com.vaadin.starter.responsiveapptemplate.ui.views.inventory.CustomerOrders;
import com.vaadin.starter.responsiveapptemplate.ui.views.inventory.Invoices;
import com.vaadin.starter.responsiveapptemplate.ui.views.inventory.Overview;
import com.vaadin.starter.responsiveapptemplate.ui.views.personnel.Accountants;
import com.vaadin.starter.responsiveapptemplate.ui.views.personnel.Managers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

@HtmlImport("frontend://styles/shared-styles.html")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class Root extends FlexBoxLayout
		implements RouterLayout, PageConfigurator, BeforeEnterObserver {

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

	public Root() {
		VaadinSession.getCurrent().setErrorHandler((ErrorHandler) errorEvent -> {
			log.error("Uncaught UI exception", errorEvent.getThrowable());
			Notification.show("We are sorry, but an internal error occurred");
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

		if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.TABS)) {
			UIUtils.showNotification("Shift-click opens new tab");
		}
	}

	/**
	 * Initialise the required components and containers.
	 */
	private void initStructure() {
		naviDrawer = AppTemplateUI.getNaviDrawer();

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
		UIConfig.Showcase showcase = UIConfig.getShowcase();

		// Initialise the navigation items based on the showcase
		if (showcase.equals(UIConfig.Showcase.DEMO)) {
			naviDrawer.addNaviItem(VaadinIcon.GRID_BIG, "Dashboard", Dashboard.class);
			naviDrawer.addNaviItem(VaadinIcon.FILE_TEXT, "Reports", Reports.class);

			NaviItem personnel = naviDrawer.addNaviItem(VaadinIcon.USERS, "Personnel", Personnel.class);
			naviDrawer.addNaviItem(personnel, "Vertical Split", VerticalSplitView.class);
			naviDrawer.addNaviItem(personnel, "Horizontal Split", HorizontalSplitView.class);

			naviDrawer.addNaviItem(VaadinIcon.FILTER, "Filter List", FilterList.class);

		} else if (showcase.equals(UIConfig.Showcase.FINANCE)) {
			naviDrawer.addNaviItem(VaadinIcon.HOME, "Home", Default.class);
			naviDrawer.addNaviItem(VaadinIcon.INSTITUTION, "Accounts", Accounts.class);
			naviDrawer.addNaviItem(VaadinIcon.CREDIT_CARD, "Payments", Payments.class);
			naviDrawer.addNaviItem(VaadinIcon.CHART, "Statistics", Statistics.class);

			NaviItem personnel = naviDrawer.addNaviItem(VaadinIcon.USERS, "Personnel", null);
			naviDrawer.addNaviItem(personnel, "Accountants", Accountants.class);
			naviDrawer.addNaviItem(personnel, "Managers", Managers.class);

		} else if (showcase.equals(UIConfig.Showcase.INVENTORY)) {
			naviDrawer.addNaviItem(VaadinIcon.HOME, "Home", Default.class);
			naviDrawer.addNaviItem(VaadinIcon.PACKAGE, "Customer Orders", CustomerOrders.class);
			naviDrawer.addNaviItem(VaadinIcon.INVOICE, "Invoices", Invoices.class);
			naviDrawer.addNaviItem(VaadinIcon.PIE_BAR_CHART, "Overview", Overview.class);
			naviDrawer.addNaviItem(VaadinIcon.STOCK, "Catalogue", Catalogue.class);

			NaviItem personnel = naviDrawer.addNaviItem(VaadinIcon.USERS, "Personnel", null);
			naviDrawer.addNaviItem(personnel, "Accountants", Accountants.class);
			naviDrawer.addNaviItem(personnel, "Managers", Managers.class);
		}
	}

	/**
	 * Configure the app's inner and outer headers and footers.
	 */
	private void initHeadersAndFooters() {
		// setAppHeaderOuter(new Label("Inner header"));
		// setAppHeaderInner(new Label("Inner header"));
		// setAppFooterOuter(new Label("Outer footer"));
		// setAppFooterInner(new Label("Inner footer"));

		if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.TABS)) {
			tabBar = new TabBar();
			setAppHeaderInner(tabBar);

			for (NaviItem item : naviDrawer.getNaviItems()) {
				((ClickNotifier<Div>) item).addClickListener(event -> naviItemClicked(item, event));
			}
		}
	}

	/**
	 * Handles the click event for navigation items when in NaviMode.TABS.
	 */
	private void naviItemClicked(NaviItem item, ClickEvent<Div> event) {
		// Shift-click to add a new tab
		if (event.getButton() == 0 && event.isShiftKey()) {
			tabBar.setSelectedTab(tabBar.addClosableNaviTab(item.getText(), item.getNavigationTarget()));
		}

		// Update the current tab, or create the first one
		else if (event.getButton() == 0) {
			if (tabBar.getTabCount() > 0) {
				tabBar.updateSelectedTab(item.getText(), item.getNavigationTarget());
			} else {
				tabBar.addClosableNaviTab(item.getText(), item.getNavigationTarget());
			}
		}
	}

	private void setAppHeaderOuter(Component... components) {
		if (appHeaderOuter == null) {
			appHeaderOuter = UIUtils.createDiv(Collections.singleton("app-header-outer"));
			getElement().insertChild(0, appHeaderOuter.getElement());
		}

		appHeaderOuter.removeAll();
		appHeaderOuter.add(components);
	}

	private void setAppHeaderInner(Component... components) {
		if (appHeaderInner == null) {
			appHeaderInner = UIUtils.createDiv(Collections.singleton("app-header-inner"));
			column.getElement().insertChild(0, appHeaderInner.getElement());
		}

		appHeaderInner.removeAll();
		appHeaderInner.add(components);
	}

	private void setAppFooterInner(Component... components) {
		if (appFooterInner == null) {
			appFooterInner = UIUtils.createDiv(Collections.singleton("app-footer-inner"));
			column.getElement().insertChild(column.getElement().getChildCount(), appFooterInner.getElement());
		}
		appFooterInner.removeAll();
		appFooterInner.add(components);
	}

	private void setAppFooterOuter(Component... components) {
		if (appFooterOuter == null) {
			appFooterOuter = UIUtils.createDiv(Collections.singleton("app-footer-outer"));
			getElement().insertChild(getElement().getChildCount(), appFooterOuter.getElement());
		}
		appFooterOuter.removeAll();
		appFooterOuter.add(components);
	}

	@Override
	public void configurePage(InitialPageSettings settings) {
		settings.addMetaTag("apple-mobile-web-app-capable", "yes");
		settings.addMetaTag("apple-mobile-web-app-status-bar-style", "black");

		if (UIConfig.getShowcase().equals(UIConfig.Showcase.INVENTORY)) {
			settings.addFavIcon("icon", "frontend/styles/favicons/inventory.ico", "256x256");
		} else if (UIConfig.getShowcase().equals(UIConfig.Showcase.FINANCE)) {
			settings.addFavIcon("icon", "frontend/styles/favicons/finance.ico", "256x256");
		} else {
			settings.addFavIcon("icon", "frontend/styles/favicons/proto-x.ico", "256x256");
		}
	}

	@Override
	public void showRouterLayoutContent(HasElement content) {
		this.viewContainer.getElement().appendChild(content.getElement());
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		// Customise the app's headers and footers when changing views.
	}

	private void initExampleNavigation() {
		NaviItem ordering = naviDrawer.addNaviItem(VaadinIcon.ABACUS, "Ordering", null);
		{
			NaviItem orderEntry = naviDrawer.addNaviItem(ordering, "Order Entry", null);
			{
				naviDrawer.addNaviItem(orderEntry, "New Order", null);
				naviDrawer.addNaviItem(orderEntry, "Remote CustomerOrders", null);
				naviDrawer.addNaviItem(orderEntry, "Incomplete CustomerOrders", null);
			}
			NaviItem setup = naviDrawer.addNaviItem(ordering, "Setup", null);
			{
				naviDrawer.addNaviItem(setup, "Clients", null);
				naviDrawer.addNaviItem(setup, "Client Groups", null);
				naviDrawer.addNaviItem(setup, "Providers", null);
				naviDrawer.addNaviItem(setup, "Lab Tests", null);
				naviDrawer.addNaviItem(setup, "Lab Test Panels", null);
				naviDrawer.addNaviItem(setup, "Patients", null);
			}
			NaviItem labeling = naviDrawer.addNaviItem(ordering, "Labeling", null);
			{
				naviDrawer.addNaviItem(labeling, "Order Labels", null);
				naviDrawer.addNaviItem(labeling, "Current Unprinted Batch", null);
				naviDrawer.addNaviItem(labeling, "Label Batches", null);
			}
			NaviItem dataCleanup = naviDrawer.addNaviItem(ordering, "Data Cleanup", null);
			{
				naviDrawer.addNaviItem(dataCleanup, "Missing Info", null);
			}
			NaviItem reports = naviDrawer.addNaviItem(ordering, "Reports", null);
			{
				naviDrawer.addNaviItem(reports, "Order Search", null);
				naviDrawer.addNaviItem(reports, "Order Stats", null);
				naviDrawer.addNaviItem(reports, "Workflow Action Queue", null);
			}
			NaviItem interfaces = naviDrawer.addNaviItem(ordering, "Interfaces", null);
			{
				naviDrawer.addNaviItem(interfaces, "EDI Stats", null);
				naviDrawer.addNaviItem(interfaces, "EDI Log", null);
			}
			NaviItem sendouts = naviDrawer.addNaviItem(ordering, "Sendouts", null);
			{
				naviDrawer.addNaviItem(sendouts, "Sendout Queue", null);
				naviDrawer.addNaviItem(sendouts, "Manifest Queue", null);
				naviDrawer.addNaviItem(sendouts, "Sendout Receive Queue", null);
				naviDrawer.addNaviItem(sendouts, "Search Sendouts", null);
			}
		}
		NaviItem anatomicPathology = naviDrawer.addNaviItem(VaadinIcon.ABACUS, "Anatomic Pathology", null);
		{
			naviDrawer.addNaviItem(anatomicPathology, "Pathologist Queue", null);
			naviDrawer.addNaviItem(anatomicPathology, "Case Distribution", null);

			NaviItem gynCytology = naviDrawer.addNaviItem(anatomicPathology, "GYN Cytology", null);
			{
				NaviItem specimenPlacement = naviDrawer.addNaviItem(gynCytology, "Specimen Placement", null);
				{
					naviDrawer.addNaviItem(specimenPlacement, "Rack Placement", null);
				}
				naviDrawer.addNaviItem(gynCytology, "Gyn Batch Log", null);
				naviDrawer.addNaviItem(gynCytology, "Cytotech", null);
				naviDrawer.addNaviItem(gynCytology, "Quality Control", null);

				NaviItem apReports = naviDrawer.addNaviItem(gynCytology, "AP Reports", null);
				{
					naviDrawer.addNaviItem(apReports, "CT [Daily Log]", null);
					naviDrawer.addNaviItem(apReports, "QC [Daily Log]", null);
					naviDrawer.addNaviItem(apReports, "Case Search", null);
					naviDrawer.addNaviItem(apReports, "Case Stats", null);
					naviDrawer.addNaviItem(apReports, "Consolidated Letter", null);
					naviDrawer.addNaviItem(apReports, "Tickler Report", null);
					naviDrawer.addNaviItem(apReports, "Followup Letter", null);
					naviDrawer.addNaviItem(apReports, "Abnormal Followup", null);
				}
			}
			NaviItem ngynCytology = naviDrawer.addNaviItem(anatomicPathology, "Surgical/NGYN Cytology", null);
			{
				naviDrawer.addNaviItem(ngynCytology, "Grossing Queue", null);
				naviDrawer.addNaviItem(ngynCytology, "Transcription Cases", null);
				naviDrawer.addNaviItem(ngynCytology, "Screening Queue", null);

				NaviItem historology = naviDrawer.addNaviItem(ngynCytology, "Historology", null);
				{
					naviDrawer.addNaviItem(historology, "Block Queue", null);
					naviDrawer.addNaviItem(historology, "Slide Queue", null);
					naviDrawer.addNaviItem(historology, "Histo Cases", null);
					naviDrawer.addNaviItem(historology, "Block Log", null);
					naviDrawer.addNaviItem(historology, "Slide Log", null);
					naviDrawer.addNaviItem(historology, "Block Stats", null);
					naviDrawer.addNaviItem(historology, "Slide Stats", null);
					naviDrawer.addNaviItem(historology, "Correlation Queue", null);
				}
				NaviItem microscopicReports = naviDrawer.addNaviItem(ngynCytology, "Microscopic Reports", null);
				{
					naviDrawer.addNaviItem(microscopicReports, "Case Log Report", null);
					naviDrawer.addNaviItem(microscopicReports, "Process Only Report", null);
					naviDrawer.addNaviItem(microscopicReports, "IntraOp Stats", null);
					naviDrawer.addNaviItem(microscopicReports, "Microscopic Case Stats", null);
					naviDrawer.addNaviItem(microscopicReports, "Microscopic Case Search", null);
					naviDrawer.addNaviItem(microscopicReports, "Consultation Stats", null);
					naviDrawer.addNaviItem(microscopicReports, "Consultation Search", null);
				}
			}
			NaviItem crossTypeReports = naviDrawer.addNaviItem(anatomicPathology, "Cross-type Reports", null);
			{
				NaviItem correlation = naviDrawer.addNaviItem(crossTypeReports, "Correlation", null);
				{
					naviDrawer.addNaviItem(correlation, "Correlation [Date]", null);
					naviDrawer.addNaviItem(correlation, "Correlation Stats", null);
					naviDrawer.addNaviItem(correlation, "Correlation Log", null);
					naviDrawer.addNaviItem(correlation, "Correlation Queue", null);
				}
				NaviItem cytologySlide = naviDrawer.addNaviItem(crossTypeReports, "Cytology Slide", null);
				{
					naviDrawer.addNaviItem(cytologySlide, "Cytology Slide Search", null);
					naviDrawer.addNaviItem(cytologySlide, "Cytology Slide Stats", null);
				}
			}
			NaviItem apSetup = naviDrawer.addNaviItem(anatomicPathology, "AP Setup", null);
			{
				NaviItem gynCytologySetup = naviDrawer.addNaviItem(apSetup, "GYN Cytology Setup", null);
				{
					naviDrawer.addNaviItem(gynCytologySetup, "Cytology Tests", null);
					naviDrawer.addNaviItem(gynCytologySetup, "Specimen Adequacy", null);
					naviDrawer.addNaviItem(gynCytologySetup, "Specimen Adequacy Categories", null);
					naviDrawer.addNaviItem(gynCytologySetup, "Interpretation", null);
					naviDrawer.addNaviItem(gynCytologySetup, "Interpretation Grades", null);
					naviDrawer.addNaviItem(gynCytologySetup, "Additional Findings", null);
					naviDrawer.addNaviItem(gynCytologySetup, "Additional Findings Categories", null);
					naviDrawer.addNaviItem(gynCytologySetup, "Result Comments", null);
					naviDrawer.addNaviItem(gynCytologySetup, "Result Templates", null);
				}
				NaviItem ngynCytologyAdmin = naviDrawer.addNaviItem(apSetup, "Surgical/NGYN Cytology Admin", null);
				{
					naviDrawer.addNaviItem(ngynCytologyAdmin, "Diagnosis Grades", null);
					naviDrawer.addNaviItem(ngynCytologyAdmin, "Site Manager", null);
					naviDrawer.addNaviItem(ngynCytologyAdmin, "Synoptic Templates", null);
					naviDrawer.addNaviItem(ngynCytologyAdmin, "Stains", null);
					naviDrawer.addNaviItem(ngynCytologyAdmin, "Stain Type", null);
					naviDrawer.addNaviItem(ngynCytologyAdmin, "Stain Panel", null);
					naviDrawer.addNaviItem(ngynCytologyAdmin, "Histo Protocols", null);
					naviDrawer.addNaviItem(ngynCytologyAdmin, "Histo Attributes", null);
					naviDrawer.addNaviItem(ngynCytologyAdmin, "Intra-Op Types", null);
					naviDrawer.addNaviItem(ngynCytologyAdmin, "Case Attributes", null);
					naviDrawer.addNaviItem(ngynCytologyAdmin, "Case Types", null);
					naviDrawer.addNaviItem(ngynCytologyAdmin, "Addendum Reasons", null);
					naviDrawer.addNaviItem(ngynCytologyAdmin, "Slide QA Status", null);
					naviDrawer.addNaviItem(ngynCytologyAdmin, "Prostate Regions", null);
					naviDrawer.addNaviItem(ngynCytologyAdmin, "Microscopic Tests", null);
				}
				NaviItem idr = naviDrawer.addNaviItem(apSetup, "IDR", null);
				{
					naviDrawer.addNaviItem(idr, "IDR Test Filters", null);
					naviDrawer.addNaviItem(idr, "Templates", null);
				}
			}
		}
	}
}
