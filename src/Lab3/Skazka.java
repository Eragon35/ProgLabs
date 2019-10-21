package Lab3;

import Lab4.MyNullFoodException;
import Lab5.Console;
import Lab5.InputFile;
import Lab5.OutputFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

public class Skazka {
    public static Date datePublic = new Date();
//    private static int humanoidCounter = 0;
    public static void start() {
        class Local {
            void method(){}
        }
        Local local = new Local();
        local.method();
    }


    public static void main(String[] args) throws Exception { //map + things
        int porchii;
        Pismak alexey_evgenievich = new Pismak("Pismak", Palace.ITMO);
        alexey_evgenievich.Skill();
        Tsopa evgenij_alexeevich = new Tsopa("Tsopa", Palace.ITMO);
        evgenij_alexeevich.Skill();
        Predmet.Butilka borjomi = new Predmet.Butilka("Из под крана", 0.32);
        Provodnik serge = new Provodnik("Проводник Серега Бесмертный", Palace.Train, 0.15);
        Skuperfield roma = new Skuperfield("Кокорин", Palace.Restoran, 0.16, borjomi);
//        Lab3.Skuperfield roma = new Lab3.Skuperfield("Кокорин", Lab3.Palace.Restoran, 0.16, borjomi, 0 );
        porchii = 0;
        try {
            roma.Eat(porchii);
//        } finally {
        } catch (MyNullFoodException ex) {
            ex.Mnenie();
            ex.printStackTrace();
        }

        roma.Peremeshenie(roma, Palace.Train);

        Predmet.Shlyapa chernaya = new Predmet.Shlyapa("Черная", 15);
        if (chernaya.IfDirty()) {
            roma.Skill(chernaya);
        }
        serge.Skill(roma, Palace.Brohenville);
        Disclaimer disclaimer = new Disclaimer();
        disclaimer.Skill();

        /**
         * Начало 5-ой лабы вариант: 11180
         * @author head of P3111, Antipin Arsentii
         */
        System.out.println("\nBeging of Lab5, variant 11180");
//        start();
        String s = " ";
        String file;
        SortedMap<Humanoid, List<Predmet>> map = new TreeMap<Humanoid, List<Predmet>>();
        Date date = new Date();

        List<Predmet> things = new LinkedList<Predmet>();
        SortedMap<Humanoid, List<Predmet>> map_test = new TreeMap<>();
        things.add(borjomi);
        things.add(chernaya);
        Lab3.Humanoid tourist = new Lab3.Humanoid("Gena", Lab3.Palace.ITMO);
        map.put(tourist, things);
        Sumka chemodan = new Sumka("чемодан",15);
        map.put(alexey_evgenievich, things);
        List <Predmet> things2 = new LinkedList<Predmet>();
        things2.add(chemodan);
        map.put(new Humanoid("Левушка", Palace.Vyazma), things2);
        map.put(new Humanoid( "Обама", Palace.USA), things2);
        datePublic = date;



        /**
        * консольный ввод
         */
        OutputFile.writeCSV("test.csv", map);
        InputFile.parser("test.csv", map_test);
        OutputFile.writeCSV("test_1.csv", map_test);
        if (args.length > 0) {
            InputFile.parser(args[0], map);
            file = args[0];
        }
        else {
            System.out.println("FileNotFoundException: файл для обращения не задан");
            java.lang.System.exit(0);
        }
        while (!s.equals("exit")) {
            System.out.print("Введите команду:");
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();
            //str = str.replaceAll("\\s", "");
            //System.out.println(str);
            Console.reader(map, str);
//            OutputFile.writeCSV(file, map);

            s = str;
        }

    }
}

