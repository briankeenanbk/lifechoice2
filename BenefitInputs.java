package lifechoice.entities;

import lifechoice.*;

public class BenefitInputs {
    private Policy policy;
    private BenefitType id;
    private BasePremiumCalculator basePremiumCalculator;
    private ExtraMortalityCalculator extraMortalityCalculator;
    private double sumAssured;
    private int maxAge;
    private RatesMap rates;
    private int extraMortalityRate;
    private int perMilleRate;
    private int ratingTerm;
    private CommissionFactors commissionFactors;
    private double policyFee;
    private double conversionFactor;
    private double essentialActivitiesFactor;

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public BenefitType getId() {
        return id;
    }

    public void setId(BenefitType id) {
        this.id = id;
    }

    public BasePremiumCalculator getBasePremiumCalculator() {
        return basePremiumCalculator;
    }

    public void setBasePremiumCalculator(BasePremiumCalculator basePremiumCalculator) {
        this.basePremiumCalculator = basePremiumCalculator;
    }

    public ExtraMortalityCalculator getExtraMortalityCalculator() {
        return extraMortalityCalculator;
    }

    public void setExtraMortalityCalculator(ExtraMortalityCalculator extraMortalityCalculator) {
        this.extraMortalityCalculator = extraMortalityCalculator;
    }

    public double getSumAssured() {
        return sumAssured;
    }

    public void setSumAssured(double sumAssured) {
        this.sumAssured = sumAssured;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public RatesMap getRates() {
        return rates;
    }

    public void setRates(RatesMap rates) {
        this.rates = rates;
    }

    public int getExtraMortalityRate() {
        return extraMortalityRate;
    }

    public void setExtraMortalityRate(int extraMortalityRate) {
        this.extraMortalityRate = extraMortalityRate;
    }

    public int getPerMilleRate() {
        return perMilleRate;
    }

    public void setPerMilleRate(int perMilleRate) {
        this.perMilleRate = perMilleRate;
    }

    public int getRatingTerm() {
        return ratingTerm;
    }

    public void setRatingTerm(int ratingTerm) {
        this.ratingTerm = ratingTerm;
    }

    public CommissionFactors getCommissionFactors() {
        return commissionFactors;
    }

    public void setCommissionFactors(CommissionFactors commissionFactors) {
        this.commissionFactors = commissionFactors;
    }

    public double getPolicyFee() {
        return policyFee;
    }

    public void setPolicyFee(double policyFee) {
        this.policyFee = policyFee;
    }

    public double getConversionFactor() {
        return Math.max(1.0, conversionFactor);
    }

    public void setConversionFactor(double conversionFactor) {
        this.conversionFactor = Math.max(1.0, conversionFactor);
    }

    public double getEssentialActivitiesFactor() {
        return Math.max(1.0, essentialActivitiesFactor);
    }

    public void setEssentialActivitiesFactor(double essentialActivitiesFactor) {
        this.essentialActivitiesFactor = Math.max(1.0, essentialActivitiesFactor);
    }
}
