package Lab7;

import Lab3.Humanoid;
import Lab3.Predmet;
import Lab5.InputFile;
import Lab6.Request;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultiThreadServer {
    static ExecutorService executor = Executors.newFixedThreadPool(8);

    public static void main(String[] args) {
        System.out.println("\nBeginning of Lab7, variant xxx");
        SortedMap<Humanoid, List<Predmet>> map = new TreeMap<>();
        List<Future<Request>> futures = new ArrayList<>(); //List of incoming requests
        //TODO: change list to queue if queue > 0, start handler thread

        //read collection from db but now for checking use old variant - read from file
        File file = new File("test_1.csv");
        if (file.exists() && !file.isDirectory()) {
            InputFile.parser("test_1.csv", map);
            System.out.println("Read collection");
        }
        else {
            System.out.println("File not found.");
            System.exit(0);
        }

        Callable<Request> callable = new ReadRequest();
        Future<Request> future;
        future = executor.submit(callable);
        futures.add(future);

    }
}
