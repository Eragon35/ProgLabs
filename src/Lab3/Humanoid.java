package Lab3;

import java.io.Serializable;

public class Humanoid implements Skill, Comparable<Humanoid>, Serializable {

    private Palace place;
    private String name;
    private int user_id;

    public Humanoid(){}
    public Humanoid(String name, Palace place, int user_id) {
        this.name = name;
        this.place = place;
        this.user_id = user_id;
    }
    public Humanoid (String name, Palace place){
        this.name = name;
        this.place = place;
}
    public void Skill(){ System.out.println("pustoy blank pri otsutsvii otveta"); }
    public Palace getPlace () { return place; }
    public String getName() { return name; }

    public int getUser_id() { return user_id; }
    public void setPlace(Palace p){
        place = p;
    }
    public void setUser_id(int user_id) { this.user_id = user_id; }

    @Override
    public int compareTo(Humanoid h) {
        return name.compareTo(h.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Humanoid humanoid = (Humanoid) o;
        if (user_id != humanoid.user_id) return false;
        if (place != humanoid.place) return false;
        return name != null ? name.equals(humanoid.name) : humanoid.name == null;
    }

    @Override
    public int hashCode() {
        int result = place != null ? place.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + user_id;
        return result;
    }
}

