package com.vaadin.starter.applayout.ui.views.finance.transactions;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.starter.applayout.backend.BankAccount;
import com.vaadin.starter.applayout.backend.DummyData;
import com.vaadin.starter.applayout.backend.Transaction;
import com.vaadin.starter.applayout.ui.components.DetailsDrawer;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.GRID_VIEW;

public class BankAccounts extends FlexLayout {

    private Random random = new Random();

    private Grid<BankAccount> grid;
    private ListDataProvider<BankAccount> dataProvider;

    private DetailsDrawer detailsDrawer;

    public BankAccounts() {
        setHeight("100%");

        // Grid
        grid = new Grid();
        grid.addColumn(BankAccount::getId)
                .setHeader("ID")
                .setFrozen(true)
                .setSortable(true)
                .setWidth(UIUtils.COLUMN_WIDTH_S)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createBankInfo))
                .setHeader("Bank Account")
                .setWidth(UIUtils.COLUMN_WIDTH_L);
        grid.addColumn(BankAccount::getOwner)
                .setHeader("Owner")
                .setWidth(UIUtils.COLUMN_WIDTH_L);
        grid.addColumn(new ComponentRenderer<>(this::createAvailability))
                .setHeader(UIUtils.createRightAlignedDiv(new Text("Availability (EUR)")))
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.addColumn(new LocalDateRenderer<>(BankAccount::getUpdated, "MMM dd, YYYY"))
                .setHeader("Updated")
                .setWidth(UIUtils.COLUMN_WIDTH_M)
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

        FlexLayout footer = UIUtils.createFlexLayout(Arrays.asList(LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Vertical.S, LumoStyles.Spacing.Right.S), save, cancel);
        footer.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.CONTRAST_5);
        footer.setWidth("100%");

        detailsDrawer.setFooter(footer);
    }

    private void showDetails(BankAccount bankAccount) {
        detailsDrawer.setContent(createDetails(bankAccount));
        detailsDrawer.show();
    }

    private Div createDetails(BankAccount bankAccount) {
        Div div = new Div();

        H4 title = new H4("Transactions");
        title.addClassName(LumoStyles.Margin.Responsive.Horizontal.ML);
        div.add(title);

        for (int i = 0; i < 10; i++) {
            Double amount = DummyData.getAmount();
            boolean positive = amount > 0;

            // Formatting reasons. We have the "-" icon, we don't need the "-" in the actual value.
            String value = String.valueOf(positive ? amount : amount * -1);

            ListItem item = new ListItem(positive ? VaadinIcon.PLUS_CIRCLE : VaadinIcon.MINUS_CIRCLE, value, DummyData.getCompany());
            item.addClassName(positive ? LumoStyles.TextColor.SUCCESS : LumoStyles.TextColor.ERROR);
            item.setDividerVisible(true);

            div.add(item);
        }

        return div;
    }

    private Component createBankInfo(BankAccount bankAccount) {
        return new ListItem(bankAccount.getAccount(), bankAccount.getBank());
    }


    private Component createAvailability(BankAccount bankAccount) {
        Double availability = bankAccount.getAvailability();

        Label label = UIUtils.createLabel(Collections.singleton(LumoStyles.FontSize.H4), UIUtils.formatAmount(availability));

        if (availability > 0) {
            label.setText("+" + label.getText());
            label.addClassName(LumoStyles.TextColor.SUCCESS);
        } else {
            label.addClassName(LumoStyles.TextColor.ERROR);
        }

        return UIUtils.createRightAlignedDiv(label);
    }
}
