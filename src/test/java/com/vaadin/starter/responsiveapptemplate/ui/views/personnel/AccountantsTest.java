package com.vaadin.starter.responsiveapptemplate.ui.views.personnel;

import com.github.mvysny.kaributesting.v10.MockVaadin;
import com.github.mvysny.kaributesting.v10.Routes;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.starter.responsiveapptemplate.backend.DummyData;
import com.vaadin.starter.responsiveapptemplate.backend.Person;
import com.vaadin.starter.responsiveapptemplate.ui.AppTemplateUI;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.mvysny.kaributesting.v10.GridKt._size;
import static com.github.mvysny.kaributesting.v10.LocatorJ._get;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author mavi
 */
class AccountantsTest {
    @BeforeEach
    public void mockVaadin() {
        MockVaadin.setup(new Routes().autoDiscoverViews("com.vaadin.starter.responsiveapptemplate"), AppTemplateUI::new);
        UI.getCurrent().navigate("accountants");
    }

    @AfterEach
    public void tearDownVaadin() {
        MockVaadin.tearDown();
    }

    /**
     * Smoke test - just navigate to the traders view.
     */
    @Test
    public void smokeTest() {
        _get(Accountants.class);
    }

    @Test
    public void testGridShowsAccountants() {
        final Grid<Person> accountantGrid = _get(Grid.class);
        assertEquals(DummyData.getPersons().stream().filter(it -> it.getRole() == Person.Role.ACCOUNTANT).count(), _size(accountantGrid));
    }
}