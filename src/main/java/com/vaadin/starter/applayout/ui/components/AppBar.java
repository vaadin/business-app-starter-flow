package com.vaadin.starter.applayout.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.shared.Registration;
import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.NaviDrawerProvider;
import com.vaadin.starter.applayout.ui.utils.UIUtils;
import com.vaadin.starter.applayout.ui.views.Default;

import java.util.Arrays;
import java.util.Collections;

public class AppBar extends FlexLayout {

    private final String CLASS_NAME = "app-bar";

    private FlexLayout container;

    private Button menuNaviIcon;
    private Button contextNaviIcon;

    private H4 title;
    private FlexLayout actionItems;
    private Image avatar;

    private Button addTab;
    private NaviTabs tabs;

    private Registration searchRegistration;
    private TextField search;
    private final FlexLayout tabContainer;

    public enum NaviMode {
        MENU, CONTEXTUAL
    }

    public AppBar(String title) {
        super();
        setClassName(CLASS_NAME);
        getElement().setAttribute(LumoStyles.THEME, LumoStyles.DARK);

        menuNaviIcon = UIUtils.createSmallTertiaryIconButton(Collections.singleton(CLASS_NAME + "__navi-icon"), VaadinIcon.MENU);
        menuNaviIcon.addClickListener(e -> NaviDrawerProvider.getNaviDrawer().toggle());

        contextNaviIcon = UIUtils.createSmallTertiaryIconButton(Arrays.asList(CLASS_NAME + "__navi-icon", CLASS_NAME + "__navi-icon--visible"), VaadinIcon.ARROW_BACKWARD);
        contextNaviIcon.setVisible(false);

        this.title = new H4(title);
        this.title.setClassName(CLASS_NAME + "__title");

        search = UIUtils.createSmallTextField();
        search.setPlaceholder("Search");
        search.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        search.setVisible(false);

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

        container = UIUtils.createFlexLayout(Collections.singleton(CLASS_NAME + "__container"), menuNaviIcon, contextNaviIcon, this.title, search, actionItems, avatar);
        container.setAlignItems(Alignment.CENTER);
        container.setFlexGrow(1, search);
        add(container);

        addTab = UIUtils.createSmallIconButton(VaadinIcon.PLUS);
        addTab.addClickListener(e -> tabs.setSelectedTab(addClosableNaviTab("New Tab", Default.class)));
        addTab.setVisible(false);

        tabs = new NaviTabs();
        tabs.setVisible(false);
        tabs.setClassName(CLASS_NAME + "__tabs");
        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            tabs.addClassName(LumoStyles.Margin.Horizontal.AUTO);
        }
        tabs.getElement().setAttribute("overflow", "end");

        tabContainer = UIUtils.createFlexLayout(Collections.singleton(CLASS_NAME + "__tab-container"), tabs, addTab);
        tabContainer.setAlignItems(Alignment.CENTER);
        add(tabContainer);
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

    public Button getMenuNaviIcon() {
        return menuNaviIcon;
    }

    public Button getContextNaviIcon() {
        return contextNaviIcon;
    }

    public void setContextNaviIcon(Icon icon) {
        contextNaviIcon.setIcon(icon);
    }

    public String getTitle() {
        return this.title.getText();
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

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

    public Tab addTab(String text) {
        Tab tab = tabs.addTab(text);
        updateTabsVisibility();
        return tab;
    }

    public Tab addClosableNaviTab(String text, Class<? extends Component> navigationTarget) {
        return tabs.addClosableNaviTab(text, navigationTarget);
    }

    public Tab getSelectedTab() {
        return tabs.getSelectedTab();
    }

    public void addTabSelectionListener(ComponentEventListener<Tabs.SelectedChangeEvent> listener) {
        tabs.addSelectedChangeListener(listener);
    }

    public void removeAllTabs() {
        tabs.removeAll();
        updateTabsVisibility();
    }

    public void searchModeOn() {
        menuNaviIcon.setVisible(false);
        title.setVisible(false);
        actionItems.setVisible(false);
        tabContainer.setVisible(false);

        contextNaviIcon.setIcon(new Icon(VaadinIcon.ARROW_BACKWARD));
        contextNaviIcon.setVisible(true);
        searchRegistration = contextNaviIcon.addClickListener(e -> searchModeOff());

        search.setVisible(true);
        search.focus();
    }

    private void searchModeOff() {
        menuNaviIcon.setVisible(true);
        title.setVisible(true);
        tabContainer.setVisible(true);

        updateActionItemsVisibility();
        updateTabsVisibility();

        contextNaviIcon.setVisible(false);
        searchRegistration.remove();

        search.setVisible(false);
    }

    public void reset() {
        setNaviMode(AppBar.NaviMode.MENU);
        removeAllActionItems();
        removeAllTabs();
    }

    private void updateActionItemsVisibility() {
        actionItems.setVisible(actionItems.getComponentCount() > 0);
    }

    private void updateTabsVisibility() {
        tabs.setVisible(tabs.getComponentCount() > 0);
    }
}
