package com.vaadin.starter.responsiveapptemplate.ui.views.demo;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.responsiveapptemplate.backend.DummyData;
import com.vaadin.starter.responsiveapptemplate.backend.UIConfig;
import com.vaadin.starter.responsiveapptemplate.ui.Root;
import com.vaadin.starter.responsiveapptemplate.ui.components.ListItem;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.responsiveapptemplate.ui.layout.*;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.*;
import com.vaadin.starter.responsiveapptemplate.ui.utils.IconSize;
import com.vaadin.starter.responsiveapptemplate.ui.utils.LumoStyles;
import com.vaadin.starter.responsiveapptemplate.ui.utils.TextColor;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;
import com.vaadin.starter.responsiveapptemplate.ui.views.ViewFrame;

@Route(value = "dashboard", layout = Root.class)
@PageTitle("Dashboard")
public class Dashboard extends ViewFrame {

	private static final String CLASS_NAME = "dashboard";

	public Dashboard() {
		if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
			setViewHeader(new AppBar("Dashboard"));
		}
		setViewContent(createContent());
	}

	private Component createContent() {
		Component progressHeader = createHeader(VaadinIcon.CHECK, "Progress");
		Component progressChart = createProgressCharts();

		Component salesHeader = createHeader(VaadinIcon.TRENDING_UP, "Sales");
		Component salesChart = UIUtils.createSalesChart("Product sales for 2018", "Items Sold");

		Component bookmarks = createBookmarks();
		Component recentItems = createRecentItems();
		FlexBoxLayout items = new FlexBoxLayout(bookmarks, recentItems);
		items.addClassName(CLASS_NAME + "__bookmarks-recent-items");
		items.setSpacing(Bottom.L);

		FlexBoxLayout content = new FlexBoxLayout(progressHeader, progressChart, salesHeader, salesChart, items);
		content.setFlexDirection(FlexDirection.COLUMN);
		content.setMargin(Horizontal.AUTO, Vertical.RESPONSIVE_L);
		content.setMaxWidth("1024px");
		content.setPadding(Horizontal.RESPONSIVE_X);
		content.setSpacing(Bottom.L);
		return content;
	}

	private Component createHeader(VaadinIcon icon, String title) {
		FlexBoxLayout header = new FlexBoxLayout(
				UIUtils.createIcon(IconSize.S, TextColor.TERTIARY, icon),
				UIUtils.createH3Label(title)
		);
		header.setAlignItems(FlexComponent.Alignment.CENTER);
		header.setMargin(Horizontal.RESPONSIVE_L, Top.S);
		header.setSpacing(Right.M);
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

		for (String section : new String[]{"Today", "Week", "Month", "Year"}) {
			card.add(createProgressSection(section));
		}

		return card;
	}

	private Component createProgressSection(String title) {
		int value = DummyData.getRandomInt(50, 100);

		FlexBoxLayout textContainer = new FlexBoxLayout(
				UIUtils.createH2Label(Integer.toString(value)),
				UIUtils.createSmallLabel("%")
		);
		textContainer.setAlignItems(FlexComponent.Alignment.BASELINE);
		textContainer.setPosition(Position.ABSOLUTE);
		textContainer.setSpacing(Right.XS);

		FlexBoxLayout chartContainer = new FlexBoxLayout(UIUtils.createProgressChart(value), textContainer);
		chartContainer.setAlignItems(FlexComponent.Alignment.CENTER);
		chartContainer.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
		chartContainer.setPosition(Position.RELATIVE);
		chartContainer.setHeight("120px");
		chartContainer.setWidth("120px");

		FlexBoxLayout column = new FlexBoxLayout(
				new Label(title),
				chartContainer
		);
		column.setAlignItems(FlexComponent.Alignment.CENTER);
		column.setFlexDirection(FlexDirection.COLUMN);
		column.setPadding(Bottom.S, Top.M);
		return column;
	}

	private Component createBookmarks() {
		Component header = createHeader(VaadinIcon.BOOKMARK, "Bookmarks");
		Component card = createTabbedList();

		Div section = new Div(header, card);
		section.addClassName(LumoStyles.Spacing.Bottom.L);
		return section;
	}

	private Component createRecentItems() {
		Component header = createHeader(VaadinIcon.TIME_BACKWARD, "Recent Items");
		Component card = createTabbedList();

		Div section = new Div(header, card);
		section.addClassName(LumoStyles.Spacing.Bottom.L);
		return section;
	}

	private Component createTabbedList() {
		Tabs tabs = new Tabs();
		for (String label : new String[]{"All", "Archive", "Workflows", "Support"}) {
			tabs.add(new Tab(label));
		}

		FlexBoxLayout items = new FlexBoxLayout(
				new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CHART), "My Weekly Report", "Last opened May 5, 2018", createInfoButton()),
				new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.SITEMAP), "My Workflow", "Last opened May 5, 2018", createInfoButton())
		);
		items.setFlexDirection(FlexDirection.COLUMN);
		items.setMargin(Vertical.S);

		FlexBoxLayout card = new FlexBoxLayout(tabs, items);
		card.setBackgroundColor(LumoStyles.Color.BASE_COLOR);
		card.setBorderRadius(BorderRadius.S);
		card.setFlexDirection(FlexDirection.COLUMN);
		card.setShadow(Shadow.S);
		return card;
	}

	private Button createInfoButton() {
		Button infoButton = UIUtils.createSmallButton(VaadinIcon.INFO);
		infoButton.addClickListener(e -> UIUtils.showNotification("Not implemented yet."));
		return infoButton;
	}
}
