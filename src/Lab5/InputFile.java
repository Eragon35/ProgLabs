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
    public static void parser(String fileName, SortedMap map) throws IOException {
        final LineNumberReader lnr = new LineNumberReader(new FileReader(fileName));
        int linesCount = 0;
        while(null != lnr.readLine()) {
            linesCount++;
        }
        String[] names = new String[linesCount];
        Palace[] places = new Palace[linesCount]; // да,да, да — ебучий костыль, но надо было определить как-то массивы имён и мест
        List<Predmet>[] things = (List<Predmet>[]) new List[linesCount];
        try  {

            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                String line[] = scanner.nextLine().split(",");
                /**
                 * In case of type we add humans to array "people" and objects to arraylist "things"
                 */
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
                        things[Integer.parseInt(line[0])].add(new Predmet.Butilka(line[2],Double.valueOf(line[3])));
                        break;
                    case "shlyapa":
                        things[Integer.parseInt(line[0])].add(new Predmet.Shlyapa(line[2],Integer.valueOf(line[3])));
                        break;
                    case "sumka":
                        things[Integer.parseInt(line[0])].add(new Sumka(line[2],Double.valueOf(line[3])));
                        break;
                    case "bagazh":
                        things[Integer.parseInt(line[0])] = null;
                        break;
                }

            }
            scanner.close();

        } catch (FileNotFoundException | ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        if (things.length != names.length){
            System.out.println("Error in reading file");
        }
        else for (int i = 0; i < names.length;i++ ){
            map.put(new Humanoid(names[i],places[i]),things[i]);
        }
        }


    }
