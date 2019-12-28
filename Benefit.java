package lifechoice;

import lifechoice.entities.CalculationResult;
import lifechoice.entities.BenefitInputs;
import lifechoice.entities.PolicyInputs;
import lifechoice.entities.MortalityCostInputs;

import java.io.Serializable;

public class Benefit implements Serializable {

    private final Policy policy;
    private final BenefitType id;
    private final double sumAssured;
    private final int maxAge;
    private final double policyFee;
    private final double conversionFactor;
    private final double essentialActivitiesFactor;
    private final BasePremiumCalculator basePremiumCalculator;
    private final PolicyInputs policyInputs;
    private final int ratingTerm;

    private ExtraMortalityCalculator extraMortalityCalculator;
    final CalculationResult result;

    private final int extraMortalityRate;

    private Life life;

    CostFactors costFactors;

    public Benefit(BenefitInputs inputs) {
        this.policy = inputs.getPolicy();
        this.policyInputs = policy.getInputs();
        this.id = inputs.getId();
        this.sumAssured = inputs.getSumAssured();
        this.maxAge = inputs.getMaxAge();
        this.policyFee = inputs.getPolicyFee();
        this.conversionFactor = inputs.getConversionFactor();
        this.essentialActivitiesFactor = inputs.getEssentialActivitiesFactor();
        this.basePremiumCalculator = inputs.getBasePremiumCalculator();
        this.extraMortalityCalculator = inputs.getExtraMortalityCalculator();
        this.extraMortalityRate = inputs.getExtraMortalityRate();
        this.ratingTerm = inputs.getRatingTerm();
        result = new CalculationResult();
    }

    public double calculateUnratedPremium(Life life) {
        this.life = life;
        double basePremium = basePremiumCalculator.calculateBasePremium(life);
        costFactors = new CostFactors(policy, this);
        double unratedPremium = costFactors.applyPremiumCostFactors(basePremium, determinePolicyFee());
        result.setUnratedPremium(unratedPremium);
        return unratedPremium;
    }

    private double determinePolicyFee() {
        return policy.benefitOnBothLives(this) ? policyFee / 2 : policyFee;
    }

    public double calculateExtraMortalityCost(Life life) {
        this.life = life;
        MortalityCostInputs mci = setupMortalityCostInputs();
        double extraMortalityCost = extraMortalityCalculator.calculateExtraMortalityCost(mci);
        result.setExtraMortalityCost(extraMortalityCost);
        return extraMortalityCost;
    }

    private MortalityCostInputs setupMortalityCostInputs() {
        MortalityCostInputs mci = new MortalityCostInputs();
        mci.setSumAssured(sumAssured);
        mci.setUnratedPremium(result.getUnratedPremium());
        mci.setExtraMortalityTerm(calculateExtraMortalityTerm());
        mci.setAgeNextQuarter(life.getLifeAge().getAgeForBenefitTerm());
        mci.setAgeFraction(life.getLifeAge().getAgeFraction());
        mci.setSmokerStatus(life.getSmokerStatus());
        mci.setRatePrefix(policy.getInputs().getPolicyType().getRatePrefix(policy.getInputs().isIncreasingBenefits()));
        mci.setCostFactors(costFactors);
        return mci;
    }

    private double calculateExtraMortalityTerm() {
        return Math.min(policyInputs.getTerm(), (maxAge - life.getLifeAge().getAgeForBenefitTerm()));
    }

    public BenefitType getId() {
        return id;
    }

    public Life getLife() {
        return life;
    }



    public double getConversionFactor() {
        return conversionFactor;
    }

    public double getEssentialActivitiesFactor() {
        return essentialActivitiesFactor;
    }

    public CalculationResult getResult() {
        return result;
    }

    public void setExtraMortalityCalculator(ExtraMortalityCalculator extraMortalityCalculator) {
        this.extraMortalityCalculator = extraMortalityCalculator;
    }

    public int getExtraMortalityRate() {
        return extraMortalityRate;
    }
}
