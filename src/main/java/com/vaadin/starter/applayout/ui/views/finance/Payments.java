package com.vaadin.starter.applayout.ui.views.finance;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.DummyData;
import com.vaadin.starter.applayout.backend.Payment;
import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.DetailsDrawer;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.applayout.ui.utils.BoxShadowBorders;
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
    private Label detailsDrawerTitle;

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
                .setWidth(UIUtils.COLUMN_WIDTH_S)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createFromInfo))
                .setHeader("From")
                .setWidth(UIUtils.COLUMN_WIDTH_XL);
        grid.addColumn(new ComponentRenderer<>(this::createToInfo))
                .setHeader("To")
                .setWidth(UIUtils.COLUMN_WIDTH_XL);
        grid.addColumn(new ComponentRenderer<>(this::createAmount))
                .setHeader(UIUtils.createRightAlignedDiv("Amount (EUR)"))
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
        Label label = UIUtils.createH4Label(UIUtils.formatAmount(amount));
        return UIUtils.createRightAlignedDiv(label);
    }

    private Component createDate(Payment payment) {
        return UIUtils.createRightAlignedDiv(UIUtils.formatDate(payment.getDate()));
    }

    private void initDetailsDrawer() {
        detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

        // Header
        detailsDrawerTitle = UIUtils.createH3Label(Arrays.asList(BoxShadowBorders.BOTTOM, LumoStyles.Padding.All.L), "");
        detailsDrawerTitle.setWidth("100%");
        detailsDrawer.setHeader(detailsDrawerTitle);

        // Footer
        Button close = UIUtils.createTertiaryButton("Close");
        close.addClickListener(e -> detailsDrawer.hide());

        FlexLayout footer = UIUtils.createFlexLayout(Arrays.asList(LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Vertical.S, LumoStyles.Spacing.Right.S), close);
        footer.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.CONTRAST_5);
        footer.setWidth("100%");

        detailsDrawer.setFooter(footer);
    }

    private void showDetails(Payment payment) {
        detailsDrawerTitle.setText("Payment Details");
        detailsDrawer.setContent(createDetails(payment));
        detailsDrawer.show();
    }

    private Component createDetails(Payment payment) {
        ListItem status = new ListItem(getStatusIcon(payment), payment.getStatus().getName(), "Status");
        status.addPrimaryClassNames(LumoStyles.Margin.Top.XS);
        status.setPrimaryAttribute(LumoStyles.THEME, getStatusTheme(payment));
        status.setPrimaryProperty(CSSProperties.AlignSelf.PROPERTY, CSSProperties.AlignSelf.BASELINE);

        ListItem from = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.UPLOAD_ALT), payment.getFrom() + "\n" + payment.getFromIBAN(), "Sender");
        ListItem to = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.DOWNLOAD_ALT), payment.getTo() + "\n" + payment.getToIBAN(), "Receiver");
        ListItem amount = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.EURO), UIUtils.formatAmount(payment.getAmount()), "Amount");
        ListItem date = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CALENDAR), UIUtils.formatDate(payment.getDate()), "Date");

        for (ListItem item : new ListItem[]{status, from, to, amount, date}) {
            item.setReverse(true);
            item.getStyle().set(CSSProperties.WhiteSpace.PROPERTY, CSSProperties.WhiteSpace.PRE_LINE);
        }

        return UIUtils.createDiv(
                Arrays.asList(LumoStyles.Padding.Vertical.S),
                status,
                from,
                to,
                amount,
                date
        );
    }

    private Icon getStatusIcon(Payment payment) {
        Icon icon;
        switch (payment.getStatus()) {
            case PENDING:
                icon = UIUtils.createTertiaryIcon(VaadinIcon.CLOCK);
                break;
            case OPEN:
                icon = UIUtils.createPrimaryIcon(VaadinIcon.QUESTION_CIRCLE);
                break;
            case SENT:
                icon = UIUtils.createSuccessIcon(VaadinIcon.CHECK);
                break;
            default:
                icon = UIUtils.createErrorIcon(VaadinIcon.WARNING);
                break;
        }
        return icon;
    }

    private String getStatusTheme(Payment payment) {
        String theme;
        switch (payment.getStatus()) {
            case PENDING:
                theme = LumoStyles.Badge.CONTRAST;
                break;
            case OPEN:
                theme = LumoStyles.Badge.DEFAULT;
                break;
            case SENT:
                theme = LumoStyles.Badge.SUCCESS;
                break;
            default:
                theme = LumoStyles.Badge.ERROR;
                break;
        }
        return theme;
    }

}
