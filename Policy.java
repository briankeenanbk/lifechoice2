package lifechoice;

import lifechoice.entities.CalculationResult;
import lifechoice.entities.PolicyInputs;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Policy implements Serializable{
    private final PolicyInputs inputs;
    private final PolicyType policyType;
    private final CoverType coverType;
    private final DateTime startDate;
    private final List<Life> lives = new ArrayList<Life>();
    private final double term;
    private final boolean increasing;

    private final CalculationResult result;

    public Policy(PolicyInputs inputs) {
        this.inputs = inputs;
        this.policyType = inputs.getPolicyType();
        this.startDate = inputs.getStartDate();
        this.coverType = inputs.getCoverType();
        this.term = inputs.getTerm();
        this.increasing = inputs.isIncreasingBenefits();
        result = new CalculationResult();
    }

    public void addLife(Life life){
        lives.add(life);
    }
    
    public void calculate() {
        calculateUnratedPremium();
        calculateExtraMortalityCost();
        calculatePerMilleCost();
    }

    private void calculateUnratedPremium() {
        double unratedPremium = 0.0;
        for (Life life : lives)
            unratedPremium += life.calculateUnratedPremium();
        result.setUnratedPremium(unratedPremium);
    }

    private void calculateExtraMortalityCost() {
        double extraMortality = 0.0;
        for (Life life : lives)
            extraMortality += life.calculateExtraMortalityCost();
        result.setExtraMortalityCost(extraMortality);
    }

    public boolean benefitOnBothLives(Benefit benefit) {
        if (lives.size() == 2) {
            Lives currentLife = benefit.getLife().getId();
            Lives otherLifeId = currentLife.equals(Lives.LIFE1) ? Lives.LIFE2 : Lives.LIFE1;
            Life otherLife = lives.get(0).getId().equals(otherLifeId) ? lives.get(0) : lives.get(1);
            List<Benefit> otherLifesBenefits = otherLife.getBenefits();
            for (Benefit otherLifesBenefit : otherLifesBenefits)
                if (otherLifesBenefit.getId().equals(benefit.getId()))
                    return true;
        }
        return false;
    }

    private void calculatePerMilleCost() {
        double perMilleCost = 0.0;
        for (Life life : lives)
            perMilleCost += life.calculatePerMilleCost();

        result.setPerMilleCost(perMilleCost);
    }

    public PolicyInputs getInputs() {
        return inputs;
    }

    public List<Life> getLives() {
        return lives;
    }

    public CalculationResult getResult() {
        return result;
    }
}
