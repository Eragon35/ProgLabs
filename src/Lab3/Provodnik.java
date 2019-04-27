package Lab3;

public class Provodnik extends Humanoid {
   private double helpful;
    public Provodnik(String name, Palace place, double helpful){
        super(name,place);
        this.helpful = helpful;
        System.out.println(name + " появился в "+ place + "e\n");
    }
//    @Override
    public void Skill (Humanoid man, Palace place){
        int count = 1;
        double wait = Math.random();
        System.out.println(man.getName() + " пытается вызвать проводника...");
        while ((wait*count) < (1-helpful)) {
            System.out.println("Проводник где-то проёбываеться...");
            count++;
        }
        System.out.println(getName() + " соизволил подойти\n");
        System.out.println(man.getName() + ": А во сколько поезд прибывает в " + place + "?");
        //System.out.println("А " + name + " ему в ответ: Поезд прибывает в " + place + " в " + place.getTime()+"\n");
        if (place.getTime() < 7) {
            System.out.print("Дорогой хуманоид, т.к. поезд приезжает в " + place + " в ");
            System.out.printf("%.2f",place.getTime()) ;
            System.out.println(" то, может быть, я даже смогу Вас разбудить");
            System.out.println("Не «может быть», а обязательно завалите на экзамене, ой - разбудите! – проворчал "+ man.getName()+". – Прошу принять во внимание,\nчто я сплю чрезвычайно крепко и обязательно стану просить дать мне еще поспать,\nно вы меня не слушайте: хватайте прямо за шиворот и принимайте лабы, выталкивайте из аудитории.\n");
        }
        }

    }

