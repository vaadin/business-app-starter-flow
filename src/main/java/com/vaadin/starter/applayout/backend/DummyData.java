package com.vaadin.starter.applayout.backend;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DummyData {

    private static Map<Long, Report> REPORTS = new HashMap<>();
    private static Map<Long, Person> PERSONS = new HashMap<>();
    private static Map<Long, Statement> STATEMENTS = new HashMap<>();
    private static Map<Long, Balance> BALANCES = new HashMap<>();
    private static final String IMG_PATH = "frontend/styles/images/";
    private static Random random = new Random();

    private static String[] IBANS = new String[] { "AD1200012030200359100100", "AE070331234567890123456", "AL47212110090000000235698741", "AT611904300234573201", "AZ21NABZ00000000137010001944", "BA391290079401028494", "BE68539007547034", "BG80BNBG96611020345678", "BH67BMAG00001299123456", "BR1800360305000010009795493C1", "BY13NBRB3600900000002Z00AB00", "CH9300762011623852957", "CR05015202001026284066", "CY17002001280000001200527600", "CZ6508000000192000145399", "DE89370400440532013000", "DK5000400440116243", "DO28BAGR00000001212453611324", "EE382200221020145685", "ES9121000418450200051332", "FI2112345600000785", "FO6264600001631634", "FR1420041010050500013M02606", "GB29NWBK60161331926819", "GE29NB0000000101904917", "GI75NWBK000000007099453", "GL8964710001000206", "GR1601101250000000012300695", "GT82TRAJ01020000001210029690", "HR1210010051863000160", "HU42117730161111101800000000", "IE29AIBK93115212345678", "IL620108000000099999999", "IQ98NBIQ850123456789012", "IS140159260076545510730339", "IT60X0542811101000000123456", "JO94CBJO0010000000000131000302", "KW81CBKU0000000000001234560101", "KZ86125KZT5004100100", "LB62099900000001001901229114", "LC55HEMM000100010012001200023015", "LI21088100002324013AA", "LT121000011101001000", "LU280019400644750000", "LV80BANK0000435195001", "MC5811222000010123456789030", "MD24AG000225100013104168", "ME25505000012345678951", "MK07250120000058984", "MR1300020001010000123456753", "MT84MALT011000012345MTLCAST001S", "MU17BOMM0101101030300200000MUR", "NL91ABNA0417164300", "NO9386011117947", "PK36SCBL0000001123456702", "PL61109010140000071219812874", "PS92PALS000000000400123456702", "PT50000201231234567890154", "QA58DOHB00001234567890ABCDEFG", "RO49AAAA1B31007593840000", "RS35260005601001611379", "SA0380000000608010167519", "SC18SSCB11010000000000001497USD", "SE4550000000058398257466", "SI56263300012039086", "SK3112000000198742637541", "SM86U0322509800000000270100", "ST68000200010192194210112", "SV62CENR00000000000000700025", "TL380080012345678910157", "TN5910006035183598478831", "TR330006100519786457841326", "UA213996220000026007233566001", "VG96VPVG0000012345678901", "XK051212012345678906", "YY24KIHB12476423125915947930915268", "ZZ25VLQT382332233206588011313776421" };
    private static String[] COMPANIES = new String[] { "Brewer Holding Century Training", "Broadcast Electric", "Chemical General Development", "Digital Agricultural Dynamics", "Digital Cargo Semiconductor", "Education Game Realizations", "European Photographic", "Financial Supplies", "Genetics Leasing Mechanical", "Henderson Progressive Imperial Publishing", "Innovative Cyberbank", "Japanese Equipment Networks", "Kilobank", "McCray Mechanical Manufacturing Grocery", "Medical Telecommunications", "Microcom", "Navarro Horizon Gold Products", "Network Office Contractors", "Royal Psychomatics", "Sharp Network Sciences Healthcare", "Speciality Investments", "Tanner Progressive Photographic Healthcare", "Terrell Royal Vista Realizations", "Unlimited Integration", "Walters Soft", "Aeon Transportation Atomic", "African Development", "Atomic Unlimited Broadcasting", "Boyer Scientific Medical Unlimited", "Buck Motors", "Canadian Speciality Group", "Cargo Leasing Instructional", "Clark Industrial Semiconductors", "Engineered Open Sports", "European Chemical", "Fletcher Applied Aeon Power", "Fulton Fabrication Memory Brokerage", "Lang Unlimited Engineering", "Logistics Globosoft", "Medical Equipment", "Office Teledynamics", "Personal Unlimited Recreational", "Sawyer Technics", "Sharpe Genetinetics", "Spanish Unlimited Realizations", "Strong Wholesale Manufacturing", "Unlimited Education Training", "Villarreal Engineered Industrial Control", "Wade Logistics Specialized Frontiers", "Wilcox Dynamics" };
    private static String[] BANKS = new String[] { "Aegis Bank", "Kindred Credit Union", "Oculus Bank System", "Aspire Credit Union", "Meridian Bancorp", "Focus Trust", "Cloud Nine Financial Corp.", "Lifespark Banks", "Obelisk Corporation", "Principal Banks" };

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

        i = 0;
        PERSONS.put(i, new Person(i++, "Rolf", "Smeds", Person.Role.DESIGNER, "rolf@email.com", null, 10, LocalDate.now()));
        PERSONS.put(i, new Person(i++, "Haijian", "Wang", Person.Role.DEVELOPER, "haijian@email.com", null, 1, LocalDate.now()));
        PERSONS.put(i, new Person(i++, "Jaska", "Kemppainen", Person.Role.DESIGNER, "jaska@email.com", "jaska", 0, LocalDate.now()));
        PERSONS.put(i, new Person(i++, "Marcio", "Dantas", Person.Role.DEVELOPER, "marcio@email.com", null, 8, LocalDate.now()));
        PERSONS.put(i, new Person(i++, "Vesa", "Nieminen", Person.Role.DEVELOPER, "vesa@email.com", null, 0, LocalDate.now()));
        PERSONS.put(i, new Person(i++, "Susanna", "Laalo", Person.Role.MANAGER, "susanna@email.com", "susanna", 64, LocalDate.now()));
        PERSONS.put(i, new Person(i++, "Hannu", "Salonen", Person.Role.DESIGNER, "hannu@email.com", null, 4, LocalDate.now()));
        PERSONS.put(i, new Person(i++, "Juuso", "Kantonen", Person.Role.DESIGNER, "juuso@email.com", "juuso", 7, LocalDate.now()));

        for (i = 0; i < 20; i++) {
            Statement statement = new Statement(i, LocalDate.now().minusDays(random.nextInt(20)), getCompany(), 0.0, 0.0, Statement.Status.values()[random.nextInt(Statement.Status.values().length)], random.nextBoolean());
            if (random.nextBoolean()) {
                statement.setOutput(Double.valueOf(random.nextInt(20000)));
            } else {
                statement.setInput(Double.valueOf(random.nextInt(20000)));
            }
            STATEMENTS.put(i, statement);
        }


        for (i = 0; i < 20; i++) {
            BALANCES.put(i, new Balance(i, getBank(), getIban(), getCompany(), Double.valueOf(random.nextInt(20000)), LocalDate.now().minusDays(random.nextInt(20))));
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

    public static Collection<Balance> getBalances() {
        return BALANCES.values();
    }

    private static String getIban() {
        return IBANS[random.nextInt(IBANS.length)];
    }

    private static String getCompany() {
        return COMPANIES[random.nextInt(COMPANIES.length)];
    }

    private static String getBank() {
        return BANKS[random.nextInt(BANKS.length)];
    }

}
