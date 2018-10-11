package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.datepicker.DatePicker;
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
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.Balance;
import com.vaadin.starter.applayout.backend.DummyData;
import com.vaadin.starter.applayout.backend.Statement;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.DetailsDrawer;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

import java.util.Collections;

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "account-reporting/balances", layout = AccountReporting.class)
public class Balances extends FlexLayout {

    private Grid<Balance> grid;
    private ListDataProvider<Balance> dataProvider;

    private final DetailsDrawer detailsDrawer;

    public Balances() {
        setHeight("100%");

        // Grid
        grid = new Grid();
        grid.addColumn(Balance::getId)
                .setHeader("ID")
                .setFrozen(true)
                .setSortable(true)
                .setWidth("60px")
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createBankInfo))
                .setHeader("Bank")
                .setWidth("320px")
                .setFlexGrow(0);
        grid.addColumn(Balance::getCompany)
                .setHeader("Company")
                .setWidth("200px");
        grid.addColumn(new ComponentRenderer<>(this::createAvailability))
                .setHeader(UIUtils.createRightAlignedDiv(new Text("Availability (EUR)")))
                .setWidth("140px")
                .setFlexGrow(0);
        grid.addColumn(new LocalDateRenderer<>(Balance::getUpdated, "MMM dd, YYYY"))
                .setHeader("Updated")
                .setWidth("140px")
                .setFlexGrow(0);


        grid.addSelectionListener(e -> {
            if (e.getFirstSelectedItem().isPresent()) {
                showDetails(e.getFirstSelectedItem().get());
            }
        });
        grid.setSizeFull();

        // Data provider
        dataProvider = DataProvider.ofCollection(DummyData.getBalances());
        grid.setDataProvider(dataProvider);

        // Grid wrapper for some nice padding
        Div gridWrapper = UIUtils.createDiv(Collections.singleton(GRID_VIEW), grid);

        // Details drawer
        detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

        // TBD
        add(gridWrapper, detailsDrawer);
    }

    private void showDetails(Balance balance) {
        detailsDrawer.setContent(createForm(balance));
        detailsDrawer.show();
    }

    private FormLayout createForm(Balance balance) {
        TextField id = new TextField("ID");
        id.setValue(String.valueOf(balance.getId()));

        TextField bank = new TextField("Bank");
        bank.setValue(balance.getBank());

        TextField account = new TextField("Account");
        account.setValue(balance.getAccount());

        TextField company = new TextField("Company");
        company.setValue(balance.getCompany());

        TextField availability = new TextField("Availability (EUR)");
        availability.setValue(String.valueOf(balance.getAvailability()));

        DatePicker updated = new DatePicker("Updated");
        updated.setValue(balance.getUpdated());

        return new FormLayout(id, bank, account, company, availability, updated);
    }

    private Component createBankInfo(Balance balance) {
        return new ListItem(balance.getBank(), balance.getAccount());
    }


    private Component createAvailability(Balance balance) {
        return UIUtils.createRightAlignedDiv(new Text(Double.toString(balance.getAvailability())));
    }
}
