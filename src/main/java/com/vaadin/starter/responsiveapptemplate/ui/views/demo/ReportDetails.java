package com.vaadin.starter.responsiveapptemplate.ui.views.demo;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.responsiveapptemplate.backend.DummyData;
import com.vaadin.starter.responsiveapptemplate.backend.Report;
import com.vaadin.starter.responsiveapptemplate.backend.UIConfig;
import com.vaadin.starter.responsiveapptemplate.ui.Root;
import com.vaadin.starter.responsiveapptemplate.ui.components.ListItem;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.responsiveapptemplate.ui.layout.*;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Bottom;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Horizontal;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Uniform;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Vertical;
import com.vaadin.starter.responsiveapptemplate.ui.utils.*;
import com.vaadin.starter.responsiveapptemplate.ui.views.ViewFrame;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

@Route(value = "report-details", layout = Root.class)
@PageTitle("Report Details")
public class ReportDetails extends ViewFrame implements HasUrlParameter<Long> {

	private static final String CLASS_NAME = "report-details";

	private AppBar appBar;

	private FlexBoxLayout logoSection;
	private Image image;
	private ListItem balance;
	private ListItem runningDate;
	private ListItem status;

	private FlexLayout accounts;
	private FlexLayout events;

	public ReportDetails() {
		if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
			appBar = createAppBar();
			setViewHeader(appBar);
		}
		setViewContent(createContent());
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

	private AppBar createAppBar() {
		AppBar appBar = new AppBar("Details");
		appBar.setNaviMode(AppBar.NaviMode.CONTEXTUAL);
		appBar.setContextNaviIcon(new Icon(VaadinIcon.ARROW_BACKWARD));
		appBar.getContextNaviIcon().addClickListener(e -> UI.getCurrent().navigate("reports"));
		return appBar;
	}

	private Component createContent() {
		logoSection = createLogoSection();

		Label accountsHeader = createHeader("Accounts (USD)");
		accounts = createAccounts();

		Label eventsHeader = createHeader("Pending Events");
		events = createPendingEvents();

		Component chart = createTransactionsChart();

		FlexBoxLayout content = new FlexBoxLayout(logoSection, accountsHeader, accounts, eventsHeader, events, chart);
		content.setFlexDirection(FlexDirection.COLUMN);
		content.setMargin(Horizontal.AUTO, Vertical.RESPONSIVE_L);
		content.setMaxWidth(CSSProperties.MaxWidth._840);
		content.setPadding(Horizontal.RESPONSIVE_X);
		return content;
	}

	private FlexBoxLayout createLogoSection() {
		image = new Image("", "");
		image.getStyle().set(CSSProperties.BorderRadius.PROPERTY, "100%");
		image.addClassNames(LumoStyles.Margin.Horizontal.L, LumoStyles.Margin.Bottom.S);
		image.setHeight("200px");
		image.setWidth("200px");

		balance = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.MONEY), "", "Current Balance");
		balance.setDividerVisible(true);
		balance.setReverse(true);

		runningDate = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CALENDAR), "", "Date Range");
		runningDate.setDividerVisible(true);
		runningDate.setReverse(true);

		status = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.LOCK), "", "Status");
		status.setReverse(true);

		FlexLayout listItems = UIUtils.createColumn(balance, runningDate, status);

		FlexBoxLayout section = new FlexBoxLayout(image, listItems);
		section.addClassName(BoxShadowBorders.BOTTOM);
		section.setAlignItems(FlexComponent.Alignment.CENTER);
		section.setFlex("1", listItems);
		section.setFlexWrap(FlexWrap.WRAP);
		section.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
		section.setPadding(Bottom.L);
		return section;
	}

	private Label createHeader(String title) {
		Label header = UIUtils.createH6Label(title);
		header.addClassNames(
				LumoStyles.Margin.Bottom.M,
				LumoStyles.Margin.Responsive.Horizontal.ML,
				LumoStyles.Margin.Top.L
		);
		return header;
	}

	private FlexLayout createAccounts() {
		Integer amount = DummyData.getRandomInt(0, 5000);
		return UIUtils.createWrappingFlexLayout(
				Arrays.asList(LumoStyles.Padding.Bottom.L, BoxShadowBorders.BOTTOM),
				createLargeListItem(VaadinIcon.PLUS, UIUtils.formatAmount(amount * 0.4), "14 deposits"),
				createLargeListItem(VaadinIcon.MINUS, UIUtils.formatAmount(amount * 0.6), "9 withdrawals"),
				createLargeListItem(VaadinIcon.PLUS_MINUS, UIUtils.formatAmount(amount), "23 in total")
		);
	}

	private FlexLayout createPendingEvents() {
		return UIUtils.createWrappingFlexLayout(
				Collections.singleton(LumoStyles.Padding.Bottom.XL),
				createLargeListItem(VaadinIcon.TIMER, UIUtils.formatAmount(DummyData.getRandomInt(0, 50)), "Open"),
				createLargeListItem(VaadinIcon.CHECK, UIUtils.formatAmount(DummyData.getRandomInt(0, 100)), "Closed"),
				createLargeListItem(VaadinIcon.BAN, UIUtils.formatAmount(DummyData.getRandomInt(0, 50)), "Failed")
		);
	}

	private ListItem createLargeListItem(VaadinIcon icon, String primary, String secondary) {
		ListItem item = new ListItem(new Icon(icon), primary, secondary);
		item.addClassName(CLASS_NAME + "__list-item");

		if (icon.equals(VaadinIcon.CHECK) || icon.equals(VaadinIcon.FLAG_CHECKERED)) {
			item.addClassName(TextColor.SUCCESS.getClassName());

		} else if (icon.equals(VaadinIcon.BAN)) {
			item.addClassName(TextColor.ERROR.getClassName());

		} else {
			item.addClassName(TextColor.SECONDARY.getClassName());
		}

		item.addPrimaryClassNames(LumoStyles.Header.H2);

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

		FlexBoxLayout card = new FlexBoxLayout(chart);
		card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
		card.setBorderRadius(BorderRadius.S);
		card.setBoxSizing(BoxSizing.BORDER_BOX);
		card.setFlexWrap(FlexWrap.WRAP);
		card.setHeight("400px");
		card.setPadding(Uniform.M);
		card.setShadow(Shadow.S);
		return card;
	}
}
