package lifechoice;

import lifechoice.entities.PremiumCalculatorInputs;
import lifechoice.entities.PolicyInputs;

public class SimplePremiumCalculator implements BasePremiumCalculator{
    final Policy policy;
    final PolicyInputs policyInputs;
    Life life;
    final RatesMap rates;
    double benefitTerm;
    private final int maxAge;

    public SimplePremiumCalculator(PremiumCalculatorInputs inputs) {
        this.policy = inputs.getPolicy();
        policyInputs = policy.getInputs();
        this.rates = inputs.getRates();
        this.maxAge = inputs.getMaxAge();
    }

    public double calculateBasePremium(Life life) {
        this.life = life;
        benefitTerm = calculateBenefitTerm();
        if (policyAnniversaryIsOnBirthday())
            return calculatePremiumWherePolicyAnniversaryOnDob();
        else
            return calculatePremiumWherePolicyAnniversaryNotDob();
    }

    private double calculateBenefitTerm() {
        return Math.min(policyInputs.getTerm(), (maxAge - life.getLifeAge().getAgeForBenefitTerm()));
    }

    private boolean policyAnniversaryIsOnBirthday() {
        return life.getLifeAge().getAgeFraction() == 0.0;
    }

    double calculatePremiumWherePolicyAnniversaryOnDob() {
        String keyNow = BenefitKeyFormatter.formatKey(policyInputs.getPolicyType().getRatePrefix(policyInputs.isIncreasingBenefits()), life.getLifeAge().getAgeAtStartDate(), life.getSmokerStatus(), (int) benefitTerm);

        return rates.lookUp(keyNow);
    }

    private double calculatePremiumWherePolicyAnniversaryNotDob() {
        return calculatePremiumFromStartDateToEndOfYear() + calculatePremiumFromEndOfYearToStartDate();
    }

    double calculatePremiumFromStartDateToEndOfYear() {
        double premium = BenefitRateLookup.getRateNext(policyInputs.getPolicyType().getRatePrefix(policyInputs.isIncreasingBenefits()), life, benefitTerm, rates);
        return premium * life.getLifeAge().getFractionOfYear();
    }

    double calculatePremiumFromEndOfYearToStartDate() {
        double premium = BenefitRateLookup.getRateNow(policyInputs.getPolicyType().getRatePrefix(policyInputs.isIncreasingBenefits()), life, benefitTerm, rates);
        return premium * (1 - life.getLifeAge().getFractionOfYear());
    }
}
