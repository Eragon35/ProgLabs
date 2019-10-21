package Lab5;

import Lab3.*;

import java.util.*;

import com.google.gson.*;

import javax.swing.*;

/**
 * This class has method reader which analyze commands and managing data
 * command's object format is JSON
 */

public class Console {
    private static GsonBuilder builder = new GsonBuilder();
//    builder.registerTypeAdapter(Humanoid.class, new CustomConverter());
    private static Gson gsonHumanoid = builder.create();
    private static Gson gson = new Gson(); //persons + items
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
        //str.replaceAll(" ","");


        switch (comand) {
            case 1:
                String a1[] = str.split("remove_all", 2);
                String a11 = a1[1];
                Integer hashCode;
                Integer hash;
                try{
                    List<Predmet> baggage = new LinkedList<Predmet>();
                    JsonElement jsonElement = new JsonParser().parse(a11);
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    if (a11.contains("butilka")){
                        JsonObject jsonObjectButilka = (JsonObject) jsonObject.get("butilka");
                        String nameSTR = jsonObjectButilka.get("name").toString();
                        String[] name1 =nameSTR.split("\"", 3);
                        String name = name1[1];
                        String valueSTR = jsonObjectButilka.get("value").toString();
                        String[] value1 = valueSTR.split("\"", 3);
                        Double value = Double.valueOf(value1[1]);
                        baggage.add(new Predmet.Butilka(name, value));
                    }
                    if (a11.contains("shlyapa")){
                        JsonObject jsonObjectShlyapa = (JsonObject) jsonObject.get("shlyapa");
                        String nameSTR = jsonObjectShlyapa.get("name").toString();
                        String[] name1 =nameSTR.split("\"", 3);
                        String name = name1[1];
                        String valueSTR = jsonObjectShlyapa.get("value").toString();
                        String[] value1 = valueSTR.split("\"", 3);
                        Double value = Double.valueOf(value1[1]);
                        baggage.add(new Predmet.Shlyapa(name, value));
                    }
                    if (a11.contains("sumka")){
                        JsonObject jsonObjectSumka = (JsonObject) jsonObject.get("sumka");
                        String nameSTR = jsonObjectSumka.get("name").toString();
                        String[] name1 =nameSTR.split("\"", 3);
                        String name = name1[1];
                        String valueSTR = jsonObjectSumka.get("value").toString();
                        String[] value1 = valueSTR.split("\"", 3);
                        Double value = Double.valueOf(value1[1]);
                        baggage.add(new Sumka(name, value));
                    }
                    if(!a11.contains("sumka") && !a11.contains("shlyapa") && !a11.contains("butilka") && a11.contains("null")) baggage = null;
                    List <Humanoid> keysToDelete = new LinkedList<Humanoid>();
                    hashCode = baggage.hashCode();
                    for (Humanoid key: map.keySet()) {
                        if (hashCode.equals(map.get(key).hashCode())) keysToDelete.add(key);
//                        else hash = map.get(key).hashCode();
//                        if ((map.get(key) == null) && (baggage == null)) keysToDelete.add(key);
//                        if (map.get(key).equals(baggage)) {
//                            keysToDelete.add(key);
//                            System.out.println("Элемент удалён");
//                        }
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
                    for (Humanoid key : map.keySet()) {
                        if ((key.getName().equals(name))&& (key.getPlace().equals(palace)))
                            keyDelete2 = new Humanoid(key.getName(), key.getPlace());
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
                    if (bagazh != null){
                        for (Predmet predmet : bagazh){
                            System.out.println("Baggage:" + predmet.getClass() + ", name: " + predmet.name + ",  value: " + predmet.getValue() + ";");
                        }
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
                    List<Predmet> baggage4 = new LinkedList<Predmet>();
                    JsonElement jsonElement = new JsonParser().parse(a41);
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    JsonObject jsonObjectHumanoid = (JsonObject) jsonObject.get("human");
                    String nameSTR4 = jsonObjectHumanoid.get("name").toString();
                    String[] name1 =nameSTR4.split("\"", 3);
                    String nameHumanoid = name1[1];
                    String palaceSTR = jsonObjectHumanoid.get("palace").toString();
                    String[] palace1 = palaceSTR.split("\"", 3);
                    Palace palace = Palace.valueOf(palace1[1]);
                    JsonObject jsonObjectBagazh = (JsonObject) jsonObject.get("bagazh");
                    if (a41.contains("butilka")){
                        JsonObject jsonObjectButilka = (JsonObject) jsonObjectBagazh.get("butilka");
                        String nameSTR = jsonObjectButilka.get("name").toString();
                        String[] name4 =nameSTR.split("\"", 3);
                        String name = name4[1];
                        String valueSTR = jsonObjectButilka.get("value").toString();
                        String[] value1 = valueSTR.split("\"", 3);
                        Double value = Double.valueOf(value1[1]);
                        baggage4.add(new Predmet.Butilka(name, value));
                    }
                    if (a41.contains("shlyapa")){
                        JsonObject jsonObjectShlyapa = (JsonObject) jsonObjectBagazh.get("shlyapa");
                        String nameSTR = jsonObjectShlyapa.get("name").toString();
                        String[] name4 =nameSTR.split("\"", 3);
                        String name = name4[1];
                        String valueSTR = jsonObjectShlyapa.get("value").toString();
                        String[] value1 = valueSTR.split("\"", 3);
                        Double value = Double.valueOf(value1[1]);
                        baggage4.add(new Predmet.Shlyapa(name, value));
                    }
                    if (a41.contains("sumka")){
                        JsonObject jsonObjectSumka = (JsonObject) jsonObjectBagazh.get("sumka");
                        String nameSTR = jsonObjectSumka.get("name").toString();
                        String[] name6 =nameSTR.split("\"", 3);
                        String name = name1[1];
                        String valueSTR = jsonObjectSumka.get("value").toString();
                        String[] value1 = valueSTR.split("\"", 3);
                        Double value = Double.valueOf(value1[1]);
                        baggage4.add(new Sumka(name, value));
                    }
                    if (map.size() > 0) {
                        maxHashCode = map.get(map.firstKey()).hashCode();
                        for (Humanoid key : map.keySet()) {
                            localHashCode = map.get(key).hashCode();
                            if (localHashCode > maxHashCode) maxHashCode = localHashCode;
                        }
                        if (baggage4.hashCode() > maxHashCode) {
                            map.put(new Humanoid(nameHumanoid, palace), baggage4);
                            System.out.println("Новый элемент был добавлен в коллецию: имя " + nameHumanoid + ", место " + palace);
                            for (Predmet predmet : baggage4) {
                                System.out.println("Baggage:" + predmet.getClass() + ", name: " + predmet.name + ",  value: " + predmet.getValue() + ";");
                            }
                        }
                    }
                    else {
                        map.put(new Humanoid(nameHumanoid, palace), baggage4);
                        System.out.println("Новый элемент был добавлен в коллецию: имя " + nameHumanoid + ", место " + palace);
                        for (Predmet predmet : baggage4) {
                            System.out.println("Baggage:" + predmet.getClass() + ", name: " + predmet.name + ",  value: " + predmet.getValue() + ";");
                        }
                    }
                }catch (Exception e){
                    System.out.println("Попробуйте ещё раз");
                }
                break;
            case 5:

                System.out.println("Тип: " + map.getClass() + "; Кол-во элементов: " + map.size() + "; Время инициализации:" + Skazka.datePublic.toString() +
                        "; Хэш-код: " + map.hashCode());
                break;
            case 6:
                String a6[] = str.split("insert", 2);
                String a61 = a6[1];
                try{
                    List<Predmet> baggage6 = new LinkedList<Predmet>();
                    JsonElement jsonElement = new JsonParser().parse(a61);
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    JsonObject jsonObjectHumanoid = (JsonObject) jsonObject.get("human");
                    String nameSTR6 = jsonObjectHumanoid.get("name").toString();
                    String[] name1 =nameSTR6.split("\"", 3);
                    String nameHumanoid = name1[1];
                    String palaceSTR = jsonObjectHumanoid.get("palace").toString();
                    String[] palace1 = palaceSTR.split("\"", 3);
                    Palace palace = Palace.valueOf(palace1[1]);
                    JsonObject jsonObjectBagazh = (JsonObject) jsonObject.get("bagazh");
                    if (a61.contains("butilka")){
                        JsonObject jsonObjectButilka = (JsonObject) jsonObjectBagazh.get("butilka");
                        String nameSTR = jsonObjectButilka.get("name").toString();
                        String[] name6 =nameSTR.split("\"", 3);
                        String name = name6[1];
                        String valueSTR = jsonObjectButilka.get("value").toString();
                        String[] value1 = valueSTR.split("\"", 3);
                        Double value = Double.valueOf(value1[1]);
                        baggage6.add(new Predmet.Butilka(name, value));
                    }
                    if (a61.contains("shlyapa")){
                        JsonObject jsonObjectShlyapa = (JsonObject) jsonObjectBagazh.get("shlyapa");
                        String nameSTR = jsonObjectShlyapa.get("name").toString();
                        String[] name6 =nameSTR.split("\"", 3);
                        String name = name6[1];
                        String valueSTR = jsonObjectShlyapa.get("value").toString();
                        String[] value1 = valueSTR.split("\"", 3);
                        Double value = Double.valueOf(value1[1]);
                        baggage6.add(new Predmet.Shlyapa(name, value));
                    }
                    if (a61.contains("sumka")){
                        JsonObject jsonObjectSumka = (JsonObject) jsonObjectBagazh.get("sumka");
                        String nameSTR = jsonObjectSumka.get("name").toString();
                        String[] name6 =nameSTR.split("\"", 3);
                        String name = name6[1];
                        String valueSTR = jsonObjectSumka.get("value").toString();
                        String[] value1 = valueSTR.split("\"", 3);
                        Double value = Double.valueOf(value1[1]);
                        baggage6.add(new Sumka(name, value));
                    }
                    map.put(new Humanoid(nameHumanoid, palace), baggage6);
                    System.out.println("Новый элемент был добавлен в коллецию: имя " + nameHumanoid + ", место " + palace);
                    for (Predmet predmet : baggage6){
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
            Integer hashCode7;
            Integer hash7;
            int count = 0;
            try{
                List<Predmet> baggage7 = new LinkedList<Predmet>();
                JsonElement jsonElement7 = new JsonParser().parse(a71);
                JsonObject jsonObject7 = jsonElement7.getAsJsonObject();
                if (a71.contains("butilka")){
                    JsonObject jsonObjectButilka = (JsonObject) jsonObject7.get("butilka");
                    String nameSTR = jsonObjectButilka.get("name").toString();
                    String[] name7 =nameSTR.split("\"", 3);
                    String name = name7[1];
                    String valueSTR = jsonObjectButilka.get("value").toString();
                    String[] value1 = valueSTR.split("\"", 3);
                    Double value = Double.valueOf(value1[1]);
                    baggage7.add(new Predmet.Butilka(name, value));
                }
                if (a71.contains("shlyapa")){
                    JsonObject jsonObjectShlyapa = (JsonObject) jsonObject7.get("shlyapa");
                    String nameSTR = jsonObjectShlyapa.get("name").toString();
                    String[] name7 =nameSTR.split("\"", 3);
                    String name = name7[1];
                    String valueSTR = jsonObjectShlyapa.get("value").toString();
                    String[] value1 = valueSTR.split("\"", 3);
                    Double value = Double.valueOf(value1[1]);
                    baggage7.add(new Predmet.Shlyapa(name, value));
                }
                if (a71.contains("sumka")){
                    JsonObject jsonObjectSumka = (JsonObject) jsonObject7.get("sumka");
                    String nameSTR = jsonObjectSumka.get("name").toString();
                    String[] name7 =nameSTR.split("\"", 3);
                    String name = name7[1];
                    String valueSTR = jsonObjectSumka.get("value").toString();
                    String[] value1 = valueSTR.split("\"", 3);
                    Double value = Double.valueOf(value1[1]);
                    baggage7.add(new Sumka(name, value));
                }
                List <Humanoid> keysToDelete = new LinkedList<Humanoid>();
                hashCode = baggage7.hashCode();
                System.out.println("Input baggage hashcode is: " + hashCode);
                for (Humanoid key: map.keySet()) {
                    if (hashCode > (map.get(key).hashCode())) keysToDelete.add(key);
                }
                for (Humanoid key: keysToDelete){
                    map.remove(key);
                    count++;
                }
                System.out.println("Удалено " + count + " элементов");

            }catch (Exception e){
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

    private static String element(String str) {
        String foo[] = str.split("\\{");
        String bar[] = foo[1].split("}");
        foo = bar[0].split(":");
        bar = foo[1].split("\"");
        System.out.println(bar[1]);
        String baz = bar[0];
        return baz;
    }

    public static void setGson(Gson gson) {
        Console.gson = gson;
    }
}
