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
            StringBuilder builder = new StringBuilder(String.valueOf(id));
            builder.delete(0,builder.length());
            builder.append(id).append(",human,name,").append(key.getName()).append("\n");
            builder.append(id).append(",human,place,").append(key.getPlace()).append("\n");
            outputStream.write(builder.toString().getBytes());
            builder.delete(0,builder.length());
//            outputStreamWriter.write(String.valueOf(id) + ",human,name" + key.getName() + "\n");
            List<Predmet> bagazh = map.get(key);
            if (bagazh != null) {
                for (Predmet predmet : bagazh){
                    if (predmet.getClass().toString().contains("Predmet$Butilka"))
                    {
                            builder.append(id).append(",butilka,name,").append(predmet.getName()).append("\n").append(id).append(",butilka,mineralka,").append(predmet.getValue()).append("\n");
                            outputStream.write(builder.toString().getBytes());
                            builder.delete(0,builder.length());
                    }

                    if (predmet.getClass().toString().contains("Predmet$Shlyapa"))
                    {
                            builder.append(id).append(",shlyapa,name,").append(predmet.getName()).append("\n").append(id).append(",shlyapa,size,").append(predmet.getValue()).append("\n");
                            outputStream.write(builder.toString().getBytes());
                            builder.delete(0,builder.length());
                    }
                    if (predmet.getClass().toString().contains("Sumka")){
                            builder.append(id).append(",sumka,name,").append(predmet.getName()).append("\n").append(id).append(",sumka,ves,").append(predmet.getValue()).append("\n");
                            outputStream.write(builder.toString().getBytes());
                            builder.delete(0,builder.length());

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
        }catch (IOException e){
            System.out.print("File for writing not found,\nTry again");
            e.printStackTrace();
        }
    }
}

