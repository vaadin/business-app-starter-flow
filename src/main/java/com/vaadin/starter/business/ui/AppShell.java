package com.vaadin.starter.business.ui;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.server.AppShellSettings;
import com.vaadin.flow.server.PWA;

@PWA(name = "###Project Name###", shortName = "###Project Name###", iconPath = "images/logo-18.png", backgroundColor = "#233348", themeColor = "#233348")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class AppShell implements AppShellConfigurator {

	@Override
	public void configurePage(AppShellSettings settings) {
		settings.addMetaTag("apple-mobile-web-app-capable", "yes");
		settings.addMetaTag("apple-mobile-web-app-status-bar-style", "black");
		settings.addFavIcon("icon", "frontend/images/favicons/favicon.ico",
				"256x256");
	}
}
