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

    static int size = 0;

    public static void main(String[] args) {
        System.out.println("\nBeginning of Lab6, variant 11250");
        int port = 2020;
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
        send(request, port);

        System.out.println("send intial request");

        response = read(port);
        assert response != null;
        map = response.getMap();
        size = map.size();

        System.out.println(size);


        //reading command from cli and preparing it to send to server
        while (!s.equals("exit")) {
            System.out.print("Введите команду:");
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();
            if (str.contains("null")) System.out.println("Параметр не может быль null");
            else ConsoleInput.reader(request, str);
            s = str;

//            if command is local do it local
            if (request.getCommand().isLocal()) {
                System.out.println("Ваша команда была выполнена локально");
                ConsoleOutput.write(map, request.getCommand());
            }
//            else send request to server
            else {
                send(request, port);
                //sending Command to server
                //waiting response and do sout it to cli
                response = read(port);
                assert response != null;
                if (response.getCommand().equals(ServerCommand.success)) {
                    map = response.getMap();
                    ConsoleOutput.write(response.getMap(), request.getCommand());
                }
                else System.out.println("Shit happenps, server send error");
            }
        }
    }
    private static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(out));
        os.flush();
        os.writeObject(obj);
        os.flush();
        os.close();
        out.close();
        return out.toByteArray();
    }
    private static void send (Request request, int port){
        try (DatagramSocket socket = new DatagramSocket())
        {      InetAddress address = InetAddress.getLocalHost();
            byte[] sendBuf = serialize(request);
            DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, address, 1111);
            socket.send(packet);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    private static Response read(int port){
        try(DatagramChannel channel = DatagramChannel.open()){
            byte[] recvBuf = new byte[1024];
//            TODO: rework to use port from args[]
            channel.socket().bind(new InetSocketAddress(port+1));
            ByteBuffer buffer = ByteBuffer.wrap(recvBuf);
            buffer.clear();
            channel.receive(buffer);
            System.out.println("Data was income");
            ByteArrayInputStream in = new ByteArrayInputStream(recvBuf);
            ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(in));
            return (Response) is.readObject();
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }
}
