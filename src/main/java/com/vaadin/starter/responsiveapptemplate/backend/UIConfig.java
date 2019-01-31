package com.vaadin.starter.responsiveapptemplate.backend;

public class UIConfig {

	private UIConfig() {

	}

	public enum NaviMode {
		LINKS, TABS
	}

	public enum NaviHeader {
		ACCOUNT_SWITCHER, BRAND_EXPRESSION
	}

	public enum Showcase {
		DEMO, FINANCE, INVENTORY
	}

	public static NaviMode getNaviMode() {
		return NaviMode.LINKS;
	}

	public static NaviHeader getNaviHeader() {
		return NaviHeader.ACCOUNT_SWITCHER;
	}

	public static Showcase getShowcase() {
		return Showcase.DEMO;
	}

}
