package com.vaadin.starter.responsiveapptemplate.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.starter.responsiveapptemplate.ui.utils.CSSProperties;
import com.vaadin.starter.responsiveapptemplate.ui.utils.FontSize;
import com.vaadin.starter.responsiveapptemplate.ui.utils.TextColor;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;

import java.util.Collections;

public class ListItem extends Composite<FlexLayout> implements HasStyle {

	private final String CLASS_NAME = "list-item";

	private Div prefix;
	private Div suffix;

	private final FlexLayout content;

	private final Label primary;
	private final Label secondary;

	private Div divider;

	public ListItem(String primary, String secondary) {
		getContent().setClassName(CLASS_NAME);
		getContent().setAlignItems(FlexComponent.Alignment.CENTER);

		this.primary = new Label(primary);
		this.secondary = UIUtils.createLabel(FontSize.S, TextColor.SECONDARY, secondary);

		this.content = UIUtils.createColumn(
				Collections.singleton(CLASS_NAME + "__content"),
				this.primary,
				this.secondary
		);
		getContent().add(this.content);
	}

	public ListItem(String primary) {
		this(primary, "");
	}



	/* === PREFIX === */

	public ListItem(Component prefix, String primary, String secondary) {
		this(primary, secondary);
		setPrefix(prefix);
	}

	public ListItem(Component prefix, String primary) {
		this(prefix, primary, "");
	}



	/* === SUFFIX === */

	public ListItem(String primary, String secondary, Component suffix) {
		this(primary, secondary);
		setSuffix(suffix);
	}

	public ListItem(String primary, Component suffix) {
		this(primary, null, suffix);
	}



	/* === PREFIX & SUFFIX === */

	public ListItem(Component prefix, String primary, String secondary, Component suffix) {
		this(primary, secondary);
		setPrefix(prefix);
		setSuffix(suffix);
	}

	public ListItem(Component prefix, String primary, Component suffix) {
		this(prefix, primary, "", suffix);
	}



	/* === MISC === */

	public void setAlignItems(FlexComponent.Alignment alignment) {
		getContent().setAlignItems(alignment);
	}

	public void setWhiteSpace(String value) {
		getElement().getStyle().set(CSSProperties.WhiteSpace.PROPERTY, value);
	}

	public void setReverse(boolean reverse) {
		if (reverse) {
			this.content.getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN_REVERSE);
		} else {
			this.content.getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);
		}
	}

	public void setHorizontalPadding(boolean horizontalPadding) {
		if (horizontalPadding) {
			getContent().getStyle().remove(CSSProperties.PaddingLeft.PROPERTY);
			getContent().getStyle().remove(CSSProperties.PaddingRight.PROPERTY);
		} else {
			getContent().getStyle().set(CSSProperties.PaddingLeft.PROPERTY, "0");
			getContent().getStyle().set(CSSProperties.PaddingRight.PROPERTY, "0");
		}
	}

	public void setPrimaryText(String text) {
		primary.setText(text);
	}

	public void addPrimaryClassNames(String... classNames) {
		primary.addClassNames(classNames);
	}

	public void setPrimaryElementAttribute(String attribute, String value) {
		primary.getElement().setAttribute(attribute, value);
	}

	public void setPrimaryStyleProperty(String property, String value) {
		primary.getStyle().set(property, value);
	}

	public void setSecondaryText(String text) {
		secondary.setText(text);
	}

	public void setPrefix(Component... components) {
		if (prefix == null) {
			prefix = UIUtils.createDiv(Collections.singleton(CLASS_NAME + "__prefix"));
			getElement().insertChild(0, prefix.getElement());
		}
		prefix.removeAll();
		prefix.add(components);
	}

	public void setSuffix(Component... components) {
		if (suffix == null) {
			suffix = UIUtils.createDiv(Collections.singleton(CLASS_NAME + "__suffix"));
			getElement().insertChild(getElement().getChildCount(), suffix.getElement());
		}
		suffix.removeAll();
		suffix.add(components);
	}

	public void setDividerVisible(boolean visible) {
		if (divider == null) {
			divider = UIUtils.createDiv(Collections.singleton(CLASS_NAME + "__divider"));
			getContent().add(divider);
		}
		divider.setVisible(visible);
	}

}
