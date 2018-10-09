package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.DummyData;
import com.vaadin.starter.applayout.backend.Person;
import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.AbstractView;
import com.vaadin.starter.applayout.ui.components.AppBar;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

import java.util.Arrays;
import java.util.Collections;

@Route(value = "vertical-split-view", layout = Root.class)
@PageTitle("Vertical Split View")
public class VerticalSplitView extends AbstractView {

    private String CLASS_NAME = "vsv";

    private AppBar appBar;
    private FlexLayout content;

    public VerticalSplitView() {
        // Header
        appBar = new AppBar("Personnel");

        // Splitter
        content = UIUtils.createColumn();
        content.setSizeFull();

        // Grid
        Grid<Person> grid = new Grid();
        grid.addColumn(Person::getId)
                .setHeader("ID")
                .setFrozen(true)
                .setSortable(true)
                .setWidth("60px")
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createUserInfo))
                .setHeader("Name")
                .setWidth("240px")
                .setFlexGrow(1);
        grid.addColumn(new ComponentRenderer<>(this::createTwitter))
                .setHeader("Twitter")
                .setWidth("160px")
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createForumPosts))
                .setHeader("Forum Posts")
                .setWidth("160px")
                .setFlexGrow(0);
        grid.addColumn(new LocalDateRenderer<>(Person::getLastModified, "MMM dd, YYYY"))
                .setHeader("Last Modified")
                .setSortable(true)
                .setWidth("160px")
                .setFlexGrow(0);
        grid.setSizeFull();

        DataProvider dataProvider = DataProvider.ofCollection(DummyData.getPersons());
        grid.setDataProvider(dataProvider);

        // Grid wrapper for some nice padding.
        Div gridWrapper = UIUtils.createDiv(Collections.singleton(CLASS_NAME + "__grid-wrapper"), grid);

        // Header
        Button formCloseButton = UIUtils.createSmallTertiaryIconButton(VaadinIcon.CLOSE);
        FlexLayout detailsHeader = UIUtils.createFlexLayout(Collections.singleton(CLASS_NAME + "__details-header"), formCloseButton);

        // Form
        FormLayout detailsContent = new FormLayout();
        detailsContent.addClassNames(CLASS_NAME + "__details-content");
        detailsContent.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("600px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("1024px", 3, FormLayout.ResponsiveStep.LabelsPosition.TOP)
        );

        TextField firstName = new TextField();
        firstName.setWidth("100%");
        detailsContent.addFormItem(firstName, "First Name");

        TextField lastName = new TextField();
        lastName.setWidth("100%");
        detailsContent.addFormItem(lastName, "Last Name");

        TextField email = new TextField();
        email.setWidth("100%");
        detailsContent.addFormItem(email, "Email");

        TextField forumPosts = new TextField();
        forumPosts.setWidth("100%");
        detailsContent.addFormItem(forumPosts, "Forum Posts");

        TextField lastModified = new TextField();
        lastModified.setWidth("100%");
        detailsContent.addFormItem(lastModified, "Last Modified");

        RadioButtonGroup radioButtonGroup = new RadioButtonGroup();
        radioButtonGroup.setItems("Male", "Female", "Other");

        detailsContent.addFormItem(radioButtonGroup, "Gender");

        // Details footer
        Button cancel = new Button("Cancel");
        Button save = UIUtils.createPrimaryButton("Save");
        FlexLayout detailsFooter = UIUtils.createFlexLayout(Arrays.asList(CLASS_NAME + "__details-footer"), cancel, save);

        // Wrapper
        FlexLayout formWrapper = UIUtils.createColumn(Collections.singleton(CLASS_NAME + "__details"), detailsHeader, detailsContent, detailsFooter);

        // Set the content's content.
        content.add(gridWrapper, formWrapper);
    }

    @Override
    protected void initSlots() {
        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            setHeader(appBar);
        }
        setContent(content);
    }

    private Component createUserInfo(Person person) {
        return new ListItem(person.getInitials(), person.getName(), person.getEmail());
    }

    private Component createTwitter(Person person) {
        Icon icon = new Icon(VaadinIcon.TWITTER);
        if (person.getTwitter() != null && !person.getTwitter().isEmpty()) {
            icon.addClassName(LumoStyles.TextColor.PRIMARY);
        } else {
            icon.addClassName(LumoStyles.TextColor.DISABLED);
        }
        return icon;
    }

    private Component createForumPosts(Person person) {
        Span badge = new Span(Integer.toString(person.getForumPosts()));
        if (person.getForumPosts() > 5) {
            badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.SUCCESS);
        } else {
            badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.ERROR);
        }
        return badge;
    }
}
