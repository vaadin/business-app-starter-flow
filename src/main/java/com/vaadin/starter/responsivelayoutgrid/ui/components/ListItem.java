package com.vaadin.starter.responsivelayoutgrid.ui.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.starter.responsivelayoutgrid.ui.LumoStyles;

public class ListItem extends FlexLayout {

	private final String CLASS_NAME = "list-item";
	private Label primaryLabel;
	private Label secondaryLabel;

	public ListItem(VaadinIcon icon, String primary, String secondary) {
		setAlignItems(FlexComponent.Alignment.CENTER);
		setClassName(CLASS_NAME);

		Icon visual = new Icon(icon);
		visual.setClassName(CLASS_NAME + "__icon");

		primaryLabel = new Label(primary);

		secondaryLabel = new Label(secondary);
		secondaryLabel.getElement().setAttribute(LumoStyles.THEME, LumoStyles.FontSize.S);
		secondaryLabel.addClassName(LumoStyles.Text.SECONDARY);

		FlexLayout column = new FlexLayout(primaryLabel, secondaryLabel);
		column.getStyle().set("flex-direction", "column");

		add(visual, column);
	}

	public ListItem(String initials, String primary, String secondary) {
		setAlignItems(FlexComponent.Alignment.CENTER);
		setClassName(CLASS_NAME);

		FlexLayout visual = new FlexLayout(new Text(initials));
		visual.setAlignItems(FlexComponent.Alignment.CENTER);
		visual.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
		visual.setClassName(CLASS_NAME + "__initials");
		visual.getElement().setAttribute(LumoStyles.THEME, LumoStyles.DARK + " " + LumoStyles.FontSize.S);

		primaryLabel = new Label(primary);

		secondaryLabel = new Label(secondary);
		secondaryLabel.getElement().setAttribute(LumoStyles.THEME, LumoStyles.FontSize.S);
		secondaryLabel.addClassName(LumoStyles.Text.SECONDARY);

		FlexLayout column = new FlexLayout(primaryLabel, secondaryLabel);
		column.getStyle().set("flex-direction", "column");

		add(visual, column);
	}

	public ListItem(VaadinIcon icon, String primary) {
		setAlignItems(FlexComponent.Alignment.CENTER);
		setClassName(CLASS_NAME);

		Icon visual = new Icon(icon);
		visual.setClassName(CLASS_NAME + "__icon");

		primaryLabel = new Label(primary);

		add(visual, primaryLabel);
	}

	public ListItem(String source, String primary) {
		setAlignItems(FlexComponent.Alignment.CENTER);
		setClassName(CLASS_NAME);

		Image image = new Image(source, "");
		image.setClassName(CLASS_NAME + "__img");

		primaryLabel = new Label(primary);

		add(image, primaryLabel);
	}

	public void setPrimaryText(String text) {
		primaryLabel.setText(text);
	}
}
