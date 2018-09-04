package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.ui.MainLayout;

@Route(value = "view-6", layout = MainLayout.class)
@PageTitle("View 6")
public class View6 extends Div {

    public View6() {

    }
}
