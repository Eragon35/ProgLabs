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
    public static void parser(String fileName, SortedMap map) throws IOException, NullPointerException {

        //TODO rework this shit for ArrayLists
        final LineNumberReader lnr = new LineNumberReader(new FileReader(fileName));
        int linesCount = 0;
        while(null != lnr.readLine()) {
            linesCount++;
        }
        linesCount = linesCount/2;
        String[] names = new String[linesCount];
        Palace[] places = new Palace[linesCount]; // да,да, да — ебучий костыль, но надо было определить как-то массивы имён и мест


        String[] name_b = new String[linesCount];
        String[] name_sh = new String[linesCount];
        String[] name_sm = new String[linesCount];
        Double[] size = new Double[linesCount];
        Double[] ves = new Double[linesCount];
        Double[] mineralka = new Double[linesCount];
        ArrayList<Predmet>[] things = new ArrayList[linesCount];
        ArrayList<Humanoid> people = new ArrayList<>();
        int last_id = 0;

        // initializing
        for (int i = 0; i < linesCount; i++) {
            things[i] = new ArrayList<>();
            name_b[i] = "";
            name_sh[i] = "";
            name_sm[i] = "";
        }

        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(",");
                last_id = Integer.parseInt(line[0]);

                // In case of type we add humans to array "people" and objects to arraylist "things"

                switch (line[1]) {
                    case "human":
                        switch (line[2]){
                            case "name":
                                names[Integer.parseInt(line[0])] = line[3];
                                break;
                            case "place":
                                places[Integer.parseInt(line[0])] = Palace.valueOf(line[3]);
                                break;
                        }
                        break;
                    case "butilka":
                        switch (line[2]) {
                            case "name":
                                name_b[Integer.parseInt(line[0])] = line[3];
                                break;
                            case "value":
                                mineralka[Integer.parseInt(line[0])] = Double.valueOf(line[3]);
                                break;
                        }
                        break;
                    case "shlyapa":
                        switch (line[2]) {
                            case "name":
                                name_sh[Integer.parseInt(line[0])] = line[3];
                                break;
                            case "value":
                                size [Integer.parseInt(line[0])]= Double.valueOf(line[3]);
                                break;
                        }
                        break;
                    case "sumka":
                        switch (line[2]) {
                            case "name":
                                name_sm [Integer.parseInt(line[0])]= line[3];
                                break;
                            case "value":
                                ves [Integer.parseInt(line[0])]= Double.valueOf(line[3]);
                                break;
                        }
                        break;
                }
            }

        } catch (FileNotFoundException | ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        for (int i = 0; i < linesCount; i++){
            if (name_b[i].length() > 0){
                things[i].add(new Predmet.Butilka(name_b[i],mineralka[i]));
            }
            if (name_sh[i].length() > 0){
                things[i].add(new Predmet.Shlyapa(name_sh[i],size[i]));
            }
            if (name_sm[i].length() > 0){
                things[i].add(new Sumka(name_sm[i],ves[i]));
            }
        }
         for (int i = 0; i < (last_id+1); i++){
                people.add(new Humanoid(names[i],places[i]));
                map.put(people.get(i),things[i]);
            }

    }
}
