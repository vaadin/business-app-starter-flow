package com.vaadin.starter.applayout.ui.components.navigation.drawer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.shared.Registration;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

import java.nio.charset.Charset;
import java.util.Collections;

public class NaviTabItem extends NaviItem {

    private Div link;

    public NaviTabItem(VaadinIcon icon, String text, Class<? extends Component> navigationTarget) {
        this(text, navigationTarget);
        link.getElement().insertChild(0, new Icon(icon).getElement());
    }

    public NaviTabItem(Image image, String text, Class<? extends Component> navigationTarget) {
        this(text, navigationTarget);
        link.getElement().insertChild(0, image.getElement());
    }

    public NaviTabItem(String svg, String text, Class<? extends Component> navigationTarget) {
        this(text, navigationTarget);
        try {
            String content = readFile(svg);
            link.getElement().insertChild(0, createSVGContainer(content));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public NaviTabItem(String text, Class<? extends Component> navigationTarget) {
        super(text, navigationTarget);
        link = UIUtils.createDiv(Collections.singleton(CLASS_NAME + "__link"), new Label(text));
        getElement().insertChild(0, link.getElement());
    }

    @Override
    public Registration addClickListener(ComponentEventListener listener) {
        return link.addClickListener(listener);
    }
}
