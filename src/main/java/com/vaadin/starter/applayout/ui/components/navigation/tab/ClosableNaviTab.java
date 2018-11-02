package com.vaadin.starter.applayout.ui.components.navigation.tab;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

public class ClosableNaviTab extends NaviTab {

    private final Button close;

    public ClosableNaviTab(String label, Class<? extends Component> navigationTarget) {
        super(label, navigationTarget);
        getElement().setAttribute("closable", true);

        close = UIUtils.createSmallTertiaryButton(VaadinIcon.CLOSE);
        add(close);
    }

    public Button getCloseButton() {
        return close;
    }
}