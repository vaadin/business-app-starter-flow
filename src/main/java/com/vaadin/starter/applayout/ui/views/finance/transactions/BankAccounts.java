package com.vaadin.starter.applayout.ui.views.finance.transactions;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.starter.applayout.backend.BankAccount;
import com.vaadin.starter.applayout.backend.DummyData;
import com.vaadin.starter.applayout.ui.components.DetailsDrawer;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.utils.BoxShadowBorders;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

import java.util.Arrays;
import java.util.Collections;

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.GRID_VIEW;

public class BankAccounts extends FlexLayout {

    private DetailsDrawer detailsDrawer;
    private Label detailsDrawerTitle;

    public BankAccounts() {
        setHeight("100%");

        // Grid
        Grid<BankAccount> grid = new Grid<>();
        grid.setDataProvider(DataProvider.ofCollection(DummyData.getBalances()));
        grid.addColumn(BankAccount::getId)
                .setHeader("ID")
                .setFrozen(true)
                .setSortable(true)
                .setWidth(UIUtils.COLUMN_WIDTH_XS)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createBankInfo))
                .setHeader("Bank Account")
                .setWidth(UIUtils.COLUMN_WIDTH_XL);
        grid.addColumn(BankAccount::getOwner)
                .setHeader("Owner")
                .setWidth(UIUtils.COLUMN_WIDTH_XL);
        grid.addColumn(new ComponentRenderer<>(this::createAvailability))
                .setHeader(UIUtils.createRightAlignedDiv("Availability ($)"))
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createDate))
                .setHeader(UIUtils.createRightAlignedDiv("Updated"))
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);

        grid.addSelectionListener(e -> {
            if (e.getFirstSelectedItem().isPresent()) {
                showDetails(e.getFirstSelectedItem().get());
            }
        });

        grid.setSizeFull();

        // Grid wrapper
        Div gridWrapper = UIUtils.createDiv(Collections.singleton(GRID_VIEW), grid);

        // Details drawer
        initDetailsDrawer();

        // Set the content
        add(gridWrapper, detailsDrawer);
    }

    private void initDetailsDrawer() {
        detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

        // Header
        detailsDrawerTitle = UIUtils.createDetailsDrawerHeader("", true, true);
        detailsDrawer.setHeader(detailsDrawerTitle);

        // Footer
        Button close = UIUtils.createTertiaryButton("Close");
        close.addClickListener(e -> detailsDrawer.hide());

        FlexLayout footer = UIUtils.createFlexLayout(Arrays.asList(LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Vertical.S, LumoStyles.Spacing.Right.S), close);
        footer.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.CONTRAST_5);
        footer.setWidth("100%");

        detailsDrawer.setFooter(footer);
    }

    private void showDetails(BankAccount bankAccount) {
        detailsDrawerTitle.setText(bankAccount.getOwner());
        detailsDrawer.setContent(createDetails());
        detailsDrawer.show();
    }

    private Div createDetails() {
        Div div = UIUtils.createDiv(Collections.singleton(LumoStyles.Padding.Vertical.S));

        for (int i = 0; i < 10; i++) {
            Double amount = DummyData.getAmount();

            ListItem item;
            if (amount > 0) {
                item = new ListItem(new Icon(VaadinIcon.PLUS), UIUtils.formatAmount(amount), DummyData.getCompany());
                item.addClassName(LumoStyles.TextColor.SUCCESS);
            } else {
                // With the list item's icon, the "-" for negative values becomes superfluous.
                item = new ListItem(new Icon(VaadinIcon.MINUS), UIUtils.formatAmount(amount * -1), DummyData.getCompany());
                item.addClassName(LumoStyles.TextColor.ERROR);
            }

            // Dividers for all but the last item. Last item has some bottom margin.
            if (i < 9) {
                item.setDividerVisible(true);
            }

            div.add(item);
        }

        return div;
    }

    private Component createBankInfo(BankAccount bankAccount) {
        return new ListItem(bankAccount.getAccount(), bankAccount.getBank());
    }

    private Component createAvailability(BankAccount bankAccount) {
        Double availability = bankAccount.getAvailability();
        Label label = UIUtils.createH4Label(UIUtils.formatAmount(availability));

        if (availability > 0) {
            label.addClassName(LumoStyles.TextColor.SUCCESS);
        } else {
            label.addClassName(LumoStyles.TextColor.ERROR);
        }

        return UIUtils.createRightAlignedDiv(label);
    }

    private Component createDate(BankAccount bankAccount) {
        return UIUtils.createRightAlignedDiv(UIUtils.formatDate(bankAccount.getUpdated()));
    }
}
