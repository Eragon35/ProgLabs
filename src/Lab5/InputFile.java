package Lab5;

import Lab3.Humanoid;
import Lab3.Palace;
import Lab3.Predmet;
import Lab3.Sumka;

import java.io.*;
import java.util.*;

/**
* Input from .csv file based on java.util.Scanner
 * has only one method 'parser' which read data from .csv file which contains four fields: id, type, key, value
 */

public class InputFile {
/** @param fileName – from whom would be data read
 * @param map – SortedMap in which data would be written
 */
    public static void parser(String fileName, SortedMap<Humanoid, List<Predmet>> map) throws NullPointerException {
        String name = null;
        Palace place;
        Map <Integer, List<Predmet>> baggage = new HashMap<>();
        ArrayList<Humanoid> people = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(",");
                switch (line[1]) {
                    case "human":
                        switch (line[2]){
                            case "name":
                                name = line[3];
                                break;
                            case "place":
                                place = Palace.valueOf(line[3]);
                                people.add(new Humanoid(name, place));
                                break;
                        }
                        break;
                    case "butilka":
                        switch (line[2]) {
                            case "name":
                                name  = line[3];
                                break;
                            case "value":
                                createAndAdd(Integer.parseInt(line[0]),
                                        new Predmet.Butilka(name, Double.parseDouble(line[3])),
                                        baggage);
                                break;
                        }
                        break;
                    case "shlyapa":
                        switch (line[2]) {
                            case "name":
                                name = line[3];
                                break;
                            case "value":
                                createAndAdd(Integer.parseInt(line[0]),
                                        new Predmet.Shlyapa(name, Double.parseDouble(line[3])),
                                        baggage);
                                break;
                        }
                        break;
                    case "sumka":
                        switch (line[2]) {
                            case "name":
                                name = line[3];
                                break;
                            case "value":
                                createAndAdd(Integer.parseInt(line[0]),
                                        new Sumka(name, Double.parseDouble(line[3])),
                                        baggage);
                                break;
                        }
                        break;
                }
            }

        } catch (FileNotFoundException | ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
         for (int i = 0; i < people.size(); i++){
                map.put(people.get(i),baggage.get(i));
            }

    }
        private static void createAndAdd (int index, Predmet predmet, Map <Integer, List<Predmet>> baggage){
        if (!baggage.containsKey(index)) baggage.put(index, new ArrayList<>());
        baggage.get(index).add(predmet);
    }
}
