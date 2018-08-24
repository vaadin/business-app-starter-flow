package com.vaadin.starter.responsivelayoutgrid.ui.utils;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;

public class UIUtils {

	public static Div createRightAlignedDiv(Component... components) {
		Div div = new Div(components);
		div.getStyle().set(CSSProperties.TextAlign.PROPERTY, CSSProperties.TextAlign.RIGHT);
		return div;
	}

}
