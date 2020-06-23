package Lab6;


import Lab3.Humanoid;
import Lab3.Predmet;
import Lab5.InputFile;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.*;

public class Server {
    static int port = 11111;

    //TODO add checking from cli for saving and turn off server and loop
/* BufferdReader stdin = new BufferedReader(new
InputStreamReader(System.in));
String str;
while((str = stdin.readLine()).length() != 0)
{
out.println(str);
}*/
    public static void main(String[] args) {
        System.out.println("\nBeging of Lab6, variant 11250");
        String filename = "test_1.csv";
        SortedMap<Humanoid, List<Predmet>> map = new TreeMap<>();
        Request rqst;
        Response rsp = new Response();

        if (args.length == 0){
            System.out.println("You haven't define port, set default 11111");
        }
        else port = Integer.parseInt(args[0]);


        //writing collection from file
        File file = new File(filename);
        if (file.exists() && !file.isDirectory()) {
            InputFile.parser(filename, map);
        }
        else {
            System.out.println("File not found.");
            java.lang.System.exit(0);
        }

        //waiting Command from client
        rqst = receive();
        assert rqst != null;
        System.out.println("Income: " + rqst.getCommand());

//        List<Predmet> baggage = new LinkedList<>();
//        Humanoid hm = rqst.getHuman();
//        baggage = rqst.getBaggage();
//        System.out.println(rqst.getCommand().toString() /*+ " " + hm.getName() + " " + hm.getPlace().toString()  + " " + baggage.size()*/);


        //do request on server
        try {
            ServerHandler.reader(rqst, map, rsp);
        }catch (Exception e){
            rsp.setCommand(ServerCommand.error);
            e.printStackTrace();
        }

        //send response to client
        write(rsp);
    }
    private static Request deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(in));
        return (Request) is.readObject();
    }
    private static Request receive (){
        try ( DatagramSocket socket = new DatagramSocket(port))
        {
            byte[] recvBuf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
            socket.receive(packet);
            return deserialize(recvBuf);
        }
        catch (ClassNotFoundException | IOException e)
        {
            e.printStackTrace();
        }
        //TODO: think about more beautiful return
        return null;
    }
//    TODO:realize write
    private static void write(Response rsp) {
        try(DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            channel.bind(null);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(rsp);
            oos.flush();
            oos.close();
            bos.close();
            byte[] sendBuf = bos.toByteArray();

            channel.send(ByteBuffer.wrap(sendBuf), new InetSocketAddress(InetAddress.getLocalHost(), 11111));
            System.out.println("Response send");
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}
