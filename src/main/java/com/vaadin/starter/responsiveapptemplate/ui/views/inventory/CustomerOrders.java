package com.vaadin.starter.responsiveapptemplate.ui.views.inventory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.responsiveapptemplate.backend.*;
import com.vaadin.starter.responsiveapptemplate.ui.Root;
import com.vaadin.starter.responsiveapptemplate.ui.components.Divider;
import com.vaadin.starter.responsiveapptemplate.ui.components.ListItem;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.responsiveapptemplate.ui.utils.CSSProperties;
import com.vaadin.starter.responsiveapptemplate.ui.utils.LumoStyles;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;
import com.vaadin.starter.responsiveapptemplate.ui.views.ViewFrame;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import static com.vaadin.starter.responsiveapptemplate.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "customer-orders", layout = Root.class)
@PageTitle("Customer Orders")
public class CustomerOrders extends ViewFrame {

	private AppBar appBar;

	private Grid<Order> grid;
	private ListDataProvider<Order> dataProvider;

	public CustomerOrders() {
		// Header
		if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
			initAppBar();
		}

		// Grid
		initGrid();

		// New order
		Button newOrder = UIUtils.createFloatingActionButton(VaadinIcon.PLUS);
		newOrder.addClickListener(e -> showDialog());

		// Set the content
		setViewContent(grid, newOrder);
		getViewContent().addClassName(GRID_VIEW);
	}

	private void initAppBar() {
		appBar = new AppBar("Customer Orders");
		appBar.addActionItem(VaadinIcon.SEARCH).addClickListener(e -> appBar.searchModeOn());
		appBar.addSearchListener(e -> filter(e));
		appBar.setSearchPlaceholder("Search by customer");
		setViewHeader(appBar);
	}

	private void initGrid() {
		dataProvider = DataProvider.ofCollection(DummyData.getOrders());
		dataProvider.setSortOrder(Order::getDate, SortDirection.ASCENDING);

		grid = new Grid<>();
		grid.setDataProvider(dataProvider);
		grid.setSizeFull();

		grid.addColumn(new ComponentRenderer<>(UIUtils::createBadge))
				.setFlexGrow(0)
				.setHeader("Status")
				.setWidth(UIUtils.COLUMN_WIDTH_S);
		grid.addColumn(Order::getCustomer)
				.setHeader("Customer")
				.setWidth(UIUtils.COLUMN_WIDTH_XL);
		grid.addColumn(new LocalDateRenderer<>(Order::getDate, DateTimeFormatter.ofPattern("MMM dd, YYYY")))
				.setComparator(Order::getDate)
				.setFlexGrow(0)
				.setHeader("Order Received")
				.setWidth(UIUtils.COLUMN_WIDTH_M);
		grid.addColumn(new ComponentRenderer<>(this::createItemCount))
				.setFlexGrow(0)
				.setHeader(UIUtils.createRightAlignedDiv("Item Count"))
				.setWidth(UIUtils.COLUMN_WIDTH_S);
		grid.addColumn(new ComponentRenderer<>(this::createValue))
				.setFlexGrow(0)
				.setHeader(UIUtils.createRightAlignedDiv("Value ($)"))
				.setWidth(UIUtils.COLUMN_WIDTH_S);

		grid.setItemDetailsRenderer(new ComponentRenderer<>(this::createDetails));
	}

	private void filter(HasValue.ValueChangeEvent event) {
		// TODO: This is just for demo purposes. Proper filtering should be done on another level.
		dataProvider.setFilter((SerializablePredicate<Order>) order -> order.getCustomer().toLowerCase().contains(event.getValue().toString().toLowerCase()));
	}

	private Component createItemCount(Order order) {
		int count = order.getItemCount();
		return UIUtils.createRightAlignedDiv(UIUtils.formatUnits(count));
	}

	private Component createValue(Order order) {
		return UIUtils.createRightAlignedDiv(UIUtils.createAmountLabel(order.getValue()));
	}

	private Component createDetails(Order order) {
		// Shipping
		Label shippingTitle = UIUtils.createH5Label("Shipping Address");
		ListItem shippingItem = new ListItem(
				UIUtils.createSecondaryIcon(VaadinIcon.MAP_MARKER),
				UIUtils.formatAddress(DummyData.getAddress()),
				new Button("Google Maps")
		);

		// Tracking
		Label trackingTitle = UIUtils.createH5Label("Tracking ID");
		ListItem trackingItem = new ListItem(
				UIUtils.createSecondaryIcon(VaadinIcon.DOT_CIRCLE),
				Integer.toString(DummyData.getRandomInt(100000, 999999)),
				new Button("Track")
		);

		// Handler
		Person handler = DummyData.getPerson();
		Label handlerTitle = UIUtils.createH5Label("Handler");
		ListItem handlerItem = new ListItem(
				UIUtils.createInitials(handler.getInitials()),
				handler.getName(),
				handler.getEmail(),
				UIUtils.createFlexLayout(
						Collections.singleton(LumoStyles.Spacing.Right.S),
						new Button("Call"),
						new Button("Email")
				)
		);

		// Items
		Label itemsTitle = UIUtils.createH5Label("Items");
		Div items = UIUtils.createDiv(Collections.singleton(LumoStyles.Margin.Bottom.L));

		Iterator<Item> iterator = order.getItems().iterator();
		while (iterator.hasNext()) {
			Item item = iterator.next();
			ListItem listItem = new ListItem(item.getName(), item.getDesc(), UIUtils.createAmountLabel(item.getPrice()));
			listItem.setAlignItems(FlexComponent.Alignment.BASELINE);
			listItem.setHorizontalPadding(false);
			items.add(listItem);
		}

		// Titles: add margins
		for (Label component : new Label[]{shippingTitle, trackingTitle, handlerTitle, itemsTitle}) {
			component.addClassNames(LumoStyles.Margin.Bottom.S, LumoStyles.Margin.Top.L);
		}

		// ListItems: remove padding, add borders
		for (ListItem component : new ListItem[]{shippingItem, trackingItem, handlerItem}) {
			component.setHorizontalPadding(false);
		}

		// Add it all together...
		return new Div(
				shippingTitle,
				shippingItem,
				new Divider(FlexComponent.Alignment.END, "8px"),
				trackingTitle,
				trackingItem,
				new Divider(FlexComponent.Alignment.END, "8px"),
				handlerTitle,
				handlerItem,
				new Divider(FlexComponent.Alignment.END, "8px"),
				itemsTitle,
				items
		);
	}

	private void showDialog() {
		Dialog dlg = new Dialog();

		// Title
		Label title = UIUtils.createH4Label("New Customer Order");

		// Status
		RadioButtonGroup<Order.Status> status = new RadioButtonGroup<>();
		status.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
		status.setItems(Order.Status.values());
		status.setRenderer(new ComponentRenderer<>(item -> UIUtils.createBadge(item)));

		// Customer selection
		ComboBox customer = new ComboBox();
		customer.setItems(DummyData.getCompanies());
		customer.setPlaceholder("Select customer");
		customer.setWidth("100%");

		// Handler
		ComboBox<Person> handler = new ComboBox();
		handler.setItems(DummyData.getPersons());
		handler.setPlaceholder("Select handler");
		handler.setWidth("100%");
		handler.setItemLabelGenerator(person -> person.getName());

		// Item badges
		Div badges = new Div();

		// Total price
		Label amount = UIUtils.createAmountLabel(0.00);
		ArrayList<Item> items = new ArrayList<>();

		// Item selection
		ComboBox<Item> itemComboBox = new ComboBox();
		itemComboBox.setItems(DummyData.getItems());
		itemComboBox.setItemLabelGenerator(item -> item.getName());
		itemComboBox.setPlaceholder("Select item");
		itemComboBox.setWidth("100%");
		itemComboBox.addValueChangeListener(e -> {
			Item item = e.getValue();
			if (item != null) {
				items.add(item);
				Span badge = UIUtils.createBadge(item.getName());
				badge.addClassNames(LumoStyles.Margin.Right.S, LumoStyles.Margin.Vertical.XS);
				badges.add(badge);
				amount.setText(UIUtils.formatAmount(items.stream().mapToDouble(Item::getPrice).sum()));
			}
		});

		// Layout
		FormLayout form = new FormLayout();
		form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP));
		form.addFormItem(status, "Status");
		form.addFormItem(customer, "Customer");
		form.addFormItem(handler, "Handler");
		form.addFormItem(itemComboBox, "Items");
		form.add(badges);
		form.addFormItem(amount, "Amount ($)");

		// Footer
		Button save = UIUtils.createPrimaryButton("Save");
		save.addClickListener(e -> {
			UIUtils.showNotification("Not implemented yet.");
			dlg.close();
		});

		Button cancel = new Button("Cancel");
		cancel.addClickListener(e -> dlg.close());

		Div footer = new Div(save, cancel);
		footer.addClassNames(LumoStyles.Margin.Top.L, LumoStyles.Spacing.Right.S);

		// Add it all together and open the dialog
		Div content = new Div(title, form, footer);
		content.getStyle().set(CSSProperties.MaxWidth.PROPERTY, "320px");

		dlg.add(content);
		dlg.open();
	}

}
