package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.ui.Root;

@Route(value = "", layout = Root.class)
@PageTitle("Welcome")
public class Default extends Div {

    public Default() {

    }
}
