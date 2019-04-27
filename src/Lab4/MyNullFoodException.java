package Lab4;

public class MyNullFoodException extends NullPointerException {//Exception{
    private Integer num;
    public int getNumber(){return num;}
    public MyNullFoodException (int i){
        //super(msg + "\n" + i + );
        num = i;

    }
    public void Mnenie(){
        System.out.println("!!! ERROR ERROR ERROR!!!\nКак можно заказать " + num + " еды\nтак что Выметайся отсюда, покуда метёлкой не отп*издили\n");

    }
}
