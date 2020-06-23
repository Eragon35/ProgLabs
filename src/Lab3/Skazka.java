package Lab3;

import Lab4.MyNullFoodException;
import java.util.*;

public class Skazka {
    public static void start() {
        class Local {
            void method(){}
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
        porchii = 0;
        try {
            roma.Eat(porchii);
        } catch (MyNullFoodException e) {
            e.Mnenie();
            e.printStackTrace();
        }

//        roma.Peremeshenie(roma, Palace.Train);

        Predmet.Shlyapa chernaya = new Predmet.Shlyapa("Черная", 15);
//        if (chernaya.IfDirty()) {
//            roma.Skill(chernaya);
//        }
        serge.Skill(roma, Palace.Brohenville);
        Disclaimer disclaimer = new Disclaimer();
        disclaimer.Skill();

    }
}

