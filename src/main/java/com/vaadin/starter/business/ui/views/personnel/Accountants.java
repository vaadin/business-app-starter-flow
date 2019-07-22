package com.vaadin.starter.business.ui.views.personnel;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.DummyData;
import com.vaadin.starter.business.backend.Person;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.ListItem;
import com.vaadin.starter.business.ui.components.detailsdrawer.DetailsDrawer;
import com.vaadin.starter.business.ui.components.detailsdrawer.DetailsDrawerFooter;
import com.vaadin.starter.business.ui.components.detailsdrawer.DetailsDrawerHeader;
import com.vaadin.starter.business.ui.util.LumoStyles;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.views.SplitViewFrame;

@Route(value = "accountants", layout = MainLayout.class)
@PageTitle("Accountants")
public class Accountants extends SplitViewFrame {

    private Grid<Person> grid;
    private ListDataProvider<Person> dataProvider;

    private DetailsDrawer detailsDrawer;
    private DetailsDrawerHeader detailsDrawerHeader;

    public Accountants() {
        setViewContent(createContent());
        setViewDetails(createDetailsDrawer());

        filter();
    }

    private Component createContent() {
        Div content = new Div(createGrid());
        content.addClassName("grid-view");
        return content;
    }

    private Grid createGrid() {
        grid = new Grid<>();
        grid.addSelectionListener(event -> event.getFirstSelectedItem()
                .ifPresent(this::showDetails));
        dataProvider = DataProvider.ofCollection(DummyData.getPersons());
        grid.setDataProvider(dataProvider);
        grid.setHeight("100%");

        grid.addColumn(Person::getId)
                .setFlexGrow(0)
                .setFrozen(true)
                .setHeader("ID")
                .setSortable(true);
        grid.addColumn(new ComponentRenderer<>(this::createUserInfo))
                .setHeader("Name");
        grid.addColumn(new ComponentRenderer<>(this::createActive))
                .setFlexGrow(0)
                .setHeader("Active")
                .setTextAlign(ColumnTextAlign.END);
        grid.addColumn(new ComponentRenderer<>(this::createInvoices))
                .setFlexGrow(0)
                .setHeader("Invoices")
                .setTextAlign(ColumnTextAlign.END);
        grid.addColumn(new ComponentRenderer<>(this::createCompanies))
                .setFlexGrow(0)
                .setHeader("Companies")
                .setTextAlign(ColumnTextAlign.END);
        grid.addColumn(new ComponentRenderer<>(this::createDate)).setFlexGrow(0)
                .setHeader("Last Report")
                .setTextAlign(ColumnTextAlign.END);

        return grid;
    }

    private Component createUserInfo(Person person) {
        ListItem item = new ListItem(
                UIUtils.createInitials(person.getInitials()), person.getName(),
                person.getEmail());
        item.setHorizontalPadding(false);
        return item;
    }

    private Component createActive(Person person) {
        Icon icon;
        if (person.getRandomBoolean()) {
            icon = UIUtils.createPrimaryIcon(VaadinIcon.CHECK);
        } else {
            icon = UIUtils.createDisabledIcon(VaadinIcon.CLOSE);
        }
        return icon;
    }

    private Component createInvoices() {
        return UIUtils.createAmountLabel(DummyData.getRandomInt(0, 5000));
    }

    private Component createCompanies() {
        return UIUtils.createUnitsLabel(DummyData.getRandomInt(0, 50));
    }

    private Component createDate(Person person) {
        return new Span(UIUtils.formatDate(person.getLastModified()));
    }

    private DetailsDrawer createDetailsDrawer() {
        detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

        // Header
        detailsDrawerHeader = new DetailsDrawerHeader("");
        detailsDrawerHeader.addCloseListener(buttonClickEvent -> detailsDrawer.hide());
        detailsDrawer.setHeader(detailsDrawerHeader);

        // Footer
        DetailsDrawerFooter footer = new DetailsDrawerFooter();
        footer.addSaveListener(e -> {
            detailsDrawer.hide();
            UIUtils.showNotification("Changes saved.");
        });
        footer.addCancelListener(e -> detailsDrawer.hide());
        detailsDrawer.setFooter(footer);

        return detailsDrawer;
    }

    private void showDetails(Person person) {
        detailsDrawerHeader.setTitle(person.getName());
        detailsDrawer.setContent(createDetails(person));
        detailsDrawer.show();
    }

    private FormLayout createDetails(Person person) {
        TextField firstName = new TextField();
        firstName.setValue(person.getFirstName());
        firstName.setWidth("100%");

        TextField lastName = new TextField();
        lastName.setValue(person.getLastName());
        lastName.setWidth("100%");

        RadioButtonGroup<String> gender = new RadioButtonGroup<>();
        gender.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        gender.setItems("Active", "Inactive");
        gender.setValue(person.getRandomBoolean() ? "Active" : "Inactive");

        FlexLayout phone = UIUtils.createPhoneLayout();

        TextField email = new TextField();
        email.setValue(person.getEmail());
        email.setWidth("100%");

        ComboBox company = new ComboBox();
        company.setItems(DummyData.getCompanies());
        company.setValue(DummyData.getCompany());
        company.setWidth("100%");

        // Form layout
        FormLayout form = new FormLayout();
        form.addClassNames(LumoStyles.Padding.Bottom.L,
                LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Top.S);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1,
                        FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("21em", 2,
                        FormLayout.ResponsiveStep.LabelsPosition.TOP));
        form.addFormItem(firstName, "First Name");
        form.addFormItem(lastName, "Last Name");
        FormLayout.FormItem statusItem = form.addFormItem(gender, "Status");
        FormLayout.FormItem phoneItem = form.addFormItem(phone, "Phone");
        FormLayout.FormItem emailItem = form.addFormItem(email, "Email");
        FormLayout.FormItem companyItem = form.addFormItem(company, "Company");
        FormLayout.FormItem uploadItem = form.addFormItem(new Upload(),
                "Image");
        UIUtils.setColSpan(2, statusItem, phoneItem, emailItem, companyItem,
                uploadItem);
        return form;
    }

    private void filter() {
        dataProvider.setFilterByValue(Person::getRole, Person.Role.ACCOUNTANT);
    }

}
