package com.vaadin.starter.applayout.ui.components;

import com.vaadin.flow.component.ComponentEvent;

public class NaviDrawerEvent extends ComponentEvent<AppBar> {
    public NaviDrawerEvent(AppBar source, boolean fromClient) {
        super(source, fromClient);
    }
}
