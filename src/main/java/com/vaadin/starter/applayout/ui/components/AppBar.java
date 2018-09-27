package com.vaadin.starter.applayout.ui.components;

import com.vaadin.flow.component.Component;
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
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.shared.Registration;
import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;
import com.vaadin.starter.applayout.ui.views.Default;

import java.util.HashMap;

public class AppBar extends FlexLayout implements AfterNavigationObserver {

    private final String CLASS_NAME = "app-bar";

    private FlexLayout container;

    private Button menuNavigationIcon;
    private Button contextualNavigationIcon;

    private H4 title;
    private FlexLayout actionItems;
    private Image avatar;

    private Button addTab;
    private Tabs tabs;

    private Registration searchRegistration;
    private TextField search;
    private final FlexLayout tabContainer;
    private HashMap<Tab, Class<? extends Component>> tabNavigationTargets;

    public enum NavigationMode {
        MENU, CONTEXTUAL
    }

    public AppBar(String title) {
        super();
        setClassName(CLASS_NAME);

        menuNavigationIcon = UIUtils.createSmallTertiaryIconButton(VaadinIcon.MENU);
        menuNavigationIcon.setClassName(CLASS_NAME + "__navigation-icon");

        contextualNavigationIcon = UIUtils.createSmallTertiaryIconButton(VaadinIcon.ARROW_BACKWARD);
        contextualNavigationIcon.setClassName(CLASS_NAME + "__navigation-icon");
        contextualNavigationIcon.addClassName(CLASS_NAME + "__navigation-icon--visible");
        contextualNavigationIcon.setVisible(false);

        this.title = new H4(title);
        this.title.setClassName(CLASS_NAME + "__title");

        search = UIUtils.createSmallTextField();
        search.setPlaceholder("Search");
        search.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        search.setVisible(false);

        avatar = new Image();
        avatar.setClassName(CLASS_NAME + "__avatar");
        avatar.setSrc("../frontend/styles/images/Riitta-Pitkanen.png");
        avatar.setVisible(UIConfig.getNavigationHeader().equals(UIConfig.NavigationHeader.BRAND_EXPRESSION));

        ContextMenu contextMenu = new ContextMenu(avatar);
        contextMenu.setOpenOnClick(true);
        contextMenu.addItem("conor.mcgregor@outlook.com", e -> System.out.println("Testing..."));
        contextMenu.addItem("conor.mcgregor@yahoo.com", e -> System.out.println("Testing..."));
        contextMenu.addItem("Settings", e -> System.out.println("Testing..."));
        contextMenu.addItem("Logout", e -> System.out.println("Testing..."));

        actionItems = new FlexLayout(avatar);
        actionItems.setClassName(CLASS_NAME + "__action-items");
        actionItems.setVisible(false);

        container = new FlexLayout(menuNavigationIcon, contextualNavigationIcon, this.title, search, actionItems, avatar);
        container.setAlignItems(Alignment.CENTER);
        container.setClassName(CLASS_NAME + "__container");
        container.setFlexGrow(1, search);
        add(container);

        addTab = UIUtils.createSmallIconButton(VaadinIcon.PLUS);
        addTab.addClickListener(e -> setSelectedTab(addClosableTab("New Tab", Default.class)));
        addTab.setVisible(false);

        tabs = new Tabs();
        tabs.setVisible(false);
        tabs.setClassName(CLASS_NAME + "__tabs");
        if (UIConfig.getNavigationMode().equals(UIConfig.NavigationMode.LINKS)) {
            tabs.addClassName(LumoStyles.Margin.Horizontal.AUTO);
        }
        tabs.getElement().setAttribute("overflow", "end");
        tabs.addSelectedChangeListener(event -> navigateToSelectedTab());

        tabNavigationTargets = new HashMap<>();

        tabContainer = new FlexLayout(tabs, addTab);
        tabContainer.setAlignItems(Alignment.CENTER);
        tabContainer.setClassName(CLASS_NAME + "__tab-container");
        add(tabContainer);
    }

    public void setNavigationMode(NavigationMode mode) {
        if (mode.equals(NavigationMode.MENU)) {
            menuNavigationIcon.setVisible(true);
            contextualNavigationIcon.setVisible(false);
        } else {
            menuNavigationIcon.setVisible(false);
            contextualNavigationIcon.setVisible(true);
        }
    }

