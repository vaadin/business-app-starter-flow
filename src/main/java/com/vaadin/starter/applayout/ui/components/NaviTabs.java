package com.vaadin.starter.applayout.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.views.Default;

/**
 * NaviTabs supports tabs that can be closed, and that can navigate to a specific target when clicked.
 */
public class NaviTabs extends Tabs {

    public NaviTabs() {
        getElement().setAttribute("overflow", "end");
        getStyle().set(CSSProperties.Overflow.PROPERTY, CSSProperties.Overflow.AUTO);
        addSelectedChangeListener(event -> navigateToSelectedTab());
    }

    /**
     * Creates a regular tab without any click listeners.
     */
    public Tab addTab(String text) {
        Tab tab = new Tab(text);
        add(tab);
        return tab;
    }

    /**
     * Creates a tab that when clicked navigates to the specified target.
     */
    public Tab addNaviTab(String text, Class<? extends Component> navigationTarget) {
        Tab tab = new NaviTab(text, navigationTarget);
        add(tab);
        return tab;
    }

    /**
     * Creates a (closable) tab that when clicked navigates to the specified target.
     */
    public Tab addClosableNaviTab(String text, Class<? extends Component> navigationTarget) {
        ClosableNaviTab tab = new ClosableNaviTab(text, navigationTarget);
        add(tab);

        tab.getCloseButton().addClickListener(event -> {
            remove(tab);
            navigateToSelectedTab();
        });

        return tab;
    }

    public void navigateToSelectedTab() {
        if (getSelectedTab() instanceof NaviTab) {
            try {
                UI.getCurrent().navigate(((NaviTab) getSelectedTab()).getNavigationTarget());
            } catch (Exception e) {
                // If the right-most tab is closed, the Tabs component does not auto-select tabs on the left.
                if (getTabCount() > 0) {
                    setSelectedIndex(getTabCount() - 1);
                } else {
                    UI.getCurrent().navigate(Default.class);
                }
            }
        }
    }

    public void updateSelectedTab(String text, Class<? extends Component> navigationTarget) {
        Tab tab = getSelectedTab();
        tab.setLabel(text);

        if (tab instanceof NaviTab) {
            ((NaviTab) tab).setNavigationTarget(navigationTarget);
        }

        if (tab instanceof ClosableNaviTab) {
            tab.add(((ClosableNaviTab) tab).getCloseButton());
        }

        navigateToSelectedTab();
    }

    public int getTabCount() {
        return Math.toIntExact(getChildren().filter(component -> component instanceof Tab).count());
    }

}
