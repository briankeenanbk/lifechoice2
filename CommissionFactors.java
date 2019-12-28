package lifechoice;

import java.io.Serializable;

public interface CommissionFactors extends Serializable {
    double lookUp(Integer key);
}
