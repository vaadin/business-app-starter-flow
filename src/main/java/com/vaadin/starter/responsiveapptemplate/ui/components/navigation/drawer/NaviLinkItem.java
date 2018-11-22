package com.vaadin.starter.responsiveapptemplate.ui.components.navigation.drawer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class NaviLinkItem extends NaviItem {

	private final Component link;

	public NaviLinkItem(VaadinIcon icon, String text, Class<? extends Component> navigationTarget) {
		this(text, navigationTarget);
		link.getElement().insertChild(0, new Icon(icon).getElement());
	}

	public NaviLinkItem(Image image, String text, Class<? extends Component> navigationTarget) {
		this(text, navigationTarget);
		link.getElement().insertChild(0, image.getElement());
	}

	public NaviLinkItem(String svg, String text, Class<? extends Component> navigationTarget) {
		this(text, navigationTarget);
		try {
			String content = readFile(svg);
			link.getElement().insertChild(0, createSVGContainer(content));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public NaviLinkItem(String text, Class<? extends Component> navigationTarget) {
		super(text, navigationTarget);

		if (navigationTarget != null) {
			RouterLink routerLink = new RouterLink(null, navigationTarget);
			routerLink.add(new Label(text));
			routerLink.setHighlightCondition(HighlightConditions.sameLocation());
			routerLink.setClassName(CLASS_NAME + "__link");
			this.link = routerLink;
		} else {
			Div div = new Div(new Label(text));
			div.setClassName(CLASS_NAME + "__link");
			div.addClickListener(e -> expandCollapse.click());
			this.link = div;
		}

		getElement().insertChild(0, link.getElement());
	}
}