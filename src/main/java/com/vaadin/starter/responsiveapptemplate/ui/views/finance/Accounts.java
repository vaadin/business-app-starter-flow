package com.vaadin.starter.responsiveapptemplate.ui.views.finance;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
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

import static com.vaadin.starter.responsiveapptemplate.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "accounts", layout = Root.class)
@ParentLayout(Root.class)
@PageTitle("Accounts")
public class Accounts extends ViewFrame implements RouterLayout {

	public Accounts() {
		// Header
		if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
			setHeader(new AppBar("Accounts"));
		}

		// Grid
		Grid<BankAccount> grid = new Grid<>();
		grid.setDataProvider(DataProvider.ofCollection(DummyData.getBankAccounts()));

		grid.addColumn(BankAccount::getId)
				.setHeader("ID")
				.setFrozen(true)
				.setSortable(true)
				.setWidth(UIUtils.COLUMN_WIDTH_XS)
				.setFlexGrow(0);
		grid.addColumn(new ComponentRenderer<>(this::createBankInfo))
				.setHeader("Bank Account")
				.setWidth(UIUtils.COLUMN_WIDTH_XL);
		grid.addColumn(BankAccount::getOwner)
				.setHeader("Owner")
				.setWidth(UIUtils.COLUMN_WIDTH_XL);
		grid.addColumn(new ComponentRenderer<>(this::createAvailability))
				.setHeader(UIUtils.createRightAlignedDiv("Availability ($)"))
				.setWidth(UIUtils.COLUMN_WIDTH_M)
				.setFlexGrow(0);
		grid.addColumn(TemplateRenderer.<BankAccount>of(
				"[[item.date]]")
				.withProperty("date", account -> UIUtils.formatDate(account.getUpdated())))
				.setHeader("Updated")
				.setComparator(BankAccount::getUpdated)
				.setWidth(UIUtils.COLUMN_WIDTH_M)
				.setFlexGrow(0);

		grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::viewDetails));

		grid.setSizeFull();

		// Set the content
		setContent(grid);
		getContent().addClassName(GRID_VIEW);
	}

	private Component createBankInfo(BankAccount bankAccount) {
		return new ListItem(bankAccount.getAccount(), bankAccount.getBank());
	}

	private Component createAvailability(BankAccount bankAccount) {
		Double availability = bankAccount.getAvailability();
		Label label = UIUtils.createH4Label(UIUtils.formatAmount(availability));

		if (availability > 0) {
			label.addClassName(TextColor.SUCCESS.getStyle());
		} else {
			label.addClassName(TextColor.ERROR.getStyle());
		}

		return UIUtils.createRightAlignedDiv(label);
	}

	private void viewDetails(BankAccount bankAccount) {
		UI.getCurrent().navigate(AccountDetails.class, bankAccount.getId());
	}
}
