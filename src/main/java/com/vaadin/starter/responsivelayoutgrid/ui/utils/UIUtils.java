package com.vaadin.starter.responsivelayoutgrid.ui.utils;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;

public class UIUtils {

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

	/* Small buttons */
	public static Button createSmallButton(String text) {
		Button button = new Button(text);
		button.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.SMALL);
		return button;
	}

	public static Button createSmallButton(VaadinIcon icon, String text) {
		Button button = new Button(text, new Icon(icon));
		button.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.SMALL);
		return button;
	}

	/* Small icon buttons */
	public static Button createSmallIconButton(VaadinIcon icon) {
		Button button = new Button(new Icon(icon));
		button.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.SMALL_ICON);
		return button;
	}

	public static Button createSmallIconButton(Icon icon) {
		Button button = new Button(icon);
		button.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.SMALL_ICON);
		return button;
	}

	/* Small tertiary icon buttons */
	public static Button createSmallTertiaryIconButton(VaadinIcon icon) {
		Button button = new Button(new Icon(icon));
		button.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.SMALL_TERTIARY_ICON);
		return button;
	}

	public static Button createSmallTertiaryIconButton(Icon icon) {
		Button button = new Button(icon);
		button.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.SMALL_TERTIARY_ICON);
		return button;
	}

	/* Small primary icon buttons */
	public static Button createSmallPrimaryIconButton(VaadinIcon icon) {
		Button button = new Button(new Icon(icon));
		button.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.SMALL_PRIMARY_ICON);
		return button;
	}

	public static Button createSmallPrimaryIconButton(Icon icon) {
		Button button = new Button(icon);
		button.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.SMALL_PRIMARY_ICON);
		return button;
	}

}
