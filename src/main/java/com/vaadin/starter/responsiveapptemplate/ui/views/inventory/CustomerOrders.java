package com.vaadin.starter.responsiveapptemplate.ui.views.inventory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
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
import com.vaadin.starter.responsiveapptemplate.ui.layout.FlexBoxLayout;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Right;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Top;
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
	private Dialog dialog;

	public CustomerOrders() {
		if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
			appBar = createAppBar();
			setViewHeader(appBar);
		}
		setViewContent(createContent());
	}

	private AppBar createAppBar() {
		AppBar appBar = new AppBar("Customer Orders");
		appBar.addActionItem(VaadinIcon.SEARCH).addClickListener(e -> appBar.searchModeOn());
		appBar.addSearchListener(e -> filter(e));
		appBar.setSearchPlaceholder("Search by customer");
		return appBar;
	}

	private Component[] createContent() {
		grid = createGrid();
		Div gridWrapper = new Div(grid);
		gridWrapper.addClassName(GRID_VIEW);

		Button fab = UIUtils.createFloatingActionButton(VaadinIcon.PLUS);
		fab.addClickListener(e -> showDialog());

		return new Component[]{gridWrapper, fab};
	}

	private Grid createGrid() {
		Grid<Order> grid = new Grid<>();
		dataProvider = DataProvider.ofCollection(DummyData.getOrders());
		dataProvider.setSortOrder(Order::getDate, SortDirection.ASCENDING);
		grid.setDataProvider(dataProvider);
		grid.setItemDetailsRenderer(new ComponentRenderer<>(this::createDetails));
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

		return grid;
	}

	private Component createItemCount(Order order) {
		int itemCount = order.getItemCount();
		Label label = UIUtils.createUnitsLabel(itemCount);
		return UIUtils.createRightAlignedDiv(label);
	}

	private Component createValue(Order order) {
		double value = order.getValue();
		Label label = UIUtils.createAmountLabel(value);
		return UIUtils.createRightAlignedDiv(label);
	}

	private Component createDetails(Order order) {
		H5 shippingTitle = new H5("Shipping Address");
		ListItem shippingItem = createShippingItem();

		H5 trackingTitle = new H5("Tracking ID");
		ListItem trackingItem = createTrackingItem();

		H5 handlerTitle = new H5("Handler");
		ListItem handlerItem = createHandlerItem();

		H5 itemsTitle = new H5("Items");
		Div items = createItems(order);

		return new Div(
				shippingTitle,
				shippingItem,
				new Divider(FlexComponent.Alignment.END, LumoStyles.Size.S),
				trackingTitle,
				trackingItem,
				new Divider(FlexComponent.Alignment.END, LumoStyles.Size.S),
				handlerTitle,
				handlerItem,
				new Divider(FlexComponent.Alignment.END, LumoStyles.Size.S),
				itemsTitle,
				items
		);
	}

	private ListItem createShippingItem() {
		ListItem item = new ListItem(
				UIUtils.createSecondaryIcon(VaadinIcon.MAP_MARKER),
				UIUtils.formatAddress(DummyData.getAddress()),
				new Button("Google Maps")
		);
		item.setHorizontalPadding(false);
		return item;
	}

	private ListItem createTrackingItem() {
		ListItem item = new ListItem(
				UIUtils.createSecondaryIcon(VaadinIcon.DOT_CIRCLE),
				Integer.toString(DummyData.getRandomInt(100000, 999999)),
				new Button("Track")
		);
		item.setHorizontalPadding(false);
		return item;
	}

	private ListItem createHandlerItem() {
		Person handler = DummyData.getPerson();
		Component initials = UIUtils.createInitials(handler.getInitials());

		FlexBoxLayout buttons = new FlexBoxLayout(new Button("Call"), new Button("Email"));
		buttons.setSpacing(Right.S);

		ListItem item = new ListItem(initials, handler.getName(), handler.getEmail(), buttons);
		item.setHorizontalPadding(false);
		return item;
	}

	private Div createItems(Order order) {
		Div items = UIUtils.createDiv(Collections.singleton(LumoStyles.Margin.Bottom.L));

		Iterator<Item> iterator = order.getItems().iterator();
		while (iterator.hasNext()) {
			Item item = iterator.next();
			ListItem listItem = new ListItem(
					item.getName(),
					item.getDesc(),
					UIUtils.createAmountLabel(item.getPrice())
			);
			listItem.setAlignItems(FlexComponent.Alignment.BASELINE);
			listItem.setHorizontalPadding(false);
			items.add(listItem);
		}
		return items;
	}

	private void showDialog() {
		Label title = UIUtils.createH4Label("New Customer Order");
		FormLayout form = createDialogForm();
		Component footer = createDialogFooter();

		dialog = new Dialog(title, form, footer);
		dialog.open();
	}

	private FormLayout createDialogForm() {
		// Components
		RadioButtonGroup status = createStatusRadioButtonGroup();
		ComboBox customer = createCustomerComboBox();
		ComboBox handler = createHandlerComboBox();
		ComboBox<Item> items = createItemsComboBox();
		Div badges = new Div();
		Label amount = UIUtils.createAmountLabel(0.00);

		// Update total when selection changes
		ArrayList<Item> list = new ArrayList<>();
		items.addValueChangeListener(e -> {
			Item item = e.getValue();
			if (item != null) {
				list.add(item);
				Span badge = UIUtils.createBadge(item.getName());
				badge.addClassNames(LumoStyles.Margin.Right.S, LumoStyles.Margin.Vertical.XS);
				badges.add(badge);
				amount.setText(UIUtils.formatAmount(list.stream().mapToDouble(Item::getPrice).sum()));
			}
		});

		// Form layout
		FormLayout form = new FormLayout();
		form.setResponsiveSteps(
				new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP)
		);
		form.addFormItem(status, "Status");
		form.addFormItem(customer, "Customer");
		form.addFormItem(handler, "Handler");
		form.addFormItem(items, "Items");
		form.add(badges);
		form.addFormItem(amount, "Amount ($)");
		return form;
	}

	private RadioButtonGroup<Order.Status> createStatusRadioButtonGroup() {
		RadioButtonGroup<Order.Status> rbg = new RadioButtonGroup<>();
		rbg.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
		rbg.setItems(Order.Status.values());
		rbg.setRenderer(new ComponentRenderer<>(item -> UIUtils.createBadge(item)));
		return rbg;
	}

	private ComboBox createCustomerComboBox() {
		ComboBox comboBox = new ComboBox();
		comboBox.setItems(DummyData.getCompanies());
		comboBox.setPlaceholder("Select customer");
		comboBox.setWidth("100%");
		return comboBox;
	}

	private ComboBox<Person> createHandlerComboBox() {
		ComboBox<Person> comboBox = new ComboBox();
		comboBox.setItems(DummyData.getPersons());
		comboBox.setItemLabelGenerator(person -> person.getName());
		comboBox.setPlaceholder("Select handler");
		comboBox.setWidth("100%");
		return comboBox;
	}

	private ComboBox<Item> createItemsComboBox() {
		ComboBox<Item> comboBox = new ComboBox();
		comboBox.setItems(DummyData.getItems());
		comboBox.setItemLabelGenerator(item -> item.getName());
		comboBox.setPlaceholder("Select item");
		comboBox.setWidth("100%");
		return comboBox;
	}

	private Component createDialogFooter() {
		Button save = UIUtils.createPrimaryButton("Save");
		save.addClickListener(e -> {
			UIUtils.showNotification("Not implemented yet.");
			closeDialog();
		});

		Button cancel = new Button("Cancel");
		cancel.addClickListener(e -> closeDialog());

		FlexBoxLayout footer = new FlexBoxLayout(save, cancel);
		footer.setMargin(Top.L);
		footer.setSpacing(Right.S);
		return footer;
	}

	private void closeDialog() {
		dialog.close();
	}

	private void filter(HasValue.ValueChangeEvent event) {
		// TODO: This is just for demo purposes. Proper filtering should be done on another level.
		dataProvider.setFilter((SerializablePredicate<Order>) order -> order.getCustomer().toLowerCase().contains(event.getValue().toString().toLowerCase()));
	}
}
