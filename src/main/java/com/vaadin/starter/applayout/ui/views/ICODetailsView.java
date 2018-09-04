package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.DummyData;
import com.vaadin.starter.applayout.backend.InitialCoinOffering;
import com.vaadin.starter.applayout.ui.MainLayout;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.utils.BoxShadowBorders;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Random;

@Route(value = "ico-details", layout = MainLayout.class)
@PageTitle("ICO Details")
public class ICODetailsView extends Div implements HasUrlParameter<Long> {

    private final Image image;
    private final ListItem amountRaised;
    private final ListItem runningDate;
    private final ListItem status;
    private final DateTimeFormatter formatter;
    private final Div viewport;
    private final Random random;

    public ICODetailsView() {
        getStyle().set(CSSProperties.Overflow.PROPERTY, CSSProperties.Overflow.AUTO);
        getStyle().set(CSSProperties.Flex.PROPERTY, "1");

        viewport = new Div();
        viewport.addClassNames(LumoStyles.Margin.Horizontal.AUTO, LumoStyles.Margin.Responsive.Vertical.ML);
        viewport.getStyle().set(CSSProperties.MaxWidth.PROPERTY, CSSProperties.MaxWidth._800);
        add(viewport);

        formatter = DateTimeFormatter.ofPattern("MMM dd, YYYY");
        random = new Random();

        /* Header section */
        image = new Image("", "");
        image.getStyle().set(CSSProperties.BorderRadius.PROPERTY, "100%");
        image.setHeight("240px");
        image.setWidth("240px");

        amountRaised = new ListItem(VaadinIcon.MONEY, "", "Amount Raised");
        amountRaised.setDividerVisible(true);

        runningDate = new ListItem(VaadinIcon.CALENDAR, "", "Running Date");
        runningDate.setDividerVisible(true);

        status = new ListItem(VaadinIcon.LOCK, "", "Status");

        FlexLayout column = new FlexLayout(amountRaised, runningDate, status);
        column.getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);
        column.getStyle().set(CSSProperties.Flex.PROPERTY, "1");

        FlexLayout row = UIUtils.createWrappingFlexLayout(image, column);
        row.setAlignItems(FlexComponent.Alignment.CENTER);
        row.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        row.addClassNames(LumoStyles.Padding.Bottom.L, BoxShadowBorders.BOTTOM);

        viewport.add(row);

        /* Dashboard sections */
        FlexLayout registrations = UIUtils.createWrappingFlexLayout(
                createLargeListItem(VaadinIcon.TIMER, Integer.toString(random.nextInt(5000)), "Pending"),
                createLargeListItem(VaadinIcon.CHECK, Integer.toString(random.nextInt(5000)), "Approved"),
                createLargeListItem(VaadinIcon.BAN, Integer.toString(random.nextInt(5000)), "Denied")
        );
        registrations.addClassNames(LumoStyles.Padding.Bottom.L, BoxShadowBorders.BOTTOM);

        FlexLayout originOfFunds = UIUtils.createWrappingFlexLayout(
                createLargeListItem(VaadinIcon.TIMER, Integer.toString(random.nextInt(5000)), "Open"),
                createLargeListItem(VaadinIcon.CHECK, Integer.toString(random.nextInt(5000)), "Closed")
        );
        originOfFunds.addClassNames(LumoStyles.Padding.Bottom.L, BoxShadowBorders.BOTTOM);

        FlexLayout balanceUpdates = UIUtils.createWrappingFlexLayout(
                createLargeListItem(VaadinIcon.TIMER, Integer.toString(random.nextInt(5000)), "Pending"),
                createLargeListItem(VaadinIcon.CHECK, Integer.toString(random.nextInt(5000)), "Approved")
        );
        balanceUpdates.addClassNames(LumoStyles.Padding.Bottom.L, BoxShadowBorders.BOTTOM);

        FlexLayout tokenReceivingAddresses = UIUtils.createWrappingFlexLayout(
                createLargeListItem(VaadinIcon.TIMER, Integer.toString(random.nextInt(5000)), "Unconfirmed"),
                createLargeListItem(VaadinIcon.CHECK, Integer.toString(random.nextInt(5000)), "Confirmed"),
                createLargeListItem(VaadinIcon.FLAG_CHECKERED, Integer.toString(random.nextInt(5000)), "Processed")
        );

        viewport.add(
                UIUtils.createH3(Collections.singleton(LumoStyles.Margin.Responsive.Horizontal.ML), "Registrations"),
                registrations,
                UIUtils.createH3(Collections.singleton(LumoStyles.Margin.Responsive.Horizontal.ML), "Origin of Funds"),
                originOfFunds,
                UIUtils.createH3(Collections.singleton(LumoStyles.Margin.Responsive.Horizontal.ML), "Balance Updates"),
                UIUtils.createParagraph(Collections.singleton(LumoStyles.Margin.Responsive.Horizontal.ML), "Import transactions by uploading a CSV file containing transactional data."),
                balanceUpdates,
                UIUtils.createH3(Collections.singleton(LumoStyles.Margin.Responsive.Horizontal.ML), "Token Receiving Address"),
                UIUtils.createParagraph(Collections.singleton(LumoStyles.Margin.Responsive.Horizontal.ML), "Before tokens are distributed, you may send out an email asking approved registrants to verify their token receiving address. Only applies to non-ETH registrants."),
                tokenReceivingAddresses
        );
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long id) {
        InitialCoinOffering ico = DummyData.get(id);

        image.setSrc(ico.getSource());
        amountRaised.setPrimaryText(Double.toString(ico.getAmountRaised()));
        runningDate.setPrimaryText(ico.getStartDate().format(formatter) + " - " + ico.getEndDate().format(formatter));

        if (ico.getStartDate().isAfter(LocalDate.now())) {
            status.setPrimaryText("Coming Soon");
        } else if (ico.getEndDate().isBefore(LocalDate.now())) {
            status.setPrimaryText("Closed");
        } else {
            status.setPrimaryText("Open");
        }

        // TODO: how do we update the page title?
    }

    private ListItem createLargeListItem(VaadinIcon icon, String primary, String secondary) {
        ListItem item = new ListItem(icon, primary, secondary);

        if (icon.equals(VaadinIcon.TIMER)) {
            item.addClassName(LumoStyles.Text.SECONDARY);
        } else if (icon.equals(VaadinIcon.CHECK) || icon.equals(VaadinIcon.FLAG_CHECKERED)) {
            item.addClassName(LumoStyles.Text.SUCCESS);
        } else if (icon.equals(VaadinIcon.BAN)) {
            item.addClassName(LumoStyles.Text.ERROR);
        }

        item.getPrimaryLabel().addClassName(LumoStyles.FontSize.H3);
        item.getStyle().set(CSSProperties.MinWidth.PROPERTY, "200px");
        item.setWidth("33%");

        return item;
    }
}
