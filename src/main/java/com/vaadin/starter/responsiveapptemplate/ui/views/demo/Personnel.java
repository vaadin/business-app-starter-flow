package com.vaadin.starter.responsiveapptemplate.ui.views.demo;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.responsiveapptemplate.backend.DummyData;
import com.vaadin.starter.responsiveapptemplate.backend.Person;
import com.vaadin.starter.responsiveapptemplate.backend.UIConfig;
import com.vaadin.starter.responsiveapptemplate.ui.Root;
import com.vaadin.starter.responsiveapptemplate.ui.components.ListItem;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;
import com.vaadin.starter.responsiveapptemplate.ui.views.ViewFrame;

import static com.vaadin.starter.responsiveapptemplate.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "personnel", layout = Root.class)
@PageTitle("Personnel")
public class Personnel extends ViewFrame {

    private AppBar appBar;
    private Grid<Person> grid;
    private ListDataProvider<Person> dataProvider;

    public Personnel() {
        setViewHeader(createAppBar());
        setViewContent(createContent());

        filter();
    }

    private AppBar createAppBar() {
        appBar = new AppBar("Personnel");
        for (Person.Role role : Person.Role.values()) {
            appBar.addTab(role.name().substring(0, 1).toUpperCase()
                    + role.name().substring(1).toLowerCase());
        }
        appBar.addTabSelectionListener(e -> filter());
        appBar.centerTabs();
        return appBar;
    }

    private Component createContent() {
        grid = createGrid();
        Div content = new Div(grid);
        content.addClassName(GRID_VIEW);
        return content;
    }

    private Grid createGrid() {
        grid = new Grid<>();
        dataProvider = DataProvider.ofCollection(DummyData.getPersons());
        grid.setDataProvider(dataProvider);
        grid.setHeight("100%");

        grid.addColumn(Person::getId).setHeader("ID").setFrozen(true)
                .setSortable(true).setWidth("60px").setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createUserInfo))
                .setHeader("Name").setWidth("240px").setFlexGrow(1);
        grid.addColumn(new ComponentRenderer<>(this::createActive))
                .setHeader("Twitter").setWidth("160px").setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createForumPosts))
                .setHeader("Forum Posts").setWidth("160px").setFlexGrow(0);
        grid.addColumn(new LocalDateRenderer<>(Person::getLastModified,
                "MMM dd, YYYY")).setHeader("Last Modified").setSortable(true)
                .setWidth("160px").setFlexGrow(0);

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

    private Component createForumPosts(Person person) {
        Span badge;
        if (person.getRandomInteger() > 5) {
            badge = UIUtils.createSuccessBadge(
                    UIUtils.formatAmount(person.getRandomInteger()));
        } else {
            badge = UIUtils.createErrorBadge(
                    UIUtils.formatAmount(person.getRandomInteger()));
        }
        return badge;
    }

    private void filter() {
        if (appBar != null) {
            dataProvider.setFilterByValue(Person::getRole, Person.Role
                    .valueOf(appBar.getSelectedTab().getLabel().toUpperCase()));
        }
    }
}
