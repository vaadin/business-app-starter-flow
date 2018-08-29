package com.vaadin.starter.responsivelayoutgrid.ui.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.shared.Registration;
import com.vaadin.starter.responsivelayoutgrid.ui.utils.LumoStyles;
import com.vaadin.starter.responsivelayoutgrid.ui.utils.UIUtils;

public class AppBar extends FlexLayout implements AfterNavigationObserver {

	private final String CLASS_NAME = "app-bar";

	private FlexLayout container;

	private Button menuNavigationIcon;
	private Button contextualNavigationIcon;

	private H4 title;
	private FlexLayout actionItems;

	private Tabs tabs;

	private Registration searchRegistration;
	private TextField search;


	public enum NavigationMode {
		MENU, CONTEXTUAL
	}

	public AppBar(String title) {
		super();
		setClassName(CLASS_NAME);
		getElement().setAttribute(LumoStyles.THEME, LumoStyles.DARK);

		menuNavigationIcon = UIUtils.createSmallTertiaryIconButton(VaadinIcon.MENU);
		menuNavigationIcon.setClassName(CLASS_NAME + "__navigation-icon");

		contextualNavigationIcon = UIUtils.createSmallTertiaryIconButton(VaadinIcon.ARROW_BACKWARD);
		contextualNavigationIcon.setClassName(CLASS_NAME + "__navigation-icon");
		contextualNavigationIcon.addClassName(CLASS_NAME + "__navigation-icon--visible");
		contextualNavigationIcon.setVisible(false);

		this.title = new H4(title);
		this.title.setClassName(CLASS_NAME + "__title");

		search = new TextField();
		search.setPlaceholder("Search");
		search.setVisible(false);

		actionItems = new FlexLayout();
		actionItems.setClassName(CLASS_NAME + "__action-items");
		actionItems.setVisible(false);

		/*
		avatar = new Image();
		avatar.setClassName(CLASS_NAME + "__avatar");
		avatar.setSrc("https://pbs.twimg.com/profile_images/1009479665705074688/0oLHVbs8_400x400.jpg");
		*/

		container = new FlexLayout(menuNavigationIcon, contextualNavigationIcon, this.title, search, actionItems);
		container.setAlignItems(Alignment.BASELINE);
		container.setClassName(CLASS_NAME + "__container");
		container.setFlexGrow(1, search);
		add(container);

		// Row 2
		tabs = new Tabs();
		tabs.setVisible(false);
		tabs.setClassName(CLASS_NAME + "__tabs");
		tabs.getElement().setAttribute("overflow", "end");
		add(tabs);
	}

	public void setNavigationMode(NavigationMode mode) {
		if (mode.equals(NavigationMode.MENU)) {
			menuNavigationIcon.setVisible(true);
			contextualNavigationIcon.setVisible(false);
		} else {
			menuNavigationIcon.setVisible(false);
			contextualNavigationIcon.setVisible(true);
		}
	}

	public Button getMenuNavigationIcon() {
		return menuNavigationIcon;
	}

	public Button getContextualNavigationIcon() {
		return contextualNavigationIcon;
	}

	public void setContextualNavigationIcon(VaadinIcon icon) {
		contextualNavigationIcon.setIcon(new Icon(icon));
	}

	public String getTitle() {
		return this.title.getText();
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}

	@Override
	public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
		// TODO: what's the best way to update the title when a navigation change occurs?
		setTitle(UI.getCurrent().getInternals().getTitle());
	}

	private Tab addTab(Tab tab) {
		tab.setClassName(CLASS_NAME + "__tab");
		tabs.add(tab);
		return tab;
	}

	public Tab addTab(String text) {
		return addTab(new Tab(text));
	}

	public Tab addClosableTab(String text) {
		Tab tab = addTab(new Tab(text));

		Button close = UIUtils.createSmallTertiaryIconButton(VaadinIcon.CLOSE);
		close.addClickListener(event -> tabs.remove(tab));
		tab.add(close);

		return tab;
	}

	public void setTabsVisible(boolean visible) {
		tabs.setVisible(visible);
	}

	public void removeAllTabs() {
		tabs.removeAll();
	}

	public Button addActionItem(VaadinIcon icon) {
		Button button = UIUtils.createSmallTertiaryIconButton(icon);
		actionItems.add(button);
		return button;
	}

	public void setActionItemsVisible(boolean visible) {
		actionItems.setVisible(visible);
	}

	public void removeAllActionItems() {
		actionItems.removeAll();
	}

	public void searchModeOn() {
		menuNavigationIcon.setVisible(false);
		title.setVisible(false);
		actionItems.setVisible(false);
		tabs.setVisible(false);

		contextualNavigationIcon.setIcon(new Icon(VaadinIcon.ARROW_BACKWARD));
		contextualNavigationIcon.setVisible(true);
		searchRegistration = contextualNavigationIcon.addClickListener(e -> searchModeOff());

		search.setVisible(true);
		search.focus();

		container.setAlignItems(Alignment.CENTER);
	}

	private void searchModeOff() {
		menuNavigationIcon.setVisible(true);
		title.setVisible(true);
		actionItems.setVisible(true);
		tabs.setVisible(true);

		contextualNavigationIcon.setVisible(false);
		searchRegistration.remove();

		search.setVisible(false);

		container.setAlignItems(Alignment.BASELINE);
	}

	public void reset() {
		setNavigationMode(AppBar.NavigationMode.MENU);

		removeAllTabs();
		setTabsVisible(false);

		removeAllActionItems();
		setActionItemsVisible(false);
	}
}
