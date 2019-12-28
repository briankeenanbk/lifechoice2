package lifechoice;

import lifechoice.entities.BenefitInputs;

public class CoreBenefit extends Benefit {

    private final double sumAssured;
    private final int perMilleRate;
    private final int ratingTerm;
    private final CommissionFactors commissionFactors;

    public CoreBenefit(BenefitInputs inputs) {
        super(inputs);
        sumAssured = inputs.getSumAssured();
        perMilleRate = inputs.getPerMilleRate();
        ratingTerm = inputs.getRatingTerm();
        commissionFactors = inputs.getCommissionFactors();
    }

    public double calculatePerMilleCost() {
        double basePerMilleCost = (sumAssured / 1000) * ((perMilleRate / 10.0)) * commissionFactors.lookUp(ratingTerm);

        double perMilleCost = costFactors.applyPerMilleCostFactors(basePerMilleCost);
        result.setPerMilleCost(perMilleCost);
        return perMilleCost;
    }
}
