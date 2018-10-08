package com.vaadin.starter.applayout.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.server.AbstractStreamResource;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.startup.FakeBrowser;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class NaviItem extends Div {

    protected final String CLASS_NAME = "navi-item";

    private String text;
    private Class<? extends Component> navigationTarget;

    private Button expandCollapse;

    private boolean subItemsVisible;
    private List<NaviItem> subItems;

    private Icon down = new Icon(VaadinIcon.PLUS);
    private Icon up = new Icon(VaadinIcon.MINUS);

    private int level = 0;

    public NaviItem(String text, Class<? extends Component> navigationTarget) {
        setClassName(CLASS_NAME);

        this.text = text;
        this.navigationTarget = navigationTarget;

        expandCollapse = UIUtils.createSmallTertiaryIconButton(up);
        expandCollapse.setVisible(false);
        expandCollapse.addClickListener(event -> {
            setSubItemsVisible(!subItemsVisible);
        });
        add(expandCollapse);

        subItemsVisible = true;
        subItems = new ArrayList<>();

        setLevel(0);
    }

    public void addSubItem(NaviItem item) {
        if (!expandCollapse.isVisible()) {
            expandCollapse.setVisible(true);
        }
        item.setLevel(getLevel() + 1);
        subItems.add(item);
    }

    public void setLevel(int level) {
        this.level = level;
        if (level > 0) {
            getElement().setAttribute("level", Integer.toString(level));
            expandCollapse.setIcon(up = new Icon(VaadinIcon.CARET_DOWN));
        }
    }

    public int getLevel() {
        return level;
    }

    public String getText() {
        return text;
    }

    public Class<? extends Component> getNavigationTarget() {
        return navigationTarget;
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);

        // If true, we only update the icon. If false, we hide all the sub items.
        if (visible) {
            if (level == 0) {
                expandCollapse.setIcon(down);
            }
        } else {
            setSubItemsVisible(visible);
        }
    }

    private void setSubItemsVisible(boolean visible) {
        if (level == 0) {
            expandCollapse.setIcon(visible ? up : down);
        }
        if (visible) {
            removeClassName("not-visible");
        } else {
            addClassName("not-visible");
        }
        subItems.forEach(item -> item.setVisible(visible));
        subItemsVisible = visible;
    }

    public boolean hasSubItems() {
        return subItems.size() > 0;
    }

    protected String readFile(String path, Charset encoding) throws IOException {
        InputStream resourceAsStream = VaadinService.getCurrent().getResourceAsStream("frontend://" + path, FakeBrowser.getEs6(), null);
        byte[] bytes = resourceAsStream.readAllBytes();
        return new String(bytes, encoding);
    }

    protected Element createSVGElement(String content) {
        Div svg = new Div();
        svg.addClassName(CLASS_NAME + "__svg-container");
        svg.getElement().setProperty("innerHTML", content);
        return svg.getElement();
    }

}
