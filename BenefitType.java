package lifechoice;

public enum BenefitType {
    LUMP_SUM_ON_DEATH(1),
    ACCELERATED_SPECIFIED_ILLNESS(2),
    ADDITIONAL_SPECIFIED_ILLNESS(3),
    INCOME_ON_DEATH(4),
    WHOLE_OF_LIFE_CONTINUATION(5),
    ACCIDENT_PAYMENT(6),
    SURGERY(7),
    BROKEN_BONES(8),
    HOSPITAL_CASH(9);

    private final int value;

    BenefitType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
