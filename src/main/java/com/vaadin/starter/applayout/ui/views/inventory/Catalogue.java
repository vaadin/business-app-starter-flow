package com.vaadin.starter.applayout.ui.views.inventory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.DummyData;
import com.vaadin.starter.applayout.backend.Item;
import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.DetailsDrawer;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;
import com.vaadin.starter.applayout.ui.views.ViewFrame;

import java.util.Arrays;
import java.util.Collections;

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "catalogue", layout = Root.class)
@PageTitle("Catalogue")
public class Catalogue extends ViewFrame {

    private DetailsDrawer detailsDrawer;

    public Catalogue() {
        // Header
        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            AppBar appBar = new AppBar("Catalogue");
            setHeader(appBar);
        }

        // Grid
        Grid<Item> grid = new Grid<>();
        grid.setDataProvider(DataProvider.ofCollection(DummyData.getItems()));

        grid.addColumn(new ComponentRenderer<>(UIUtils::createBadge))
                .setHeader("Category")
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createInfo))
                .setHeader("Name")
                .setWidth(UIUtils.COLUMN_WIDTH_XL)
                .setFlexGrow(1);
        grid.addColumn(Item::getVendor)
                .setHeader("Vendor")
                .setWidth(UIUtils.COLUMN_WIDTH_XL)
                .setFlexGrow(1);
        grid.addColumn(new ComponentRenderer<>(this::createPrice))
                .setHeader(UIUtils.createRightAlignedDiv("Unit Price ($)"))
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createStock))
                .setHeader(UIUtils.createRightAlignedDiv("In Catalogue"))
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createSold))
                .setHeader(UIUtils.createRightAlignedDiv("Units Sold"))
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);

        grid.addSelectionListener(e -> {
            if (e.getFirstSelectedItem().isPresent()) {
                showDetails(e.getFirstSelectedItem().get());
            }
        });

        grid.setSizeFull();

        // Grid wrapper
        Div gridWrapper = UIUtils.createDiv(Collections.singleton(GRID_VIEW), grid);

        // Details drawer
        initDetailsDrawer();

        // Set the content
        FlexLayout content = new FlexLayout(gridWrapper, detailsDrawer);
        content.setSizeFull();
        setContent(content);
    }

    private Component createInfo(Item item) {
        return new ListItem(item.getName(), item.getDesc());
    }

    private Component createPrice(Item item) {
        Double price = item.getPrice();
        Label label = UIUtils.createH4Label(UIUtils.formatAmount(price));
        return UIUtils.createRightAlignedDiv(label);
    }

    private Component createStock(Item item) {
        return UIUtils.createRightAlignedDiv(UIUtils.formatUnits(item.getStock()));
    }

    private Component createSold(Item item) {
        return UIUtils.createRightAlignedDiv(UIUtils.formatUnits(item.getSold()));
    }

    private void initDetailsDrawer() {
        detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

        // Footer
        Button save = UIUtils.createPrimaryButton("Save");
        save.addClickListener(e -> Notification.show("Not implemented yet.", 3000, Notification.Position.BOTTOM_CENTER));

        Button cancel = UIUtils.createTertiaryButton("Cancel");
        cancel.addClickListener(e -> detailsDrawer.hide());

        Button delete = UIUtils.createErrorPrimaryButton("Delete");
        delete.addClassName(LumoStyles.Margin.Left.AUTO);
        delete.addClickListener(e -> Notification.show("Not implemented yet.", 3000, Notification.Position.BOTTOM_CENTER));

        FlexLayout footer = UIUtils.createFlexLayout(Arrays.asList(LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Vertical.S, LumoStyles.Spacing.Right.S), save, cancel, delete);
        footer.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.CONTRAST_5);
        footer.setWidth("100%");

        detailsDrawer.setFooter(footer);
    }

    private void showDetails(Item item) {
        detailsDrawer.setContent(createDetails(item));
        detailsDrawer.show();
    }

    private Component createDetails(Item item) {
        Label title = UIUtils.createDetailsDrawerHeader("Edit Item", false, false);

        RadioButtonGroup<Item.Category> category = new RadioButtonGroup<>();
        category.setItems(Item.Category.values());
        category.setValue(item.getCategory());
        category.setRenderer(new ComponentRenderer<>(i -> UIUtils.createBadge(i)));
        category.getStyle().set(CSSProperties.Display.PROPERTY, CSSProperties.Display.FLEX);
        category.getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);

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
        FormLayout form = UIUtils.createFormLayout(Arrays.asList(LumoStyles.Padding.Bottom.L, LumoStyles.Padding.Horizontal.L));
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP));

        form.add(title);
        form.addFormItem(category, "Category").getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);
        form.addFormItem(name, "Name");
        form.addFormItem(desc, "Description");
        form.addFormItem(vendor, "Vendor");
        form.addFormItem(price, "Unit Price ($)");
        form.addFormItem(stock, "In Catalogue");
        form.addFormItem(sold, "Units Sold");

        return form;
    }

}
