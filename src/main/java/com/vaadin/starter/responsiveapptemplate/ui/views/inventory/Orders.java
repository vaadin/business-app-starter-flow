package com.vaadin.starter.responsiveapptemplate.ui.views.inventory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.responsiveapptemplate.backend.DummyData;
import com.vaadin.starter.responsiveapptemplate.backend.Item;
import com.vaadin.starter.responsiveapptemplate.backend.Order;
import com.vaadin.starter.responsiveapptemplate.backend.UIConfig;
import com.vaadin.starter.responsiveapptemplate.ui.Root;
import com.vaadin.starter.responsiveapptemplate.ui.components.ListItem;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.responsiveapptemplate.ui.utils.LumoStyles;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;
import com.vaadin.starter.responsiveapptemplate.ui.views.ViewFrame;

import java.util.Arrays;
import java.util.Iterator;

import static com.vaadin.starter.responsiveapptemplate.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "orders", layout = Root.class)
@PageTitle("Orders")
public class Orders extends ViewFrame {

    public Orders() {
        // Header
        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            setHeader(new AppBar("Orders"));
        }

        // Grid
        Grid<Order> grid = new Grid<>();
        grid.setDataProvider(DataProvider.ofCollection(DummyData.getOrders()));

        grid.addColumn(new ComponentRenderer<>(UIUtils::createBadge))
                .setHeader("Status")
                .setWidth(UIUtils.COLUMN_WIDTH_S)
                .setFlexGrow(0);
        grid.addColumn(Order::getCustomer)
                .setHeader("Customer")
                .setWidth(UIUtils.COLUMN_WIDTH_XL);
        grid.addColumn(new ComponentRenderer<>(this::createDate))
                .setHeader(UIUtils.createRightAlignedDiv("Order Received"))
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createItemCount))
                .setHeader(UIUtils.createRightAlignedDiv("Item Count"))
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createValue))
                .setHeader(UIUtils.createRightAlignedDiv("Value ($)"))
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);

        grid.setItemDetailsRenderer(new ComponentRenderer<>(this::createDetails));

        grid.setSizeFull();

        // Set the content
        setContent(grid);
        getContent().addClassName(GRID_VIEW);
    }

    private Component createItemCount(Order order) {
        int count = order.getItemCount();
        return UIUtils.createRightAlignedDiv(UIUtils.formatUnits(count));
    }

    private Component createValue(Order order) {
        Double value = order.getValue();
        Label label = UIUtils.createH4Label(UIUtils.formatAmount(value));
        return UIUtils.createRightAlignedDiv(label);
    }

    private Component createDate(Order order) {
        return UIUtils.createRightAlignedDiv(UIUtils.formatDate(order.getDate()));
    }

    private Component createDetails(Order order) {
        Div details = UIUtils.createDiv(Arrays.asList(LumoStyles.Padding.Vertical.XS));

        Iterator<Item> iterator = order.getItems().iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            ListItem listItem = new ListItem(item.getName(), item.getDesc(), UIUtils.createH5Label(UIUtils.formatAmount(item.getPrice())));
            if (iterator.hasNext()) {
                listItem.setDividerVisible(true);
            }
            details.add(listItem);
        }

        return details;
    }

}
