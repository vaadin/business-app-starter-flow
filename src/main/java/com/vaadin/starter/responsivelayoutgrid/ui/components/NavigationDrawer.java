package com.vaadin.starter.responsivelayoutgrid.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.starter.responsivelayoutgrid.ui.utils.BoxShadowBorders;
import com.vaadin.starter.responsivelayoutgrid.ui.utils.LumoStyles;
import com.vaadin.starter.responsivelayoutgrid.ui.utils.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;

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

		boolean accountSwitcher = true;
		if (accountSwitcher) {
			initAccountSwitcher();
		} else {
			initBrandExpression();
		}

		initSearch();
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

		dropdown = UIUtils.createSmallIconButton(VaadinIcon.ANGLE_DOWN);
		email.add(dropdown);

		ContextMenu contextMenu = new ContextMenu(dropdown);
		contextMenu.setOpenOnClick(true);
		contextMenu.addItem("joacim@gmail.com", e -> System.out.println("Testing..."));
		contextMenu.addItem("tove@gmail.com", e -> System.out.println("Testing..."));

		content.add(avatar, username, email);
	}

	private void initBrandExpression() {
		Image logo = new Image();
		logo.setClassName(CLASS_NAME + "__logo");
		logo.setSrc("https://upload.wikimedia.org/wikipedia/commons/7/76/Vaadin_Logo.svg");

		content.add(logo);
	}

	private void initSearch() {
		TextField search = new TextField();
		search.setPlaceholder("Search");
		content.add(search);
	}

	public NavigationItem addNavigationItem(VaadinIcon icon, String text, Class<? extends Component> navigationTarget) {
		NavigationItem item = new NavigationItem(icon, text, navigationTarget);
		content.add(item);
		return item;
	}

	public NavigationItem addNavigationSubItem(NavigationItem parent, String text, Class<? extends Component> navigationTarget) {
		NavigationItem item = new NavigationItem(text, navigationTarget);
		parent.addSubNavigationItem(item);
		content.add(item);
		return item;
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
