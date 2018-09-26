package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.DummyData;
import com.vaadin.starter.applayout.backend.Report;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "reports", layout = Root.class)
@PageTitle("Reports")
public class ReportsView extends Div {

    private final Grid<Report> grid;

    public ReportsView() {
        setClassName(GRID_VIEW);
        getStyle().set(CSSProperties.Flex.PROPERTY, "1");

        grid = new Grid();
        grid.addColumn(new ComponentRenderer<>(this::createreportInfo))
                .setHeader("Company")
                .setFrozen(true)
                .setSortable(true)
                .setWidth("200px")
                .setFlexGrow(1);
        grid.addColumn(new LocalDateRenderer<>(Report::getStartDate, "MMM dd, YYYY"))
                .setHeader("Start Date")
                .setSortable(true)
                .setWidth("160px")
                .setFlexGrow(0);
        grid.addColumn(new LocalDateRenderer<>(Report::getEndDate, "MMM dd, YYYY"))
                .setHeader("End Date")
                .setSortable(true)
                .setWidth("160px")
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createBalance))
                .setHeader(UIUtils.createRightAlignedDiv(new Text("Balance")))
                .setSortable(true)
                .setWidth("160px")
                .setFlexGrow(0);

        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::viewDetails));
        grid.setItems(DummyData.getAll());
        grid.setSizeFull();
        add(grid);
    }

    private Component createreportInfo(Report report) {
        return new ListItem(report.getSource(), report.getName());
    }

    private Component createBalance(Report report) {
        return UIUtils.createRightAlignedDiv(new Text(Double.toString(report.getBalance()) + " " + report.getCurrency()));
    }

    private void viewDetails(Report report) {
        UI.getCurrent().navigate(ReportDetailsView.class, report.getId());
    }
}
