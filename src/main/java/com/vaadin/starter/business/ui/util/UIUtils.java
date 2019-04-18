package com.vaadin.starter.business.ui.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.starter.business.backend.Address;
import com.vaadin.starter.business.backend.DummyData;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.layout.size.Right;
import com.vaadin.starter.business.ui.util.css.AlignSelf;
import com.vaadin.starter.business.ui.util.css.BorderRadius;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.util.css.Overflow;
import com.vaadin.starter.business.ui.util.css.Shadow;
import com.vaadin.starter.business.ui.util.css.TextAlign;
import com.vaadin.starter.business.ui.util.css.WhiteSpace;

public class UIUtils {

    public static final String IMG_PATH = "images/";

    public static final String COLUMN_WIDTH_XS = "80px";
    public static final String COLUMN_WIDTH_S = "120px";
    public static final String COLUMN_WIDTH_M = "160px";
    public static final String COLUMN_WIDTH_L = "200px";
    public static final String COLUMN_WIDTH_XL = "240px";

    /**
     * Thread-unsafe formatters.
     */
    private static final ThreadLocal<DecimalFormat> decimalFormat = ThreadLocal
            .withInitial(() -> new DecimalFormat("###,###,###.00"));
    private static final ThreadLocal<DateTimeFormatter> dateFormat = ThreadLocal
            .withInitial(() -> DateTimeFormatter.ofPattern("MMM dd, YYYY"));

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

    public static Button createTertiaryInlineButton(String text,
            VaadinIcon icon) {
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
        return createButton(text, ButtonVariant.LUMO_SUCCESS,
                ButtonVariant.LUMO_PRIMARY);
    }

    public static Button createSuccessPrimaryButton(VaadinIcon icon) {
        return createButton(icon, ButtonVariant.LUMO_SUCCESS,
                ButtonVariant.LUMO_PRIMARY);
    }

    public static Button createSuccessPrimaryButton(String text,
            VaadinIcon icon) {
        return createButton(text, icon, ButtonVariant.LUMO_SUCCESS,
                ButtonVariant.LUMO_PRIMARY);
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
        return createButton(text, ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_PRIMARY);
    }

    public static Button createErrorPrimaryButton(VaadinIcon icon) {
        return createButton(icon, ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_PRIMARY);
    }

    public static Button createErrorPrimaryButton(String text,
            VaadinIcon icon) {
        return createButton(text, icon, ButtonVariant.LUMO_ERROR,
                ButtonVariant.LUMO_PRIMARY);
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
        return createButton(text, ButtonVariant.LUMO_CONTRAST,
                ButtonVariant.LUMO_PRIMARY);
    }

    public static Button createContrastPrimaryButton(VaadinIcon icon) {
        return createButton(icon, ButtonVariant.LUMO_CONTRAST,
                ButtonVariant.LUMO_PRIMARY);
    }

    public static Button createContrastPrimaryButton(String text,
            VaadinIcon icon) {
        return createButton(text, icon, ButtonVariant.LUMO_CONTRAST,
                ButtonVariant.LUMO_PRIMARY);
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

    public static Button createButton(VaadinIcon icon,
            ButtonVariant... variants) {
        Button button = new Button(new Icon(icon));
        button.addThemeVariants(variants);
        return button;
    }

    // Text and icon

    public static Button createButton(String text, VaadinIcon icon,
            ButtonVariant... variants) {
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

    public static Label createLabel(FontSize size, TextColor color,
            String text) {
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
        layout.setTheme(Lumo.DARK);

        layout.setHeight(LumoStyles.Size.M);
        layout.setWidth(LumoStyles.Size.M);
        return layout;
    }

    public static String formatAddress(Address address) {
        return address.getStreet() + "\n" + address.getCity() + ", "
                + address.getCity() + " " + address.getZip();
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

    public static Icon createIcon(IconSize size, TextColor color,
            VaadinIcon icon) {
        Icon i = new Icon(icon);
        i.addClassNames(size.getClassName(), color.getClassName());
        return i;
    }

    /* === DATES === */

    public static String formatDate(LocalDate date) {
        return dateFormat.get().format(date);
    }

    /* === NOTIFICATIONS === */

    public static void showNotification(String text) {
        Notification.show(text, 3000, Notification.Position.BOTTOM_CENTER);
    }

    /* === CSS UTILITIES === */

    public static void setAlignSelf(AlignSelf alignSelf,
            Component... components) {
        for (Component component : components) {
            component.getElement().getStyle().set("align-self",
                    alignSelf.getValue());
        }
    }

    public static void setBackgroundColor(String backgroundColor,
            Component... components) {
        for (Component component : components) {
            component.getElement().getStyle().set("background-color",
                    backgroundColor);
        }
    }

    public static void setBorderRadius(BorderRadius borderRadius,
            Component... components) {
        for (Component component : components) {
            component.getElement().getStyle().set("border-radius",
                    borderRadius.getValue());
        }
    }

    public static void setBoxSizing(BoxSizing boxSizing,
            Component... components) {
        for (Component component : components) {
            component.getElement().getStyle().set("box-sizing",
                    boxSizing.getValue());
        }
    }

    public static void setColSpan(int span, Component... components) {
        for (Component component : components) {
            component.getElement().setAttribute("colspan",
                    Integer.toString(span));
        }
    }

    public static void setFontWeight(FontWeight fontWeight,
            Component... components) {
        for (Component component : components) {
            component.getElement().getStyle().set("font-weight",
                    fontWeight.getValue());
        }
    }

    public static void setMaxWidth(String value, Component... components) {
        for (Component component : components) {
            component.getElement().getStyle().set("max-width", value);
        }
    }

    public static void setOverflow(Overflow overflow, Component... components) {
        for (Component component : components) {
            component.getElement().getStyle().set("overflow",
                    overflow.getValue());
        }
    }

    public static void setShadow(Shadow shadow, Component... components) {
        for (Component component : components) {
            component.getElement().getStyle().set("box-shadow",
                    shadow.getValue());
        }
    }

    public static void setTextAlign(TextAlign textAlign,
            Component... components) {
        for (Component component : components) {
            component.getElement().getStyle().set("text-align",
                    textAlign.getValue());
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

    public static void setWhiteSpace(WhiteSpace whiteSpace,
            Component... components) {
        for (Component component : components) {
            component.getElement().setProperty("white-space",
                    whiteSpace.getValue());
        }
    }

    public static void setWidth(String value, Component... components) {
        for (Component component : components) {
            component.getElement().getStyle().set("width", value);
        }
    }

}
