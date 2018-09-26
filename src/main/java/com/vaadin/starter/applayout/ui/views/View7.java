package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.ui.Root;

@Route(value = "view-7", layout = Root.class)
@PageTitle("AbstractView 7")
public class View7 extends Div {

    public View7() {

    }
}
