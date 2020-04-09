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
    private static int id = 0;
    private static StringBuilder builder = new StringBuilder(String.valueOf(id));

    public static void writeCSV(String fileName, SortedMap<Humanoid, List<Predmet>> map){
        id = 0;
        try (OutputStream outputStream = new FileOutputStream(fileName)) {
            for (Humanoid key : map.keySet()) {
                builder.delete(0,builder.length());
                builder.append(id).append(",human,name,").append(key.getName()).append("\n");
                builder.append(id).append(",human,place,").append(key.getPlace()).append("\n");
                outputStream.write(builder.toString().getBytes());
                builder.delete(0,builder.length());
                List<Predmet> bagazh = map.get(key);
                if (bagazh != null) {
                    for (Predmet predmet : bagazh){
                        if (predmet.getClass().toString().contains("Predmet$Butilka")) {
                            appender("butilka", outputStream, predmet);
                        }
                        if (predmet.getClass().toString().contains("Predmet$Shlyapa")) {
                            appender("shlyapa", outputStream, predmet);
                        }
                        if (predmet.getClass().toString().contains("Sumka")) {
                            appender("sumka", outputStream, predmet);
                        }
                    }
                }
                else {
                    builder.append(id).append(",bagazh,size,null\n");
                    outputStream.write(builder.toString().getBytes());
                    builder.delete(0,builder.length());
                }
                id++;
        }
        }catch (IOException e) {
            System.out.print("File for writing not found,\nTry again");
            e.printStackTrace();
        }

    }
    private static void appender (String s, OutputStream outputStream, Predmet predmet) throws IOException {
        builder.append(id).append(",").append(s).append(",name,").append(predmet.getName()).append("\n").append(id).append(",").append(s).append(",value,").append(predmet.getValue()).append("\n");
        outputStream.write(builder.toString().getBytes());
        builder.delete(0,builder.length());
    }

}

