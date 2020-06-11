package Lab3;

import java.io.Serializable;
import java.util.Objects;

public abstract class Predmet extends Bog implements Serializable
{
    double value;
    public Predmet(String name, double value){
        super(name);
        this.value = value;
    }
    public Predmet (String name){
        super(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Predmet predmet = (Predmet) o;
        return (Double.compare(predmet.value, value) == 0 && predmet.name.equals(name));
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    public double getValue(){return value;}

    public static class Shlyapa extends Predmet{
        public Shlyapa(String name, double value) {
            super(name, value);
        }
    }

    public static class Butilka extends Predmet {
        public Butilka (String name, double value){
            super(name, value);
        }

        public Butilka(String name) {
            super(name, 0);
        }
// на неё нужно садиться
    }
}

