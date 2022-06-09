package com.vaadin.starter.business.it;

import com.vaadin.flow.component.html.testbench.DivElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HomeIT extends AbstractIT {

    @Before
    public void init() {
        String url = getBaseURL().replace(super.getBaseURL(),
                super.getBaseURL() + "/");
        getDriver().get(url);
    }

    @Test
    public void homeView() {
        DivElement homeView = $(DivElement.class).id("home");
        Assert.assertNotNull(homeView);
    }

}
