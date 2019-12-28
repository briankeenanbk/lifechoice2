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

public class LifeChoiceYouAndFamilySingleLifeTest {
    private static final RatesMap lsodRates = new RatesMapFromFileSystem("TestData/LumpSumOnDeathRates.csv");
    private static final RatesMap accRates = new RatesMapFromFileSystem("TestData/AccSpecIllnessRates.csv");
    private static final CommissionFactors commissionFactors = new LifeChoiceCommissionFactorsFromFileSystem();

    private static Policy policy;

    @BeforeClass
    public static void setup() {
        DateTime policyStartDate = stringToDate("21/02/2018");
        DateTime dob = stringToDate("22/01/1971");
        policy = new Policy(setupPolicyInputs(policyStartDate));

        BenefitInputs benefitInputs = setupBenefitLSODInputs(policy);
        CoreBenefit benefit = new CoreBenefit(benefitInputs);
        Life life = new Life(Lives.LIFE1, policyStartDate, dob, SmokerStatus.SMOKER);
        life.addCoreBenefit(benefit);
        life.addBenefit(benefit);
        benefitInputs = setupBenefitAccSpecIllnessInputs(policy);
        Benefit benefit2 = new Benefit(benefitInputs);
        life.addBenefit(benefit2);
        policy.addLife(life);

        policy.calculate();
    }

   private static PolicyInputs setupPolicyInputs(DateTime policyStartDate) {
        PolicyInputs policyInputs = new PolicyInputs();
        policyInputs.setPolicyType(PolicyType.YOU_AND_FAMILY);
        policyInputs.setCoverType(CoverType.SINGLE);
        policyInputs.setStartDate(policyStartDate);
        policyInputs.setTerm(37);
        policyInputs.setIncreasingBenefits(false);
        policyInputs.setAgeGapRates(new AgeFactorRatesMapFromFileSystem());
        policyInputs.setSingleRates(new SingleFactorRatesMapFromFileSystem());
        return policyInputs;
    }

    private static BenefitInputs setupBenefitLSODInputs(Policy policy) {
        BenefitInputs benefitInputs = new BenefitInputs();
        benefitInputs.setPolicy(policy);
        benefitInputs.setId(BenefitType.LUMP_SUM_ON_DEATH);
        benefitInputs.setSumAssured(167000);
        benefitInputs.setMaxAge(85);
        benefitInputs.setRates(lsodRates);
        benefitInputs.setExtraMortalityRate(25);
        benefitInputs.setPerMilleRate(20);
        benefitInputs.setRatingTerm(10);
        benefitInputs.setCommissionFactors(commissionFactors);
        benefitInputs.setPolicyFee(5.90);
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

    private static BenefitInputs setupBenefitAccSpecIllnessInputs(Policy policy) {
        BenefitInputs benefitInputs = new BenefitInputs();
        benefitInputs.setPolicy(policy);
        benefitInputs.setId(BenefitType.ACCELERATED_SPECIFIED_ILLNESS);
        benefitInputs.setSumAssured(50000);
        benefitInputs.setMaxAge(75);
        benefitInputs.setRates(accRates);
        benefitInputs.setExtraMortalityRate(0);
        benefitInputs.setPerMilleRate(0);
        benefitInputs.setRatingTerm(0);
        benefitInputs.setCommissionFactors(commissionFactors);
        benefitInputs.setPolicyFee(0.0);
        PremiumCalculatorInputs inputs = new PremiumCalculatorInputs();
        inputs.setPolicy(policy);
        inputs.setRates(benefitInputs.getRates());
        inputs.setMaxAge(benefitInputs.getMaxAge());
        inputs.setSumAssured(benefitInputs.getSumAssured());
        BasePremiumCalculator basePremiumCalculator = new SumAssuredPremiumCalculator(inputs);
        benefitInputs.setBasePremiumCalculator(basePremiumCalculator);

        ExtraMortalityCalculator extraMortalityCalculator = new RiderExtraMortalityCost(benefitInputs.getExtraMortalityRate());
        benefitInputs.setExtraMortalityCalculator(extraMortalityCalculator);
        return benefitInputs;
    }

    private static DateTime stringToDate(String dte) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        return formatter.parseDateTime(dte);
    }

    @Test
    public void unratedPremium() {
        assertEquals("Unrated premium", 273.87, policy.getResult().getUnratedPremium(), 0.01);
    }

    @Test
    public void extraMortality() {
        assertEquals("Extra mortality", 40.21, policy.getResult().getExtraMortalityCost(), 0.01);
    }

    @Test
    public void perMille() {
        assertEquals("Per mille", 33.71, policy.getResult().getPerMilleCost(), 0.01);
    }

    @Test
    public void totalRatings() {
        assertEquals("Total ratings", 73.92, policy.getResult().getExtraMortalityCost() + policy.getResult().getPerMilleCost(), 0.01);
    }

    @Test
    public void totalPremium() {
        assertEquals("Total premium", 347.80, policy.getResult().getUnratedPremium() + policy.getResult().getExtraMortalityCost() + policy.getResult().getPerMilleCost(), 0.01);
    }

    @Test
    public void priceMatch(){
        PriceMatch pm = new PriceMatch(policy, 200);
        Policy policy = pm.priceMatch();
        assertEquals("Price Match Total premium", 273.92, policy.getResult().getUnratedPremium() + policy.getResult().getExtraMortalityCost() + policy.getResult().getPerMilleCost(), 0.01);
    }

}
