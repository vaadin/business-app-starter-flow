package com.vaadin.starter.responsiveapptemplate.ui.utils;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.starter.responsiveapptemplate.backend.*;
import com.vaadin.starter.responsiveapptemplate.ui.components.DataSeriesItemWithRadius;

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

	// Styles

	public static Button createPrimaryButton(String text) {
		return createButton(ButtonStyle.PRIMARY, text);
	}

	public static Button createPrimaryButton(VaadinIcon icon) {
		return createButton(ButtonStyle.PRIMARY, icon);
	}

	public static Button createTertiaryButton(String text) {
		return createButton(ButtonStyle.TERTIARY, text);
	}

	public static Button createTertiaryButton(VaadinIcon icon) {
		return createButton(ButtonStyle.TERTIARY, icon);
	}

	public static Button createTertiaryInlineButton(String text) {
		return createButton(ButtonStyle.TERTIARY_INLINE, text);
	}

	public static Button createTertiaryInlineButton(VaadinIcon icon) {
		return createButton(ButtonStyle.TERTIARY_INLINE, icon);
	}

	public static Button createSuccessButton(String text) {
		return createButton(ButtonStyle.SUCCESS, text);
	}

	public static Button createSuccessButton(VaadinIcon icon) {
		return createButton(ButtonStyle.SUCCESS, icon);
	}

	public static Button createSuccessPrimaryButton(String text) {
		return createButton(ButtonStyle.SUCCESS_PRIMARY, text);
	}

	public static Button createSuccessPrimaryButton(VaadinIcon icon) {
		return createButton(ButtonStyle.SUCCESS_PRIMARY, icon);
	}

	public static Button createErrorButton(String text) {
		return createButton(ButtonStyle.ERROR, text);
	}

	public static Button createErrorButton(VaadinIcon icon) {
		return createButton(ButtonStyle.ERROR, icon);
	}

	public static Button createErrorPrimaryButton(String text) {
		return createButton(ButtonStyle.ERROR_PRIMARY, text);
	}

	public static Button createErrorPrimaryButton(VaadinIcon icon) {
		return createButton(ButtonStyle.ERROR_PRIMARY, icon);
	}

	public static Button createContrastButton(String text) {
		return createButton(ButtonStyle.CONTRAST, text);
	}

	public static Button createContrastButton(VaadinIcon icon) {
		return createButton(ButtonStyle.CONTRAST, icon);
	}

	public static Button createContrastPrimaryButton(String text) {
		return createButton(ButtonStyle.CONTRAST_PRIMARY, text);
	}

	public static Button createContrastPrimaryButton(VaadinIcon icon) {
		return createButton(ButtonStyle.CONTRAST_PRIMARY, icon);
	}

	// Size

	public static Button createSmallButton(String text) {
		return createButton(ButtonSize.SMALL, text);
	}

	public static Button createSmallButton(VaadinIcon icon) {
		return createButton(ButtonSize.SMALL, icon);
	}

	public static Button createLargeButton(String text) {
		return createButton(ButtonSize.LARGE, text);
	}

	public static Button createLargeButton(VaadinIcon icon) {
		return createButton(ButtonSize.LARGE, icon);
	}

	// Text

	public static Button createButton(ButtonSize size, ButtonStyle style, String text) {
		Button button = new Button(text);
		button.getElement().setAttribute(LumoStyles.THEME, String.join(" ", size.getStyle(), style.getStyle()));
		return button;
	}

	public static Button createButton(ButtonSize size, String text) {
		Button button = new Button(text);
		button.getElement().setAttribute(LumoStyles.THEME, String.join(" ", size.getStyle()));
		return button;
	}

	public static Button createButton(ButtonStyle style, String text) {
		Button button = new Button(text);
		button.getElement().setAttribute(LumoStyles.THEME, String.join(" ", style.getStyle()));
		return button;
	}

	// Icon

	public static Button createButton(ButtonSize size, ButtonStyle style, VaadinIcon icon) {
		Button button = new Button(new Icon(icon));
		button.getElement().setAttribute(LumoStyles.THEME, String.join(" ", size.getStyle(), style.getStyle(), ButtonStyle.ICON.getStyle()));
		return button;
	}

	public static Button createButton(ButtonSize size, VaadinIcon icon) {
		Button button = new Button(new Icon(icon));
		button.getElement().setAttribute(LumoStyles.THEME, String.join(" ", size.getStyle(), ButtonStyle.ICON.getStyle()));
		return button;
	}

	public static Button createButton(ButtonStyle style, VaadinIcon icon) {
		Button button = new Button(new Icon(icon));
		button.getElement().setAttribute(LumoStyles.THEME, String.join(" ", style.getStyle(), ButtonStyle.ICON.getStyle()));
		return button;
	}

	// Text and icon

	public static Button createButton(ButtonSize size, ButtonStyle style, String text, VaadinIcon icon) {
		Button button = new Button(text, new Icon(icon));
		button.getElement().setAttribute(LumoStyles.THEME, String.join(" ", size.getStyle(), style.getStyle(), ButtonStyle.ICON.getStyle()));
		return button;
	}

	public static Button createButton(ButtonSize size, String text, VaadinIcon icon) {
		Button button = new Button(text, new Icon(icon));
		button.getElement().setAttribute(LumoStyles.THEME, String.join(" ", size.getStyle(), ButtonStyle.ICON.getStyle()));
		return button;
	}

	public static Button createButton(ButtonStyle style, String text, VaadinIcon icon) {
		Button button = new Button(text, new Icon(icon));
		button.getElement().setAttribute(LumoStyles.THEME, String.join(" ", style.getStyle(), ButtonStyle.ICON.getStyle()));
		return button;
	}



	/* ==== TEXTFIELDS ==== */

	public static TextField createSmallTextField() {
		TextField textField = new TextField();
		textField.getElement().setAttribute(LumoStyles.THEME, LumoStyles.TextField.SMALL);
		return textField;
	}


	/* ==== LABELS ==== */
	public static Label createLabel(FontSize size, TextColor color, String text) {
		Label label = new Label(text);
		label.addClassNames(size.getStyle(), color.getStyle());
		return label;
	}

	public static Label createXXSmallLabel(String text) {
		Label label = new Label(text);
		label.addClassName(FontSize.XXS.getStyle());
		return label;
	}

	public static Label createXSmallLabel(String text) {
		Label label = new Label(text);
		label.addClassName(FontSize.XS.getStyle());
		return label;
	}

	public static Label createSmallLabel(String text) {
		Label label = new Label(text);
		label.addClassName(FontSize.S.getStyle());
		return label;
	}

	public static Label createLargeLabel(String text) {
		Label label = new Label(text);
		label.addClassName(FontSize.L.getStyle());
		return label;
	}

	public static Label createXLargeLabel(String text) {
		Label label = new Label(text);
		label.addClassName(FontSize.XL.getStyle());
		return label;
	}

	public static Label createXXLargeLabel(String text) {
		Label label = new Label(text);
		label.addClassName(FontSize.XXL.getStyle());
		return label;
	}

	public static Label createXXXLargeLabel(String text) {
		Label label = new Label(text);
		label.addClassName(FontSize.XXXL.getStyle());
		return label;
	}

	public static Label createH2Label(String text) {
		Label label = new Label(text);
		label.addClassName(LumoStyles.Header.H2);
		return label;
	}

	public static Label createH3Label(String text) {
		Label label = new Label(text);
		label.addClassName(LumoStyles.Header.H3);
		return label;
	}

	public static Label createH4Label(String text) {
		Label label = new Label(text);
		label.addClassName(LumoStyles.Header.H4);
		return label;
	}

	public static Label createH5Label(String text) {
		Label label = new Label(text);
		label.addClassName(LumoStyles.Header.H5);
		return label;
	}

	public static Label createH6Label(String text) {
		Label label = new Label(text);
		label.addClassName(LumoStyles.Header.H6);
		return label;
	}



	/* === MISC === */

	public static Component createInitials(String initials) {
		FlexLayout layout = UIUtils.createFlexLayout(Collections.singleton("initials"), new Text(initials));

		layout.setAlignItems(FlexComponent.Alignment.CENTER);
		layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

		layout.getElement().setAttribute(LumoStyles.THEME, LumoStyles.DARK);
		layout.addClassName(FontSize.S.getStyle());
		layout.setHeight(LumoStyles.Size.M);
		layout.setWidth(LumoStyles.Size.M);

		return layout;
	}



	/* === FORMLAYOUT === */

	public static void setFormLayoutColSpan(int span, Component... components) {
		for (Component component : components) {
			component.getElement().setAttribute("colspan", Integer.toString(span));
		}
	}

	public static FlexLayout createPhoneLayout() {
		TextField prefix = new TextField();
		prefix.setValue("+358");
		prefix.setWidth("80px");

		TextField number = new TextField();
		number.setValue(DummyData.getPhoneNumber());

		FlexLayout layout = UIUtils.createFlexLayout(Collections.singleton(LumoStyles.Spacing.Right.S), prefix, number);
		layout.setFlexGrow(1, number);
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

	public static Span createBadge(String text) {
		Span badge = new Span(text);
		badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.DEFAULT);
		return badge;
	}

	public static Span createSuccessBadge(String text) {
		Span badge = new Span(text);
		badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.SUCCESS);
		return badge;
	}

	public static Span createContrastBadge(String text) {
		Span badge = new Span(text);
		badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.CONTRAST);
		return badge;
	}

	public static Span createErrorBadge(String text) {
		Span badge = new Span(text);
		badge.getElement().setAttribute(LumoStyles.THEME, LumoStyles.Badge.ERROR);
		return badge;
	}

	// Transaction badge

	public static Component createBadge(Transaction transaction) {
		return createBadge(transaction.getStatus());
	}

	public static Component createBadge(Transaction.Status status) {
		Span badge;
		switch (status) {
			case PENDING:
				badge = createContrastBadge(status.getName());
				break;
			default:
				badge = createBadge(status.getName());
				break;
		}
		return badge;
	}

	// Invoice badge

	public static Component createBadge(Invoice invoice) {
		return createBadge(invoice.getStatus());
	}

	public static Component createBadge(Invoice.Status status) {
		Span badge;
		switch (status) {
			case OUTSTANDING:
				badge = createContrastBadge(status.getName());
				break;
			case OVERDUE:
				badge = createErrorBadge(status.getName());
				break;
			case PAID:
				badge = createSuccessBadge(status.getName());
				break;
			default:
				badge = createBadge(status.getName());
				break;
		}
		return badge;
	}

	// Order badge

	public static Component createBadge(Order order) {
		return createBadge(order.getStatus());
	}

	public static Component createBadge(Order.Status status) {
		Span badge;
		switch (status) {
			case PENDING:
				badge = createContrastBadge(status.getName());
				break;
			case SENT:
				badge = createSuccessBadge(status.getName());
				break;
			case FAILED:
				badge = createErrorBadge(status.getName());
				break;
			default:
				badge = createBadge(status.getName());
				break;
		}
		return badge;
	}

	// Payment badge

	public static Component createBadge(Payment payment) {
		Payment.Status status = payment.getStatus();
		Span badge = new Span(status.getName());
		badge.getElement().setAttribute(LumoStyles.THEME, status.getTheme());
		badge.getElement().setProperty(CSSProperties.Title.PROPERTY, status.getDesc());
		return badge;
	}

	// Item badge

	public static Component createBadge(Item item) {
		return createBadge(item.getCategory());
	}

	public static Component createBadge(Item.Category category) {
		Span badge;
		switch (category) {
			case HEALTHCARE:
				badge = createSuccessBadge(category.getName());
				break;
			case CONSTRUCTION:
				badge = createContrastBadge(category.getName());
				break;
			default:
				badge = createBadge(category.getName());
				break;
		}
		return badge;
	}



	/* === ICONS === */

	public static Icon createPrimaryIcon(VaadinIcon icon) {
		Icon i = new Icon(icon);
		i.addClassName(TextColor.PRIMARY.getStyle());
		return i;
	}

	public static Icon createSecondaryIcon(VaadinIcon icon) {
		Icon i = new Icon(icon);
		i.addClassName(TextColor.SECONDARY.getStyle());
		return i;
	}

	public static Icon createTertiaryIcon(VaadinIcon icon) {
		Icon i = new Icon(icon);
		i.addClassName(TextColor.TERTIARY.getStyle());
		return i;
	}

	public static Icon createDisabledIcon(VaadinIcon icon) {
		Icon i = new Icon(icon);
		i.addClassName(TextColor.DISABLED.getStyle());
		return i;
	}

	public static Icon createSuccessIcon(VaadinIcon icon) {
		Icon i = new Icon(icon);
		i.addClassName(TextColor.SUCCESS.getStyle());
		return i;
	}

	public static Icon createErrorIcon(VaadinIcon icon) {
		Icon i = new Icon(icon);
		i.addClassName(TextColor.ERROR.getStyle());
		return i;
	}

	public static Icon createSmallIcon(VaadinIcon icon) {
		Icon i = new Icon(icon);
		i.addClassName(IconSize.SMALL.getStyle());
		return i;
	}

	public static Icon createLargeIcon(VaadinIcon icon) {
		Icon i = new Icon(icon);
		i.addClassName(IconSize.LARGE.getStyle());
		return i;
	}

	// Combinations

	public static Icon createIcon(IconSize size, TextColor color, VaadinIcon icon) {
		Icon i = new Icon(icon);
		i.addClassNames(size.getStyle(), color.getStyle());
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

	public static Component createSalesChart(String title, String yAxis) {
		Chart chart = new Chart(ChartType.AREASPLINE);

		Configuration conf = chart.getConfiguration();
		conf.setTitle(title);
		conf.getLegend().setEnabled(false);

		XAxis xAxis = new XAxis();
		xAxis.setCategories("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
		conf.addxAxis(xAxis);

		conf.getyAxis().setTitle(yAxis);

		conf.addSeries(new ListSeries(220, 240, 400, 360, 420, 640, 580, 800, 600, 580, 740, 800));

		FlexLayout card = UIUtils.createWrappingFlexLayout(Arrays.asList(LumoStyles.BorderRadius.S, LumoStyles.Padding.All.M, LumoStyles.Shadow.S), chart);
		card.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.BASE_COLOR);
		card.getStyle().set(CSSProperties.BoxSizing.PROPERTY, CSSProperties.BoxSizing.BORDER_BOX);
		card.setHeight("400px");
		return card;
	}



	/* === DETAILS DRAWER === */

	public static Label createDetailsDrawerHeader(String title, boolean border, boolean horizontalPadding) {
		Label header = UIUtils.createH3Label(title);
		header.addClassName(LumoStyles.Padding.Top.L);

		// If we have a border, we set a slightly larger bottom padding.
		if (border) {
			header.addClassNames(BoxShadowBorders.BOTTOM, LumoStyles.Padding.Bottom.L);
		} else {
			header.addClassName(LumoStyles.Padding.Bottom.M);
		}

		if (horizontalPadding) {
			header.addClassName(LumoStyles.Padding.Horizontal.L);
		}

		header.getStyle().set(CSSProperties.BoxSizing.PROPERTY, CSSProperties.BoxSizing.BORDER_BOX);
		header.setWidth("100%");
		return header;
	}



	/* === NOTIFICATIONS === */

	public static void showNotification(String text) {
		Notification.show(text, 3000, Notification.Position.BOTTOM_CENTER);
	}

}