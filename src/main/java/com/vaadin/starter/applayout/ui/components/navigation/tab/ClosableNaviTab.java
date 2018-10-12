package com.vaadin.starter.applayout.ui.components.navigation.tab;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

public class ClosableNaviTab extends NaviTab {

    private Button close;

    public ClosableNaviTab(String label, Class<? extends Component> navigationTarget) {
        super(label, navigationTarget);
        getElement().setAttribute("closable", true);

        close = UIUtils.createSmallTertiaryIconButton(VaadinIcon.CLOSE);
        add(close);
    }

    public Button getCloseButton() {
        return close;
    }
}