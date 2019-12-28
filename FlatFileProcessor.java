package lifechoice;

import java.io.*;
import java.util.Map;

abstract class FlatFileProcessor implements Serializable {
    private final String fileName;

    FlatFileProcessor(String fileName) {
        this.fileName = fileName;
    }

    void processFile(Map<String, Double> rate) throws IOException {
        BufferedReader br = openFile();
        String line = br.readLine();

        while (line != null) {
            String[] columns = line.split(",");
            String key = columns[0];
            Double value = Double.parseDouble(columns[1]);
            rate.put(key, value);
            line = br.readLine();
        }
        closeFile(br);
    }

    BufferedReader openFile() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return br;
    }

    void closeFile(BufferedReader br) {
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
