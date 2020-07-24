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

public class ConsoleInput {

//    TODO: do smth with show, info and help

    private static final List<Predmet> baggage = new LinkedList<>();
    public static void reader(Request cmd, String str) {
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

        switch (input) {
            case remove_all:
                extractBaggage(str.split("remove_all")[1], cmd);
                cmd.setCommand(input);
                break;

            case remove_lower:
                extractBaggage(str.split("remove_lower")[1], cmd);
                cmd.setCommand(input);
                break;

            case remove:
                try {
                    JsonObject jsonObject = new JsonParser()
                            .parse(str.split("remove", 2)[1])
                            .getAsJsonObject();
                    String name = jsonObject
                            .get("name")
                            .toString()
                            .split("\"", 3)
                            [1];
                    Palace palace = Palace
                            .valueOf(jsonObject
                                .get("palace")
                                .toString()
                                .split("\"", 3)
                                [1]
                            );
                    cmd.setCommand(input);
                    cmd.setHuman(new Humanoid(name, palace));

                } catch (JsonSyntaxException e){
                    System.out.println("Ошибка при обработке Вашего запроса, попробуйте ещё раз");
                }catch (IllegalArgumentException e){
                    System.out.println("Ошибка при обработке Вашего запроса, подобного места не существует");
                }
                break;

            case add_if_max:
                extractHB(str.split("add_if_max", 2)[1], cmd);
                cmd.setCommand(input);
                break;

            case insert:
                extractHB(str.split("insert", 2)[1], cmd);
                cmd.setCommand(input);
                break;

            case help:
            case show:
            case info:
                cmd.setCommand(input);
                break;

            case exit:
                cmd.setCommand(input);
//                System.exit(0);
                break;

            case other:
                System.out.println("Вы не ввели ни одной команды или ввели неправильно, введите 'help' чтобы узнать команды");
                cmd.setCommand(ClientCommand.other);
                break;

            default:
                System.out.println("АААА, сложна, введите команду ещё раз");
                break;
        }
    }

    private static void addBaggage(JsonObject jsonObject, String s) {
        JsonObject jsonObjectBaggage = jsonObject
                .get(s)
                .getAsJsonObject();
        String name = jsonObjectBaggage
                .get("name")
                .toString()
                .split("\"", 3)
                [1];
        double value = Double
                .parseDouble(jsonObjectBaggage
                    .get("value")
                    .toString()
                    .split("\"", 3)
                    [1]
                );
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
    private static void extractHB(String json, Request cmd){
        try{
            baggage.clear();
            JsonObject jsonObject = new JsonParser()
                    .parse(json)
                    .getAsJsonObject();
            JsonObject jsonObjectHumanoid = jsonObject
                    .get("human")
                    .getAsJsonObject();
            String name = jsonObjectHumanoid
                    .get("name")
                    .toString()
                    .split("\"", 3)
                    [1];
            Palace place = Palace
                    .valueOf(jsonObjectHumanoid
                        .get("palace")
                        .toString()
                        .split("\"", 3)
                        [1]
                    );
            JsonObject jsonObjectBagazh = jsonObject
                    .get("bagazh")
                    .getAsJsonObject();
            if (json.contains("butilka")){
                addBaggage(jsonObjectBagazh, "butilka");
            }
            if (json.contains("shlyapa")){
                addBaggage(jsonObjectBagazh, "shlyapa");
            }
            if (json.contains("sumka")){
                addBaggage(jsonObjectBagazh, "sumka");
            }

            cmd.setHuman(new Humanoid(name, place));
            cmd.setBaggage(baggage);
        }catch (Exception e){
            System.out.println("Попробуйте ещё раз");
            e.printStackTrace();
        }

    }
    private static void extractBaggage(String json, Request cmd){
        try{
            baggage.clear();
            JsonObject jsonObject = new JsonParser()
                    .parse(json)
                    .getAsJsonObject();
            if (json.contains("butilka")) {
                addBaggage(jsonObject, "butilka");
            }
            if (json.contains("shlyapa")) {
                addBaggage(jsonObject, "shlyapa");
            }
            if (json.contains("sumka")) {
                addBaggage(jsonObject, "");
            }
            cmd.setBaggage(baggage);

        }catch (Exception e) {
            System.out.println("Попробуйте ещё раз");
            e.printStackTrace();
        }
    }
}
