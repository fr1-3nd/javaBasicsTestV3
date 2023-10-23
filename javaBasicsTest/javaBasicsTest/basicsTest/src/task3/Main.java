package javaBasicsTest.basicsTest.src.task3;

import java.io.File;
import java.util.Map;

import static javaBasicsTest.basicsTest.src.task3.PrimitivesCounter.countPrimitives;

public class Main {
    public static void main(String[] args) {
        /*
        trzeba napisac metodę Map<String, Integer> countPrimitives(File ... javaFiles)
        Zadaniem metody jest policzenie i zwrocenie raportu w postaci mapy: ile jest zmiennych prymitywnych w kazdym
        z plikow java.
        np: intow jest 5, doubli jest 3, booleanow sa 2. itp.
        UWAGA: nalezy sie upewnic ze przekazane pliki istnieja i sa plikami java [jesli nie to je pomijamy]
        UWAGA: wyszukiwanie ma sie dziec rownolegle dla wszystkich plikow.
        UWAGA: pomijamy zmienne w literałach oraz komentarzach tj jak mamy:
       */

        File file1 = new File("Example1.java");
        File file2 = new File("Example2.java");
        File File = new File("File.txt");

        Map<String, Map<String, Integer>> results = countPrimitives(file1, file2, File);
        for (Map.Entry<String, Map<String, Integer>> entry : results.entrySet()) {
            String fileName = entry.getKey();
            Map<String, Integer> primitiveCounts = entry.getValue();

            System.out.println("Name of file : " + fileName);
            System.out.println("Statistic:");
            for (Map.Entry<String, Integer> countEntry : primitiveCounts.entrySet()) {
                String typeName = countEntry.getKey();
                int count = countEntry.getValue();
                System.out.println(typeName + ": " + count);
            }
            System.out.println();
        }
        /* pliki na ktorych testowalem aplikacje sa spakowane do paczki zip w projekcie i znajduja  sie w paczce pod
        wskazanymi nazwami w wierszach 20 , 21 i 22.
         */
    }
}