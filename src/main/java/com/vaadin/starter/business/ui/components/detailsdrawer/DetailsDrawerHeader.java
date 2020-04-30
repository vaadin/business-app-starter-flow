package com.vaadin.starter.business.ui.components.detailsdrawer;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Left;
import com.vaadin.starter.business.ui.layout.size.Vertical;
import com.vaadin.starter.business.ui.util.*;
import com.vaadin.starter.business.ui.util.css.FlexDirection;

public class DetailsDrawerHeader extends FlexBoxLayout {

	private Button close;
	private H2 title;

	public DetailsDrawerHeader(String title) {
		setFlexDirection(FlexDirection.COLUMN);
		setFlexShrink("0", this);
		UIUtils.setBoxShadow(true, 0, -1, LumoStyles.Color.Contrast._10, this);
		setWidthFull();

		this.close = UIUtils.createTertiaryInlineButton(VaadinIcon.CLOSE);
		UIUtils.setLineHeight("1", this.close);

		this.title = new H2(title);
		// Theming needed since Lumo's typography styles aren't applied properly in CRUD yet.
		UIUtils.setFontSize(FontSize.L, this.title);
		UIUtils.setFontWeight(FontWeight._600, this.title);
		UIUtils.setLineHeight(LineHeight.XS, this.title);
		UIUtils.setMargin(this.title, Vertical.AUTO);
		UIUtils.setPadding(this.title, Left.L);

		FlexBoxLayout wrapper = new FlexBoxLayout(this.close, this.title);
		wrapper.setAlignItems(FlexComponent.Alignment.CENTER);
		wrapper.setPadding(Horizontal.RESPONSIVE_L, Vertical.M);
		add(wrapper);
	}

	public DetailsDrawerHeader(String title, Tabs tabs) {
		this(title);
		add(tabs);
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}

	public void addCloseListener(ComponentEventListener<ClickEvent<Button>> listener) {
		this.close.addClickListener(listener);
	}

}
