import lifechoice.ClientAge;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClientAgeTest {

    @Test
    public void getAgeNextQuarterBirthdayDayOnStartDate() {
        DateTime policyStartDate = stringToDate("21/02/2018");
        DateTime dob = stringToDate("21/02/1975");
        ClientAge anq  = new ClientAge(policyStartDate, dob);

        assertEquals(43.25, anq.getAgeForBenefitTerm(), 0.00);
    }

    @Test
    public void getAgeNextQuarterBirthdayDayAfterStartDate() {
        DateTime policyStartDate = stringToDate("21/02/2018");
        DateTime dob = stringToDate("22/01/1971");
        ClientAge anq  = new ClientAge(policyStartDate, dob);
        assertEquals(47.25, anq.getAgeForBenefitTerm(), 0.00);
    }

    @Test
    public void getAgeNextQuarterBirthdayAfterStartDate() {
        DateTime policyStartDate = stringToDate("21/02/2018");
        DateTime dob = stringToDate("01/05/1971");
        ClientAge anq  = new ClientAge(policyStartDate, dob);
        assertEquals(47, anq.getAgeForBenefitTerm(), 0.00);
    }

    @Test
    public void getAgeNextQuarterBirthdayBeforeStartDate() {
        DateTime policyStartDate = stringToDate("21/02/2018");
        DateTime dob = stringToDate("01/01/1971");
        ClientAge anq  = new ClientAge(policyStartDate, dob);
        assertEquals(47.25, anq.getAgeForBenefitTerm(), 0.00);
    }

    @Test
    public void getAgeNextQuarterThreeMonthsBetweenBirthdayAndStartDate() {
        DateTime policyStartDate = stringToDate("21/02/2018");
        DateTime dob = stringToDate("01/06/1971");
        ClientAge anq  = new ClientAge(policyStartDate, dob);
        assertEquals(46.75, anq.getAgeForBenefitTerm(), 0.00);

        dob = stringToDate("23/06/1971");
        anq  = new ClientAge(policyStartDate, dob);
        assertEquals(46.75, anq.getAgeForBenefitTerm(), 0.00);
    }


    @Test
    public void getAgeNextQuarterSixMonthsBetweenBirthdayAndStartDate() {
        DateTime policyStartDate = stringToDate("21/02/2018");
        DateTime dob = stringToDate("01/09/1971");
        ClientAge anq  = new ClientAge(policyStartDate, dob);
        assertEquals(46.50, anq.getAgeForBenefitTerm(), 0.00);
    }

    private  DateTime stringToDate(String dte) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        return formatter.parseDateTime(dte);
    }
}