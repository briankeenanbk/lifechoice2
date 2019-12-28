package lifechoice;

import lifechoice.entities.MortalityCostInputs;

public class RiderExtraMortalityCost implements ExtraMortalityCalculator {
    private final int extraMortalityRate;

    public RiderExtraMortalityCost(int extraMortalityRate) {
        this.extraMortalityRate = extraMortalityRate;
    }

    public double calculateExtraMortalityCost(MortalityCostInputs mci) {
        return mci.getUnratedPremium() * extraMortalityRate/100;
    }
}
