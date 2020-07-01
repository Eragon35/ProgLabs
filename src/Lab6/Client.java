package Lab6;

import Lab3.Humanoid;
import Lab3.Predmet;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.*;

public class Client {
    /*
    variant 11250
     */
    static int port = 11111;
    static int size = 0;
    static Response response;
    static DatagramSocket socket;
    static DatagramChannel channel;

    static {
        try {
            socket = new DatagramSocket();
            channel = DatagramChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("\nBeginning of Lab6, variant 11250");
        String s = "";
        Request request = new Request();

        SortedMap<Humanoid, List<Predmet>> map;
        if (args.length == 0){
            System.out.println("You haven't define port, set default 11111");
        }
        else port = Integer.parseInt(args[0]);

//        first connection with server for map initialization

        request.setCommand(ClientCommand.get_map);
        write(request);
        System.out.println("Send initiation request");
        response = read();
        assert response != null;
        map = response.getMap();
        size = map.size();

        //reading command from cli and preparing it to send to server
        while (!s.equals("exit")) {
            System.out.print("Введите команду:");
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();
            if (str.contains("null")) System.out.println("Параметр не может быль null");
            else ConsoleInput.reader(request, str);
            s = str;

            if (request.getCommand().equals(ClientCommand.other)) continue;
            if (request.getCommand().isLocal()) {
                System.out.println("Ваша команда была выполнена локально");
                ConsoleOutput.write(map, request.getCommand());
            }
            else {
                //sending Command to server
                write(request);

                //waiting response and do sout it to cli
                response = read();
                assert response != null;
                if (response.getCommand().equals(ServerCommand.success)) {
                    map = response.getMap();
                    ConsoleOutput.write(response.getMap(), request.getCommand());
                    size = map.size();
                } else System.out.println("Shit happenps, server send error");
            }
        }
    }
        private static void write(Request request){
        try {
            InetAddress address = InetAddress.getLocalHost();
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(bos));
            oos.flush();
            oos.writeObject(request);
            oos.flush();
            //retrieves byte array
            byte[] sendBuf = bos.toByteArray();
            DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, address, 1111);
            socket.send(packet);
            oos.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Response read(){
        try {
            byte[] recvBuf = new byte[1024];
            channel.socket().bind(new InetSocketAddress(11111));
            ByteBuffer buffer = ByteBuffer.wrap(recvBuf);
            buffer.clear();

            channel.receive(buffer);
            ByteArrayInputStream bis = new ByteArrayInputStream(recvBuf);
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(bis));
            Response response = (Response) ois.readObject();
            bis.close();
            ois.close();
            return response;
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }
}
