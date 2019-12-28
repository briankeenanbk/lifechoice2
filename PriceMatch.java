package lifechoice;

import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.util.List;

public class PriceMatch {
    private final Policy originalPolicy;
    private final Policy priceMatchedPolicy;
    private final double priceMatchPremium;

    private static final List<BenefitType> priceMatchableBenefits = new ArrayList<BenefitType>();
    private static final List<BenefitType> priceMatchablePremiumBenefits = new ArrayList<BenefitType>();

    static {
        //     priceMatchableBenefits.add(BenefitType.LUMP_SUM_ON_DEATH);
        priceMatchableBenefits.add(BenefitType.ADDITIONAL_SPECIFIED_ILLNESS);
        priceMatchableBenefits.add(BenefitType.ACCELERATED_SPECIFIED_ILLNESS);
        priceMatchableBenefits.add(BenefitType.ADDITIONAL_SPECIFIED_ILLNESS);
        priceMatchableBenefits.add(BenefitType.INCOME_ON_DEATH);
        priceMatchableBenefits.add(BenefitType.ACCIDENT_PAYMENT);
        priceMatchableBenefits.add(BenefitType.HOSPITAL_CASH);

        priceMatchablePremiumBenefits.add(BenefitType.LUMP_SUM_ON_DEATH);
        priceMatchablePremiumBenefits.add(BenefitType.ADDITIONAL_SPECIFIED_ILLNESS);
        priceMatchablePremiumBenefits.add(BenefitType.ACCELERATED_SPECIFIED_ILLNESS);
        priceMatchablePremiumBenefits.add(BenefitType.ADDITIONAL_SPECIFIED_ILLNESS);
        priceMatchablePremiumBenefits.add(BenefitType.ACCIDENT_PAYMENT);
        priceMatchablePremiumBenefits.add(BenefitType.HOSPITAL_CASH);
    }

    public PriceMatch(Policy originalPolicy, double priceMatchPremium) {
        this.originalPolicy = originalPolicy;
        this.priceMatchPremium = priceMatchPremium;

        this.priceMatchedPolicy = makeCopyOf(originalPolicy);
    }

    private Policy makeCopyOf(Policy originalPolicy) {
        return SerializationUtils.roundtrip(originalPolicy);
    }

    public Policy priceMatch() {
        double ratio = calculateRatio();

        List<Life> lives = priceMatchedPolicy.getLives();
        for (Life life : lives) {
            List<Benefit> benefits = life.getBenefits();
            for (Benefit benefit : benefits) {
                if (priceMatchablePremiumBenefits.contains(benefit.getId()))
                    applyRatio(ratio, benefit);
                if (priceMatchableBenefits.contains(benefit.getId())) {
                    benefit.setExtraMortalityCalculator(new RiderExtraMortalityCost(benefit.getExtraMortalityRate()));
                    benefit.calculateExtraMortalityCost(life);
                }

            }
            updateLife(life, benefits);
        }
        updatePolicy(lives);
        return priceMatchedPolicy;
    }

    private double calculateRatio() {
        double unratedPremium = calculatePriceMatchablePremium();
        return priceMatchPremium / unratedPremium;
    }

    private double calculatePriceMatchablePremium() {
        double unRatedPremium = 0.0;

        List<Life> lives = originalPolicy.getLives();
        for (Life life : lives) {
            List<Benefit> benefits = life.getBenefits();
            for (Benefit benefit : benefits) {
                if (priceMatchablePremiumBenefits.contains(benefit.getId()))
                    unRatedPremium += benefit.getResult().getUnratedPremium();
            }
        }
        return unRatedPremium;
    }

    private void applyRatio(double ratio, Benefit benefit) {
        benefit.getResult().setUnratedPremium(benefit.getResult().getUnratedPremium() * ratio);

        //benefit.getResult().setExtraMortalityCost(benefit.getResult().getExtraMortalityCost() * ratio);
        //  benefit.getResult().setPerMilleCost(benefit.getResult().getPerMilleCost() * ratio);
    }

    private void updateLife(Life life, List<Benefit> benefits) {
        double unRatedPremium = 0.0;
        double extraMortalityCost = 0.0;
        double perMilleCost = 0.0;

        for (Benefit benefit : benefits) {
            unRatedPremium += benefit.getResult().getUnratedPremium();
            extraMortalityCost += benefit.getResult().getExtraMortalityCost();
            perMilleCost += benefit.getResult().getPerMilleCost();
        }

        life.getResult().setUnratedPremium(unRatedPremium);
        life.getResult().setExtraMortalityCost(extraMortalityCost);
        life.getResult().setPerMilleCost(perMilleCost);
    }

    private void updatePolicy(List<Life> lives) {
        double unRatedPremium = 0.0;
        double extraMortalityCost = 0.0;
        double perMilleCost = 0.0;

        for (Life life : lives) {
            unRatedPremium += life.getResult().getUnratedPremium();
            extraMortalityCost += life.getResult().getExtraMortalityCost();
            perMilleCost += life.getResult().getPerMilleCost();
        }

        priceMatchedPolicy.getResult().setUnratedPremium(unRatedPremium);
        priceMatchedPolicy.getResult().setExtraMortalityCost(extraMortalityCost);
        priceMatchedPolicy.getResult().setPerMilleCost(perMilleCost);
    }

}
