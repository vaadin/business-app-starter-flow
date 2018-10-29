package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

import java.util.Arrays;

@Route(value = "", layout = Root.class)
@PageTitle("Welcome")
public class Default extends ViewFrame {

    public Default() {
        // Header
        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            setHeader(new AppBar("Responsive App Template"));
        }

        // Content
        Div div = UIUtils.createDiv(
                Arrays.asList(LumoStyles.Margin.Horizontal.AUTO, LumoStyles.Padding.All.L),
                new Html(
                        "<span>" +
                                "A responsive application template with some dummy data. " +
                                "Loosely based on the <b>responsive layout grid</b> guidelines set by <a href=\"https://material.io/design/layout/responsive-layout-grid.html\">Material Design</a>. " +
                                "Utilises the <a href=\"https://vaadin.com/themes/lumo\">Lumo</a> theme." +
                                "</span>"
                )
        );
        div.getStyle().set(CSSProperties.MaxWidth.PROPERTY, CSSProperties.MaxWidth._800);
        setContent(div);

    }
}
