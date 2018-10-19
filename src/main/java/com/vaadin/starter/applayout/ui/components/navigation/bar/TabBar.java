package com.vaadin.starter.applayout.ui.components.navigation.bar;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.AppLayoutUI;
import com.vaadin.starter.applayout.ui.components.navigation.tab.NaviTabs;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;
import com.vaadin.starter.applayout.ui.views.Default;

import java.util.Collections;

public class TabBar extends FlexLayout {

    private final String CLASS_NAME = "tab-bar";

    private Button menuNaviIcon;

    private FlexLayout actionItems;
    private Image avatar;

    private Button addTab;
    private NaviTabs tabs;

    public TabBar() {
        super();
        setClassName(CLASS_NAME);
        getElement().setAttribute(LumoStyles.THEME, LumoStyles.DARK);

        menuNaviIcon = UIUtils.createSmallTertiaryIconButton(Collections.singleton(CLASS_NAME + "__navi-icon"), VaadinIcon.MENU);
        menuNaviIcon.addClickListener(e -> AppLayoutUI.getNaviDrawer().toggle());

        avatar = new Image();
        avatar.setClassName(CLASS_NAME + "__avatar");
        avatar.setSrc("https://pbs.twimg.com/profile_images/2642704545/a77c0524766c6f3b4be4929f2005e627_400x400.png");
        avatar.setVisible(UIConfig.getNaviHeader().equals(UIConfig.NaviHeader.BRAND_EXPRESSION));

        ContextMenu contextMenu = new ContextMenu(avatar);
        contextMenu.setOpenOnClick(true);
        contextMenu.addItem("john.smith@gmail.com", e -> System.out.println("Testing..."));
        contextMenu.addItem("john.smith@yahoo.com", e -> System.out.println("Testing..."));
        contextMenu.addItem("Settings", e -> System.out.println("Testing..."));
        contextMenu.addItem("Logout", e -> System.out.println("Testing..."));

        actionItems = UIUtils.createFlexLayout(Collections.singleton(CLASS_NAME + "__action-items"), avatar);
        actionItems.setVisible(false);

        addTab = UIUtils.createSmallIconButton(VaadinIcon.PLUS);
        addTab.addClickListener(e -> tabs.setSelectedTab(addClosableNaviTab("New Tab", Default.class)));
        addTab.setClassName(CLASS_NAME + "__add-tab");

        tabs = new NaviTabs();
        tabs.setClassName(CLASS_NAME + "__tabs");

        add(menuNaviIcon, tabs, addTab, actionItems);
    }

    /* === MENU ICON === */

    public Button getMenuNaviIcon() {
        return menuNaviIcon;
    }

    public void setMenuNaviIconVisible(boolean visible) {
        menuNaviIcon.setVisible(visible);
    }


    /* === TABS === */

    public void centerTabs() {
        tabs.addClassName(LumoStyles.Margin.Horizontal.AUTO);
    }

    private void configureTab(Tab tab) {
        tab.addClassName(CLASS_NAME + "__tab");
    }

    public Tab addTab(String text) {
        Tab tab = tabs.addTab(text);
        configureTab(tab);
        return tab;
    }

    public Tab addNaviTab(String text, Class<? extends Component> navigationTarget) {
        Tab tab = tabs.addNaviTab(text, navigationTarget);
        configureTab(tab);
        return tab;
    }

    public Tab addClosableNaviTab(String text, Class<? extends Component> navigationTarget) {
        Tab tab = tabs.addClosableNaviTab(text, navigationTarget);
        configureTab(tab);
        return tab;
    }

    public Tab getSelectedTab() {
        return tabs.getSelectedTab();
    }

    public void setSelectedTab(Tab selectedTab) {
        tabs.setSelectedTab(selectedTab);
    }

    public void updateSelectedTab(String text, Class<? extends Component> navigationTarget) {
        tabs.updateSelectedTab(text, navigationTarget);
    }

    public void addTabSelectionListener(ComponentEventListener<Tabs.SelectedChangeEvent> listener) {
        tabs.addSelectedChangeListener(listener);
    }

    public int getTabCount() {
        return tabs.getTabCount();
    }

    public void removeAllTabs() {
        tabs.removeAll();
    }


    /* === ADD TAB BUTTON === */

    public void setAddTabVisible(boolean visible) {
        addTab.setVisible(visible);
    }


    /* === ACTION ITEMS === */

    public Component addActionItem(Component component) {
        actionItems.add(component);
        updateActionItemsVisibility();
        return component;
    }

    public Button addActionItem(Icon icon) {
        Button button = UIUtils.createSmallTertiaryIconButton(icon);
        addActionItem(button);
        return button;
    }

    public void removeAllActionItems() {
        actionItems.removeAll();
        updateActionItemsVisibility();
    }


    /* === UPDATE VISIBILITY === */

    private void updateActionItemsVisibility() {
        actionItems.setVisible(actionItems.getComponentCount() > 0);
    }
}
