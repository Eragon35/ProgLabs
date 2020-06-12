package Lab6;

import Lab3.Humanoid;
import Lab3.Palace;
import Lab3.Predmet;
import Lab3.Sumka;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;

public class ConsoleV2 {
    private static final List<Predmet> baggage = new LinkedList<>();
    public static void reader(Request cmd, String str, SortedMap<Humanoid, List<Predmet>> map) {
        ClientCommand input = ClientCommand.other;
        if (str.contains("remove_all")) input = ClientCommand.remove_all; // удалить из коллекции все элементы, эквивалентные заданному
        if (str.contains("remove_lower")) input = ClientCommand.remove_lower; // удалить из коллекции все элементы, меньшие, чем заданный
        if (str.contains("remove") && !str.contains("remove_all") && !str.contains("remove_lower"))
            input = ClientCommand.remove; // удалить элемент из коллекции по его ключу
        if (str.contains("show")) input = ClientCommand.show; // вывести в стандартный поток вывода все элементы коллекции в строковом представлении
        if (str.contains("add_if_max")) input = ClientCommand.add_if_max; // добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
        if (str.contains("info")) input = ClientCommand.info; // вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
        if (str.contains("insert")) input = ClientCommand.insert; // добавить новый элемент с заданным ключом
        if (str.contains("exit")) input = ClientCommand.exit; // выход
        if (str.contains("help")) input = ClientCommand.help; // вывод мануала

        String helptxt = "remove_all {element}: удалить из коллекции все элементы, эквивалентные заданному\nremove {String key}: " +
                "удалить элемент из коллекции по его ключу\nshow: вывести в стандартный поток вывода все элементы коллекции в строковом представлении" +
                "\nadd_if_max {element}: добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции" +
                "\ninfo: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\ninsert " +
                "{String key} {element}: добавить новый элемент с заданным ключом\nremove_lower {element}: удалить из коллекции все элементы, меньшие, чем заданный";

        switch (input) {
            case remove_all:
                String[] a1 = str.split("remove_all", 2); //to be honest still not work
                String a11 = a1[1];
                try{
                    baggage.clear();
                    JsonObject jsonObject = new JsonParser().parse(a11).getAsJsonObject();
                    if (a11.contains("butilka")){
                        addBaggage(jsonObject, "butilka");
                    }
                    if (a11.contains("shlyapa")){
                        addBaggage(jsonObject, "shlyapa");
                    }
                    if (a11.contains("sumka")){
                        addBaggage(jsonObject, "sumka");
                    }

                    cmd.setCommand(input);
                    cmd.setBaggage(baggage);
                }catch (Exception e){
                    System.out.println("Ваша кома некорректна, попробуйте ещё раз");
                    e.printStackTrace();
                }
                break;

            case remove:
                String[] a2 = str.split("remove", 2);
                try {
                    JsonObject jsonObject = new JsonParser().parse(a2[1]).getAsJsonObject();
                    String nameSTR = jsonObject.get("name").toString();
                    String[] name1 =nameSTR.split("\"", 3);
                    String name = name1[1];
                    String palaceSTR = jsonObject.get("palace").toString();
                    String[] palace1 = palaceSTR.split("\"", 3);
                    Palace palace = Palace.valueOf(palace1[1]);

                    cmd.setCommand(input);
                    cmd.setHuman(new Humanoid(name, palace));

//                    size = map.size();
//                    map.keySet().removeIf(key -> key.getName().equals(name)&&key.getPlace().equals(palace));
//                    if (!size.equals(map.size())) System.out.println("Персонаж удалён");
//                    else System.out.println("Людей по введёму параметру не обнаружено. Никто не удалён");
                } catch (JsonSyntaxException e){
                    System.out.println("Ошибка при обработке Вашего запроса, попробуйте ещё раз");
                }catch (IllegalArgumentException e){
                    System.out.println("Ошибка при обработке Вашего запроса, подобного места не существует");
                }
                break;

            case show:
                if (map.size()== 0) System.out.println("Элементов в коллекции нет");
                for (Humanoid key : map.keySet()) {
                    System.out.println("Human: name = " + key.getName() + ", place = " + key.getPlace() +";");
                    List<Predmet> bagazh = map.get(key);
                    if (bagazh != null){
                        System.out.println("Baggage size is " + baggage.size());
                        baggage.forEach((Predmet predmet) -> System.out.println("Baggage:" + predmet.getClass() +
                                ", name: " + predmet.getName() + ",  value: " + predmet.getValue()));
                        System.out.println("Baggage hashcode: " + baggage.hashCode());
                    }
                    else System.out.println("Baggage = null;");
                    System.out.println("———————————————————————————————————————");
                }
                break;

            case add_if_max:
                String[] a4 = str.split("add_if_max", 2);
                String a41 = a4[1];
                try{
                    baggage.clear();
                    JsonObject jsonObject = new JsonParser().parse(a41).getAsJsonObject();
                    JsonObject jsonObjectHumanoid = jsonObject.get("human").getAsJsonObject();
                    String nameSTR4 = jsonObjectHumanoid.get("name").toString();
                    String[] name1 = nameSTR4.split("\"", 3);
                    String nameHumanoid = name1[1];
                    String palaceSTR = jsonObjectHumanoid.get("palace").toString();
                    String[] palace1 = palaceSTR.split("\"", 3);
                    Palace palace = Palace.valueOf(palace1[1]);
                    JsonObject jsonObjectBagazh = jsonObject.get("bagazh").getAsJsonObject();
                    if (a41.contains("butilka")){
                        addBaggage(jsonObjectBagazh, "butilka");
                    }
                    if (a41.contains("shlyapa")){
                        addBaggage(jsonObjectBagazh, "shlyapa");
                    }
                    if (a41.contains("sumka")){
                        addBaggage(jsonObjectBagazh, "sumka");
                    }

                    cmd.setCommand(input);
                    cmd.setHuman(new Humanoid(nameHumanoid, palace));
                    cmd.setBaggage(baggage);
                }catch (Exception e){
                    System.out.println("Попробуйте ещё раз");
                    e.printStackTrace();
                }
                break;

            case info:
                System.out.println("Тип: " + map.getClass() + "; Кол-во элементов: " + map.size() + "; Хэш-код: " + map.hashCode());
                break;

            case insert:
                String[] a6 = str.split("insert", 2);
                String a61 = a6[1];
                try{
                    baggage.clear();
                    JsonObject jsonObject = new JsonParser().parse(a6[1]).getAsJsonObject();
                    JsonObject jsonObjectHumanoid = jsonObject.get("human").getAsJsonObject();
                    String nameSTR6 = jsonObjectHumanoid.get("name").toString();
                    String[] name1 =nameSTR6.split("\"", 3);
                    String nameHumanoid = name1[1];
                    String palaceSTR = jsonObjectHumanoid.get("palace").toString();
                    String[] palace1 = palaceSTR.split("\"", 3);
                    Palace palace = Palace.valueOf(palace1[1]);
                    JsonObject jsonObjectBagazh = jsonObject.get("bagazh").getAsJsonObject();
                    if (a61.contains("butilka")){
                        addBaggage(jsonObjectBagazh, "butilka");
                    }
                    if (a61.contains("shlyapa")){
                        addBaggage(jsonObjectBagazh, "shlyapa");
                    }
                    if (a61.contains("sumka")){
                        addBaggage(jsonObjectBagazh, "sumka");
                    }

                    cmd.setCommand(input);
                    cmd.setHuman(new Humanoid(nameHumanoid, palace));
                    cmd.setBaggage(baggage);

//                    map.put(new Humanoid(nameHumanoid, palace), baggage);
//                    System.out.println("Новый элемент был добавлен в коллецию: имя " + nameHumanoid + ", место " + palace);
                }catch (Exception e){
                    System.out.println("Попробуйте ещё раз");
                    e.printStackTrace();
                }
                break;

            case remove_lower:
                String[] a7 = str.split("remove_lower");
                String a71 = a7[1];
                try{
                    baggage.clear();
                    JsonObject jsonObject = new JsonParser().parse(a71).getAsJsonObject();
                    if (a71.contains("butilka")) {
                        addBaggage(jsonObject, "butilka");
                    }
                    if (a71.contains("shlyapa")) {
                        addBaggage(jsonObject, "shlyapa");
                    }
                    if (a71.contains("sumka")) {
                        addBaggage(jsonObject, "");
                    }

                    cmd.setCommand(input);
                    cmd.setBaggage(baggage);

                }catch (Exception e) {
                    System.out.println("Попробуйте ещё раз");
                    e.printStackTrace();
                }
                break;

            case exit:
                System.exit(0);
                break;

            case help:
                System.out.println(helptxt);
                cmd.setCommand(ClientCommand.other);
                break;

            case other:
                System.out.println("Вы не ввели ни одной команды или ввели неправильно, введите 'help' чтобы узнать команды");
                break;

            default:
                System.out.println("АААА, сложна, введите команду ещё раз");
                break;
        }
    }

    private static void addBaggage(JsonObject jsonObject, String s) {
        JsonObject jsonObjectBaggage = jsonObject.get(s).getAsJsonObject();
        String nameSTR = jsonObjectBaggage.get("name").toString();
        String[] names =nameSTR.split("\"", 3);
        String name = names[1];
        String valueSTR = jsonObjectBaggage.get("value").toString();
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
