package com.vaadin.starter.applayout.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;

public abstract class AbstractView extends Div {

    private final String CLASS_NAME = "abstract-view";

    private Div header;
    private Div content;
    private Div footer;

    public AbstractView() {
        setClassName(CLASS_NAME);

        initHeader();
        initContent();
        initFooter();

        add(header, content, footer);
    }

    protected void initHeader() {
        header = new Div();
        header.setClassName(CLASS_NAME + "__header");
    }

    protected void initContent() {
        content = new Div();
        content.setClassName(CLASS_NAME + "__content");
    }

    protected void initFooter() {
        footer = new Div();
        footer.setClassName(CLASS_NAME + "__footer");
    }

    public Div getHeader() {
        return header;
    }

    public void setHeader(Component component) {
        header.removeAll();
        header.add(component);
    }

    public Div getContent() {
        return content;
    }

    public void setContent(Component component) {
        content.removeAll();
        content.add(component);
    }

    public Div getFooter() {
        return footer;
    }

    public void setFooter(Component component) {
        footer.removeAll();
        footer.add(component);
    }

}
