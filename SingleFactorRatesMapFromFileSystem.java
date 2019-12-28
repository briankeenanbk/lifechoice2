package lifechoice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SingleFactorRatesMapFromFileSystem extends FlatFileProcessor implements RatesMap {
    private static final Map<String, Double> rate = new HashMap<String, Double>();

    public SingleFactorRatesMapFromFileSystem() {
        super("TestData/SingleFactorRates.csv");
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

