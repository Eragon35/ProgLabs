package Lab3;

public class Bagazh extends Predmet {

    public double dirty;
    public Bagazh(String name){
        super(name);
        dirty = Math.random();

    }
    public Bagazh(String name, double dirty){
        super(name);
        this.dirty = dirty;

    }

}
