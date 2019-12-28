package lifechoice;

class BenefitKeyFormatter {

    public static String formatKey(String prefix, int age, SmokerStatus smokerStatus, int term) {
        return String.format("%s%03d%02d%03d", prefix, age, smokerStatus.ordinal(), term);
    }

    public static String formatAgeGapKey(String prefix, int benefitId, boolean increasing, SmokerStatus life1SmokerStatus, SmokerStatus life2SmokerStatus, int ageDifference) {
        String key = "";
        if (life1SmokerStatus.ordinal() == 0 && life2SmokerStatus.ordinal() == 0)
            key = "0";
        if (life1SmokerStatus.ordinal() == 0 && life2SmokerStatus.ordinal() == 1)
            key = "1";
        if (life1SmokerStatus.ordinal() == 1 && life2SmokerStatus.ordinal() == 0)
            key = "2";
        if (life1SmokerStatus.ordinal() == 1 && life2SmokerStatus.ordinal() == 1)
            key = "3";

        return String.format("%s%02d%d%s%02d", prefix, benefitId, increasing ? 1 : 0, key, ageDifference);
    }

    public static String formatSingleKey(String prefix, int benefitId, boolean increasing, SmokerStatus smokerStatus) {
        return String.format("%s%02d%d%d", prefix, benefitId, increasing ? 1 : 0, smokerStatus.ordinal());
    }
}
