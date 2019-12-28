package lifechoice.entities;

import lifechoice.CostFactors;
import lifechoice.SmokerStatus;

public class MortalityCostInputs {
    private double sumAssured;
    private double unratedPremium;
    private double extraMortalityTerm;
    private double ageNextQuarter;
    private double ageFraction;
    private SmokerStatus smokerStatus;
    private String ratePrefix;
    private CostFactors costFactors;

    public double getSumAssured() {
        return sumAssured;
    }

    public void setSumAssured(double sumAssured) {
        this.sumAssured = sumAssured;
    }

    public double getUnratedPremium() {
        return unratedPremium;
    }

    public void setUnratedPremium(double unratedPremium) {
        this.unratedPremium = unratedPremium;
    }

    public double getExtraMortalityTerm() {
        return extraMortalityTerm;
    }

    public void setExtraMortalityTerm(double extraMortalityTerm) {
        this.extraMortalityTerm = extraMortalityTerm;
    }

    public double getAgeNextQuarter() {
        return ageNextQuarter;
    }

    public void setAgeNextQuarter(double ageNextQuarter) {
        this.ageNextQuarter = ageNextQuarter;
    }

    public double getAgeFraction() {
        return ageFraction;
    }

    public void setAgeFraction(double ageFraction) {
        this.ageFraction = ageFraction;
    }

    public SmokerStatus getSmokerStatus() {
        return smokerStatus;
    }

    public void setSmokerStatus(SmokerStatus smokerStatus) {
        this.smokerStatus = smokerStatus;
    }

    public String getRatePrefix() {
        return ratePrefix;
    }

    public void setRatePrefix(String ratePrefix) {
        this.ratePrefix = ratePrefix;
    }

    public CostFactors getCostFactors() {
        return costFactors;
    }

    public void setCostFactors(CostFactors costFactors) {
        this.costFactors = costFactors;
    }
}
