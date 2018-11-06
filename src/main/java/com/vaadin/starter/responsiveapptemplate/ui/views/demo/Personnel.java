package com.vaadin.starter.responsiveapptemplate.ui.views.demo;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
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
import com.vaadin.starter.responsiveapptemplate.ui.utils.LumoStyles;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;
import com.vaadin.starter.responsiveapptemplate.ui.views.ViewFrame;

import static com.vaadin.starter.responsiveapptemplate.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "personnel", layout = Root.class)
@PageTitle("Personnel")
public class Personnel extends ViewFrame {

    private AppBar appBar;
    private final ListDataProvider<Person> dataProvider;

    public Personnel() {
        // Header
        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            appBar = new AppBar("Personnel");
            for (Person.Role role : Person.Role.values()) {
                appBar.addTab(role.name().substring(0, 1).toUpperCase() + role.name().substring(1).toLowerCase());
            }
            appBar.addTabSelectionListener(e -> filter());
            appBar.centerTabs();
            setHeader(appBar);
        }

        // Grid
        Grid<Person> grid = new Grid<>();
        dataProvider = DataProvider.ofCollection(DummyData.getPersons());
        grid.setDataProvider(dataProvider);

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
        grid.addColumn(new ComponentRenderer<>(this::createActive))
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

        // Set the content
        setContent(grid);
        getContent().addClassName(GRID_VIEW);

        // Do the initial filtering
        filter();
    }

    private void filter() {
        if (appBar != null) {
            dataProvider.setFilterByValue(Person::getRole, Person.Role.valueOf(appBar.getSelectedTab().getLabel().toUpperCase()));
        }
    }

    private Component createUserInfo(Person person) {
        return new ListItem(UIUtils.createInitials(person.getInitials()), person.getName(), person.getEmail());
    }

    private Component createActive(Person person) {
        if (person.getRandomBoolean()) {
            Icon icon = new Icon(VaadinIcon.CHECK);
            icon.addClassName(LumoStyles.TextColor.PRIMARY);
            return icon;
        } else {
            Icon icon = new Icon(VaadinIcon.CLOSE);
            icon.addClassName(LumoStyles.TextColor.DISABLED);
            return icon;
        }
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
