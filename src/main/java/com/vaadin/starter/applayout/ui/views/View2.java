package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.DummyData;
import com.vaadin.starter.applayout.backend.Person;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.AbstractView;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;

@Route(value = "view-2", layout = Root.class)
@PageTitle("AbstractView 2")
public class View2 extends AbstractView {

    private final SplitLayout splitter;

    public View2() {
        splitter = new SplitLayout();
        splitter.setWidth("100%");
        splitter.setOrientation(SplitLayout.Orientation.HORIZONTAL);

        Grid<Person> grid = new Grid<>();
        grid.setItems(DummyData.getPersons());
        grid.getElement().getStyle().set("flex-grow", "1");

        grid.addColumn(Person::getName).setHeader("Name");
        grid.addColumn(Person::getFirstName).setHeader("First Name");
        grid.addColumn(Person::getLastName).setHeader("Last Name");
        grid.addColumn(Person::getEmail).setHeader("Email");
        grid.addColumn(Person::getLastModified).setHeader("Last Modified");

        Div gridWrapper = new Div(grid);
        gridWrapper.addClassNames(LumoStyles.Padding.All.L);
        gridWrapper.getElement().getStyle().set(CSSProperties.Display.PROPERTY, CSSProperties.Display.FLEX);
        gridWrapper.getElement().getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);

        splitter.addToPrimary(gridWrapper);
        splitter.getPrimaryComponent().getElement().getStyle().set("flex-grow", "1");

        FormLayout form = new FormLayout();

        Div formWrapper = new Div(form);
        formWrapper.getElement().getStyle().set("flex-grow", "1");
        formWrapper.getElement().getStyle().set("overflow", "auto");

        TextField firstName = new TextField();
        firstName.setWidth("100%");
        FormLayout.FormItem firstNameItem = form.addFormItem(firstName, "First Name");

        TextField lastName = new TextField();
        lastName.setWidth("100%");
        FormLayout.FormItem lastNameItem = form.addFormItem(lastName, "Last Name");

        RadioButtonGroup gender = new RadioButtonGroup();
        gender.setItems("Male", "Female", "Other");
        FormLayout.FormItem genderItem = form.addFormItem(gender, "Gender");
        genderItem.getElement().setAttribute("colspan", "2");

        TextField numberPrefix = new TextField();
        numberPrefix.setWidth("80px");

        TextField number = new TextField();
        number.setWidth("calc(100% - 100px)");
        Div phone = new Div(numberPrefix, number);
        phone.getElement().getStyle().set(CSSProperties.Display.PROPERTY, CSSProperties.Display.FLEX);
        phone.getElement().getStyle().set(CSSProperties.JustifyContent.PROPERTY, CSSProperties.JustifyContent.SPACE_BETWEEN);

        FormLayout.FormItem phoneItem = form.addFormItem(phone, "Phone");
        phoneItem.getElement().setAttribute("colspan", "2");

        TextField email = new TextField();
        email.setWidth("100%");
        FormLayout.FormItem emailItem = form.addFormItem(email, "Email");
        emailItem.getElement().setAttribute("colspan", "2");

        ComboBox company = new ComboBox();
        company.setWidth("calc(49.95% - 0.75rem)");
        FormLayout.FormItem companyItem = form.addFormItem(company, "Company");
        companyItem.getElement().setAttribute("colspan", "2");

        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        FormLayout.FormItem uploadItem = form.addFormItem(upload, "Image");
        uploadItem.getElement().setAttribute("colspan", "2");

        Button cancel = new Button("Cancel");
        Button save = new Button("Save");
        save.getElement().setAttribute("theme", "primary");

        Div actions = new Div();
        actions.setClassName(LumoStyles.Padding.Top.M);
        actions.getElement().getStyle().set(CSSProperties.Display.PROPERTY, CSSProperties.Display.FLEX);
        actions.getElement().getStyle().set(CSSProperties.JustifyContent.PROPERTY, CSSProperties.JustifyContent.SPACE_BETWEEN);
        actions.add(cancel, save);
        splitter.addToSecondary(formWrapper, actions);
        splitter.getSecondaryComponent().getElement().getStyle().set("flex-grow", "0");
        splitter.getSecondaryComponent().getElement().getStyle().set(CSSProperties.Display.PROPERTY, CSSProperties.Display.FLEX);
        splitter.getSecondaryComponent().getElement().getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);
        splitter.getSecondaryComponent().getElement().getStyle().set("max-width", "400px");
        splitter.getSecondaryComponent().getElement().setAttribute("class", LumoStyles.Padding.All.L);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("21em", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );
    }

    @Override
    protected void initSlots() {
        setContent(splitter);
    }

}
