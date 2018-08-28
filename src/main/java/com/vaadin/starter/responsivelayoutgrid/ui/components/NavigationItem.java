package com.vaadin.starter.responsivelayoutgrid.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.starter.responsivelayoutgrid.ui.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class NavigationItem extends Div {

	private final String CLASS_NAME = "navigation-item";
	private RouterLink link;
	private Button expandCollapse;
	private List<NavigationItem> subItems;
	private Icon down = new Icon(VaadinIcon.CARET_DOWN);
	private Icon up = new Icon(VaadinIcon.CARET_UP);
	private int level = 0;

	public NavigationItem(VaadinIcon icon, String text, Class<? extends Component> navigationTarget) {
		setClassName(CLASS_NAME);

		link = new RouterLink(null, navigationTarget);
		if (icon != null) {
			link.add(new Icon(icon), new Label(text));
		} else {
			link.add(new Label(text));
		}
		link.setHighlightCondition(HighlightConditions.sameLocation());
		link.setClassName(CLASS_NAME + "__link");
		add(link);

		expandCollapse = UIUtils.createSmallTertiaryIconButton(down);
		expandCollapse.setVisible(false);
		expandCollapse.addClickListener(event -> {
			expandCollapse.setIcon(expandCollapse.getIcon().equals(up) ? down : up);
			subItems.forEach(item -> item.setVisible(!item.isVisible()));
		});
		add(expandCollapse);

		subItems = new ArrayList<>();
		setLevel(0);
	}

	public NavigationItem(String text, Class<? extends Component> navigationTarget) {
		this(null, text, navigationTarget);
	}

	public void addSubNavigationItem(NavigationItem item) {
		if (!expandCollapse.isVisible()) {
			expandCollapse.setVisible(true);
		}
		item.setLevel(getLevel() + 1);
		subItems.add(item);
	}

	public void setLevel(int level) {
		this.level = level;
		if (level > 0) {
			getElement().setAttribute("level", Integer.toString(level));
		}
	}

	public int getLevel() {
		return level;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);

		if (!visible) {
			subItems.forEach(item -> item.setVisible(visible));
		}
	}
}
