package com.vaadin.starter.applayout.ui.views.personnel;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
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

import java.text.DecimalFormat;
import java.util.Collections;

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "accountants", layout = Root.class)
@PageTitle("Accountants")
public class Accountants extends ViewFrame {

    private AppBar appBar;

    private Grid<Person> grid;
    private ListDataProvider<Person> dataProvider;

    public Accountants() {
        // Header
        appBar = new AppBar("Traders");

        // Grid
        grid = new Grid<>();
        grid.addColumn(Person::getId)
                .setHeader("ID")
                .setFrozen(true)
                .setSortable(true)
                .setWidth(UIUtils.COLUMN_WIDTH_S)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createUserInfo))
                .setHeader("Name")
                .setWidth(UIUtils.COLUMN_WIDTH_L)
                .setFlexGrow(1);
        grid.addColumn(new ComponentRenderer<>(this::createActive))
                .setHeader("Active")
                .setWidth(UIUtils.COLUMN_WIDTH_S)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createReports))
                .setHeader(UIUtils.createRightAlignedDiv(new Text("Reports")))
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createCompanies))
                .setHeader(UIUtils.createRightAlignedDiv(new Text("Companies")))
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.addColumn(new LocalDateRenderer<>(Person::getLastModified, "MMM dd, YYYY"))
                .setHeader("Last Report")
                .setSortable(true)
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.setSizeFull();

        dataProvider = DataProvider.ofCollection(DummyData.getPersons());
        grid.setDataProvider(dataProvider);

        filter();

        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            setHeader(appBar);
        }
        setContent(grid);
        getContentComponent().addClassName(GRID_VIEW);
    }

    private void filter() {
        dataProvider.setFilterByValue(Person::getRole, Person.Role.ACCOUNTANT);
    }

    private Component createUserInfo(Person person) {
        return new ListItem(person.getInitials(), person.getName(), person.getEmail());
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

    private Component createReports() {
        int reports = DummyData.getRandomInt(5000);
        String format = new DecimalFormat("###,###,###").format(reports);
        return UIUtils.createRightAlignedDiv(UIUtils.createLabel(Collections.singleton(LumoStyles.FontSize.H4), format));
    }

    private Component createCompanies() {
        return UIUtils.createRightAlignedDiv(UIUtils.createLabel(Collections.singleton(LumoStyles.FontSize.H4), String.valueOf(DummyData.getRandomInt(50))));
    }
}
