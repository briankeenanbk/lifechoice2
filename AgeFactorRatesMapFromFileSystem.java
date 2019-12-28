package lifechoice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AgeFactorRatesMapFromFileSystem extends FlatFileProcessor implements RatesMap {
    private static final Map<String, Double> rate = new HashMap<String, Double>();

    public AgeFactorRatesMapFromFileSystem() {
        super("TestData/AgeFactorRates.csv");
        try {
            processFile(rate);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double lookUp(String key) {
        if (rate.containsKey(key))
            return rate.get(key);
        return 1.0;
    }
}



