package com.vaadin.starter.applayout.ui.views.inventory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.*;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;
import com.vaadin.starter.applayout.ui.views.ViewFrame;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

@Route(value = "analysis", layout = Root.class)
@PageTitle("Analysis")
public class Analysis extends ViewFrame {

    private static final String CLASS_NAME = "dashboard";
    private static final String REPORTS = "Reports";
    private static final String LOGS = "Logs";

    public Analysis() {
        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            setHeader(new AppBar("Analysis"));
        }

        Div viewport = UIUtils.createDiv(
                Arrays.asList(CLASS_NAME, LumoStyles.Margin.Horizontal.AUTO, LumoStyles.Margin.Responsive.Vertical.ML),
                createHeader(VaadinIcon.INVOICE, "Invoices"),
                createProgressCharts(),
                createHeader(VaadinIcon.LIST, "Orders"),
                createSalesChart(),
                UIUtils.createFlexLayout(
                        Collections.singleton(CLASS_NAME + "__bookmarks-recent-items"),
                        createTabbedList(REPORTS),
                        createTabbedList(LOGS)
                )
        );
        setContent(viewport);
    }

    private Component createHeader(VaadinIcon icon, String title) {
        FlexLayout header = UIUtils.createFlexLayout(
                Arrays.asList(
                        LumoStyles.Margin.Bottom.L,
                        LumoStyles.Margin.Top.XL,
                        LumoStyles.Margin.Responsive.Horizontal.ML,
                        LumoStyles.Spacing.Right.M
                ),
                UIUtils.createSmallIcon(Collections.singleton(LumoStyles.TextColor.TERTIARY), icon),
                UIUtils.createLabel(Collections.singleton(LumoStyles.FontSize.H3), title)
        );
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        return header;
    }

    private Component createProgressCharts() {
        FlexLayout card = UIUtils.createWrappingFlexLayout(Arrays.asList(CLASS_NAME + "__progress", LumoStyles.BorderRadius.S, LumoStyles.Shadow.S));
        card.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        card.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.BASE_COLOR);

        for (Invoice.Status status : Invoice.Status.values()) {
            card.add(createProgressSection(status));
        }

        return card;
    }

    private Component createProgressSection(Invoice.Status status) {
        int value;

        switch (status) {
            case OUTSTANDING:
                value = 24;
                break;

            case OPEN:
                value = 40;
                break;

            case PAID:
                value = 32;
                break;

            default:
                value = 4;
                break;
        }

        FlexLayout textContainer = UIUtils.createFlexLayout(
                Collections.singleton(LumoStyles.Spacing.Right.XS),
                UIUtils.createLabel(Collections.singleton(LumoStyles.FontSize.H2), Integer.toString(value)),
                UIUtils.createLabel(Collections.singleton(LumoStyles.FontSize.S), "%")
        );
        textContainer.setAlignItems(FlexComponent.Alignment.BASELINE);
        textContainer.getStyle().set(CSSProperties.Position.PROPERTY, CSSProperties.Position.ABSOLUTE);

        Chart chart = UIUtils.createProgressChart(value);
        chart.addClassName(status.getName().toLowerCase());

        FlexLayout chartContainer = new FlexLayout(chart, textContainer);
        chartContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        chartContainer.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        chartContainer.getStyle().set(CSSProperties.Position.PROPERTY, CSSProperties.Position.RELATIVE);
        chartContainer.setHeight("120px");
        chartContainer.setWidth("120px");

        FlexLayout column = UIUtils.createColumn(
                Arrays.asList(LumoStyles.Padding.Bottom.S, LumoStyles.Padding.Top.M),
                new Label(status.getName()),
                chartContainer
        );
        column.setAlignItems(FlexComponent.Alignment.CENTER);
        return column;
    }

    private Component createSalesChart() {
        Chart chart = new Chart(ChartType.AREASPLINE);

        Configuration conf = chart.getConfiguration();
        conf.setTitle("2018");
        conf.getLegend().setEnabled(false);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        conf.addxAxis(xAxis);

        conf.getyAxis().setTitle("");

        conf.addSeries(new ListSeries(220, 240, 400, 360, 420, 640, 580, 800, 600, 580, 740, 800));

        FlexLayout card = UIUtils.createWrappingFlexLayout(Arrays.asList(LumoStyles.BorderRadius.S, LumoStyles.Padding.All.M, LumoStyles.Shadow.S), chart);
        card.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.BASE_COLOR);
        card.getStyle().set(CSSProperties.BoxSizing.PROPERTY, CSSProperties.BoxSizing.BORDER_BOX);
        card.setHeight("400px");
        return card;
    }

    private Component createTabbedList(String title) {
        Component header = createHeader(title.equals(REPORTS) ? VaadinIcon.RECORDS : VaadinIcon.EDIT, title);

        Tabs tabs = new Tabs();
        String[] labels = title.equals(REPORTS) ? new String[]{"All", "Annual", "Progress", "Technical"} : new String[]{"All", "Analytics", "System", "User"};

        for (String label : labels) {
            tabs.add(new Tab(label));
        }

        FlexLayout items = UIUtils.createColumn(
                Collections.singleton(LumoStyles.Margin.Vertical.S),
                new ListItem(new Icon(VaadinIcon.CHART), "My Weekly Report", "Last opened May 5, 2018"),
                new ListItem(new Icon(VaadinIcon.SITEMAP), "My Workflow", "Last opened May 5, 2018")
        );

        FlexLayout card = UIUtils.createColumn(Arrays.asList(LumoStyles.BorderRadius.S, LumoStyles.Shadow.S), tabs, items);
        card.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.BASE_COLOR);

        return new Div(header, card);
    }
}
