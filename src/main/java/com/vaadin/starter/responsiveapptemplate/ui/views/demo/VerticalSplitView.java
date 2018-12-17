package com.vaadin.starter.responsiveapptemplate.ui.views.demo;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.responsiveapptemplate.backend.DummyData;
import com.vaadin.starter.responsiveapptemplate.backend.Person;
import com.vaadin.starter.responsiveapptemplate.backend.UIConfig;
import com.vaadin.starter.responsiveapptemplate.ui.Root;
import com.vaadin.starter.responsiveapptemplate.ui.components.DetailsDrawer;
import com.vaadin.starter.responsiveapptemplate.ui.components.ListItem;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.responsiveapptemplate.ui.utils.CSSProperties;
import com.vaadin.starter.responsiveapptemplate.ui.utils.LumoStyles;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;
import com.vaadin.starter.responsiveapptemplate.ui.views.ViewFrame;

import java.util.Arrays;
import java.util.Collections;

import static com.vaadin.starter.responsiveapptemplate.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "vertical-split-view", layout = Root.class)
@PageTitle("Vertical Split View")
public class VerticalSplitView extends ViewFrame {

	private DetailsDrawer detailsDrawer;
	private Label detailsDrawerTitle;

	public VerticalSplitView() {
		// Header
		if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
			setHeader(new AppBar("Personnel"));
		}

		// Grid
		Grid<Person> grid = new Grid<>();
		grid.setDataProvider(DataProvider.ofCollection(DummyData.getPersons()));

		grid.addColumn(Person::getId)
				.setHeader("ID")
				.setFrozen(true)
				.setSortable(true)
				.setWidth(UIUtils.COLUMN_WIDTH_XS)
				.setFlexGrow(0);
		grid.addColumn(new ComponentRenderer<>(this::createUserInfo))
				.setHeader("Name")
				.setWidth(UIUtils.COLUMN_WIDTH_XL)
				.setFlexGrow(1);
		grid.addColumn(new ComponentRenderer<>(this::createTwitter))
				.setHeader("Twitter")
				.setWidth(UIUtils.COLUMN_WIDTH_M)
				.setFlexGrow(0);
		grid.addColumn(new ComponentRenderer<>(this::createForumPosts))
				.setHeader("Forum Posts")
				.setWidth(UIUtils.COLUMN_WIDTH_M)
				.setFlexGrow(0);
		grid.addColumn(new LocalDateRenderer<>(Person::getLastModified, "MMM dd, YYYY"))
				.setHeader("Last Modified")
				.setSortable(true)
				.setWidth(UIUtils.COLUMN_WIDTH_M)
				.setFlexGrow(0);

		grid.addSelectionListener(e -> {
			if (e.getFirstSelectedItem().isPresent()) {
				showDetails(e.getFirstSelectedItem().get());
			}
		});

		grid.setSizeFull();

		// Grid wrapper
		Div gridWrapper = UIUtils.createDiv(Collections.singleton(GRID_VIEW), grid);

		// Wrapper
		initDetailsDrawer();

		// Set the content
		FlexLayout content = UIUtils.createColumn(gridWrapper, detailsDrawer);
		content.setSizeFull();
		setContent(content);
	}

	private void initDetailsDrawer() {
		detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.BOTTOM);

		// Header
		detailsDrawerTitle = UIUtils.createDetailsDrawerHeader("", true, true);
		detailsDrawer.setHeader(detailsDrawerTitle);

		// Footer
		Button save = UIUtils.createPrimaryButton("Save");
		save.addClickListener(e -> UIUtils.showNotification("Not implemented yet."));

		Button cancel = UIUtils.createTertiaryButton("Cancel");
		cancel.addClickListener(e -> detailsDrawer.hide());

		FlexLayout footer = UIUtils.createFlexLayout(Arrays.asList(LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Vertical.S, LumoStyles.Spacing.Right.S), save, cancel);
		footer.getStyle().set(CSSProperties.BackgroundColor.PROPERTY, LumoStyles.Color.CONTRAST_5);
		footer.setWidth("100%");
		detailsDrawer.setFooter(footer);
	}

	private void showDetails(Person person) {
		detailsDrawerTitle.setText(person.getName());
		detailsDrawer.setContent(createDetails(person));
		detailsDrawer.show();
	}

	private FormLayout createDetails(Person person) {
		FormLayout form = UIUtils.createFormLayout(
				Arrays.asList(
						LumoStyles.Padding.Bottom.L,
						LumoStyles.Padding.Horizontal.L,
						LumoStyles.Padding.Top.S
				)
		);

		form.setResponsiveSteps(
				new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("600px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("1024px", 3, FormLayout.ResponsiveStep.LabelsPosition.TOP)
		);

		TextField firstName = new TextField();
		firstName.setValue(person.getFirstName());
		firstName.setWidth("100%");

		TextField lastName = new TextField();
		lastName.setValue(person.getLastName());
		lastName.setWidth("100%");

		RadioButtonGroup<String> gender = new RadioButtonGroup<>();
		gender.setItems("Male", "Female", "Other");
		gender.setValue("Other");

		FlexLayout phone = UIUtils.createPhoneLayout();

		TextField email = new TextField();
		email.setValue(person.getEmail());
		email.setWidth("100%");

		ComboBox company = new ComboBox();
		company.setItems(DummyData.getCompanies());
		company.setValue(DummyData.getCompany());
		company.setWidth("100%");

		form.addFormItem(firstName, "First Name");
		form.addFormItem(lastName, "Last Name");
		form.addFormItem(gender, "Gender");
		form.addFormItem(phone, "Phone");
		form.addFormItem(email, "Email");
		form.addFormItem(company, "Company");
		form.addFormItem(new Upload(), "Image");

		return form;
	}

	private Component createUserInfo(Person person) {
		ListItem item = new ListItem(UIUtils.createInitials(person.getInitials()), person.getName(), person.getEmail());
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
			badge = UIUtils.createSuccessBadge(UIUtils.formatAmount(person.getRandomInteger()));
		} else {
			badge = UIUtils.createErrorBadge(UIUtils.formatAmount(person.getRandomInteger()));
		}
		return badge;
	}
}
