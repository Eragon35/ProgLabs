package Lab3;

import java.util.Random;

public enum Palace {
    //    public class Lab3.Palace extends java.lang.Enum {
    Train(-1),
    SanKomarik(6.00),
    Brohenville(3.00),
    ITMO(13.37),
    USA(0),
    Vyazma(0),
    Restoran(-1),
    Dagestan(14.88);

    //exception na "-1" i minuti > 59
    private double time;

    Palace(double time) {
        this.time = time;
    }

    public double getTime() {
        return this.time;
    }

    public String toString() {
        switch (this) {
            case Train:
                return "Train";
            case Dagestan:
                return "Dagestan";
            case ITMO:
                return "ITMO";
            case Brohenville:
                return "Brohenville";
            case SanKomarik:
                return "SanKomarik";
            case Restoran:
                return "Restoran";
            case USA:
                return "USA";
            case Vyazma:
                return "Vyazma";
        }
        return null;
    }

    public static Palace randomPlace() {
        return Palace.values()[new Random().nextInt(Palace.values().length)];
    }
}

