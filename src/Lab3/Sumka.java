package Lab3;

public class Sumka extends Predmet {
    public boolean put;
    private double ves;
    double dirty;
    public Sumka (String name, boolean put, double dirty, double ves){
       super(name);
       this.put = put;
       this.dirty = dirty;
       this.ves = ves;
    }
    public Sumka (String name, double dirty, double ves){
        super(name);
        this.ves = ves;
        this.dirty = dirty;
    }
    public Sumka (String name, double ves){
        super(name);
        this.ves = ves;
    }
    @Override
    public double getValue(){
        return ves;
    }
  //  @Override
    public boolean IfDirty(){
        if (dirty == 0) return false;
        else return true;
    }
  //  @Override
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
