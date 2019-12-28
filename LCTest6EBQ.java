import lifechoice.*;
import lifechoice.entities.BenefitInputs;
import lifechoice.entities.PolicyInputs;
import lifechoice.entities.PremiumCalculatorInputs;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class LCTest6EBQ {
    private static final RatesMap lsodRates = new RatesMapFromFileSystem("TestData/LumpSumOnDeathRates.csv");
    private static final RatesMap accRates = new RatesMapFromFileSystem("TestData/AccSpecIllnessRates.csv");
    private static final CommissionFactors commissionFactors = new LifeChoiceCommissionFactorsFromFileSystem();

    private static Policy policy;

    @BeforeClass
    public static void setup() {
        DateTime policyStartDate = stringToDate("01/07/2015");
        DateTime dob1 = stringToDate("22/10/1983");
        DateTime dob2 = stringToDate("24/07/1986");
        policy = new Policy(setupPolicyInputs(policyStartDate));

        BenefitInputs benefitInputs = setupBenefitLSODInputsLife1(policy);
        CoreBenefit benefit1 = new CoreBenefit(benefitInputs);
        Life life1 = new Life(Lives.LIFE1, policyStartDate, dob1, SmokerStatus.NON_SMOKER);
        life1.addCoreBenefit(benefit1);
        life1.addBenefit(benefit1);

        policy.addLife(life1);

        benefitInputs = setupBenefitLSODInputsLife2(policy);
        benefit1 = new CoreBenefit(benefitInputs);
        Life life2 = new Life(Lives.LIFE2, policyStartDate, dob2, SmokerStatus.NON_SMOKER);
        life2.addCoreBenefit(benefit1);
        life2.addBenefit(benefit1);

        policy.addLife(life2);

        policy.calculate();
    }

    private static PolicyInputs setupPolicyInputs(DateTime policyStartDate) {
        PolicyInputs policyInputs = new PolicyInputs();
        policyInputs.setPolicyType(PolicyType.HOME);
        policyInputs.setCoverType(CoverType.JOINT);
        policyInputs.setStartDate(policyStartDate);
        policyInputs.setTerm(30);
        policyInputs.setIncreasingBenefits(false);
        policyInputs.setAgeGapRates(new AgeFactorRatesMapFromFileSystem());
        policyInputs.setSingleRates(new SingleFactorRatesMapFromFileSystem());
        return policyInputs;
    }

    private static BenefitInputs setupBenefitLSODInputsLife1(Policy policy) {
        BenefitInputs benefitInputs = new BenefitInputs();
        benefitInputs.setPolicy(policy);
        benefitInputs.setId(BenefitType.LUMP_SUM_ON_DEATH);
        benefitInputs.setSumAssured(315576);
        benefitInputs.setMaxAge(85);
        benefitInputs.setRates(lsodRates);
        benefitInputs.setExtraMortalityRate(0);
        benefitInputs.setPerMilleRate(20);
        benefitInputs.setRatingTerm(30);
        benefitInputs.setCommissionFactors(commissionFactors);
        benefitInputs.setPolicyFee(5.90);
        benefitInputs.setConversionFactor(0.0);
        benefitInputs.setEssentialActivitiesFactor(0.0);
        PremiumCalculatorInputs inputs = new PremiumCalculatorInputs();
        inputs.setPolicy(policy);
        inputs.setRates(benefitInputs.getRates());
        inputs.setMaxAge(benefitInputs.getMaxAge());
        inputs.setSumAssured(benefitInputs.getSumAssured());
        BasePremiumCalculator basePremiumCalculator = new SumAssuredPremiumCalculator(inputs);
        benefitInputs.setBasePremiumCalculator(basePremiumCalculator);
        ExtraMortalityCalculator extraMortalityCalculator = new CoreExtraMortalityCost(benefitInputs.getRates(), benefitInputs.getExtraMortalityRate());
        benefitInputs.setExtraMortalityCalculator(extraMortalityCalculator);
        return benefitInputs;
    }

    private static BenefitInputs setupBenefitLSODInputsLife2(Policy policy) {
        BenefitInputs benefitInputs = new BenefitInputs();
        benefitInputs.setPolicy(policy);
        benefitInputs.setId(BenefitType.LUMP_SUM_ON_DEATH);
        benefitInputs.setSumAssured(315576);
        benefitInputs.setMaxAge(85);
        benefitInputs.setRates(lsodRates);
        benefitInputs.setExtraMortalityRate(0);
        benefitInputs.setPerMilleRate(0);
        benefitInputs.setRatingTerm(0);
        benefitInputs.setCommissionFactors(commissionFactors);
        benefitInputs.setPolicyFee(5.90);
        benefitInputs.setConversionFactor(0.0);
        benefitInputs.setEssentialActivitiesFactor(0.0);
        PremiumCalculatorInputs inputs = new PremiumCalculatorInputs();
        inputs.setPolicy(policy);
        inputs.setRates(benefitInputs.getRates());
        inputs.setMaxAge(benefitInputs.getMaxAge());
        inputs.setSumAssured(benefitInputs.getSumAssured());
        BasePremiumCalculator basePremiumCalculator = new SumAssuredPremiumCalculator(inputs);
        benefitInputs.setBasePremiumCalculator(basePremiumCalculator);
        ExtraMortalityCalculator extraMortalityCalculator = new CoreExtraMortalityCost(benefitInputs.getRates(), benefitInputs.getExtraMortalityRate());
        benefitInputs.setExtraMortalityCalculator(extraMortalityCalculator);
        return benefitInputs;
    }


    private static DateTime stringToDate(String dte) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        return formatter.parseDateTime(dte);
    }

    @Test
    public void unratedPremium() {
        assertEquals("Unrated premium", 27.62, policy.getResult().getUnratedPremium(), 0.01);
    }

    @Test
    public void extraMortality() {
        assertEquals("Extra mortality", 0.0, policy.getResult().getExtraMortalityCost(), 0.01);
    }

    @Test
    public void perMille() {
        assertEquals("Per mille", 56.62, policy.getResult().getPerMilleCost(), 0.01);
    }

    @Test
    public void totalRatings() {
        assertEquals("Total ratings", 56.62, policy.getResult().getExtraMortalityCost() + policy.getResult().getPerMilleCost(), 0.01);
    }

    @Test
    public void totalPremium() {
        assertEquals("Total premium", 84.24, policy.getResult().getUnratedPremium() + policy.getResult().getExtraMortalityCost() + policy.getResult().getPerMilleCost(), 0.01);
    }

    @Test
    public void totalPremiumIncludingLevy() {
        assertEquals("Total premium", 85.08, policy.getResult().getUnratedPremium() + policy.getResult().getExtraMortalityCost() + policy.getResult().getPerMilleCost() + ((policy.getResult().getUnratedPremium() + policy.getResult().getExtraMortalityCost() + policy.getResult().getPerMilleCost()) * .01), 0.01);
    }

    @Test
    public void priceMatch(){
        PriceMatch pm = new PriceMatch(policy, 30.14);
        Policy policy = pm.priceMatch();
        assertEquals("Price Match Total premium", 87.62, (policy.getResult().getUnratedPremium() + policy.getResult().getExtraMortalityCost() + policy.getResult().getPerMilleCost()) * 1.01, 0.01);
    }

}
