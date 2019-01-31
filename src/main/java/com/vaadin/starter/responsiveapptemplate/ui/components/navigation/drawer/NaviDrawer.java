package com.vaadin.starter.responsiveapptemplate.ui.components.navigation.drawer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.starter.responsiveapptemplate.backend.UIConfig;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;

import java.util.ArrayList;
import java.util.Collections;

public abstract class NaviDrawer extends Composite<Div> implements AfterNavigationObserver {

	private static final String CLASS_NAME = "navi-drawer";
	private static final String RAIL = "rail";
	private static final String OPEN = "open";

	private Div scrim;
	private Div mainContent;
	private TextField search;
	private Div scrollableArea;

	private Div naviList;
	private ArrayList<NaviItem> items;

	private Button railButton;


	public NaviDrawer() {
		getContent().setClassName(CLASS_NAME);

		initScrim();
		initMainContent();
		initHeader();
		initSearch();
		initScrollableArea();
		initNaviList();
		initFooter();
	}

	private void initScrim() {
		// Backdrop on small viewports
		scrim = UIUtils.createDiv(Collections.singleton(CLASS_NAME + "__scrim"));
		scrim.addClickListener(event -> close());
		getContent().add(scrim);
	}

	private void initMainContent() {
		mainContent = UIUtils.createDiv(Collections.singleton(CLASS_NAME + "__content"));
		getContent().add(mainContent);
	}

	private void initHeader() {
		if (UIConfig.getNaviHeader().equals(UIConfig.NaviHeader.ACCOUNT_SWITCHER)) {
			mainContent.add(new AccountSwitcher());
		} else {
			mainContent.add(new BrandExpression());
		}
	}

	private void initSearch() {
		search = new TextField();
		search.setPlaceholder("Search");
		search.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
		search.addValueChangeListener(e -> search());
		mainContent.add(search);
	}

	private void search() {
		items.forEach(naviItem -> naviItem.setVisible(naviItem.getText().toLowerCase().contains(search.getValue().toLowerCase())));
	}

	private void initScrollableArea() {
		scrollableArea = UIUtils.createDiv(Collections.singleton(CLASS_NAME + "__scroll-area"));
		mainContent.add(scrollableArea);
	}

	private void initNaviList() {
		// Wrapper for navigation items
		naviList = UIUtils.createDiv(Collections.singleton(CLASS_NAME + "__list"));
		scrollableArea.add(naviList);

		items = new ArrayList<>();
	}

	private void initFooter() {
		railButton = UIUtils.createSmallButton("Collapse", VaadinIcon.CHEVRON_LEFT_SMALL);
		railButton.addClassName(CLASS_NAME + "__footer");
		railButton.addClickListener(event -> toggleRailMode());
		mainContent.add(railButton);
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
		naviList.add(item);
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
