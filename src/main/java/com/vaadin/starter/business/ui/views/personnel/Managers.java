package com.vaadin.starter.business.ui.views.personnel;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditorPosition;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.business.backend.DummyData;
import com.vaadin.starter.business.backend.Person;
import com.vaadin.starter.business.ui.MainLayout;
import com.vaadin.starter.business.ui.components.FlexBoxLayout;
import com.vaadin.starter.business.ui.components.Initials;
import com.vaadin.starter.business.ui.components.ListItem;
import com.vaadin.starter.business.ui.components.detailsdrawer.DetailsDrawerHeader;
import com.vaadin.starter.business.ui.layout.size.*;
import com.vaadin.starter.business.ui.util.LumoStyles;
import com.vaadin.starter.business.ui.util.UIUtils;
import com.vaadin.starter.business.ui.util.css.BoxSizing;
import com.vaadin.starter.business.ui.util.css.FlexDirection;
import com.vaadin.starter.business.ui.util.css.Overflow;
import com.vaadin.starter.business.ui.views.ViewFrame;

import java.util.Optional;

@Route(value = "managers", layout = MainLayout.class)
@PageTitle("Managers")
public class Managers extends ViewFrame {

	private Crud<Person> crud;
	private Grid<Person> grid;
	private ListDataProvider<Person> dataProvider;
	private DetailsDrawerHeader header;

	public Managers() {
		setViewContent(createContent());
		filter();
	}

	private Component createContent() {
		FlexBoxLayout content = new FlexBoxLayout(createCrud());
		content.setBoxSizing(BoxSizing.BORDER_BOX);
		content.setHeightFull();
		content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);
		return content;
	}

	private Crud<Person> createCrud() {
		crud = new Crud<>(Person.class, createGrid(), createEditor());
		UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, crud);
		crud.setEditOnClick(true);
		crud.setEditorPosition(CrudEditorPosition.BOTTOM);
		crud.setSizeFull();
		return crud;
	}

	private Grid<Person> createGrid() {
		grid = new Grid<>();
		grid.addSelectionListener(event -> {
			Optional<Person> person = event.getFirstSelectedItem();
			if (person.isPresent()) {
				header.setTitle(person.get().getFirstName() + " " + person.get().getLastName());
			}
		});
		grid.setSelectionMode(Grid.SelectionMode.SINGLE);
		dataProvider = DataProvider.ofCollection(DummyData.getPersons());
		grid.setDataProvider(dataProvider);
		grid.setSizeFull();

		grid.addColumn(Person::getId)
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setFrozen(true)
				.setHeader("ID")
				.setSortable(true);
		grid.addColumn(new ComponentRenderer<>(this::createUserInfo))
				.setAutoWidth(true)
				.setHeader("Name");
		grid.addColumn(new ComponentRenderer<>(this::createActive))
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setHeader("Active")
				.setTextAlign(ColumnTextAlign.END);
		grid.addColumn(new ComponentRenderer<>(this::createApprovalLimit))
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setHeader("Approval Limit ($)")
				.setTextAlign(ColumnTextAlign.END);
		grid.addColumn(new ComponentRenderer<>(this::createDate))
				.setAutoWidth(true)
				.setFlexGrow(0)
				.setHeader("Last Report")
				.setTextAlign(ColumnTextAlign.END);

		return grid;
	}

	private Component createUserInfo(Person person) {
		ListItem item = new ListItem(
				new Initials(person.getInitials()), person.getName(),
				person.getEmail());
		item.setPadding(Vertical.XS);
		item.setSpacing(Right.M);
		return item;
	}

	private Component createActive(Person person) {
		Icon icon;
		if (person.getRandomBoolean()) {
			icon = UIUtils.createPrimaryIcon(VaadinIcon.CHECK);
		} else {
			icon = UIUtils.createDisabledIcon(VaadinIcon.CLOSE);
		}
		return icon;
	}

	private Component createApprovalLimit(Person person) {
		int amount = person.getRandomInteger() > 0 ? person.getRandomInteger()
				: 0;
		return UIUtils.createAmountLabel(amount);
	}

	private Component createDate(Person person) {
		return new Span(UIUtils.formatDate(person.getLastModified()));
	}

	private BinderCrudEditor<Person> createEditor() {
		Binder<Person> binder = new Binder<>(Person.class);

		TextField firstName = new TextField();
		firstName.setWidthFull();
		binder.bind(firstName, "firstName");

		TextField lastName = new TextField();
		lastName.setWidthFull();
		binder.bind(lastName, "lastName");

		RadioButtonGroup<String> status = new RadioButtonGroup<>();
		status.setItems("Active", "Inactive");
		binder.bind(status,
				(person) -> person.getRandomBoolean() ? "Active" : "Inactive",
				(person, value) -> person.setRandomBoolean(value == "Active" ? true : false));

		FlexLayout phone = UIUtils.createPhoneLayout();

		TextField email = new TextField();
		email.setWidthFull();
		binder.bind(email, "email");

		ComboBox<String> company = new ComboBox<>();
		company.setItems(DummyData.getCompanies());
		company.setValue(DummyData.getCompany());
		company.setWidthFull();

		// Editor header
		header = new DetailsDrawerHeader("");
		header.addCloseListener(event -> crud.setOpened(false));

		// Form layout
		FormLayout form = new FormLayout();
		UIUtils.setOverflow(Overflow.AUTO, form);
		UIUtils.setPadding(form, Bottom.L, Horizontal.RESPONSIVE_L, Top.S);
		form.setResponsiveSteps(
				new FormLayout.ResponsiveStep("0", 1,
						FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("600px", 2,
						FormLayout.ResponsiveStep.LabelsPosition.TOP),
				new FormLayout.ResponsiveStep("1024px", 3,
						FormLayout.ResponsiveStep.LabelsPosition.TOP)
		);
		form.addFormItem(firstName, "First Name");
		form.addFormItem(lastName, "Last Name");
		form.addFormItem(status, "Status");
		form.addFormItem(phone, "Phone");
		form.addFormItem(email, "Email");
		form.addFormItem(company, "Company");
		form.addFormItem(new Upload(), "Image");

		FlexBoxLayout view = new FlexBoxLayout(header, form);
		view.setFlexDirection(FlexDirection.COLUMN);
		view.setHeightFull();

		return new BinderCrudEditor<>(binder, view);
	}

	private void filter() {
		dataProvider.setFilterByValue(Person::getRole, Person.Role.MANAGER);
	}
}
