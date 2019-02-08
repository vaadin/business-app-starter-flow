package com.vaadin.starter.responsiveapptemplate.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;

import java.util.Collections;

public class DetailsDrawer extends Composite<FlexLayout> implements HasStyle {

	private static final String CLASS_NAME = "details-drawer";

	private final FlexLayout header;
	private final FlexLayout content;
	private final FlexLayout footer;

	public enum Position {
		RIGHT, BOTTOM
	}

	public DetailsDrawer(Position position, Component... components) {
		addClassName(CLASS_NAME);

		header = UIUtils.createFlexLayout(Collections.singleton(CLASS_NAME + "__header"));
		content = UIUtils.createColumn(Collections.singleton(CLASS_NAME + "__content"), components);
		footer = UIUtils.createFlexLayout(Collections.singleton(CLASS_NAME + "__footer"));

		getContent().add(header, content, footer);

		setPosition(position);
	}

	public void setHeader(Component... components) {
		this.header.removeAll();
		this.header.add(components);
	}

	public FlexLayout getHeader() {
		return this.header;
	}

	public void setContent(Component... components) {
		this.content.removeAll();
		this.content.add(components);
	}

	public void setFooter(Component... components) {
		this.footer.removeAll();
		this.footer.add(components);
	}

	public void setPosition(Position position) {
		getElement().setAttribute("position", position.name().toLowerCase());
	}

	public void hide() {
		getElement().setAttribute("open", false);
	}

	public void show() {
		getElement().setAttribute("open", true);
	}
}
