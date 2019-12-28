package lifechoice;

import lifechoice.entities.MortalityCostInputs;

public class CoreExtraMortalityCost implements ExtraMortalityCalculator {
    private final RatesMap rates;
    private double ageNextQuarter;
    private double ageFraction;
    private SmokerStatus smokerStatus;
    private double extraMortalityTerm;
    private String ratePrefix;
    private double sumAssured;
    private final int extraMortalityRate;

    public CoreExtraMortalityCost(RatesMap rates, int extraMortalityRate) {
        this.rates = rates;
        this.extraMortalityRate = extraMortalityRate;
    }

    public double calculateExtraMortalityCost(MortalityCostInputs mci) {
        ageNextQuarter = mci.getAgeNextQuarter();
        ageFraction = mci.getAgeFraction();
        smokerStatus = mci.getSmokerStatus();
        extraMortalityTerm = mci.getExtraMortalityTerm();
        ratePrefix = mci.getRatePrefix();
        CostFactors costFactors = mci.getCostFactors();
        sumAssured = mci.getSumAssured();
        double baseExtraMortality = calculateExtraMortalityRating(determineMortalityRate());

        return costFactors.applyExtraMortalityCostFactors(baseExtraMortality);
    }

    private double calculateExtraMortalityRating(double extraMortalityBenefitRate) {
        return sumAssured * (extraMortalityRate / 100.0) * extraMortalityBenefitRate;
    }

    private double determineMortalityRate() {
        if (policyAnniversaryIsOnBirthday())
            return getHighMortalityRate();
        else {
            double lowRate = getLowMortalityRate();
            double highRate = getHighMortalityRate();
            return (ageFraction * (highRate - lowRate)) + lowRate;
        }
    }

    private double getLowMortalityRate() {
        int lowAge = (int) Math.floor(ageNextQuarter);
        int lowTerm = (int) Math.floor(extraMortalityTerm);
        String key = BenefitKeyFormatter.formatKey(ratePrefix, lowAge, smokerStatus, lowTerm);
        return rates.lookUp(key);
    }

    private double getHighMortalityRate() {
        int highAge = (int) Math.ceil(ageNextQuarter);
        int highTerm = (int) Math.ceil(extraMortalityTerm);
        String key = BenefitKeyFormatter.formatKey(ratePrefix, highAge, smokerStatus, highTerm);
        return rates.lookUp(key);
    }

    private boolean policyAnniversaryIsOnBirthday() {
        return ageFraction == 0.0;
    }

}
