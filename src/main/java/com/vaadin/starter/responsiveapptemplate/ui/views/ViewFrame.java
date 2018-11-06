package com.vaadin.starter.responsiveapptemplate.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;

/**
 * A view frame that establishes app design guidelines. It consists of three parts:
 * <ul>
 * <li>Topmost {@link #setHeader(Component) header}</li>
 * <li>Center {@link #setContent(Component) content}</li>
 * <li>Bottom {@link #setFooter(Component) footer}</li>
 * </ul>
 */
public class ViewFrame extends Div {

    private final String CLASS_NAME = "abstract-view";

    private final Div header = new Div();
    private final Div content = new Div();
    private final Div footer = new Div();

    public ViewFrame() {
        setClassName(CLASS_NAME);

        header.setClassName(CLASS_NAME + "__header");
        content.setClassName(CLASS_NAME + "__content");
        footer.setClassName(CLASS_NAME + "__footer");

        add(header, content, footer);
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

    public void setFooter(Component component) {
        footer.removeAll();
        footer.add(component);
    }
}
