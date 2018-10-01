package com.vaadin.starter.applayout.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

import java.util.ArrayList;
import java.util.Collections;

public abstract class NaviDrawer extends Div implements AfterNavigationObserver {

    private final String CLASS_NAME = "navi-drawer";
    private final String OPEN = CLASS_NAME + "--open";
    private final String RAIL = CLASS_NAME + "--rail";

    private Div scrim;
    private Div content;
    private Div scrollArea;

    protected Div list;
    private ArrayList<NaviItem> items;

    private Button railButton;

    public NaviDrawer() {
        setClassName(CLASS_NAME);
        init();
    }

    private void init() {
        // Backdrop on small viewports.
        scrim = UIUtils.createDiv(Collections.singleton(CLASS_NAME + "__scrim"));
        scrim.addClickListener(event -> close());
        add(scrim);

        // Main content.
        content = UIUtils.createDiv(Collections.singleton(CLASS_NAME + "__content"));
        add(content);

        // Scrollable area.
        scrollArea = UIUtils.createDiv(Collections.singleton(CLASS_NAME + "__scroll-area"));
        content.add(scrollArea);

        // Header: account switcher or brand logo.
        if (UIConfig.getNaviHeader().equals(UIConfig.NaviHeader.ACCOUNT_SWITCHER)) {
            scrollArea.add(new AccountSwitcher());
        } else {
            scrollArea.add(new BrandExpression());
        }

        // Search field.
        TextField search = new TextField();
        search.setPlaceholder("Search");
        scrollArea.add(search);

        // Wrapper for navigation items.
        list = UIUtils.createDiv(Collections.singleton(CLASS_NAME + "__list"));
        scrollArea.add(list);

        items = new ArrayList<>();

        // "Footer", currently only a collapse/expand button.
        railButton = UIUtils.createSmallButton(Collections.singleton(CLASS_NAME + "__footer"), VaadinIcon.CARET_LEFT, "Collapse");
        railButton.addClickListener(event -> setRailModeEnabled(getClassName().contains(RAIL)));
        content.add(railButton);
    }

    private void setRailModeEnabled(boolean enabled) {
        if (enabled) {
            removeClassName(RAIL);
            railButton.setIcon(new Icon(VaadinIcon.CHEVRON_LEFT_SMALL));
        } else {
            addClassName(RAIL);
            railButton.setIcon(new Icon(VaadinIcon.CHEVRON_RIGHT_SMALL));
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
