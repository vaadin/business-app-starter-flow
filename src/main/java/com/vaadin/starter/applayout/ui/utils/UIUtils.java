package com.vaadin.starter.applayout.ui.utils;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.starter.applayout.backend.*;
import com.vaadin.starter.applayout.ui.components.DataSeriesItemWithRadius;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class UIUtils {

    public static final String IMG_PATH = "frontend/styles/images/";

    public static final String COLUMN_WIDTH_XS = "80px";
    public static final String COLUMN_WIDTH_S = "120px";
    public static final String COLUMN_WIDTH_M = "160px";
    public static final String COLUMN_WIDTH_L = "200px";
    public static final String COLUMN_WIDTH_XL = "240px";

    /**
     * Thread-unsafe formatters.
     */
    private static final ThreadLocal<DecimalFormat> decimalFormat = ThreadLocal.withInitial(() -> new DecimalFormat("###,###,###.00"));
    private static final ThreadLocal<DateTimeFormatter> dateFormat = ThreadLocal.withInitial(() -> DateTimeFormatter.ofPattern("MMM dd, YYYY"));



    /* ==== LAYOUTS ==== */

    public static FormLayout createFormLayout(Collection<String> classNames, Component... components) {
        FormLayout form = new FormLayout(components);
        classNames.forEach(form::addClassName);
        return form;
    }

    public static Div createDiv(Collection<String> classNames, Component... components) {
        Div div = new Div(components);
        classNames.forEach(div::addClassName);
        return div;
    }

    public static Div createRightAlignedDiv(Component... components) {
        Div div = new Div(components);
        div.getStyle().set(CSSProperties.TextAlign.PROPERTY, CSSProperties.TextAlign.RIGHT);
        return div;
    }

    public static Div createRightAlignedDiv(String text) {
        Div div = new Div(new Text(text));
        div.getStyle().set(CSSProperties.TextAlign.PROPERTY, CSSProperties.TextAlign.RIGHT);
        return div;
    }

    public static FlexLayout createWrappingFlexLayout(Component... components) {
        FlexLayout layout = new FlexLayout(components);
        layout.getStyle().set(CSSProperties.FlexWrap.PROPERTY, CSSProperties.FlexWrap.WRAP);
        return layout;
    }

    public static FlexLayout createWrappingFlexLayout(Collection<String> classNames, Component... components) {
        FlexLayout layout = createWrappingFlexLayout(components);
        classNames.forEach(layout::addClassName);
        return layout;
    }

    public static FlexLayout createFlexLayout(Collection<String> classNames, Component... components) {
        FlexLayout layout = new FlexLayout(components);
        classNames.forEach(layout::addClassName);
        return layout;
    }

    public static FlexLayout createColumn(Component... components) {
        FlexLayout layout = new FlexLayout(components);
        layout.getStyle().set(CSSProperties.FlexDirection.PROPERTY, CSSProperties.FlexDirection.COLUMN);
        return layout;
    }

    public static FlexLayout createColumn(Collection<String> classNames, Component... components) {
        FlexLayout layout = createColumn(components);
        classNames.forEach(layout::addClassName);
        return layout;
    }



    /* ==== BUTTONS ==== */

    /* Primary */
    public static Button createPrimaryButton(String text) {
        Button button = new Button(text);
        button.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.PRIMARY);
        return button;
    }

    /* Tertiary */
    public static Button createTertiaryButton(String text) {
        Button button = new Button(text);
        button.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.TERTIARY);
        return button;
    }

    public static Button createTertiaryIconButton(VaadinIcon icon) {
        Button button = new Button(new Icon(icon));
        button.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.TERTIARY);
        return button;
    }

    public static Button createSmallTertiaryIconButton(VaadinIcon icon) {
        return createSmallTertiaryIconButton(new Icon(icon));
    }

    public static Button createSmallTertiaryIconButton(Collection<String> classNames, VaadinIcon icon) {
        Button button = createSmallTertiaryIconButton(icon);
        classNames.forEach(button::addClassName);
        return button;
    }

    public static Button createSmallTertiaryIconButton(Icon icon) {
        Button button = new Button(icon);
        button.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.TERTIARY_ICON_SMALL);
        return button;
    }

    /* Secondary (normal) */
    public static Button createSmallButton(String text) {
        Button button = new Button(text);
        button.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.SMALL);
        return button;
    }

    public static Button createSmallButton(VaadinIcon icon, String text) {
        Button button = createSmallButton(text);
        button.setIcon(new Icon(icon));
        return button;
    }

    public static Button createSmallButton(Collection<String> classNames, VaadinIcon icon, String text) {
        Button button = createSmallButton(icon, text);
        classNames.forEach(button::addClassName);
        return button;
    }

    public static Button createSmallIconButton(VaadinIcon icon) {
        return createSmallIconButton(new Icon(icon));
    }

    public static Button createSmallIconButton(Icon icon) {
        Button button = new Button(icon);
        button.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.SMALL_ICON);
        return button;
    }

    /* Error */
    public static Button createErrorButton(String text) {
        Button button = new Button(text);
        button.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.ERROR);
        return button;
    }

    public static Button createErrorPrimaryButton(String text) {
        Button button = new Button(text);
        button.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Button.ERROR_PRIMARY);
        return button;
    }


    /* ==== TEXTFIELDS ==== */

    public static TextField createSmallTextField() {
        TextField textField = new TextField();
        textField.getElement().setAttribute(LumoStyles.THEME, LumoStyles.TextField.SMALL);
        return textField;
    }



    /* ==== TEXT ==== */

    public static Label createLabel(Collection<String> classNames, String text) {
        Label label = new Label(text);
        classNames.forEach(label::addClassName);
        return label;
    }

    public static Label createSmallLabel(String text) {
        Label label = new Label(text);
        label.getElement().setAttribute(LumoStyles.THEME, LumoStyles.FontSize.S);
        return label;
    }

    public static Label createSmallLabel(Collection<String> classNames, String text) {
        Label label = createSmallLabel(text);
        classNames.forEach(label::addClassName);
        return label;
    }

    public static Label createSmallSecondaryLabel(String text) {
        Label label = createSmallLabel(text);
        label.addClassNames(LumoStyles.TextColor.SECONDARY);
        return label;
    }

    public static Label createH2Label(String text) {
        Label label = new Label(text);
        label.addClassNames(LumoStyles.FontSize.H2);
        return label;
    }

    public static H3 createH3(Collection<String> classNames, String text) {
        H3 header = new H3(text);
        classNames.forEach(header::addClassName);
        return header;
    }

    public static Label createH3Label(String text) {
        Label label = new Label(text);
        label.addClassNames(LumoStyles.FontSize.H3);
        return label;
    }

    public static Label createH3Label(Collection<String> classNames, String text) {
        Label label = createH3Label(text);
        classNames.forEach(label::addClassName);
        return label;
    }

    public static Label createH4Label(String text) {
        Label label = new Label(text);
        label.addClassNames(LumoStyles.FontSize.H4);
        return label;
    }

    public static Label createH5Label(String text) {
        Label label = new Label(text);
        label.addClassNames(LumoStyles.FontSize.H5);
        return label;
    }

    public static Label createH6Label(String text) {
        Label label = new Label(text);
        label.addClassNames(LumoStyles.FontSize.H4);
        return label;
    }

    public static Label createH6Label(Collection<String> classNames, String text) {
        Label label = createH6Label(text);
        classNames.forEach(label::addClassName);
        return label;
    }


    /* ==== ICONS ==== */

    public static Icon createSmallIcon(VaadinIcon icon) {
        Icon i = new Icon(icon);
        i.addClassName(LumoStyles.IconSize.S);
        return i;
    }

    public static Icon createSmallIcon(Collection<String> classNames, VaadinIcon icon) {
        Icon i = createSmallIcon(icon);
        classNames.forEach(i::addClassName);
        return i;
    }

    public static Icon createLargeIcon(VaadinIcon icon) {
        Icon i = new Icon(icon);
        i.addClassName(LumoStyles.IconSize.L);
        return i;
    }



    /* === MISC === */

    public static Component createInitials(String initials) {
        FlexLayout layout = UIUtils.createFlexLayout(Collections.singleton("initials"), new Text(initials));

        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        layout.getElement().setAttribute(LumoStyles.THEME, String.join(" ", LumoStyles.DARK, LumoStyles.FontSize.S));
        layout.setHeight(LumoStyles.Size.M);
        layout.setWidth(LumoStyles.Size.M);

        return layout;
    }



    /* === NUMBERS === */

    public static String formatAmount(Double amount) {
        return decimalFormat.get().format(amount);
    }

    public static String formatAmount(int amount) {
        return decimalFormat.get().format(amount);
    }

    public static String formatUnits(int units) {
        return NumberFormat.getIntegerInstance().format(units);
    }


    /* === BADGES === */
    public static Component createBadge(Transaction transaction) {
        return createBadge(transaction.getStatus());
    }

    public static Component createBadge(Transaction.Status status) {
        Span badge = new Span(status.getName());
        if (status.equals(Transaction.Status.PROCESSED)) {
            badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.DEFAULT);
        } else {
            badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.CONTRAST);
        }
        return badge;
    }

    public static Component createBadge(Invoice invoice) {
        return createBadge(invoice.getStatus());
    }

    public static Component createBadge(Invoice.Status status) {
        Span badge = new Span(status.getName());
        switch (status) {
            case OUTSTANDING:
                badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.CONTRAST);
                break;
            case OPEN:
                badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.DEFAULT);
                break;
            case PAID:
                badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.SUCCESS);
                break;
            default:
                badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.ERROR);
                break;
        }
        return badge;
    }

    public static Component createBadge(Order order) {
        Order.Status status = order.getStatus();
        Span badge = new Span(status.getName());
        switch (status) {
            case PENDING:
                badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.CONTRAST);
                break;
            case OPEN:
                badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.DEFAULT);
                break;
            case SENT:
                badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.SUCCESS);
                break;
            default:
                badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.ERROR);
                break;
        }
        return badge;
    }

    public static Component createBadge(Payment payment) {
        Payment.Status status = payment.getStatus();
        Span badge = new Span(status.getName());
        switch (status) {
            case PENDING:
                badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.CONTRAST);
                break;
            case OPEN:
                badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.DEFAULT);
                break;
            case SENT:
                badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.SUCCESS);
                break;
            default:
                badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.ERROR);
                break;
        }
        return badge;
    }

    public static Component createBadge(Item item) {
        return createBadge(item.getCategory());
    }

    public static Component createBadge(Item.Category category) {
        Span badge = new Span(category.getName());
        switch (category) {
            case HEALTHCARE:
                badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.SUCCESS);
                break;
            case DENTAL:
                badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.DEFAULT);
                break;
            default:
                badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.CONTRAST);
                break;
        }
        return badge;
    }



    /* === ICONS === */

    public static Icon createPrimaryIcon(VaadinIcon icon) {
        Icon i = new Icon(icon);
        i.addClassName(LumoStyles.TextColor.PRIMARY);
        return i;
    }

    public static Icon createSecondaryIcon(VaadinIcon icon) {
        Icon i = new Icon(icon);
        i.addClassName(LumoStyles.TextColor.SECONDARY);
        return i;
    }

    public static Icon createTertiaryIcon(VaadinIcon icon) {
        Icon i = new Icon(icon);
        i.addClassName(LumoStyles.TextColor.TERTIARY);
        return i;
    }

    public static Icon createSuccessIcon(VaadinIcon icon) {
        Icon i = new Icon(icon);
        i.addClassName(LumoStyles.TextColor.SUCCESS);
        return i;
    }

    public static Icon createErrorIcon(VaadinIcon icon) {
        Icon i = new Icon(icon);
        i.addClassName(LumoStyles.TextColor.ERROR);
        return i;
    }



    /* === DATES === */

    public static String formatDate(LocalDate date) {
        return dateFormat.get().format(date);
    }



    /* === CHARTS === */

    public static Chart createProgressChart(int value) {
        Chart chart = new Chart();
        chart.setSizeFull();

        Configuration configuration = chart.getConfiguration();
        configuration.getChart().setType(ChartType.SOLIDGAUGE);
        configuration.setTitle("");
        configuration.getTooltip().setEnabled(false);

        configuration.getyAxis().setMin(0);
        configuration.getyAxis().setMax(100);
        configuration.getyAxis().getLabels().setEnabled(false);

        PlotOptionsSolidgauge opt = new PlotOptionsSolidgauge();
        opt.getDataLabels().setEnabled(false);
        configuration.setPlotOptions(opt);

        DataSeriesItemWithRadius point = new DataSeriesItemWithRadius();
        point.setY(value);
        point.setInnerRadius("100%");
        point.setRadius("110%");
        configuration.setSeries(new DataSeries(point));

        Pane pane = configuration.getPane();
        pane.setStartAngle(0);
        pane.setEndAngle(360);

        Background background = new Background();
        background.setShape(BackgroundShape.ARC);
        background.setInnerRadius("100%");
        background.setOuterRadius("110%");
        pane.setBackground(background);

        return chart;
    }

    public static Component createSalesChart(String title) {
        Chart chart = new Chart(ChartType.AREASPLINE);

        Configuration conf = chart.getConfiguration();
        conf.setTitle(title);
        conf.getLegend().setEnabled(false);

        XAxis xAxis = new XAxis();
        xAxis.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        conf.addxAxis(xAxis);

        conf.getyAxis().setTitle("");

        conf.addSeries(new ListSeries(220, 240, 400, 360, 420, 640, 580, 800, 600, 580, 740, 800));

        FlexLayout card = UIUtils.createWrappingFlexLayout(Arrays.asList(LumoStyles.BorderRadius.S, LumoStyles.Padding.All.M, LumoStyles.Shadow.S), chart);
        card.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.BASE_COLOR);
        card.getStyle().set(CSSProperties.BoxSizing.PROPERTY, CSSProperties.BoxSizing.BORDER_BOX);
        card.setHeight("400px");
        return card;
    }

}
