package com.vaadin.starter.applayout.ui.views.accountreporting;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.starter.applayout.backend.UIConfig;
import com.vaadin.starter.applayout.ui.Root;
import com.vaadin.starter.applayout.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.applayout.ui.views.AbstractView;

@Route(value = "account-reporting", layout = Root.class)
@ParentLayout(Root.class)
@PageTitle("Account Reporting")
public class AccountReporting extends AbstractView implements RouterLayout {

    private AppBar appBar;
    private String STATEMENTS = "Statements";
    private String BALANCES = "Balances";

    public AccountReporting() {
        // Header
        appBar = new AppBar("Account Reporting");

        appBar.addTab(STATEMENTS);
        appBar.addTab(BALANCES);

        appBar.addTabSelectionListener(e -> updateContent());
        appBar.centerTabs();

        updateContent();
    }

    @Override
    protected void initSlots() {
        if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
            setHeader(appBar);
        }
    }

    private void updateContent() {
        String label = appBar.getSelectedTab().getLabel();

        if (label.equals(STATEMENTS)) {
            setContent(new Statements());

        } else if (label.equals(BALANCES)) {
            setContent(new Balances());

        }
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        if (content != null) {
            getContent().getElement().appendChild(content.getElement());
        }
    }
}
