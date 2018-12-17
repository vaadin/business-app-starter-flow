package com.vaadin.starter.responsiveapptemplate.ui.components.navigation.drawer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.startup.FakeBrowser;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class NaviItem extends Div {

	protected final String CLASS_NAME = "navi-item";

	private String text;
	private Class<? extends Component> navigationTarget;

	protected Button expandCollapse;

	private boolean subItemsVisible;
	private List<NaviItem> subItems;

	private VaadinIcon down = VaadinIcon.CARET_DOWN;
	private VaadinIcon up = VaadinIcon.CARET_UP;

	private int level = 0;

	public NaviItem(String text, Class<? extends Component> navigationTarget) {
		setClassName(CLASS_NAME);

		this.text = text;
		this.navigationTarget = navigationTarget;

		expandCollapse = UIUtils.createButton(up, ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
		expandCollapse.setVisible(false);
		expandCollapse.addClickListener(event -> setSubItemsVisible(!subItemsVisible));
		add(expandCollapse);

		subItemsVisible = true;
		subItems = new ArrayList<>();

		setLevel(0);
	}

	public void addSubItem(NaviItem item) {
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

	public String getText() {
		return text;
	}

	public Class<? extends Component> getNavigationTarget() {
		return navigationTarget;
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);

		// If true, we only update the icon. If false, we hide all the sub items.
		if (visible) {
			if (level == 0) {
				expandCollapse.setIcon(new Icon(down));
			}
		} else {
			setSubItemsVisible(visible);
		}
	}

	private void setSubItemsVisible(boolean visible) {
		if (level == 0) {
			expandCollapse.setIcon(new Icon(visible ? up : down));
		}
		if (visible) {
			removeClassName("not-visible");
		} else {
			addClassName("not-visible");
		}
		subItems.forEach(item -> item.setVisible(visible));
		subItemsVisible = visible;
	}

	public boolean hasSubItems() {
		return subItems.size() > 0;
	}

	protected static String readFile(String path) throws IOException {
		try (InputStream resourceAsStream = VaadinService.getCurrent().getResourceAsStream("frontend://" + path, FakeBrowser.getEs6(), null)) {
			return IOUtils.toString(resourceAsStream, Charsets.UTF_8);
		}
	}

	protected Element createSVGContainer(String content) {
		Div svg = new Div();
		svg.addClassName(CLASS_NAME + "__svg-container");
		svg.getElement().setProperty("innerHTML", content);
		return svg.getElement();
	}
}
