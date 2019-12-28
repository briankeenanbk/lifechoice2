package lifechoice.entities;

import lifechoice.Policy;
import lifechoice.RatesMap;

public class PremiumCalculatorInputs {
    private Policy policy;
    private RatesMap rates;
    private double sumAssured;
    private int maxAge;

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public RatesMap getRates() {
        return rates;
    }

    public void setRates(RatesMap rates) {
        this.rates = rates;
    }

    public double getSumAssured() {
        return sumAssured;
    }

    public void setSumAssured(double sumAssured) {
        this.sumAssured = sumAssured;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }
}
