package lifechoice;

import lifechoice.entities.PolicyInputs;

import java.io.Serializable;
import java.util.List;

public class CostFactors implements Serializable {
    private final double singleFactor;
    private final double ageGapFactor;
    private final double coverTypeFactor;
    private final double conversionFactor;
    private final double essentialActivitiesFactor;
    private final double staffFactor;
    private final Policy policy;
    private final Benefit benefit;
    private final PolicyInputs policyInputs;

    public CostFactors(Policy policy, Benefit benefit) {
        this.policy = policy;
        this.policyInputs = policy.getInputs();
        this.benefit = benefit;
        coverTypeFactor = policy.getInputs().getCoverType().getFactor();
        ageGapFactor = policy.getInputs().getCoverType().equals(CoverType.SINGLE)  ? 1.0 : determineAgeGapFactor();
        singleFactor = determineSingleFactor();
        conversionFactor =  benefit.getConversionFactor();
        essentialActivitiesFactor = benefit.getEssentialActivitiesFactor();
        staffFactor = policyInputs.isStaff() ? 0.85 : 1.0;
    }

    private double determineAgeGapFactor() {
        return policy.benefitOnBothLives(benefit) ? getAgeGapFactor() : 1.0;
    }

    private double getAgeGapFactor() {
        List<Life> life = policy.getLives();
        int ageDifference = Math.abs((int) Math.round(life.get(0).getLifeAge().getAgeForBenefitTerm() - life.get(1).getLifeAge().getAgeForBenefitTerm()));
        String key = BenefitKeyFormatter.formatAgeGapKey(policyInputs.getPolicyType().getAgeGapPrefix(),
                benefit.getId().getValue(), policyInputs.isIncreasingBenefits(),
                life.get(0).getSmokerStatus(), life.get(1).getSmokerStatus(), ageDifference);

        return policyInputs.getAgeGapRates().lookUp(key);
    }

    private double determineSingleFactor() {
        return !policy.benefitOnBothLives(benefit) ? getSingleFactor() : 1.0;
    }

    private double getSingleFactor() {
        List<Life> life = policy.getLives();
        String key = BenefitKeyFormatter.formatSingleKey(policyInputs.getPolicyType().getSinglePrefix(policyInputs.isIncreasingBenefits()), benefit.getId().getValue(),
                policyInputs.isIncreasingBenefits(), life.get(0).getSmokerStatus());
        return policyInputs.getSingleRates().lookUp(key);
    }

    public double applyPremiumCostFactors(double basePremium, double policyFee) {
        double basePremiumWithCostFactors = applySingleAgeGapFactors(basePremium);
        double unratedPremium = basePremiumWithCostFactors + policyFee;
        unratedPremium = applyCoverType(unratedPremium);
        unratedPremium = applyConversion(unratedPremium);
        unratedPremium = applyStaff(unratedPremium);
        return unratedPremium;
    }

    public double reversePremiumCostFactors(double premium, double policyFee) {
        double basePremium = reverseStaff(premium);
        basePremium = reverseConversion(basePremium);
        basePremium = reverseCoverType(basePremium);
        basePremium -= policyFee;
        basePremium = reverseSingleAgeGapFactors(basePremium);
        return basePremium;
    }

    private double applyStaff(double unratedPremium) {
        return unratedPremium * staffFactor;
    }

    private double reverseStaff(double premium) {
        return premium / staffFactor;
    }

    public double applyExtraMortalityCostFactors(double baseExtraMortality) {
        double extraMortalityCost = applySingleAgeGapFactors(baseExtraMortality);
        extraMortalityCost = applyCoverType(extraMortalityCost);
        extraMortalityCost = applyConversion(extraMortalityCost);
        extraMortalityCost = applyStaff(extraMortalityCost);
        return extraMortalityCost;
    }

    public double applyPerMilleCostFactors(double basePerMilleCost) {
        double perMilleCost = applyCoverType(basePerMilleCost);
        perMilleCost = applyConversion(perMilleCost);
        perMilleCost = applyStaff(perMilleCost);
        return perMilleCost;
    }

    private double applySingleAgeGapFactors(double amount) {
        return amount * singleFactor * ageGapFactor;
    }

    private double reverseSingleAgeGapFactors(double amount) {
        return amount / singleFactor / ageGapFactor;
    }

    private double applyCoverType(double amount) {
        return amount * coverTypeFactor;
    }

    private double reverseCoverType(double amount) {
        return amount / coverTypeFactor;
    }

    private double applyConversion(double amount) {
        return amount * conversionFactor;
    }

    private double reverseConversion(double amount) {
        return amount / conversionFactor;
    }




}
