package com.vaadin.starter.responsivelayoutgrid.ui.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.responsivelayoutgrid.backend.DummyData;
import com.vaadin.starter.responsivelayoutgrid.backend.InitialCoinOffering;
import com.vaadin.starter.responsivelayoutgrid.ui.MainLayout;
import com.vaadin.starter.responsivelayoutgrid.ui.components.ListItem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Route(value = "ico-details", layout = MainLayout.class)
@PageTitle("ICO Details")
public class ICODetailsView extends Div implements HasUrlParameter<Long> {

    private final String DETAILS_VIEW = "details-view";
    private final Image image;
    private final ListItem amountRaised;
    private final ListItem runningDate;
    private final ListItem status;
    private final DateTimeFormatter formatter;
    private final Div viewport;

    public ICODetailsView() {
        setClassName(DETAILS_VIEW);
        getStyle().set("flex", "1");

        viewport = new Div();
        viewport.setClassName(DETAILS_VIEW + "__viewport");
        add(viewport);

        formatter = DateTimeFormatter.ofPattern("MMM dd, YYYY");

        image = new Image("", "");
        image.setHeight("240px");
        image.setWidth("240px");

        amountRaised = new ListItem(VaadinIcon.MONEY, "", "Amount Raised");

        runningDate = new ListItem(VaadinIcon.CALENDAR, "", "Running Date");

        status = new ListItem(VaadinIcon.LOCK, "", "Status");

        FlexLayout column = new FlexLayout(amountRaised, runningDate, status);
        column.getStyle().set("flex-direction", "column");

        FlexLayout row = new FlexLayout(image, column);
        row.setAlignItems(FlexComponent.Alignment.CENTER);
        row.getStyle().set("flex-wrap", "wrap");

        viewport.add(row);
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
}
