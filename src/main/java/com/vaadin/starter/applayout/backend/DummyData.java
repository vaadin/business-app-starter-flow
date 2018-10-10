package com.vaadin.starter.applayout.backend;

import javax.swing.plaf.nimbus.State;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DummyData {

    private static Map<Long, Report> REPORTS = new HashMap<>();
    private static Map<Long, Person> PERSONS = new HashMap<>();
    private static Map<Long, Statement> STATEMENTS = new HashMap<>();
    private static final String IMG_PATH = "frontend/styles/images/";
    private static Random random = new Random();

    private DummyData() {
    }

    static {
        long i = 0;
        REPORTS.put(i, new Report(i++, IMG_PATH + "sample-logo1.jpg", "Woocero Ltd.", LocalDate.now().minusDays(2), LocalDate.now().plusDays(14), 120000.00, "EUR"));
        REPORTS.put(i, new Report(i++, IMG_PATH + "sample-logo1.jpg", "Woocero Ltd.", LocalDate.now().minusDays(3), LocalDate.now().plusDays(4), 220000.00, "EUR"));
        REPORTS.put(i, new Report(i++, IMG_PATH + "sample-logo2.jpg", "Cemoco Exports", LocalDate.now().minusDays(5), LocalDate.now().plusDays(2), 20000.00, "EUR"));
        REPORTS.put(i, new Report(i++, IMG_PATH + "sample-logo1.jpg", "Woocero Ltd.", LocalDate.now().minusDays(2), LocalDate.now().plusDays(1), 7500.00, "EUR"));
        REPORTS.put(i, new Report(i++, IMG_PATH + "sample-logo2.jpg", "Cemoco Exports", LocalDate.now().minusDays(1), LocalDate.now().plusDays(7), 1200000.00, "EUR"));
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

        long k = 0;
        for (String sender : new String[] { "Brewer Holding Century Training", "Broadcast Electric", "Chemical General Development", "Digital Agricultural Dynamics", "Digital Cargo Semiconductor", "Education Game Realizations", "European Photographic", "Financial Supplies", "Genetics Leasing Mechanical", "Henderson Progressive Imperial Publishing", "Innovative Cyberbank", "Japanese Equipment Networks", "Kilobank", "Mccray Mechanical Manufacturing Grocery", "Medical Telecommunications", "Microcom", "Navarro Horizon Gold Products", "Network Office Contractors", "Royal Psychomatics", "Sharp Network Sciences Healthcare", "Speciality Investments", "Tanner Progressive Photographic Healthcare", "Terrell Royal Vista Realizations", "Unlimited Integration", "Walters Soft" }) {
            STATEMENTS.put(k, new Statement(k++, LocalDate.now().minusDays(random.nextInt(20)), sender, Double.valueOf(random.nextInt(20000)), Statement.Status.values()[new Random().nextInt(Statement.Status.values().length)], random.nextBoolean()));
        }
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

    public static Statement getStatement(Long id) {
        return STATEMENTS.get(id);
    }

    public static Collection<Statement> getStatements() {
        return STATEMENTS.values();
    }

}
