package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.AbstractView;

@Route(value = "", layout = Root.class)
@PageTitle("Welcome")
public class Default extends AbstractView {

    public Default() {
        add(new Label("Testing!"));

    }

    @Override
    protected void initSlots() {

    }
}
