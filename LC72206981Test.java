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

public class LC72206981Test {
    private static final RatesMap lsodRates = new RatesMapFromFileSystem("TestData/LumpSumOnDeathRates.csv");
    private static final RatesMap accRates = new RatesMapFromFileSystem("TestData/AccSpecIllnessRates.csv");
    private static final RatesMap iodRates = new RatesMapFromFileSystem("TestData/IncomeOnDeathRates.csv");
    private static final RatesMap wolRates = new RatesMapFromFileSystem("TestData/WholeOfLifeContinuationRates.csv");
    private static final RatesMap accidentRates = new RatesMapFromFileSystem("TestData/AccidentRates.csv");
    private static final CommissionFactors commissionFactors = new LifeChoiceCommissionFactorsFromFileSystem();

    private static Policy policy;

    @BeforeClass
    public static void setup() {
        DateTime policyStartDate = stringToDate("01/11/2015");
        DateTime dob1 = stringToDate("02/02/1980");
        DateTime dob2 = stringToDate("02/07/1993");
        policy = new Policy(setupPolicyInputs(policyStartDate));

        BenefitInputs benefitInputs = setupBenefitLSODInputsLife1(policy);
        CoreBenefit benefit1 = new CoreBenefit(benefitInputs);
        Life life1 = new Life(Lives.LIFE1, policyStartDate, dob1, SmokerStatus.SMOKER);
        life1.addCoreBenefit(benefit1);
        life1.addBenefit(benefit1);
        benefitInputs = setupBenefitAccSpecIllnessInputsLife1(policy);
        Benefit benefit2 = new Benefit(benefitInputs);
        life1.addBenefit(benefit2);
        benefitInputs = setupBenefitWOLInputsLife1(policy);
        Benefit benefit3 = new Benefit(benefitInputs);
        life1.addBenefit(benefit3);
        policy.addLife(life1);

        benefitInputs = setupBenefitLSODInputsLife2(policy);
        benefit1 = new CoreBenefit(benefitInputs);
        Life life2 = new Life(Lives.LIFE2, policyStartDate, dob2, SmokerStatus.SMOKER);
        life2.addCoreBenefit(benefit1);
        life2.addBenefit(benefit1);

        benefitInputs = setupBenefitIncomeOnDeathInputsLife2(policy);
        CoreBenefit corebenefit2 = new CoreBenefit(benefitInputs);
        life2.addCoreBenefit(corebenefit2);
        life2.addBenefit(corebenefit2);

        benefitInputs = setupBenefitWOLInputsLife2(policy);
        benefit3 = new Benefit(benefitInputs);
        life2.addBenefit(benefit3);

        benefitInputs = setupBenefitAccidentInputsLife2(policy);
        Benefit benefit4 = new Benefit(benefitInputs);
        life2.addBenefit(benefit4);

        policy.addLife(life2);

        policy.calculate();
    }

    private static PolicyInputs setupPolicyInputs(DateTime policyStartDate) {
        PolicyInputs policyInputs = new PolicyInputs();
        policyInputs.setPolicyType(PolicyType.YOU_AND_FAMILY);
        policyInputs.setCoverType(CoverType.DUAL);
        policyInputs.setStartDate(policyStartDate);
        policyInputs.setTerm(35);
        policyInputs.setIncreasingBenefits(false);
        policyInputs.setAgeGapRates(new AgeFactorRatesMapFromFileSystem());
        policyInputs.setSingleRates(new SingleFactorRatesMapFromFileSystem());
        return policyInputs;
    }

