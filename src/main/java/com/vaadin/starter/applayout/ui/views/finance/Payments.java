package com.vaadin.starter.applayout.ui.views.finance;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
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

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "payments", layout = Root.class)
@PageTitle("Payments")
public class Payments extends ViewFrame {

    private AppBar appBar;
    private final ListDataProvider<Payment> dataProvider;
    private DetailsDrawer detailsDrawer;


    public Payments() {
        // Header
        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            appBar = new AppBar("Payments");
            for (Payment.Status status : Payment.Status.values()) {
                appBar.addTab(status.getName());
            }
            appBar.addTabSelectionListener(e -> {
                hideDetails();
                filter();
            });
            appBar.centerTabs();
            setHeader(appBar);
        }

        // Grid
        Grid<Payment> grid = new Grid<>();
        dataProvider = DataProvider.ofCollection(DummyData.getPayments());
        grid.setDataProvider(dataProvider);

        grid.addColumn(new ComponentRenderer<>(UIUtils::createBadge))
                .setHeader("Status")
                .setWidth(UIUtils.COLUMN_WIDTH_S)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createFromInfo))
                .setHeader("From")
                .setWidth(UIUtils.COLUMN_WIDTH_XL);
        grid.addColumn(new ComponentRenderer<>(this::createToInfo))
                .setHeader("To")
                .setWidth(UIUtils.COLUMN_WIDTH_XL);
        grid.addColumn(new ComponentRenderer<>(this::createAmount))
                .setHeader(UIUtils.createRightAlignedDiv("Amount ($)"))
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.addColumn(TemplateRenderer.<Payment>of(
                "[[item.date]]")
                .withProperty("date", payment -> UIUtils.formatDate(payment.getDate())))
                .setHeader("Due Date")
                .setComparator(Payment::getDate)
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
        FlexLayout content = new FlexLayout(gridWrapper, detailsDrawer);
        content.setSizeFull();
        setContent(content);

        // Initial filtering
        filter();
    }

    private void filter() {
        dataProvider.setFilterByValue(Payment::getStatus, Payment.Status.valueOf(appBar.getSelectedTab().getLabel().toUpperCase()));
    }

    private Component createFromInfo(Payment payment) {
        return new ListItem(payment.getFrom(), payment.getFromIBAN());
    }

    private Component createToInfo(Payment payment) {
        return new ListItem(payment.getTo(), payment.getToIBAN());
    }

    private Component createAmount(Payment payment) {
        Double amount = payment.getAmount();
        Label label = UIUtils.createH4Label(UIUtils.formatAmount(amount));
        return UIUtils.createRightAlignedDiv(label);
    }

    private void initDetailsDrawer() {
        detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

        // Header
        Label detailsDrawerTitle = UIUtils.createDetailsDrawerHeader("Payment Details", false, true);

        Tabs tabs = new Tabs(new Tab("Details"), new Tab("Attachments"), new Tab("History"));
        tabs.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Tabs.EQUAL_WIDTH_TABS);

        detailsDrawer.setHeader(detailsDrawerTitle, tabs);
        detailsDrawer.getHeader().getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);

        // Footer
        Button close = UIUtils.createTertiaryButton("Close");
        close.addClickListener(e -> detailsDrawer.hide());

        FlexLayout footer = UIUtils.createFlexLayout(Arrays.asList(LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Vertical.S, LumoStyles.Spacing.Right.S), close);
        footer.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.CONTRAST_5);
        footer.setWidth("100%");

        detailsDrawer.setFooter(footer);
    }

    private void showDetails(Payment payment) {
        detailsDrawer.setContent(createDetails(payment));
        detailsDrawer.show();
    }

    private void hideDetails() {
        detailsDrawer.hide();
    }

    private Component createDetails(Payment payment) {
        ListItem status = new ListItem(payment.getStatus().getIcon(), payment.getStatus().getName(), "Status");
        status.addPrimaryClassNames(LumoStyles.Margin.Top.XS);
        status.setPrimaryElementAttribute(LumoStyles.THEME, payment.getStatus().getTheme());
        status.setPrimaryStyleProperty(CSSProperties.AlignSelf.PROPERTY, CSSProperties.AlignSelf.BASELINE);
        status.getElement().setProperty(CSSProperties.Title.PROPERTY, payment.getStatus().getDesc());

        ListItem from = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.UPLOAD_ALT), payment.getFrom() + "\n" + payment.getFromIBAN(), "Sender");
        ListItem to = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.DOWNLOAD_ALT), payment.getTo() + "\n" + payment.getToIBAN(), "Receiver");
        ListItem amount = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.DOLLAR), UIUtils.formatAmount(payment.getAmount()), "Amount");
        ListItem date = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CALENDAR), UIUtils.formatDate(payment.getDate()), "Date");

        for (ListItem item : new ListItem[]{status, from, to, amount, date}) {
            item.setReverse(true);
            item.getStyle().set(CSSProperties.WhiteSpace.PROPERTY, CSSProperties.WhiteSpace.PRE_LINE);
        }

        return UIUtils.createDiv(
                Collections.singleton(LumoStyles.Padding.Vertical.S),
                status,
                from,
                to,
                amount,
                date
        );
    }

}
