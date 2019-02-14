package com.vaadin.starter.responsiveapptemplate.ui.views.demo;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
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
import com.vaadin.starter.responsiveapptemplate.ui.components.ListItem;
import com.vaadin.starter.responsiveapptemplate.ui.components.detailsdrawer.DetailsDrawer;
import com.vaadin.starter.responsiveapptemplate.ui.components.detailsdrawer.DetailsDrawerFooter;
import com.vaadin.starter.responsiveapptemplate.ui.components.detailsdrawer.DetailsDrawerHeader;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.responsiveapptemplate.ui.utils.LumoStyles;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;
import com.vaadin.starter.responsiveapptemplate.ui.views.SplitViewFrame;

import static com.vaadin.starter.responsiveapptemplate.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "horizontal-split-view", layout = Root.class)
@PageTitle("Horizontal Split View")
public class HorizontalSplitView extends SplitViewFrame {

	private Grid<Person> grid;
	private DetailsDrawer detailsDrawer;
	private DetailsDrawerHeader detailsDrawerHeader;

	public HorizontalSplitView() {
		if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
			setViewHeader(new AppBar("Personnel"));
		}
		setViewContent(createContent());
		setViewDetails(createDetailsDrawer());
	}

	private Component createContent() {
		Div content = new Div(createGrid());
		content.addClassName(GRID_VIEW);
		return content;
	}

	private Grid createGrid() {
		grid = new Grid<>();
		grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::showDetails));
		grid.setDataProvider(DataProvider.ofCollection(DummyData.getPersons()));
		grid.setHeight("100%");

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

		return grid;
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

	private DetailsDrawer createDetailsDrawer() {
		detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

		// Header
		detailsDrawerHeader = new DetailsDrawerHeader("");
		detailsDrawer.setHeader(detailsDrawerHeader);

		// Footer
		DetailsDrawerFooter footer = new DetailsDrawerFooter();
		footer.addCancelListener(e -> detailsDrawer.hide());
		detailsDrawer.setFooter(footer);

		return detailsDrawer;
	}

	private void showDetails(Person person) {
		detailsDrawerHeader.setText(person.getName());
		detailsDrawer.setContent(createDetails(person));
		detailsDrawer.show();
	}

	private FormLayout createDetails(Person person) {
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

		// Form layout
		FormLayout form = new FormLayout();
		form.addClassNames(LumoStyles.Padding.Bottom.L, LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Top.S);
		form.setResponsiveSteps(
				new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("21em", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP)
		);
		form.addFormItem(firstName, "First Name");
		form.addFormItem(lastName, "Last Name");
		FormLayout.FormItem genderItem = form.addFormItem(gender, "Gender");
		FormLayout.FormItem phoneItem = form.addFormItem(phone, "Phone");
		FormLayout.FormItem emailItem = form.addFormItem(email, "Email");
		FormLayout.FormItem companyItem = form.addFormItem(company, "Company");
		FormLayout.FormItem uploadItem = form.addFormItem(new Upload(), "Image");
		UIUtils.setColSpan(2, genderItem, phoneItem, emailItem, companyItem, uploadItem);
		return form;
	}
}
