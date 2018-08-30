package com.vaadin.starter.responsivelayoutgrid.ui.components;

import com.vaadin.flow.component.icon.VaadinIcon;

public class NavigationTabDrawer extends NavigationDrawer {

	public NavigationTabDrawer() {
		super();
	}

	public NavigationTabItem addNavigationItem(VaadinIcon icon, String text) {
		NavigationTabItem item = new NavigationTabItem(icon, text);
		addNavigationItem(item);
		return item;
	}

	public NavigationTabItem addNavigationItem(NavigationItem parent, String text) {
		NavigationTabItem item = new NavigationTabItem(text);
		addNavigationItem(parent, item);
		return item;
	}
}
