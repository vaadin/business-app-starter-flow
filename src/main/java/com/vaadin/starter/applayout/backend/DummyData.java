package com.vaadin.starter.applayout.backend;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DummyData {

    private static Map<Long, Report> REPORTS = new HashMap<>();
    private static Map<Long, Person> PERSONS = new HashMap<>();
    private static final String IMG_PATH = "frontend/styles/images/";

    private DummyData() {
    }

    static {
        long i = 0;
        REPORTS.put(i, new Report(i++, IMG_PATH + "sample-logo1.jpg", "Woocero Ltd.", LocalDate.now().minusDays(2), LocalDate.now().plusDays(14), 15000.00, "EUR"));
        REPORTS.put(i, new Report(i++, IMG_PATH + "sample-logo1.jpg", "Woocero Ltd.", LocalDate.now().minusDays(3), LocalDate.now().plusDays(4), 25000.00, "EUR"));
        REPORTS.put(i, new Report(i++, IMG_PATH + "sample-logo2.jpg", "Cemoco Exports", LocalDate.now().minusDays(5), LocalDate.now().plusDays(2), 5000.00, "EUR"));
        REPORTS.put(i, new Report(i++, IMG_PATH + "sample-logo1.jpg", "Woocero Ltd.", LocalDate.now().minusDays(2), LocalDate.now().plusDays(1), 7500.00, "EUR"));
        REPORTS.put(i, new Report(i++, IMG_PATH + "sample-logo2.jpg", "Cemoco Exports", LocalDate.now().minusDays(1), LocalDate.now().plusDays(7), 150000.00, "EUR"));
        REPORTS.put(i, new Report(i++, IMG_PATH + "sample-logo2.jpg", "Cemoco Exports", LocalDate.now().minusDays(7), LocalDate.now().plusDays(2), 20000.00, "EUR"));
        REPORTS.put(i, new Report(i++, IMG_PATH + "sample-logo2.jpg", "Cemoco Exports", LocalDate.now().minusDays(8), LocalDate.now().plusDays(8), 12500.00, "EUR"));

        long j = 0;
        PERSONS.put(j, new Person(j++, "Rolf", "Smeds", Person.Role.DESIGNER, "rolf@email.com", null, 10, LocalDate.now()));
        PERSONS.put(j, new Person(j++, "Haijian", "Wang", Person.Role.DEVELOPER, "haijian@email.com", null, 1, LocalDate.now()));
        PERSONS.put(j, new Person(j++, "Jaska", "Kemppainen", Person.Role.DESIGNER, "jaska@email.com", "jaska", 0, LocalDate.now()));
        PERSONS.put(j, new Person(j++, "Marcio", "Dantas", Person.Role.DEVELOPER, "marcio@email.com", null, 8, LocalDate.now()));
        PERSONS.put(j, new Person(j++, "Vesa", "Nieminen", Person.Role.DEVELOPER, "vesa@email.com", null, 0, LocalDate.now()));
        PERSONS.put(j, new Person(j++, "Susanna", "Laalo", Person.Role.MANAGER, "susanna@email.com", "susanna", 64, LocalDate.now()));
        PERSONS.put(j, new Person(j++, "Hannu", "Salonen", Person.Role.DESIGNER, "hannu@email.com", null, 4, LocalDate.now()));
        PERSONS.put(j, new Person(j++, "Juuso", "Kantonen", Person.Role.DESIGNER, "juuso@email.com", "juuso", 7, LocalDate.now()));
    }

    public static Report getReport(Long id) {
        return REPORTS.get(id);
    }

    public static Collection<Report> getReports() {
        return REPORTS.values();
    }

    public static Person getPerson(Long id) {
        return PERSONS.get(id);
    }

    public static Collection<Person> getPersons() {
        return PERSONS.values();
    }

}
