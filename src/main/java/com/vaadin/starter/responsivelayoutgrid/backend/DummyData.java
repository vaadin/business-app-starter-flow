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
		INITIAL_COIN_OFFERINGS.put(i, new InitialCoinOffering(i++, IMG_PATH + "ambrosus.jpg", "Ambrosus", LocalDate.now(), LocalDate.now(), 5000.00));
		INITIAL_COIN_OFFERINGS.put(i, new InitialCoinOffering(i++, IMG_PATH + "cardstack.jpg", "Cardstack", LocalDate.now(), LocalDate.now(), 5000.00));
		INITIAL_COIN_OFFERINGS.put(i, new InitialCoinOffering(i++, IMG_PATH + "decentraland.png", "Decentraland", LocalDate.now(), LocalDate.now(), 5000.00));
		INITIAL_COIN_OFFERINGS.put(i, new InitialCoinOffering(i++, IMG_PATH + "jibrel-network.jpg", "Jibrel Network", LocalDate.now(), LocalDate.now(), 5000.00));
		INITIAL_COIN_OFFERINGS.put(i, new InitialCoinOffering(i++, IMG_PATH + "moeda.jpg", "Moeda", LocalDate.now(), LocalDate.now(), 5000.00));
		INITIAL_COIN_OFFERINGS.put(i, new InitialCoinOffering(i++, IMG_PATH + "omisego.jpg", "OmiseGo", LocalDate.now(), LocalDate.now(), 5000.00));
		INITIAL_COIN_OFFERINGS.put(i, new InitialCoinOffering(i++, IMG_PATH + "rightmesh.jpg", "RightMesh", LocalDate.now(), LocalDate.now(), 5000.00));
	}

	public static InitialCoinOffering get(Long id) {
		return INITIAL_COIN_OFFERINGS.get(id);
	}

	public static Collection<InitialCoinOffering> getAll() {
		return INITIAL_COIN_OFFERINGS.values();
	}

}
