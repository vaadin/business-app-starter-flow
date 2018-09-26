package com.vaadin.starter.applayout.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

import java.util.ArrayList;

public abstract class NaviDrawer extends Div implements AfterNavigationObserver {

    private final String CLASS_NAME = "navi-drawer";
    private final String OPEN = CLASS_NAME + "--open";
    private final String RAIL = CLASS_NAME + "--rail";

    private Div scrim;
    private Div content;
    private Div scrollArea;

    private Image avatar;
    private H4 username;
    private Label email;
    private Button dropdown;

    protected Div list;
    private ArrayList<NaviItem> items;

    private final Button railButton = UIUtils.createSmallButton(VaadinIcon.CARET_LEFT, "Collapse");

    public NaviDrawer() {
        setClassName(CLASS_NAME);
        init();
    }

    private void init() {
        // Backdrop on small viewports.
        scrim = new Div();
        scrim.setClassName(CLASS_NAME + "__scrim");
        scrim.addClickListener(event -> close());
        add(scrim);

        // Main content.
        content = new Div();
        content.setClassName(CLASS_NAME + "__content");
        add(content);

        // Scrollable area.
        scrollArea = new Div();
        scrollArea.setClassName(CLASS_NAME + "__scroll-area");
        content.add(scrollArea);

        // Header: account switcher or brand logo.
        if (UIConfig.getNaviHeader().equals(UIConfig.NaviHeader.ACCOUNT_SWITCHER)) {
            initAccountSwitcher();
        } else {
            initBrandExpression();
        }

        // Search field.
        TextField search = new TextField();
        search.setPlaceholder("Search");
        scrollArea.add(search);

        // Wrapper for navigation items.
        list = new Div();
        list.setClassName(CLASS_NAME + "__list");
        scrollArea.add(list);

        items = new ArrayList<>();

        // "Footer", currently only a collapse/expand button.
        railButton.setClassName(CLASS_NAME + "__footer");
        railButton.addClickListener(event -> setRailModeEnabled(getClassName().contains(RAIL)));
        content.add(railButton);
    }

    private void initAccountSwitcher() {
        scrollArea.add(new AccountSwitcher());
    }

    private void initBrandExpression() {
        scrollArea.add(new BrandExpression());
    }

    private void setRailModeEnabled(boolean enabled) {
        if (enabled) {
            removeClassName(RAIL);
            railButton.setIcon(new Icon(VaadinIcon.CARET_LEFT));
        } else {
            addClassName(RAIL);
            railButton.setIcon(new Icon(VaadinIcon.CARET_RIGHT));
        }
    }

    public void toggle() {
        if (getClassName().contains(OPEN)) {
            close();
        } else {
            open();
        }
    }

    private void open() {
        addClassName(OPEN);
    }

    private void close() {
        removeClassName(OPEN);
    }

    protected void addNaviItem(NaviItem item) {
        list.add(item);
        items.add(item);
    }

    protected void addNaviItem(NaviItem parent, NaviItem item) {
        parent.addSubItem(item);
        addNaviItem(item);
    }

    public abstract NaviItem addNaviItem(VaadinIcon icon, String text, Class<? extends Component> navigationTarget);

    public abstract NaviItem addNaviItem(NaviItem parent, String text, Class<? extends Component> navigationTarget);

    public ArrayList<NaviItem> getNaviItems() {
        return items;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        close();
    }
}
