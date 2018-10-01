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
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.applayout.backend.DummyData;
import com.vaadin.starter.applayout.backend.Person;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.AbstractView;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.FILTER_LIST_VIEW;

@Route(value = "filter-list", layout = Root.class)
@PageTitle("Filter list")
public class FilterList extends AbstractView {

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
        tokenRemove.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.SMALL_TERTIARY_ICON);
        tokenRemove.setIcon(new Icon(VaadinIcon.CLOSE_SMALL));
        token.add(tokenRemove);

        filterOptions = new FlexLayout();
        filterOptions.getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);
        filterOptions.setClassName("filter-options");
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

        // Grid
        grid = new Grid();
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
        grid.addColumn(new ComponentRenderer<>(this::createTwitter))
                .setHeader("Twitter")
                .setWidth("160px")
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createForumPosts))
                .setHeader("Forum Posts")
                .setWidth("160px")
                .setFlexGrow(0);
        grid.addColumn(new LocalDateRenderer<>(Person::getLastModified, "MMM dd, YYYY"))
                .setHeader("Last Modified")
                .setSortable(true)
                .setWidth("160px")
                .setFlexGrow(0);

        DataProvider dataProvider = DataProvider.ofCollection(DummyData.getPersons());
        grid.setDataProvider(dataProvider);
        grid.setSizeFull();
    }

    @Override
    protected void initSlots() {
        setContent(grid);
        getContent().addClassName(FILTER_LIST_VIEW);
    }

    private Component createUserInfo(Person person) {
        return new ListItem(person.getInitials(), person.getName(), person.getEmail());
    }

    private Component createTwitter(Person person) {
        Icon icon = new Icon(VaadinIcon.TWITTER);
        if (person.getTwitter() != null && !person.getTwitter().isEmpty()) {
            icon.addClassName(LumoStyles.TextColor.PRIMARY);
        } else {
            icon.addClassName(LumoStyles.TextColor.DISABLED);
        }
        return icon;
    }

    private Component createForumPosts(Person person) {
        Span badge = new Span(Integer.toString(person.getForumPosts()));
        if (person.getForumPosts() > 5) {
            badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.SUCCESS);
        } else {
            badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.ERROR);
        }
        return badge;
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
