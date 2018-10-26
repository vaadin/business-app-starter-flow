package com.vaadin.starter.applayout.ui.views.finance.transactions;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.starter.applayout.backend.DummyData;
import com.vaadin.starter.applayout.backend.Transaction;
import com.vaadin.starter.applayout.ui.components.DetailsDrawer;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

import java.util.Arrays;
import java.util.Collections;

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.GRID_VIEW;

public class AllTransactions extends FlexLayout {

    private DetailsDrawer detailsDrawer;

    public AllTransactions() {
        setHeight("100%");

        // Grid
        Grid<Transaction> grid = new Grid<>();
        grid.setDataProvider(DataProvider.ofCollection(DummyData.getTransactions()));

        grid.addColumn(Transaction::getId)
                .setHeader("ID")
                .setFrozen(true)
                .setSortable(true)
                .setWidth(UIUtils.COLUMN_WIDTH_XS)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createStatus))
                .setHeader("Status")
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createPayeePayerInfo))
                .setHeader("Payee / Payer")
                .setSortable(true)
                .setWidth(UIUtils.COLUMN_WIDTH_L);
        grid.addColumn(new ComponentRenderer<>(this::createAmount))
                .setHeader(UIUtils.createRightAlignedDiv("Amount (EUR)"))
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createAttachment))
                .setHeader(UIUtils.createRightAlignedDiv("Attachment"))
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createDate))
                .setHeader(UIUtils.createRightAlignedDiv("Date"))
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);

        grid.addSelectionListener(e -> {
            if (e.getFirstSelectedItem().isPresent()) {
                showDetails(e.getFirstSelectedItem().get());
            }
        });

        grid.setSizeFull();

        // Grid wrapper for some nice padding
        Div gridWrapper = UIUtils.createDiv(Collections.singleton(GRID_VIEW), grid);

        // Details drawer
        initDetailsDrawer();

        // TBD
        add(gridWrapper, detailsDrawer);
    }

    private void initDetailsDrawer() {
        detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

        Tabs tabs = new Tabs(new Tab("Details"), new Tab("Attachments"), new Tab("History"));
        tabs.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Tabs.EQUAL_WIDTH_TABS);
        detailsDrawer.setHeader(tabs);

        Button cancel = UIUtils.createTertiaryButton("Cancel");
        cancel.addClickListener(e -> detailsDrawer.hide());

        Button save = UIUtils.createPrimaryButton("Save");

        FlexLayout footer = UIUtils.createFlexLayout(Arrays.asList(LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Vertical.S, LumoStyles.Spacing.Right.S), save, cancel);
        footer.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.CONTRAST_5);
        footer.setWidth("100%");
        detailsDrawer.setFooter(footer);
    }

    private void showDetails(Transaction transaction) {
        detailsDrawer.setContent(createDetails(transaction));
        detailsDrawer.show();
    }

    private Component createDetails(Transaction transaction) {
        TextField id = new TextField("ID");
        id.setValue(String.valueOf(transaction.getId()));

        RadioButtonGroup<Transaction.Status> status = new RadioButtonGroup<>();
        status.setItems(Transaction.Status.values());
        status.setRenderer(new TextRenderer<>(Transaction.Status::getName));
        status.setValue(transaction.getStatus());
        status.getStyle().set(CSSProperties.Display.PROPERTY, CSSProperties.Display.FLEX);
        status.getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);

        TextField sender = new TextField("Payee / Payer");
        sender.setValue(transaction.getCompany());

        TextField amount = new TextField("Amount (EUR)");
        amount.setValue(UIUtils.formatAmount(transaction.getAmount()));

        DatePicker date = new DatePicker("Date");
        date.setValue(transaction.getDate());

        FormLayout form = UIUtils.createFormLayout(Collections.singleton(LumoStyles.Padding.All.L), id);
        form.addFormItem(status, "Status").getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);
        form.add(sender, amount, date);

        return form;
    }

    private Component createStatus(Transaction transaction) {
        Transaction.Status status = transaction.getStatus();
        Span badge = new Span(status.getName());
        if (status.equals(Transaction.Status.PROCESSED)) {
            badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.DEFAULT);
        } else {
            badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.CONTRAST);
        }
        return badge;
    }

    private Component createPayeePayerInfo(Transaction transaction) {
        return new ListItem(transaction.getCompany(), transaction.getIBAN());
    }

    private Component createAmount(Transaction transaction) {
        Label label = UIUtils.createH4Label(UIUtils.formatAmount(transaction.getAmount()));
        if (transaction.getAmount() < 0) {
            label.addClassName(LumoStyles.TextColor.ERROR);
        } else {
            label.addClassName(LumoStyles.TextColor.SUCCESS);
        }
        return UIUtils.createRightAlignedDiv(label);
    }

    private Component createAttachment(Transaction transaction) {
        Icon icon = new Icon(VaadinIcon.FILE);
        if (transaction.hasAttachment()) {
            icon.addClassName(LumoStyles.TextColor.PRIMARY);
        } else {
            icon.addClassName(LumoStyles.TextColor.DISABLED);
        }
        return UIUtils.createRightAlignedDiv(icon);
    }

    private Component createDate(Transaction transaction) {
        return UIUtils.createRightAlignedDiv(UIUtils.formatDate(transaction.getDate()));
    }
}
