package Lab3;

import java.io.Serializable;

public class Humanoid implements Skill, Comparable<Humanoid>, Serializable {

    private Palace place;
    private String name;

    public Humanoid(){}

    public Humanoid (String name, Palace place){
        this.name = name;
        this.place = place;

}
    public void Skill(){
        System.out.println("pustoy blank pri otsutsvii otveta");
    }
    public Palace getPlace () {return place;}
    public String getName() {return name;}
    public void setPlace(Palace p){
        place = p;
    }

//    public Palace Peremeshenie(Humanoid h, Palace p){ //exception na train
////        System.out.print(h.name + " peremestils9 iz " + h.place);
//
//        if (p == Palace.Dagestan) System.out.println(h.getName() + " iz " + h.getPlace() +" na zanigenoy vishnevoy Priore приехал в ДАГИСТАН\n");
//        else if (p == Palace.Train) {
//            System.out.println(h.getName() + " sel v poezd na stancii " + h.getPlace()+ "\n");}
//        else System.out.println(h.getName() + " peremestils9 iz " + h.getPlace() + " v " + p);
//        h.setPlace(p);
//        return p;
//    }

    @Override
    public int compareTo(Humanoid h) {
        return name.compareTo(h.getName());
    }
}

