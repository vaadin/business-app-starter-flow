package com.vaadin.starter.applayout.ui.views;

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
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.AbstractView;
import com.vaadin.starter.applayout.ui.components.AppBar;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.utils.BoxShadowBorders;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

@Route(value = "report-details", layout = Root.class)
@PageTitle("Report Details")
public class ReportDetailsView extends AbstractView implements HasUrlParameter<Long> {

    private Random random;

    private AppBar appBar;
    private Div viewport;
    private Image image;
    private ListItem balance;
    private ListItem runningDate;
    private ListItem status;
    private DateTimeFormatter formatter;

    public ReportDetailsView() {
        formatter = DateTimeFormatter.ofPattern("MMM dd, YYYY");
        random = new Random();
        Integer randBalance = random.nextInt(5000);

        // Header
        appBar = new AppBar("Details");
        appBar.setNaviMode(AppBar.NaviMode.CONTEXTUAL);
        appBar.setContextNaviIcon(new Icon(VaadinIcon.ARROW_BACKWARD));
        appBar.getContextNaviIcon().addClickListener(e -> UI.getCurrent().navigate("reports"));

        // Logo section
        image = new Image("", "");
        image.getStyle().set(CSSProperties.BorderRadius.PROPERTY, "100%");
        image.setHeight("240px");
        image.setWidth("240px");

        balance = new ListItem(VaadinIcon.MONEY, "", "Current balance");
        balance.setDividerVisible(true);

        runningDate = new ListItem(VaadinIcon.CALENDAR, "", "Date Range");
        runningDate.setDividerVisible(true);

        status = new ListItem(VaadinIcon.LOCK, "", "Status");

        FlexLayout column = UIUtils.createColumn(balance, runningDate, status);
        column.getStyle().set(CSSProperties.Flex.PROPERTY, "1");

        FlexLayout row = UIUtils.createWrappingFlexLayout(Arrays.asList(LumoStyles.Padding.Bottom.L, BoxShadowBorders.BOTTOM), image, column);
        row.setAlignItems(FlexComponent.Alignment.CENTER);
        row.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        // Transactions
        FlexLayout transactions = UIUtils.createWrappingFlexLayout(
                Arrays.asList(LumoStyles.Padding.Bottom.L, BoxShadowBorders.BOTTOM),
                createLargeListItem(VaadinIcon.PLUS, Math.round((randBalance * 0.4)) + ".00 EUR", "14 deposits"),
                createLargeListItem(VaadinIcon.MINUS, Math.round((randBalance * 0.6)) + ".00 EUR", "9 withdrawals"),
                createLargeListItem(VaadinIcon.PLUS_MINUS, (randBalance) + ".00 EUR", "23 in total")
        );

        // Pending events
        FlexLayout pending = UIUtils.createWrappingFlexLayout(
                Collections.singleton(LumoStyles.Padding.Bottom.XL),
                createLargeListItem(VaadinIcon.TIMER, Integer.toString(random.nextInt(50)), "Open"),
                createLargeListItem(VaadinIcon.CHECK, Integer.toString(random.nextInt(100)), "Closed"),
                createLargeListItem(VaadinIcon.BAN, Integer.toString(random.nextInt(50)), "Failed")
        );

        // Transactions chart
        Component transactionsChart = createTransactionsChart();

        // Add it all to the viewport
        viewport = UIUtils.createDiv(
                Arrays.asList(LumoStyles.Margin.Horizontal.AUTO, LumoStyles.Margin.Responsive.Vertical.ML),
                row,
                UIUtils.createH3(Collections.singleton(LumoStyles.Margin.Responsive.Horizontal.ML), "Transactions"),
                transactions,
                UIUtils.createH3(Collections.singleton(LumoStyles.Margin.Responsive.Horizontal.ML), "Pending Events"),
                pending,
                transactionsChart
        );
        viewport.getStyle().set(CSSProperties.MaxWidth.PROPERTY, CSSProperties.MaxWidth._800);
    }

    @Override
    protected void initSlots() {
        setHeader(appBar);
        setContent(viewport);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long id) {
        Report report = DummyData.getReport(id);

        appBar.setTitle(report.getName());
        image.setSrc(report.getSource());

        balance.setPrimaryText(Double.toString(report.getBalance()) + " " + report.getCurrency());
        runningDate.setPrimaryText(report.getStartDate().format(formatter) + " - " + report.getEndDate().format(formatter));

        if (report.getStartDate().isAfter(LocalDate.now())) {
            status.setPrimaryText("Coming Soon");
        } else if (report.getEndDate().isBefore(LocalDate.now())) {
            status.setPrimaryText("Closed");
        } else {
            status.setPrimaryText("Open");
        }

        // TODO: how do we update the page title?
    }

    private ListItem createLargeListItem(VaadinIcon icon, String primary, String secondary) {
        ListItem item = new ListItem(icon, primary, secondary);

        if (icon.equals(VaadinIcon.TIMER)) {
            item.addClassName(LumoStyles.TextColor.SECONDARY);
        } else if (icon.equals(VaadinIcon.CHECK) || icon.equals(VaadinIcon.FLAG_CHECKERED)) {
            item.addClassName(LumoStyles.TextColor.SUCCESS);
        } else if (icon.equals(VaadinIcon.BAN)) {
            item.addClassName(LumoStyles.TextColor.ERROR);
        }

        item.getPrimaryLabel().addClassName(LumoStyles.FontSize.H3);
        item.getStyle().set(CSSProperties.MinWidth.PROPERTY, "200px");
        item.setWidth("33%");

        return item;
    }

    private Component createTransactionsChart() {
        Chart chart = new Chart(ChartType.COLUMN);

        Configuration conf = chart.getConfiguration();
        conf.setTitle("Recent transactions");
        conf.getLegend().setEnabled(true);

        XAxis xAxis = new XAxis();
        xAxis.setCategories(new String[]{"14.9.", "15.9.", "16.9.", "17.9.", "18.9.", "19.9.", "20.9.", "21.9."});
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
