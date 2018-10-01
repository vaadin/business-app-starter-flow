package com.vaadin.starter.applayout.backend;

public class UIConfig {

    private UIConfig() {

    }

    public enum NaviMode {
        LINKS, TABS
    }

    public enum NaviHeader {
        ACCOUNT_SWITCHER, BRAND_EXPRESSION
    }

    public static NaviMode getNaviMode() {
        return NaviMode.LINKS;
    }

    public static NaviHeader getNaviHeader() {
        return NaviHeader.BRAND_EXPRESSION;
    }

}
