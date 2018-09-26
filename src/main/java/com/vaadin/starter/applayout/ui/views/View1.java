package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.ui.Root;

@Route(value = "view-1", layout = Root.class)
@PageTitle("AbstractView 1")
public class View1 extends Div {

    public View1() {

    }
}
