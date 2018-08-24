package com.vaadin.starter.responsivelayoutgrid.ui.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
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
import com.vaadin.starter.responsivelayoutgrid.ui.LumoStyles;
import com.vaadin.starter.responsivelayoutgrid.ui.MainLayout;
import com.vaadin.starter.responsivelayoutgrid.ui.components.ListItem;
import com.vaadin.starter.responsivelayoutgrid.ui.utils.BoxShadowBorders;
import com.vaadin.starter.responsivelayoutgrid.ui.utils.CSSProperties;
import com.vaadin.starter.responsivelayoutgrid.ui.utils.UIUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static com.vaadin.starter.responsivelayoutgrid.ui.utils.ViewStyles.DETAILS_VIEW;

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
        setClassName(DETAILS_VIEW);
        getStyle().set("flex", "1");

        viewport = new Div();
        viewport.setClassName(DETAILS_VIEW + "__viewport");
        add(viewport);

        formatter = DateTimeFormatter.ofPattern("MMM dd, YYYY");
		random = new Random();

        /* Header section */
        image = new Image("", "");
        image.setHeight("240px");
        image.setWidth("240px");

        amountRaised = new ListItem(VaadinIcon.MONEY, "", "Amount Raised");
        amountRaised.addClassName(BoxShadowBorders.BOTTOM);

        runningDate = new ListItem(VaadinIcon.CALENDAR, "", "Running Date");
		runningDate.addClassName(BoxShadowBorders.BOTTOM);

		status = new ListItem(VaadinIcon.LOCK, "", "Status");

        FlexLayout column = new FlexLayout(amountRaised, runningDate, status);
        column.getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);
		column.getStyle().set(CSSProperties.Flex.PROPERTY, "1");

        FlexLayout row = UIUtils.createWrappingFlexLayout(image, column);
        row.setAlignItems(FlexComponent.Alignment.CENTER);

        viewport.add(row);

        /* Dashboard sections */
		viewport.add(
			new H4("Registrations"),
			UIUtils.createWrappingFlexLayout(
				createLargeListItem(VaadinIcon.TIMER, Integer.toString(random.nextInt(1000)), "Pending"),
				createLargeListItem(VaadinIcon.CHECK, Integer.toString(random.nextInt(1000)), "Approved"),
				createLargeListItem(VaadinIcon.BAN, Integer.toString(random.nextInt(1000)), "Denied")
			)
		);

		viewport.add(
			new H4("Origin of Funds"),
			UIUtils.createWrappingFlexLayout(
				createLargeListItem(VaadinIcon.TIMER, Integer.toString(random.nextInt(1000)), "Open"),
				createLargeListItem(VaadinIcon.CHECK, Integer.toString(random.nextInt(1000)), "Closed")
			)
		);

		viewport.add(
			new H4("Balance Updates"),
			UIUtils.createWrappingFlexLayout(
				createLargeListItem(VaadinIcon.TIMER, Integer.toString(random.nextInt(1000)), "Pending"),
				createLargeListItem(VaadinIcon.CHECK, Integer.toString(random.nextInt(1000)), "Approved")
			)
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

    public ListItem createLargeListItem(VaadinIcon icon, String primary, String secondary) {
		ListItem item = new ListItem(icon, primary, secondary);
		item.getPrimaryLabel().addClassName(LumoStyles.FontSize.H2);
		item.getStyle().set(CSSProperties.MinWidth.PROPERTY, "200px");
		item.setWidth("33%");
		return item;
	}
}
