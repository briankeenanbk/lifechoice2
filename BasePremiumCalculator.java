package lifechoice;

import java.io.Serializable;

public interface BasePremiumCalculator extends Serializable {
    double calculateBasePremium(Life life);
}
