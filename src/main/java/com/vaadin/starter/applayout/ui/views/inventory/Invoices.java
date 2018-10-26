package com.vaadin.starter.applayout.ui.views.inventory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.*;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.DetailsDrawer;
import com.vaadin.starter.applayout.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;
import com.vaadin.starter.applayout.ui.views.ViewFrame;

import java.util.Arrays;
import java.util.Collections;

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "invoices", layout = Root.class)
@PageTitle("Invoices")
public class Invoices extends ViewFrame {

    private DetailsDrawer detailsDrawer;

    public Invoices() {
        // Header
        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            setHeader(new AppBar("Invoices"));
        }

        // Grid
        Grid<Invoice> grid = new Grid<>();
        grid.setDataProvider(DataProvider.ofCollection(DummyData.getInvoices()));

        grid.addColumn(Invoice::getId)
                .setHeader("ID")
                .setWidth(UIUtils.COLUMN_WIDTH_S)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(UIUtils::createBadge))
                .setHeader("Status")
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.addColumn(Invoice::getCustomer)
                .setHeader("Customer")
                .setWidth(UIUtils.COLUMN_WIDTH_L);
        grid.addColumn(new ComponentRenderer<>(this::createAmount))
                .setHeader(UIUtils.createRightAlignedDiv("Amount"))
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createDate))
                .setHeader(UIUtils.createRightAlignedDiv("Due Date"))
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

        // Set the content
        FlexLayout content = new FlexLayout(gridWrapper, detailsDrawer);
        content.setSizeFull();
        setContent(content);
    }

    private Component createAmount(Invoice invoice) {
        Double amount = invoice.getAmount();
        Label label = UIUtils.createH4Label(UIUtils.formatAmount(amount));
        return UIUtils.createRightAlignedDiv(label);
    }

    private Component createDate(Invoice invoice) {
        return UIUtils.createRightAlignedDiv(UIUtils.formatDate(invoice.getDueDate()));
    }

    private void initDetailsDrawer() {
        detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

        // Footer
        Button save = UIUtils.createPrimaryButton("Save");

        Button cancel = UIUtils.createTertiaryButton("Cancel");
        cancel.addClickListener(e -> detailsDrawer.hide());

        FlexLayout footer = UIUtils.createFlexLayout(Arrays.asList(LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Vertical.S, LumoStyles.Spacing.Right.S), save, cancel);
        footer.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.CONTRAST_5);
        footer.setWidth("100%");

        detailsDrawer.setFooter(footer);
    }

    private void showDetails(Invoice invoice) {
        detailsDrawer.setContent(createDetails(invoice));
        detailsDrawer.show();
    }

    private Component createDetails(Invoice invoice) {
        TextField id = new TextField("Invoice ID");
        id.setValue(String.valueOf(invoice.getId()));

        TextField customer = new TextField("Customer");
        customer.setValue(String.valueOf(invoice.getCustomer()));

        DatePicker invoiceDate = new DatePicker("Invoice Date");
        invoiceDate.setValue(invoice.getInvoiceDate());

        TextField amount = new TextField("Amount (EUR)");
        amount.setValue(UIUtils.formatAmount(invoice.getAmount()));

        RadioButtonGroup<Invoice.Status> status = new RadioButtonGroup<>();
        status.setItems(Invoice.Status.values());
        status.setValue(invoice.getStatus());
        status.setRenderer(new ComponentRenderer<>(item -> UIUtils.createBadge(item)));
        status.getStyle().set(CSSProperties.Display.PROPERTY, CSSProperties.Display.FLEX);
        status.getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);

        DatePicker dueDate = new DatePicker("Due Date");
        dueDate.setValue(invoice.getDueDate());

        // Read-only fields.
        for (HasValue component : new HasValue[]{id, customer, invoiceDate, amount}) {
            component.setReadOnly(true);
        }

        // Add it all together.
        FormLayout form = UIUtils.createFormLayout(Collections.singleton(LumoStyles.Padding.All.L), id, customer, invoiceDate, amount);
        form.addFormItem(status, "Status").getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);
        form.add(dueDate);

        return form;
    }

}
