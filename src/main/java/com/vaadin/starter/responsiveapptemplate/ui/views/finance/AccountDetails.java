package com.vaadin.starter.responsiveapptemplate.ui.views.finance;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.html.Div;
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
import com.vaadin.starter.responsiveapptemplate.backend.BankAccount;
import com.vaadin.starter.responsiveapptemplate.backend.DummyData;
import com.vaadin.starter.responsiveapptemplate.backend.UIConfig;
import com.vaadin.starter.responsiveapptemplate.ui.Root;
import com.vaadin.starter.responsiveapptemplate.ui.components.ListItem;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.responsiveapptemplate.ui.utils.*;
import com.vaadin.starter.responsiveapptemplate.ui.views.ViewFrame;

import java.time.LocalDate;
import java.util.Arrays;

@Route(value = "account-details", layout = Root.class)
@PageTitle("Account Details")
public class AccountDetails extends ViewFrame implements HasUrlParameter<Long> {

	public static final int RECENT_TRANSACTIONS = 4;
	private AppBar appBar;
	private final Div viewport;
	private ListItem availability;
	private ListItem bankAccount;
	private ListItem updated;

	public AccountDetails() {
		// Header
		if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
			appBar = new AppBar("Details");
			appBar.setNaviMode(AppBar.NaviMode.CONTEXTUAL);
			appBar.setContextNaviIcon(new Icon(VaadinIcon.ARROW_BACKWARD));
			appBar.getContextNaviIcon().addClickListener(e -> UI.getCurrent().navigate("accounts"));
			setHeader(appBar);
		}

		// Content
		Label monthlyOverviewHeader = UIUtils.createH3Label("Monthly Overview");
		monthlyOverviewHeader.addClassNames(LumoStyles.Margin.Vertical.L, LumoStyles.Margin.Responsive.Horizontal.ML);

		viewport = UIUtils.createDiv(
				Arrays.asList(LumoStyles.Margin.Horizontal.AUTO, LumoStyles.Margin.Responsive.Vertical.ML),
				createLogoSection(),
				createRecentTransactionsHeader(),
				createRecentTransactionsList(),
				monthlyOverviewHeader,
				createTransactionsChart()
		);
		viewport.getStyle().set(CSSProperties.MaxWidth.PROPERTY, CSSProperties.MaxWidth._800);
		setContent(viewport);
	}

	@Override
	public void setParameter(BeforeEvent beforeEvent, Long id) {
		BankAccount account = DummyData.getBankAccount(id);

		if (appBar != null) {
			appBar.setTitle(account.getOwner());
		}

		UI.getCurrent().getPage().setTitle(account.getOwner());

		availability.setPrimaryText(UIUtils.formatAmount(account.getAvailability()));
		bankAccount.setPrimaryText(account.getAccount());
		bankAccount.setSecondaryText(account.getBank());
		updated.setPrimaryText(UIUtils.formatDate(account.getUpdated()));
	}

	private Component createLogoSection() {
		Image image = new Image(UIUtils.IMG_PATH + "sample-logo1.jpg", "");
		image.getStyle().set(CSSProperties.BorderRadius.PROPERTY, "100%");
		image.addClassName(LumoStyles.Margin.Horizontal.L);
		image.setHeight("200px");
		image.setWidth("200px");

		availability = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.DOLLAR), "", "Availability");
		availability.addPrimaryClassNames(LumoStyles.Header.H2);
		availability.setDividerVisible(true);
		availability.setReverse(true);

		bankAccount = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.INSTITUTION), "", "");
		bankAccount.setDividerVisible(true);
		bankAccount.setReverse(true);
		bankAccount.getStyle().set(CSSProperties.WhiteSpace.PROPERTY, CSSProperties.WhiteSpace.PRE_LINE);

		updated = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CALENDAR), "", "Updated");
		updated.setReverse(true);

		FlexLayout listItems = UIUtils.createColumn(availability, bankAccount, updated);
		listItems.getStyle().set(CSSProperties.Flex.PROPERTY, "1");

		FlexLayout section = UIUtils.createWrappingFlexLayout(Arrays.asList(LumoStyles.Padding.Bottom.L, BoxShadowBorders.BOTTOM), image, listItems);
		section.setAlignItems(FlexComponent.Alignment.CENTER);
		section.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

		return section;
	}

	private FlexLayout createRecentTransactionsHeader() {
		Button viewAll = UIUtils.createSmallButton("View All");
		viewAll.addClickListener(e -> UIUtils.showNotification("Not implemented yet."));
		viewAll.getStyle().set(CSSProperties.Margin.PROPERTY, "0 0 0 auto");

		FlexLayout header = UIUtils.createFlexLayout(
				Arrays.asList(
						LumoStyles.Margin.Bottom.M,
						LumoStyles.Margin.Responsive.Horizontal.ML,
						LumoStyles.Margin.Top.L
				),
				UIUtils.createH3Label("Recent Transactions"),
				viewAll
		);
		header.setAlignItems(FlexComponent.Alignment.CENTER);

		return header;
	}

	private Div createRecentTransactionsList() {
		Div div = UIUtils.createDiv(Arrays.asList(LumoStyles.Padding.Bottom.L, BoxShadowBorders.BOTTOM));

		for (int i = 0; i < RECENT_TRANSACTIONS; i++) {
			Double amount = DummyData.getAmount();

			Label amountLabel = UIUtils.createH4Label(UIUtils.formatAmount(amount));
			amountLabel.addClassName(amount > 0 ? TextColor.SUCCESS.getStyle() : TextColor.ERROR.getStyle());

			ListItem item = new ListItem(DummyData.getLogo(), DummyData.getCompany(), UIUtils.formatDate(LocalDate.now().minusDays(i)), amountLabel);

			// Dividers for all but the last item. Last item has some bottom margin.
			if (i < RECENT_TRANSACTIONS - 1) {
				item.setDividerVisible(true);
			}

			div.add(item);
		}

		return div;
	}

	private Component createTransactionsChart() {
		Chart chart = new Chart(ChartType.COLUMN);

		Configuration conf = chart.getConfiguration();
		conf.setTitle("");
		conf.getLegend().setEnabled(true);

		XAxis xAxis = new XAxis();
		xAxis.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
		conf.addxAxis(xAxis);

		conf.getyAxis().setTitle("Amount ($)");

		// Withdrawals and deposits
		ListSeries withDrawals = new ListSeries("Withdrawals");
		ListSeries deposits = new ListSeries("Deposits");

		for (int i = 0; i < 8; i++) {
			withDrawals.addData(DummyData.getRandomInt(5000, 10000));
			deposits.addData(DummyData.getRandomInt(5000, 10000));
		}

		conf.addSeries(withDrawals);
		conf.addSeries(deposits);

		FlexLayout card = UIUtils.createWrappingFlexLayout(
				Arrays.asList(
						LumoStyles.BorderRadius.S,
						LumoStyles.Padding.Horizontal.M,
						LumoStyles.Padding.Top.L,
						LumoStyles.Shadow.S
				),
				chart
		);
		card.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.BASE_COLOR);
		card.getStyle().set(CSSProperties.BoxSizing.PROPERTY, CSSProperties.BoxSizing.BORDER_BOX);
		card.setHeight("400px");
		return card;
	}
}
