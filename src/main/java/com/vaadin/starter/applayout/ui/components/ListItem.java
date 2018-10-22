package com.vaadin.starter.applayout.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.StringJoiner;

public class ListItem extends FlexLayout {

    private static final String CLASS_NAME = "list-item";

    private Div prefix;
    private Div suffix;

    private final Label primary;
    private final Label secondary;

    private Div divider;

    public ListItem(String primary, String secondary) {
        setClassName(CLASS_NAME);
        setAlignItems(FlexComponent.Alignment.CENTER);

        this.primary = new Label(primary);
        this.secondary = UIUtils.createLabel(Collections.singleton(LumoStyles.TextColor.SECONDARY), secondary);
        this.secondary.getElement().setAttribute(LumoStyles.THEME, LumoStyles.FontSize.S);

        add(UIUtils.createColumn(Collections.singleton(CLASS_NAME + "__content"), this.primary, this.secondary));
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

    public ListItem(Component prefix, String primary, String secondary, Component suffix) {
        this(primary, secondary);
        setPrefix(prefix);
        setSuffix(suffix);
    }

    public ListItem(Component prefix, String primary, Component suffix) {
        this(prefix, primary, "", suffix);
    }


    /* === MISC === */

    public void setPrimary(String text) {
        primary.setText(text);
    }

    public void addPrimaryClassNames(String... classNames) {
        primary.addClassNames(classNames);
    }

    public void setSecondary(String text) {
        secondary.setText(text);
    }

    public void addSecondaryClassNames(String... classNames) {
        secondary.addClassNames(classNames);
    }

    public void setPrefix(Component... components) {
        if (prefix == null) {
            prefix = UIUtils.createDiv(Collections.singleton(CLASS_NAME + "__prefix"));
            getElement().insertChild(0, prefix.getElement());
        }
        prefix.removeAll();
        prefix.add(components);
    }

    public void setSuffix(Component... components) {
        if (suffix == null) {
            suffix = UIUtils.createDiv(Collections.singleton(CLASS_NAME + "__suffix"));
            getElement().insertChild(getElement().getChildCount(), suffix.getElement());
        }
        suffix.removeAll();
        suffix.add(components);
    }

    public void setDividerVisible(boolean visible) {
        if (divider == null) {
            divider = UIUtils.createDiv(Collections.singleton(CLASS_NAME + "__divider"));
            add(divider);
        }
        divider.setVisible(visible);
    }

    private Component createInitials(String initials) {
        FlexLayout init = UIUtils.createFlexLayout(Collections.singleton(CLASS_NAME + "__initials"), new Text(initials));
        init.setAlignItems(FlexComponent.Alignment.CENTER);
        init.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        init.getElement().setAttribute(LumoStyles.THEME, new StringJoiner(" ", LumoStyles.DARK, LumoStyles.FontSize.S).toString());
        return init;
    }
}
