package Lab5;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.SortedMap;


public class InputFile {
    static Scanner scanner;

    public static void parser(String fileName, SortedMap map)
    {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String line[] = scanner.nextLine().split(",");
                map.put(line[0], line[1]);
            }
        } catch (FileNotFoundException | ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }

        try {
            Scanner scanner = new Scanner(new File(fileName));
            scanner.useDelimiter(",");
            while(scanner.hasNext()){
                System.out.print(scanner.next()+"|");
            }
            scanner.close();
        } catch (FileNotFoundException | ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }

    }

}
