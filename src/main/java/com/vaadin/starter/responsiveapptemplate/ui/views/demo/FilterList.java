package com.vaadin.starter.responsiveapptemplate.ui.views.demo;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import com.vaadin.starter.responsiveapptemplate.backend.DummyData;
import com.vaadin.starter.responsiveapptemplate.backend.Person;
import com.vaadin.starter.responsiveapptemplate.ui.Root;
import com.vaadin.starter.responsiveapptemplate.ui.components.ListItem;
import com.vaadin.starter.responsiveapptemplate.ui.components.Token;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.responsiveapptemplate.ui.layout.FlexBoxLayout;
import com.vaadin.starter.responsiveapptemplate.ui.layout.FlexDirection;
import com.vaadin.starter.responsiveapptemplate.ui.layout.FlexWrap;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Bottom;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Horizontal;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Right;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Vertical;
import com.vaadin.starter.responsiveapptemplate.ui.utils.BoxShadowBorders;
import com.vaadin.starter.responsiveapptemplate.ui.utils.LumoStyles;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;
import com.vaadin.starter.responsiveapptemplate.ui.views.ViewFrame;

@Route(value = "filter-list", layout = Root.class)
@PageTitle("Filter list")
public class FilterList extends ViewFrame {

    private Div filterArea;
    private FlexBoxLayout filterHeader;
    private Button toggleButton;
    private FlexBoxLayout options;
    private FlexBoxLayout tokens;
    private Grid<Person> grid;

    public FilterList() {
        setViewHeader(new AppBar("Statistics"));
        setViewContent(createContent());
    }

    private Component createContent() {
        FlexLayout content = UIUtils.createColumn(createFilterArea(),
                createGridWrapper());
        content.setHeight("100%");
        return content;
    }

    private Div createFilterArea() {
        filterArea = new Div(createFilterHeader(), createFilters());
        filterArea.addClassName(LumoStyles.Padding.Responsive.Horizontal.L);
        return filterArea;
    }

    private FlexLayout createFilterHeader() {
        toggleButton = UIUtils.createButton(VaadinIcon.CHEVRON_UP_SMALL,
                ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        toggleButton.addClickListener(event -> toggleFilterArea());

        Label title = UIUtils.createH5Label("Filter");
        Span badge = UIUtils.createContrastBadge("4");

        TextField search = new TextField();
        search.setPlaceholder("Quick filter...");
        search.addClassName(LumoStyles.Margin.Left.AUTO);

        filterHeader = new FlexBoxLayout(toggleButton, title, badge, search);
        filterHeader.addClassName(BoxShadowBorders.BOTTOM);
        filterHeader.setAlignItems(FlexComponent.Alignment.CENTER);
        filterHeader.setPadding(Vertical.RESPONSIVE_M);
        filterHeader.setSpacing(Right.S);
        return filterHeader;
    }

    private FlexBoxLayout createFilters() {
        FlexBoxLayout filters = new FlexBoxLayout(createFilterOptions(),
                createTokens());
        filters.setFlex("1", options, tokens);
        filters.setFlexWrap(FlexWrap.WRAP);
        filters.setSpacing(Right.L);
        return filters;
    }

    private FlexLayout createFilterOptions() {
        Label title = UIUtils.createH6Label("Options");

        ComboBox combo = new ComboBox();
        combo.setPlaceholder("Select...");

        Checkbox checkbox = new Checkbox("Checkbox label");

        RadioButtonGroup<String> optionGroup = new RadioButtonGroup<>();
        optionGroup.setItems("Option 1", "Option 2", "Option 3");

        options = new FlexBoxLayout(title, combo, checkbox, optionGroup);
        options.setFlexDirection(FlexDirection.COLUMN);
        options.setPadding(Vertical.M);
        options.setSpacing(Bottom.S);
        return options;
    }

    private FlexLayout createTokens() {
        Label title = UIUtils.createH6Label("Tokens");

        ComboBox combo = new ComboBox();
        combo.setClassName("token-select");
        combo.setPlaceholder("Add filter...");

        Token token1 = new Token("Filter token 1");
        Token token2 = new Token("Filter token 2");

        Div tokenArea = new Div(token1, token2);
        tokenArea.addClassName(LumoStyles.Spacing.Right.S);

        tokens = new FlexBoxLayout(title, combo, tokenArea);
        tokens.setFlexDirection(FlexDirection.COLUMN);
        tokens.setPadding(Vertical.M);
        tokens.setSpacing(Bottom.S);
        return tokens;
    }

    private FlexBoxLayout createGridWrapper() {
        FlexBoxLayout gridWrapper = new FlexBoxLayout(createGrid());
        gridWrapper.setFlexDirection(FlexDirection.COLUMN);
        gridWrapper.setHeight("100%");
        gridWrapper.setMargin(Horizontal.RESPONSIVE_X);
        return gridWrapper;
    }

    private Grid createGrid() {
        grid = new Grid<>();
        grid.setDataProvider(DataProvider.ofCollection(DummyData.getPersons()));

        grid.addColumn(Person::getId).setHeader("ID").setFrozen(true)
                .setSortable(true).setWidth(UIUtils.COLUMN_WIDTH_XS)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createUserInfo))
                .setHeader("Name").setWidth(UIUtils.COLUMN_WIDTH_XL)
                .setFlexGrow(1);
        grid.addColumn(new ComponentRenderer<>(this::createTwitter))
                .setHeader("Twitter").setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.addColumn(new ComponentRenderer<>(this::createForumPosts))
                .setHeader("Forum Posts").setWidth(UIUtils.COLUMN_WIDTH_M)
                .setFlexGrow(0);
        grid.addColumn(new LocalDateRenderer<>(Person::getLastModified,
                "MMM dd, YYYY")).setHeader("Last Modified").setSortable(true)
                .setWidth(UIUtils.COLUMN_WIDTH_M).setFlexGrow(0);

        return grid;
    }

    private Component createUserInfo(Person person) {
        ListItem item = new ListItem(
                UIUtils.createInitials(person.getInitials()), person.getName(),
                person.getEmail());
        item.setHorizontalPadding(false);
        return item;
    }

    private Component createTwitter(Person person) {
        Icon icon;
        if (person.getRandomBoolean()) {
            icon = UIUtils.createPrimaryIcon(VaadinIcon.TWITTER);
        } else {
            icon = UIUtils.createDisabledIcon(VaadinIcon.TWITTER);
        }
        return icon;
    }

    private Component createForumPosts(Person person) {
        Span badge;
        if (person.getRandomInteger() > 5) {
            badge = UIUtils.createSuccessBadge(
                    UIUtils.formatAmount(person.getRandomInteger()));
        } else {
            badge = UIUtils.createErrorBadge(
                    UIUtils.formatAmount(person.getRandomInteger()));
        }
        return badge;
    }

    private void toggleFilterArea() {
        options.setVisible(!options.isVisible());
        tokens.setVisible(!tokens.isVisible());
        toggleButton.setIcon(
                options.isVisible() ? new Icon(VaadinIcon.CHEVRON_UP_SMALL)
                        : new Icon(VaadinIcon.CHEVRON_DOWN_SMALL));

        if (options.isVisible()) {
            filterHeader.addClassNames(BoxShadowBorders.BOTTOM);
        } else {
            filterHeader.removeClassNames(BoxShadowBorders.BOTTOM);
        }
    }

}
