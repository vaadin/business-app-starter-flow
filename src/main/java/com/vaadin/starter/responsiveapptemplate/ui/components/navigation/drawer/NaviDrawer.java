package com.vaadin.starter.responsiveapptemplate.ui.components.navigation.drawer;

import java.util.ArrayList;
import java.util.Collections;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.starter.responsiveapptemplate.ui.utils.UIUtils;

import elemental.json.JsonObject;

@HtmlImport("swipe-away.html")
public abstract class NaviDrawer extends Composite<Div>
        implements AfterNavigationObserver {

    private static final String CLASS_NAME = "navi-drawer";
    private static final String RAIL = "rail";
    private static final String OPEN = "open";

    private Div scrim;

    private Div mainContent;
    private TextField search;
    private Div scrollableArea;

    private Div naviList;
    private ArrayList<NaviLinkItem> items;

    private Button railButton;

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        UI ui = attachEvent.getUI();
        ui.getPage().executeJavaScript("window.addSwipeAway($0,$1,$2,$3)",
                mainContent.getElement(), this, "onSwipeAway",
                scrim.getElement());
    }

    @ClientCallable
    public void onSwipeAway(JsonObject data) {
        close();
    }

    public NaviDrawer() {
        getContent().setClassName(CLASS_NAME);

        initScrim();
        initMainContent();

        initHeader();
        initSearch();

        initScrollableArea();
        initNaviList();

        initFooter();
    }

    private void initScrim() {
        // Backdrop on small viewports
        scrim = UIUtils
                .createDiv(Collections.singleton(CLASS_NAME + "__scrim"));
        scrim.addClickListener(event -> close());
        getContent().add(scrim);
    }

    private void initMainContent() {
        mainContent = UIUtils
                .createDiv(Collections.singleton(CLASS_NAME + "__content"));
        getContent().add(mainContent);
    }

    private void initHeader() {
        mainContent.add(new BrandExpression());
    }

    private void initSearch() {
        search = new TextField();
        search.setPlaceholder("Search");
        search.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        search.addValueChangeListener(e -> search());
        mainContent.add(search);
    }

    private void search() {
        items.forEach(naviItem -> naviItem.setVisible(naviItem.getText()
                .toLowerCase().contains(search.getValue().toLowerCase())));
    }

    private void initScrollableArea() {
        scrollableArea = UIUtils
                .createDiv(Collections.singleton(CLASS_NAME + "__scroll-area"));
        mainContent.add(scrollableArea);
    }

    private void initNaviList() {
        // Wrapper for navigation items
        naviList = UIUtils
                .createDiv(Collections.singleton(CLASS_NAME + "__list"));
        scrollableArea.add(naviList);

        items = new ArrayList<>();
    }

    private void initFooter() {
        railButton = UIUtils.createSmallButton("Collapse",
                VaadinIcon.CHEVRON_LEFT_SMALL);
        railButton.addClassName(CLASS_NAME + "__footer");
        railButton.addClickListener(event -> toggleRailMode());
        mainContent.add(railButton);
    }

    private void toggleRailMode() {
        if (getElement().hasAttribute(RAIL)) {
            getElement().setAttribute(RAIL, false);
            railButton.setIcon(new Icon(VaadinIcon.CHEVRON_LEFT_SMALL));
            railButton.setText("Collapse");
        } else {
            getElement().setAttribute(RAIL, true);
            railButton.setIcon(new Icon(VaadinIcon.CHEVRON_RIGHT_SMALL));
            railButton.setText("Expand");
        }
    }

    public void toggle() {
        if (getElement().hasAttribute(OPEN)) {
            close();
        } else {
            open();
        }
    }

    private void open() {
        getElement().setAttribute(OPEN, true);
    }

    private void close() {
        getElement().setAttribute(OPEN, false);
        applyIOS122Workaround();
    }

    private void applyIOS122Workaround() {
        // iOS 12.2 sometimes fails to animate the menu away.
        // It should be gone after 240ms
        // This will make sure it disappears even when the browser fails.
        getUI().get().getPage().executeJavaScript(
                "var originalStyle = getComputedStyle($0).transitionProperty;" //
                        + "setTimeout(function() {$0.style.transitionProperty='padding'; requestAnimationFrame(function() {$0.style.transitionProperty=originalStyle})}, 250);",
                mainContent.getElement());
    }

    protected void addNaviItem(NaviLinkItem item) {
        naviList.add(item);
        items.add(item);
    }

    protected void addNaviItem(NaviLinkItem parent, NaviLinkItem item) {
        parent.addSubItem(item);
        addNaviItem(item);
    }

    public abstract NaviLinkItem addNaviItem(VaadinIcon icon, String text,
            Class<? extends Component> navigationTarget);

    public abstract NaviLinkItem addNaviItem(Image image, String text,
            Class<? extends Component> navigationTarget);

    public abstract NaviLinkItem addNaviItem(String path, String text,
            Class<? extends Component> navigationTarget);

    public abstract NaviLinkItem addNaviItem(NaviLinkItem parent, String text,
            Class<? extends Component> navigationTarget);

    public ArrayList<NaviLinkItem> getNaviItems() {
        return items;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        close();
    }
}
