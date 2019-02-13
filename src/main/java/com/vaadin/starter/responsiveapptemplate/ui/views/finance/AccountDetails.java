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
import com.vaadin.starter.responsiveapptemplate.ui.layout.*;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.*;
import com.vaadin.starter.responsiveapptemplate.ui.utils.*;
import com.vaadin.starter.responsiveapptemplate.ui.views.ViewFrame;

import java.time.LocalDate;
import java.util.Arrays;

@Route(value = "account-details", layout = Root.class)
@PageTitle("Account Details")
public class AccountDetails extends ViewFrame implements HasUrlParameter<Long> {

	public int RECENT_TRANSACTIONS = 4;

	private AppBar appBar;
	private ListItem availability;
	private ListItem bankAccount;
	private ListItem updated;

	public AccountDetails() {
		if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
			setViewHeader(createAppBar());
		}
		setViewContent(createContent());
	}

	@Override
	public void setParameter(BeforeEvent beforeEvent, Long id) {
		BankAccount account = DummyData.getBankAccount(id);
		UI.getCurrent().getPage().setTitle(account.getOwner());

		if (appBar != null) {
			appBar.setTitle(account.getOwner());
		}

		availability.setPrimaryText(UIUtils.formatAmount(account.getAvailability()));
		bankAccount.setPrimaryText(account.getAccount());
		bankAccount.setSecondaryText(account.getBank());
		updated.setPrimaryText(UIUtils.formatDate(account.getUpdated()));
	}

	private AppBar createAppBar() {
		appBar = new AppBar("Details");
		appBar.setNaviMode(AppBar.NaviMode.CONTEXTUAL);
		appBar.setContextNaviIcon(new Icon(VaadinIcon.ARROW_BACKWARD));
		appBar.getContextNaviIcon().addClickListener(e -> UI.getCurrent().navigate("accounts"));
		return appBar;
	}

	private Component createContent() {
		FlexBoxLayout content = new FlexBoxLayout(
				createLogoSection(),
				createRecentTransactionsHeader(),
				createRecentTransactionsList(),
				createMonthlyOverviewHeader(),
				createMonthlyOverviewChart()
		);
		content.setFlexDirection(FlexDirection.COLUMN);
		content.setMargin(Horizontal.AUTO, Vertical.RESPONSIVE_L);
		content.setMaxWidth(CSSProperties.MaxWidth._840);
		return content;
	}

	private FlexBoxLayout createLogoSection() {
		Image image = DummyData.getLogo();
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

		FlexBoxLayout section = new FlexBoxLayout(image, listItems);
		section.addClassName(BoxShadowBorders.BOTTOM);
		section.setAlignItems(FlexComponent.Alignment.CENTER);
		section.setFlex("1", listItems);
		section.setFlexWrap(FlexWrap.WRAP);
		section.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
		section.setPadding(Bottom.L);
		return section;
	}

	private Component createRecentTransactionsHeader() {
		Label title = UIUtils.createH3Label("Recent Transactions");

		Button viewAll = UIUtils.createSmallButton("View All");
		viewAll.addClickListener(e -> UIUtils.showNotification("Not implemented yet."));
		viewAll.addClassName(LumoStyles.Margin.Left.AUTO);

		FlexBoxLayout header = new FlexBoxLayout(title, viewAll);
		header.setAlignItems(FlexComponent.Alignment.CENTER);
		header.setMargin(Bottom.M, Horizontal.RESPONSIVE_L, Top.L);
		return header;
	}

	private Component createRecentTransactionsList() {
		Div items = UIUtils.createDiv(Arrays.asList(LumoStyles.Padding.Bottom.L, BoxShadowBorders.BOTTOM));

		for (int i = 0; i < RECENT_TRANSACTIONS; i++) {
			Double amount = DummyData.getAmount();

			Label amountLabel = UIUtils.createAmountLabel(amount);
			if (amount > 0) {
				amountLabel.addClassName(TextColor.SUCCESS.getClassName());
			} else {
				amountLabel.addClassName(TextColor.ERROR.getClassName());
			}

			ListItem item = new ListItem(DummyData.getLogo(), DummyData.getCompany(), UIUtils.formatDate(LocalDate.now().minusDays(i)), amountLabel);

			// Dividers for all but the last item
			item.setDividerVisible(i < RECENT_TRANSACTIONS - 1);
			items.add(item);
		}

		return items;
	}

	private Component createMonthlyOverviewHeader() {
		Label header = UIUtils.createH3Label("Monthly Overview");
		header.addClassNames(LumoStyles.Margin.Vertical.L, LumoStyles.Margin.Responsive.Horizontal.L);
		return header;
	}

	private Component createMonthlyOverviewChart() {
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
