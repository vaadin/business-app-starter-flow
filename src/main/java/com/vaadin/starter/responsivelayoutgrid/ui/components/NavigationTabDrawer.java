package com.vaadin.starter.responsivelayoutgrid.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;

public class NavigationTabDrawer extends NavigationDrawer {

	public NavigationTabDrawer() {
		super();
	}

	public NavigationTabItem addNavigationItem(VaadinIcon icon, String text, Class<? extends Component> navigationTarget) {
		NavigationTabItem item = new NavigationTabItem(icon, text, navigationTarget);
		addNavigationItem(item);
		return item;
	}

	public NavigationTabItem addNavigationItem(NavigationItem parent, String text, Class<? extends Component> navigationTarget) {
		NavigationTabItem item = new NavigationTabItem(text, navigationTarget);
		addNavigationItem(parent, item);
		return item;
	}
}
