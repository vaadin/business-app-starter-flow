package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.DummyData;
import com.vaadin.starter.applayout.backend.Person;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.AbstractView;
import com.vaadin.starter.applayout.ui.components.AppBar;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

import java.util.Arrays;
import java.util.Collections;

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "vertical-split-view", layout = Root.class)
@PageTitle("Vertical Split View")
public class VerticalSplitView extends AbstractView {

    private AppBar appBar;
    private SplitLayout splitter;

    public VerticalSplitView() {
        // Header
        appBar = new AppBar("Personnel");

        // Splitter
        splitter = new SplitLayout();
        splitter.setOrientation(SplitLayout.Orientation.VERTICAL);
        splitter.setSizeFull();

        // Grid
        Grid<Person> grid = new Grid<>();
        grid.setItems(DummyData.getPersons());
        grid.addColumn(Person::getName).setHeader("Name");
        grid.addColumn(Person::getFirstName).setHeader("First Name");
        grid.addColumn(Person::getLastName).setHeader("Last Name");
        grid.addColumn(Person::getEmail).setHeader("Email");
        grid.addColumn(Person::getForumPosts).setHeader("Forum Posts");
        grid.addColumn(Person::getLastModified).setHeader("Last Modified");
        grid.setSizeFull();

        // Grid wrapper for some nice padding.
        Div gridWrapper = UIUtils.createDiv(Collections.singleton(GRID_VIEW), grid);

        // Form
        TextField name = new TextField("Name");
        TextField firstName = new TextField("First Name");
        TextField lastName = new TextField("Last Name");
        TextField email = new TextField("Email");
        TextField forumPosts = new TextField("Forum Posts");
        TextField lastModified = new TextField("Last Modified");

        RadioButtonGroup radioButtonGroup = new RadioButtonGroup();
        radioButtonGroup.setItems("Option A", "Option B");
        radioButtonGroup.getElement().getStyle().set(CSSProperties.Display.PROPERTY, CSSProperties.Display.FLEX);
        radioButtonGroup.getElement().getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);

        FormLayout form = new FormLayout(name, firstName, lastName, email, radioButtonGroup, forumPosts, lastModified);
        form.addClassName(LumoStyles.Padding.All.L);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("21em", 2),
                new FormLayout.ResponsiveStep("22em", 3)
        );

        // Actions
        Button cancel = new Button("Cancel");
        Button save = UIUtils.createPrimaryButton("Save");
        FlexLayout actions = UIUtils.createFlexLayout(Arrays.asList(LumoStyles.Padding.All.L, LumoStyles.Spacing.Right.S), cancel, save);

        // Set the splitter's content.
        splitter.addToPrimary(gridWrapper);
        splitter.addToSecondary(form, actions);
        splitter.setSplitterPosition(75);
    }

    @Override
    protected void initSlots() {
        setHeader(appBar);
        setContent(splitter);
    }
}
