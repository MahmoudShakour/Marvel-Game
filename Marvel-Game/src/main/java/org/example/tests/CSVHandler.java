package tests;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class CSVHandler {
  static String path = "/home/khaled/Desktop/SandBox/Marvel/Marvel-Game/src/main/java/org/example/tests/";
  // static String path = "/home/mahmoudshakour/Workspace/Marvel-Game/Marvel-Game/src/main/java/org/example/tests/";

  public static ArrayList<ArrayList<String>> load(String filename) {
    BufferedReader br;
    ArrayList<ArrayList<String>> dataTable = new ArrayList<>();
    try {
      br = new BufferedReader(
          new FileReader(path + filename));
      while (true) {
        String line = br.readLine();
        if (line == null)
          break;
        ArrayList<String> row = new ArrayList<>(Arrays.asList(line.split(",")));
        for (int j = 0; j < row.size(); j++)
          row.get(j).trim();
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

  public static void main(String[] args) {
    ArrayList<ArrayList<String>> arr = load("test_abilities.csv");
    System.out.println(arr.size());
  }
}
