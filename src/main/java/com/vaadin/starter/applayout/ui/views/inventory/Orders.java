package com.vaadin.starter.applayout.ui.views.inventory;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.*;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;
import com.vaadin.starter.applayout.ui.views.ViewFrame;

import java.util.Collections;
import java.util.StringJoiner;

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "orders", layout = Root.class)
@PageTitle("Orders")
public class Orders extends ViewFrame {

    public Orders() {
        // Header
        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            AppBar appBar = new AppBar("Orders");
            setHeader(appBar);
        }

        // Grid
        Grid<Order> grid = new Grid<>();
        grid.setDataProvider(DataProvider.ofCollection(DummyData.getOrders()));

        grid.addColumn(new ComponentRenderer<>(this::createStatus))
                .setHeader("Status")
                .setWidth(UIUtils.COLUMN_WIDTH_S)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createItems))
                .setHeader("Items")
                .setWidth(UIUtils.COLUMN_WIDTH_L)
                .setFlexGrow(1);
        grid.addColumn(Order::getCustomer)
                .setHeader("Customer");
        grid.addColumn(new ComponentRenderer<>(this::createValue))
                .setHeader(UIUtils.createRightAlignedDiv(new Text("Value (€)")))
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.addColumn(new LocalDateRenderer<>(Order::getDate, "MMM dd, YYYY"))
                .setHeader("Order Received")
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);

        grid.setItemDetailsRenderer(new ComponentRenderer<>(this::createDetails));

        grid.setSizeFull();

        setContent(grid);
        getContent().addClassName(GRID_VIEW);
    }

    private Component createStatus(Order Order) {
        Order.Status status = Order.getStatus();
        Span badge = new Span(status.getName());

        switch (status) {
            case SENT:
                badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.SUCCESS);
                break;
            case ONGOING:
                badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.DEFAULT);
                break;
            default:
                badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.ERROR);
                break;
        }

        return badge;
    }

    private Component createItems(Order order) {
        StringJoiner joiner = new StringJoiner(", ");
        order.getItems().forEach(item -> joiner.add(item.getName()));
        return new Text(joiner.toString());
    }

    private Component createValue(Order order) {
        Double value = order.getValue();
        Label label = UIUtils.createLabel(Collections.singleton(LumoStyles.FontSize.H4), UIUtils.formatAmount(value));
        return UIUtils.createRightAlignedDiv(label);
    }

    private Component createDetails(Order order) {
        Div details = new Div();
        for (Item item : order.getItems()) {
            ListItem listItem = new ListItem(item.getName(), item.getDesc());
            listItem.setSuffix(new Text(UIUtils.formatAmount(item.getPrice())));
            details.add(listItem);
        }
        return details;
    }

}
