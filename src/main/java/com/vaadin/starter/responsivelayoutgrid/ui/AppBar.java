package com.vaadin.starter.responsivelayoutgrid.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;

public class AppBar extends FlexLayout implements AfterNavigationObserver {

	private final String CLASS_NAME = "app-bar";
	private Button navigationIcon;
	private H4 title;
	private Tabs tabs;
	private final FlexLayout actionItems;

	public AppBar(String title) {
		super();
		setClassName(CLASS_NAME);
		getElement().setAttribute(LumoStyles.THEME, LumoStyles.DARK);

		// Two rows:
		// 1) navigation icon, title and action items
		// 2) tabs

		// Row 1
		navigationIcon = new Button(new Icon(VaadinIcon.MENU));
		navigationIcon.setClassName(CLASS_NAME + "__navigation-icon");
		navigationIcon.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.ICON_SMALL);

		this.title = new H4(title);
		this.title.setClassName(CLASS_NAME + "__title");

		actionItems = new FlexLayout();
		actionItems.setClassName(CLASS_NAME + "__action-items");
		actionItems.setVisible(false);

		FlexLayout container = new FlexLayout(navigationIcon, this.title, actionItems);
		container.setAlignItems(Alignment.BASELINE);
		container.setClassName(CLASS_NAME + "__container");
		add(container);

		// Row 2
		tabs = new Tabs();
		tabs.setVisible(false);
		tabs.setClassName(CLASS_NAME + "__tabs");
		add(tabs);
	}

	public Button getNavigationIcon() {
		return navigationIcon;
	}

	public void setNavigationIcon(VaadinIcon icon) {
		navigationIcon.setIcon(new Icon(icon));
	}

	public void resetNavigationIcon() {
		navigationIcon.setIcon(new Icon(VaadinIcon.MENU));
		navigationIcon.removeClassName(CLASS_NAME + "__navigation-icon--visible");
	}

	/**
	 * The navigation icon is hidden by default on large viewports. Setting its visibility to true will override that
	 * behaviour.
	 */
	public void setNavigationIconVisible(boolean visible) {
		if (visible) {
			navigationIcon.addClassName(CLASS_NAME + "__navigation-icon--visible");
		} else {
			navigationIcon.removeClassName(CLASS_NAME + "__navigation-icon--visible");
		}
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

	public void addTab(Tab tab) {
		tab.setClassName(CLASS_NAME + "__tab");
		tabs.add(tab);
	}

	public void setTabsVisible(boolean visible) {
		tabs.setVisible(visible);
	}

	public void removeAllTabs() {
		tabs.removeAll();
	}

	public Button addActionItem(VaadinIcon icon) {
		Button button = new Button(new Icon(icon));
		button.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.ICON_SMALL);

		actionItems.add(button);

		return button;
	}

	public void setActionItemsVisible(boolean visible) {
		actionItems.setVisible(true);
	}

	public void removeAllActionItems() {
		actionItems.removeAll();
	}



}
