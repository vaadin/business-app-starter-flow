package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.DummyData;
import com.vaadin.starter.applayout.backend.InitialCoinOffering;
import com.vaadin.starter.applayout.ui.MainLayout;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "initial-coin-offerings", layout = MainLayout.class)
@PageTitle("Initial Coin Offerings")
public class ICOMasterView extends Div {

    private final Grid<InitialCoinOffering> grid;

    public ICOMasterView() {
        setClassName(GRID_VIEW);
        getStyle().set(CSSProperties.Flex.PROPERTY, "1");

        grid = new Grid();
        grid.addColumn(new ComponentRenderer<>(this::createICOInfo))
                .setHeader("Name")
                .setFrozen(true)
                .setSortable(true)
                .setWidth("200px")
                .setFlexGrow(1);
        grid.addColumn(new LocalDateRenderer<>(InitialCoinOffering::getStartDate, "MMM dd, YYYY"))
                .setHeader("Start Date")
                .setSortable(true)
                .setWidth("160px")
                .setFlexGrow(0);
        grid.addColumn(new LocalDateRenderer<>(InitialCoinOffering::getEndDate, "MMM dd, YYYY"))
                .setHeader("End Date")
                .setSortable(true)
                .setWidth("160px")
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createAmountRaised))
                .setHeader(UIUtils.createRightAlignedDiv(new Text("Amount Raised")))
                .setSortable(true)
                .setWidth("160px")
                .setFlexGrow(0);

        grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::viewDetails));
        grid.setItems(DummyData.getAll());
        grid.setSizeFull();
        add(grid);
    }

    private Component createICOInfo(InitialCoinOffering ico) {
        return new ListItem(ico.getSource(), ico.getName());
    }

    private Component createAmountRaised(InitialCoinOffering ico) {
        return UIUtils.createRightAlignedDiv(new Text(Double.toString(ico.getAmountRaised())));
    }

    private void viewDetails(InitialCoinOffering ico) {
        UI.getCurrent().navigate(ICODetailsView.class, ico.getId());
    }
}
