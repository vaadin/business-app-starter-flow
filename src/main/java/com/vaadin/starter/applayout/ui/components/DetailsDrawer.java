package com.vaadin.starter.applayout.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

import java.util.Collections;

public class DetailsDrawer extends FlexLayout {

    private String CLASS_NAME = "details-drawer";

    private Position position;

    private FlexLayout header;
    private FlexLayout content;
    private FlexLayout footer;

    public enum Position {
        RIGHT, BOTTOM
    }

    public DetailsDrawer(Position position, Component... components) {
        addClassName(CLASS_NAME);

        header = UIUtils.createFlexLayout(Collections.singleton(CLASS_NAME + "__header"));
        content = UIUtils.createColumn(Collections.singleton(CLASS_NAME + "__content"), components);
        footer = UIUtils.createFlexLayout(Collections.singleton(CLASS_NAME + "__footer"));
        add(header, content, footer);

        setPosition(position);
        hide();
    }

    public void setHeader(Component... components) {
        this.header.removeAll();
        this.header.add(components);
    }

    public void setContent(Component... components) {
        this.content.removeAll();
        this.content.add(components);
    }

    public void setFooter(Component... components) {
        this.footer.removeAll();
        this.footer.add(components);
    }

    public void setPosition(Position position) {
        this.position = position;
        getElement().setAttribute("position", position.name().toLowerCase());
    }

    public void hide() {
        getElement().setAttribute("open", false);
    }

    public void show() {
        getElement().setAttribute("open", true);
    }
}
