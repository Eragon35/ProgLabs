package Lab7;

import Lab3.Humanoid;
import Lab3.Predmet;
import Lab5.InputFile;
import Lab5.OutputFile;
import Lab6.ClientCommand;
import Lab6.Request;
import Lab6.Response;
import Lab6.ServerHandler;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerV2 {

    public static ArrayDeque<Request> requestQueue = new ArrayDeque<>();
    public static ArrayDeque<Response> responseQueue = new ArrayDeque<>();
    public static int port = 1111;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("\nBeginning of Lab7, variant 777712");
        if (args.length != 0){ port = Integer.parseInt(args[0]); }

        ConcurrentHashMap<Humanoid, List<Predmet>> map = new ConcurrentHashMap<>();
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        ExecutorService cachedPool = Executors.newCachedThreadPool();

        InputFromDB.read(map);
        System.out.println("map size is " + map.size());
        threadPool.execute(new ReadRequest());
        while (true) {
            Thread.sleep(100);
            while (requestQueue.size() > 0){ threadPool.execute(new ServerHandlerV2(requestQueue.poll(), map)); }
            while (responseQueue.size() > 0){ cachedPool.execute(new SendResponse(responseQueue.poll())); }
        }
    }
}
