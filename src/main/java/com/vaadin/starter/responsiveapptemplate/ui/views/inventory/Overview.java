package com.vaadin.starter.responsiveapptemplate.ui.views.inventory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.responsiveapptemplate.backend.Invoice;
import com.vaadin.starter.responsiveapptemplate.backend.UIConfig;
import com.vaadin.starter.responsiveapptemplate.ui.Root;
import com.vaadin.starter.responsiveapptemplate.ui.components.ListItem;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.responsiveapptemplate.ui.layout.*;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.*;
import com.vaadin.starter.responsiveapptemplate.ui.utils.*;
import com.vaadin.starter.responsiveapptemplate.ui.views.ViewFrame;

@Route(value = "overview", layout = Root.class)
@PageTitle("Overview")
public class Overview extends ViewFrame {

    private static final String CLASS_NAME = "dashboard";
    private static final String REPORTS = "Reports";
    private static final String LOGS = "Logs";

    public Overview() {
        setViewHeader(new AppBar("Overview"));
        setViewContent(createContent());
    }

    private Component createContent() {
        Component paymentsHeader = createHeader(VaadinIcon.INVOICE, "Invoices");
        Component paymentsCharts = createProgressCharts();

        Component transactionsHeader = createHeader(VaadinIcon.PACKAGE,
                "Customer Orders");
        Component transactionsChart = UIUtils.createSalesChart("2018",
                "Number of Customer Orders");

        Component reports = createReports();
        Component logs = createLogs();
        FlexBoxLayout items = new FlexBoxLayout(reports, logs);
        items.addClassName(CLASS_NAME + "__bookmarks-recent-items");
        items.setSpacing(Bottom.L);

        FlexBoxLayout content = new FlexBoxLayout(paymentsHeader,
                paymentsCharts, transactionsHeader, transactionsChart, items);
        content.setFlexDirection(FlexDirection.COLUMN);
        content.setMargin(Horizontal.AUTO, Vertical.RESPONSIVE_L);
        content.setMaxWidth("1024px");
        content.setPadding(Horizontal.RESPONSIVE_X);
        content.setSpacing(Bottom.L);
        return content;
    }

    private Component createHeader(VaadinIcon icon, String title) {
        FlexBoxLayout header = new FlexBoxLayout(
                UIUtils.createIcon(IconSize.M, TextColor.TERTIARY, icon),
                UIUtils.createH3Label(title));
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setMargin(Horizontal.RESPONSIVE_L, Top.S);
        header.setSpacing(Right.L);
        return header;
    }

    private Component createProgressCharts() {
        FlexBoxLayout card = new FlexBoxLayout();
        card.addClassName(CLASS_NAME + "__progress");
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
        card.setBorderRadius(BorderRadius.S);
        card.setFlexWrap(FlexWrap.WRAP);
        card.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        card.setShadow(Shadow.S);

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

        FlexBoxLayout textContainer = new FlexBoxLayout(
                UIUtils.createH2Label(Integer.toString(value)),
                UIUtils.createLabel(FontSize.S, "%"));
        textContainer.setAlignItems(FlexComponent.Alignment.BASELINE);
        textContainer.setPosition(Position.ABSOLUTE);
        textContainer.setSpacing(Right.XS);

        Chart chart = UIUtils.createProgressChart(value);
        chart.addClassName(status.getName().toLowerCase());

        FlexBoxLayout chartContainer = new FlexBoxLayout(chart, textContainer);
        chartContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        chartContainer
                .setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        chartContainer.setPosition(Position.RELATIVE);
        chartContainer.setHeight("120px");
        chartContainer.setWidth("120px");

        FlexBoxLayout column = new FlexBoxLayout(new Label(status.getName()),
                chartContainer);
        column.setAlignItems(FlexComponent.Alignment.CENTER);
        column.setFlexDirection(FlexDirection.COLUMN);
        column.setPadding(Bottom.S, Top.M);
        return column;
    }

    private Component createReports() {
        Component header = createHeader(VaadinIcon.RECORDS, "Reports");

        Tabs tabs = new Tabs();
        for (String label : new String[] { "All", "Archive", "Workflows",
                "Support" }) {
            tabs.add(new Tab(label));
        }

        FlexBoxLayout items = new FlexBoxLayout(
                new ListItem(
                        UIUtils.createIcon(IconSize.M, TextColor.TERTIARY,
                                VaadinIcon.CHART),
                        "Weekly Report", "Generated Oct 5, 2018",
                        createInfoButton()),
                new ListItem(
                        UIUtils.createIcon(IconSize.M, TextColor.TERTIARY,
                                VaadinIcon.SITEMAP),
                        "Payment Workflows", "Last modified Oct 24, 2018",
                        createInfoButton()));
        items.setFlexDirection(FlexDirection.COLUMN);
        items.setMargin(Vertical.S);

        FlexBoxLayout card = new FlexBoxLayout(tabs, items);
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
        card.setBorderRadius(BorderRadius.S);
        card.setFlexDirection(FlexDirection.COLUMN);
        card.setShadow(Shadow.S);

        Div section = new Div(header, card);
        section.addClassName(LumoStyles.Spacing.Bottom.L);
        return section;
    }

    private Component createLogs() {
        Component header = createHeader(VaadinIcon.EDIT, "Logs");

        Tabs tabs = new Tabs();
        for (String label : new String[] { "All", "Transfer", "Security",
                "Change" }) {
            tabs.add(new Tab(label));
        }

        FlexBoxLayout items = new FlexBoxLayout(
                new ListItem(
                        UIUtils.createIcon(IconSize.M, TextColor.TERTIARY,
                                VaadinIcon.EXCHANGE),
                        "Transfers (October)", "Generated Oct 31, 2018",
                        createInfoButton()),
                new ListItem(
                        UIUtils.createIcon(IconSize.M, TextColor.TERTIARY,
                                VaadinIcon.SHIELD),
                        "Security Log", "Updated 16:31 CET",
                        createInfoButton()));
        items.setFlexDirection(FlexDirection.COLUMN);
        items.setMargin(Vertical.S);

        FlexBoxLayout card = new FlexBoxLayout(tabs, items);
        card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
        card.setBorderRadius(BorderRadius.S);
        card.setFlexDirection(FlexDirection.COLUMN);
        card.setShadow(Shadow.S);

        Div section = new Div(header, card);
        section.addClassName(LumoStyles.Spacing.Bottom.L);
        return section;
    }

    private Button createInfoButton() {
        Button infoButton = UIUtils.createSmallButton(VaadinIcon.INFO);
        infoButton.addClickListener(
                e -> UIUtils.showNotification("Not implemented yet."));
        return infoButton;
    }
}
