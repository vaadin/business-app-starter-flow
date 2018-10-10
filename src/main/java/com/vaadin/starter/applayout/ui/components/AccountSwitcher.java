package com.vaadin.starter.applayout.ui.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.starter.applayout.ui.utils.LumoStyles;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

public class AccountSwitcher extends Div {

    private String CLASS_NAME = "account-switcher";

    private Image avatar;
    private H4 username;
    private Label email;
    private Button dropdown;
    private ContextMenu menu;

    public AccountSwitcher() {
        setClassName(CLASS_NAME);

        initAvatar();
        initUsername();
        initEmail();

        add(avatar, username, email);
    }

    private void initAvatar() {
        avatar = new Image();
        avatar.setClassName(CLASS_NAME + "__avatar");
        avatar.setSrc("https://pbs.twimg.com/profile_images/2642704545/a77c0524766c6f3b4be4929f2005e627_400x400.png");
    }

    private void initUsername() {
        username = new H4("John Smith");
        username.setClassName(CLASS_NAME + "__title");
    }

    private void initEmail() {
        email = new Label("john.smith@gmail.com");
        email.setClassName(CLASS_NAME + "__email");
        email.getElement().setAttribute(LumoStyles.THEME, LumoStyles.FontSize.S);

        dropdown = UIUtils.createSmallTertiaryIconButton(VaadinIcon.ANGLE_DOWN);
        email.add(dropdown);

        menu = new ContextMenu(dropdown);
        menu.setOpenOnClick(true);
        menu.addItem("john.smith@outlook.com", e -> System.out.println("Testing..."));
        menu.addItem("john.smith@yahoo.com", e -> System.out.println("Testing..."));
    }
}
