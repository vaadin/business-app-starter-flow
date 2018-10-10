package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.*;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.AbstractView;
import com.vaadin.starter.applayout.ui.components.AppBar;
import com.vaadin.starter.applayout.ui.components.DetailsDrawer;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "account-reporting", layout = Root.class)
@PageTitle("Account Reporting")
public class AccountReporting extends AbstractView {

    private Grid<Statement> grid;
    private ListDataProvider<Statement> dataProvider;

    private AppBar appBar;
    private FlexLayout content;
    private final DetailsDrawer detailsDrawer;

    public AccountReporting() {
        // Header
        appBar = new AppBar("Account Reporting");
        for (String tab : new String[] {"Balances", "Statements", "Coding Rules"}) {
            appBar.addTab(tab);
        }
        appBar.centerTabs();

        // Container for the grid and details drawer
        content = new FlexLayout();
        content.setSizeFull();

        // Grid
        grid = new Grid();
        grid.addColumn(Statement::getId)
                .setHeader("ID")
                .setFrozen(true)
                .setSortable(true)
                .setWidth("60px")
                .setFlexGrow(0);
        grid.addColumn(new LocalDateRenderer<>(Statement::getDate, "MMM dd, YYYY"))
                .setHeader("Date")
                .setSortable(true)
                .setWidth("160px")
                .setFlexGrow(0);
        grid.addColumn(Statement::getSender)
                .setHeader("Sender")
                .setWidth("240px")
                .setFlexGrow(1);
        grid.addColumn(new ComponentRenderer<>(this::createAmount))
                .setHeader(UIUtils.createRightAlignedDiv(new Text("Amount (EUR)")))
                .setWidth("160px")
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createStatus))
                .setHeader("Status")
                .setWidth("160px")
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createAttachment))
                .setHeader("Attachment")
                .setWidth("160px")
                .setFlexGrow(0);
        grid.addSelectionListener(e -> {
            if (e.getFirstSelectedItem().isPresent()) {
                showDetails(e.getFirstSelectedItem().get());
            }
        });
        grid.setSizeFull();

        // Data provider
        dataProvider = DataProvider.ofCollection(DummyData.getStatements());
        grid.setDataProvider(dataProvider);

        // Grid wrapper for some nice padding
        Div gridWrapper = UIUtils.createDiv(Collections.singleton(GRID_VIEW), grid);

        // Details drawer
        detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

        // Set the content's content.
        content.add(gridWrapper, detailsDrawer);
    }

    @Override
    protected void initSlots() {
        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            setHeader(appBar);
        }
        setContent(content);
    }

    private void showDetails(Statement statement) {
        detailsDrawer.setContent(createForm(statement));
        detailsDrawer.show();
    }

    private FormLayout createForm(Statement statement) {
        TextField id = new TextField("ID");
        id.setValue(String.valueOf(statement.getId()));

        DatePicker date = new DatePicker("Date");
        date.setValue(statement.getDate());

        TextField sender = new TextField("Sender");
        sender.setValue(statement.getSender());

        TextField amount = new TextField("Amount");
        amount.setValue(String.valueOf(statement.getAmount()));

        ComboBox<Statement.Status> status = new ComboBox<>("Status");
        status.setItems(Statement.Status.values());
        status.setItemLabelGenerator((ItemLabelGenerator<Statement.Status>) status1 -> status1.getName());
        status.setValue(statement.getStatus());

        return new FormLayout(id, date, sender, amount, status);
    }

    private Component createAmount(Statement statement) {
        return UIUtils.createRightAlignedDiv(new Text(Double.toString(statement.getAmount())));
    }

    private Component createStatus(Statement statement) {
        Statement.Status status = statement.getStatus();
        Span badge = new Span(status.getName());
        if (status.equals(Statement.Status.PROCESSED)) {
            badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.SUCCESS);
        } else {
            badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.ERROR);
        }
        return badge;
    }

    private Component createAttachment(Statement statement) {
        Icon icon = new Icon(VaadinIcon.FILE);
        if (statement.hasAttachment()) {
            icon.addClassName(LumoStyles.TextColor.PRIMARY);
        } else {
            icon.addClassName(LumoStyles.TextColor.DISABLED);
        }
        return icon;
    }
}
