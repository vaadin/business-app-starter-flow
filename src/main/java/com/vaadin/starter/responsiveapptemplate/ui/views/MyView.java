package com.vaadin.starter.responsiveapptemplate.ui.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.responsiveapptemplate.ui.Root;
import com.vaadin.starter.responsiveapptemplate.ui.utils.LumoStyles;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;

@Route(value = "my-view", layout = Root.class)
@PageTitle("My View")
public class MyView extends ViewFrame {

	public MyView() {
		// Header
		Label title = new Label("Title");

		Button print = new Button(new Icon(VaadinIcon.PRINT));
		Button external = new Button(new Icon(VaadinIcon.EXTERNAL_LINK));

		HorizontalLayout header = new HorizontalLayout(title, print, external);
		header.setAlignItems(FlexComponent.Alignment.CENTER);
		header.setFlexGrow(1, title);
		header.setPadding(true);
		header.setSpacing(true);

		setViewHeader(header);

		// Content
		Paragraph text = new Paragraph("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");

		VerticalLayout content = new VerticalLayout(text);
		content.setPadding(true);

		setViewContent(content);

		// Footer
		Button reply = new Button("Reply", new Icon(VaadinIcon.REPLY));
		Button replyAll = new Button("Reply All", new Icon(VaadinIcon.REPLY_ALL));
		Button forward = new Button("Forward", new Icon(VaadinIcon.FORWARD));

		HorizontalLayout footer = new HorizontalLayout(reply, replyAll, forward);
		footer.setPadding(true);
		footer.setSpacing(true);

		setViewFooter(footer);

		UIUtils.setBackgroundColor(LumoStyles.Color.Primary._10, header);
		UIUtils.setBackgroundColor(LumoStyles.Color.Contrast._5, footer);
	}

}
