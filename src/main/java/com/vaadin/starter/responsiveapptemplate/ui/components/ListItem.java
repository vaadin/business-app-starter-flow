package com.vaadin.starter.responsiveapptemplate.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.starter.responsiveapptemplate.ui.layout.FlexBoxLayout;
import com.vaadin.starter.responsiveapptemplate.ui.layout.FlexDirection;
import com.vaadin.starter.responsiveapptemplate.ui.layout.WhiteSpace;
import com.vaadin.starter.responsiveapptemplate.ui.utils.FontSize;
import com.vaadin.starter.responsiveapptemplate.ui.utils.TextColor;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;

import java.util.Collections;

public class ListItem extends FlexLayout implements HasStyle {

    private final String CLASS_NAME = "list-item";

    private Div prefix;
    private Div suffix;

    private FlexBoxLayout content;

    private Label primary;
    private Label secondary;

    private Div divider;

    public ListItem(String primary, String secondary) {
        setClassName(CLASS_NAME);
        setAlignItems(FlexComponent.Alignment.CENTER);

        this.primary = new Label(primary);
        this.secondary = UIUtils.createLabel(FontSize.S, TextColor.SECONDARY,
                secondary);

        content = new FlexBoxLayout(this.primary, this.secondary);
        content.addClassName(CLASS_NAME + "__content");
        content.setFlexDirection(FlexDirection.COLUMN);
        add(content);
    }

    public ListItem(String primary) {
        this(primary, "");
    }

    /* === PREFIX === */

    public ListItem(Component prefix, String primary, String secondary) {
        this(primary, secondary);
        setPrefix(prefix);
    }

    public ListItem(Component prefix, String primary) {
        this(prefix, primary, "");
    }

    /* === SUFFIX === */

    public ListItem(String primary, String secondary, Component suffix) {
        this(primary, secondary);
        setSuffix(suffix);
    }

    public ListItem(String primary, Component suffix) {
        this(primary, null, suffix);
    }

    /* === PREFIX & SUFFIX === */

    public ListItem(Component prefix, String primary, String secondary,
            Component suffix) {
        this(primary, secondary);
        setPrefix(prefix);
        setSuffix(suffix);
    }

    public ListItem(Component prefix, String primary, Component suffix) {
        this(prefix, primary, "", suffix);
    }

    /* === MISC === */

    public FlexBoxLayout getContent() {
        return content;
    }

    public void setWhiteSpace(WhiteSpace whiteSpace) {
        UIUtils.setWhiteSpace(whiteSpace, this);
    }

    public void setReverse(boolean reverse) {
        if (reverse) {
            content.setFlexDirection(FlexDirection.COLUMN_REVERSE);
        } else {
            content.setFlexDirection(FlexDirection.COLUMN);
        }
    }

    public void setHorizontalPadding(boolean horizontalPadding) {
        if (horizontalPadding) {
            getStyle().remove("padding-left");
            getStyle().remove("padding-right");
        } else {
            getStyle().set("padding-left", "0");
            getStyle().set("padding-right", "0");
        }
    }

    public void setPrimaryText(String text) {
        primary.setText(text);
    }

    public Label getPrimary() {
        return primary;
    }

    public void setSecondaryText(String text) {
        secondary.setText(text);
    }

    public void setPrefix(Component... components) {
        if (prefix == null) {
            prefix = UIUtils
                    .createDiv(Collections.singleton(CLASS_NAME + "__prefix"));
            getElement().insertChild(0, prefix.getElement());
        }
        prefix.removeAll();
        prefix.add(components);
    }

    public void setSuffix(Component... components) {
        if (suffix == null) {
            suffix = UIUtils
                    .createDiv(Collections.singleton(CLASS_NAME + "__suffix"));
            getElement().insertChild(getElement().getChildCount(),
                    suffix.getElement());
        }
        suffix.removeAll();
        suffix.add(components);
    }

    public void setDividerVisible(boolean visible) {
        if (divider == null) {
            divider = UIUtils
                    .createDiv(Collections.singleton(CLASS_NAME + "__divider"));
            add(divider);
        }
        divider.setVisible(visible);
    }

}
