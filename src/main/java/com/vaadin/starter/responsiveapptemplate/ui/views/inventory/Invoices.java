package com.vaadin.starter.responsiveapptemplate.ui.views.inventory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.responsiveapptemplate.backend.DummyData;
import com.vaadin.starter.responsiveapptemplate.backend.Invoice;
import com.vaadin.starter.responsiveapptemplate.backend.UIConfig;
import com.vaadin.starter.responsiveapptemplate.ui.Root;
import com.vaadin.starter.responsiveapptemplate.ui.components.DetailsDrawer;
import com.vaadin.starter.responsiveapptemplate.ui.components.ListItem;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.responsiveapptemplate.ui.utils.*;
import com.vaadin.starter.responsiveapptemplate.ui.views.ViewFrame;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;

import static com.vaadin.starter.responsiveapptemplate.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "invoices", layout = Root.class)
@PageTitle("Invoices")
public class Invoices extends ViewFrame {

	private AppBar appBar;

	private Grid<Invoice> grid;
	private ListDataProvider<Invoice> dataProvider;

	private DetailsDrawer detailsDrawer;
	private Label detailsDrawerTitle;

	public Invoices() {
		// Header
		if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
			initAppBar();
		}

		// Grid
		initGrid();

		// Details drawer
		initDetailsDrawer();

		// Set the content
		Div gridWrapper = UIUtils.createDiv(Collections.singleton(GRID_VIEW), grid);

		FlexLayout content = new FlexLayout(gridWrapper, detailsDrawer);
		content.setSizeFull();

