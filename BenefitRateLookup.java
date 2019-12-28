package lifechoice;

class BenefitRateLookup {

    public static double getRateNow(String prefix, Life life, double benefitTerm, RatesMap rates) {
        String keyNow = BenefitKeyFormatter.formatKey(prefix, life.getLifeAge().getAgeAtStartDate(), life.getSmokerStatus(), (int) Math.floor(benefitTerm));
        double rateNow = rates.lookUp(keyNow);

        String keyNext = BenefitKeyFormatter.formatKey(prefix, life.getLifeAge().getAgeNextInQuarterAfterStartDate(), life.getSmokerStatus(), (int) Math.floor(benefitTerm));
        double rateNext = rates.lookUp(keyNext);

        return life.getLifeAge().getAgeFraction() * (rateNext - rateNow) + rateNow;
    }

    public static double getRateNext(String prefix, Life life, double benefitTerm, RatesMap rates) {
        String keyNow = BenefitKeyFormatter.formatKey(prefix, life.getLifeAge().getAgeAtStartDate(), life.getSmokerStatus(), (int) Math.ceil(benefitTerm));
        double rateNow = rates.lookUp(keyNow);

        String keyNext = BenefitKeyFormatter.formatKey(prefix, life.getLifeAge().getAgeNextInQuarterAfterStartDate(), life.getSmokerStatus(), (int) Math.floor(benefitTerm));
        double rateNext = rates.lookUp(keyNext);

        return life.getLifeAge().getAgeFraction() * (rateNext - rateNow) + rateNow;
    }


}
