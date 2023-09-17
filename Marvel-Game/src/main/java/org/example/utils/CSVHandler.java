package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public abstract class CSVHandler {
  public static List<String[]> load(String filename) {
    BufferedReader br;
    List<String[]> dataTable = new ArrayList<>();
    try {
      br = new BufferedReader(
          new InputStreamReader(CSVHandler.class.getResourceAsStream(filename)));
      while (true) {
        String line = br.readLine();
        if (line == null)
          break;
        String[] row = line.split(",");
        for (int j = 0; j < row.length; j++)
          row[j] = row[j].trim();
        dataTable.add(row);
      }
      br.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return dataTable;
  }
}
