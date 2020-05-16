package Lab3;

import Lab4.MyDirtyException;
import java.io.Serializable;

public abstract class Predmet extends Bog implements Put, FindDirt, Serializable {
    double value;
    protected boolean put;
    public Predmet(String name, boolean put){
        super(name);
        this.put = put;

    }
    public Predmet (String name){
        super(name);
        this.put = true;
    }
    @Override
    public boolean getPuttable(){
        return put;
    }

    public double getValue(){return value;}

    public static class Shlyapa extends Predmet{
        double dirty;
        double size;
        public Shlyapa(String name, double size){
            super(name);
            this.size = size;

        }
        public Shlyapa(String name){
            super(name);
            dirty = Math.random();
        }
        @Override
        public double getValue(){
            return Double.valueOf(size);
        }
        public boolean IfDirty(){
            if (dirty == 0) return false;
            else return true;
        }
        public double getDirty(){
            if (dirty > 1) {
                dirty = 0;
            }
            return dirty;
        }
        public void checkDirty(){
            if (dirty < 0 || dirty > 1){
                throw new MyDirtyException(this);
            }
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

    public static class Butilka extends Predmet {
        double dirty;
        private double mineralka;
        public Butilka(String name){
            super(name);
            mineralka = -0.05;
        }

        public Butilka (String name, boolean put, double mineralka){
            super(name,put);
            this.mineralka = mineralka;
        }
        public Butilka (String name, double mineralka){
            super(name);
            this.mineralka = mineralka;
        }
        // на неё нужно садиться

        public double getValue(){ return mineralka; }
        public boolean getPuttable(){ return put; }
        public void setPuttable(boolean mean){ this.put = mean; }
        public double getMineralka (){ return mineralka; }
        public double getDirty() { return dirty; }
        public boolean IfDirty() { return false; }
    }

}

