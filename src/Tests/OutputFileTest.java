package Tests;

import Lab3.Humanoid;
import Lab3.Predmet;
import Lab5.InputFile;

import java.io.File;
import java.util.List;
import java.util.SortedMap;

public class OutputFileTest {
    public static void outputTest(SortedMap<Humanoid, List<Predmet>> map){
        File file = new File("Test.csv");
        System.out.println(file.exists());

    }
}
