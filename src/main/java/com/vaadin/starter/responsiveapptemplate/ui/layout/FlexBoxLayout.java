package com.vaadin.starter.responsiveapptemplate.ui.layout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Size;

public class FlexBoxLayout extends FlexLayout {

	public FlexBoxLayout(Component... components) {
		super(components);
	}

	public void setFlex(String value, Component component) {
		component.getElement().getStyle().set("flex", value);
	}

	public void setFlexBasis(String value, Component component) {
		component.getElement().getStyle().set("flex-basis", value);
	}

	public void setFlexDirection(FlexDirection direction) {
		getStyle().set("flex-direction", direction.getValue());
	}

	public void setFlexShrink(String value, Component component) {
		component.getElement().getStyle().set("flex-shrink", value);
	}

	public void setMargin(Size... sizes) {
		for (Size size : sizes) {
			for (String attribute : size.getMarginAttributes()) {
				getStyle().set(attribute, size.getVariable());
			}
		}
	}

	public void setMaxWidth(String value) {
		getStyle().set("max-width", value);
	}

	public void setPadding(Size... sizes) {
		for (Size size : sizes) {
			for (String attribute : size.getPaddingAttributes()) {
				getStyle().set(attribute, size.getVariable());
			}
		}
	}

	public void setSpacing(Size... sizes) {
		for (Size size : sizes) {
			addClassName(size.getSpacingClassName());
		}
	}
}
