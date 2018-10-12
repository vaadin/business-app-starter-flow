package com.vaadin.starter.applayout.ui.views.finance.transactions;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.starter.applayout.backend.Balance;
import com.vaadin.starter.applayout.backend.DummyData;
import com.vaadin.starter.applayout.ui.components.DetailsDrawer;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

import java.util.Arrays;
import java.util.Collections;

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.GRID_VIEW;

public class BankAccounts extends FlexLayout {

    private Grid<Balance> grid;
    private ListDataProvider<Balance> dataProvider;

    private DetailsDrawer detailsDrawer;

    public BankAccounts() {
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
                .setHeader("Bank Account")
                .setWidth("320px")
                .setFlexGrow(0);
        grid.addColumn(Balance::getOwner)
                .setHeader("Owner")
                .setWidth("200px");
        grid.addColumn(new ComponentRenderer<>(this::createAvailability))
                .setHeader(UIUtils.createRightAlignedDiv(new Text("Availability (EUR)")))
                .setWidth("240px")
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
        initDetailsDrawer();

        // TBD
        add(gridWrapper, detailsDrawer);
    }

    private void initDetailsDrawer() {
        detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

        // Footer
        Button cancel = UIUtils.createTertiaryButton("Cancel");
        cancel.addClickListener(e -> detailsDrawer.hide());

        Button save = UIUtils.createPrimaryButton("Save");

        FlexLayout footer = UIUtils.createFlexLayout(Arrays.asList(LumoStyles.Padding.All.S, LumoStyles.Spacing.Right.S), cancel, save);
        footer.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.CONTRAST_5);
        footer.setJustifyContentMode(JustifyContentMode.END);
        footer.setWidth("100%");

        detailsDrawer.setFooter(footer);
    }

    private void showDetails(Balance balance) {
        detailsDrawer.setContent(createDetails(balance));
        detailsDrawer.show();
    }

    private FormLayout createDetails(Balance balance) {
        TextField id = new TextField("ID");
        id.setValue(String.valueOf(balance.getId()));

        H3 header1 = new H3("Header");

        TextField bank = new TextField("Bank");
        bank.setValue(balance.getBank());

        TextField account = new TextField("Account");
        account.setValue(balance.getAccount());

        TextField company = new TextField("Owner");
        company.setValue(balance.getOwner());

        H3 header2 = new H3("Header");

        TextField availability = new TextField("Availability (EUR)");
        availability.setValue(String.valueOf(balance.getAvailability()));

        DatePicker updated = new DatePicker("Updated");
        updated.setValue(balance.getUpdated());

        FormLayout form = new FormLayout(id, header1, bank, account, company, header2, availability, updated);
        form.addClassName(LumoStyles.Padding.All.L);
        return form;
    }

    private Component createBankInfo(Balance balance) {
        return new ListItem(balance.getAccount(), balance.getBank());
    }


    private Component createAvailability(Balance balance) {
        Double availability = balance.getAvailability();

        Label label = UIUtils.createLabel(Collections.singleton(LumoStyles.FontSize.H4), Double.toString(availability));

        if (availability > 0) {
            label.setText("+" + label.getText());
            label.addClassName(LumoStyles.TextColor.SUCCESS);
        } else {
            label.addClassName(LumoStyles.TextColor.ERROR);
        }

        return UIUtils.createRightAlignedDiv(label);
    }
}
