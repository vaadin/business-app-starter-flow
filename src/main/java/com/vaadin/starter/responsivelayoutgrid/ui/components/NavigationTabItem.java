package com.vaadin.starter.responsivelayoutgrid.ui.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

public class NavigationTabItem extends NavigationItem {

	private Div link;

	public NavigationTabItem(VaadinIcon icon, String text) {
		super(icon, text);

		link = new Div();
		if (icon != null) {
			link.add(new Icon(icon), new Label(text));
		} else {
			link.add(new Label(text));
		}
		link.setClassName(CLASS_NAME + "__link");

		getElement().insertChild(0, link.getElement());
	}

	public NavigationTabItem(String text) {
		this(null, text);
	}
}
