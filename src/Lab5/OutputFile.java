package Lab5;

import Lab3.Humanoid;
import Lab3.Predmet;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.SortedMap;

/**
 * Output to .csv file based on java.io.OutputStreamWriter
 * has only one method 'writeCSV' which write map to .csv file which contains four fields: id, type, key, value
 */

public class OutputFile {
    /**
     * @param fileName – to whom data would be written
     * @param map – SortedMap from which data would be read
     */
    public static void writeCSV(String fileName, SortedMap<Humanoid, List<Predmet>> map){

        try {
            OutputStream outputStream = new FileOutputStream(fileName);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
//        File file = new File(fileName);
        int id = 0;

        for (Humanoid key : map.keySet()) {
            outputStreamWriter.write(String.valueOf(id) + ",human,name" + key.getName() + "\n");
            //write name + place (it's 2 lines)
            List<Predmet> bagazh = map.get(key);
            if (bagazh != null) {
                for (Predmet predmet : bagazh){
                    switch (predmet.getClass().toString()) {
                        case "Butilka":
                            outputStreamWriter.write(String.valueOf(id) + ",butilka,name" + String.valueOf(predmet.getName()) + "\n" +
                                    String.valueOf(id) + ",butilka,mineralka" + String.valueOf(predmet.getValue()) + "\n");
                            break;
                        case "Shlyapa":
                            outputStreamWriter.write(String.valueOf(id) + ",shlyapa,name" + String.valueOf(predmet.getName()) + "\n" +
                                    String.valueOf(id) + ",shlyapa,size" + String.valueOf(predmet.getValue()) + "\n");
                            break;
                        case "Sumka":
                            outputStreamWriter.write(String.valueOf(id) + ",sumka,name" + String.valueOf(predmet.getName()) + "\n" +
                                    String.valueOf(id) + ",sumka,ves" + String.valueOf(predmet.getValue()) + "\n");
                            break;
                        }
                    }
            }
            else outputStreamWriter.write(String.valueOf(id) + "bagazh, size, null\n");
            id++;
        }
        }catch (IOException e){
            System.out.print("File for writing not found,\nTry again");
            e.printStackTrace();
        }
    }
}

