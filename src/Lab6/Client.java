package Lab6;

import Lab3.Humanoid;
import Lab3.Predmet;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Client {
    /*
    variant 11250
     */

    public static void main(String[] args) {
        System.out.println("\nBeging of Lab6, variant 11250");
        String s = " ";
        Command cmd = new Command();
        SortedMap<Humanoid, List<Predmet>> map = new TreeMap<>();
        int port = 11111;
        if (args.length == 0){
            System.out.println("You haven't define port, set default 11111");
        }
        else port = Integer.parseInt(args[0]);


        //reading command from cli and preparing it to send to server
        while (!s.equals("exit")) {
            System.out.print("Введите команду:");
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();
            if (str.contains("null")) System.out.println("Параметр не может быль null");
            else ConsoleV2.reader(cmd, str);
            s = str;

            System.out.println(cmd.getCommand().toString() + " " + cmd.getHuman().getName() + " " + cmd.getHuman().getPlace().toString() );

            if (cmd.getCommand().equals(ClientCommand.other)) System.out.println("Ваша команда была выполнена локально. Запрос на сервер не отправлен");
            else {
                try (DatagramSocket socket = new DatagramSocket())
                {      InetAddress address = InetAddress.getLocalHost();
                    ByteArrayOutputStream byteStream = new ByteArrayOutputStream(1024);
                    ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(byteStream));
                    os.flush();
                    os.writeObject(cmd);
                    os.flush();
                    //retrieves byte array
                    byte[] sendBuf = byteStream.toByteArray();
                    DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, address, port);
                    int byteCount = packet.getLength();
                    socket.send(packet);
                    os.close();
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
}
