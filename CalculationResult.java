package lifechoice.entities;

import java.io.Serializable;

public class CalculationResult implements Serializable {
    private double unratedPremium;
    private double extraMortalityCost;
    private double perMilleCost;

    public double getUnratedPremium() {
        return unratedPremium;
    }

    public void setUnratedPremium(double unratedPremium) {
        this.unratedPremium = unratedPremium;
    }

    public double getExtraMortalityCost() {
        return extraMortalityCost;
    }

    public void setExtraMortalityCost(double extraMortalityCost) {
        this.extraMortalityCost = extraMortalityCost;
    }

    public double getPerMilleCost() {
        return perMilleCost;
    }

    public void setPerMilleCost(double perMilleCost) {
        this.perMilleCost = perMilleCost;
    }
}
