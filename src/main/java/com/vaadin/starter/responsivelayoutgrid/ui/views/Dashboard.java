package com.vaadin.starter.responsivelayoutgrid.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.responsivelayoutgrid.ui.MainLayout;
import com.vaadin.starter.responsivelayoutgrid.ui.utils.CSSProperties;
import com.vaadin.starter.responsivelayoutgrid.ui.utils.LumoStyles;
import com.vaadin.starter.responsivelayoutgrid.ui.utils.UIUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Dashboard")
public class Dashboard extends Div {

    private final Random random;
    private final Div viewport;

    public Dashboard() {
        getStyle().set(CSSProperties.Overflow.PROPERTY, CSSProperties.Overflow.AUTO);
        getStyle().set(CSSProperties.Flex.PROPERTY, "1");

        random = new Random();

        viewport = new Div();
        viewport.addClassNames(LumoStyles.Margin.Horizontal.AUTO, LumoStyles.Margin.Responsive.Vertical.ML);
        viewport.getStyle().set(CSSProperties.MaxWidth.PROPERTY, CSSProperties.MaxWidth._800);
        add(viewport);

        viewport.add(createHeader(VaadinIcon.CHECK, "Progress"));
        viewport.add(createProgressSection());

        viewport.add(createHeader(VaadinIcon.TRENDING_UP, "Sales"));
        viewport.add(createHeader(VaadinIcon.BOOKMARK, "Bookmark"));
        viewport.add(createHeader(VaadinIcon.TIME_BACKWARD, "Recent Items"));

    }

    private Component createProgressSection() {
        FlexLayout progress = UIUtils.createWrappingFlexLayout(Arrays.asList(LumoStyles.Shadow.S, LumoStyles.BorderRadius.S));
        progress.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        progress.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, "var(--lumo-base-color)");

        for (String section : new String[]{"Today", "Week", "Month", "Year"}) {
            progress.add(createChart(section));
        }

        return progress;
    }

    private Component createHeader(VaadinIcon icon, String title) {
        FlexLayout header = UIUtils.createFlexLayout(
                Arrays.asList(
                        LumoStyles.Margin.Bottom.L,
                        LumoStyles.Margin.Responsive.Horizontal.ML,
                        LumoStyles.Margin.Top.XL,
                        LumoStyles.Spacing.Right.M
                ),
                UIUtils.createSmallIcon(Collections.singleton(LumoStyles.Text.TERTIARY), icon),
                UIUtils.createLabel(Collections.singleton(LumoStyles.FontSize.H3), title)
        );
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        return header;
    }

    private Component createChart(String title) {
        int value = random.nextInt(100);

        FlexLayout textContainer = UIUtils.createFlexLayout(
                Collections.singleton(LumoStyles.Spacing.Right.XS),
                UIUtils.createLabel(Collections.singleton(LumoStyles.FontSize.H2), Integer.toString(value)),
                UIUtils.createLabel(Collections.singleton(LumoStyles.FontSize.S), "%")
        );
        textContainer.setAlignItems(FlexComponent.Alignment.BASELINE);
        textContainer.getStyle().set(CSSProperties.Position.PROPERTY, CSSProperties.Position.ABSOLUTE);

        FlexLayout chartContainer = new FlexLayout(createChart(value), textContainer);
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
        column.getStyle().set(CSSProperties.MinWidth.PROPERTY, "200px");
        return column;
    }

    private Chart createChart(int value) {
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
