package com.vaadin.starter.responsiveapptemplate.ui.utils;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.starter.responsiveapptemplate.backend.*;
import com.vaadin.starter.responsiveapptemplate.ui.components.DataSeriesItemWithRadius;
import com.vaadin.starter.responsiveapptemplate.ui.layout.*;
import com.vaadin.starter.responsiveapptemplate.ui.layout.TextAlign;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Right;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.StringJoiner;

import static com.vaadin.starter.responsiveapptemplate.ui.utils.BadgeShape.PILL;

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

	public static FlexLayout createWrappingFlexLayout(Component... components) {
		FlexBoxLayout layout = new FlexBoxLayout(components);
		layout.setFlexWrap(FlexWrap.WRAP);
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
		FlexBoxLayout layout = new FlexBoxLayout(components);
		layout.setFlexDirection(FlexDirection.COLUMN);
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
		return createButton(text, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createPrimaryButton(VaadinIcon icon) {
		return createButton(icon, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createPrimaryButton(String text, VaadinIcon icon) {
		return createButton(text, icon, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createTertiaryButton(String text) {
		return createButton(text, ButtonVariant.LUMO_TERTIARY);
	}

	public static Button createTertiaryButton(VaadinIcon icon) {
		return createButton(icon, ButtonVariant.LUMO_TERTIARY);
	}

	public static Button createTertiaryButton(String text, VaadinIcon icon) {
		return createButton(text, icon, ButtonVariant.LUMO_TERTIARY);
	}

	public static Button createTertiaryInlineButton(String text) {
		return createButton(text, ButtonVariant.LUMO_TERTIARY_INLINE);
	}

	public static Button createTertiaryInlineButton(VaadinIcon icon) {
		return createButton(icon, ButtonVariant.LUMO_TERTIARY_INLINE);
	}

	public static Button createTertiaryInlineButton(String text, VaadinIcon icon) {
		return createButton(text, icon, ButtonVariant.LUMO_TERTIARY_INLINE);
	}

	public static Button createSuccessButton(String text) {
		return createButton(text, ButtonVariant.LUMO_SUCCESS);
	}

	public static Button createSuccessButton(VaadinIcon icon) {
		return createButton(icon, ButtonVariant.LUMO_SUCCESS);
	}

	public static Button createSuccessButton(String text, VaadinIcon icon) {
		return createButton(text, icon, ButtonVariant.LUMO_SUCCESS);
	}

	public static Button createSuccessPrimaryButton(String text) {
		return createButton(text, ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createSuccessPrimaryButton(VaadinIcon icon) {
		return createButton(icon, ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createSuccessPrimaryButton(String text, VaadinIcon icon) {
		return createButton(text, icon, ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createErrorButton(String text) {
		return createButton(text, ButtonVariant.LUMO_ERROR);
	}

	public static Button createErrorButton(VaadinIcon icon) {
		return createButton(icon, ButtonVariant.LUMO_ERROR);
	}

	public static Button createErrorButton(String text, VaadinIcon icon) {
		return createButton(text, icon, ButtonVariant.LUMO_ERROR);
	}

	public static Button createErrorPrimaryButton(String text) {
		return createButton(text, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createErrorPrimaryButton(VaadinIcon icon) {
		return createButton(icon, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createErrorPrimaryButton(String text, VaadinIcon icon) {
		return createButton(text, icon, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createContrastButton(String text) {
		return createButton(text, ButtonVariant.LUMO_CONTRAST);
	}

	public static Button createContrastButton(VaadinIcon icon) {
		return createButton(icon, ButtonVariant.LUMO_CONTRAST);
	}

	public static Button createContrastButton(String text, VaadinIcon icon) {
		return createButton(text, icon, ButtonVariant.LUMO_CONTRAST);
	}

	public static Button createContrastPrimaryButton(String text) {
		return createButton(text, ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createContrastPrimaryButton(VaadinIcon icon) {
		return createButton(icon, ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_PRIMARY);
	}

	public static Button createContrastPrimaryButton(String text, VaadinIcon icon) {
		return createButton(text, icon, ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_PRIMARY);
	}

	// Size

	public static Button createSmallButton(String text) {
		return createButton(text, ButtonVariant.LUMO_SMALL);
	}

	public static Button createSmallButton(VaadinIcon icon) {
		return createButton(icon, ButtonVariant.LUMO_SMALL);
	}

	public static Button createSmallButton(String text, VaadinIcon icon) {
		return createButton(text, icon, ButtonVariant.LUMO_SMALL);
	}

	public static Button createLargeButton(String text) {
		return createButton(text, ButtonVariant.LUMO_LARGE);
	}

	public static Button createLargeButton(VaadinIcon icon) {
		return createButton(icon, ButtonVariant.LUMO_LARGE);
	}

	public static Button createLargeButton(String text, VaadinIcon icon) {
		return createButton(text, icon, ButtonVariant.LUMO_LARGE);
	}

	// Text

	public static Button createButton(String text, ButtonVariant... variants) {
		Button button = new Button(text);
		button.addThemeVariants(variants);
		return button;
	}

	// Icon

	public static Button createButton(VaadinIcon icon, ButtonVariant... variants) {
		Button button = new Button(new Icon(icon));
		button.addThemeVariants(variants);
		return button;
	}

	// Text and icon

	public static Button createButton(String text, VaadinIcon icon, ButtonVariant... variants) {
		Button button = new Button(text, new Icon(icon));
		button.addThemeVariants(variants);
		return button;
	}



	/* ==== TEXTFIELDS ==== */

	public static TextField createSmallTextField() {
		TextField textField = new TextField();
		textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		return textField;
	}



	/* ==== LABELS ==== */

	public static Label createLabel(FontSize size, TextColor color, String text) {
		Label label = new Label(text);
		label.addClassNames(size.getClassName(), color.getClassName());
		return label;
	}

	public static Label createLabel(FontSize size, String text) {
		return createLabel(size, TextColor.BODY, text);
	}

	public static Label createLabel(TextColor color, String text) {
		return createLabel(FontSize.M, color, text);
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
		FlexBoxLayout layout = new FlexBoxLayout(new Text(initials));
		layout.addClassNames("initials", FontSize.S.getClassName());

		layout.setAlignItems(FlexComponent.Alignment.CENTER);
		layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
		layout.setTheme(Theme.DARK);

		layout.setHeight(LumoStyles.Size.M);
		layout.setWidth(LumoStyles.Size.M);
		return layout;
	}

	public static String formatAddress(Address address) {
		return address.getStreet() + "\n" + address.getCity() + ", " + address.getCity() + " " + address.getZip();
	}

	public static Label createAmountLabel(double amount) {
		Label label = createH5Label(formatAmount(amount));
		label.addClassName(LumoStyles.FontFamily.MONOSPACE);
		return label;
	}

	public static Label createUnitsLabel(int units) {
		Label label = new Label(formatUnits(units));
		label.addClassName(LumoStyles.FontFamily.MONOSPACE);
		return label;
	}

	public static Button createFloatingActionButton(VaadinIcon icon) {
		Button button = createPrimaryButton(icon);
		button.addThemeName("fab");
		return button;
	}



	/* === FORMLAYOUT === */

	public static FlexLayout createPhoneLayout() {
		TextField prefix = new TextField();
		prefix.setValue("+358");
		prefix.setWidth("80px");

		TextField number = new TextField();
		number.setValue(DummyData.getPhoneNumber());

		FlexBoxLayout layout = new FlexBoxLayout(prefix, number);
		layout.setFlexGrow(1, number);
		layout.setSpacing(Right.S);
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
		setTheme(BadgeColor.NORMAL.getThemeName(), badge);
		return badge;
	}

	public static Span createPrimaryBadge(String text) {
		Span badge = new Span(text);
		setTheme(BadgeColor.NORMAL_PRIMARY.getThemeName(), badge);
		return badge;
	}

	public static Span createSuccessBadge(String text) {
		Span badge = new Span(text);
		setTheme(BadgeColor.SUCCESS.getThemeName(), badge);
		return badge;
	}

	public static Span createSuccessPrimaryBadge(String text) {
		Span badge = new Span(text);
		setTheme(BadgeColor.SUCCESS_PRIMARY.getThemeName(), badge);
		return badge;
	}

	public static Span createContrastBadge(String text) {
		Span badge = new Span(text);
		setTheme(BadgeColor.CONTRAST.getThemeName(), badge);
		return badge;
	}

	public static Span createContrastPrimaryBadge(String text) {
		Span badge = new Span(text);
		setTheme(BadgeColor.CONTRAST_PRIMARY.getThemeName(), badge);
		return badge;
	}

	public static Span createErrorBadge(String text) {
		Span badge = new Span(text);
		setTheme(BadgeColor.ERROR.getThemeName(), badge);
		return badge;
	}

	public static Span createErrorPrimaryBadge(String text) {
		Span badge = new Span(text);
		setTheme(BadgeColor.ERROR_PRIMARY.getThemeName(), badge);
		return badge;
	}

	public static Span createBadge(BadgeSize size, BadgeShape shape, BadgeColor color, String text) {
		StringJoiner joiner = new StringJoiner(" ");
		joiner.add(color.getThemeName());

		if (shape.equals(PILL)) {
			joiner.add(shape.getThemeName());
		}

		if (size.equals(BadgeSize.S)) {
			joiner.add(size.getThemeName());
		}

		Span badge = new Span(text);
		setTheme(joiner.toString(), badge);
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
		Span badge = new Span(status.getName());
		setTheme(status.getTheme(), badge);
		setTooltip(status.getDesc(), badge);
		return badge;
	}

	// Payment badge

	public static Component createBadge(Payment payment) {
		Payment.Status status = payment.getStatus();
		Span badge = new Span(status.getName());
		setTheme(status.getTheme(), badge);
		setTooltip(status.getDesc(), badge);
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
		i.addClassName(TextColor.PRIMARY.getClassName());
		return i;
	}

	public static Icon createSecondaryIcon(VaadinIcon icon) {
		Icon i = new Icon(icon);
		i.addClassName(TextColor.SECONDARY.getClassName());
		return i;
	}

	public static Icon createTertiaryIcon(VaadinIcon icon) {
		Icon i = new Icon(icon);
		i.addClassName(TextColor.TERTIARY.getClassName());
		return i;
	}

	public static Icon createDisabledIcon(VaadinIcon icon) {
		Icon i = new Icon(icon);
		i.addClassName(TextColor.DISABLED.getClassName());
		return i;
	}

	public static Icon createSuccessIcon(VaadinIcon icon) {
		Icon i = new Icon(icon);
		i.addClassName(TextColor.SUCCESS.getClassName());
		return i;
	}

	public static Icon createErrorIcon(VaadinIcon icon) {
		Icon i = new Icon(icon);
		i.addClassName(TextColor.ERROR.getClassName());
		return i;
	}

	public static Icon createSmallIcon(VaadinIcon icon) {
		Icon i = new Icon(icon);
		i.addClassName(IconSize.S.getClassName());
		return i;
	}

	public static Icon createLargeIcon(VaadinIcon icon) {
		Icon i = new Icon(icon);
		i.addClassName(IconSize.L.getClassName());
		return i;
	}

	// Combinations

	public static Icon createIcon(IconSize size, TextColor color, VaadinIcon icon) {
		Icon i = new Icon(icon);
		i.addClassNames(size.getClassName(), color.getClassName());
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

		FlexLayout card = createWrappingFlexLayout(Arrays.asList(LumoStyles.BorderRadius.S, LumoStyles.Padding.Uniform.M, LumoStyles.Shadow.S), chart);
		setBackgroundColor(LumoStyles.Color.BASE_COLOR, card);
		setBoxSizing(BoxSizing.BORDER_BOX, card);
		card.setHeight("400px");
		return card;
	}



	/* === NOTIFICATIONS === */

	public static void showNotification(String text) {
		Notification.show(text, 3000, Notification.Position.BOTTOM_CENTER);
	}



	/* === CSS UTILITIES === */

	public static void setAlignSelf(AlignSelf alignSelf, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("align-self", alignSelf.getValue());
		}
	}

	public static void setBackgroundColor(String backgroundColor, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("background-color", backgroundColor);
		}
	}

	public static void setBorderRadius(BorderRadius borderRadius, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("border-radius", borderRadius.getValue());
		}
	}

	public static void setBoxSizing(BoxSizing boxSizing, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("box-sizing", boxSizing.getValue());
		}
	}

	public static void setColSpan(int span, Component... components) {
		for (Component component : components) {
			component.getElement().setAttribute("colspan", Integer.toString(span));
		}
	}

	public static void setOverflow(Overflow overflow, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("overflow", overflow.getValue());
		}
	}

	public static void setTextAlign(TextAlign textAlign, Component... components) {
		for (Component component : components) {
			component.getElement().getStyle().set("text-align", textAlign.getValue());
		}
	}

	public static void setTheme(String theme, Component... components) {
		for (Component component : components) {
			component.getElement().setAttribute("theme", theme);
		}
	}

	public static void setTooltip(String tooltip, Component... components) {
		for (Component component : components) {
			component.getElement().setProperty("title", tooltip);
		}
	}

	public static void setWhiteSpace(WhiteSpace whiteSpace, Component... components) {
		for (Component component : components) {
			component.getElement().setProperty("white-space", whiteSpace.getValue());
		}
	}


}