		setViewContent(content);
	}

	private void initAppBar() {
		appBar = new AppBar("Invoices");
		appBar.addActionItem(VaadinIcon.SEARCH).addClickListener(e -> appBar.searchModeOn());
		appBar.addSearchListener(e -> filter(e));
		appBar.setSearchPlaceholder("Search by customer");
		setViewHeader(appBar);
	}

	private void initGrid() {
		grid = new Grid<>();
		dataProvider = DataProvider.ofCollection(DummyData.getInvoices());
		dataProvider.setSortOrder(Invoice::getDueDate, SortDirection.ASCENDING);
		grid.setDataProvider(dataProvider);

		grid.addColumn(Invoice::getId)
				.setFlexGrow(0)
				.setHeader("ID")
				.setWidth(UIUtils.COLUMN_WIDTH_XS);
		grid.addColumn(new ComponentRenderer<>(UIUtils::createBadge))
				.setFlexGrow(0)
				.setHeader("Status")
				.setWidth(UIUtils.COLUMN_WIDTH_S);
		grid.addColumn(new ComponentRenderer<>(this::createOrder))
				.setHeader("Customer / Order ID")
				.setWidth(UIUtils.COLUMN_WIDTH_XL);
		grid.addColumn(new LocalDateRenderer<>(Invoice::getDueDate, DateTimeFormatter.ofPattern("MMM dd, YYYY")))
				.setComparator(Invoice::getDueDate)
				.setFlexGrow(0)
				.setHeader("Due Date")
				.setWidth(UIUtils.COLUMN_WIDTH_M);
		grid.addColumn(new ComponentRenderer<>(this::createAmount))
				.setFlexGrow(0)
				.setHeader(UIUtils.createRightAlignedDiv("Amount ($)"))
				.setWidth(UIUtils.COLUMN_WIDTH_S);

		grid.addSelectionListener(e -> {
			if (e.getFirstSelectedItem().isPresent()) {
				showDetails(e.getFirstSelectedItem().get());
			}
		});

		grid.setSizeFull();
	}

	private void filter(HasValue.ValueChangeEvent event) {
		// TODO: This is just for demo purposes. Proper filtering should be done on another level.
		dataProvider.setFilter((SerializablePredicate<Invoice>) invoice -> invoice.getOrder().getCustomer().toLowerCase().contains(event.getValue().toString().toLowerCase()));
	}

	private Component createOrder(Invoice invoice) {
		ListItem item = new ListItem(invoice.getOrder().getCustomer(), String.valueOf(invoice.getOrder().getId()));
		item.setHorizontalPadding(false);
		return item;
	}

	private Component createAmount(Invoice invoice) {
		Double value = invoice.getOrder().getValue();
		Label label = UIUtils.createAmountLabel(value);
		return UIUtils.createRightAlignedDiv(label);
	}

	private void initDetailsDrawer() {
		detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

		// Header
		detailsDrawerTitle = UIUtils.createDetailsDrawerHeader("", false, true);

		Tab details = new Tab("Details");
		Tab attachments = new Tab("Attachments");
		Tab activity = new Tab("Activity");

		Tabs tabs = new Tabs(details, attachments, activity);
		tabs.addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS);

		tabs.addSelectedChangeListener(e -> {
			Tab selectedTab = tabs.getSelectedTab();
			if (selectedTab.equals(details)) {
				detailsDrawer.setContent(createDetails(grid.getSelectionModel().getFirstSelectedItem().get()));
			} else if (selectedTab.equals(attachments)) {
				detailsDrawer.setContent(createAttachments());
			} else if (selectedTab.equals(activity)) {
				detailsDrawer.setContent(createActivity());
			}
		});

		detailsDrawer.setHeader(detailsDrawerTitle, tabs);
		detailsDrawer.getHeader().getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);

		// Footer
		Button save = UIUtils.createPrimaryButton("Save");
		save.addClickListener(e -> UIUtils.showNotification("Not implemented yet."));

		Button cancel = UIUtils.createTertiaryButton("Cancel");
		cancel.addClickListener(e -> detailsDrawer.hide());

		FlexLayout footer = UIUtils.createFlexLayout(Arrays.asList(LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Vertical.S, LumoStyles.Spacing.Right.S), save, cancel);
		footer.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.Contrast._5);
		footer.setWidth("100%");

		detailsDrawer.setFooter(footer);
	}

	private void showDetails(Invoice invoice) {
		detailsDrawerTitle.setText(invoice.getOrder().getCustomer());
		detailsDrawer.setContent(createDetails(invoice));
		detailsDrawer.show();
	}

	private Component createDetails(Invoice invoice) {
		ListItem invoiceId = new ListItem(UIUtils.createSecondaryIcon(VaadinIcon.INVOICE), String.valueOf(invoice.getId()), "Invoice ID");
		ListItem invoiceDate = new ListItem(UIUtils.createSecondaryIcon(VaadinIcon.CALENDAR), UIUtils.formatDate(invoice.getInvoiceDate()), "Invoice Date");
		ListItem orderId = new ListItem(UIUtils.createSecondaryIcon(VaadinIcon.PACKAGE), String.valueOf(invoice.getOrder().getId()), "Order ID");
		ListItem amount = new ListItem(UIUtils.createSecondaryIcon(VaadinIcon.DOLLAR), UIUtils.formatAmount(invoice.getOrder().getValue()), "Amount");

		for (ListItem item : new ListItem[]{invoiceId, invoiceDate, orderId, amount}) {
			item.setHorizontalPadding(false);
			item.setReverse(true);
		}

		RadioButtonGroup<Invoice.Status> status = new RadioButtonGroup<>();
		status.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
		status.setItems(Invoice.Status.values());
		status.setRenderer(new ComponentRenderer<>(item -> UIUtils.createBadge(item)));
		status.setValue(invoice.getStatus());

		DatePicker dueDate = new DatePicker();
		dueDate.setValue(invoice.getDueDate());
		dueDate.setWidth("100%");

		// Add it all together.
		FormLayout form = UIUtils.createFormLayout(Arrays.asList(LumoStyles.Padding.Bottom.L, LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Top.S));
		form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP));

		form.add(invoiceId, invoiceDate, orderId, amount);
		form.addFormItem(status, "Status");
		form.addFormItem(dueDate, "Due Date");

		return form;
	}

	private Component createAttachments() {
		Label label = UIUtils.createLabel(FontSize.S, TextColor.SECONDARY, "Not implemented yet.");
		label.addClassName(LumoStyles.Padding.Uniform.L);
		return label;
	}

	private Component createActivity() {
		Label label = UIUtils.createLabel(FontSize.S, TextColor.SECONDARY, "Not implemented yet.");
		label.addClassName(LumoStyles.Padding.Uniform.L);
		return label;
	}

}
