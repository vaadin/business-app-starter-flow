package com.vaadin.starter.applayout.ui.views.inventory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.DummyData;
import com.vaadin.starter.applayout.backend.Item;
import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;
import com.vaadin.starter.applayout.ui.views.ViewFrame;

import java.util.Collections;

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "stock", layout = Root.class)
@PageTitle("Stock")
public class Stock extends ViewFrame {

    public Stock() {
        // Header
        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            AppBar appBar = new AppBar("Stock");
            setHeader(appBar);
        }

        // Grid
        Grid<Item> grid = new Grid<>();
        grid.setDataProvider(DataProvider.ofCollection(DummyData.getItems()));

        grid.addColumn(new ComponentRenderer<>(this::createCategory))
                .setHeader("Category")
                .setWidth(UIUtils.COLUMN_WIDTH_S)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createInfo))
                .setHeader("Name")
                .setWidth(UIUtils.COLUMN_WIDTH_L)
                .setFlexGrow(1);
        grid.addColumn(Item::getVendor)
                .setHeader("Vendor")
                .setWidth(UIUtils.COLUMN_WIDTH_L)
                .setFlexGrow(1);
        grid.addColumn(new ComponentRenderer<>(this::createPrice))
                .setHeader(UIUtils.createRightAlignedDiv(new Text("Unit Price (€)")))
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createStock))
                .setHeader(UIUtils.createRightAlignedDiv(new Text("In Stock")))
                .setWidth(UIUtils.COLUMN_WIDTH_S)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createSold))
                .setHeader(UIUtils.createRightAlignedDiv(new Text("Units Sold")))
                .setWidth(UIUtils.COLUMN_WIDTH_S)
                .setFlexGrow(0);

        grid.setSizeFull();

        setContent(grid);
        getContent().addClassName(GRID_VIEW);
    }

    private Component createInfo(Item item) {
        return new ListItem(item.getName(), item.getDesc());
    }

    private Component createCategory(Item item) {
        Item.Category category = item.getCategory();
        Span badge = new Span(category.getName());

        switch (category) {
            case HEALTHCARE:
                badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.SUCCESS);
                break;
            case CONSTRUCTION:
                badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.CONTRAST);
                break;
            default:
                badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.DEFAULT);
                break;
        }

        return badge;
    }

    private Component createPrice(Item item) {
        Double price = item.getPrice();
        Label label = UIUtils.createLabel(Collections.singleton(LumoStyles.FontSize.H4), UIUtils.formatAmount(price));
        return UIUtils.createRightAlignedDiv(label);
    }

    private Component createStock(Item item) {
        return UIUtils.createRightAlignedDiv(new Text(UIUtils.formatUnits(item.getStock())));
    }

    private Component createSold(Item item) {
        return UIUtils.createRightAlignedDiv(new Text(UIUtils.formatUnits(item.getSold())));
    }

}
