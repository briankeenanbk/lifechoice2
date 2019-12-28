package lifechoice;

import lifechoice.entities.PremiumCalculatorInputs;

public class SumAssuredPremiumCalculator extends SimplePremiumCalculator{

    private final double sumAssured;

    public SumAssuredPremiumCalculator(PremiumCalculatorInputs inputs) {
        super(inputs);
        this.sumAssured = inputs.getSumAssured();
    }

    @Override
    protected double calculatePremiumWherePolicyAnniversaryOnDob() {
        String keyNow = BenefitKeyFormatter.formatKey(policyInputs.getPolicyType().getRatePrefix(policyInputs.isIncreasingBenefits()), life.getLifeAge().getAgeAtStartDate(), life.getSmokerStatus(), (int) benefitTerm);
        double rate = rates.lookUp(keyNow);

        return sumAssured * rate;
    }

    @Override
    protected double calculatePremiumFromStartDateToEndOfYear() {
        double premium = sumAssured * BenefitRateLookup.getRateNext(policyInputs.getPolicyType().getRatePrefix(policyInputs.isIncreasingBenefits()), life, benefitTerm, rates);
        return premium * life.getLifeAge().getFractionOfYear();
    }

    @Override
    protected double calculatePremiumFromEndOfYearToStartDate() {
        double premium = sumAssured * BenefitRateLookup.getRateNow(policyInputs.getPolicyType().getRatePrefix(policyInputs.isIncreasingBenefits()), life, benefitTerm, rates);
        return premium * (1 - life.getLifeAge().getFractionOfYear());
    }
}
