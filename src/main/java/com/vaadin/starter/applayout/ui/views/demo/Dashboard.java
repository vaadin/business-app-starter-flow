package com.vaadin.starter.applayout.ui.views.demo;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.XAxis;
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
import com.vaadin.starter.applayout.backend.DummyData;
import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;
import com.vaadin.starter.applayout.ui.views.ViewFrame;

import java.util.Arrays;
import java.util.Collections;

@Route(value = "dashboard", layout = Root.class)
@PageTitle("Dashboard")
public class Dashboard extends ViewFrame {

    private static final String CLASS_NAME = "dashboard";

    public Dashboard() {
        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            setHeader(new AppBar("Dashboard"));
        }

        Div viewport = UIUtils.createDiv(
                Arrays.asList(CLASS_NAME, LumoStyles.Margin.Horizontal.AUTO, LumoStyles.Margin.Responsive.Vertical.ML),
                createHeader(VaadinIcon.CHECK, "Progress"),
                createProgressCharts(),
                createHeader(VaadinIcon.TRENDING_UP, "Sales"),
                createSalesChart(),
                UIUtils.createFlexLayout(
                        Collections.singleton(CLASS_NAME + "__bookmarks-recent-items"),
                        new Div(createHeader(VaadinIcon.BOOKMARK, "Bookmark"), createTabbedList()),
                        new Div(createHeader(VaadinIcon.TIME_BACKWARD, "Recent Items"), createTabbedList())
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
                UIUtils.createH3Label(title)
        );
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        return header;
    }

    private Component createProgressCharts() {
        FlexLayout card = UIUtils.createWrappingFlexLayout(Arrays.asList(CLASS_NAME + "__progress", LumoStyles.BorderRadius.S, LumoStyles.Shadow.S));
        card.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        card.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.BASE_COLOR);

        for (String section : new String[]{"Today", "Week", "Month", "Year"}) {
            card.add(createProgressSection(section));
        }

        return card;
    }

    private Component createProgressSection(String title) {
        int value = DummyData.getRandomInt(50, 100);

        FlexLayout textContainer = UIUtils.createFlexLayout(
                Collections.singleton(LumoStyles.Spacing.Right.XS),
                UIUtils.createH2Label(Integer.toString(value)),
                UIUtils.createSmallLabel("%")
        );
        textContainer.setAlignItems(FlexComponent.Alignment.BASELINE);
        textContainer.getStyle().set(CSSProperties.Position.PROPERTY, CSSProperties.Position.ABSOLUTE);

        FlexLayout chartContainer = new FlexLayout(UIUtils.createProgressChart(value), textContainer);
        chartContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        chartContainer.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        chartContainer.getStyle().set(CSSProperties.Position.PROPERTY, CSSProperties.Position.RELATIVE);
        chartContainer.setHeight("120px");
        chartContainer.setWidth("120px");

        FlexLayout column = UIUtils.createColumn(
                Arrays.asList(LumoStyles.Padding.Bottom.S, LumoStyles.Padding.Top.M),
                new Label(title),
                chartContainer
        );
        column.setAlignItems(FlexComponent.Alignment.CENTER);
        return column;
    }

    private Component createSalesChart() {
        Chart chart = new Chart(ChartType.AREASPLINE);

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Product sales for 2018");
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

    private Component createTabbedList() {
        Tabs tabs = new Tabs();

        for (String label : new String[]{"All", "Archive", "Workflows", "Support"}) {
            tabs.add(new Tab(label));
        }

        FlexLayout items = UIUtils.createColumn(
                Collections.singleton(LumoStyles.Margin.Vertical.S),
                new ListItem(new Icon(VaadinIcon.CHART), "My Weekly Report", "Last opened May 5, 2018"),
                new ListItem(new Icon(VaadinIcon.SITEMAP), "My Workflow", "Last opened May 5, 2018")
        );

        FlexLayout card = UIUtils.createColumn(Arrays.asList(LumoStyles.BorderRadius.S, LumoStyles.Shadow.S), tabs, items);
        card.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.BASE_COLOR);
        return card;
    }
}
