package Lab5;

import Lab3.*;
import java.util.*;
import com.google.gson.*;

/**
 * This class has method reader which analyze commands and managing data
 * command's object format is JSON
 */

public class Console {
    private static List<Predmet> baggage = new LinkedList<>();

    /**
     * @param map is SortedMap which contains all data
     * @param str is String which contains a command
     */

    // TODO: 14.10.2019  сделать отлово arrayOutOfBounceException'ов

    public static void reader(SortedMap<Humanoid, List<Predmet>> map, String str) { // ограничение на одну сумку/шляпу/бутылку
        int comand = 0;
        if (str.contains("remove_all")) comand = 1; // удалить из коллекции все элементы, эквивалентные заданному -
        if (str.contains("remove") && !str.contains("remove_all") && !str.contains("remove_lower"))
            comand = comand * 10 + 2; // удалить элемент из коллекции по его ключу +++
        if (str.contains("show")) comand = comand * 10 + 3; // вывести в стандартный поток вывода все элементы коллекции в строковом представлении +++
        if (str.contains("add_if_max")) comand = comand * 10 + 4; // добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции +++
        if (str.contains("info")) comand = comand * 10 + 5; // вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.) +++
        if (str.contains("insert")) comand = comand * 10 + 6; // добавить новый элемент с заданным ключом +++
        if (str.contains("remove_lower")) comand = comand * 10 + 7; // удалить из коллекции все элементы, меньшие, чем заданный +++
        if (str.contains("exit")) comand = comand * 10 + 8; // выход +++
        if (str.contains("help")) comand = comand * 10 + 9; // вывод мануала +++
        if (comand > 10) comand = -1;

        String helptxt = "remove_all {element}: удалить из коллекции все элементы, эквивалентные заданному\nremove {String key}: " +
                "удалить элемент из коллекции по его ключу\nshow: вывести в стандартный поток вывода все элементы коллекции в строковом представлении" +
                "\nadd_if_max {element}: добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции" +
                "\ninfo: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\ninsert " +
                "{String key} {element}: добавить новый элемент с заданным ключом\nremove_lower {element}: удалить из коллекции все элементы, меньшие, чем заданный\n";

        switch (comand) {
            case 1:
                String[] a1 = str.split("remove_all", 2);
                String a11 = a1[1];
                Integer hashCode;
                try{
                    baggage.clear();
                    JsonElement jsonElement = new JsonParser().parse(a11);
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    if (a11.contains("butilka")){
                        addBaggage(jsonObject, "butilka");
                    }
                    if (a11.contains("shlyapa")){
                        addBaggage(jsonObject, "shlyapa");
                    }
                    if (a11.contains("sumka")){
                        addBaggage(jsonObject, "sumka");
                    }
                    if(!a11.contains("sumka") && !a11.contains("shlyapa") && !a11.contains("butilka") && a11.contains("null")) baggage = null;
                    List <Humanoid> keysToDelete = new LinkedList<>();
                    hashCode = baggage.hashCode();
                    for (Humanoid key: map.keySet()) {
                        if (hashCode.equals(map.get(key).hashCode())) keysToDelete.add(key);
                    }
                    for (Humanoid key: keysToDelete){
                        map.remove(key);
                    }

                }catch (Exception e){
                    System.out.println("Попробуйте ещё раз");
                }
                break;

            case 2:
                String[] a2 = str.split("remove", 2);
                try {
                    JsonElement jsonElement = new JsonParser().parse(a2[1]);
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    String nameSTR = jsonObject.get("name").toString();
                    String[] name1 =nameSTR.split("\"", 3);
                    String name = name1[1];
                    String palaceSTR = jsonObject.get("palace").toString();
                    String[] palace1 = palaceSTR.split("\"", 3);
                    Palace palace = Palace.valueOf(palace1[1]);

                    Humanoid keyDelete2 = null;
    //                Humanoid removedHuman = new Humanoid(name, palace);
                    //TODO check if Humanoid has method equals and why i can't remove inside cycle
                    for (Humanoid key : map.keySet()) {
                        if ((key.getName().equals(name))&& (key.getPlace().equals(palace)))
                            keyDelete2 = new Humanoid(key.getName(), key.getPlace());
//                            map.remove(keyDelete2);
                    }
                    map.remove(keyDelete2);
                } catch (JsonSyntaxException e){
                    System.out.println("Ошибка при обработке Вашего запроса, попробуйте ещё раз");
                }catch (IllegalArgumentException e){
                    System.out.println("Ошибка при обработке Вашего запроса, подобного места не существует");
                }
                break;

            case 3:
                if (map.size() == 0) System.out.println("Элементов в коллекции нет");
                for (Humanoid key : map.keySet()) {
                    System.out.println("Human: name = " + key.getName() + ", place = " + key.getPlace() +";");
                    List<Predmet> bagazh = map.get(key);
                    //TODO check lambda function

                    if (bagazh != null){
                        bagazh.forEach((Predmet predmet) -> System.out.println("Baggage:" + predmet.getClass() +
                                ", name: " + predmet.name + ",  value: " + predmet.getValue() + ";"));
//                        for (Predmet predmet : bagazh){
//                            System.out.println("Baggage:" + predmet.getClass() + ", name: " + predmet.name + ",  value: " + predmet.getValue() + ";");
//                        }
                        System.out.println("Baggage hashcode: " + bagazh.hashCode());
                    }
                    else System.out.println("Baggage = null;");
                    System.out.println("———————————————————————————————————————");
                }
                break;

            case 4:
                int maxHashCode;
                int localHashCode;
                String[] a4 = str.split("add_if_max", 2);
                String a41 = a4[1];
                try{
                    baggage.clear();
                    JsonElement jsonElement = new JsonParser().parse(a41);
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    JsonObject jsonObjectHumanoid = (JsonObject) jsonObject.get("human");
                    String nameSTR4 = jsonObjectHumanoid.get("name").toString();
                    String[] name1 =nameSTR4.split("\"", 3);
                    String nameHumanoid = name1[1];
                    String palaceSTR = jsonObjectHumanoid.get("palace").toString();
                    String[] palace1 = palaceSTR.split("\"", 3);
                    Palace palace = Palace.valueOf(palace1[1]);
                    if (a41.contains("butilka")){
                        addBaggage(jsonObject, "butilka");
                    }
                    if (a41.contains("shlyapa")){
                        addBaggage(jsonObject, "shlyapa");
                    }
                    if (a41.contains("sumka")){
                        addBaggage(jsonObject, "sumka");
                    }
                    if (map.size() > 0) {
                        maxHashCode = map.get(map.firstKey()).hashCode();
                        for (Humanoid key : map.keySet()) {
                            localHashCode = map.get(key).hashCode();
                            if (localHashCode > maxHashCode) maxHashCode = localHashCode;
                        }
                        if (baggage.hashCode() > maxHashCode) {
                            map.put(new Humanoid(nameHumanoid, palace), baggage);
                            System.out.println("Новый элемент был добавлен в коллецию: имя " + nameHumanoid + ", место " + palace);
                            for (Predmet predmet : baggage) {
                                System.out.println("Baggage:" + predmet.getClass() + ", name: " + predmet.name + ",  value: " + predmet.getValue() + ";");
                            }
                        }
                    }
                    else {
                        map.put(new Humanoid(nameHumanoid, palace), baggage);
                        System.out.println("Новый элемент был добавлен в коллецию: имя " + nameHumanoid + ", место " + palace);
                        for (Predmet predmet : baggage) {
                            System.out.println("Baggage:" + predmet.getClass() + ", name: " + predmet.name + ",  value: " + predmet.getValue() + ";");
                        }
                    }
                }catch (Exception e){
                    System.out.println("Попробуйте ещё раз");
                }
                break;

            case 5:
                System.out.println("Тип: " + map.getClass() + "; Кол-во элементов: " + map.size() + "; Время инициализации:" +
                        ConsoleApp.datePublic.toString() + "; Хэш-код: " + map.hashCode());
                break;

            case 6:
                String[] a6 = str.split("insert", 2);
                String a61 = a6[1];
                try{
                    baggage.clear();
                    JsonElement jsonElement = new JsonParser().parse(a61);
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    JsonObject jsonObjectHumanoid = (JsonObject) jsonObject.get("human");
                    String nameSTR6 = jsonObjectHumanoid.get("name").toString();
                    String[] name1 =nameSTR6.split("\"", 3);
                    String nameHumanoid = name1[1];
                    String palaceSTR = jsonObjectHumanoid.get("palace").toString();
                    String[] palace1 = palaceSTR.split("\"", 3);
                    Palace palace = Palace.valueOf(palace1[1]);
                    if (a61.contains("butilka")){
                        addBaggage(jsonObject, "butilka");
                    }
                    if (a61.contains("shlyapa")){
                        addBaggage(jsonObject, "shlyapa");
                    }
                    if (a61.contains("sumka")){
                        addBaggage(jsonObject, "sumka");
                    }
                    map.put(new Humanoid(nameHumanoid, palace), baggage);
                    System.out.println("Новый элемент был добавлен в коллецию: имя " + nameHumanoid + ", место " + palace);
                    for (Predmet predmet : baggage){
                        System.out.println("Baggage:" + predmet.getClass() + ", name: " + predmet.name + ",  value: " + predmet.getValue() + ";");
                    }
                    }catch (Exception e){
                        System.out.println("Попробуйте ещё раз");
                        e.printStackTrace();
                    }

                break;

            case 7:
                String[] a7 = str.split("remove_lower");
                String a71 = a7[1];
                int count = 0;
                try{
                    baggage.clear();
                    JsonElement jsonElement7 = new JsonParser().parse(a71);
                    JsonObject jsonObject = jsonElement7.getAsJsonObject();
                    if (a71.contains("butilka")) {
                        addBaggage(jsonObject, "butilka");
                    }
                    if (a71.contains("shlyapa")) {
                        addBaggage(jsonObject, "shlyapa");
                    }
                    if (a71.contains("sumka")) {
                        addBaggage(jsonObject, "");
                    }
                    List <Humanoid> keysToDelete = new LinkedList<>();
                    hashCode = baggage.hashCode();
                    System.out.println("Input baggage hashcode is: " + hashCode);
                    map.keySet().removeIf(key -> key.hashCode() < hashCode);
                    //TODO check correct work of up string (и придумать как считать count) if needed delete keysToDelete
    //                for (Humanoid key: map.keySet()) {
    //                    if (hashCode > (map.get(key).hashCode())) keysToDelete.add(key);
    //                }
    //                for (Humanoid key: keysToDelete) {
    //                    map.remove(key);
    //                    count++;
    //                }
                    System.out.println("Удалено " + count + " элементов");

                }catch (Exception e) {
                    System.out.println("Попробуйте ещё раз");
                    e.printStackTrace();
                }
                break;

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

    private static void addBaggage(JsonObject jsonObject, String s) {
        JsonObject jsonObjectBaggage = (JsonObject) jsonObject.get(s);
        String nameSTR = jsonObjectBaggage.get("name").toString();
        String[] names =nameSTR.split("\"", 3);
        String name = names[1];
        String valueSTR = jsonObject.get("value").toString();
        String[] value1 = valueSTR.split("\"", 3);
        double value = Double.parseDouble(value1[1]);
        switch (s) {
            case "butilka":
                baggage.add(new Predmet.Butilka(name, value));
                break;
            case "shlyapa":
                baggage.add(new Predmet.Shlyapa(name, value));
                break;
            case "sumka" :
                baggage.add(new Sumka(name, value));
                break;
        }
    }
}
