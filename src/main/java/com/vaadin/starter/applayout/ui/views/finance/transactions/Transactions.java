package com.vaadin.starter.applayout.ui.views.finance.transactions;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.applayout.ui.views.ViewFrame;

@Route(value = "transactions", layout = Root.class)
@ParentLayout(Root.class)
@PageTitle("Transactions")
public class Transactions extends ViewFrame implements RouterLayout {

    private AppBar appBar;
    private String STATEMENTS = "All Transactions";
    private String BALANCES = "Bank Accounts";

    public Transactions() {
        // Header
        appBar = new AppBar("Transactions");

        appBar.addTab(STATEMENTS);
        appBar.addTab(BALANCES);

        appBar.addTabSelectionListener(e -> updateContent());
        appBar.centerTabs();

        updateContent();

        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            setHeader(appBar);
        }
    }

    private void updateContent() {
        String label = appBar.getSelectedTab().getLabel();

        if (label.equals(STATEMENTS)) {
            setContent(new AllTransactions());

        } else if (label.equals(BALANCES)) {
            setContent(new BankAccounts());
        }
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        if (content != null) {
            getContent().getElement().appendChild(content.getElement());
        }
    }
}
