package com.vaadin.starter.responsivelayoutgrid.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.starter.responsivelayoutgrid.backend.UIConfig;
import com.vaadin.starter.responsivelayoutgrid.ui.utils.LumoStyles;
import com.vaadin.starter.responsivelayoutgrid.ui.utils.UIUtils;

import java.util.ArrayList;

public abstract class NavigationDrawer extends Div implements AfterNavigationObserver {

    private final String CLASS_NAME = "navigation-drawer";
    private final String OPEN = CLASS_NAME + "--open";
    private final String RAIL = CLASS_NAME + "--rail";

    private Div scrim;
    private Div content;
    private Div scrollArea;

    private Image avatar;
    private H4 username;
    private Label email;
    private Button dropdown;

    protected Div list;
    private ArrayList<NavigationItem> items;

    private final Button railButton = UIUtils.createSmallButton(VaadinIcon.CARET_LEFT, "Collapse");

    public NavigationDrawer() {
        setClassName(CLASS_NAME);
        init();
    }

    private void init() {
        // Backdrop on small viewports.
        scrim = new Div();
        scrim.setClassName(CLASS_NAME + "__scrim");
        scrim.addClickListener(event -> close());
        add(scrim);

        // Main content.
        content = new Div();
        content.setClassName(CLASS_NAME + "__content");
        add(content);

        // Scrollable area.
        scrollArea = new Div();
        scrollArea.setClassName(CLASS_NAME + "__scroll-area");
        content.add(scrollArea);

        // Header: account switcher or brand logo.
        if (UIConfig.getNavigationHeader().equals(UIConfig.NavigationHeader.ACCOUNT_SWITCHER)) {
            initAccountSwitcher();
        } else {
            initBrandExpression();
        }

        // Search field.
        TextField search = new TextField();
        search.setPlaceholder("Search");
        scrollArea.add(search);

        // Wrapper for navigation items.
        list = new Div();
        list.setClassName(CLASS_NAME + "__list");
        scrollArea.add(list);

        items = new ArrayList<>();

        // "Footer", currently only a collapse/expand button.
        railButton.setClassName(CLASS_NAME + "__footer");
        railButton.addClickListener(event -> setRailModeEnabled(getClassName().contains(RAIL)));
        content.add(railButton);
    }

    private void initAccountSwitcher() {
        avatar = new Image();
        avatar.setClassName(CLASS_NAME + "__avatar");
        avatar.setSrc("https://pbs.twimg.com/profile_images/798351849984294912/okhePpJW_400x400.jpg");

        username = new H4("Conor McGregor");
        username.setClassName(CLASS_NAME + "__title");

        email = new Label("conor.mcgregor@gmail.com");
        email.setClassName(CLASS_NAME + "__email");
        email.getElement().setAttribute(LumoStyles.THEME, LumoStyles.FontSize.S);

        dropdown = UIUtils.createSmallTertiaryIconButton(VaadinIcon.ANGLE_DOWN);
        email.add(dropdown);

        ContextMenu contextMenu = new ContextMenu(dropdown);
        contextMenu.setOpenOnClick(true);
        contextMenu.addItem("conor.mcgregor@outlook.com", e -> System.out.println("Testing..."));
        contextMenu.addItem("conor.mcgregor@yahoo.com", e -> System.out.println("Testing..."));

        scrollArea.add(avatar, username, email);
    }

    private void initBrandExpression() {
        Image logo = new Image();
        logo.setSrc("https://upload.wikimedia.org/wikipedia/en/thumb/b/bf/KFC_logo.svg/300px-KFC_logo.svg.png");

        Div logoWrapper = new Div(logo);
        logoWrapper.setClassName(CLASS_NAME + "__logo");

        scrollArea.add(logoWrapper);
    }

    private void setRailModeEnabled(boolean enabled) {
        if (enabled) {
            removeClassName(RAIL);
            railButton.setIcon(new Icon(VaadinIcon.CARET_LEFT));
        } else {
            addClassName(RAIL);
            railButton.setIcon(new Icon(VaadinIcon.CARET_RIGHT));
        }
    }

    public void toggle() {
        if (getClassName().contains(OPEN)) {
            close();
        } else {
            open();
        }
    }

    private void open() {
        addClassName(OPEN);
    }

    private void close() {
        removeClassName(OPEN);
    }

    protected void addNavigationItem(NavigationItem item) {
        list.add(item);
        items.add(item);
    }

    protected void addNavigationItem(NavigationItem parent, NavigationItem item) {
        parent.addSubItem(item);
        addNavigationItem(item);
    }

    public abstract NavigationItem addNavigationItem(VaadinIcon icon, String text, Class<? extends Component> navigationTarget);

    public abstract NavigationItem addNavigationItem(NavigationItem parent, String text, Class<? extends Component> navigationTarget);

    public ArrayList<NavigationItem> getNavigationItems() {
        return items;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        close();
    }
}
