package com.vaadin.starter.applayout.ui;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ClickNotifier;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
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
import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.components.NaviDrawer;
import com.vaadin.starter.applayout.ui.components.NaviItem;
import com.vaadin.starter.applayout.ui.components.TabBar;
import com.vaadin.starter.applayout.ui.utils.NaviDrawerProvider;
import com.vaadin.starter.applayout.ui.utils.UIUtils;
import com.vaadin.starter.applayout.ui.views.*;

import java.util.Collections;

@HtmlImport("frontend://styles/shared-styles.html")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class Root extends FlexLayout
        implements RouterLayout, PageConfigurator, BeforeEnterObserver {

    private String CLASS_NAME = "root";

    private Div appHeaderOuter;
    private Div appHeaderInner;
    private Div appFooterInner;
    private Div appFooterOuter;

    private FlexLayout row;
    private FlexLayout column;

    private NaviDrawer naviDrawer;
    private TabBar tabBar;
    private FlexLayout viewContainer;

    public Root() {
        setClassName(CLASS_NAME);

        // Initialise the UI building blocks.
        initStructure();

        // Populate the navigation drawer.
        initNaviItems();

        // Configure the headers and footers (optional).
        initHeadersAndFooters();
    }

    /**
     * Initialise the required components and containers.
     */
    private void initStructure() {
        naviDrawer = NaviDrawerProvider.getNaviDrawer();

        // TODO: Explore DOM event triggering/listening.
        // naviDrawer.getElement().addEventListener("my-event", e -> naviDrawer.toggle());
        // naviDrawer.getElement().addSynchronizedPropertyEvent("my-event");

        initViewContainer();

        column = UIUtils.createColumn(Collections.singleton(CLASS_NAME + "__column"), viewContainer);

        row = UIUtils.createFlexLayout(Collections.singleton(CLASS_NAME + "__row"), naviDrawer, column);
        add(row);
    }

    /**
     * Initialise the navigation items.
     */
    private void initNaviItems() {

        naviDrawer.addNaviItem(VaadinIcon.DASHBOARD, "Dashboard", Dashboard.class);
        naviDrawer.addNaviItem(VaadinIcon.FILE, "Reports", ReportsView.class);

        NaviItem personnel = naviDrawer.addNaviItem(VaadinIcon.USERS, "Personnel", Personnel.class);
        naviDrawer.addNaviItem(personnel, "Vertical Split", VerticalSplitView.class);
        naviDrawer.addNaviItem(personnel, "Horizontal Split", HorizontalSplitView.class);

        naviDrawer.addNaviItem(VaadinIcon.FILTER, "Filter List", FilterList.class);
    }

    /**
     * Configure the app's inner and outer headers and footers.
     */
    private void initHeadersAndFooters() {
        // setAppHeaderOuter(new Label("Outer header"));
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
        // Shift-click to add a new tab.
        if (event.getButton() == 0 && event.isShiftKey()) {
            tabBar.setSelectedTab(tabBar.addClosableNaviTab(item.getText(), item.getNavigationTarget()));
        }

        // Update the current tab, or create the first one.
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

    private void initViewContainer() {
        viewContainer = UIUtils.createFlexLayout(Collections.singleton(CLASS_NAME + "__view-container"));
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
        NaviItem ordering = naviDrawer.addNaviItem(VaadinIcon.ABACUS, "Ordering", Default.class);
        {
            NaviItem orderEntry = naviDrawer.addNaviItem(ordering, "Order Entry", Default.class);
            {
                naviDrawer.addNaviItem(orderEntry, "New Order", Default.class);
                naviDrawer.addNaviItem(orderEntry, "Remote Orders", Default.class);
                naviDrawer.addNaviItem(orderEntry, "Incomplete Orders", Default.class);
            }
            NaviItem setup = naviDrawer.addNaviItem(ordering, "Setup", Default.class);
            {
                naviDrawer.addNaviItem(setup, "Clients", Default.class);
                naviDrawer.addNaviItem(setup, "Client Groups", Default.class);
                naviDrawer.addNaviItem(setup, "Providers", Default.class);
                naviDrawer.addNaviItem(setup, "Lab Tests", Default.class);
                naviDrawer.addNaviItem(setup, "Lab Test Panels", Default.class);
                naviDrawer.addNaviItem(setup, "Patients", Default.class);
            }
            NaviItem labeling = naviDrawer.addNaviItem(ordering, "Labeling", Default.class);
            {
                naviDrawer.addNaviItem(labeling, "Order Labels", Default.class);
                naviDrawer.addNaviItem(labeling, "Current Unprinted Batch", Default.class);
                naviDrawer.addNaviItem(labeling, "Label Batches", Default.class);
            }
            NaviItem dataCleanup = naviDrawer.addNaviItem(ordering, "Data Cleanup", Default.class);
            {
                naviDrawer.addNaviItem(dataCleanup, "Missing Info", Default.class);
            }
            NaviItem reports = naviDrawer.addNaviItem(ordering, "Reports", Default.class);
            {
                naviDrawer.addNaviItem(reports, "Order Search", Default.class);
                naviDrawer.addNaviItem(reports, "Order Stats", Default.class);
                naviDrawer.addNaviItem(reports, "Workflow Action Queue", Default.class);
            }
            NaviItem interfaces = naviDrawer.addNaviItem(ordering, "Interfaces", Default.class);
            {
                naviDrawer.addNaviItem(interfaces, "EDI Stats", Default.class);
                naviDrawer.addNaviItem(interfaces, "EDI Log", Default.class);
            }
            NaviItem sendouts = naviDrawer.addNaviItem(ordering, "Sendouts", Default.class);
            {
                naviDrawer.addNaviItem(sendouts, "Sendout Queue", Default.class);
                naviDrawer.addNaviItem(sendouts, "Manifest Queue", Default.class);
                naviDrawer.addNaviItem(sendouts, "Sendout Receive Queue", Default.class);
                naviDrawer.addNaviItem(sendouts, "Search Sendouts", Default.class);
            }
        }

        NaviItem anatomicPathology = naviDrawer.addNaviItem(VaadinIcon.ABACUS, "Anatomic Pathology", Default.class);
        {
            naviDrawer.addNaviItem(anatomicPathology, "Pathologist Queue", Default.class);
            naviDrawer.addNaviItem(anatomicPathology, "Case Distribution", Default.class);

            NaviItem gynCytology = naviDrawer.addNaviItem(anatomicPathology, "GYN Cytology", Default.class);
            {
                NaviItem specimenPlacement = naviDrawer.addNaviItem(gynCytology, "Specimen Placement", Default.class);
                {
                    naviDrawer.addNaviItem(specimenPlacement, "Rack Placement", Default.class);
                }
                naviDrawer.addNaviItem(gynCytology, "Gyn Batch Log", Default.class);
                naviDrawer.addNaviItem(gynCytology, "Cytotech", Default.class);
                naviDrawer.addNaviItem(gynCytology, "Quality Control", Default.class);

                NaviItem apReports = naviDrawer.addNaviItem(gynCytology, "AP Reports", Default.class);
                {
                    naviDrawer.addNaviItem(apReports, "CT [Daily Log]", Default.class);
                    naviDrawer.addNaviItem(apReports, "QC [Daily Log]", Default.class);
                    naviDrawer.addNaviItem(apReports, "Case Search", Default.class);
                    naviDrawer.addNaviItem(apReports, "Case Stats", Default.class);
                    naviDrawer.addNaviItem(apReports, "Consolidated Letter", Default.class);
                    naviDrawer.addNaviItem(apReports, "Tickler Report", Default.class);
                    naviDrawer.addNaviItem(apReports, "Followup Letter", Default.class);
                    naviDrawer.addNaviItem(apReports, "Abnormal Followup", Default.class);
                }
            }
            NaviItem ngynCytology = naviDrawer.addNaviItem(anatomicPathology, "Surgical/NGYN Cytology", Default.class);
            {
                naviDrawer.addNaviItem(ngynCytology, "Grossing Queue", Default.class);
                naviDrawer.addNaviItem(ngynCytology, "Transcription Cases", Default.class);
                naviDrawer.addNaviItem(ngynCytology, "Screening Queue", Default.class);

                NaviItem historology = naviDrawer.addNaviItem(ngynCytology, "Historology", Default.class);
                {
                    naviDrawer.addNaviItem(historology, "Block Queue", Default.class);
                    naviDrawer.addNaviItem(historology, "Slide Queue", Default.class);
                    naviDrawer.addNaviItem(historology, "Histo Cases", Default.class);
                    naviDrawer.addNaviItem(historology, "Block Log", Default.class);
                    naviDrawer.addNaviItem(historology, "Slide Log", Default.class);
                    naviDrawer.addNaviItem(historology, "Block Stats", Default.class);
                    naviDrawer.addNaviItem(historology, "Slide Stats", Default.class);
                    naviDrawer.addNaviItem(historology, "Correlation Queue", Default.class);
                }
                NaviItem microscopicReports = naviDrawer.addNaviItem(ngynCytology, "Microscopic Reports", Default.class);
                {
                    naviDrawer.addNaviItem(microscopicReports, "Case Log Report", Default.class);
                    naviDrawer.addNaviItem(microscopicReports, "Process Only Report", Default.class);
                    naviDrawer.addNaviItem(microscopicReports, "IntraOp Stats", Default.class);
                    naviDrawer.addNaviItem(microscopicReports, "Microscopic Case Stats", Default.class);
                    naviDrawer.addNaviItem(microscopicReports, "Microscopic Case Search", Default.class);
                    naviDrawer.addNaviItem(microscopicReports, "Consultation Stats", Default.class);
                    naviDrawer.addNaviItem(microscopicReports, "Consultation Search", Default.class);
                }
            }
            NaviItem crossTypeReports = naviDrawer.addNaviItem(anatomicPathology, "Cross-type Reports", Default.class);
            {
                NaviItem correlation = naviDrawer.addNaviItem(crossTypeReports, "Correlation", Default.class);
                {
                    naviDrawer.addNaviItem(correlation, "Correlation [Date]", Default.class);
                    naviDrawer.addNaviItem(correlation, "Correlation Stats", Default.class);
                    naviDrawer.addNaviItem(correlation, "Correlation Log", Default.class);
                    naviDrawer.addNaviItem(correlation, "Correlation Queue", Default.class);
                }
                NaviItem cytologySlide = naviDrawer.addNaviItem(crossTypeReports, "Cytology Slide", Default.class);
                {
                    naviDrawer.addNaviItem(cytologySlide, "Cytology Slide Search", Default.class);
                    naviDrawer.addNaviItem(cytologySlide, "Cytology Slide Stats", Default.class);
                }
            }
            NaviItem apSetup = naviDrawer.addNaviItem(anatomicPathology, "AP Setup", Default.class);
            {
                NaviItem gynCytologySetup = naviDrawer.addNaviItem(apSetup, "GYN Cytology Setup", Default.class);
                {
                    naviDrawer.addNaviItem(gynCytologySetup, "Cytology Tests", Default.class);
                    naviDrawer.addNaviItem(gynCytologySetup, "Specimen Adequacy", Default.class);
                    naviDrawer.addNaviItem(gynCytologySetup, "Specimen Adequacy Categories", Default.class);
                    naviDrawer.addNaviItem(gynCytologySetup, "Interpretation", Default.class);
                    naviDrawer.addNaviItem(gynCytologySetup, "Interpretation Grades", Default.class);
                    naviDrawer.addNaviItem(gynCytologySetup, "Additional Findings", Default.class);
                    naviDrawer.addNaviItem(gynCytologySetup, "Additional Findings Categories", Default.class);
                    naviDrawer.addNaviItem(gynCytologySetup, "Result Comments", Default.class);
                    naviDrawer.addNaviItem(gynCytologySetup, "Result Templates", Default.class);
                }
                NaviItem ngynCytologyAdmin = naviDrawer.addNaviItem(apSetup, "Surgical/NGYN Cytology Admin", Default.class);
                {
                    naviDrawer.addNaviItem(ngynCytologyAdmin, "Diagnosis Grades", Default.class);
                    naviDrawer.addNaviItem(ngynCytologyAdmin, "Site Manager", Default.class);
                    naviDrawer.addNaviItem(ngynCytologyAdmin, "Synoptic Templates", Default.class);
                    naviDrawer.addNaviItem(ngynCytologyAdmin, "Stains", Default.class);
                    naviDrawer.addNaviItem(ngynCytologyAdmin, "Stain Type", Default.class);
                    naviDrawer.addNaviItem(ngynCytologyAdmin, "Stain Panel", Default.class);
                    naviDrawer.addNaviItem(ngynCytologyAdmin, "Histo Protocols", Default.class);
                    naviDrawer.addNaviItem(ngynCytologyAdmin, "Histo Attributes", Default.class);
                    naviDrawer.addNaviItem(ngynCytologyAdmin, "Intra-Op Types", Default.class);
                    naviDrawer.addNaviItem(ngynCytologyAdmin, "Case Attributes", Default.class);
                    naviDrawer.addNaviItem(ngynCytologyAdmin, "Case Types", Default.class);
                    naviDrawer.addNaviItem(ngynCytologyAdmin, "Addendum Reasons", Default.class);
                    naviDrawer.addNaviItem(ngynCytologyAdmin, "Slide QA Status", Default.class);
                    naviDrawer.addNaviItem(ngynCytologyAdmin, "Prostate Regions", Default.class);
                    naviDrawer.addNaviItem(ngynCytologyAdmin, "Microscopic Tests", Default.class);
                }
                NaviItem idr = naviDrawer.addNaviItem(apSetup, "IDR", Default.class);
                {
                    naviDrawer.addNaviItem(idr, "IDR Test Filters", Default.class);
                    naviDrawer.addNaviItem(idr, "Templates", Default.class);
                }
            }
        }
    }
}
