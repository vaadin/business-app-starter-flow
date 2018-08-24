package com.vaadin.starter.responsivelayoutgrid.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.responsivelayoutgrid.backend.DummyData;
import com.vaadin.starter.responsivelayoutgrid.backend.InitialCoinOffering;
import com.vaadin.starter.responsivelayoutgrid.ui.MainLayout;
import com.vaadin.starter.responsivelayoutgrid.ui.components.ListItem;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Initial Coin Offerings")
public class ICOMasterView extends Div {

    private final String GRID_VIEW = "grid-view";
    private final Grid<InitialCoinOffering> grid;

    private enum Tab {
        ONGOING, UPCOMING, CLOSED
    }

    private Tab selected;

    public ICOMasterView() {
        setClassName(GRID_VIEW);
        getStyle().set("flex", "1");

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
                .setHeader(createRightAlignedDiv(new Text("Amount Raised")))
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
        return createRightAlignedDiv(new Text(Double.toString(ico.getAmountRaised())));
    }

    private void viewDetails(InitialCoinOffering ico) {
        UI.getCurrent().navigate(ICODetailsView.class, ico.getId());
    }

    private Div createRightAlignedDiv(Component... components) {
        Div div = new Div(components);
        div.getStyle().set("text-align", "right");
        return div;
    }

    public void selectTab(Tab tab) {

    }
}
