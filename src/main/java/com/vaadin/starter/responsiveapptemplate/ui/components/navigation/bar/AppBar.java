package com.vaadin.starter.responsiveapptemplate.ui.components.navigation.bar;

import java.util.Collections;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;
import com.vaadin.starter.responsiveapptemplate.backend.UIConfig;
import com.vaadin.starter.responsiveapptemplate.ui.Root;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.tab.NaviTab;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.tab.NaviTabs;
import com.vaadin.starter.responsiveapptemplate.ui.utils.LumoStyles;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;
import com.vaadin.starter.responsiveapptemplate.ui.views.Home;

public class AppBar extends Composite<FlexLayout> {

    private String CLASS_NAME = "app-bar";

    private FlexLayout container;

    private Button menuNaviIcon;
    private Button contextNaviIcon;

    private H4 title;
    private FlexLayout actionItems;
    private Image avatar;

    private FlexLayout tabContainer;
    private NaviTabs naviTabs;
    private Button addTab;

    private TextField search;
    private Registration searchRegistration;

    public enum NaviMode {
        MENU, CONTEXTUAL
    }

    public AppBar(String title, NaviTab... tabs) {
        getContent().setClassName(CLASS_NAME);
        getElement().setAttribute(LumoStyles.THEME, LumoStyles.DARK);

        initMenuNaviIcon();
        initContextNaviIcon();
        initTitle(title);
        initSearch();
        initAvatar();
        initActionItems();
        initContainer();
        initTabs(tabs);
    }

    public void setNaviMode(NaviMode mode) {
        if (mode.equals(NaviMode.MENU)) {
            menuNaviIcon.setVisible(true);
            contextNaviIcon.setVisible(false);
        } else {
            menuNaviIcon.setVisible(false);
            contextNaviIcon.setVisible(true);
        }
    }

    private void initMenuNaviIcon() {
        menuNaviIcon = UIUtils.createTertiaryInlineButton(VaadinIcon.MENU);
        menuNaviIcon.removeThemeVariants(ButtonVariant.LUMO_ICON);
        menuNaviIcon.addClassName(CLASS_NAME + "__navi-icon");
        menuNaviIcon.addClickListener(e -> Root.get().getNaviDrawer().toggle());
    }

    private void initContextNaviIcon() {
        contextNaviIcon = UIUtils
                .createTertiaryInlineButton(VaadinIcon.ARROW_LEFT);
        contextNaviIcon.removeThemeVariants(ButtonVariant.LUMO_ICON);
        contextNaviIcon.addClassNames(CLASS_NAME + "__navi-icon",
                CLASS_NAME + "__navi-icon--visible");
        contextNaviIcon.setVisible(false);
    }

    private void initTitle(String title) {
        this.title = new H4(title);
        this.title.setClassName(CLASS_NAME + "__title");
    }

    private void initSearch() {
        search = new TextField();
        search.setPlaceholder("Search");
        search.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        search.setVisible(false);
    }

    private void initAvatar() {
        avatar = new Image();
        avatar.setClassName(CLASS_NAME + "__avatar");
        avatar.setSrc(
                "https://pbs.twimg.com/profile_images/2642704545/a77c0524766c6f3b4be4929f2005e627_400x400.png");
        avatar.setVisible(UIConfig.getNaviHeader()
                .equals(UIConfig.NaviHeader.BRAND_EXPRESSION));

        ContextMenu contextMenu = new ContextMenu(avatar);
        contextMenu.setOpenOnClick(true);
        contextMenu.addItem("john.smith@gmail.com",
                e -> Notification.show("Not implemented yet.", 3000,
                        Notification.Position.BOTTOM_CENTER));
        contextMenu.addItem("Settings",
                e -> Notification.show("Not implemented yet.", 3000,
                        Notification.Position.BOTTOM_CENTER));
        contextMenu.addItem("Log Out",
                e -> Notification.show("Not implemented yet.", 3000,
                        Notification.Position.BOTTOM_CENTER));
    }

    private void initActionItems() {
        actionItems = UIUtils.createFlexLayout(
                Collections.singleton(CLASS_NAME + "__action-items"), avatar);
        actionItems.setVisible(false);
    }

    private void initContainer() {
        container = UIUtils.createFlexLayout(
                Collections.singleton(CLASS_NAME + "__container"), menuNaviIcon,
                contextNaviIcon, this.title, search, actionItems, avatar);
        container.setAlignItems(FlexComponent.Alignment.CENTER);
        container.setFlexGrow(1, search);
        getContent().add(container);
    }

