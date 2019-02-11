package com.vaadin.starter.responsiveapptemplate.ui.views.inventory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.responsiveapptemplate.backend.DummyData;
import com.vaadin.starter.responsiveapptemplate.backend.Item;
import com.vaadin.starter.responsiveapptemplate.backend.UIConfig;
import com.vaadin.starter.responsiveapptemplate.ui.Root;
import com.vaadin.starter.responsiveapptemplate.ui.components.DetailsDrawer;
import com.vaadin.starter.responsiveapptemplate.ui.components.ListItem;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.responsiveapptemplate.ui.layout.FlexBoxLayout;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Horizontal;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Right;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Vertical;
import com.vaadin.starter.responsiveapptemplate.ui.utils.LumoStyles;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;
import com.vaadin.starter.responsiveapptemplate.ui.views.ViewFrame;

import static com.vaadin.starter.responsiveapptemplate.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "catalogue", layout = Root.class)
@PageTitle("Catalogue")
public class Catalogue extends ViewFrame {

	private AppBar appBar;

	private Grid<Item> grid;
	private ListDataProvider<Item> dataProvider;

	private DetailsDrawer detailsDrawer;

	public Catalogue() {
		if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
			appBar = createAppBar();
			setViewHeader(appBar);
		}
		setViewContent(createContent());
	}

	private AppBar createAppBar() {
		AppBar appBar = new AppBar("Catalogue");
		appBar.addActionItem(VaadinIcon.SEARCH).addClickListener(e -> appBar.searchModeOn());
		appBar.addSearchListener(e -> filter(e));
		appBar.setSearchPlaceholder("Search by item name, description and vendor");
		return appBar;
	}

	private Component createContent() {
		grid = createGrid();
		Div gridWrapper = new Div(grid);
		gridWrapper.addClassName(GRID_VIEW);

		detailsDrawer = createDetailsDrawer();

		FlexLayout content = new FlexLayout(gridWrapper, detailsDrawer);
		content.setHeight("100%");
		return content;
	}

	private Grid createGrid() {
		Grid<Item> grid = new Grid<>();
		grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::showDetails));
		dataProvider = DataProvider.ofCollection(DummyData.getItems());
		grid.setDataProvider(dataProvider);
		grid.setHeight("100%");

		grid.addColumn(new ComponentRenderer<>(UIUtils::createBadge))
				.setHeader("Category")
				.setWidth(UIUtils.COLUMN_WIDTH_M)
				.setFlexGrow(0);
		grid.addColumn(new ComponentRenderer<>(this::createInfo))
				.setHeader("Item")
				.setWidth(UIUtils.COLUMN_WIDTH_XL)
				.setFlexGrow(1);
		grid.addColumn(Item::getVendor)
				.setHeader("Vendor")
				.setWidth(UIUtils.COLUMN_WIDTH_XL)
				.setFlexGrow(1);
		grid.addColumn(new ComponentRenderer<>(this::createPrice))
				.setHeader(UIUtils.createRightAlignedDiv("Unit Price ($)"))
				.setWidth(UIUtils.COLUMN_WIDTH_S)
				.setFlexGrow(0);
		grid.addColumn(new ComponentRenderer<>(this::createStock))
				.setHeader(UIUtils.createRightAlignedDiv("In Stock"))
				.setWidth(UIUtils.COLUMN_WIDTH_S)
				.setFlexGrow(0);
		grid.addColumn(new ComponentRenderer<>(this::createSold))
				.setHeader(UIUtils.createRightAlignedDiv("Units Sold"))
				.setWidth(UIUtils.COLUMN_WIDTH_S)
				.setFlexGrow(0);

		return grid;
	}

	private void filter(HasValue.ValueChangeEvent event) {
		dataProvider.setFilter((SerializablePredicate<Item>) item -> {
			// TODO: This is just for demo purposes. Proper filtering should be done on another level.
			String data = (item.getName() + item.getDesc() + item.getVendor()).toLowerCase();
			if (data.toLowerCase().contains(event.getValue().toString().toLowerCase())) {
				return true;
			}
			return false;
		});
	}

	private Component createInfo(Item item) {
		ListItem listItem = new ListItem(item.getName(), item.getDesc());
		listItem.setHorizontalPadding(false);
		return listItem;
	}

	private Component createPrice(Item item) {
		Double price = item.getPrice();
		Label label = UIUtils.createAmountLabel(price);
		return UIUtils.createRightAlignedDiv(label);
	}

	private Component createStock(Item item) {
		return UIUtils.createRightAlignedDiv(UIUtils.createUnitsLabel(item.getStock()));
	}

	private Component createSold(Item item) {
		return UIUtils.createRightAlignedDiv(UIUtils.createUnitsLabel(item.getSold()));
	}

	private DetailsDrawer createDetailsDrawer() {
		DetailsDrawer detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

		// Footer
		Button save = UIUtils.createPrimaryButton("Save");
		save.addClickListener(e -> Notification.show("Not implemented yet.", 3000, Notification.Position.BOTTOM_CENTER));

		Button cancel = UIUtils.createTertiaryButton("Cancel");
		cancel.addClickListener(e -> detailsDrawer.hide());

		Button delete = UIUtils.createErrorPrimaryButton("Delete");
		delete.addClassName(LumoStyles.Margin.Left.AUTO);
		delete.addClickListener(e -> Notification.show("Not implemented yet.", 3000, Notification.Position.BOTTOM_CENTER));

		FlexBoxLayout footer = new FlexBoxLayout(save, cancel);
		footer.setBackgroundColor(LumoStyles.Color.Contrast._5);
		footer.setPadding(Horizontal.L, Vertical.S);
		footer.setSpacing(Right.S);
		footer.setWidth("100%");
		detailsDrawer.setFooter(footer);

		return detailsDrawer;
	}

	private void showDetails(Item item) {
		detailsDrawer.setContent(createDetails(item));
		detailsDrawer.show();
	}

	private Component createDetails(Item item) {
		FormLayout form = new FormLayout();
		form.addClassNames(LumoStyles.Padding.Bottom.L, LumoStyles.Padding.Horizontal.L);

		form.setResponsiveSteps(
				new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP)
		);

		Label title = UIUtils.createDetailsDrawerHeader("Edit Item", false, false);

		RadioButtonGroup<Item.Category> category = new RadioButtonGroup<>();
		category.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
		category.setItems(Item.Category.values());
		category.setValue(item.getCategory());
		category.setRenderer(new ComponentRenderer<>(i -> UIUtils.createBadge(i)));

		TextField name = new TextField();
		name.setValue(item.getName());
		name.setWidth("100%");

		TextArea desc = new TextArea();
		desc.setValue(item.getDesc());
		desc.setWidth("100%");

		TextField vendor = new TextField();
		vendor.setValue(item.getVendor());
		vendor.setWidth("100%");

		TextField price = new TextField();
		price.setValue(UIUtils.formatAmount(item.getPrice()));
		price.setWidth("100%");

		TextField stock = new TextField();
		stock.setValue(UIUtils.formatUnits(item.getStock()));
		stock.setWidth("100%");

		TextField sold = new TextField();
		sold.setValue(UIUtils.formatUnits(item.getSold()));
		sold.setWidth("100%");

		// Add it all together.
		form.add(title);
		form.addFormItem(category, "Category");
		form.addFormItem(name, "Name");
		form.addFormItem(desc, "Description");
		form.addFormItem(vendor, "Vendor");
		form.addFormItem(price, "Unit Price ($)");
		form.addFormItem(stock, "In Stock");
		form.addFormItem(sold, "Units Sold");

		return form;
	}
}
