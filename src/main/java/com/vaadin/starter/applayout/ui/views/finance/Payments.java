package com.vaadin.starter.applayout.ui.views.finance;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.*;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.DetailsDrawer;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;
import com.vaadin.starter.applayout.ui.views.AbstractView;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "payments", layout = Root.class)
@PageTitle("Payments")
public class Payments extends AbstractView {

    private Random random = new Random();

    private AppBar appBar;

    private FlexLayout content;

    private Grid<Payment> grid;
    private ListDataProvider<Payment> dataProvider;

    private DetailsDrawer detailsDrawer;

    public Payments() {
        // Header
        appBar = new AppBar("Payments");

        // Main container
        content = new FlexLayout();
        content.setSizeFull();

        // Grid
        grid = new Grid();
        grid.addColumn(new ComponentRenderer<>(this::createStatus))
                .setHeader("Status")
                .setWidth("100px")
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createBankInfo))
                .setHeader("Bank")
                .setWidth("320px")
                .setFlexGrow(0);
        grid.addColumn(Payment::getPayer)
                .setHeader("Payer")
                .setWidth("200px");
        grid.addColumn(new ComponentRenderer<>(this::createAmount))
                .setHeader(UIUtils.createRightAlignedDiv(new Text("Amount (EUR)")))
                .setWidth("240px")
                .setFlexGrow(0);
        grid.addColumn(new LocalDateRenderer<>(Payment::getDate, "MMM dd, YYYY"))
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
        dataProvider = DataProvider.ofCollection(DummyData.getPayments());
        grid.setDataProvider(dataProvider);

        // Grid wrapper for some nice padding
        Div gridWrapper = UIUtils.createDiv(Collections.singleton(GRID_VIEW), grid);

        // Details drawer
        initDetailsDrawer();

        // Set the content's content.
        content.add(gridWrapper, detailsDrawer);
    }

    @Override
    protected void initSlots() {
        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            setHeader(appBar);
        }
        setContent(content);
    }

    private Component createStatus(Payment payment) {
        Payment.Status status = payment.getStatus();
        Span badge = new Span(status.getName());

        switch (status) {
            case PENDING:
                badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.DEFAULT);
                break;

            case OPEN:
                badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.CONTRAST);
                break;

            case SENT:
                badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.SUCCESS);
                break;

            default:
                badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.ERROR);
                break;
        }

        return badge;
    }

    private Component createBankInfo(Payment payment) {
        return new ListItem(payment.getAccount(), payment.getBank());
    }

    private Component createAmount(Payment payment) {
        return UIUtils.createRightAlignedDiv(UIUtils.createLabel(Arrays.asList(LumoStyles.FontSize.H4, LumoStyles.TextColor.SUCCESS), "+" + payment.getAmount()));
    }

    private void initDetailsDrawer() {
        detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

        // Footer
        Button cancel = UIUtils.createTertiaryButton("Cancel");
        cancel.addClickListener(e -> detailsDrawer.hide());

        Button save = UIUtils.createPrimaryButton("Save");

        FlexLayout footer = UIUtils.createFlexLayout(Arrays.asList(LumoStyles.Padding.All.S, LumoStyles.Spacing.Right.S), cancel, save);
        footer.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.CONTRAST_5);
        footer.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        footer.setWidth("100%");

        detailsDrawer.setFooter(footer);
    }

    private void showDetails(Payment payment) {
        detailsDrawer.setContent(createDetails(payment));
        detailsDrawer.show();
    }

    private Component createDetails(Payment payment) {
        H3 payer = new H3("Payer");

        TextField company = new TextField("Company");
        company.setValue(payment.getPayer());

        TextField account = new TextField("Account");
        account.setValue(payment.getAccount());

        TextField bank = new TextField("Bank");
        bank.setValue(payment.getBank());

        H3 details = new H3("Details");

        TextField message = new TextField("Message");
        message.setValue("Invoice " + random.nextInt(10000));

        DatePicker date = new DatePicker("Due Date");
        date.setValue(payment.getDate());

        FormLayout form = new FormLayout(payer, company, account, bank, details, message, date);
        form.addClassNames(LumoStyles.Padding.Bottom.L, LumoStyles.Padding.Horizontal.L);
        return form;
    }
}
