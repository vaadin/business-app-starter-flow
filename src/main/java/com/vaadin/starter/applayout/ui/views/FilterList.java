package com.vaadin.starter.applayout.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
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

    private String FILTER_SECTION = "filter-section";;

    private Grid<Person> grid;

    private Div filterArea;
    private Button toggleButton;
    private FlexLayout tokens;
    private FlexLayout options;

    public FilterList() {
        filterArea = UIUtils.createDiv(
                Arrays.asList("filter-area", LumoStyles.Padding.Responsive.Horizontal.ML, LumoStyles.Shadow.M),
                createFilterHeader(),
                UIUtils.createWrappingFlexLayout(
                        Collections.singleton(LumoStyles.Spacing.Right.L),
                        createFilterOptions(),
                        createTokens()
                )
        );
        filterArea.getElement().setAttribute(LumoStyles.THEME, LumoStyles.DARK);

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
        grid.setDataProvider(dataProvider);
    }

    @Override
    protected void initSlots() {
        setHeader(filterArea);
        setContent(grid);
        getContent().addClassName(FILTER_LIST_VIEW);
    }

    private FlexLayout createFilterHeader() {
        toggleButton = UIUtils.createTertiaryIconButton(VaadinIcon.CHEVRON_UP_SMALL);
        toggleButton.addClickListener(event -> toggleFilterArea());

        Label title = UIUtils.createLabel(Collections.singleton(LumoStyles.FontSize.H4), "Filter");

        Label badge = new Label("4");
        badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.CONTRAST);

        TextField search = new TextField();
        search.setPlaceholder("Quick filter...");
        search.addClassName(LumoStyles.Margin.Left.AUTO);

        FlexLayout header = UIUtils.createFlexLayout(Arrays.asList(LumoStyles.Padding.Vertical.XS, LumoStyles.Spacing.Right.S), toggleButton, title, badge, search);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        return header;
    }

    private FlexLayout createFilterOptions() {
        Label title = UIUtils.createLabel(Arrays.asList(LumoStyles.FontSize.H5), "Options");

        ComboBox combo = new ComboBox();
        combo.setPlaceholder("Select...");

        Checkbox checkbox = new Checkbox("Checkbox label");

        RadioButtonGroup optionGroup = new RadioButtonGroup();
        optionGroup.setItems("Option 1", "Option 2", "Option 3");

        options = UIUtils.createColumn(Arrays.asList(FILTER_SECTION, LumoStyles.Padding.Vertical.M, LumoStyles.Spacing.Bottom.S), title, combo, checkbox, optionGroup);
        return options;
    }

    private Component createTokens() {
        Label title = UIUtils.createLabel(Arrays.asList(LumoStyles.FontSize.H5), "Tokens");

        ComboBox combo = new ComboBox();
        combo.setClassName("token-select");
        combo.setPlaceholder("Add filter...");

        Div token = UIUtils.createDiv(Collections.singleton("token"), new Label("Filter token 1"), UIUtils.createSmallTertiaryIconButton(VaadinIcon.CLOSE_SMALL));
        token.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.DEFAULT);

        Div tokenArea = new Div(token);

        tokens = UIUtils.createColumn(Arrays.asList(FILTER_SECTION, LumoStyles.Padding.Vertical.M, LumoStyles.Spacing.Bottom.S), title, combo, tokenArea);
        return tokens;
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
        options.setVisible(!options.isVisible());
        tokens.setVisible(!tokens.isVisible());
        toggleButton.setIcon(options.isVisible() ? new Icon(VaadinIcon.CHEVRON_UP_SMALL) : new Icon(VaadinIcon.CHEVRON_DOWN_SMALL));
    }

}
