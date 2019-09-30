package Lab3;

public class Tsopa extends Humanoid {
    public Tsopa (String name, Palace place){

        super (name, place);
    }
    @Override
    public void Skill(){
        System.out.println(getName() + " появился в " + getPlace() + " и говорит: 'Так то я Евгений Алексеевич'\n");
    }
}
