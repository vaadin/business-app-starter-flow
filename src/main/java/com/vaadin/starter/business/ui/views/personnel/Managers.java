package com.vaadin.starter.business.ui.views.personnel;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
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
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.components.Initials;
import com.vaadin.starter.business.ui.components.ListItem;
import com.vaadin.starter.business.ui.components.detailsdrawer.DetailsDrawer;
import com.vaadin.starter.business.ui.components.detailsdrawer.DetailsDrawerFooter;
import com.vaadin.starter.business.ui.components.detailsdrawer.DetailsDrawerHeader;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Top;
import com.vaadin.starter.business.ui.util.LumoStyles;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.views.SplitViewFrame;

@Route(value = "managers", layout = MainLayout.class)
@PageTitle("Managers")
public class Managers extends SplitViewFrame {

    private Grid<Person> grid;
    private ListDataProvider<Person> dataProvider;

    private DetailsDrawer detailsDrawer;
    private DetailsDrawerHeader detailsDrawerHeader;

    public Managers() {
        setViewContent(createContent());
        setViewDetails(createDetailsDrawer());
        setViewDetailsPosition(Position.BOTTOM);

        filter();
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(createGrid());
        content.setBoxSizing(BoxSizing.BORDER_BOX);
        content.setHeightFull();
        content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
        return content;
    }

    private Grid createGrid() {
        grid = new Grid<>();
        grid.addSelectionListener(event -> event.getFirstSelectedItem()
                .ifPresent(this::showDetails));
        dataProvider = DataProvider.ofCollection(DummyData.getPersons());
        grid.setDataProvider(dataProvider);
        grid.setSizeFull();

        grid.addColumn(Person::getId)
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setFrozen(true)
                .setHeader("ID")
                .setSortable(true);
        grid.addColumn(new ComponentRenderer<>(this::createUserInfo))
                .setAutoWidth(true)
                .setHeader("Name");
        grid.addColumn(new ComponentRenderer<>(this::createActive))
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setHeader("Active")
                .setTextAlign(ColumnTextAlign.END);
        grid.addColumn(new ComponentRenderer<>(this::createApprovalLimit))
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setHeader("Approval Limit ($)")
                .setTextAlign(ColumnTextAlign.END);
        grid.addColumn(new ComponentRenderer<>(this::createDate))
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setHeader("Last Report")
                .setTextAlign(ColumnTextAlign.END);

        return grid;
    }

    private Component createUserInfo(Person person) {
        return new ListItem(
                new Initials(person.getInitials()), person.getName(),
                person.getEmail());
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

    private Component createApprovalLimit(Person person) {
        int amount = person.getRandomInteger() > 0 ? person.getRandomInteger()
                : 0;
        return UIUtils.createAmountLabel(amount);
    }

    private Component createDate(Person person) {
        return new Span(UIUtils.formatDate(person.getLastModified()));
    }

    private DetailsDrawer createDetailsDrawer() {
        detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.BOTTOM);

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
        firstName.setWidthFull();

        TextField lastName = new TextField();
        lastName.setValue(person.getLastName());
        lastName.setWidthFull();

        RadioButtonGroup<String> gender = new RadioButtonGroup<>();
        gender.setItems("Active", "Inactive");
        gender.setValue(person.getRandomBoolean() ? "Active" : "Inactive");

        FlexLayout phone = UIUtils.createPhoneLayout();

        TextField email = new TextField();
        email.setValue(person.getEmail());
        email.setWidthFull();

        ComboBox company = new ComboBox();
        company.setItems(DummyData.getCompanies());
        company.setValue(DummyData.getCompany());
        company.setWidthFull();

        // Form layout
        FormLayout form = new FormLayout();
        form.addClassNames(LumoStyles.Padding.Bottom.L,
                LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Top.S);
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1,
                        FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("600px", 2,
                        FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("1024px", 3,
                        FormLayout.ResponsiveStep.LabelsPosition.TOP));
        form.addFormItem(firstName, "First Name");
        form.addFormItem(lastName, "Last Name");
        form.addFormItem(gender, "Status");
        form.addFormItem(phone, "Phone");
        form.addFormItem(email, "Email");
        form.addFormItem(company, "Company");
        form.addFormItem(new Upload(), "Image");
        return form;
    }

    private void filter() {
        dataProvider.setFilterByValue(Person::getRole, Person.Role.MANAGER);
    }
}
