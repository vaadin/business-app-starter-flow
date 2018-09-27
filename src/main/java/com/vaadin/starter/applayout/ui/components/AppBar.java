package com.vaadin.starter.applayout.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
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
import com.vaadin.starter.applayout.ui.utils.UIUtils;
import com.vaadin.starter.applayout.ui.views.Default;

import java.util.HashMap;

public class AppBar extends FlexLayout {

    private final String CLASS_NAME = "app-bar";

    private FlexLayout container;

    private Button menuNaviIcon;
    private Button contextualNaviIcon;

    private H4 title;
    private FlexLayout actionItems;
    private Image avatar;

    private Button addTab;
    private Tabs tabs;

    private Registration searchRegistration;
    private TextField search;
    private final FlexLayout tabContainer;
    private HashMap<Tab, Class<? extends Component>> tabNaviTargets;

    public enum NaviMode {
        MENU, CONTEXTUAL
    }

    public AppBar(String title) {
        super();
        setClassName(CLASS_NAME);
        getElement().setAttribute(LumoStyles.THEME, LumoStyles.DARK);

        menuNaviIcon = UIUtils.createSmallTertiaryIconButton(VaadinIcon.MENU);
        menuNaviIcon.setClassName(CLASS_NAME + "__navi-icon");

        contextualNaviIcon = UIUtils.createSmallTertiaryIconButton(VaadinIcon.ARROW_BACKWARD);
        contextualNaviIcon.setClassName(CLASS_NAME + "__navi-icon");
        contextualNaviIcon.addClassName(CLASS_NAME + "__navi-icon--visible");
        contextualNaviIcon.setVisible(false);

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

        actionItems = new FlexLayout(avatar);
        actionItems.setClassName(CLASS_NAME + "__action-items");
        actionItems.setVisible(false);

        container = new FlexLayout(menuNaviIcon, contextualNaviIcon, this.title, search, actionItems, avatar);
        container.setAlignItems(Alignment.CENTER);
        container.setClassName(CLASS_NAME + "__container");
        container.setFlexGrow(1, search);
        add(container);

        addTab = UIUtils.createSmallIconButton(VaadinIcon.PLUS);
        addTab.addClickListener(e -> tabs.setSelectedTab(addClosableTab("New Tab", Default.class)));
        addTab.setVisible(false);

        tabs = new Tabs();
        tabs.setVisible(false);
        tabs.setClassName(CLASS_NAME + "__tabs");
        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            tabs.addClassName(LumoStyles.Margin.Horizontal.AUTO);
        }
        tabs.getElement().setAttribute("overflow", "end");
        tabs.addSelectedChangeListener(event -> navigateToSelectedTab());

        tabNaviTargets = new HashMap<>();

        tabContainer = new FlexLayout(tabs, addTab);
        tabContainer.setAlignItems(Alignment.CENTER);
        tabContainer.setClassName(CLASS_NAME + "__tab-container");
        add(tabContainer);
    }

    public void setNaviMode(NaviMode mode) {
        if (mode.equals(NaviMode.MENU)) {
            menuNaviIcon.setVisible(true);
            contextualNaviIcon.setVisible(false);
        } else {
            menuNaviIcon.setVisible(false);
            contextualNaviIcon.setVisible(true);
        }
    }

    public Button getMenuNaviIcon() {
        return menuNaviIcon;
    }

    public Button getContextualNaviIcon() {
        return contextualNaviIcon;
    }

    public void setContextualNaviIcon(Icon icon) {
        contextualNaviIcon.setIcon(icon);
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

    /**
     * Creates a regular tab without any click listeners.
     */
    public Tab addTab(String text) {
        Tab tab = new Tab(text);
        tab.setClassName(CLASS_NAME + "__tab");

        tabs.add(tab);
        updateTabsVisibility();

        return tab;
    }

    /**
     * Creates a tab that when clicked navigates to the specified target.
     */
    public Tab addNaviTab(String text, Class<? extends Component> navigationTarget) {
        Tab tab = addTab(text);
        tabNaviTargets.put(tab, navigationTarget);
        return tab;
    }

    /**
     * Creates a closable tab that when clicked navigates to the specified target.
     */
    public Tab addClosableTab(String text, Class<? extends Component> navigationTarget) {
        ClosableTab tab = new ClosableTab(text);
        tabNaviTargets.put(tab, navigationTarget);

        tab.getCloseButton().addClickListener(event -> {
            tabs.remove(tab);
            tabNaviTargets.remove(tab);
            navigateToSelectedTab();
        });

        return tab;
    }

    public void updateSelectedTab(String text, Class<? extends Component> navigationTarget) {
        Tab tab = tabs.getSelectedTab();
        tab.setLabel(text);

        if (tab instanceof ClosableTab) {
            tab.add(((ClosableTab) tab).getCloseButton());
        }

        tabNaviTargets.put(tab, navigationTarget);
        navigateToSelectedTab();
    }

    public Tab getSelectedTab() {
        return tabs.getSelectedTab();
    }

    public void setSelectedTabIndex(int index) {
        tabs.setSelectedIndex(index);
    }

    public void setSelectedTab(Tab tab) {
        tabs.setSelectedTab(tab);
    }

    public void addTabSelectionListener(ComponentEventListener<Tabs.SelectedChangeEvent> listener) {
        tabs.addSelectedChangeListener(listener);
    }

    public int getTabCount() {
        return Math.toIntExact(tabs.getChildren().filter(component -> component instanceof Tab).count());
    }

    public void removeAllTabs() {
        tabs.removeAll();
        updateTabsVisibility();
    }

    public void navigateToSelectedTab() {
        // Is this a "navi" or "regular" tab?
        if (tabNaviTargets.get(tabs.getSelectedTab()) != null) {
            try {
                UI.getCurrent().navigate(tabNaviTargets.get(tabs.getSelectedTab()));
            } catch (Exception e) {
                // If the right-most tab is closed, the  Tabs component does not auto-select tabs on the left.
                if (getTabCount() > 0) {
                    tabs.setSelectedIndex(getTabCount() - 1);
                } else {
                    UI.getCurrent().navigate(Default.class);
                }
            }
        }
    }

    public void searchModeOn() {
        menuNaviIcon.setVisible(false);
        title.setVisible(false);
        actionItems.setVisible(false);
        tabContainer.setVisible(false);

        contextualNaviIcon.setIcon(new Icon(VaadinIcon.ARROW_BACKWARD));
        contextualNaviIcon.setVisible(true);
        searchRegistration = contextualNaviIcon.addClickListener(e -> searchModeOff());

        search.setVisible(true);
        search.focus();
    }

    private void searchModeOff() {
        menuNaviIcon.setVisible(true);
        title.setVisible(true);
        tabContainer.setVisible(true);

        updateActionItemsVisibility();
        updateTabsVisibility();

        contextualNaviIcon.setVisible(false);
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
