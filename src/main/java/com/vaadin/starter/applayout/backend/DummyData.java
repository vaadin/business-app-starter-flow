package com.vaadin.starter.applayout.backend;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DummyData {

    private static Map<Long, Report> REPORTS = new HashMap<>();
    private static final String IMG_PATH = "frontend/styles/images/";

    private DummyData() {
    }

    static {
        long i = 0;

        REPORTS.put(i, new Report(i++, IMG_PATH + "sample-logo1.jpg", "Woocero Ltd.",
                LocalDate.now().minusDays(2), LocalDate.now().plusDays(14), 15000.00, "EUR"));

        REPORTS.put(i, new Report(i++, IMG_PATH + "sample-logo1.jpg", "Woocero Ltd.",
                LocalDate.now().minusDays(3), LocalDate.now().plusDays(4), 25000.00, "EUR"));

        REPORTS.put(i, new Report(i++, IMG_PATH + "sample-logo2.jpg", "Cemoco Exports",
                LocalDate.now().minusDays(5), LocalDate.now().plusDays(2), 5000.00, "EUR"));

        REPORTS.put(i, new Report(i++, IMG_PATH + "sample-logo1.jpg", "Woocero Ltd.",
                LocalDate.now().minusDays(2), LocalDate.now().plusDays(1), 7500.00, "EUR"));

        REPORTS.put(i, new Report(i++, IMG_PATH + "sample-logo2.jpg", "Cemoco Exports",
                LocalDate.now().minusDays(1), LocalDate.now().plusDays(7), 150000.00, "EUR"));

        REPORTS.put(i, new Report(i++, IMG_PATH + "sample-logo2.jpg", "Cemoco Exports",
                LocalDate.now().minusDays(7), LocalDate.now().plusDays(2), 20000.00, "EUR"));

        REPORTS.put(i, new Report(i++, IMG_PATH + "sample-logo2.jpg", "Cemoco Exports",
                LocalDate.now().minusDays(8), LocalDate.now().plusDays(8), 12500.00, "EUR"));
    }

    public static Report get(Long id) {
        return REPORTS.get(id);
    }

    public static Collection<Report> getAll() {
        return REPORTS.values();
    }

}
