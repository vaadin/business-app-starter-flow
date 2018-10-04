package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
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

@Route(value = "horizontal-split-view", layout = Root.class)
@PageTitle("Horizontal Split View")
public class HorizontalSplitView extends AbstractView {

    private final AppBar appBar;
    private final Div splitter;

    public HorizontalSplitView() {
        // Header
        appBar = new AppBar("Personnel");

        // Splitter
        splitter = new Div();
        splitter.getStyle().set(CSSProperties.Display.PROPERTY, CSSProperties.Display.FLEX);
//        splitter.setOrientation(SplitLayout.Orientation.HORIZONTAL);
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
        gridWrapper.getStyle().set(CSSProperties.Flex.PROPERTY, "1 0 auto");

        // Form close container
        Button formCloseButton = new Button("Close");
        FlexLayout formClose = UIUtils.createFlexLayout(Arrays.asList(LumoStyles.Padding.All.M, LumoStyles.Spacing.Right.S), formCloseButton);
        formClose.getElement().getStyle().set(CSSProperties.BackgroundColor.PROPERTY, CSSProperties.BackgroundColor.WHITE);

        // Form
        FormLayout form = new FormLayout();
        form.addClassNames(LumoStyles.Padding.Left.M, LumoStyles.Padding.Right.M);
        form.getStyle().set("overflow-y", "auto");
        form.getStyle().set("overflow-x", "hidden");
        form.getStyle().set("flex-grow", "1");

        TextField firstName = new TextField();
        firstName.setWidth("100%");
        form.addFormItem(firstName, "First Name");

        TextField lastName = new TextField();
        lastName.setWidth("100%");
        form.addFormItem(lastName, "Last Name");

        RadioButtonGroup gender = new RadioButtonGroup();
        gender.setItems("Male", "Female", "Other");
        FormLayout.FormItem genderItem = form.addFormItem(gender, "Gender");
        setColSpan(genderItem, 2);

        FlexLayout phone = createPhoneLayout();
        FormLayout.FormItem phoneItem = form.addFormItem(phone, "Phone");
        setColSpan(phoneItem, 2);

        TextField email = new TextField();
        email.setWidth("100%");
        FormLayout.FormItem emailItem = form.addFormItem(email, "Email");
        setColSpan(emailItem, 2);

        ComboBox company = new ComboBox();
        company.setWidth("100%");
        FormLayout.FormItem companyItem = form.addFormItem(company, "Company");
        setColSpan(companyItem, 2);

        FormLayout.FormItem uploadItem = form.addFormItem(new Upload(), "Image");
        setColSpan(uploadItem, 2);

        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("21em", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );

        // Actions
        Button cancel = new Button("Cancel");
        Button save = UIUtils.createPrimaryButton("Save");
        FlexLayout actions = UIUtils.createFlexLayout(Arrays.asList(LumoStyles.Padding.All.M, LumoStyles.Spacing.Right.S), cancel, save);
        actions.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);


        FlexLayout formWrapper = UIUtils.createColumn(Arrays.asList(LumoStyles.Shadow.L), formClose, form, actions);
        formWrapper.addClassName("abstract-view__detail");

        // Set the splitter's content.
        splitter.add(gridWrapper, formWrapper);
    }

    @Override
    protected void initSlots() {
        setHeader(appBar);
        setContent(splitter);
    }

    private FlexLayout createPhoneLayout() {
        TextField prefix = new TextField();
        prefix.setWidth("80px");

        TextField number = new TextField();

        FlexLayout layout = UIUtils.createFlexLayout(Collections.singleton(LumoStyles.Spacing.Right.S), prefix, number);
        layout.setFlexGrow(1, number);
        return layout;
    }

    private void setColSpan(Component component, int span) {
        component.getElement().setAttribute("colspan", Integer.toString(span));
    }

}
