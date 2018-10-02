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
import com.vaadin.flow.component.orderedlayout.FlexComponent;
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

import java.util.Arrays;
import java.util.Collections;

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.FILTER_LIST_VIEW;
import static com.vaadin.starter.applayout.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "filter-list", layout = Root.class)
@PageTitle("Filter list")
public class FilterList extends AbstractView {

    private Grid<Person> grid;

    private Div filterArea;
    private Button toggleButton;

    private String COLLAPSED = "collapsed";

    public FilterList() {
        filterArea = UIUtils.createDiv(
                Arrays.asList("filter-area", LumoStyles.Padding.Responsive.Horizontal.SM, LumoStyles.Padding.Vertical.XS),
                createFilterHeader(),
                UIUtils.createWrappingFlexLayout(
                        createFilterOptions(),
                        createTokens()
                )
        );
        filterArea.getStyle().set(CSSProperties.FlexShrink.PROPERTY, CSSProperties.FlexShrink._0);

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
        grid.setSizeFull();

        DataProvider dataProvider = DataProvider.ofCollection(DummyData.getPersons());
        grid.setDataProvider(DataProvider.ofCollection(DummyData.getPersons()));
    }

    @Override
    protected void initSlots() {
        setHeader(filterArea);
        setContent(grid);
        getContent().addClassName(FILTER_LIST_VIEW);
    }

    private FlexLayout createFilterHeader() {
        toggleButton = UIUtils.createTertiaryButton(Collections.singleton("filter-toggle"), VaadinIcon.CHEVRON_UP_SMALL, "Filter options");
        toggleButton.addClickListener(event -> toggleFilterArea());

        Label suffix = UIUtils.createLabel(Collections.singleton("filters-badge"), "4");
        suffix.getElement().setProperty("slot", "suffix");
        suffix.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.CONTRAST);
        toggleButton.getElement().appendChild(suffix.getElement());

        TextField search = new TextField();
        search.setPlaceholder("Quick filter...");

        FlexLayout header = UIUtils.createFlexLayout(Collections.singleton(LumoStyles.Spacing.Right.M), toggleButton, search);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        return header;
    }

    private FlexLayout createFilterOptions() {
        Label title = UIUtils.createLabel(Arrays.asList(LumoStyles.FontSize.H5), "Filter options");

        ComboBox combo = new ComboBox();
        combo.setPlaceholder("Select...");

        Checkbox checkbox = new Checkbox("Checkbox label");

        RadioButtonGroup optionGroup = new RadioButtonGroup();
        optionGroup.setItems("Option 1", "Option 2", "Option 3");

        return UIUtils.createColumn(Arrays.asList("filter-section", LumoStyles.Spacing.Bottom.S), title, combo, checkbox, optionGroup);
    }

    private Component createTokens() {
        Label title = UIUtils.createLabel(Arrays.asList(LumoStyles.FontSize.H5), "Filter tokens");

        ComboBox combo = new ComboBox();
        combo.setClassName("token-select");
        combo.setPlaceholder("Add filter...");

        Div token = UIUtils.createDiv(Collections.singleton("token"), new Label("Filter token 1"), UIUtils.createSmallTertiaryIconButton(VaadinIcon.CLOSE_SMALL));
        token.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.DEFAULT);

        Div tokenArea = UIUtils.createDiv(Collections.singleton("token-area"), token);

        return UIUtils.createColumn(Arrays.asList("filter-section", LumoStyles.Spacing.Bottom.S), title, combo, tokenArea);
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

    private void toggleFilterArea() {
        if (filterArea.getClassName().contains(COLLAPSED)) {
            filterArea.removeClassName(COLLAPSED);
            toggleButton.setIcon(new Icon(VaadinIcon.CHEVRON_UP_SMALL));
        } else {
            filterArea.addClassName(COLLAPSED);
            toggleButton.setIcon(new Icon(VaadinIcon.CHEVRON_DOWN_SMALL));
        }
    }

}
