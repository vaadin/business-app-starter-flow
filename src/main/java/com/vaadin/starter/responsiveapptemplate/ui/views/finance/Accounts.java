package com.vaadin.starter.responsiveapptemplate.ui.views.finance;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.starter.responsiveapptemplate.backend.BankAccount;
import com.vaadin.starter.responsiveapptemplate.backend.DummyData;
import com.vaadin.starter.responsiveapptemplate.backend.UIConfig;
import com.vaadin.starter.responsiveapptemplate.ui.Root;
import com.vaadin.starter.responsiveapptemplate.ui.components.ListItem;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.responsiveapptemplate.ui.utils.TextColor;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;
import com.vaadin.starter.responsiveapptemplate.ui.views.ViewFrame;

import java.time.format.DateTimeFormatter;

import static com.vaadin.starter.responsiveapptemplate.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "accounts", layout = Root.class)
@ParentLayout(Root.class)
@PageTitle("Accounts")
public class Accounts extends ViewFrame implements RouterLayout {

	private Grid<BankAccount> grid;

	public Accounts() {
		// View header
		if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
			setViewHeader(new AppBar("Accounts"));
		}

		// Grid
		initGrid();

		// Set the content
		setViewContent(grid);
		getViewContent().addClassName(GRID_VIEW);
	}

	private void initGrid() {
		grid = new Grid<>();
		grid.setDataProvider(DataProvider.ofCollection(DummyData.getBankAccounts()));

		grid.addColumn(BankAccount::getId)
				.setFlexGrow(0)
				.setFrozen(true)
				.setHeader("ID")
				.setSortable(true)
				.setWidth(UIUtils.COLUMN_WIDTH_XS);
		grid.addColumn(new ComponentRenderer<>(this::createBankInfo))
				.setHeader("Bank Account")
				.setWidth(UIUtils.COLUMN_WIDTH_XL);
		grid.addColumn(BankAccount::getOwner)
				.setHeader("Owner")
				.setWidth(UIUtils.COLUMN_WIDTH_XL);
		grid.addColumn(new ComponentRenderer<>(this::createAvailability))
				.setFlexGrow(0)
				.setHeader(UIUtils.createRightAlignedDiv("Availability ($)"))
				.setWidth(UIUtils.COLUMN_WIDTH_M);
		grid.addColumn(new LocalDateRenderer<>(BankAccount::getUpdated, DateTimeFormatter.ofPattern("MMM dd, YYYY")))
				.setComparator(BankAccount::getUpdated)
				.setFlexGrow(0)
				.setHeader("Updated")
				.setWidth(UIUtils.COLUMN_WIDTH_M);

		grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::viewDetails));

		grid.setSizeFull();
	}

	private Component createBankInfo(BankAccount bankAccount) {
		ListItem item = new ListItem(bankAccount.getAccount(), bankAccount.getBank());
		item.setHorizontalPadding(false);
		return item;
	}

	private Component createAvailability(BankAccount bankAccount) {
		Double availability = bankAccount.getAvailability();
		Label label = UIUtils.createAmountLabel(availability);

		if (availability > 0) {
			label.addClassName(TextColor.SUCCESS.getClassName());
		} else {
			label.addClassName(TextColor.ERROR.getClassName());
		}

		return UIUtils.createRightAlignedDiv(label);
	}

	private void viewDetails(BankAccount bankAccount) {
		UI.getCurrent().navigate(AccountDetails.class, bankAccount.getId());
	}
}
