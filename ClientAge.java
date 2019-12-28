package lifechoice;

import org.joda.time.DateTime;
import org.joda.time.Months;

public class ClientAge {
    private final DateTime startDate;
    private final DateTime dateOfBirth;

    public ClientAge(DateTime startDate, DateTime dateOfBirth) {
        this.startDate = startDate;
        this.dateOfBirth = dateOfBirth;
    }

    public double getAgeForBenefitTerm() {
        DateTime nextQuarter = startDate.plusMonths(3);
        Months months = Months.monthsBetween(dateOfBirth, nextQuarter);
        double ageNextQuarter = months.getMonths() / 12.0;
        ageNextQuarter = toNearestQuarter(ageNextQuarter);
        return ageNextQuarter;
    }

    private double toNearestQuarter(double years) {
        if (isWholeNumber(years) || isExactQuarter(years))
            return years;
        else
            return roundDownToNearestQuarter(years);
    }

    private boolean isWholeNumber(double number) {
        return Math.ceil(number) == Math.floor(number);
    }

    private boolean isExactQuarter(double number) {
        return number == (Math.round(number * 4) / 4f);
    }

    private double roundDownToNearestQuarter(double value) {
        double roundedUpYears = Math.round(value * 4) / 4f;
        double roundedDownYears = roundedUpYears;
        if (roundedUpYears - value > 0.00)
            roundedDownYears -= .25;
        return roundedDownYears;
    }
}
