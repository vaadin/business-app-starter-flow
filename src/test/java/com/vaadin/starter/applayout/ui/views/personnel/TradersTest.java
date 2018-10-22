package com.vaadin.starter.applayout.ui.views.personnel;

import com.github.karibu.testing.v10.MockVaadin;
import com.github.karibu.testing.v10.Routes;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.starter.applayout.backend.DummyData;
import com.vaadin.starter.applayout.backend.Person;
import com.vaadin.starter.applayout.ui.AppLayoutUI;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static com.github.karibu.testing.v10.GridKt.*;
import static com.github.karibu.testing.v10.LocatorJ.*;

/**
 * @author mavi
 */
class ManagersTest {
    @BeforeEach
    public void mockVaadin() {
        MockVaadin.setup(new Routes().autoDiscoverViews("com.vaadin.starter.applayout"), AppLayoutUI::new);
        UI.getCurrent().navigate("managers");
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
        _get(Managers.class);
    }

    @Test
    public void testGridShowsManagers() {
        final Grid<Person> traderGrid = _get(Grid.class);
        assertEquals(DummyData.getPersons().stream().filter(it -> it.getRole() == Person.Role.MANAGER).count(), _size(traderGrid));
    }
}
