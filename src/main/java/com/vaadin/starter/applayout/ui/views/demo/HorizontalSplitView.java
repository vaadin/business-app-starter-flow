package com.vaadin.starter.applayout.ui.views.demo;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.DummyData;
import com.vaadin.starter.applayout.backend.Person;
import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.DetailsDrawer;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;
import com.vaadin.starter.applayout.ui.views.ViewFrame;

import java.util.Arrays;
import java.util.Collections;

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "horizontal-split-view", layout = Root.class)
@PageTitle("Horizontal Split View")
public class HorizontalSplitView extends ViewFrame {

    private DetailsDrawer detailsDrawer;
    private Label detailsDrawerTitle;

    public HorizontalSplitView() {
        // Header
        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            setHeader(new AppBar("Personnel"));
        }

        // Grid
        Grid<Person> grid = new Grid<>();
        grid.setDataProvider(DataProvider.ofCollection(DummyData.getPersons()));

        grid.addColumn(Person::getId)
                .setHeader("ID")
                .setFrozen(true)
                .setSortable(true)
                .setWidth(UIUtils.COLUMN_WIDTH_XS)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createUserInfo))
                .setHeader("Name")
                .setWidth(UIUtils.COLUMN_WIDTH_XL)
                .setFlexGrow(1);
        grid.addColumn(new ComponentRenderer<>(this::createTwitter))
                .setHeader("Twitter")
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createForumPosts))
                .setHeader("Forum Posts")
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.addColumn(new LocalDateRenderer<>(Person::getLastModified, "MMM dd, YYYY"))
                .setHeader("Last Modified")
                .setSortable(true)
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);

        grid.addSelectionListener(e -> {
            if (e.getFirstSelectedItem().isPresent()) {
                showDetails(e.getFirstSelectedItem().get());
            }
        });

        grid.setSizeFull();

        // Grid wrappe
        Div gridWrapper = UIUtils.createDiv(Collections.singleton(GRID_VIEW), grid);

        // Details drawer
        initDetailsDrawer();

        // Set the content
        FlexLayout content = new FlexLayout(gridWrapper, detailsDrawer);
        content.setSizeFull();
        setContent(content);
    }

    private void initDetailsDrawer() {
        detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

        // Header
        detailsDrawerTitle = UIUtils.createDetailsDrawerHeader("", true, true);
        detailsDrawer.setHeader(detailsDrawerTitle);

        // Footer
        Button save = UIUtils.createPrimaryButton("Save");
        save.addClickListener(e -> Notification.show("Not implemented yet.", 3000, Notification.Position.BOTTOM_CENTER));

        Button cancel = UIUtils.createTertiaryButton("Cancel");
        cancel.addClickListener(e -> detailsDrawer.hide());

        FlexLayout footer = UIUtils.createFlexLayout(Arrays.asList(LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Vertical.S, LumoStyles.Spacing.Right.S), save, cancel);
        footer.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.CONTRAST_5);
        footer.setWidth("100%");
        detailsDrawer.setFooter(footer);

    }

    private void showDetails(Person person) {
        detailsDrawerTitle.setText(person.getName());
        detailsDrawer.setContent(createDetails(person));
        detailsDrawer.show();
    }

    private FormLayout createDetails(Person person) {
        FormLayout form = UIUtils.createFormLayout(
                Arrays.asList(
                        LumoStyles.Padding.Bottom.L,
                        LumoStyles.Padding.Horizontal.L,
                        LumoStyles.Padding.Top.S
                )
        );

        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("21em", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );

        TextField firstName = new TextField();
        firstName.setValue(person.getFirstName());
        firstName.setWidth("100%");

        TextField lastName = new TextField();
        lastName.setValue(person.getLastName());
        lastName.setWidth("100%");

        RadioButtonGroup<String> gender = new RadioButtonGroup<>();
        gender.setItems("Male", "Female", "Other");
        gender.setValue("Other");

        FlexLayout phone = createPhoneLayout();

        TextField email = new TextField();
        email.setValue(person.getEmail());
        email.setWidth("100%");

        ComboBox company = new ComboBox();
        company.setItems(DummyData.getCompanies());
        company.setValue(DummyData.getCompany());
        company.setWidth("100%");

        form.addFormItem(firstName, "First Name");
        form.addFormItem(lastName, "Last Name");
        FormLayout.FormItem genderItem = form.addFormItem(gender, "Gender");
        FormLayout.FormItem phoneItem = form.addFormItem(phone, "Phone");
        FormLayout.FormItem emailItem = form.addFormItem(email, "Email");
        FormLayout.FormItem companyItem = form.addFormItem(company, "Company");
        FormLayout.FormItem uploadItem = form.addFormItem(new Upload(), "Image");

        setColSpan(genderItem, 2);
        setColSpan(phoneItem, 2);
        setColSpan(emailItem, 2);
        setColSpan(companyItem, 2);
        setColSpan(uploadItem, 2);

        return form;
    }

    private FlexLayout createPhoneLayout() {
        TextField prefix = new TextField();
        prefix.setValue("+358");
        prefix.setWidth("80px");

        TextField number = new TextField();
        number.setValue(DummyData.getPhoneNumber());

        FlexLayout layout = UIUtils.createFlexLayout(Collections.singleton(LumoStyles.Spacing.Right.S), prefix, number);
        layout.setFlexGrow(1, number);
        return layout;
    }

    private void setColSpan(Component component, int span) {
        component.getElement().setAttribute("colspan", Integer.toString(span));
    }

    private Component createUserInfo(Person person) {
        return new ListItem(UIUtils.createInitials(person.getInitials()), person.getName(), person.getEmail());
    }

    private Component createTwitter(Person person) {
        Icon icon = new Icon(VaadinIcon.TWITTER);
        if (person.getRandomBoolean()) {
            icon.addClassName(LumoStyles.TextColor.PRIMARY);
        } else {
            icon.addClassName(LumoStyles.TextColor.DISABLED);
        }
        return icon;
    }

    private Component createForumPosts(Person person) {
        Span badge = new Span(Integer.toString(person.getRandomInteger()));
        if (person.getRandomInteger() > 5) {
            badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.SUCCESS);
        } else {
            badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.ERROR);
        }
        return badge;
    }

}
