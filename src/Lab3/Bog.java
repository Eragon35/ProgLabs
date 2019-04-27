package Lab3;

public abstract class Bog {
    private String name;
    public Bog(String name){
        this.name = name;
    }
public String getName(){return name;};
    public Bog(){

    }
    public void setName(String n){
        name = n;
    }
}
