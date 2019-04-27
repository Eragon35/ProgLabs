package Lab3;

import Lab4.MyNullFoodException;
import Lab5.Console;
import Lab5.InputFile;
import com.google.gson.Gson;

import java.util.*;

public class Skazka {
    public static Date datePublic = new Date();

//    public enum Lab3.Palace {
//        //    public class Lab3.Palace extends java.lang.Enum {
//        Train(-1),
//        SanKomarik(6.00),
//        Brohenville(3.00),
//        ITMO(13.37),
//        Restoran(-1),
//        Dagestan(14.88);
//
//        //exception na "-1" i minuti > 59
//        private double time;
//
//        Lab3.Palace(double time) {
//            this.time = time;
//        }
//
//        public double getTime() {
//            return this.time;
//        }
//
//        public String toString() {
//            switch (this) {
//                case Train:
//                    return "Train";
//                case Dagestan:
//                    return "Dagestan";
//                case ITMO:
//                    return "ITMO";
//                case Brohenville:
//                    return "Brohenville";
//                case SanKomarik:
//                    return "SanKomarik";
//                case Restoran:
//                    return "Restoran";
//            }
//            return null;
//        }
//
//        public static Lab3.Palace randomPlace() {
//            return Lab3.Palace.values()[new Random().nextInt(Lab3.Palace.values().length)];
//        }
//    }

    public static void start() {
        class Local {
            void method() {


                /**
                 *  Доп изменить private - поле и вывести значения полей
                 **/
                try {
//                    Field field = roma.getClass().getDeclaredField("pichevarenie"); //возвращает все поля
//                    field.setAccessible(true); // даёт возможность изменять приват поля
//                    field.setDouble(roma, 1900);
//                    System.out.println("\n\t" + field.get(roma) + "\n");
////                    Field field1 = roma.getClass().getDeclaredField("name");
////                    field1.setAccessible(true);
////                    field1.set(roma, "Арсентий");
//                } catch (Exception ignore) {
//                }
//
//                         try       {
//                    Field[] fields = roma.getClass().getDeclaredFields();
//                    for (Field f : fields){
//                                 f.setAccessible(true);
//                    }
//                    for (Field f : fields)
//                    {
//                        Class type = f.getType();
//                        String name = f.getName();
//                        System.out.print("\t");
//                        String modifiers = Modifier.toString(f.getModifiers());
//                        if (modifiers.length() > 0) System.out.print(modifiers + " ");
//                        System.out.println(type.getName() + " " + name + "; " + f.get(roma));
//                    }
                } catch ( /*IllegalAccessException e*/ Exception e) {
                    e.printStackTrace();
                }
            }
        }
        Local local = new Local();
        local.method();
    }


    public static void main(String[] args) { //map + things
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
            System.out.println();
        }

        roma.Peremeshenie(roma, Palace.Train);

        Predmet.Shlyapa chernaya = new Predmet.Shlyapa("Черная", 0.15);
        if (chernaya.IfDirty()) {
            roma.Skill(chernaya);
        }
        serge.Skill(roma, Palace.Brohenville);
        Disclaimer disclaimer = new Disclaimer();
        disclaimer.Skill();

        /**
         * Начало 5-ой лабы
         */
//        start();
        String s = " ";
        Gson gson = new Gson();
        List<Predmet> things = new LinkedList<>();
        SortedMap<Humanoid, List<Predmet>> map = new TreeMap<>();
        Date date = new Date();
        if (args.length > 0) InputFile.parser(args[0], map);
        datePublic = date;
//        Lab3.Predmet items[] = {borjomi, chernaya};
//        things.add(borjomi);
//        things.add(chernaya);
//        Lab3.Humanoid tourist = new Lab3.Humanoid("Gena", Lab3.Palace.ITMO);
//        map.put(tourist, things);
//        map.put(alexey_evgenievich, things);
        Console.insert(map, "Левушка", Palace.Vyazma);
        System.out.println("Добавляем второго");
        Console.insert(map, "Обама", Palace.USA);
        System.out.println(map.size());
        System.out.println(map.firstKey().getName() + " second " + map.lastKey().getName());

        /**
        * консольный ввод
         */
                s = "exit";
        while (!s.equals("exit")) {
            System.out.print("Введите команду:");
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();
            //str = str.replaceAll("\\s", "");
            //System.out.println(str);
            Console.reader(map, str);

            s = str;
        }

    }
}

