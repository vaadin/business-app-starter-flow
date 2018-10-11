package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.starter.applayout.backend.DummyData;
import com.vaadin.starter.applayout.backend.Statement;
import com.vaadin.starter.applayout.ui.components.DetailsDrawer;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

import java.util.Arrays;
import java.util.Collections;

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.GRID_VIEW;

public class Statements extends FlexLayout {

    private Grid<Statement> grid;
    private ListDataProvider<Statement> dataProvider;

    private DetailsDrawer detailsDrawer;

    public Statements() {
        setHeight("100%");

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
                .setWidth("140px")
                .setFlexGrow(0);
        grid.addColumn(Statement::getPayee)
                .setHeader("Payee")
                .setSortable(true)
                .setWidth("240px")
                .setFlexGrow(1);
        grid.addColumn(new ComponentRenderer<>(this::createOutput))
                .setHeader(UIUtils.createRightAlignedDiv(new Text("Output (EUR)")))
                .setWidth("120px")
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createInput))
                .setHeader(UIUtils.createRightAlignedDiv(new Text("Input (EUR)")))
                .setWidth("120px")
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
        initDetailsDrawer();

        // TBD
        add(gridWrapper, detailsDrawer);
    }

    private void initDetailsDrawer() {
        detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

        Tabs tabs = new Tabs(new Tab("Details"), new Tab("Attachments"), new Tab("History"));
        tabs.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Tabs.EQUAL_WIDTH_TABS);
        detailsDrawer.setHeader(tabs);

        Button cancel = UIUtils.createTertiaryButton("Cancel");
        cancel.addClickListener(e -> detailsDrawer.hide());

        Button save = UIUtils.createPrimaryButton("Save");

        FlexLayout footer = UIUtils.createFlexLayout(Arrays.asList(LumoStyles.Padding.All.S, LumoStyles.Spacing.Right.S), cancel, save);
        footer.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.CONTRAST_5);
        footer.setJustifyContentMode(JustifyContentMode.END);
        footer.setWidth("100%");
        detailsDrawer.setFooter(footer);
    }

    private void showDetails(Statement statement) {
        detailsDrawer.setContent(createForm(statement));
        detailsDrawer.show();
    }

    private Component createForm(Statement statement) {
        TextField id = new TextField("ID");
        id.setValue(String.valueOf(statement.getId()));

        DatePicker date = new DatePicker("Date");
        date.setValue(statement.getDate());

        TextField sender = new TextField("Payee");
        sender.setValue(statement.getPayee());

        TextField output = new TextField("Output");
        output.setValue(String.valueOf(statement.getOutput()));

        TextField input = new TextField("Input");
        input.setValue(String.valueOf(statement.getInput()));

        RadioButtonGroup<Statement.Status> status = new RadioButtonGroup();
        status.setItems(Statement.Status.values());
        status.setRenderer(new TextRenderer<>(Statement.Status::getName));
        status.setValue(statement.getStatus());
        status.getStyle().set(CSSProperties.Display.PROPERTY, CSSProperties.Display.FLEX);
        status.getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);

        FormLayout form = new FormLayout(id, date, sender, output, input);

        FormLayout.FormItem statusItem = form.addFormItem(status, "Status");
        statusItem.getStyle().set(CSSProperties.Display.PROPERTY, CSSProperties.Display.FLEX);
        statusItem.getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);

        return UIUtils.createColumn(form);
    }

    private Component createOutput(Statement statement) {
        return UIUtils.createRightAlignedDiv(new Text(Double.toString(statement.getOutput())));
    }

    private Component createInput(Statement statement) {
        return UIUtils.createRightAlignedDiv(new Text(Double.toString(statement.getInput())));
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
