package com.vaadin.starter.applayout.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.shared.Registration;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

import java.util.Collections;

public class NaviTabItem extends NaviItem {

    private Div link;

    public NaviTabItem(VaadinIcon icon, String text, Class<? extends Component> navigationTarget) {
        super(icon, text, navigationTarget);

        link = UIUtils.createDiv(Collections.singleton(CLASS_NAME + "__link"));
        if (icon != null) {
            link.add(new Icon(icon), new Label(text));
        } else {
            link.add(new Label(text));
        }
        getElement().insertChild(0, link.getElement());
    }

    public NaviTabItem(String text, Class<? extends Component> navigationTarget) {
        this(null, text, navigationTarget);
    }

    @Override
    public Registration addClickListener(ComponentEventListener listener) {
        return link.addClickListener(listener);
    }
}
