package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.ui.Root;

@Route(value = "view-4", layout = Root.class)
@PageTitle("AbstractView 4")
public class View4 extends Div {

    public View4() {

    }
}
