package com.vaadin.starter.applayout.ui.views.inventory;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.views.ViewFrame;

@Route(value = "invoices", layout = Root.class)
@PageTitle("Invoices")
public class Invoices extends ViewFrame {

    public Invoices() {

    }

}
