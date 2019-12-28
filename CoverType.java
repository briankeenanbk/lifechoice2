package lifechoice;

public enum CoverType {
    SINGLE {
        public double getFactor() {
            return 1.0;
        }
    },
    DUAL {
        public double getFactor() {
            return 1.0;
        }
    },
    JOINT {
        public double getFactor() {
            return 0.945;
        }
    };

    public abstract double getFactor();
}
