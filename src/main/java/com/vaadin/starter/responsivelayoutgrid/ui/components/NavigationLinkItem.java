package com.vaadin.starter.responsivelayoutgrid.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class NavigationLinkItem extends NavigationItem {

	private RouterLink link;

	public NavigationLinkItem(VaadinIcon icon, String text, Class<? extends Component> navigationTarget) {
		super(icon, text);

		link = new RouterLink(null, navigationTarget);
		if (icon != null) {
			link.add(new Icon(icon), new Label(text));
		} else {
			link.add(new Label(text));
		}
		link.setHighlightCondition(HighlightConditions.sameLocation());
		link.setClassName(CLASS_NAME + "__link");

		getElement().insertChild(0, link.getElement());
	}

	public NavigationLinkItem(String text, Class<? extends Component> navigationTarget) {
		this(null, text, navigationTarget);
	}
}
