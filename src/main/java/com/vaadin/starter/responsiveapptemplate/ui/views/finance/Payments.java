package com.vaadin.starter.responsiveapptemplate.ui.views.finance;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.responsiveapptemplate.backend.DummyData;
import com.vaadin.starter.responsiveapptemplate.backend.Payment;
import com.vaadin.starter.responsiveapptemplate.backend.UIConfig;
import com.vaadin.starter.responsiveapptemplate.ui.Root;
import com.vaadin.starter.responsiveapptemplate.ui.components.ListItem;
import com.vaadin.starter.responsiveapptemplate.ui.components.detailsdrawer.DetailsDrawer;
import com.vaadin.starter.responsiveapptemplate.ui.components.detailsdrawer.DetailsDrawerFooter;
import com.vaadin.starter.responsiveapptemplate.ui.components.detailsdrawer.DetailsDrawerHeader;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.responsiveapptemplate.ui.layout.FlexDirection;
import com.vaadin.starter.responsiveapptemplate.ui.layout.WhiteSpace;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Bottom;
import com.vaadin.starter.responsiveapptemplate.ui.utils.FontSize;
import com.vaadin.starter.responsiveapptemplate.ui.utils.LumoStyles;
import com.vaadin.starter.responsiveapptemplate.ui.utils.TextColor;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;
import com.vaadin.starter.responsiveapptemplate.ui.views.SplitViewFrame;

import static com.vaadin.starter.responsiveapptemplate.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "payments", layout = Root.class)
@PageTitle("Payments")
public class Payments extends SplitViewFrame {

    private AppBar appBar;
    private Grid<Payment> grid;
    private ListDataProvider<Payment> dataProvider;
    private DetailsDrawer detailsDrawer;

    public Payments() {
        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            setViewHeader(createAppBar());
        }
        setViewContent(createContent());
        setViewDetails(createDetailsDrawer());

        UIUtils.createLabel(FontSize.XS, TextColor.SECONDARY, "I rule!");
        UIUtils.createLabel(FontSize.XS, TextColor.ERROR, "I rule!");

        filter();
    }

    private AppBar createAppBar() {
        appBar = new AppBar("Payments");
        for (Payment.Status status : Payment.Status.values()) {
            appBar.addTab(status.getName());
        }
        appBar.addTabSelectionListener(e -> {
            filter();
            detailsDrawer.hide();
        });
        appBar.centerTabs();
        return appBar;
    }

    private Component createContent() {
        Div content = new Div(createGrid());
        content.addClassName(GRID_VIEW);
        return content;
    }

    private Grid createGrid() {
        grid = new Grid<>();
        grid.addSelectionListener(event -> event.getFirstSelectedItem()
                .ifPresent(this::showDetails));
        dataProvider = DataProvider.ofCollection(DummyData.getPayments());
        grid.setDataProvider(dataProvider);
        grid.setHeight("100%");

        grid.addColumn(new ComponentRenderer<>(UIUtils::createBadge))
                .setHeader("Status").setWidth(UIUtils.COLUMN_WIDTH_S)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createFromInfo))
                .setHeader("From").setWidth(UIUtils.COLUMN_WIDTH_XL);
        grid.addColumn(new ComponentRenderer<>(this::createToInfo))
                .setHeader("To").setWidth(UIUtils.COLUMN_WIDTH_XL);
        grid.addColumn(new ComponentRenderer<>(this::createAmount))
                .setHeader("Amount ($)").setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0).setTextAlign(ColumnTextAlign.END);
        grid.addColumn(TemplateRenderer.<Payment> of("[[item.date]]")
                .withProperty("date",
                        payment -> UIUtils.formatDate(payment.getDate())))
                .setHeader("Due Date").setComparator(Payment::getDate)
                .setWidth(UIUtils.COLUMN_WIDTH_M).setFlexGrow(0);

        return grid;
    }

    private Component createFromInfo(Payment payment) {
        ListItem item = new ListItem(payment.getFrom(), payment.getFromIBAN());
        item.setHorizontalPadding(false);
        return item;
    }

    private Component createToInfo(Payment payment) {
        ListItem item = new ListItem(payment.getTo(), payment.getToIBAN());
        item.setHorizontalPadding(false);
        return item;
    }

    private Component createAmount(Payment payment) {
        Double amount = payment.getAmount();
        return UIUtils.createAmountLabel(amount);
    }

    private DetailsDrawer createDetailsDrawer() {
        detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

        // Header
        DetailsDrawerHeader detailsDrawerTitle = new DetailsDrawerHeader(
                "Payment Details", true);

        Tabs tabs = new Tabs(new Tab("Details"), new Tab("Attachments"),
                new Tab("History"));
        tabs.addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS);

        detailsDrawer.setHeader(detailsDrawerTitle, tabs);
        detailsDrawer.getHeader().setFlexDirection(FlexDirection.COLUMN);

        // Footer
        DetailsDrawerFooter footer = new DetailsDrawerFooter();
        footer.addCancelListener(e -> detailsDrawer.hide());
        detailsDrawer.setFooter(footer);

        return detailsDrawer;
    }

    private void showDetails(Payment payment) {
        detailsDrawer.setContent(createDetails(payment));
        detailsDrawer.show();
    }

    private Component createDetails(Payment payment) {
        ListItem status = new ListItem(payment.getStatus().getIcon(),
                payment.getStatus().getName(), "Status");

        status.getContent().setAlignItems(FlexComponent.Alignment.BASELINE);
        status.getContent().setSpacing(Bottom.XS);
        UIUtils.setTheme(payment.getStatus().getTheme(), status.getPrimary());
        UIUtils.setTooltip(payment.getStatus().getDesc(), status);

        ListItem from = new ListItem(
                UIUtils.createTertiaryIcon(VaadinIcon.UPLOAD_ALT),
                payment.getFrom() + "\n" + payment.getFromIBAN(), "Sender");
        ListItem to = new ListItem(
                UIUtils.createTertiaryIcon(VaadinIcon.DOWNLOAD_ALT),
                payment.getTo() + "\n" + payment.getToIBAN(), "Receiver");
        ListItem amount = new ListItem(
                UIUtils.createTertiaryIcon(VaadinIcon.DOLLAR),
                UIUtils.formatAmount(payment.getAmount()), "Amount");
        ListItem date = new ListItem(
                UIUtils.createTertiaryIcon(VaadinIcon.CALENDAR),
                UIUtils.formatDate(payment.getDate()), "Date");

        for (ListItem item : new ListItem[] { status, from, to, amount,
                date }) {
            item.setReverse(true);
            item.setWhiteSpace(WhiteSpace.PRE_LINE);
        }

        Div details = new Div(status, from, to, amount, date);
        details.addClassName(LumoStyles.Padding.Vertical.S);
        return details;
    }

    private void filter() {
        if (appBar != null) {
            dataProvider.setFilterByValue(Payment::getStatus, Payment.Status
                    .valueOf(appBar.getSelectedTab().getLabel().toUpperCase()));
        }
    }
}
