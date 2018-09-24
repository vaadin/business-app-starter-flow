package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.Person;
import com.vaadin.starter.applayout.ui.MainLayout;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;

@Route(value = "view-1", layout = MainLayout.class)
@PageTitle("View 1")
public class View1 extends Div {

    public View1() {
        setSizeFull();

        SplitLayout splitter = new SplitLayout();
        splitter.setOrientation(SplitLayout.Orientation.VERTICAL);
        splitter.setSizeFull();

        Grid<Person> grid = new Grid<>();
        grid.setItems();

        grid.addColumn(Person::getName).setHeader("Name");
        grid.addColumn(Person::getFirstName).setHeader("First Name");
        grid.addColumn(Person::getLastName).setHeader("Last Name");
        grid.addColumn(Person::getEmail).setHeader("Email");
        grid.addColumn(Person::getLastModified).setHeader("Last Modified");
        grid.addColumn(Person::getTimesDenied).setHeader("Times Denied");

        Div gridWrapper = new Div(grid);
        gridWrapper.setClassName(LumoStyles.Padding.All.L);

        splitter.addToPrimary(gridWrapper);



        FormLayout form = new FormLayout();

        Div formWrapper = new Div(form);
        formWrapper.setClassName(LumoStyles.Padding.All.L);

        splitter.addToSecondary(formWrapper);

        TextField name = new TextField();
        name.setLabel("Name");
        name.setWidth("200px");

        TextField firstName = new TextField();
        firstName.setLabel("First Name");

        Label label = new Label("Text Information");


        TextField lastName = new TextField();
        lastName.setLabel("Last Name");

        TextField email = new TextField();
        email.setLabel("Email");

        RadioButtonGroup radioButtonGroup = new RadioButtonGroup();
        radioButtonGroup.setItems("Option A", "Option B", "Option C");

        TextField lastModified = new TextField();
        lastModified.setLabel("Last Modified");

        TextField timesDenied = new TextField();
        timesDenied.setLabel("Last Modified");

        form.add(name, firstName, label, lastName, email, radioButtonGroup, lastModified, timesDenied);

        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("21em", 2),
                new FormLayout.ResponsiveStep("22em", 3));

        add(splitter);

    }
}
