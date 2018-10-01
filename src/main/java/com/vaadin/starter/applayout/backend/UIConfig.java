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
        return NaviMode.TABS;
    }

    public static NaviHeader getNaviHeader() {
        return NaviHeader.BRAND_EXPRESSION;
    }

}
