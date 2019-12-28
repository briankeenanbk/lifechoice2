package lifechoice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RatesMapFromFileSystem extends FlatFileProcessor implements RatesMap {
    private final Map<String, Double> rate = new HashMap<String, Double>();

    public RatesMapFromFileSystem(String fileName) {
        super(fileName);
        try {
            processFile(rate);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  double lookUp(String key) {
        if (rate.containsKey(key))
            return rate.get(key);
        return 0.0;
    }
}
