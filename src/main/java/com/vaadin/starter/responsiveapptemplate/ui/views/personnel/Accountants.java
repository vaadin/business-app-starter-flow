package com.vaadin.starter.responsiveapptemplate.ui.views.personnel;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
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

@Route(value = "accountants", layout = Root.class)
@PageTitle("Accountants")
public class Accountants extends ViewFrame {

	private ListDataProvider<Person> dataProvider;
	private DetailsDrawer detailsDrawer;
	private Label detailsDrawerTitle;

	public Accountants() {
		// Header
		if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
			setHeader(new AppBar("Accountants"));
		}

		// Grid
		Grid<Person> grid = new Grid<>();
		dataProvider = DataProvider.ofCollection(DummyData.getPersons());
		grid.setDataProvider(dataProvider);

		grid.addColumn(Person::getId)
				.setHeader("ID")
				.setFrozen(true)
				.setSortable(true)
				.setWidth(UIUtils.COLUMN_WIDTH_XS)
				.setFlexGrow(0);
		grid.addColumn(new ComponentRenderer<>(this::createUserInfo))
				.setHeader("Name")
				.setWidth(UIUtils.COLUMN_WIDTH_XL);
		grid.addColumn(new ComponentRenderer<>(this::createActive))
				.setHeader(UIUtils.createRightAlignedDiv("Active"))
				.setWidth(UIUtils.COLUMN_WIDTH_XS)
				.setFlexGrow(0);
		grid.addColumn(new ComponentRenderer<>(this::createReports))
				.setHeader(UIUtils.createRightAlignedDiv("Reports"))
				.setWidth(UIUtils.COLUMN_WIDTH_M)
				.setFlexGrow(0);
		grid.addColumn(new ComponentRenderer<>(this::createCompanies))
				.setHeader(UIUtils.createRightAlignedDiv("Companies"))
				.setWidth(UIUtils.COLUMN_WIDTH_M)
				.setFlexGrow(0);
		grid.addColumn(new ComponentRenderer<>(this::createDate))
				.setHeader(UIUtils.createRightAlignedDiv("Last Report"))
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

		// Details drawer
		initDetailsDrawer();

		// Set the content
		FlexLayout content = new FlexLayout(gridWrapper, detailsDrawer);
		content.setSizeFull();
		setContent(content);

		// Initial filtering
		filter();
	}

	private void filter() {
		dataProvider.setFilterByValue(Person::getRole, Person.Role.ACCOUNTANT);
	}

	private Component createUserInfo(Person person) {
		return new ListItem(UIUtils.createInitials(person.getInitials()), person.getName(), person.getEmail());
	}

	private Component createActive(Person person) {
		Icon icon;
		if (person.getRandomBoolean()) {
			icon = UIUtils.createPrimaryIcon(VaadinIcon.CHECK);
		} else {
			icon = UIUtils.createDisabledIcon(VaadinIcon.CLOSE);
		}
		return UIUtils.createRightAlignedDiv(icon);
	}

	private Component createReports() {
		return UIUtils.createRightAlignedDiv(UIUtils.createH4Label(UIUtils.formatUnits(DummyData.getRandomInt(0, 5000))));
	}

	private Component createCompanies() {
		return UIUtils.createRightAlignedDiv(UIUtils.createH4Label(String.valueOf(DummyData.getRandomInt(0, 50))));
	}

	private Component createDate(Person person) {
		return UIUtils.createRightAlignedDiv(UIUtils.formatDate(person.getLastModified()));
	}

	private void initDetailsDrawer() {
		detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

		// Header
		detailsDrawerTitle = UIUtils.createDetailsDrawerHeader("", true, true);
		detailsDrawer.setHeader(detailsDrawerTitle);

		// Footer
		Button save = UIUtils.createPrimaryButton("Save");
		save.addClickListener(e -> Notification.show("Not implemented yet.", 3000, Notification.Position.BOTTOM_CENTER));

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
				new FormLayout.ResponsiveStep("21em", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
		);

		TextField firstName = new TextField();
		firstName.setValue(person.getFirstName());
		firstName.setWidth("100%");

		TextField lastName = new TextField();
		lastName.setValue(person.getLastName());
		lastName.setWidth("100%");

		RadioButtonGroup<String> gender = new RadioButtonGroup<>();
		gender.setItems("Active", "Inactive");
		gender.setValue(person.getRandomBoolean() ? "Active" : "Inactive");

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
		FormLayout.FormItem genderItem = form.addFormItem(gender, "Gender");
		FormLayout.FormItem phoneItem = form.addFormItem(phone, "Phone");
		FormLayout.FormItem emailItem = form.addFormItem(email, "Email");
		FormLayout.FormItem companyItem = form.addFormItem(company, "Company");
		FormLayout.FormItem uploadItem = form.addFormItem(new Upload(), "Image");

		UIUtils.setFormLayoutColSpan(2, genderItem, phoneItem, emailItem, companyItem, uploadItem);

		return form;
	}
}