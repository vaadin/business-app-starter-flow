package com.vaadin.starter.applayout.ui.views.demo;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.DummyData;
import com.vaadin.starter.applayout.backend.Report;
import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.applayout.ui.utils.BoxShadowBorders;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;
import com.vaadin.starter.applayout.ui.views.ViewFrame;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

@Route(value = "report-details", layout = Root.class)
@PageTitle("Report Details")
public class ReportDetails extends ViewFrame implements HasUrlParameter<Long> {

    private static final String CLASS_NAME = "report-details";

    private AppBar appBar;
    private Div viewport;
    private Image image;
    private ListItem balance;
    private ListItem runningDate;
    private ListItem status;

    public ReportDetails() {
        Integer amount = DummyData.getRandomInt(0, 5000);

        // Header
        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            appBar = new AppBar("Details");
            appBar.setNaviMode(AppBar.NaviMode.CONTEXTUAL);
            appBar.setContextNaviIcon(new Icon(VaadinIcon.ARROW_BACKWARD));
            appBar.getContextNaviIcon().addClickListener(e -> UI.getCurrent().navigate("reports"));
            setHeader(appBar);
        }

        // Logo section
        image = new Image("", "");
        image.getStyle().set(CSSProperties.BorderRadius.PROPERTY, "100%");
        image.addClassNames(LumoStyles.Margin.Horizontal.L, LumoStyles.Margin.Bottom.S);
        image.setHeight("200px");
        image.setWidth("200px");

        balance = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.MONEY), "", "Current Balance");
        balance.setReverse(true);
        balance.setDividerVisible(true);

        runningDate = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CALENDAR), "", "Date Range");
        runningDate.setReverse(true);
        runningDate.setDividerVisible(true);

        status = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.LOCK), "", "Status");
        status.setReverse(true);

        FlexLayout column = UIUtils.createColumn(balance, runningDate, status);
        column.getStyle().set(CSSProperties.Flex.PROPERTY, "1");

        FlexLayout row = UIUtils.createWrappingFlexLayout(Arrays.asList(LumoStyles.Padding.Bottom.L, BoxShadowBorders.BOTTOM), image, column);
        row.setAlignItems(FlexComponent.Alignment.CENTER);
        row.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        // Accounts
        FlexLayout transactions = UIUtils.createWrappingFlexLayout(
                Arrays.asList(LumoStyles.Padding.Bottom.L, BoxShadowBorders.BOTTOM),
                createLargeListItem(VaadinIcon.PLUS, UIUtils.formatAmount(amount * 0.4), "14 deposits"),
                createLargeListItem(VaadinIcon.MINUS, UIUtils.formatAmount(amount * 0.6), "9 withdrawals"),
                createLargeListItem(VaadinIcon.PLUS_MINUS, UIUtils.formatAmount(amount), "23 in total")
        );

        // Pending events
        FlexLayout pending = UIUtils.createWrappingFlexLayout(
                Collections.singleton(LumoStyles.Padding.Bottom.XL),
                createLargeListItem(VaadinIcon.TIMER, UIUtils.formatAmount(DummyData.getRandomInt(0, 50)), "Open"),
                createLargeListItem(VaadinIcon.CHECK, UIUtils.formatAmount(DummyData.getRandomInt(0, 100)), "Closed"),
                createLargeListItem(VaadinIcon.BAN, UIUtils.formatAmount(DummyData.getRandomInt(0, 50)), "Failed")
        );

        // Accounts chart
        Component transactionsChart = createTransactionsChart();

        // Add it all to the viewport
        viewport = UIUtils.createDiv(
                Arrays.asList(LumoStyles.Margin.Horizontal.AUTO, LumoStyles.Margin.Responsive.Vertical.ML),
                row,
                UIUtils.createH6Label(Arrays.asList(LumoStyles.Margin.Bottom.M, LumoStyles.Margin.Responsive.Horizontal.ML, LumoStyles.Margin.Top.L), "Accounts (USD)"),
                transactions,
                UIUtils.createH6Label(Arrays.asList(LumoStyles.Margin.Bottom.M, LumoStyles.Margin.Responsive.Horizontal.ML, LumoStyles.Margin.Top.L), "Pending Events"),
                pending,
                transactionsChart
        );
        viewport.getStyle().set(CSSProperties.MaxWidth.PROPERTY, CSSProperties.MaxWidth._800);

        setContent(viewport);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long id) {
        Report report = DummyData.getReport(id);

        if (appBar != null) {
            appBar.setTitle(report.getName());
        }

        image.setSrc(report.getSource());

        balance.setPrimaryText(UIUtils.formatAmount(report.getBalance()));
        runningDate.setPrimaryText(UIUtils.formatDate(report.getStartDate()) + " - " + UIUtils.formatDate(report.getEndDate()));

        if (report.getStartDate().isAfter(LocalDate.now())) {
            status.setPrimaryText("Coming Soon");
        } else if (report.getEndDate().isBefore(LocalDate.now())) {
            status.setPrimaryText("Closed");
        } else {
            status.setPrimaryText("Open");
        }
    }

    private ListItem createLargeListItem(VaadinIcon icon, String primary, String secondary) {
        ListItem item = new ListItem(new Icon(icon), primary, secondary);
        item.addClassName(CLASS_NAME + "__list-item");

        if (icon.equals(VaadinIcon.TIMER)) {
            item.addClassName(LumoStyles.TextColor.SECONDARY);

        } else if (icon.equals(VaadinIcon.CHECK) || icon.equals(VaadinIcon.FLAG_CHECKERED)) {
            item.addClassName(LumoStyles.TextColor.SUCCESS);

        } else if (icon.equals(VaadinIcon.BAN)) {
            item.addClassName(LumoStyles.TextColor.ERROR);
        }

        item.addPrimaryClassNames(LumoStyles.FontSize.H2);

        return item;
    }

    private Component createTransactionsChart() {
        Chart chart = new Chart(ChartType.COLUMN);

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Recent Accounts");
        conf.getLegend().setEnabled(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("14.9.", "15.9.", "16.9.", "17.9.", "18.9.", "19.9.", "20.9.", "21.9.");
        conf.addxAxis(xAxis);

        conf.getyAxis().setTitle("");

        conf.addSeries(new ListSeries("Withdrawals", 220, 100, 400, 360, 60, 660, 740, 800));
        conf.addSeries(new ListSeries("Deposits", 400, 300, 0, 440, 480, 400, 0, 0));

        FlexLayout card = UIUtils.createWrappingFlexLayout(Arrays.asList(LumoStyles.BorderRadius.S, LumoStyles.Padding.All.M, LumoStyles.Shadow.S), chart);
        card.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.BASE_COLOR);
        card.getStyle().set(CSSProperties.BoxSizing.PROPERTY, CSSProperties.BoxSizing.BORDER_BOX);
        card.setHeight("400px");
        return card;
    }
}
