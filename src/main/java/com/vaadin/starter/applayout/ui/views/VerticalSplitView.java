package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.DummyData;
import com.vaadin.starter.applayout.backend.Person;
import com.vaadin.starter.applayout.backend.UIConfig;
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
    private FlexLayout splitter;

    public VerticalSplitView() {
        // Header
        appBar = new AppBar("Personnel");

        // Splitter
        splitter = UIUtils.createColumn();
        splitter.getStyle().set(CSSProperties.Display.PROPERTY, CSSProperties.Display.FLEX);
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
        gridWrapper.setHeight("100%");

        // Form

        FormLayout form = new FormLayout();
        form.addClassNames(LumoStyles.Padding.Left.M, LumoStyles.Padding.Right.M);
        form.getStyle().set(CSSProperties.FlexGrow.PROPERTY, CSSProperties.FlexGrow._1);
        form.getStyle().set(CSSProperties.Overflow.PROPERTY, CSSProperties.Overflow.AUTO);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("600px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("1024px", 3, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );

        TextField firstName = new TextField();
        firstName.setWidth("100%");
        form.addFormItem(firstName, "First Name");

        TextField lastName = new TextField();
        lastName.setWidth("100%");
        form.addFormItem(lastName, "Last Name");

        TextField email = new TextField();
        email.setWidth("100%");
        form.addFormItem(email, "Email");

        TextField forumPosts = new TextField();
        forumPosts.setWidth("100%");
        form.addFormItem(forumPosts, "Forum Posts");

        TextField lastModified = new TextField();
        lastModified.setWidth("100%");
        form.addFormItem(lastModified, "Last Modified");

        RadioButtonGroup radioButtonGroup = new RadioButtonGroup();
        radioButtonGroup.setItems("Male", "Female", "Other");

        form.addFormItem(radioButtonGroup, "Gender");

        // Actions
        Button cancel = new Button("Cancel");
        Button save = UIUtils.createPrimaryButton("Save");

        // Form close container
        Button formCloseButton = new Button("Close");
        FlexLayout formClose = UIUtils.createFlexLayout(Arrays.asList(LumoStyles.Padding.All.M), formCloseButton);
        formClose.getElement().getStyle().set(CSSProperties.FlexShrink.PROPERTY, CSSProperties.FlexShrink._0);
        formClose.getElement().getStyle().set(CSSProperties.BackgroundColor.PROPERTY, CSSProperties.BackgroundColor.WHITE);

        FlexLayout actions = UIUtils.createFlexLayout(Arrays.asList(LumoStyles.Padding.All.M, LumoStyles.Spacing.Right.S), cancel, save);
        actions.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        actions.getElement().getStyle().set(CSSProperties.FlexShrink.PROPERTY, CSSProperties.FlexShrink._0);

        FlexLayout detail = UIUtils.createColumn(Arrays.asList(LumoStyles.Shadow.M), formClose, form, actions);
        detail.getElement().getStyle().set(CSSProperties.BackgroundColor.PROPERTY, CSSProperties.BackgroundColor.WHITE);
        detail.setHeight("600px");

        // Set the splitter's content.
        splitter.add(gridWrapper, detail);
    }

    @Override
    protected void initSlots() {
        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            setHeader(appBar);
        }
        setContent(splitter);
    }
}
