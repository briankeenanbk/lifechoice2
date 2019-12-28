package lifechoice;

import lifechoice.entities.CalculationResult;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Life implements Serializable {
    private final Lives id;
    private final DateTime dateOfBirth;
    private final LifeAge lifeAge;

    private final List<Benefit> benefits = new ArrayList<Benefit>();
    private final List<CoreBenefit> coreBenefits = new ArrayList<CoreBenefit>();

    private final SmokerStatus smokerStatus;
    private final CalculationResult result;

    public Life(Lives id, DateTime policyStartDate, DateTime dateOfBirth, SmokerStatus smokerStatus) {
        this.id = id;
        this.dateOfBirth = dateOfBirth;
        this.smokerStatus = smokerStatus;
        lifeAge = new LifeAge(policyStartDate, dateOfBirth);
        result = new CalculationResult();
    }

    public double calculateUnratedPremium() {
        double unratedPremium = 0.0;
        for (Benefit benefit: benefits)
            unratedPremium += benefit.calculateUnratedPremium(this);
        result.setUnratedPremium(unratedPremium);
        return unratedPremium;
    }

   public double calculateExtraMortalityCost() {
        double extraMortality = 0.0;
        for (Benefit benefit: benefits)
            extraMortality += benefit.calculateExtraMortalityCost(this);
        result.setExtraMortalityCost(extraMortality);
        return extraMortality;
    }

    public double calculatePerMilleCost() {
        double perMilleCost = 0.0;
        for (CoreBenefit coreBenefit: coreBenefits)
            perMilleCost += coreBenefit.calculatePerMilleCost();

        result.setPerMilleCost(perMilleCost);
        return perMilleCost;
    }

    public void addBenefit(Benefit benefit) {
        benefits.add(benefit);
    }

    public void addCoreBenefit(CoreBenefit coreBenefit) {
        coreBenefits.add(coreBenefit);
    }

    public SmokerStatus getSmokerStatus() {
        return smokerStatus;
    }

    public LifeAge getLifeAge() {
        return lifeAge;
    }

    public List<Benefit> getBenefits() {
        return benefits;
    }

    public Lives getId() {
        return id;
    }

    public CalculationResult getResult() {
        return result;
    }
}
