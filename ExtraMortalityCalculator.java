package lifechoice;

import lifechoice.entities.MortalityCostInputs;

import java.io.Serializable;

public interface ExtraMortalityCalculator extends Serializable {
    double calculateExtraMortalityCost(MortalityCostInputs mci);
}
