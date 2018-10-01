package com.vaadin.starter.applayout.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.starter.applayout.ui.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class NaviItem extends Div {

    protected final String CLASS_NAME = "navi-item";

    private String text;
    private Class<? extends Component> navigationTarget;

    private Button expandCollapse;

    private boolean subItemsVisible;
    private List<NaviItem> subItems;

    private Icon down = new Icon(VaadinIcon.CARET_DOWN);
    private Icon up = new Icon(VaadinIcon.CARET_UP);

    private int level = 0;

    public NaviItem(VaadinIcon icon, String text, Class<? extends Component> navigationTarget) {
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

    public NaviItem(String text, Class<? extends Component> navigationTarget) {
        this(null, text, navigationTarget);
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
            // expandCollapse.setIcon(up = new Icon(VaadinIcon.CARET_DOWN));
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
            expandCollapse.setIcon(down);
            /*
            if (level == 0) {
                expandCollapse.setIcon(down);
            }
            */
        } else {
            setSubItemsVisible(false);
        }
    }

    private void setSubItemsVisible(boolean visible) {
        expandCollapse.setIcon(visible ? up : down);

        /*
        if (level == 0) {
            expandCollapse.setIcon(visible ? up : down);
        }
        if (visible) {
            removeClassName("not-visible");
        } else {
            addClassName("not-visible");
        }
        */

        subItems.forEach(item -> item.setVisible(visible));
        subItemsVisible = visible;
    }

    public boolean hasSubItems() {
        return subItems.size() > 0;
    }
}
