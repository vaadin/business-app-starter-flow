package com.vaadin.starter.business.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.BankAccount;
import com.vaadin.starter.business.backend.DummyData;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.ListItem;
import com.vaadin.starter.business.ui.util.TextColor;
import com.vaadin.starter.business.ui.util.UIUtils;

import java.time.format.DateTimeFormatter;

@Route(value = "accounts", layout = MainLayout.class)
@PageTitle("Accounts")
public class Accounts extends ViewFrame {

    private Grid<BankAccount> grid;

    public Accounts() {
        setViewContent(createContent());
    }

    private Component createContent() {
        Div content = new Div(createGrid());
        content.addClassName("grid-view");
        return content;
    }

    private Grid createGrid() {
        grid = new Grid<>();
        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::viewDetails));
        grid.setDataProvider(DataProvider.ofCollection(DummyData.getBankAccounts()));
        grid.setId("accounts");
        grid.setSizeFull();

        grid.addColumn(BankAccount::getId)
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setFrozen(true)
                .setHeader("ID")
                .setSortable(true);
        grid.addColumn(new ComponentRenderer<>(this::createBankInfo))
                .setComparator(BankAccount::getAccount)
                .setHeader("Bank Account")
                .setWidth("200px");
        grid.addColumn(BankAccount::getOwner)
                .setHeader("Owner")
                .setSortable(true)
                .setWidth("200px");
        grid.addColumn(new ComponentRenderer<>(this::createAvailability))
                .setAutoWidth(true)
                .setComparator(BankAccount::getAvailability)
                .setFlexGrow(0)
                .setHeader("Availability ($)")
                .setTextAlign(ColumnTextAlign.END);
        grid.addColumn(new LocalDateRenderer<>(BankAccount::getUpdated, DateTimeFormatter.ofPattern("MMM dd, YYYY")))
                .setAutoWidth(true)
                .setComparator(BankAccount::getUpdated)
                .setFlexGrow(0)
                .setHeader("Updated");

        return grid;
    }

    private Component createBankInfo(BankAccount bankAccount) {
        ListItem item = new ListItem(bankAccount.getAccount(), bankAccount.getBank());
        item.setHorizontalPadding(false);
        return item;
    }

    private Component createAvailability(BankAccount bankAccount) {
        Double availability = bankAccount.getAvailability();
        Label label = UIUtils.createAmountLabel(availability);

        if (availability > 0) {
            UIUtils.setTextColor(TextColor.SUCCESS, label);
        } else {
            UIUtils.setTextColor(TextColor.ERROR, label);
        }

        return label;
    }

    private void viewDetails(BankAccount bankAccount) {
        UI.getCurrent().navigate(AccountDetails.class, bankAccount.getId());
    }
}
