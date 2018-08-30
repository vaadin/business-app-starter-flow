package com.vaadin.starter.responsivelayoutgrid.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;

public class NavigationLinkDrawer extends NavigationDrawer {

	public NavigationLinkDrawer() {
		super();
	}

	public NavigationLinkItem addNavigationItem(VaadinIcon icon, String text, Class<? extends Component> navigationTarget) {
		NavigationLinkItem item = new NavigationLinkItem(icon, text, navigationTarget);
		addNavigationItem(item);
		return item;
	}

	public NavigationLinkItem addNavigationItem(NavigationItem parent, String text, Class<? extends Component> navigationTarget) {
		NavigationLinkItem item = new NavigationLinkItem(text, navigationTarget);
		addNavigationItem(parent, item);
		return item;
	}
}
