package com.vaadin.starter.responsivelayoutgrid.ui.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.responsivelayoutgrid.ui.MainLayout;

@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Dashboard")
public class Dashboard extends Div {

	public Dashboard() {

	}
}
