import lifechoice.LifeAge;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LifeAgeTest {
    private LifeAge lifeAge;

    @Before
    public void setUp() {
        DateTime policyStartDate = stringToDate("21/02/2018");
        DateTime dob = stringToDate("22/01/1971");
        lifeAge = new LifeAge(policyStartDate, dob);
    }

    @Test
    public void getAgeNextQuarter() {
        assertEquals(47.25, lifeAge.getAgeForBenefitTerm(), 0.00);
    }

    @Test
    public void getAgeNextHalf() {
        assertEquals(47.5, lifeAge.getAgeInSecondQuarterAfterStartDate(), 0.00);
    }

    @Test
    public void getAgeNow() {
        assertEquals(47, lifeAge.getAgeAtStartDate());
    }

    @Test
    public void getAgeNext() {
        assertEquals(48, lifeAge.getAgeNextInQuarterAfterStartDate());
    }

    @Test
    public void getAgeFraction() {
        assertEquals(.25, lifeAge.getAgeFraction(), 0.00);
    }

    @Test
    public void getFractionOfYear() {
        assertEquals(0.9166666666666666, lifeAge.getFractionOfYear(), 0.00001);
    }

    private  DateTime stringToDate(String dte) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        return formatter.parseDateTime(dte);
    }
}