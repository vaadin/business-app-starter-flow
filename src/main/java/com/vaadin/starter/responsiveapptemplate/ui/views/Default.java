package com.vaadin.starter.responsiveapptemplate.ui.views;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.responsiveapptemplate.backend.UIConfig;
import com.vaadin.starter.responsiveapptemplate.ui.Root;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.responsiveapptemplate.ui.utils.CSSProperties;
import com.vaadin.starter.responsiveapptemplate.ui.utils.LumoStyles;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;

import java.util.Arrays;

@Route(value = "", layout = Root.class)
@PageTitle("Welcome")
public class Default extends ViewFrame {

	public Default() {
		// Header
		if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
			setViewHeader(new AppBar("Responsive App Template"));
		}

		// Content
		Div div = UIUtils.createDiv(
				Arrays.asList(LumoStyles.Margin.Horizontal.AUTO, LumoStyles.Padding.Uniform.L),
				new Html("<span>A responsive application template with some dummy data. Loosely based on the <b>responsive layout grid</b> guidelines set by <a href=\"https://material.io/design/layout/responsive-layout-grid.html\">Material Design</a>. Utilises the <a href=\"https://vaadin.com/themes/lumo\">Lumo</a> theme.</span>")
		);
		div.getStyle().set(CSSProperties.MaxWidth.PROPERTY, CSSProperties.MaxWidth._840);
		setViewContent(div);
	}

}
