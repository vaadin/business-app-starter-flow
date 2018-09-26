package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.ui.Root;

@Route(value = "view-9", layout = Root.class)
@PageTitle("AbstractView 9")
public class View9 extends Div {

    public View9() {

    }
}
