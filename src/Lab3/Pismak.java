package Lab3;

public class Pismak extends Humanoid {
    public Pismak (String name, Palace place){

        super (name, place);
    }
    @Override
    public void Skill(){
        System.out.println(getName() + " появился в " + getPlace() + " и говорит: 'Кокорин и Низовцеев лучшие'\n");
    }
}