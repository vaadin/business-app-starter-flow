package com.vaadin.starter.responsiveapptemplate.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;

/**
 * A view frame that establishes app design guidelines. It consists of three parts:
 * <ul>
 * <li>Topmost {@link #setViewHeader(Component...) header}</li>
 * <li>Center {@link #setViewContent(Component...) content}</li>
 * <li>Bottom {@link #setViewFooter(Component...) footer}</li>
 * </ul>
 */
public class ViewFrame extends Composite<Div> {

	private final String CLASS_NAME = "abstract-view";

	private final Div header = new Div();
	private final Div content = new Div();
	private final Div footer = new Div();

	public ViewFrame() {
		getContent().setClassName(CLASS_NAME);

		header.setClassName(CLASS_NAME + "__header");
		content.setClassName(CLASS_NAME + "__content");
		footer.setClassName(CLASS_NAME + "__footer");

		getContent().add(header, content, footer);
	}

	public void setViewHeader(Component... components) {
		header.removeAll();
		header.add(components);
	}

	public void setViewContent(Component... components) {
		content.removeAll();
		content.add(components);
	}

	public Div getViewContent() {
		return content;
	}

	public void setViewFooter(Component... components) {
		footer.removeAll();
		footer.add(components);
	}
}
