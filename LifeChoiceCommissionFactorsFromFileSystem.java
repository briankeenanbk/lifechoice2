package lifechoice;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LifeChoiceCommissionFactorsFromFileSystem extends FlatFileProcessor implements CommissionFactors {
    private static final Map<Integer, Double> rate = new HashMap<Integer, Double>();

    public LifeChoiceCommissionFactorsFromFileSystem() {
        super("TestData/LCCommissionFactors.csv");
        try {
            processFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processFile() throws IOException {
        rate.put(0, 0.00);
        BufferedReader br = openFile();
        String line = br.readLine();

        while (line != null) {
            String[] columns = line.split(",");
            Integer key = Integer.parseInt(columns[0]);
            Double value = Double.parseDouble(columns[1]);
            rate.put(key, value);
            line = br.readLine();
        }
        closeFile(br);
    }

    public  double lookUp(Integer key) {
        return rate.get(key);
    }
}
