package com.vaadin.starter.applayout.ui.views.finance;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.DummyData;
import com.vaadin.starter.applayout.backend.Payment;
import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.DetailsDrawer;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;
import com.vaadin.starter.applayout.ui.views.ViewFrame;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "payments", layout = Root.class)
@PageTitle("Payments")
public class Payments extends ViewFrame {

    private Random random = new Random();

    private DetailsDrawer detailsDrawer;

    public Payments() {
        // Header
        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            AppBar appBar = new AppBar("Payments");
            setHeader(appBar);
        }

        // Grid
        Grid<Payment> grid = new Grid<>();
        grid.setDataProvider(DataProvider.ofCollection(DummyData.getPayments()));

        grid.addColumn(new ComponentRenderer<>(UIUtils::createBadge))
                .setHeader("Status")
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createFromInfo))
                .setHeader("From")
                .setWidth(UIUtils.COLUMN_WIDTH_L);
        grid.addColumn(new ComponentRenderer<>(this::createToInfo))
                .setHeader("To")
                .setWidth(UIUtils.COLUMN_WIDTH_L);
        grid.addColumn(new ComponentRenderer<>(this::createAmount))
                .setHeader(UIUtils.createRightAlignedDiv(new Text("Amount (EUR)")))
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.addColumn(new LocalDateRenderer<>(Payment::getDate, "MMM dd, YYYY"))
                .setHeader("Date")
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

    private Component createFromInfo(Payment payment) {
        return new ListItem(payment.getFrom(), payment.getFromIBAN());
    }

    private Component createToInfo(Payment payment) {
        return new ListItem(payment.getTo(), payment.getToIBAN());
    }

    private Component createAmount(Payment payment) {
        Double amount = payment.getAmount();
        Label label = UIUtils.createLabel(Collections.singleton(LumoStyles.FontSize.H4), UIUtils.formatAmount(amount));
        return UIUtils.createRightAlignedDiv(label);
    }

    private void initDetailsDrawer() {
        detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

        // Footer
        Button close = UIUtils.createTertiaryButton("Close");
        close.addClickListener(e -> detailsDrawer.hide());

        FlexLayout footer = UIUtils.createFlexLayout(Arrays.asList(LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Vertical.S), close);
        footer.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.CONTRAST_5);
        footer.setWidth("100%");

        detailsDrawer.setFooter(footer);
    }

    private void showDetails(Payment payment) {
        detailsDrawer.setContent(createDetails(payment));
        detailsDrawer.show();
    }

    private Component createDetails(Payment payment) {
        /* === FROM === */
        H3 fromHeader = new H3("From");

        TextField from = new TextField("From");
        from.setValue(payment.getFrom());

        TextField fromIBAN = new TextField("IBAN");
        fromIBAN.setValue(payment.getFromIBAN());

        /* === TO === */
        H3 toHeader = new H3("To");

        TextField to = new TextField("To");
        to.setValue(payment.getTo());

        TextField toIBAN = new TextField("IBAN");
        toIBAN.setValue(payment.getToIBAN());

        /* === MISC === */
        H3 misc = new H3("Misc");

        TextArea message = new TextArea("Message");
        message.setValue("Invoice " + random.nextInt(10000));

        DatePicker date = new DatePicker("Date");
        date.setValue(payment.getDate());

        // Set everything to read-only.
        for (HasValueAndElement field : new HasValueAndElement[]{from, fromIBAN, to, toIBAN, message, date}) {
            field.setReadOnly(true);
        }

        FormLayout form = new FormLayout(fromHeader, from, fromIBAN, toHeader, to, toIBAN, misc, message, date);
        form.addClassNames(LumoStyles.Padding.Bottom.L, LumoStyles.Padding.Horizontal.L);
        return form;
    }
}
