package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.AbstractView;
import com.vaadin.starter.applayout.ui.components.AppBar;

@Route(value = "test-view", layout = Root.class)
@PageTitle("Test View")
public class TestView extends AbstractView {

    public TestView() {
        setHeader(new AppBar("Test View"));
    }

}
