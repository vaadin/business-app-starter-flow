package com.vaadin.starter.responsiveapptemplate.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.responsiveapptemplate.backend.UIConfig;
import com.vaadin.starter.responsiveapptemplate.ui.Root;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.responsiveapptemplate.ui.layout.FlexBoxLayout;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Horizontal;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Uniform;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Vertical;
import com.vaadin.starter.responsiveapptemplate.ui.utils.CSSProperties;
import com.vaadin.starter.responsiveapptemplate.ui.utils.LumoStyles;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;

import java.util.Arrays;

@Route(value = "", layout = Root.class)
@PageTitle("Welcome")
public class Default extends ViewFrame {

	public Default() {
		if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
			setViewHeader(new AppBar("Responsive App Template"));
		}

		setViewContent(createContent());
	}

	private Component createContent() {
		Html text = new Html("<span>A responsive application template with some dummy data. Loosely based on the <b>responsive layout grid</b> guidelines set by <a href=\"https://material.io/design/layout/responsive-layout-grid.html\">Material Design</a>. Utilises the <a href=\"https://vaadin.com/themes/lumo\">Lumo</a> theme.</span>");

		FlexBoxLayout content = new FlexBoxLayout(text);
		content.setMargin(Horizontal.AUTO);
		content.setMaxWidth(CSSProperties.MaxWidth._840);
		content.setPadding(Horizontal.RESPONSIVE_L, Vertical.L);
		return content;
	}

}
