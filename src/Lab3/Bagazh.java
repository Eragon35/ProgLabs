package Lab3;

public class Bagazh extends Predmet {

//    private boolean put = true;
    public double dirty;
    public Bagazh(String name){
        super(name);
        dirty = Math.random(); //задаём на сколько грязная шляпа от 0 до 1

    }
    public Bagazh(String name, double dirty){
        super(name);
        this.dirty = dirty;

    }
    public Bagazh(String name, boolean put, double dirty){
        super(name, put);
        this.dirty = dirty;

    }

    @Override
    public boolean IfDirty(){
//        if (dirty == 0) return false;
//        else return true;
        return  (dirty != 0);
    }
    @Override
    public double getDirty(){
        return dirty;
    }

    @Override
    public boolean getPuttable(){
        return put;
    }
    @Override
    public void setPuttable(boolean mean){
        this.put = mean;
    }
}
