package com.vaadin.starter.responsiveapptemplate.ui.views.finance;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.responsiveapptemplate.backend.Payment;
import com.vaadin.starter.responsiveapptemplate.backend.UIConfig;
import com.vaadin.starter.responsiveapptemplate.ui.Root;
import com.vaadin.starter.responsiveapptemplate.ui.components.ListItem;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.responsiveapptemplate.ui.utils.CSSProperties;
import com.vaadin.starter.responsiveapptemplate.ui.utils.LumoStyles;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;
import com.vaadin.starter.responsiveapptemplate.ui.views.ViewFrame;

import java.util.Arrays;
import java.util.Collections;

@Route(value = "statistics", layout = Root.class)
@PageTitle("Statistics")
public class Statistics extends ViewFrame {

    private static final String CLASS_NAME = "dashboard";

    public Statistics() {
        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            setHeader(new AppBar("Statistics"));
        }

        Div viewport = UIUtils.createDiv(
                Arrays.asList(CLASS_NAME, LumoStyles.Margin.Horizontal.AUTO, LumoStyles.Margin.Responsive.Vertical.ML),
                createHeader(VaadinIcon.CREDIT_CARD, "Payments"),
                createProgressCharts(),
                createHeader(VaadinIcon.MONEY_EXCHANGE, "Transactions"),
                UIUtils.createSalesChart("2018", "Number of Processed Transactions"),
                UIUtils.createFlexLayout(
                        Collections.singleton(CLASS_NAME + "__bookmarks-recent-items"),
                        createReports(),
                        createLogs()
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

        for (Payment.Status status : Payment.Status.values()) {
            card.add(createProgressSection(status));
        }

        return card;
    }

    private Component createProgressSection(Payment.Status status) {
        int value;

        switch (status) {
            case PENDING:
                value = 24;
                break;

            case SUBMITTED:
                value = 40;
                break;

            case CONFIRMED:
                value = 32;
                break;

            default:
                value = 4;
                break;
        }

        FlexLayout textContainer = UIUtils.createFlexLayout(
                Collections.singleton(LumoStyles.Spacing.Right.XS),
                UIUtils.createH2Label(Integer.toString(value)),
                UIUtils.createSmallLabel("%")
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

    private Component createReports() {
        Component header = createHeader(VaadinIcon.RECORDS, "Reports");

        Tabs tabs = new Tabs();
        for (String label : new String[]{"All", "Archive", "Workflows", "Support"}) {
            tabs.add(new Tab(label));
        }

        FlexLayout items = UIUtils.createColumn(
                Collections.singleton(LumoStyles.Margin.Vertical.S),
                new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CHART), "Weekly Report", "Generated Oct 5, 2018", createInfoButton()),
                new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.SITEMAP), "Payment Workflows", "Last modified Oct 24, 2018", createInfoButton())
        );

        FlexLayout card = UIUtils.createColumn(Arrays.asList(LumoStyles.BorderRadius.S, LumoStyles.Shadow.S), tabs, items);
        card.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.BASE_COLOR);

        return new Div(header, card);
    }

    private Component createLogs() {
        Component header = createHeader(VaadinIcon.EDIT, "Logs");

        Tabs tabs = new Tabs();
        for (String label : new String[]{"All", "Transfer", "Security", "Change"}) {
            tabs.add(new Tab(label));
        }

        FlexLayout items = UIUtils.createColumn(
                Collections.singleton(LumoStyles.Margin.Vertical.S),
                new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.EXCHANGE), "Transfers (October)", "Generated Oct 31, 2018", createInfoButton()),
                new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.SHIELD), "Security Log", "Updated 16:31 CET", createInfoButton())
        );

        FlexLayout card = UIUtils.createColumn(Arrays.asList(LumoStyles.BorderRadius.S, LumoStyles.Shadow.S), tabs, items);
        card.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.BASE_COLOR);

        return new Div(header, card);
    }

    private Button createInfoButton() {
        Button infoButton = UIUtils.createSmallButton(VaadinIcon.INFO);
        infoButton.addClickListener(e -> Notification.show("Not implemented yet.", 3000, Notification.Position.BOTTOM_CENTER));
        return infoButton;
    }
}
