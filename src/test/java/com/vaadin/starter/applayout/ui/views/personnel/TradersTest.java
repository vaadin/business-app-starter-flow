package com.vaadin.starter.applayout.ui.views.personnel;

import com.github.karibu.testing.v10.MockVaadin;
import com.github.karibu.testing.v10.Routes;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.starter.applayout.backend.DummyData;
import com.vaadin.starter.applayout.backend.Person;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static com.github.karibu.testing.v10.GridKt.*;
import static com.github.karibu.testing.v10.LocatorJ.*;

/**
 * @author mavi
 */
class TradersTest {
    @BeforeEach
    public void mockVaadin() {
        MockVaadin.setup(new Routes().autoDiscoverViews("com.vaadin.starter.applayout"));
        UI.getCurrent().navigate("traders");
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
        _get(Traders.class);
    }

    @Test
    public void testGridShowsTraders() {
        final Grid<Person> traderGrid = _get(Grid.class);
        assertEquals(DummyData.getPersons().stream().filter(it -> it.getRole() == Person.Role.TRADER).count(), _size(traderGrid));
    }
}
