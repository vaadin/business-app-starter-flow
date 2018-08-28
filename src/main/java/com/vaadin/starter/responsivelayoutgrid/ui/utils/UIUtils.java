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

	public static Button createSmallIconButton(VaadinIcon icon) {
		Button button = new Button(new Icon(icon));
		button.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.ICON_SMALL);
		return button;
	}

	public static Button createSmallIconButton(Icon icon) {
		Button button = new Button(icon);
		button.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.ICON_SMALL);
		return button;
	}

}
