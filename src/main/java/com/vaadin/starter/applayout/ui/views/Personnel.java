package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.Person;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.AbstractView;
import com.vaadin.starter.applayout.ui.components.AppBar;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "personnel", layout = Root.class)
@PageTitle("Personnel")
public class Personnel extends AbstractView {

    private final Grid<Person> grid;
    private final String DESIGNERS = "Designers";
    private final String DEVELOPERS = "Developers";
    private final String MANAGERS = "Managers";
    private final ListDataProvider<Person> dataProvider;

    public Personnel() {
        grid = new Grid();
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

        dataProvider = DataProvider.ofCollection(getItems());
        grid.setDataProvider(dataProvider);
        grid.setSizeFull();

        AppBar appBar = new AppBar("Personnel");
        for (Person.Role role : Person.Role.values()) {
            appBar.addTab(role.name().substring(0, 1).toUpperCase() + role.name().substring(1).toLowerCase());
        }
        appBar.addTabSelectionListener(e -> dataProvider.setFilterByValue(Person::getRole, Person.Role.valueOf(appBar.getSelectedTab().getLabel().toUpperCase())));

        // TODO: Find a proper way.
        appBar.setSelectedTabIndex(1);
        appBar.setSelectedTabIndex(0);

        setHeader(appBar);
        setContent(grid);
        getContent().addClassName(GRID_VIEW);
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

    private List<Person> getItems() {
        List<Person> items = new ArrayList<>();
        int i = 0;
        items.add(new Person(i++, "Rolf", "Smeds", Person.Role.DESIGNER, "rolf@email.com", null, 10, LocalDate.now()));
        items.add(new Person(i++, "Haijian", "Wang", Person.Role.DEVELOPER, "haijian@email.com", null, 1, LocalDate.now()));
        items.add(new Person(i++, "Jaska", "Kemppainen", Person.Role.DESIGNER, "jaska@email.com", "jaska", 0, LocalDate.now()));
        items.add(new Person(i++, "Marcio", "Dantas", Person.Role.DEVELOPER, "marcio@email.com", null, 8, LocalDate.now()));
        items.add(new Person(i++, "Vesa", "Nieminen", Person.Role.DEVELOPER, "vesa@email.com", null, 0, LocalDate.now()));
        items.add(new Person(i++, "Susanna", "Laalo", Person.Role.MANAGER, "susanna@email.com", "susanna", 64, LocalDate.now()));
        items.add(new Person(i++, "Hannu", "Salonen", Person.Role.DESIGNER, "hannu@email.com", null, 4, LocalDate.now()));
        items.add(new Person(i++, "Juuso", "Kantonen", Person.Role.DESIGNER, "juuso@email.com", "juuso", 7, LocalDate.now()));
        return items;
    }

}
