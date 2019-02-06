package com.vaadin.starter.responsiveapptemplate.ui.components;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.starter.responsiveapptemplate.ui.utils.CSSProperties;
import com.vaadin.starter.responsiveapptemplate.ui.utils.LumoStyles;

public class Divider extends Composite<FlexLayout> {

	private final String CLASS_NAME = "divider";

	private final Div divider;

	public Divider(String height) {
		this(FlexComponent.Alignment.CENTER, height);
	}

	public Divider(FlexComponent.Alignment alignItems, String height) {
		getContent().addClassName(CLASS_NAME);

		getContent().setAlignItems(alignItems);
		getContent().setHeight(height);

		divider = new Div();
		divider.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.Contrast._20);
		divider.setHeight("1px");
		divider.setWidth("100%");
		getContent().add(divider);
	}

}
