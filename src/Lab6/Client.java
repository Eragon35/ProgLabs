package Lab6;

import Lab3.Humanoid;
import Lab3.Predmet;

import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

public class Client {
    /*
    variant 11250
     */

    public static void main(String[] args) {
        System.out.println("\nBeging of Lab6, variant 11250");
        String s = " ";
        Command cmd = new Command();
        SortedMap<Humanoid, List<Predmet>> map = new TreeMap<>();


        //reading command from cli and preparing it to send to server
        while (!s.equals("exit")) {
            System.out.print("Введите команду:");
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();
            if (str.contains("null")) System.out.println("Параметр не может быль null");
            else ConsoleV2.reader(cmd, str);
            s = str;
            if (cmd.getCommand().equals(ClientCommand.other)) System.out.println("Ваша команда была выполнена локально. Запрос на сервер не отправлен");
            else {
                //sending Command to server
            }

            //waiting response and do sout it to cli
        }



    }
//    public static void main(String[] args) throws IOException, InterruptedException {
//        try (DatagramSocket socket = new DatagramSocket()) {
//            DatagramPacket packet = encodePacket("Hello world!");
////            packet.setSocketAddress(new InetSocketAddress(11111));
//            socket.send(packet);
////            while (!socket.isConnected()){
////                System.out.println("waiting");
////                Thread.sleep(1000);
////            }
////            System.out.println("Connected");
//        }
//
//    }
//    private static DatagramPacket encodePacket(String text) throws UnknownHostException {
//        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
//        return new DatagramPacket(bytes, bytes.length, InetAddress.getLocalHost(), 11111);
//    }
}
