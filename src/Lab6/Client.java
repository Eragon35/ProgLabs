package Lab6;

import Lab3.Humanoid;
import Lab3.Predmet;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    /*
    variant 11250
     */

    public static void main(String[] args) {
        System.out.println("\nBeging of Lab6, variant 11250");
        String s = " ";
        Request cmd = new Request();
        SortedMap<Humanoid, List<Predmet>> map = new TreeMap<>();
        int port = 11111;
        if (args.length == 0){
            System.out.println("You haven't define port, set default 11111");
        }
        else port = Integer.parseInt(args[0]);

//        TODO: add first connection with server for map initialization


        //reading command from cli and preparing it to send to server
        while (!s.equals("exit")) {
            System.out.print("Введите команду:");
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();
            if (str.contains("null")) System.out.println("Параметр не может быль null");
            else ConsoleV2.reader(cmd, str, map);
            s = str;

//            System.out.println(cmd.getCommand().toString() + " " + cmd.getHuman().getName() + " " + cmd.getHuman().getPlace().toString() );

            if (cmd.getCommand().equals(ClientCommand.other)) System.out.println("Ваша команда была выполнена локально. Запрос на сервер не отправлен");
            else {
                try (DatagramSocket socket = new DatagramSocket())
                {      InetAddress address = InetAddress.getLocalHost();
                    byte[] sendBuf = serialize(cmd);
                    DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, address, port);
                    int byteCount = packet.getLength();
                    socket.send(packet);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                //sending Command to server
            }

            //waiting response and do sout it to cli
        }
    }
    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(out));
        os.flush();
        os.writeObject(obj);
        os.flush();
        return out.toByteArray();
    }
}
