package Lab6;

import Lab3.Humanoid;
import Lab3.Predmet;

import java.util.List;
import java.util.SortedMap;

public class ConsoleOutput {
    public static void write(SortedMap<Humanoid, List<Predmet>> map, ClientCommand command){
        String help = "remove_all {element}: удалить из коллекции все элементы, эквивалентные заданному\nremove {String key}: " +
                "удалить элемент из коллекции по его ключу\nshow: вывести в стандартный поток вывода все элементы коллекции в строковом представлении" +
                "\nadd_if_max {element}: добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции" +
                "\ninfo: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\ninsert " +
                "{String key} {element}: добавить новый элемент с заданным ключом\nremove_lower {element}: удалить из коллекции все элементы, меньшие, чем заданный";
        switch (command){
            case show:
                if (map.size()== 0) System.out.println("Элементов в коллекции нет");
                for (Humanoid key : map.keySet()) {
                    System.out.println("Human: name = " + key.getName() + ", place = " + key.getPlace() +";");
                    List<Predmet> baggage = map.get(key);
                    if (baggage != null){
                        System.out.println("Baggage size is " + baggage.size());
                        baggage.forEach((Predmet predmet) -> System.out.println("Baggage:" + predmet.getClass() +
                                ", name: " + predmet.getName() + ",  value: " + predmet.getValue()));
                        System.out.println("Baggage hashcode: " + baggage.hashCode());
                    }
                    else System.out.println("Baggage = null;");
                    System.out.println("———————————————————————————————————————");
                }
                break;
            case info:
                System.out.println("Тип: " + map.getClass() + "; Кол-во элементов: " + map.size() + "; Хэш-код: " + map.hashCode());
                break;
            case help:
                System.out.println(help);
                break;
            case remove_all:
            case remove_lower:
                System.out.println("Удалено " + (Client.size - map.size()) + " элементов");
                break;
            case insert:
                if(map.size() > Client.size) System.out.println("Новый элемент был добавлен в коллецию");
                else System.out.println("Ошибка приложения. Звоните в службу поддержки +7(812)237-10-82");
                break;
            case add_if_max:
                if(map.size() > Client.size) System.out.println("Новый элемент был добавлен в коллецию");
                else if(map.size() == Client.size) System.out.println("Новый элемент не был добавлен в коллецию");
                else System.out.println("Ошибка приложения. Звоните в службу поддержки +7(812)237-10-82");
            case remove:
                if(map.size() < Client.size) System.out.println("Элемени из коллекции удален");
                else if(map.size() == Client.size) System.out.println("Людей по введёму параметру не обнаружено. Никто не удалён");
                else System.out.println("Ошибка приложения. Звоните в службу поддержки +7(812)237-10-82");
                break;
            default:
                System.out.println("Ошибка приложения. Звоните в службу поддержки +7(812)237-10-82");
                break;

        }
    }

}
