package Lab3;

import java.util.Random;

public enum Palace {
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

    public static Palace randomPlace() {
        return Palace.values()[new Random().nextInt(Palace.values().length)];
    }
}

