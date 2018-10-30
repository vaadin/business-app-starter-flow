package com.vaadin.starter.applayout.ui.views.demo;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.DummyData;
import com.vaadin.starter.applayout.backend.Report;
import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.applayout.ui.utils.UIUtils;
import com.vaadin.starter.applayout.ui.views.ViewFrame;

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "reports", layout = Root.class)
@PageTitle("Reports")
public class Reports extends ViewFrame {

    public Reports() {
        // Header
        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            setHeader(new AppBar("Reports"));
        }

        // Grid
        Grid<Report> grid = new Grid<>();
        grid.setItems(DummyData.getReports());

        grid.addColumn(new ComponentRenderer<>(this::createReportInfo))
                .setHeader("Company")
                .setFrozen(true)
                .setWidth("200px")
                .setFlexGrow(1);
        grid.addColumn(new LocalDateRenderer<>(Report::getStartDate, "MMM dd, YYYY"))
                .setHeader("Start Date")
                .setWidth("160px")
                .setFlexGrow(0);
        grid.addColumn(new LocalDateRenderer<>(Report::getEndDate, "MMM dd, YYYY"))
                .setHeader("End Date")
                .setWidth("160px")
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createBalance))
                .setHeader(UIUtils.createRightAlignedDiv("Balance ($)"))
                .setWidth("160px")
                .setFlexGrow(0);

        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::viewDetails));
        grid.setItems(DummyData.getReports());
        grid.setSizeFull();

        setContent(grid);
        getContent().addClassName(GRID_VIEW);
    }

    private Component createReportInfo(Report report) {
        return new ListItem(new Image(report.getSource(), ""), report.getName());
    }

    private Component createBalance(Report report) {
        return UIUtils.createRightAlignedDiv(UIUtils.formatAmount(report.getBalance()));
    }

    private void viewDetails(Report report) {
        UI.getCurrent().navigate(ReportDetails.class, report.getId());
    }
}