    private void initTabs(NaviTab... tabs) {
        addTab = UIUtils.createSmallButton(VaadinIcon.PLUS);
        addTab.addClickListener(e -> naviTabs
                .setSelectedTab(addClosableNaviTab("New Tab", Home.class)));
        addTab.setVisible(false);

        naviTabs = tabs.length > 0 ? new NaviTabs(tabs) : new NaviTabs();
        naviTabs.setClassName(CLASS_NAME + "__tabs");
        naviTabs.setVisible(false);
        for (NaviTab tab : tabs) {
            configureTab(tab);
        }

        tabContainer = UIUtils.createFlexLayout(
                Collections.singleton(CLASS_NAME + "__tab-container"), naviTabs,
                addTab);
        tabContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        getContent().add(tabContainer);
    }

    /* === MENU ICON === */

    public Button getMenuNaviIcon() {
        return menuNaviIcon;
    }

    public void setMenuNaviIconVisible(boolean visible) {
        menuNaviIcon.setVisible(visible);
    }

    /* === CONTEXT ICON === */

    public Button getContextNaviIcon() {
        return contextNaviIcon;
    }

    public void setContextNaviIcon(Icon icon) {
        contextNaviIcon.setIcon(icon);
        contextNaviIcon.removeThemeVariants(ButtonVariant.LUMO_ICON);
    }

    /* === TITLE === */

    public String getTitle() {
        return this.title.getText();
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    /* === ACTION ITEMS === */

    public Component addActionItem(Component component) {
        actionItems.add(component);
        updateActionItemsVisibility();
        return component;
    }

    public Button addActionItem(VaadinIcon icon) {
        Button button = UIUtils.createButton(icon, ButtonVariant.LUMO_SMALL,
                ButtonVariant.LUMO_TERTIARY);
        addActionItem(button);
        return button;
    }

    public void removeAllActionItems() {
        actionItems.removeAll();
        updateActionItemsVisibility();
    }

    /* === TABS === */

    public void centerTabs() {
        naviTabs.addClassName(LumoStyles.Margin.Horizontal.AUTO);
    }

    private void configureTab(Tab tab) {
        tab.addClassName(CLASS_NAME + "__tab");
        updateTabsVisibility();
    }

    public Tab addTab(String text) {
        Tab tab = naviTabs.addTab(text);
        configureTab(tab);
        return tab;
    }

    public Tab addNaviTab(String text,
            Class<? extends Component> navigationTarget) {
        Tab tab = naviTabs.addNaviTab(text, navigationTarget);
        configureTab(tab);
        return tab;
    }

    public Tab addClosableNaviTab(String text,
            Class<? extends Component> navigationTarget) {
        Tab tab = naviTabs.addClosableNaviTab(text, navigationTarget);
        configureTab(tab);
        return tab;
    }

    public Tab getSelectedTab() {
        return naviTabs.getSelectedTab();
    }

    public void setSelectedTab(Tab selectedTab) {
        naviTabs.setSelectedTab(selectedTab);
    }

    public void updateSelectedTab(String text,
            Class<? extends Component> navigationTarget) {
        naviTabs.updateSelectedTab(text, navigationTarget);
    }

    public void navigateToSelectedTab() {
        naviTabs.navigateToSelectedTab();
    }

    public void addTabSelectionListener(
            ComponentEventListener<Tabs.SelectedChangeEvent> listener) {
        naviTabs.addSelectedChangeListener(listener);
    }

    public int getTabCount() {
        return naviTabs.getTabCount();
    }

    public void removeAllTabs() {
        naviTabs.removeAll();
        updateTabsVisibility();
    }

    /* === ADD TAB BUTTON === */

    public void setAddTabVisible(boolean visible) {
        addTab.setVisible(visible);
    }

    /* === SEARCH === */

    public void searchModeOn() {
        menuNaviIcon.setVisible(false);
        title.setVisible(false);
        actionItems.setVisible(false);
        tabContainer.setVisible(false);

        contextNaviIcon.setIcon(new Icon(VaadinIcon.ARROW_BACKWARD));
        contextNaviIcon.setVisible(true);
        searchRegistration = contextNaviIcon
                .addClickListener(e -> searchModeOff());

        search.setVisible(true);
        search.focus();
    }

    public void addSearchListener(HasValue.ValueChangeListener listener) {
        search.addValueChangeListener(listener);
    }

    public void setSearchPlaceholder(String placeholder) {
        search.setPlaceholder(placeholder);
    }

    private void searchModeOff() {
        menuNaviIcon.setVisible(true);
        title.setVisible(true);
        tabContainer.setVisible(true);

        updateActionItemsVisibility();
        updateTabsVisibility();

        contextNaviIcon.setVisible(false);
        searchRegistration.remove();

        search.clear();
        search.setVisible(false);
    }

    /* === RESET === */

    public void reset() {
        setNaviMode(AppBar.NaviMode.MENU);
        removeAllActionItems();
        removeAllTabs();
    }

    /* === UPDATE VISIBILITY === */

    private void updateActionItemsVisibility() {
        actionItems.setVisible(actionItems.getComponentCount() > 0);
    }

    private void updateTabsVisibility() {
        naviTabs.setVisible(naviTabs.getComponentCount() > 0);
    }

    public Image getAvatar() {
        return avatar;
    }
}
