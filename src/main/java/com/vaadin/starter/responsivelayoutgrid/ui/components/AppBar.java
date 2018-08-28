package com.vaadin.starter.responsivelayoutgrid.ui.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
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
	private Button menuNavigationIcon;
	private Button contextualNavigationIcon;
	private H4 title;
	private Tabs tabs;
	private final FlexLayout actionItems;
	private final FlexLayout container;
	private TextField search;
	private Registration searchRegistration;

	public enum NavigationMode {
		MENU, CONTEXTUAL
	}

	public AppBar(String title) {
		super();
		setClassName(CLASS_NAME);
		getElement().setAttribute(LumoStyles.THEME, LumoStyles.DARK);

		menuNavigationIcon = UIUtils.createSmallIconButton(VaadinIcon.MENU);
		menuNavigationIcon.setClassName(CLASS_NAME + "__navigation-icon");

		contextualNavigationIcon = UIUtils.createSmallIconButton(VaadinIcon.ARROW_BACKWARD);
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

		container = new FlexLayout(menuNavigationIcon, contextualNavigationIcon, this.title, search, actionItems);
		container.setAlignItems(Alignment.BASELINE);
		container.setClassName(CLASS_NAME + "__container");
		container.setFlexGrow(1, search);
		add(container);

		// Row 2
		tabs = new Tabs();
		tabs.setVisible(false);
		tabs.setClassName(CLASS_NAME + "__tabs");
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
		Button button = UIUtils.createSmallIconButton(icon);
		actionItems.add(button);
		return button;
	}

	public void setActionItemsVisible(boolean visible) {
		actionItems.setVisible(true);
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
}
