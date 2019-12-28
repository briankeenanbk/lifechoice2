package lifechoice;

public enum PolicyType {
    YOU_AND_FAMILY {
        public String getRatePrefix(boolean increasing) {
            return increasing ? "12" : "11";
        }

        public String getAgeGapPrefix() {
            return "21";
        }

        public String getSinglePrefix(boolean increasing) {
            return "31";
        }
    },
    HOME {
        public String getRatePrefix(boolean increasing) {
            return increasing ? "13" : "13";
        }

        public String getAgeGapPrefix() {
            return "22";
        }

        public String getSinglePrefix(boolean increasing) {
            return "31";
        }
    },
    ASSETS {
        public String getRatePrefix(boolean increasing) {
            return increasing ? "12" : "11";
        }

        public String getAgeGapPrefix() {
            return "21";
        }

        public String getSinglePrefix(boolean increasing) {
            return increasing ? "32" : "31";
        }
    };

    public abstract String getRatePrefix(boolean increasing);
    public abstract String getAgeGapPrefix();
    public abstract String getSinglePrefix(boolean increasing);
}
