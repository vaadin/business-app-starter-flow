package com.vaadin.starter.business.ui.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
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
import com.vaadin.starter.business.backend.DummyData;
import com.vaadin.starter.business.backend.Payment;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.Badge;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.components.ListItem;
import com.vaadin.starter.business.ui.components.detailsdrawer.DetailsDrawer;
import com.vaadin.starter.business.ui.components.detailsdrawer.DetailsDrawerHeader;
import com.vaadin.starter.business.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.business.ui.layout.size.Bottom;
import com.vaadin.starter.business.ui.layout.size.Horizontal;
import com.vaadin.starter.business.ui.layout.size.Top;
import com.vaadin.starter.business.ui.util.FontSize;
import com.vaadin.starter.business.ui.util.LumoStyles;
import com.vaadin.starter.business.ui.util.TextColor;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.util.css.WhiteSpace;

@PageTitle("Payments")
@Route(value = "payments", layout = MainLayout.class)
public class Payments extends SplitViewFrame {

    private Grid<Payment> grid;
    private ListDataProvider<Payment> dataProvider;
    private DetailsDrawer detailsDrawer;

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        initAppBar();
        setViewContent(createContent());
        setViewDetails(createDetailsDrawer());
        filter();
    }

    private void initAppBar() {
        AppBar appBar = MainLayout.get().getAppBar();
        for (Payment.Status status : Payment.Status.values()) {
            appBar.addTab(status.getName());
        }
        appBar.addTabSelectionListener(e -> {
            filter();
            detailsDrawer.hide();
        });
        appBar.centerTabs();
    }

    private Component createContent() {
        FlexBoxLayout content = new FlexBoxLayout(createGrid());
        content.setBoxSizing(BoxSizing.BORDER_BOX);
        content.setHeightFull();
        content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
        return content;
    }

    private Grid createGrid() {
        dataProvider = DataProvider.ofCollection(DummyData.getPayments());

        grid = new Grid<>();
        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::showDetails));
        grid.setDataProvider(dataProvider);
        grid.setHeightFull();

        ComponentRenderer<Badge, Payment> badgeRenderer = new ComponentRenderer<>(
            payment -> {
                Payment.Status status = payment.getStatus();
                Badge badge = new Badge(status.getName(), status.getTheme());
                UIUtils.setTooltip(status.getDesc(), badge);
                return badge;
            }
        );
        grid.addColumn(badgeRenderer)
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setHeader("Status");
        grid.addColumn(new ComponentRenderer<>(this::createFromInfo))
                .setHeader("From")
                .setWidth("200px");
        grid.addColumn(new ComponentRenderer<>(this::createToInfo))
                .setHeader("To")
                .setWidth("200px");
        grid.addColumn(new ComponentRenderer<>(this::createAmount))
                .setAutoWidth(true)
                .setFlexGrow(0)
                .setHeader("Amount ($)")
                .setTextAlign(ColumnTextAlign.END);
        grid.addColumn(TemplateRenderer.<Payment> of("[[item.date]]")
                .withProperty("date", payment -> UIUtils.formatDate(payment.getDate())))
                .setAutoWidth(true)
                .setComparator(Payment::getDate)
                .setFlexGrow(0)
                .setHeader("Due Date");

        return grid;
    }

    private Component createFromInfo(Payment payment) {
        return new ListItem(payment.getFrom(), payment.getFromIBAN());
    }

    private Component createToInfo(Payment payment) {
        return new ListItem(payment.getTo(), payment.getToIBAN());
    }

    private Component createAmount(Payment payment) {
        Double amount = payment.getAmount();
        return UIUtils.createAmountLabel(amount);
    }

    private DetailsDrawer createDetailsDrawer() {
        detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

        // Header
        Tab details = new Tab("Details");
        Tab attachments = new Tab("Attachments");
        Tab history = new Tab("History");

        Tabs tabs = new Tabs(details, attachments, history);
        tabs.addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS);
        tabs.addSelectedChangeListener(e -> {
            Tab selectedTab = tabs.getSelectedTab();
            if (selectedTab.equals(details)) {
                detailsDrawer.setContent(createDetails(grid.getSelectionModel().getFirstSelectedItem().get()));
            } else if (selectedTab.equals(attachments)) {
                detailsDrawer.setContent(createAttachments());
            } else if (selectedTab.equals(history)) {
                detailsDrawer.setContent(createHistory());
            }
        });

        DetailsDrawerHeader detailsDrawerHeader = new DetailsDrawerHeader("Payment Details", tabs);
        detailsDrawerHeader.addCloseListener(buttonClickEvent -> detailsDrawer.hide());
        detailsDrawer.setHeader(detailsDrawerHeader);

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
        UIUtils.setTheme(payment.getStatus().getTheme().getThemeName(),
                status.getPrimary());
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

    private Component createAttachments() {
        Label message = UIUtils.createLabel(FontSize.S, TextColor.SECONDARY, "Not implemented yet.");
        message.addClassNames(LumoStyles.Padding.Responsive.Horizontal.L, LumoStyles.Padding.Vertical.L);
        return message;
    }

    private Component createHistory() {
        Label message = UIUtils.createLabel(FontSize.S, TextColor.SECONDARY, "Not implemented yet.");
        message.addClassNames(LumoStyles.Padding.Responsive.Horizontal.L, LumoStyles.Padding.Vertical.L);
        return message;
    }

    private void filter() {
        Tab selectedTab = MainLayout.get().getAppBar().getSelectedTab();
        if (selectedTab != null)
            dataProvider.setFilterByValue(Payment::getStatus, Payment.Status
                    .valueOf(selectedTab.getLabel().toUpperCase()));
    }
}
