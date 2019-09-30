package Lab5;

import Lab3.*;

import java.util.*;
import com.google.gson.*;

/**
 * This class has method reader which analyze commands and managing data
 * command's object format is JSON
 */

public class Console { //persons + items
    private List<Predmet> things = new LinkedList<>();
    private Scanner scanner = new Scanner(System.in);
    private static String spliter = ",";
    private static String helptxt = "remove_all {element}: удалить из коллекции все элементы, эквивалентные заданному\nremove {String key}: " +
            "удалить элемент из коллекции по его ключу\nshow: вывести в стандартный поток вывода все элементы коллекции в строковом представлении" +
            "\nadd_if_max {element}: добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции" +
            "\ninfo: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\ninsert " +
            "{String key} {element}: добавить новый элемент с заданным ключом\nremove_lower {element}: удалить из коллекции все элементы, меньшие, чем заданный\n";


    /**
     * @param map is SortedMap which contains all data
     * @param str is String which contains a command
     */

    public static void reader(SortedMap<Humanoid, List<Predmet>> map, String str) { // сделать отлово arrayOutOfBounceException'ов
        int comand = 0;
        if (str.contains("remove_all")) comand = 1; // удалить из коллекции все элементы, эквивалентные заданному
        if (str.contains("remove") && !str.contains("remove_all") && !str.contains("remove_lower"))
            comand = comand * 10 + 2; // удалить элемент из коллекции по его ключу
        if (str.contains("show"))
            comand = comand * 10 + 3; // вывести в стандартный поток вывода все элементы коллекции в строковом представлении +
        if (str.contains("add_if_max"))
            comand = comand * 10 + 4; // добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
        if (str.contains("info"))
            comand = comand * 10 + 5; // вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
        if (str.contains("insert")) comand = comand * 10 + 6; // добавить новый элемент с заданным ключом
        if (str.contains("remove_lower"))
            comand = comand * 10 + 7; // удалить из коллекции все элементы, меньшие, чем заданный
        if (str.contains("exit")) comand = comand * 10 + 8; // выход +
        if (str.contains("help")) comand = comand * 10 + 9; // вывод мануала +
        if (comand > 10) comand = -1;
        //str.replaceAll(" ","");


        switch (comand) {
            case 1:
                String a1[] = str.split("remove", 1);
                String a11 = element(a1[1]);
//            String a12[] = a1[1].split("\\{");
//            a1 = a12[1].split("}");
//            String a11 = a1[0];
                SortedMap<Humanoid, List<Predmet>> map1 = new TreeMap<>();
                for (Humanoid key : map.keySet()) {
                    if (!a11.equals(map.get(key))) map1.put(key, map.get(key)); //Inserter(map1, key, map.get(key));
                    //map.put(key,map1.get(key));
                }
                map.clear();
                for (Humanoid key : map1.keySet()) {
                    map.put(key, map1.get(key));
                }
                break;
            case 2:
                String[] a2 = str.split("remove", 1);
                map.remove(a2[1]);
                break;
            case 3:
                for (Humanoid key : map.keySet()) {
                    System.out.print(key.getName() + " " + key.getPlace() +": ");
                    List<Predmet> bagazh = map.get(key);
                    if (bagazh != null){
                        for (Predmet predmet : bagazh){
                            System.out.print(predmet.getClass() + "name: " + predmet.name + "value: " + predmet.getValue() + " ");
                        }
                    }
                    else System.out.print("bagaz = null");
                    System.out.println();
                }
                break;
                //TODO case 4 add_if_max
//            case 4:
//                String[] a4 = str.split("add_if_max", 1);
//                if (a4[1].contains(",")) {
//
//                    String[] a41 = str.split(spliter, 2);
//                    map.put(a41[0], (a41[1]));
//                    boolean isMax = true;
//                    if (!a41[1].equals(map.firstKey())) map.remove(a41[0]);
//
//                    int element = map.get(a41[0]).hashCode();
//                    map.remove(a41[0]);
////                System.out.println("hashCode element: " + element);
//                    for (Humanoid key : map.keySet()) {
////                System.out.println("Key: " + key + "; his element hashCode: " + map.get(key).hashCode());
//                        if ((map.get(key).hashCode() > element)) isMax = false;
//                    }
//                    if (isMax) map.put(new Humanoid(a41[0], Palace.valueOf(a41[1])));
//                } else System.out.println("Введите значения ещё раз, через запятую");
//                break;
            case 5:

                System.out.println("Тип: " + map.getClass() + "; Кол-во элементов: " + map.size() + "; Время инициализации:" + Skazka.datePublic.toString() +
                        "; Хэш-код: " + map.hashCode());
                break;
                //TODO insert + remoce_lower
//            case 6:
//                String[] a6 = str.split("insert");
//                if (a6[1].contains(",")) {
//                    String[] a61 = a6[1].split(spliter, 2);
//                    map.put(a61[0], a61[1]);
//                } else System.out.println("Введите значения ещё раз, через запятую");
//                break;
//            case 7:
//                String[] a7 = str.split("remove_lower");
//                System.out.println(a7[1]);
////            str.replace("remove_lower", "");
//                map.put(" ", a7[1]);
//                int element = map.get(" ").hashCode();
//                SortedMap<Humanoid, List<Predmet>> map7 = new TreeMap<>();
//                System.out.println("hashCode element: " + element);
//                for (Humanoid key : map.keySet()) {
////                System.out.println("Key: " + key + "; his element hashCode: " + map.get(key).hashCode());
//                    if (!(map.get(key).hashCode() < element)) map7.put(key, map.get(key));
//                }
//                map.clear();
//                for (Humanoid key : map7.keySet()) {
//                    map.put(key, map7.get(key));
//                }
//                map.remove(" ");
//                break;
            case 8:
                java.lang.System.exit(0);
                break;
            case 9:
                System.out.println(helptxt);
                break;
            case 0:
                System.out.println("Вы не ввели ни одной команды или ввели неправильно, введите 'help' чтобы узнать команды");
                break;
            case (-1):
                System.out.println("Вы ввели несколько команд вместе. Вводите по одной команде за раз" +
                        "\nИли заданные параметры содержат зарезервированные команды. Задайте другое имя");

                break;

            default:
                System.out.println("АААА, сложна, введите команду ещё раз");
                break;
        }

    }

    private static String element(String str) {
        String foo[] = str.split("\\{");
        String bar[] = foo[1].split("}");
        foo = bar[0].split(":");
        bar = foo[1].split("\"");
        System.out.println(bar[1]);
        String baz = bar[0];
        return baz;
    }
}
