package lifechoice.entities;

import lifechoice.CoverType;
import lifechoice.PolicyType;
import lifechoice.RatesMap;
import org.joda.time.DateTime;

import java.io.Serializable;

public class PolicyInputs implements Serializable{
    private PolicyType policyType;
    private CoverType coverType;
    private DateTime startDate;
    private boolean increasingBenefits;
    private double term;
    private RatesMap ageGapRates;
    private RatesMap singleRates;
    private boolean staff;

    public PolicyType getPolicyType() {
        return policyType;
    }

    public void setPolicyType(PolicyType policyType) {
        this.policyType = policyType;
    }

    public CoverType getCoverType() {
        return coverType;
    }

    public void setCoverType(CoverType coverType) {
        this.coverType = coverType;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public boolean isIncreasingBenefits() {
        return increasingBenefits;
    }

    public void setIncreasingBenefits(boolean increasingBenefits) {
        this.increasingBenefits = increasingBenefits;
    }

    public double getTerm() {
        return term;
    }

    public void setTerm(double term) {
        this.term = term;
    }

    public RatesMap getAgeGapRates() {
        return ageGapRates;
    }

    public void setAgeGapRates(RatesMap ageGapRates) {
        this.ageGapRates = ageGapRates;
    }

    public RatesMap getSingleRates() {
        return singleRates;
    }

    public void setSingleRates(RatesMap singleRates) {
        this.singleRates = singleRates;
    }

    public boolean isStaff() {
        return staff;
    }

    public void setStaff(boolean staff) {
        this.staff = staff;
    }
}
