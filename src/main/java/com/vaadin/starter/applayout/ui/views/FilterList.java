package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.Person;
import com.vaadin.starter.applayout.ui.MainLayout;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.FILTER_LIST_VIEW;

@Route(value = "filter-list", layout = MainLayout.class)
@PageTitle("Filter list")
public class FilterList extends FlexLayout {

    private final Grid<Person> grid;
    private final Div filterArea;
    private final Div filterAreaHeader;
    private final FlexLayout filterTokens;
    private final ComboBox tokenSelect;
    private final Div tokenArea;
    private final Div token;
    private final Button filterAreaToggle = UIUtils.createSmallButton(VaadinIcon.CHEVRON_UP_SMALL, "Filter options");
    private final Label tokenLabel;
    private final Button tokenRemove;
    private final Label filtersBadge;

    private final String TOGGLE = "collapsed";
    private final FlexLayout filterOptions;
    private final ComboBox filterSelect;
    private final Checkbox filterOptionCheckBox;
    private final Label filterTokensLabel;
    private final Label filterOptionsLabel;
    private final RadioButtonGroup FilterOptionsRadioButtons;
    private final TextField quickFilter;

    public FilterList() {
        setClassName(FILTER_LIST_VIEW);
        getStyle().set(CSSProperties.Flex.PROPERTY, "1");
        getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);

        filterArea = new Div();
        filterArea.setClassName("filter-area");
        filterArea.getStyle().set(CSSProperties.FlexShrink.PROPERTY, CSSProperties.FlexShrink._0);
        filterArea.addClassNames(LumoStyles.Padding.Responsive.Horizontal.SM);
        filterArea.addClassNames(LumoStyles.Padding.Vertical.XS);
        add(filterArea);

        filterAreaHeader = new Div();
        filterAreaHeader.setClassName("filter-area-header");
        filterAreaHeader.setWidth("100%");
        filterArea.add(filterAreaHeader);

