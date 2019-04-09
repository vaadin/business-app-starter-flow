package com.vaadin.starter.responsiveapptemplate.ui.views;

import java.time.format.DateTimeFormatter;

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
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.starter.responsiveapptemplate.backend.BankAccount;
import com.vaadin.starter.responsiveapptemplate.backend.DummyData;
import com.vaadin.starter.responsiveapptemplate.ui.MainLayout;
import com.vaadin.starter.responsiveapptemplate.ui.components.ListItem;
import com.vaadin.starter.responsiveapptemplate.ui.util.TextColor;
import com.vaadin.starter.responsiveapptemplate.ui.util.UIUtils;

@Route(value = "accounts", layout = MainLayout.class)
@ParentLayout(MainLayout.class)
@PageTitle("Accounts")
public class Accounts extends ViewFrame implements RouterLayout {

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
        grid.addSelectionListener(event -> event.getFirstSelectedItem()
                .ifPresent(this::viewDetails));
        grid.setDataProvider(
                DataProvider.ofCollection(DummyData.getBankAccounts()));
        grid.setSizeFull();

        grid.addColumn(BankAccount::getId).setFlexGrow(0).setFrozen(true)
                .setHeader("ID").setSortable(true)
                .setWidth(UIUtils.COLUMN_WIDTH_XS);
        grid.addColumn(new ComponentRenderer<>(this::createBankInfo))
                .setHeader("Bank Account").setWidth(UIUtils.COLUMN_WIDTH_XL);
        grid.addColumn(BankAccount::getOwner).setHeader("Owner")
                .setWidth(UIUtils.COLUMN_WIDTH_XL);
        grid.addColumn(new ComponentRenderer<>(this::createAvailability))
                .setFlexGrow(0).setHeader("Availability ($)")
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setTextAlign(ColumnTextAlign.END);
        grid.addColumn(new LocalDateRenderer<>(BankAccount::getUpdated,
                DateTimeFormatter.ofPattern("MMM dd, YYYY")))
                .setComparator(BankAccount::getUpdated).setFlexGrow(0)
                .setHeader("Updated").setWidth(UIUtils.COLUMN_WIDTH_M);

        return grid;
    }

    private Component createBankInfo(BankAccount bankAccount) {
        ListItem item = new ListItem(bankAccount.getAccount(),
                bankAccount.getBank());
        item.setHorizontalPadding(false);
        return item;
    }

    private Component createAvailability(BankAccount bankAccount) {
        Double availability = bankAccount.getAvailability();
        Label label = UIUtils.createAmountLabel(availability);

        if (availability > 0) {
            label.addClassName(TextColor.SUCCESS.getClassName());
        } else {
            label.addClassName(TextColor.ERROR.getClassName());
        }

        return label;
    }

    private void viewDetails(BankAccount bankAccount) {
        UI.getCurrent().navigate(AccountDetails.class, bankAccount.getId());
    }
}
