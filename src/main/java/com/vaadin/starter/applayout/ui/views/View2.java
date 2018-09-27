package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.ui.MainLayout;

@Route(value = "view-2", layout = MainLayout.class)
@PageTitle("View 2")
public class View2 extends Div {

    public View2() {

    }
}
