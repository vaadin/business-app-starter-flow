package com.vaadin.starter.responsiveapptemplate.ui.components.navigation.drawer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.starter.responsiveapptemplate.backend.UIConfig;
import com.vaadin.starter.responsiveapptemplate.ui.utils.ButtonSize;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;

import java.util.ArrayList;
import java.util.Collections;

public abstract class NaviDrawer extends Div implements AfterNavigationObserver {

	private static final String CLASS_NAME = "navi-drawer";
	private static final String RAIL = "rail";
	private static final String OPEN = "open";


	private Div scrim;
	private Div content;
	private Div scrollArea;

	protected Div list;
	private ArrayList<NaviItem> items;

	private Button railButton;
	private TextField search;

	public NaviDrawer() {
		setClassName(CLASS_NAME);
		init();
	}

	private void init() {
		// Backdrop on small viewports.
		scrim = UIUtils.createDiv(Collections.singleton(CLASS_NAME + "__scrim"));
		scrim.addClickListener(event -> close());
		add(scrim);

		// Main content.
		content = UIUtils.createDiv(Collections.singleton(CLASS_NAME + "__content"));
		add(content);

		// Header: account switcher or brand logo.
		if (UIConfig.getNaviHeader().equals(UIConfig.NaviHeader.ACCOUNT_SWITCHER)) {
			content.add(new AccountSwitcher());
		} else {
			content.add(new BrandExpression());
		}

		// Search field.
		search = new TextField();
		search.setPlaceholder("Filter");
		search.setPrefixComponent(new Icon(VaadinIcon.FILTER));
		search.addValueChangeListener(e -> search());
		content.add(search);

		// Scrollable area.
		scrollArea = UIUtils.createDiv(Collections.singleton(CLASS_NAME + "__scroll-area"));
		content.add(scrollArea);

		// Wrapper for navigation items.
		list = UIUtils.createDiv(Collections.singleton(CLASS_NAME + "__list"));
		scrollArea.add(list);

		items = new ArrayList<>();

		// "Footer", currently only a collapse/expand button.
		railButton = UIUtils.createButton(ButtonSize.SMALL, "Collapse", VaadinIcon.CHEVRON_LEFT_SMALL);
		railButton.addClassName(CLASS_NAME + "__footer");
		railButton.addClickListener(event -> toggleRailMode());
		content.add(railButton);
	}

	private void search() {
		items.forEach(naviItem -> {
			naviItem.setVisible(naviItem.getText().toLowerCase().contains(search.getValue().toLowerCase()));
		});
	}

	private void toggleRailMode() {
		if (getElement().hasAttribute(RAIL)) {
			getElement().setAttribute(RAIL, false);
			railButton.setIcon(new Icon(VaadinIcon.CHEVRON_LEFT_SMALL));
			railButton.setText("Collapse");
		} else {
			getElement().setAttribute(RAIL, true);
			railButton.setIcon(new Icon(VaadinIcon.CHEVRON_RIGHT_SMALL));
			railButton.setText("Expand");
		}
	}

	public void toggle() {
		if (getElement().hasAttribute(OPEN)) {
			close();
		} else {
			open();
		}
	}

	private void open() {
		getElement().setAttribute(OPEN, true);
	}

	private void close() {
		getElement().setAttribute(OPEN, false);
	}

	protected void addNaviItem(NaviItem item) {
		list.add(item);
		items.add(item);
	}

	protected void addNaviItem(NaviItem parent, NaviItem item) {
		parent.addSubItem(item);
		addNaviItem(item);
	}

	public abstract NaviItem addNaviItem(VaadinIcon icon, String text, Class<? extends Component> navigationTarget);

	public abstract NaviItem addNaviItem(Image image, String text, Class<? extends Component> navigationTarget);

	public abstract NaviItem addNaviItem(String path, String text, Class<? extends Component> navigationTarget);

	public abstract NaviItem addNaviItem(NaviItem parent, String text, Class<? extends Component> navigationTarget);

	public ArrayList<NaviItem> getNaviItems() {
		return items;
	}

	@Override
	public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
		close();
	}
}
