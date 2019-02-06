package com.vaadin.starter.responsiveapptemplate.ui.views.demo;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.responsiveapptemplate.backend.DummyData;
import com.vaadin.starter.responsiveapptemplate.backend.UIConfig;
import com.vaadin.starter.responsiveapptemplate.ui.Root;
import com.vaadin.starter.responsiveapptemplate.ui.components.ListItem;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.responsiveapptemplate.ui.layout.FlexBoxLayout;
import com.vaadin.starter.responsiveapptemplate.ui.layout.FlexDirection;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Bottom;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Horizontal;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Right;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Top;
import com.vaadin.starter.responsiveapptemplate.ui.utils.*;
import com.vaadin.starter.responsiveapptemplate.ui.views.ViewFrame;

import java.util.Arrays;
import java.util.Collections;

@Route(value = "dashboard", layout = Root.class)
@PageTitle("Dashboard")
public class Dashboard extends ViewFrame {

	private static final String CLASS_NAME = "dashboard";

	public Dashboard() {
		if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
			setViewHeader(new AppBar("Dashboard"));
		}

		setViewContent(createViewport());
	}

	private Component createViewport() {
		Component progressHeader = createHeader(VaadinIcon.CHECK, "Progress");
		Component progressChart = createProgressCharts();

		Component salesHeader = createHeader(VaadinIcon.TRENDING_UP, "Sales");
		Component salesChart = UIUtils.createSalesChart("Product sales for 2018", "Items Sold");

		Component bookmarks = createBookmarks();
		Component recentItems = createRecentItems();
		FlexLayout items = new FlexLayout(bookmarks, recentItems);
		items.addClassName(CLASS_NAME + "__bookmarks-recent-items");

		FlexBoxLayout viewport = new FlexBoxLayout(progressHeader, progressChart, salesHeader, salesChart, items);
		viewport.addClassName(CLASS_NAME);
		viewport.setFlexDirection(FlexDirection.COLUMN);
		viewport.setMargin(Horizontal.AUTO);
		viewport.setMaxWidth(CSSProperties.MaxWidth._1024);
		viewport.setPadding(Bottom.L, Horizontal.RESPONSIVE_L);
		return viewport;
	}

	private Component createHeader(VaadinIcon icon, String title) {
		FlexBoxLayout header = new FlexBoxLayout(
				UIUtils.createIcon(IconSize.S, TextColor.TERTIARY, icon),
				UIUtils.createH3Label(title)
		);
		header.setAlignItems(FlexComponent.Alignment.CENTER);
		header.setMargin(Bottom.L, Horizontal.RESPONSIVE_L, Top.XL);
		header.setSpacing(Right.M);
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

	private Component createBookmarks() {
		Component header = createHeader(VaadinIcon.BOOKMARK, "Bookmarks");
		Component card = createTabbedList();
		return new Div(header, card);
	}

	private Component createRecentItems() {
		Component header = createHeader(VaadinIcon.TIME_BACKWARD, "Recent Items");
		Component card = createTabbedList();
		return new Div(header, card);
	}

	private Component createTabbedList() {
		Tabs tabs = new Tabs();

		for (String label : new String[]{"All", "Archive", "Workflows", "Support"}) {
			tabs.add(new Tab(label));
		}

		FlexLayout items = UIUtils.createColumn(
				Collections.singleton(LumoStyles.Margin.Vertical.S),
				new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CHART), "My Weekly Report", "Last opened May 5, 2018", createInfoButton()),
				new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.SITEMAP), "My Workflow", "Last opened May 5, 2018", createInfoButton())
		);

		FlexLayout card = UIUtils.createColumn(Arrays.asList(LumoStyles.BorderRadius.S, LumoStyles.Shadow.S), tabs, items);
		card.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.BASE_COLOR);
		return card;
	}

	private Button createInfoButton() {
		Button infoButton = UIUtils.createSmallButton(VaadinIcon.INFO);
		infoButton.addClickListener(e -> UIUtils.showNotification("Not implemented yet."));
		return infoButton;
	}
}
