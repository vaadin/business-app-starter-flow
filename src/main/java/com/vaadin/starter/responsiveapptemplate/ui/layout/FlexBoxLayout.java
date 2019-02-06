package com.vaadin.starter.responsiveapptemplate.ui.layout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Size;

public class FlexBoxLayout extends FlexLayout {

	public FlexBoxLayout(Component... components) {
		super(components);
	}

	public void setBackgroundColor(String value) {
		getStyle().set("background-color", value);
	}

	public void setBackgroundColor(String value, Theme theme) {
		getStyle().set("background-color", value);
		setTheme(theme);
	}

	public void setBorderRadius(BorderRadius radius) {
		getStyle().set("border-radius", radius.getValue());
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

	public void setFlexWrap(FlexWrap wrap) {
		getStyle().set("flex-wrap", wrap.getValue());
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

	public void setPosition(Position position) {
		getStyle().set("position", position.getValue());
	}

	public void setShadow(Shadow shadow) {
		getStyle().set("box-shadow", shadow.getValue());
	}

	public void setSpacing(Size... sizes) {
		for (Size size : sizes) {
			addClassName(size.getSpacingClassName());
		}
	}

	public void setTheme(Theme theme) {
		if (theme.equals(Theme.DARK)) {
			getElement().setAttribute("theme", "dark");
		} else {
			getElement().removeAttribute("theme");
		}
	}
}
