package com.vaadin.starter.responsivelayoutgrid.backend;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DummyData {

    private static Map<Long, InitialCoinOffering> INITIAL_COIN_OFFERINGS = new HashMap<>();
    private static final String IMG_PATH = "frontend/styles/images/";

    private DummyData() {
    }

    static {
        long i = 0;

        INITIAL_COIN_OFFERINGS.put(i, new InitialCoinOffering(i++, IMG_PATH + "ambrosus.jpg", "Ambrosus",
                LocalDate.now().minusDays(2), LocalDate.now().plusDays(14), 15000.00));

        INITIAL_COIN_OFFERINGS.put(i, new InitialCoinOffering(i++, IMG_PATH + "cardstack.jpg", "Cardstack",
                LocalDate.now().minusDays(3), LocalDate.now().plusDays(4), 25000.00));

        INITIAL_COIN_OFFERINGS.put(i, new InitialCoinOffering(i++, IMG_PATH + "decentraland.png", "Decentraland",
                LocalDate.now().minusDays(5), LocalDate.now().plusDays(2), 5000.00));

        INITIAL_COIN_OFFERINGS.put(i, new InitialCoinOffering(i++, IMG_PATH + "jibrel-network.jpg", "Jibrel Network",
                LocalDate.now().minusDays(2), LocalDate.now().plusDays(1), 7500.00));

        INITIAL_COIN_OFFERINGS.put(i, new InitialCoinOffering(i++, IMG_PATH + "moeda.jpg", "Moeda",
                LocalDate.now().minusDays(1), LocalDate.now().plusDays(7), 150000.00));

        INITIAL_COIN_OFFERINGS.put(i, new InitialCoinOffering(i++, IMG_PATH + "omisego.jpg", "OmiseGo",
                LocalDate.now().minusDays(7), LocalDate.now().plusDays(2), 20000.00));

        INITIAL_COIN_OFFERINGS.put(i, new InitialCoinOffering(i++, IMG_PATH + "rightmesh.jpg", "RightMesh",
                LocalDate.now().minusDays(8), LocalDate.now().plusDays(8), 12500.00));
    }

    public static InitialCoinOffering get(Long id) {
        return INITIAL_COIN_OFFERINGS.get(id);
    }

    public static Collection<InitialCoinOffering> getAll() {
        return INITIAL_COIN_OFFERINGS.values();
    }

}
