package com.vaadin.starter.applayout.ui.utils;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.starter.applayout.backend.Person;
import com.vaadin.starter.applayout.ui.components.ListItem;

import java.util.Collection;

public class UIUtils {



    /* ==== LAYOUTS ==== */

    public static Div createDiv(Collection<String> classNames, Component... components) {
        Div div = new Div(components);
        classNames.forEach(div::addClassName);
        return div;
    }

    public static Div createRightAlignedDiv(Component... components) {
        Div div = new Div(components);
        div.getStyle().set(CSSProperties.TextAlign.PROPERTY, CSSProperties.TextAlign.RIGHT);
        return div;
    }

    public static FlexLayout createWrappingFlexLayout(Component... components) {
        FlexLayout layout = new FlexLayout(components);
        layout.getStyle().set(CSSProperties.FlexWrap.PROPERTY, CSSProperties.FlexWrap.WRAP);
        return layout;
    }

    public static FlexLayout createWrappingFlexLayout(Collection<String> classNames, Component... components) {
        FlexLayout layout = createWrappingFlexLayout(components);
        classNames.forEach(layout::addClassName);
        return layout;
    }

    public static FlexLayout createFlexLayout(Collection<String> classNames, Component... components) {
        FlexLayout layout = new FlexLayout(components);
        classNames.forEach(layout::addClassName);
        return layout;
    }

    public static FlexLayout createColumn(Component... components) {
        FlexLayout layout = new FlexLayout(components);
        layout.getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);
        return layout;
    }

    public static FlexLayout createColumn(Collection<String> classNames, Component... components) {
        FlexLayout layout = createColumn(components);
        classNames.forEach(layout::addClassName);
        return layout;
    }



    /* ==== BUTTONS ==== */

    /* Primary buttons */
    public static Button createPrimaryButton(String text) {
        Button button = new Button(text);
        button.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.PRIMARY);
        return button;
    }

    /* Tertiary buttons */
    public static Button createTertiaryButton(String text) {
        Button button = new Button(text);
        button.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.TERTIARY);
        return button;
    }

    public static Button createTertiaryButton(VaadinIcon icon, String text) {
        Button button = createTertiaryButton(text);
        button.setIcon(new Icon(icon));
        return button;
    }

    public static Button createTertiaryButton(Collection<String> classNames, VaadinIcon icon, String text) {
        Button button = createTertiaryButton(icon, text);
        classNames.forEach(button::addClassName);
        return button;
    }

    /* Tertiary icon buttons */
    public static Button createTertiaryIconButton(VaadinIcon icon) {
        Button button = new Button(new Icon(icon));
        button.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.TERTIARY);
        return button;
    }

    /* Small buttons */
    public static Button createSmallButton(String text) {
        Button button = new Button(text);
        button.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.SMALL);
        return button;
    }

    public static Button createSmallButton(Collection<String> classNames, String text) {
        Button button = createSmallButton(text);
        classNames.forEach(button::addClassName);
        return button;
    }

    public static Button createSmallButton(VaadinIcon icon, String text) {
        Button button = createSmallButton(text);
        button.setIcon(new Icon(icon));
        return button;
    }

    public static Button createSmallButton(Collection<String> classNames, VaadinIcon icon, String text) {
        Button button = createSmallButton(icon, text);
        classNames.forEach(button::addClassName);
        return button;
    }

    /* Small icon buttons */
    public static Button createSmallIconButton(VaadinIcon icon) {
        return createSmallIconButton(new Icon(icon));
    }

    public static Button createSmallIconButton(Icon icon) {
        Button button = new Button(icon);
        button.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.SMALL_ICON);
        return button;
    }

    /* Small tertiary icon buttons */
    public static Button createSmallTertiaryIconButton(VaadinIcon icon) {
        return createSmallTertiaryIconButton(new Icon(icon));
    }

    public static Button createSmallTertiaryIconButton(Collection<String> classNames, VaadinIcon icon) {
        Button button = createSmallTertiaryIconButton(icon);
        classNames.forEach(button::addClassName);
        return button;
    }

    public static Button createSmallTertiaryIconButton(Icon icon) {
        Button button = new Button(icon);
        button.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.SMALL_TERTIARY_ICON);
        return button;
    }

    public static Button createSmallTertiaryIconButton(Collection<String> classNames, Icon icon) {
        Button button = createSmallTertiaryIconButton(icon);
        classNames.forEach(button::addClassName);
        return button;
    }

    /* Small primary icon buttons */
    public static Button createSmallPrimaryIconButton(VaadinIcon icon) {
        return createSmallPrimaryIconButton(new Icon(icon));
    }

    public static Button createSmallPrimaryIconButton(Icon icon) {
        Button button = new Button(icon);
        button.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.SMALL_PRIMARY_ICON);
        return button;
    }



    /* ==== TEXTFIELDS ==== */

    /* Create small textfield */
    public static TextField createSmallTextField() {
        TextField textField = new TextField();
        textField.getElement().setAttribute(LumoStyles.THEME, LumoStyles.TextField.SMALL);
        return textField;
    }



    /* ==== TEXT ==== */

    /* Paragraphs */
    public static Paragraph createParagraph(Collection<String> classNames, String text) {
        Paragraph p = new Paragraph(text);
        classNames.forEach(p::addClassName);
        return p;
    }

    /* Headers */
    public static H3 createH3(Collection<String> classNames, String text) {
        H3 h3 = new H3(text);
        classNames.forEach(h3::addClassName);
        return h3;
    }

    /* Labels */
    public static Label createLabel(Collection<String> classNames, String text) {
        Label label = new Label(text);
        classNames.forEach(label::addClassName);
        return label;
    }



    /* ==== ICONS ==== */

    /* Icons */
    public static Icon createSmallIcon(VaadinIcon icon) {
        Icon i = new Icon(icon);
        i.addClassName(LumoStyles.IconSize.S);
        return i;
    }

    public static Icon createSmallIcon(Collection<String> classNames, VaadinIcon icon) {
        Icon i = createSmallIcon(icon);
        classNames.forEach(i::addClassName);
        return i;
    }

    public static Icon createLargeIcon(VaadinIcon icon) {
        Icon i = new Icon(icon);
        i.addClassName(LumoStyles.IconSize.L);
        return i;
    }

    public static Icon createLargeIcon(Collection<String> classNames, VaadinIcon icon) {
        Icon i = createLargeIcon(icon);
        classNames.forEach(i::addClassName);
        return i;
    }
}
