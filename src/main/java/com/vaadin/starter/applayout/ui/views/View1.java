package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.DummyData;
import com.vaadin.starter.applayout.backend.Person;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;

@Route(value = "view-1", layout = Root.class)
@PageTitle("AbstractView 1")
public class View1 extends Div {

    public View1() {

        getStyle().set("flex-grow", "1");
        getStyle().set(CSSProperties.Display.PROPERTY, CSSProperties.Display.FLEX);

        SplitLayout splitter = new SplitLayout();
        splitter.setWidth("100%");
        splitter.setOrientation(SplitLayout.Orientation.VERTICAL);

        Grid<Person> grid = new Grid<>();
        grid.setItems(DummyData.getPersons());
        grid.getStyle().set("flex-grow", "1");

        grid.addColumn(Person::getName).setHeader("Name");
        grid.addColumn(Person::getFirstName).setHeader("First Name");
        grid.addColumn(Person::getLastName).setHeader("Last Name");
        grid.addColumn(Person::getEmail).setHeader("Email");
        grid.addColumn(Person::getLastModified).setHeader("Last Modified");
        grid.addColumn(Person::getForumPosts).setHeader("Forum Posts");

        Div gridWrapper = new Div(grid);
        gridWrapper.addClassNames(LumoStyles.Padding.Top.L, LumoStyles.Padding.Left.L, LumoStyles.Padding.Right.L);
        gridWrapper.getStyle().set(CSSProperties.Display.PROPERTY, CSSProperties.Display.FLEX);
        gridWrapper.getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);

        splitter.addToPrimary(gridWrapper);
        splitter.getPrimaryComponent().getElement().getStyle().set("flex-grow", "1");

        FormLayout form = new FormLayout();

        Div formWrapper = new Div(form);
        formWrapper.getStyle().set(CSSProperties.Display.PROPERTY, CSSProperties.Display.INLINE_BLOCK);

        TextField name = new TextField();
        name.setLabel("Name");

        TextField firstName = new TextField();
        firstName.setLabel("First Name");

        TextField label = new TextField("Text Information");

        TextField lastName = new TextField();
        lastName.setLabel("Last Name");

        TextField email = new TextField();
        email.setLabel("Email");

        RadioButtonGroup radioButtonGroup = new RadioButtonGroup();
        radioButtonGroup.setItems("Option A", "Option B");
        radioButtonGroup.getStyle().set("display", "flex");
        radioButtonGroup.getStyle().set("flexDirection", "column");

        TextField lastModified = new TextField();
        lastModified.setLabel("Last Modified");

        TextField timesDenied = new TextField();
        timesDenied.setLabel("Last Modified");

        Button cancel = new Button("Cancel");
        Button save = new Button("Save");
        save.getElement().setAttribute("theme", "primary");

        Div actions = new Div();
        actions.setClassName(LumoStyles.Padding.Top.M);
        actions.getStyle().set(CSSProperties.Display.PROPERTY, CSSProperties.Display.FLEX);
        actions.getStyle().set(CSSProperties.JustifyContent.PROPERTY, CSSProperties.JustifyContent.SPACE_BETWEEN);
        actions.add(cancel, save);
        splitter.addToSecondary(formWrapper, actions);


        form.add(name, firstName, label, lastName, email, radioButtonGroup, lastModified, timesDenied);

        splitter.getSecondaryComponent().getElement().getStyle().set("flex-grow", "0");


        splitter.getSecondaryComponent().getElement().setAttribute("class", LumoStyles.Padding.All.L);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("21em", 2),
                new FormLayout.ResponsiveStep("22em", 3));
        add(splitter);

    }
}
