package lifechoice;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.Years;

import java.io.Serializable;

public class LifeAge implements Serializable {
    private final double ageForBenefitTerm;
    private final double ageInSecondQuarterAfterStartDate;
    private final double fractionOfYear;
    private final int ageAtStartDate;
    private final int ageNextInQuarterAfterStartDate;
    private final double ageFraction;

    public LifeAge(DateTime policyStartDate, DateTime dateOfBirth) {
        this.ageForBenefitTerm = new ClientAge(policyStartDate, dateOfBirth).getAgeForBenefitTerm();
        ageAtStartDate = (int) Math.floor(ageForBenefitTerm);
        ageNextInQuarterAfterStartDate = (int) Math.ceil(ageForBenefitTerm);
        ageFraction = ageForBenefitTerm - ageAtStartDate;
        ageInSecondQuarterAfterStartDate = ageForBenefitTerm + 0.25;
        fractionOfYear = fractionOfYear(policyStartDate, dateOfBirth);
    }

    public double getAgeForBenefitTerm() {
        return ageForBenefitTerm;
    }

    public double getAgeInSecondQuarterAfterStartDate() {
        return ageInSecondQuarterAfterStartDate;
    }

    public int getAgeAtStartDate() {
        return ageAtStartDate;
    }

    public int getAgeNextInQuarterAfterStartDate() {
        return ageNextInQuarterAfterStartDate;
    }

    public double getAgeFraction() {
        return ageFraction;
    }

    public double getFractionOfYear() {
        return fractionOfYear;
    }

    private static double fractionOfYear(DateTime startDate, DateTime birthday) {
        Years years = Years.yearsBetween(birthday, startDate);
        birthday = birthday.plusYears(years.getYears() + 1);

        Months months = Months.monthsBetween(startDate, birthday);
        return (months.getMonths() / 12.0) - (Math.floor((months.getMonths() / 12.0)));
    }


}
