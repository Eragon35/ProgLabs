package Lab6;

import Lab3.Humanoid;
import Lab3.Predmet;
import Lab5.Console;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    /*
    variant 11250
     */
    static int port = 11111;

    public static void main(String[] args) {
        System.out.println("\nBeging of Lab6, variant 11250");
        String s = "";
        Request request = new Request();
        Response response;
        SortedMap<Humanoid, List<Predmet>> map;
        if (args.length == 0){
            System.out.println("You haven't define port, set default 11111");
        }
        else port = Integer.parseInt(args[0]);

//        first connection with server for map initialization
        request.setCommand(ClientCommand.get_map);
        send(request);
        response = read();
        map = response.getMap();


        //reading command from cli and preparing it to send to server
        while (!s.equals("exit")) {
            System.out.print("Введите команду:");
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();
            if (str.contains("null")) System.out.println("Параметр не может быль null");
            else ConsoleInput.reader(request, str);
            s = str;

//            System.out.println(cmd.getCommand().toString() + " " + cmd.getHuman().getName() + " " + cmd.getHuman().getPlace().toString() );


//            TODO: rework if statement
            if (request.getCommand().isLocal()) System.out.println("Ваша команда была выполнена локально. Запрос на сервер не отправлен");
            else {
                send(request);
                //sending Command to server
            }

            //waiting response and do sout it to cli
            response = read();
            if (response.getCommand().equals(ServerCommand.success)) ConsoleOutput.write(response.getMap(), request.getCommand());
            else System.out.println("Shit happenps, server send error");
        }
    }
    private static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(out));
        os.flush();
        os.writeObject(obj);
        os.flush();
        return out.toByteArray();
    }
    private static void send (Request request){
        try (DatagramSocket socket = new DatagramSocket())
        {      InetAddress address = InetAddress.getLocalHost();
            byte[] sendBuf = serialize(request);
            DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, address, port);
            socket.send(packet);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
//    TODO: realize read
    private static Response read(){
        return null;
    }
}
