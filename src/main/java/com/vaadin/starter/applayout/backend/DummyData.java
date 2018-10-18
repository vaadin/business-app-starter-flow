package com.vaadin.starter.applayout.backend;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DummyData {

    private static final Map<Long, Report> REPORTS = new HashMap<>();
    private static final Map<Long, Person> PERSONS = new HashMap<>();
    private static final Map<Long, Transaction> TRANSACTIONS = new HashMap<>();
    private static final Map<Long, BankAccount> BALANCES = new HashMap<>();
    private static final Map<Long, Payment> PAYMENTS = new HashMap<>();

    private static final String IMG_PATH = "frontend/styles/images/";
    private static Random random = new Random();

    private static String[] IBANS = new String[]{"AD12 0001 2030 2003 5910 0100", "AE07 0331 2345 6789 0123 456", "AL47 2121 1009 0000 0002 3569 8741", "AT61 1904 3002 3457 3201", "AZ21 NABZ 0000 0000 1370 1000 1944", "BA39 1290 0794 0102 8494", "BE68 5390 0754 7034", "BG80 BNBG 9661 1020 3456 78", "BH67 BMAG 0000 1299 1234 56", "BR18 0036 0305 0000 1000 9795 493C 1", "BY13 NBRB 3600 9000 0000 2Z00 AB00", "CH93 0076 2011 6238 5295 7", "CR05 0152 0200 1026 2840 66", "CY17 0020 0128 0000 0012 0052 7600", "CZ65 0800 0000 1920 0014 5399", "DE89 3704 0044 0532 0130 00", "DK50 0040 0440 1162 43", "DO28 BAGR 0000 0001 2124 5361 1324", "EE38 2200 2210 2014 5685", "ES91 2100 0418 4502 0005 1332", "FI21 1234 5600 0007 85", "FO62 6460 0001 6316 34", "FR14 2004 1010 0505 0001 3M02 606", "GB29 NWBK 6016 1331 9268 19", "GE29 NB00 0000 0101 9049 17", "GI75 NWBK 0000 0000 7099 453", "GL89 6471 0001 0002 06", "GR16 0110 1250 0000 0001 2300 695", "GT82 TRAJ 0102 0000 0012 1002 9690", "HR12 1001 0051 8630 0016 0", "HU42 1177 3016 1111 1018 0000 0000", "IE29 AIBK 9311 5212 3456 78", "IL62 0108 0000 0009 9999 999", "IQ98 NBIQ 8501 2345 6789 012", "IS14 0159 2600 7654 5510 7303 39", "IT60 X054 2811 1010 0000 0123 456", "JO94 CBJO 0010 0000 0000 0131 0003 02", "KW81 CBKU 0000 0000 0000 1234 5601 01", "KZ86 125K ZT50 0410 0100", "LB62 0999 0000 0001 0019 0122 9114", "LC55 HEMM 0001 0001 0012 0012 0002 3015", "LI21 0881 0000 2324 013A A", "LT12 1000 0111 0100 1000", "LU28 0019 4006 4475 0000", "LV80 BANK 0000 4351 9500 1", "MC58 1122 2000 0101 2345 6789 030", "MD24 AG00 0225 1000 1310 4168", "ME25 5050 0001 2345 6789 51", "MK07 2501 2000 0058 984", "MR13 0002 0001 0100 0012 3456 753", "MT84 MALT 0110 0001 2345 MTLC AST0 01S", "MU17 BOMM 0101 1010 3030 0200 000M UR", "NL91 ABNA 0417 1643 00", "NO93 8601 1117 947", "PK36 SCBL 0000 0011 2345 6702", "PL61 1090 1014 0000 0712 1981 2874", "PS92 PALS 0000 0000 0400 1234 5670 2", "PT50 0002 0123 1234 5678 9015 4", "QA58 DOHB 0000 1234 5678 90AB CDEF G", "RO49 AAAA 1B31 0075 9384 0000", "RS35 2600 0560 1001 6113 79", "SA03 8000 0000 6080 1016 7519", "SC18 SSCB 1101 0000 0000 0000 1497 USD", "SE45 5000 0000 0583 9825 7466", "SI56 2633 0001 2039 086", "SK31 1200 0000 1987 4263 7541", "SM86 U032 2509 8000 0000 0270 100", "ST68 0002 0001 0192 1942 1011 2", "SV62 CENR 0000 0000 0000 0070 0025", "TL38 0080 0123 4567 8910 157", "TN59 1000 6035 1835 9847 8831", "TR33 0006 1005 1978 6457 8413 26", "UA21 3996 2200 0002 6007 2335 6600 1", "VG96 VPVG 0000 0123 4567 8901", "XK05 1212 0123 4567 8906", "YY24 KIHB 1247 6423 1259 1594 7930 9152 68", "ZZ25 VLQT 3823 3223 3206 5880 1131 3776 421"};
    private static String[] COMPANIES = new String[]{"Brewer Holding Century Training", "Broadcast Electric", "Chemical General Development", "Digital Agricultural Dynamics", "Digital Cargo Semiconductor", "Education Game Realizations", "European Photographic", "Financial Supplies", "Genetics Leasing Mechanical", "Henderson Progressive Imperial Publishing", "Innovative Cyberbank", "Japanese Equipment Networks", "Kilobank", "McCray Mechanical Manufacturing Grocery", "Medical Telecommunications", "Microcom", "Navarro Horizon Gold Products", "Network Office Contractors", "Royal Psychomatics", "Sharp Network Sciences Healthcare", "Speciality Investments", "Tanner Progressive Photographic Healthcare", "Terrell Royal Vista Realizations", "Unlimited Integration", "Walters Soft", "Aeon Transportation Atomic", "African Development", "Atomic Unlimited Broadcasting", "Boyer Scientific Medical Unlimited", "Buck Motors", "Canadian Speciality Group", "Cargo Leasing Instructional", "Clark Industrial Semiconductors", "Engineered Open Sports", "European Chemical", "Fletcher Applied Aeon Power", "Fulton Fabrication Memory Brokerage", "Lang Unlimited Engineering", "Logistics Globosoft", "Medical Equipment", "Office Teledynamics", "Personal Unlimited Recreational", "Sawyer Technics", "Sharpe Genetinetics", "Spanish Unlimited Realizations", "Strong Wholesale Manufacturing", "Unlimited Education Training", "Villarreal Engineered Industrial Control", "Wade Logistics Specialized Frontiers", "Wilcox Dynamics"};
    private static String[] BANKS = new String[]{"Aegis Bank", "Kindred Credit Union", "Oculus Bank System", "Aspire Credit Union", "Meridian Bancorp", "Focus Trust", "Cloud Nine Financial Corp.", "Lifespark Banks", "Obelisk Corporation", "Principal Banks"};
    private static String[] FIRST_NAMES = new String[]{"Tereasa", "Latina", "Flo", "Harriett", "Tiffiny", "Darren", "Chau", "Annabell", "Laurena", "Janene", "Arline", "Everett", "Beatris", "Josefina", "Shera", "Traci", "Avery", "Zulma", "Cheryle", "Yuko", "Tish", "Karena", "Junie", "Francene", "Sherice", "Jacob", "Elois", "Lamonica", "Carly", "Sybil", "Adriene", "Arielle", "Patty", "Dori", "Emerald", "Edda", "Bao", "Kathrin", "Ken", "Lael", "Chia", "Larisa", "Raymond", "Judith", "Bridget", "Tana", "Windy", "Fe", "Bernardina", "Cherilyn"};
    private static String[] LAST_NAMES = new String[]{"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Robinson", "Clark", "Rodriguez", "Lewis", "Lee", "Walker", "Hall", "Allen", "Young", "Hernandez", "King", "Wright", "Lopez", "Hill", "Scott", "Green", "Adams", "Baker", "Gonzalez", "Nelson", "Carter", "Mitchell", "Perez", "Roberts", "Turner", "Phillips", "Campbell", "Parker", "Evans", "Edwards", "Collins"};

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

        for (i = 0; i < 40; i++) {
            String firstName = getFirstName();
            PERSONS.put(i, new Person(i, firstName, getLastName(), getRole(), firstName.toLowerCase() + "@email.com", random.nextBoolean(), random.nextInt(500000), getDate()));
        }

        for (i = 0; i < 40; i++) {
            TRANSACTIONS.put(i, new Transaction(i, getTransactionStatus(), getCompany(), getIBAN(), getAmount(), random.nextBoolean(), getDate()));
        }

        for (i = 0; i < 40; i++) {
            BALANCES.put(i, new BankAccount(i, getBank(), getIBAN(), getCompany(), getAmount(), getDate()));
        }

        for (i = 0; i < 40; i++) {
            PAYMENTS.put(i, new Payment(getPaymentStatus(), getCompany(), getIBAN(), getBank(), getIBAN(), getPositiveAmount(), getDate()));
        }
    }

    public static Report getReport(Long id) {
        return REPORTS.get(id);
    }

    public static Collection<Report> getReports() {
        return REPORTS.values();
    }

    public static Collection<Person> getPersons() {
        return PERSONS.values();
    }

    public static Collection<Transaction> getTransactions() {
        return TRANSACTIONS.values();
    }

    public static Collection<BankAccount> getBalances() {
        return BALANCES.values();
    }

    public static Collection<Payment> getPayments() {
        return PAYMENTS.values();
    }

    private static String getIBAN() {
        return IBANS[random.nextInt(IBANS.length)];
    }

    public static String getCompany() {
        return COMPANIES[random.nextInt(COMPANIES.length)];
    }

    private static String getBank() {
        return BANKS[random.nextInt(BANKS.length)];
    }

    private static Transaction.Status getTransactionStatus() {
        return Transaction.Status.values()[random.nextInt(Transaction.Status.values().length)];
    }

    private static Payment.Status getPaymentStatus() {
        return Payment.Status.values()[random.nextInt(Payment.Status.values().length)];
    }

    public static Double getAmount() {
        return random.nextBoolean() ? getNegativeAmount() : getPositiveAmount();
    }

    public static Double getPositiveAmount() {
        return random.nextDouble() * 20000;
    }

    public static Double getNegativeAmount() {
        return random.nextDouble() * -20000;
    }

    public static int getRandomNumber(int bound) {
        return random.nextInt(bound);
    }

    public static LocalDate getDate() {
        return LocalDate.now().minusDays(random.nextInt(20));
    }

    public static String getFirstName() {
        return FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
    }

    public static String getLastName() {
        return LAST_NAMES[random.nextInt(LAST_NAMES.length)];
    }

    private static Person.Role getRole() {
        return Person.Role.values()[random.nextInt(Person.Role.values().length)];
    }
}
