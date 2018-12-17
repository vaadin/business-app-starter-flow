package com.vaadin.starter.responsiveapptemplate.ui.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.starter.responsiveapptemplate.ui.utils.CSSProperties;
import com.vaadin.starter.responsiveapptemplate.ui.utils.LumoStyles;

public class Divider extends FlexLayout {

	private final String CLASS_NAME = "divider";

	private final Div divider;

	public Divider(String height) {
		this(Alignment.CENTER, height);
	}

	public Divider(Alignment alignItems, String height) {
		addClassName(CLASS_NAME);

		setAlignItems(alignItems);
		setHeight(height);

		divider = new Div();
		divider.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.CONTRAST_20);
		divider.setHeight("1px");
		divider.setWidth("100%");
		add(divider);
	}

}
