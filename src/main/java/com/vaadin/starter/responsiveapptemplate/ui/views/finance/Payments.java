package com.vaadin.starter.responsiveapptemplate.ui.views.finance;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.responsiveapptemplate.backend.DummyData;
import com.vaadin.starter.responsiveapptemplate.backend.Payment;
import com.vaadin.starter.responsiveapptemplate.backend.UIConfig;
import com.vaadin.starter.responsiveapptemplate.ui.Root;
import com.vaadin.starter.responsiveapptemplate.ui.components.DetailsDrawer;
import com.vaadin.starter.responsiveapptemplate.ui.components.ListItem;
import com.vaadin.starter.responsiveapptemplate.ui.components.navigation.bar.AppBar;
import com.vaadin.starter.responsiveapptemplate.ui.layout.FlexBoxLayout;
import com.vaadin.starter.responsiveapptemplate.ui.layout.FlexDirection;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Horizontal;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Right;
import com.vaadin.starter.responsiveapptemplate.ui.layout.size.Vertical;
import com.vaadin.starter.responsiveapptemplate.ui.utils.CSSProperties;
import com.vaadin.starter.responsiveapptemplate.ui.utils.LumoStyles;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;
import com.vaadin.starter.responsiveapptemplate.ui.views.ViewFrame;

import static com.vaadin.starter.responsiveapptemplate.ui.utils.ViewStyles.GRID_VIEW;

@Route(value = "payments", layout = Root.class)
@PageTitle("Payments")
public class Payments extends ViewFrame {

	private AppBar appBar;

	private Grid<Payment> grid;
	private ListDataProvider<Payment> dataProvider;

	private DetailsDrawer detailsDrawer;

	public Payments() {
		if (UIConfig.getNaviMode().equals(UIConfig.NaviMode.LINKS)) {
			appBar = createAppBar();
			setViewHeader(appBar);
		}
		setViewContent(createContent());

		filter();
	}

	private AppBar createAppBar() {
		AppBar appBar = new AppBar("Payments");
		for (Payment.Status status : Payment.Status.values()) {
			appBar.addTab(status.getName());
		}
		appBar.addTabSelectionListener(e -> {
			filter();
			hideDetails();
		});
		appBar.centerTabs();
		return appBar;
	}

	private Component createContent() {
		grid = createGrid();
		Div gridWrapper = new Div(grid);
		gridWrapper.addClassName(GRID_VIEW);

		detailsDrawer = createDetailsDrawer();

		FlexLayout content = new FlexLayout(gridWrapper, detailsDrawer);
		content.setSizeFull();
		return content;
	}

	private Grid createGrid() {
		Grid<Payment> grid = new Grid<>();
		dataProvider = DataProvider.ofCollection(DummyData.getPayments());
		grid.addSelectionListener(event -> event.getFirstSelectedItem().ifPresent(this::showDetails));
		grid.setDataProvider(dataProvider);
		grid.setSizeFull();

		grid.addColumn(new ComponentRenderer<>(UIUtils::createBadge))
				.setHeader("Status")
				.setWidth(UIUtils.COLUMN_WIDTH_S)
				.setFlexGrow(0);
		grid.addColumn(new ComponentRenderer<>(this::createFromInfo))
				.setHeader("From")
				.setWidth(UIUtils.COLUMN_WIDTH_XL);
		grid.addColumn(new ComponentRenderer<>(this::createToInfo))
				.setHeader("To")
				.setWidth(UIUtils.COLUMN_WIDTH_XL);
		grid.addColumn(new ComponentRenderer<>(this::createAmount))
				.setHeader(UIUtils.createRightAlignedDiv("Amount ($)"))
				.setWidth(UIUtils.COLUMN_WIDTH_M)
				.setFlexGrow(0);
		grid.addColumn(TemplateRenderer.<Payment>of(
				"[[item.date]]")
				.withProperty("date", payment -> UIUtils.formatDate(payment.getDate())))
				.setHeader("Due Date")
				.setComparator(Payment::getDate)
				.setWidth(UIUtils.COLUMN_WIDTH_M)
				.setFlexGrow(0);

		return grid;
	}

	private void filter() {
		if (appBar != null) {
			dataProvider.setFilterByValue(Payment::getStatus, Payment.Status.valueOf(appBar.getSelectedTab().getLabel().toUpperCase()));
		}
	}

	private Component createFromInfo(Payment payment) {
		ListItem item = new ListItem(payment.getFrom(), payment.getFromIBAN());
		item.setHorizontalPadding(false);
		return item;
	}

	private Component createToInfo(Payment payment) {
		ListItem item = new ListItem(payment.getTo(), payment.getToIBAN());
		item.setHorizontalPadding(false);
		return item;
	}

	private Component createAmount(Payment payment) {
		Double amount = payment.getAmount();
		Label label = UIUtils.createAmountLabel(amount);
		return UIUtils.createRightAlignedDiv(label);
	}

	private DetailsDrawer createDetailsDrawer() {
		DetailsDrawer detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.RIGHT);

		// Header
		Label detailsDrawerTitle = UIUtils.createDetailsDrawerHeader("Payment Details", false, true);

		Tabs tabs = new Tabs(new Tab("Details"), new Tab("Attachments"), new Tab("History"));
		tabs.addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS);

		detailsDrawer.setHeader(detailsDrawerTitle, tabs);
		detailsDrawer.getHeader().setFlexDirection(FlexDirection.COLUMN);

		// Footer
		Button close = UIUtils.createTertiaryButton("Close");
		close.addClickListener(e -> detailsDrawer.hide());

		FlexBoxLayout footer = new FlexBoxLayout(close);
		footer.setBackgroundColor(LumoStyles.Color.Contrast._5);
		footer.setPadding(Horizontal.L, Vertical.S);
		footer.setSpacing(Right.S);
		footer.setWidth("100%");
		detailsDrawer.setFooter(footer);

		return detailsDrawer;
	}

	private void showDetails(Payment payment) {
		detailsDrawer.setContent(createDetails(payment));
		detailsDrawer.show();
	}

	private void hideDetails() {
		detailsDrawer.hide();
	}

	private Component createDetails(Payment payment) {
		ListItem status = new ListItem(payment.getStatus().getIcon(), payment.getStatus().getName(), "Status");
		status.addPrimaryClassNames(LumoStyles.Margin.Top.XS);
		status.setPrimaryElementAttribute(LumoStyles.THEME, payment.getStatus().getTheme());
		status.setPrimaryStyleProperty(CSSProperties.AlignSelf.PROPERTY, CSSProperties.AlignSelf.BASELINE);
		status.getElement().setProperty(CSSProperties.Title.PROPERTY, payment.getStatus().getDesc());

		ListItem from = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.UPLOAD_ALT), payment.getFrom() + "\n" + payment.getFromIBAN(), "Sender");
		ListItem to = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.DOWNLOAD_ALT), payment.getTo() + "\n" + payment.getToIBAN(), "Receiver");
		ListItem amount = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.DOLLAR), UIUtils.formatAmount(payment.getAmount()), "Amount");
		ListItem date = new ListItem(UIUtils.createTertiaryIcon(VaadinIcon.CALENDAR), UIUtils.formatDate(payment.getDate()), "Date");

		for (ListItem item : new ListItem[]{status, from, to, amount, date}) {
			item.setReverse(true);
			item.setWhiteSpace(CSSProperties.WhiteSpace.PRE_LINE);
		}

		Div details = new Div(status, from, to, amount, date);
		details.addClassName(LumoStyles.Padding.Vertical.S);
		return details;
	}

}
