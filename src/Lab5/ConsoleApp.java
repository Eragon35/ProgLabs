package Lab5;

import Lab3.Humanoid;
import Lab3.Predmet;

import java.io.File;
import java.util.*;

public class
ConsoleApp {
    public static Date datePublic = new Date();
    /**
     * Начало 5-ой лабы вариант: 11180
     * @author head of P3111, Antipin Arsentii
     */
    public static void main(String[] args) throws NullPointerException {


        System.out.println("\nBeginning of Lab5, variant 11180");
        String s = " ";
        SortedMap<Humanoid, List<Predmet>> map = new TreeMap<>();
        datePublic = new Date();

        // консольный ввод
        if (args.length > 0) {
            System.out.println(args[0]);
            File file = new File(args[0]);
            if (file.exists() && !file.isDirectory()) {
                InputFile.parser(args[0], map);
            }
            else System.out.println("File not found.");
        }
        else {
            System.out.println("FileNotFoundException: файл для обращения не задан");
            java.lang.System.exit(0);
        }
        while (!s.equals("exit")) {
            System.out.print("Введите команду:");
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();
            if (str.contains("null")) System.out.println("Параметр не может быль null");
            else Console.reader(map, str);
            //comment next line to turn off changing the file
            OutputFile.writeCSV(args[0], map);
            s = str;
        }
    }
}
