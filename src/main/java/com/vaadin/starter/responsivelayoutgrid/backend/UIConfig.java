package com.vaadin.starter.responsivelayoutgrid.backend;

public class UIConfig {

	private UIConfig() {

	}

	public enum NavigationMode {
		LINKS, TABS
	}

	public static NavigationMode getNavigationMode() {
		return NavigationMode.TABS;
	}

}
