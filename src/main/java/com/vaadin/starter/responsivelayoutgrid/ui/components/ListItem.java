package com.vaadin.starter.responsivelayoutgrid.ui.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.starter.responsivelayoutgrid.ui.utils.CSSProperties;
import com.vaadin.starter.responsivelayoutgrid.ui.utils.LumoStyles;

public class ListItem extends FlexLayout {

	private final String CLASS_NAME = "list-item";
	private Label primaryLabel;
	private Label secondaryLabel;
	private Div divider;

	public ListItem(String primary) {
		setAlignItems(FlexComponent.Alignment.CENTER);
		setClassName(CLASS_NAME);

		primaryLabel = new Label(primary);

		divider = new Div();
		divider.setClassName(CLASS_NAME + "__divider");
		divider.setVisible(false);
		add(divider);
	}

	public ListItem(VaadinIcon icon, String primary, String secondary) {
		this(primary);

		Icon visual = new Icon(icon);
		visual.setClassName(CLASS_NAME + "__icon");

		secondaryLabel = new Label(secondary);
		secondaryLabel.getElement().setAttribute(LumoStyles.THEME, LumoStyles.FontSize.S);
		secondaryLabel.addClassName(LumoStyles.Text.SECONDARY);

		FlexLayout column = new FlexLayout(primaryLabel, secondaryLabel);
		column.getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);

		add(visual, column);
	}

	public ListItem(String initials, String primary, String secondary) {
		this(primary);

		FlexLayout visual = new FlexLayout(new Text(initials));
		visual.setAlignItems(FlexComponent.Alignment.CENTER);
		visual.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
		visual.setClassName(CLASS_NAME + "__initials");
		visual.getElement().setAttribute(LumoStyles.THEME, LumoStyles.DARK + " " + LumoStyles.FontSize.S);

		secondaryLabel = new Label(secondary);
		secondaryLabel.getElement().setAttribute(LumoStyles.THEME, LumoStyles.FontSize.S);
		secondaryLabel.addClassName(LumoStyles.Text.SECONDARY);

		FlexLayout column = new FlexLayout(primaryLabel, secondaryLabel);
		column.getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);

		add(visual, column);
	}

	public ListItem(VaadinIcon icon, String primary) {
		this(primary);

		Icon visual = new Icon(icon);
		visual.setClassName(CLASS_NAME + "__icon");

		add(visual, primaryLabel);
	}

	public ListItem(String source, String primary) {
		this(primary);

		Image image = new Image(source, "");
		image.setClassName(CLASS_NAME + "__img");

		add(image, primaryLabel);
	}

	public void setPrimaryText(String text) {
		primaryLabel.setText(text);
	}

	public Label getPrimaryLabel() {
		return this.primaryLabel;
	}

	public void setDividerVisible(boolean visible) {
		divider.setVisible(visible);
	}
}