    private static BenefitInputs setupBenefitLSODInputsLife1(Policy policy) {
        BenefitInputs benefitInputs = new BenefitInputs();
        benefitInputs.setPolicy(policy);
        benefitInputs.setId(BenefitType.LUMP_SUM_ON_DEATH);
        benefitInputs.setSumAssured(660000);
        benefitInputs.setMaxAge(85);
        benefitInputs.setRates(lsodRates);
        benefitInputs.setExtraMortalityRate(0);
        benefitInputs.setPerMilleRate(0);
        benefitInputs.setRatingTerm(0);
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

    private static BenefitInputs setupBenefitIncomeOnDeathInputsLife2(Policy policy) {
        BenefitInputs benefitInputs = new BenefitInputs();
        benefitInputs.setPolicy(policy);
        benefitInputs.setId(BenefitType.INCOME_ON_DEATH);
        benefitInputs.setSumAssured(1000);
        benefitInputs.setMaxAge(85);
        benefitInputs.setRates(iodRates);
        benefitInputs.setExtraMortalityRate(0);
        benefitInputs.setPerMilleRate(0);
        benefitInputs.setRatingTerm(0);
        benefitInputs.setCommissionFactors(commissionFactors);
        benefitInputs.setPolicyFee(5.90);
        PremiumCalculatorInputs inputs = new PremiumCalculatorInputs();
        inputs.setPolicy(policy);
        inputs.setRates(benefitInputs.getRates());
        inputs.setMaxAge(benefitInputs.getMaxAge());
        inputs.setSumAssured(benefitInputs.getSumAssured());
        BasePremiumCalculator basePremiumCalculator = new SimplePremiumCalculator(inputs);
        benefitInputs.setBasePremiumCalculator(basePremiumCalculator);
        ExtraMortalityCalculator extraMortalityCalculator = new CoreExtraMortalityCost(benefitInputs.getRates(), benefitInputs.getExtraMortalityRate());
        benefitInputs.setExtraMortalityCalculator(extraMortalityCalculator);
        return benefitInputs;
    }

    private static BenefitInputs setupBenefitLSODInputsLife2(Policy policy) {
        BenefitInputs benefitInputs = new BenefitInputs();
        benefitInputs.setPolicy(policy);
        benefitInputs.setId(BenefitType.LUMP_SUM_ON_DEATH);
        benefitInputs.setSumAssured(150000);
        benefitInputs.setMaxAge(85);
        benefitInputs.setRates(lsodRates);
        benefitInputs.setExtraMortalityRate(0);
        benefitInputs.setPerMilleRate(0);
        benefitInputs.setRatingTerm(0);
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

    private static BenefitInputs setupBenefitAccidentInputsLife2(Policy policy) {
        BenefitInputs benefitInputs = new BenefitInputs();
        benefitInputs.setPolicy(policy);
        benefitInputs.setId(BenefitType.ACCIDENT_PAYMENT);
        benefitInputs.setSumAssured(280);
        benefitInputs.setMaxAge(65);
        benefitInputs.setRates(accidentRates);
        benefitInputs.setExtraMortalityRate(0);
        benefitInputs.setPerMilleRate(0);
        benefitInputs.setRatingTerm(0);
        benefitInputs.setCommissionFactors(commissionFactors);
        benefitInputs.setPolicyFee(0.0);
        benefitInputs.setConversionFactor(0);
        benefitInputs.setEssentialActivitiesFactor(0.0);
        PremiumCalculatorInputs inputs = new PremiumCalculatorInputs();
        inputs.setPolicy(policy);
        inputs.setRates(benefitInputs.getRates());
        inputs.setMaxAge(benefitInputs.getMaxAge());
        inputs.setSumAssured(benefitInputs.getSumAssured());
        BasePremiumCalculator basePremiumCalculator = new SimplePremiumCalculator(inputs);
        benefitInputs.setBasePremiumCalculator(basePremiumCalculator);
        ExtraMortalityCalculator extraMortalityCalculator = new RiderExtraMortalityCost(benefitInputs.getExtraMortalityRate());
        benefitInputs.setExtraMortalityCalculator(extraMortalityCalculator);
        return benefitInputs;
    }

    private static BenefitInputs setupBenefitAccSpecIllnessInputsLife1(Policy policy) {
        BenefitInputs benefitInputs = new BenefitInputs();
        benefitInputs.setPolicy(policy);
        benefitInputs.setId(BenefitType.ACCELERATED_SPECIFIED_ILLNESS);
        benefitInputs.setSumAssured(90000);
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
        BasePremiumCalculator basePremiumCalculator =  new SumAssuredPremiumCalculator(inputs);
        benefitInputs.setBasePremiumCalculator(basePremiumCalculator);
        ExtraMortalityCalculator extraMortalityCalculator = new RiderExtraMortalityCost(benefitInputs.getExtraMortalityRate());
        benefitInputs.setExtraMortalityCalculator(extraMortalityCalculator);
        return benefitInputs;
    }

    private static BenefitInputs setupBenefitWOLInputsLife1(Policy policy) {
        BenefitInputs benefitInputs = new BenefitInputs();
        benefitInputs.setPolicy(policy);
        benefitInputs.setId(BenefitType.WHOLE_OF_LIFE_CONTINUATION);
        benefitInputs.setSumAssured(43000);
        benefitInputs.setMaxAge(85);
        benefitInputs.setRates(wolRates);
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
        BasePremiumCalculator basePremiumCalculator =  new SumAssuredPremiumCalculator(inputs);
        benefitInputs.setBasePremiumCalculator(basePremiumCalculator);

        ExtraMortalityCalculator extraMortalityCalculator = new RiderExtraMortalityCost(benefitInputs.getExtraMortalityRate());
        benefitInputs.setExtraMortalityCalculator(extraMortalityCalculator);
        return benefitInputs;
    }

    private static BenefitInputs setupBenefitWOLInputsLife2(Policy policy) {
        BenefitInputs benefitInputs = new BenefitInputs();
        benefitInputs.setPolicy(policy);
        benefitInputs.setId(BenefitType.WHOLE_OF_LIFE_CONTINUATION);
        benefitInputs.setSumAssured(24000);
        benefitInputs.setMaxAge(85);
        benefitInputs.setRates(wolRates);
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
        BasePremiumCalculator basePremiumCalculator =  new SumAssuredPremiumCalculator(inputs);
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
        assertEquals("Unrated premium", 311.70, policy.getResult().getUnratedPremium(), 0.01);
    }

    @Test
    public void extraMortality() {
        assertEquals("Extra mortality", 0.0, policy.getResult().getExtraMortalityCost(), 0.01);
    }

    @Test
    public void perMille() {
        assertEquals("Per mille", 0.0, policy.getResult().getPerMilleCost(), 0.01);
    }

    @Test
    public void totalRatings() {
        assertEquals("Total ratings", 0.0, policy.getResult().getExtraMortalityCost() + policy.getResult().getPerMilleCost(), 0.01);
    }

    @Test
    public void totalPremium() {
        assertEquals("Total premium", 311.70, policy.getResult().getUnratedPremium() + policy.getResult().getExtraMortalityCost() + policy.getResult().getPerMilleCost(), 0.01);
    }
}

