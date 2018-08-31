package com.vaadin.starter.responsivelayoutgrid.backend;

public class UIConfig {

	private UIConfig() {

	}

	public enum NavigationMode {
		LINKS, TABS
	}

	public enum NavigationHeader {
		ACCOUNT_SWITCHER, BRAND_EXPRESSION
	}

	public static NavigationMode getNavigationMode() {
		return NavigationMode.TABS;
	}

	public static NavigationHeader getNavigationHeader() {
		return NavigationHeader.ACCOUNT_SWITCHER;
	}

}
