package com.vaadin.starter.applayout.ui.views.demo;

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
import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.ListItem;
import com.vaadin.starter.applayout.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.applayout.ui.utils.BoxShadowBorders;
import com.vaadin.starter.applayout.ui.utils.CSSProperties;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;
import com.vaadin.starter.applayout.ui.views.ViewFrame;

import java.util.Arrays;
import java.util.Collections;

import static com.vaadin.starter.applayout.ui.utils.ViewStyles.FILTER_LIST_VIEW;

@Route(value = "filter-list", layout = Root.class)
@PageTitle("Filter list")
public class FilterList extends ViewFrame {

    private Button toggleButton;
    private FlexLayout tokens;
    private FlexLayout options;
    private FlexLayout filterHeader;

    public FilterList() {
        // Header
        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            setHeader(new AppBar("Statistics"));
        }

        // Filters
        Div filterArea = UIUtils.createDiv(
                Arrays.asList("filter-area", LumoStyles.Padding.Responsive.Horizontal.SL),
                createFilterHeader(),
                UIUtils.createWrappingFlexLayout(
                        Collections.singleton(LumoStyles.Spacing.Right.L),
                        createFilterOptions(),
                        createTokens()
                )
        );

        // Grid
        Grid<Person> grid = new Grid<>();
        grid.addColumn(Person::getId)
                .setHeader("ID")
                .setFrozen(true)
                .setSortable(true)
                .setWidth(UIUtils.COLUMN_WIDTH_XS)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createUserInfo))
                .setHeader("Name")
                .setWidth(UIUtils.COLUMN_WIDTH_L)
                .setFlexGrow(1);
        grid.addColumn(new ComponentRenderer<>(this::createTwitter))
                .setHeader("Twitter")
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createForumPosts))
                .setHeader("Forum Posts")
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.addColumn(new LocalDateRenderer<>(Person::getLastModified, "MMM dd, YYYY"))
                .setHeader("Last Modified")
                .setSortable(true)
                .setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);

        DataProvider dataProvider = DataProvider.ofCollection(DummyData.getPersons());
        grid.setDataProvider(dataProvider);

        // Content wrapper
        FlexLayout content = UIUtils.createColumn(Collections.singleton(FILTER_LIST_VIEW), filterArea, grid);
        content.setHeight("100%");
        setContent(content);
    }

    private FlexLayout createFilterHeader() {
        toggleButton = UIUtils.createTertiaryIconButton(VaadinIcon.CHEVRON_UP_SMALL);
        toggleButton.addClickListener(event -> toggleFilterArea());

        Label title = UIUtils.createLabel(Collections.singleton(LumoStyles.FontSize.H5), "Filter");

        Label badge = new Label("4");
        badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.CONTRAST);

        TextField search = new TextField();
        search.setPlaceholder("Quick filter...");
        search.addClassName(LumoStyles.Margin.Left.AUTO);

        filterHeader = UIUtils.createFlexLayout(Arrays.asList("filter-header", BoxShadowBorders.BOTTOM, LumoStyles.Padding.Vertical.XS, LumoStyles.Spacing.Right.S), toggleButton, title, badge, search);
        filterHeader.setAlignItems(FlexComponent.Alignment.CENTER);
        return filterHeader;
    }

    private FlexLayout createFilterOptions() {
        Label title = UIUtils.createLabel(Arrays.asList(LumoStyles.FontSize.H6), "Options");

        ComboBox combo = new ComboBox();
        combo.setPlaceholder("Select...");

        Checkbox checkbox = new Checkbox("Checkbox label");

        RadioButtonGroup<String> optionGroup = new RadioButtonGroup<>();
        optionGroup.setItems("Option 1", "Option 2", "Option 3");

        options = UIUtils.createColumn(Arrays.asList(LumoStyles.Padding.Vertical.M, LumoStyles.Spacing.Bottom.S), title, combo, checkbox, optionGroup);
        options.getStyle().set(CSSProperties.FlexGrow.PROPERTY, CSSProperties.FlexGrow._1);
        return options;
    }

    private Component createTokens() {
        Label title = UIUtils.createLabel(Arrays.asList(LumoStyles.FontSize.H6), "Tokens");

        ComboBox combo = new ComboBox();
        combo.setClassName("token-select");
        combo.setPlaceholder("Add filter...");

        Div token = UIUtils.createDiv(Collections.singleton("token"), new Label("Filter token 1"), UIUtils.createSmallTertiaryIconButton(VaadinIcon.CLOSE_SMALL));
        token.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.DEFAULT);

        Div tokenArea = new Div(token);

        tokens = UIUtils.createColumn(Arrays.asList(LumoStyles.Padding.Vertical.M, LumoStyles.Spacing.Bottom.S), title, combo, tokenArea);
        tokens.getStyle().set(CSSProperties.FlexGrow.PROPERTY, CSSProperties.FlexGrow._1);
        return tokens;
    }

    private Component createUserInfo(Person person) {
        return new ListItem(UIUtils.createInitials(person.getInitials()), person.getName(), person.getEmail());
    }

    private Component createTwitter(Person person) {
        Icon icon = new Icon(VaadinIcon.TWITTER);
        if (person.getRandomBoolean()) {
            icon.addClassName(LumoStyles.TextColor.PRIMARY);
        } else {
            icon.addClassName(LumoStyles.TextColor.DISABLED);
        }
        return icon;
    }

    private Component createForumPosts(Person person) {
        Span badge = new Span(Integer.toString(person.getRandomInteger()));
        if (person.getRandomInteger() > 5) {
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

        if (options.isVisible()) {
            filterHeader.addClassName(BoxShadowBorders.BOTTOM);
        } else {
            filterHeader.removeClassName(BoxShadowBorders.BOTTOM);
        }
    }

}