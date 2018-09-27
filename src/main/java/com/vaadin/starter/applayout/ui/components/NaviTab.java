package com.vaadin.starter.applayout.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.tabs.Tab;

public class NaviTab extends Tab {

    private Class<? extends Component> navigationTarget;

    public NaviTab(String label, Class<? extends Component> navigationTarget) {
        super(label);
        this.navigationTarget = navigationTarget;
    }


    public Class<? extends Component> getNavigationTarget() {
        return navigationTarget;
    }

    public void setNavigationTarget(Class<? extends Component> navigationTarget) {
        this.navigationTarget = navigationTarget;
    }
}
