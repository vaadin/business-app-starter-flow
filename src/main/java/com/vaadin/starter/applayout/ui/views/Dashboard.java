package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.ui.MainLayout;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Dashboard")
public class Dashboard extends Div {

    private final String CLASS_NAME = "dashboard";

    private final Random random;
    private final Div viewport;

    public Dashboard() {
        getStyle().set(CSSProperties.Overflow.PROPERTY, CSSProperties.Overflow.AUTO);
        getStyle().set(CSSProperties.Flex.PROPERTY, "1");

        random = new Random();

        viewport = new Div();
        viewport.addClassNames(LumoStyles.Margin.Responsive.Horizontal.LXL, LumoStyles.Margin.Responsive.Vertical.ML);
        add(viewport);

        viewport.add(createHeader(VaadinIcon.CHECK, "Progress"));
        viewport.add(createProgressCharts());

        viewport.add(createHeader(VaadinIcon.TRENDING_UP, "Sales"));
        viewport.add(createSalesChart());

        FlexLayout row = UIUtils.createFlexLayout(
                Collections.singleton(CLASS_NAME + "__bookmarks-recent-items"),
                new Div(createHeader(VaadinIcon.BOOKMARK, "Bookmark"), createTabbedList()),
                new Div(createHeader(VaadinIcon.TIME_BACKWARD, "Recent Items"), createTabbedList())
        );
        viewport.add(row);

    }

    private Component createHeader(VaadinIcon icon, String title) {
        FlexLayout header = UIUtils.createFlexLayout(
                Arrays.asList(
                        LumoStyles.Margin.Bottom.L,
                        LumoStyles.Margin.Top.XL,
                        LumoStyles.Spacing.Right.M
                ),
                UIUtils.createSmallIcon(Collections.singleton(LumoStyles.Text.TERTIARY), icon),
                UIUtils.createLabel(Collections.singleton(LumoStyles.FontSize.H3), title)
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
        int value = random.nextInt(100);

        FlexLayout textContainer = UIUtils.createFlexLayout(
                Collections.singleton(LumoStyles.Spacing.Right.XS),
                UIUtils.createLabel(Collections.singleton(LumoStyles.FontSize.H2), Integer.toString(value)),
                UIUtils.createLabel(Collections.singleton(LumoStyles.FontSize.S), "%")
        );
        textContainer.setAlignItems(FlexComponent.Alignment.BASELINE);
        textContainer.getStyle().set(CSSProperties.Position.PROPERTY, CSSProperties.Position.ABSOLUTE);

        FlexLayout chartContainer = new FlexLayout(createProgressChart(value), textContainer);
        chartContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        chartContainer.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        chartContainer.getStyle().set(CSSProperties.Position.PROPERTY, CSSProperties.Position.RELATIVE);
        chartContainer.setHeight("120px");
        chartContainer.setWidth("120px");

        FlexLayout column = UIUtils.createFlexLayout(
                Arrays.asList(LumoStyles.Padding.Bottom.S, LumoStyles.Padding.Top.M),
                new Label(title),
                chartContainer
        );
        column.setAlignItems(FlexComponent.Alignment.CENTER);
        column.getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);

        return column;
    }

    private Chart createProgressChart(int value) {
        Chart chart = new Chart();
        chart.setSizeFull();

        Configuration configuration = chart.getConfiguration();
        configuration.getChart().setType(ChartType.SOLIDGAUGE);
        configuration.setTitle("");
        configuration.getTooltip().setEnabled(false);

        configuration.getyAxis().setMin(0);
        configuration.getyAxis().setMax(100);
        configuration.getyAxis().getLabels().setEnabled(false);

        PlotOptionsSolidgauge opt = new PlotOptionsSolidgauge();
        opt.getDataLabels().setEnabled(false);
        configuration.setPlotOptions(opt);

        DataSeriesItemWithRadius point = new DataSeriesItemWithRadius();
        point.setY(value);
        point.setInnerRadius("100%");
        point.setRadius("110%");
        configuration.setSeries(new DataSeries(point));

        Pane pane = configuration.getPane();
        pane.setStartAngle(0);
        pane.setEndAngle(360);

        Background background = new Background();
        background.setShape(BackgroundShape.ARC);
        background.setInnerRadius("100%");
        background.setOuterRadius("110%");
        pane.setBackground(background);

        return chart;
    }

    private Component createSalesChart() {
        Chart chart = new Chart(ChartType.AREASPLINE);

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Product sales for 2018");
        conf.getLegend().setEnabled(false);

        XAxis xAxis = new XAxis();
        xAxis.setCategories(new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
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
        tabs.add(new Tab("All"));
        tabs.add(new Tab("Archive"));
        tabs.add(new Tab("Workflows"));
        tabs.add(new Tab("Support"));

        FlexLayout items = UIUtils.createFlexLayout(
                Collections.singleton(LumoStyles.Margin.Vertical.S),
                new ListItem(VaadinIcon.CHART, "My Weekly Report", "Last opened May 5, 2018"),
                new ListItem(VaadinIcon.SITEMAP, "My Workflow", "Last opened May 5, 2018")
        );
        items.getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);

        FlexLayout card = UIUtils.createWrappingFlexLayout(Arrays.asList(LumoStyles.BorderRadius.S, LumoStyles.Shadow.S), tabs, items);
        card.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.BASE_COLOR);
        card.getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);
        return card;
    }

    private class DataSeriesItemWithRadius extends DataSeriesItem {

        private String radius;
        private String innerRadius;

        public String getRadius() {
            return radius;
        }

        public void setRadius(String radius) {
            this.radius = radius;
            makeCustomized();
        }

        public String getInnerRadius() {
            return innerRadius;
        }

        public void setInnerRadius(String innerRadius) {
            this.innerRadius = innerRadius;
            makeCustomized();
        }
    }
}
