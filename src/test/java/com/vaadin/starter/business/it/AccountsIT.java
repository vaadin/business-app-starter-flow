package com.vaadin.starter.business.it;

import com.vaadin.flow.component.grid.testbench.GridElement;
import com.vaadin.flow.component.grid.testbench.GridTHTDElement;
import com.vaadin.testbench.TestBenchElement;
import org.junit.Before;
import org.junit.Test;

public class AccountsIT extends AbstractIT {

    @Before
    public void init() {
        String url = getBaseURL().replace(super.getBaseURL(),
                super.getBaseURL() + "/accounts");
        getDriver().get(url);
    }

    @Test
    public void accountDetailsCorrect() {
        GridElement accountsGrid = $(GridElement.class).id("accounts");

        GridTHTDElement availability = accountsGrid.getCell(0, 3);
        String gridViewAvailability = availability.getText();

        accountsGrid.getCell(0, 0).click();

        TestBenchElement availabilityDetails = $(TestBenchElement.class)
                .id("availability").$("label").withAttributeContainingWord("class", "h2")
                .first();
        assertNumbers(gridViewAvailability, availabilityDetails.getText());
    }

}