    public Button getMenuNavigationIcon() {
        return menuNavigationIcon;
    }

    public Button getContextualNavigationIcon() {
        return contextualNavigationIcon;
    }

    public void setContextualNavigationIcon(VaadinIcon icon) {
        contextualNavigationIcon.setIcon(new Icon(icon));
    }

    public String getTitle() {
        return this.title.getText();
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        // TODO: what's the best way to update the title when a navigation change occurs?
        if (UIConfig.getNavigationMode().equals(UIConfig.NavigationMode.LINKS)) {
            setTitle(UI.getCurrent().getInternals().getTitle());
        }
    }

    public Component addActionItem(Component component) {
        actionItems.add(component);
        updateActionItemsVisibility();
        return component;
    }

    public Button addActionItem(VaadinIcon icon) {
        Button button = UIUtils.createSmallTertiaryIconButton(icon);
        addActionItem(button);
        return button;
    }

    public void removeAllActionItems() {
        actionItems.removeAll();
        updateActionItemsVisibility();
    }

    public void setAvatarVisible(boolean visible) {
        avatar.setVisible(true);
    }

    public void setAddTabButtonVisible(boolean visible) {
        addTab.setVisible(visible);
    }

    private void addTab(Tab tab, Class<? extends Component> navigationTarget) {
        tab.setClassName(CLASS_NAME + "__tab");

        tabs.add(tab);
        tabNavigationTargets.put(tab, navigationTarget);

        updateTabsVisibility();
    }

    public Tab addTab(String text, Class<? extends Component> navigationTarget) {
        Tab tab = new Tab(text);
        addTab(tab, navigationTarget);

        return tab;
    }

    public Tab addClosableTab(String text, Class<? extends Component> navigationTarget) {
        ClosableTab tab = new ClosableTab(text);
        addTab(tab, navigationTarget);

        tab.getCloseButton().addClickListener(event -> {
            tabs.remove(tab);
            tabNavigationTargets.remove(tab);
            navigateToSelectedTab();
        });

        return tab;
    }

    public void setSelectedTab(Tab selectedTab) {
        tabs.setSelectedTab(selectedTab);
    }

    public void updateSelectedTab(String text, Class<? extends Component> navigationTarget) {
        Tab tab = getSelectedTab();
        tab.setLabel(text);

        if (tab instanceof ClosableTab) {
            tab.add(((ClosableTab) tab).getCloseButton());
        }

        tabNavigationTargets.put(tab, navigationTarget);
        navigateToSelectedTab();
    }

    public Tab getSelectedTab() {
        return tabs.getSelectedTab();
    }

    public int getTabCount() {
        return Math.toIntExact(tabs.getChildren().filter(component -> component instanceof Tab).count());
    }

    public void removeAllTabs() {
        tabs.removeAll();
        updateTabsVisibility();
    }

    public void navigateToSelectedTab() {
        try {
            UI.getCurrent().navigate(tabNavigationTargets.get(getSelectedTab()));
        } catch (Exception e) {
            // BUG: If the right-most tab is closed, the  Tabs component does not auto-select tabs on the left.
            if (getTabCount() > 0) {
                tabs.setSelectedIndex(getTabCount() - 1);
            } else {
                UI.getCurrent().navigate(Default.class);
            }
        }
    }

    public void searchModeOn() {
        menuNavigationIcon.setVisible(false);
        title.setVisible(false);
        actionItems.setVisible(false);
        tabContainer.setVisible(false);

        contextualNavigationIcon.setIcon(new Icon(VaadinIcon.ARROW_BACKWARD));
        contextualNavigationIcon.setVisible(true);
        searchRegistration = contextualNavigationIcon.addClickListener(e -> searchModeOff());

        search.setVisible(true);
        search.focus();
    }

    private void searchModeOff() {
        menuNavigationIcon.setVisible(true);
        title.setVisible(true);
        tabContainer.setVisible(true);

        updateActionItemsVisibility();
        updateTabsVisibility();

        contextualNavigationIcon.setVisible(false);
        searchRegistration.remove();

        search.setVisible(false);
    }

    public void reset() {
        setNavigationMode(AppBar.NavigationMode.MENU);

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
