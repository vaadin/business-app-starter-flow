package com.vaadin.starter.applayout.ui.views.finance.transactions;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
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
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
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

    private Grid<Transaction> grid;
    private ListDataProvider<Transaction> dataProvider;

    private DetailsDrawer detailsDrawer;

    public AllTransactions() {
        setHeight("100%");

        // Grid
        grid = new Grid();
        grid.addColumn(Transaction::getId)
                .setHeader("ID")
                .setFrozen(true)
                .setSortable(true)
                .setWidth("60px")
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createStatus))
                .setHeader("Status")
                .setWidth("120px")
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createPayeePayerInfo))
                .setHeader("Payee / Payer")
                .setSortable(true)
                .setWidth("240px")
                .setFlexGrow(1);
        grid.addColumn(new ComponentRenderer<>(this::createAmount))
                .setHeader(UIUtils.createRightAlignedDiv(new Text("Amount (EUR)")))
                .setWidth("240px")
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createAttachment))
                .setHeader("Attachment")
                .setWidth("120px")
                .setFlexGrow(0);
        grid.addColumn(new LocalDateRenderer<>(Transaction::getDate, "MMM dd, YYYY"))
                .setHeader("Date")
                .setWidth("140px")
                .setFlexGrow(0);

        grid.addSelectionListener(e -> {
            if (e.getFirstSelectedItem().isPresent()) {
                showDetails(e.getFirstSelectedItem().get());
            }
        });

        grid.setSizeFull();

        // Data provider
        dataProvider = DataProvider.ofCollection(DummyData.getTransactions());
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

        Tabs tabs = new Tabs(new Tab("Details"), new Tab("Attachments"), new Tab("History"));
        tabs.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Tabs.EQUAL_WIDTH_TABS);
        detailsDrawer.setHeader(tabs);

        Button cancel = UIUtils.createTertiaryButton("Cancel");
        cancel.addClickListener(e -> detailsDrawer.hide());

        Button save = UIUtils.createPrimaryButton("Save");

        FlexLayout footer = UIUtils.createFlexLayout(Arrays.asList(LumoStyles.Padding.All.S, LumoStyles.Spacing.Right.S), cancel, save);
        footer.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.CONTRAST_5);
        footer.setJustifyContentMode(JustifyContentMode.END);
        footer.setWidth("100%");
        detailsDrawer.setFooter(footer);
    }

    private void showDetails(Transaction transaction) {
        detailsDrawer.setContent(createDetails(transaction));
        detailsDrawer.show();
    }

    private Component createDetails(Transaction transaction) {
        FormLayout form = new FormLayout();
        form.addClassName(LumoStyles.Padding.All.L);

        TextField id = new TextField("ID");
        id.setValue(String.valueOf(transaction.getId()));
        form.add(id);

        RadioButtonGroup<Transaction.Status> status = new RadioButtonGroup();
        status.setItems(Transaction.Status.values());
        status.setRenderer(new TextRenderer<>(Transaction.Status::getName));
        status.setValue(transaction.getStatus());
        status.getStyle().set(CSSProperties.Display.PROPERTY, CSSProperties.Display.FLEX);
        status.getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);

        FormLayout.FormItem statusItem = form.addFormItem(status, "Status");
        statusItem.getStyle().set(CSSProperties.Display.PROPERTY, CSSProperties.Display.FLEX);
        statusItem.getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);

        TextField sender = new TextField("Payee / Payer");
        sender.setValue(transaction.getCompany());

        TextField amount = new TextField("Amount (EUR)");
        amount.setValue(String.valueOf(transaction.getAmount()));

        DatePicker date = new DatePicker("Date");
        date.setValue(transaction.getDate());

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
        Label label = UIUtils.createLabel(Collections.singleton(LumoStyles.FontSize.H4), String.valueOf(transaction.getAmount()));

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
        return icon;
    }
}
