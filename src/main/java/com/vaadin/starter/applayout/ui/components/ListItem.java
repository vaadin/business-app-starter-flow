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

import java.util.Collections;

public class ListItem extends FlexLayout {

    private static final String CLASS_NAME = "list-item";

    private Component prefix;

    private final Label primaryLabel;
    private Label secondaryLabel;

    private final Div divider;


    public ListItem(String primary) {
        setAlignItems(FlexComponent.Alignment.CENTER);
        setClassName(CLASS_NAME);

        primaryLabel = new Label(primary);
        add(primaryLabel);

        divider = UIUtils.createDiv(Collections.singleton(CLASS_NAME + "__divider"));
        divider.setVisible(false);
        add(divider);
    }

    public ListItem(String primary, String secondary) {
        this(primary);

        initSecondaryLabel(secondary);
        add(UIUtils.createColumn(primaryLabel, secondaryLabel));
    }

    public ListItem(VaadinIcon icon, String primary, String secondary) {
        this(primary);

        Icon visual = new Icon(icon);
        visual.setClassName(CLASS_NAME + "__icon");
        prefix = visual;

        initSecondaryLabel(secondary);
        add(prefix, UIUtils.createColumn(primaryLabel, secondaryLabel));
    }

    public ListItem(String initials, String primary, String secondary) {
        this(primary);

        FlexLayout visual = UIUtils.createFlexLayout(Collections.singleton(CLASS_NAME + "__initials"), new Text(initials));
        visual.setAlignItems(FlexComponent.Alignment.CENTER);
        visual.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        visual.getElement().setAttribute(LumoStyles.THEME, LumoStyles.DARK + " " + LumoStyles.FontSize.S);
        prefix = visual;

        initSecondaryLabel(secondary);

        add(prefix, UIUtils.createColumn(primaryLabel, secondaryLabel));
    }

    public ListItem(VaadinIcon icon, String primary) {
        this(primary);

        Icon visual = new Icon(icon);
        visual.addClassName(CLASS_NAME + "__icon");
        prefix = visual;

        add(prefix, primaryLabel);
    }

    public ListItem(Image image, String primary) {
        this(primary);

        image.addClassName(CLASS_NAME + "__img");
        prefix = image;

        add(prefix, primaryLabel);
    }


    /* === PREFIX === */

    public Component getPrefix() {
        return prefix;
    }


    /* === PRIMARY === */

    public void setPrimaryText(String text) {
        primaryLabel.setText(text);
    }

    public Label getPrimaryLabel() {
        return this.primaryLabel;
    }


    /* === SECONDARY LABEL === */

    private void initSecondaryLabel(String text) {
        secondaryLabel = UIUtils.createLabel(Collections.singleton(LumoStyles.TextColor.SECONDARY), text);
        secondaryLabel.getElement().setAttribute(LumoStyles.THEME, LumoStyles.FontSize.S);
    }


    /* === DIVIDER === */

    public void setDividerVisible(boolean visible) {
        divider.setVisible(visible);
    }
}
