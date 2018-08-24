package com.vaadin.starter.responsivelayoutgrid.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.responsivelayoutgrid.backend.Person;
import com.vaadin.starter.responsivelayoutgrid.ui.LumoStyles;
import com.vaadin.starter.responsivelayoutgrid.ui.MainLayout;
import com.vaadin.starter.responsivelayoutgrid.ui.components.ListItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Route(value = "id-verifications", layout = MainLayout.class)
@PageTitle("ID Verifications")
public class IDVerifications extends Div {

    private final Grid<Person> grid;

    public IDVerifications() {
        setClassName("grid-view");
        getStyle().set("flex", "1");

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
        grid.addColumn(new ComponentRenderer<>(this::createOriginOfFunds))
                .setHeader("Origin of Funds")
                .setWidth("160px")
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createTimesDenied))
                .setHeader("Times Denied")
                .setWidth("160px")
                .setFlexGrow(0);
        grid.addColumn(new LocalDateRenderer<>(Person::getLastModified, "MMM dd, YYYY"))
                .setHeader("Last Modified")
                .setSortable(true)
                .setWidth("160px")
                .setFlexGrow(0);
        grid.setItems(getItems());
        grid.setSizeFull();
        add(grid);
    }

    private Component createUserInfo(Person person) {
        return new ListItem(person.getInitials(), person.getName(), person.getEmail());
    }

    private Component createOriginOfFunds(Person person) {
        Icon icon = new Icon(VaadinIcon.FILE);
        if (person.isOriginOfFunds()) {
            icon.addClassName(LumoStyles.Text.PRIMARY);
        } else {
            icon.addClassName(LumoStyles.Text.SECONDARY);
        }
        return icon;
    }

    private Component createTimesDenied(Person person) {
        Span badge = new Span(Integer.toString(person.getTimesDenied()));
        if (person.getTimesDenied() > 5) {
            badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.ERROR);
        } else {
            badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.DEFAULT);
        }
        return badge;
    }

    private List<Person> getItems() {
        List<Person> items = new ArrayList<>();
        int i = 0;
        items.add(new Person(i++, "Rolf", "Smeds", "rolfa@email.com", true, 10, LocalDate.now()));
        items.add(new Person(i++, "Haijian", "Wang", "haijian@email.com", false, 1, LocalDate.now()));
        items.add(new Person(i++, "Jaska", "Kemppainen", "jaska@email.com", true, 0, LocalDate.now()));
        items.add(new Person(i++, "Marcio", "Dantas", "marcio@email.com", false, 8, LocalDate.now()));
        items.add(new Person(i++, "Vesa", "Nieminen", "vesa@email.com", false, 0, LocalDate.now()));
        items.add(new Person(i++, "Susanna", "Laalo", "susanna@email.com", true, 64, LocalDate.now()));
        items.add(new Person(i++, "Hannu", "Salonen", "hannu@email.com", false, 4, LocalDate.now()));
        items.add(new Person(i++, "Juuso", "Kantonen", "juuso@email.com", true, 7, LocalDate.now()));
        return items;
    }

}
