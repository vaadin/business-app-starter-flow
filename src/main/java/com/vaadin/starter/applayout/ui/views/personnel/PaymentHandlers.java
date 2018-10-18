package com.vaadin.starter.applayout.ui.views.personnel;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
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
import com.vaadin.starter.applayout.ui.views.AbstractView;

import java.util.Collections;

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "payment-handlers", layout = Root.class)
@PageTitle("Payment Handlers")
public class PaymentHandlers extends AbstractView {

    private AppBar appBar;

    private Grid<Person> grid;
    private ListDataProvider<Person> dataProvider;

    public PaymentHandlers() {
        // Header
        appBar = new AppBar("PaymentHandlers");

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
        grid.addColumn(new ComponentRenderer<>(this::createProcessedPayments))
                .setHeader(UIUtils.createRightAlignedDiv(new Text("Processed Payments (€)")))
                .setWidth(UIUtils.COLUMN_WIDTH_L)
                .setFlexGrow(0);
        grid.addColumn(new LocalDateRenderer<>(Person::getLastModified, "MMM dd, YYYY"))
                .setHeader("Last Modified")
                .setSortable(true)
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.setSizeFull();

        dataProvider = DataProvider.ofCollection(DummyData.getPersons());
        grid.setDataProvider(dataProvider);

        filter();
    }

    @Override
    protected void initSlots() {
        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            setHeader(appBar);
        }
        setContent(grid);
        getContent().addClassName(GRID_VIEW);
    }

    private void filter() {
        dataProvider.setFilterByValue(Person::getRole, Person.Role.PAYMENT_HANDLER);
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

    private Component createProcessedPayments(Person person) {
        int amount = person.getRandomInteger() > 0 ? person.getRandomInteger() : 0;

        Label label = UIUtils.createLabel(Collections.singleton(LumoStyles.FontSize.H4), UIUtils.formatAmount(amount));
        label.addClassName(amount > 0 ? LumoStyles.TextColor.SUCCESS : LumoStyles.TextColor.ERROR);

        return UIUtils.createRightAlignedDiv(label);
    }
}
