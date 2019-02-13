package com.vaadin.starter.responsiveapptemplate.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.Div;
import com.vaadin.starter.responsiveapptemplate.ui.layout.FlexBoxLayout;
import com.vaadin.starter.responsiveapptemplate.ui.layout.FlexDirection;

/**
 * A view frame that establishes app design guidelines. It consists of three parts:
 * <ul>
 * <li>Topmost {@link #setViewHeader(Component...) header}</li>
 * <li>Center {@link #setViewContent(Component...) content}</li>
 * <li>Bottom {@link #setViewFooter(Component...) footer}</li>
 * </ul>
 */
public class ViewFrameWithDetails extends Composite<Div> implements HasStyle {

	private final String CLASS_NAME = "view-frame";

	private final Div header = new Div();
	private final FlexBoxLayout wrapper = new FlexBoxLayout();
	private final Div content = new Div();
	private final Div details = new Div();
	private final Div footer = new Div();

	public enum Position {
		RIGHT, BOTTOM
	}

	public ViewFrameWithDetails() {
		setClassName(CLASS_NAME);

		header.setClassName(CLASS_NAME + "__header");
		wrapper.setClassName(CLASS_NAME + "__wrapper");
		content.setClassName(CLASS_NAME + "__content");
		details.setClassName(CLASS_NAME + "__details");
		footer.setClassName(CLASS_NAME + "__footer");

		wrapper.add(content, details);
		getContent().add(header, wrapper, footer);
	}

	/**
	 * Sets the header slot's components.
	 */
	public void setViewHeader(Component... components) {
		header.removeAll();
		header.add(components);
	}

	/**
	 * Sets the content slot's components.
	 */
	public void setViewContent(Component... components) {
		content.removeAll();
		content.add(components);
	}

	/**
	 * Sets the content slot's components.
	 */
	public void setViewDetails(Component... components) {
		details.removeAll();
		details.add(components);
	}

	public void setViewDetailsPosition(Position position) {
		if (position.equals(Position.RIGHT)) {
			wrapper.setFlexDirection(FlexDirection.ROW);

		} else if (position.equals(Position.BOTTOM)) {
			wrapper.setFlexDirection(FlexDirection.COLUMN);
		}
	}

	/**
	 * Sets the footer slot's components.
	 */
	public void setViewFooter(Component... components) {
		footer.removeAll();
		footer.add(components);
	}
}
