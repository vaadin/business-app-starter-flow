package com.vaadin.starter.applayout.ui.views.personnel;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.DummyData;
import com.vaadin.starter.applayout.backend.Person;
import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;
import com.vaadin.starter.applayout.ui.views.ViewFrame;

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "managers", layout = Root.class)
@PageTitle("Managers")
public class Managers extends ViewFrame {

    private ListDataProvider<Person> dataProvider;

    public Managers() {
        // Header
        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            setHeader(new AppBar("Managers"));
        }

        // Grid
        Grid<Person> grid = new Grid<>();
        dataProvider = DataProvider.ofCollection(DummyData.getPersons());
        grid.setDataProvider(dataProvider);

        grid.addColumn(Person::getId)
                .setHeader("ID")
                .setFrozen(true)
                .setSortable(true)
                .setWidth(UIUtils.COLUMN_WIDTH_XS)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createUserInfo))
                .setHeader("Name")
                .setWidth(UIUtils.COLUMN_WIDTH_XL);
        grid.addColumn(new ComponentRenderer<>(this::createActive))
                .setHeader(UIUtils.createRightAlignedDiv("Active"))
                .setWidth(UIUtils.COLUMN_WIDTH_XS)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createApprovalLimit))
                .setHeader(UIUtils.createRightAlignedDiv("Approval Limit (€)"))
                .setWidth(UIUtils.COLUMN_WIDTH_XL)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createDate))
                .setHeader(UIUtils.createRightAlignedDiv("Last Report"))
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.setSizeFull();

        setContent(grid);
        getContent().addClassName(GRID_VIEW);

        filter();
    }

    private void filter() {
        dataProvider.setFilterByValue(Person::getRole, Person.Role.MANAGER);
    }

    private Component createUserInfo(Person person) {
        return new ListItem(UIUtils.createInitials(person.getInitials()), person.getName(), person.getEmail());
    }

    private Component createActive(Person person) {
        Icon icon;
        if (person.getRandomBoolean()) {
            icon = new Icon(VaadinIcon.CHECK);
            icon.addClassName(LumoStyles.TextColor.PRIMARY);
        } else {
            icon = new Icon(VaadinIcon.CLOSE);
            icon.addClassName(LumoStyles.TextColor.DISABLED);
        }
        return UIUtils.createRightAlignedDiv(icon);
    }

    private Component createApprovalLimit(Person person) {
        int amount = person.getRandomInteger() > 0 ? person.getRandomInteger() : 0;

        Label label = UIUtils.createH4Label(UIUtils.formatAmount(amount));
        label.addClassName(amount > 0 ? LumoStyles.TextColor.SUCCESS : LumoStyles.TextColor.ERROR);

        return UIUtils.createRightAlignedDiv(label);
    }

    private Component createDate(Person person) {
        return UIUtils.createRightAlignedDiv(UIUtils.formatDate(person.getLastModified()));
    }
}
