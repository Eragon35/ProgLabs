package Lab3;

import Lab4.MyNullFoodException;

public class Skuperfield extends Humanoid {
    private  static String name;
    private double pichevarenie;
    public Predmet.Butilka voda;

    public Skuperfield (String name, Palace place) {

        super(name, place);
        this.name = name;
        pichevarenie = Math.random();

        System.out.println(name + "упал с неба на " + place + " и пьёт водичку для лучшего пищеварения\n");
    }
    public Skuperfield (String name, Palace place, double pichevarenie) {
        // exception na pichevarenie < 0 && > 1
        super(name, place);
        this.name = name;
        this.pichevarenie = pichevarenie;
        System.out.println(name + " заспавнился в " + place + " со здоровым пищеварением равным " + pichevarenie*100 + " %");
        Predmet.Butilka izPodKrana = new Predmet.Butilka("воду из под крана");
        Vodichka(izPodKrana);
    }

    public Skuperfield (String name, Palace place, double pichevarenie, Predmet.Butilka voda) {
        // exception na pichevarenie < 0 && > 1
        super(name, place);
        this.name = name;
        this.pichevarenie = pichevarenie;
        this.voda = voda;
        System.out.println(name + " заспавнился в " + place + " со здоровым пищеварением равным " + pichevarenie * 100 + " %");
        Vodichka(voda);
    }
    public Skuperfield (String name, Palace place, double pichevarenie, Predmet.Butilka voda, Integer porchii) {
        super(name, place);
        this.name = name;
        this.pichevarenie = pichevarenie;
        this.voda = voda;
            }

    public void Vodichka ( Predmet.Butilka voda) {

        int i = 0;
        while ((pichevarenie < 1) && (i < 3)) {
            pichevarenie += voda.getMineralka();
            if (pichevarenie > 1){pichevarenie = 1;}
            if (pichevarenie < 0){pichevarenie = 0;}
            System.out.printf("Пьёт " + voda.getName() + ", пищеварение стало %.2f %%\n", pichevarenie * 100);
            i++;

        }
        if (pichevarenie < 1) {
            System.out.println("Нечего пить минералочку, когда печень пропил");
        }else System.out.println("Вот какое замечательное место и вода\n");
    }
  //  public void setBagazh (String ... bagazh){    }
//    @Override
    public static void Skill(Predmet.Shlyapa hat){
//        public static void Lab3.Skill(Lab3.Bagazh hat, double dirt){
        hat.checkDirty();
        System.out.printf(name + " смотрит на шляпу и такой: 'УУУУУУ с*ка, шляпа грязная на %.2f %%, давай ка размажем грязь'", hat.dirty*100);
        while (hat.dirty < 1){
            hat.dirty *=2;
            System.out.println("");
            if (hat.dirty < 1) System.out.printf("Грязь размазана на %.2f %%, надо размазывать ещё :(", hat.dirty*100);

        }
        System.out.println("Размазали грязь - теперь шляпа равномерно грязная = чистая, а Смысл переполняет меня\n");
        //return 0;

    }
    public void Eat(int i) { //экзепшен на 0
        String msg = "";
        if (i < 1) throw new MyNullFoodException(i);
        System.out.println(name + ": офицант, подайте мне " + i + " порции еды");
        System.out.println("И принялся " + name + " уплетать за " + i + " человек\n");
    }

    public double getPichevarenie() {
        return pichevarenie;
    }
}
