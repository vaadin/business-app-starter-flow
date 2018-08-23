package com.vaadin.starter.responsivelayoutgrid.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class NavigationDrawer extends Div implements AfterNavigationObserver {

	private final String CLASS_NAME = "navigation-drawer";
	private final String OPEN = CLASS_NAME + "--open";
	private Div content;
	private Image avatar;
	private H4 username;
	private Label email;
	private Button dropdown;
	private Div scrim;

	public NavigationDrawer() {
		setClassName(CLASS_NAME);
		initScrim();
		initContent();
		initAccountSwitcher();
	}

	private void initScrim() {
		scrim = new Div();
		scrim.setClassName(CLASS_NAME + "__scrim");
		add(scrim);
		scrim.addClickListener(event -> close());
	}

	private void initContent() {
		content = new Div();
		content.setClassName(CLASS_NAME + "__content");
		add(content);
	}

	private void initAccountSwitcher() {
		avatar = new Image();
		avatar.setClassName(CLASS_NAME + "__avatar");
		avatar.setSrc("https://pbs.twimg.com/profile_images/1009479665705074688/0oLHVbs8_400x400.jpg");

		username = new H4("Theodore Fortelius");
		username.setClassName(CLASS_NAME + "__title");

		email = new Label("theodore@gmail.com");
		email.setClassName(CLASS_NAME + "__email");
		email.getElement().setAttribute(LumoStyles.THEME, LumoStyles.FontSize.S);

		dropdown = new Button(new Icon(VaadinIcon.ANGLE_DOWN));
		dropdown.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.ICON_SMALL);
		email.add(dropdown);

		content.add(avatar, username, email);
	}

	public void addNavigationItem(VaadinIcon icon, String text, Class<? extends Component> navigationTarget) {
		RouterLink item = new RouterLink(null, navigationTarget);
		item.add(new Icon(icon), new Text(text));
		item.setHighlightCondition(HighlightConditions.sameLocation());
		item.setClassName(CLASS_NAME + "__item");
		content.add(item);
	}

	public void toggle() {
		if (getClassName().contains(OPEN)) {
			close();
		} else {
			open();
		}
	}

	private void open() {
		addClassName(OPEN);
	}

	public void close() {
		removeClassName(OPEN);
	}

	@Override
	public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
		close();
	}
}
