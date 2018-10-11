package com.vaadin.starter.applayout.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

import java.util.Collections;

public class DetailsDrawer extends FlexLayout {

    private String CLASS_NAME = "details-drawer";

    private Position position;

    private Component header;
    private FlexLayout content;
    private Component footer;

    public enum Position {
        RIGHT, BOTTOM
    }

    public DetailsDrawer(Position position, Component... components) {
        addClassName(CLASS_NAME);

        Button close = UIUtils.createSmallTertiaryIconButton(VaadinIcon.CLOSE);
        close.addClickListener(e -> hide());
        header = UIUtils.createFlexLayout(Collections.singleton(CLASS_NAME + "__header"), close);

        content = UIUtils.createColumn(Collections.singleton(CLASS_NAME + "__content"), components);

        Button cancel = new Button("Cancel");
        Button save = UIUtils.createPrimaryButton("Save");
        footer = UIUtils.createFlexLayout(Collections.singleton(CLASS_NAME + "__footer"), cancel, save);

        add(header, content, footer);

        setPosition(position);
        hide();
    }

    public void setHeader(Component header) {
        this.header = header;
    }

    public Component getHeader() {
        return this.header;
    }

    public void setContent(Component... components) {
        this.content.removeAll();
        this.content.add(components);
    }

    public Component getContent() {
        return this.content;
    }

    public void setFooter(Component footer) {
        this.footer = footer;
    }

    public Component getFooter() {
        return this.footer;
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
