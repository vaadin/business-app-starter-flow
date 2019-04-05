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

    public static NaviHeader getNaviHeader() {
        return NaviHeader.BRAND_EXPRESSION;
    }

}
