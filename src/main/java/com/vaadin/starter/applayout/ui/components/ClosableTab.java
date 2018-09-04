package com.vaadin.starter.applayout.ui.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

public class ClosableTab extends Tab {

    private Button close;

    public ClosableTab(String label) {
        super(label);

        close = UIUtils.createSmallTertiaryIconButton(VaadinIcon.CLOSE);
        add(close);
    }

    public Button getCloseButton() {
        return close;
    }
}