        filterAreaToggle.addClickListener(event -> setFilterAreaEnabled(filterArea.getClassName().contains(TOGGLE)));
        filterAreaToggle.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.TERTIARY_ICON);
        filterAreaToggle.setClassName("filter-toggle");
        filterAreaHeader.add(filterAreaToggle);

        filtersBadge = new Label("4");
        filtersBadge.getElement().setProperty("slot", "suffix");
        filtersBadge.setClassName("filters-badge");
        filtersBadge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.CONTRAST);
        filterAreaToggle.getElement().appendChild(filtersBadge.getElement());

        filtersBadge = new Label("4");

        quickFilter = new TextField();
        quickFilter.setPlaceholder("Quick filter...");
        filterAreaHeader.add(quickFilter);

        filterTokens = new FlexLayout();
        filterTokens.getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);
        filterTokens.setClassName("filter-tokens");
        filterArea.add(filterTokens);

        filterTokensLabel = new Label("Filter tokens");
        filterTokensLabel.addClassName("h5");
        filterTokensLabel.addClassNames(LumoStyles.Margin.Vertical.XS);
        filterTokens.add(filterTokensLabel);

        tokenSelect = new ComboBox();
        tokenSelect.setClassName("token-select");
        tokenSelect.setPlaceholder("Add filter...");
        tokenSelect.addClassNames(LumoStyles.Margin.Vertical.XS);
        filterTokens.add(tokenSelect);

        tokenArea = new Div();
        tokenArea.setClassName("token-area");
        filterTokens.add(tokenArea);

        token = new Div();
        token.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.DEFAULT);
        token.setClassName("token");
        tokenArea.add(token);

        tokenLabel = new Label("Filter token 1");
        token.add(tokenLabel);

        tokenRemove = new Button();
        tokenRemove .getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.SMALL_TERTIARY_ICON);
        tokenRemove.setIcon(new Icon(VaadinIcon.CLOSE_SMALL));
        token.add(tokenRemove);

        filterOptions = new FlexLayout();
        filterOptions.getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);
        filterOptions .setClassName("filter-options");
        filterArea.add(filterOptions);

        filterOptionsLabel = new Label("Filter options");
        filterOptionsLabel.addClassName("h5");
        filterOptionsLabel.addClassNames(LumoStyles.Margin.Vertical.XS);
        filterOptionsLabel.setWidth("100%");
        filterOptions.add(filterOptionsLabel);

        filterSelect = new ComboBox();
        filterSelect.setPlaceholder("Select...");
        filterSelect.addClassNames(LumoStyles.Margin.Vertical.XS);
        filterOptions.add(filterSelect);

        filterOptionCheckBox = new Checkbox();
        filterOptionCheckBox.setLabel("Checkbox label");
        filterOptionCheckBox.addClassNames(LumoStyles.Margin.Vertical.XS);
        filterOptions.add(filterOptionCheckBox);

        FilterOptionsRadioButtons = new RadioButtonGroup();
        FilterOptionsRadioButtons.setItems("Option 1", "Option 2", "Option 3");
        FilterOptionsRadioButtons.addClassNames(LumoStyles.Margin.Vertical.XS);
        filterOptions.add(FilterOptionsRadioButtons);

        grid = new Grid();
        grid.getStyle().set(CSSProperties.Flex.PROPERTY, "1");
        grid.addColumn(Person::getId)
                .setHeader("ID")
                .setFrozen(true)
                .setSortable(true)
                .setWidth("60px")
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createUserInfo))
                .setHeader("Name")
                .setWidth("240px")
                .setFlexGrow(1);
        grid.addColumn(new ComponentRenderer<>(this::createOriginOfFunds))
                .setHeader("Origin of Funds")
                .setWidth("160px")
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createTimesDenied))
                .setHeader("Times Denied")
                .setWidth("160px")
                .setFlexGrow(0);
        grid.addColumn(new LocalDateRenderer<>(Person::getLastModified, "MMM dd, YYYY"))
                .setHeader("Last Modified")
                .setSortable(true)
                .setWidth("160px")
                .setFlexGrow(0);

        grid.setItems(getItems());
        grid.setSizeFull();
        add(grid);
    }

    private Component createUserInfo(Person person) {
        return new ListItem(person.getInitials(), person.getName(), person.getEmail());
    }

    private Component createOriginOfFunds(Person person) {
        Icon icon = new Icon(VaadinIcon.FILE);
        if (person.isOriginOfFunds()) {
            icon.addClassName(LumoStyles.Text.PRIMARY);
        } else {
            icon.addClassName(LumoStyles.Text.SECONDARY);
        }
        return icon;
    }

    private Component createTimesDenied(Person person) {
        Span badge = new Span(Integer.toString(person.getTimesDenied()));
        if (person.getTimesDenied() > 5) {
            badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.ERROR);
        } else {
            badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.DEFAULT);
        }
        return badge;
    }

    private List<Person> getItems() {
        List<Person> items = new ArrayList<>();
        int i = 0;
        items.add(new Person(i++, "Rolf", "Smeds", "rolfa@email.com", true, 10, LocalDate.now()));
        items.add(new Person(i++, "Haijian", "Wang", "haijian@email.com", false, 1, LocalDate.now()));
        items.add(new Person(i++, "Jaska", "Kemppainen", "jaska@email.com", true, 0, LocalDate.now()));
        items.add(new Person(i++, "Marcio", "Dantas", "marcio@email.com", false, 8, LocalDate.now()));
        items.add(new Person(i++, "Vesa", "Nieminen", "vesa@email.com", false, 0, LocalDate.now()));
        items.add(new Person(i++, "Susanna", "Laalo", "susanna@email.com", true, 64, LocalDate.now()));
        items.add(new Person(i++, "Hannu", "Salonen", "hannu@email.com", false, 4, LocalDate.now()));
        items.add(new Person(i++, "Juuso", "Kantonen", "juuso@email.com", true, 7, LocalDate.now()));
        return items;
    }

    private void setFilterAreaEnabled(boolean enabled) {
        if (enabled) {
            filterArea.removeClassName(TOGGLE);
            filterAreaToggle.setIcon(new Icon(VaadinIcon.CHEVRON_UP_SMALL));
        } else {
            filterArea.addClassName(TOGGLE);
            filterAreaToggle.setIcon(new Icon(VaadinIcon.CHEVRON_DOWN_SMALL));
        }
    }

}
