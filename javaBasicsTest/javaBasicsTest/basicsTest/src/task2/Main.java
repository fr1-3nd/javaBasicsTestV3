package javaBasicsTest.basicsTest.src.task2;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
/*
dana jest tablica intow o rozmiarze 200 mln elementow, elementy maja losowe wartosci z pelnego zakresu inta.
- policz ile czasu potrwa liczenie sumy wszystkich elementow uzywajac 1 watku
- policz ile czasu potrwa liczenie sumy wszystkich elementow 2 watkow
- policz ile czasu potrwa liczenie sumy wszystkich elementow 4 watkow
- policz ile czasu potrwa liczenie sumy wszystkich elementow 8 watkow
w komentarzu wypisz raport jak postępuje czas wraz ze wzrostem ilosci watkow. [lub jak spada]
 */
        int arraySize = 200_000_000;
        int[] array = new int[arraySize];
        Random rand = new Random();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < arraySize; i++) {
            array[i] = rand.nextInt();
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        System.out.println("The array has been generated.");
        System.out.println("Generation time: " + elapsedTime + " milliseconds");

        MultiThreadSumCalculator calculator = new MultiThreadSumCalculator(array, 4);
        calculator.calculateSum();


        /* EDIT; synchronized jest po to zeby nie bylo bledow w obliczeniach, poniewaz mamy program wielowatkowy, slowo synchronized sprawia
        ze dostep do sumy jest blokowany jesli jeden z watkow akurat go zmienia wtedy kolejny watek czeka az ten poprzedzajacy
        skonczy. jesli by za braklo slowka synchronized, wtedy mnogloby dojsc do sytuacji gdzie wiele watkow mogloby
        probowac jednoczesnie zmienic  sume, i mogloby to prowadzic do nie prawidlowych wynikow
        */


        /* A wiec u mnie na komputerze wyglada to nastepujaco, przy uzyciu jednego watku sredni czas zsumowania calej
        tablicy to okolo 73ms, przy uzyciu dwoch watkow czas zsumowania wszystkich liczb spada do okolo 43ms,
        przy uzyciu czterech watkow czas sie skraca do 39ms a wiec juz nie znacznie bo tylko 5ms. Przy uzyciu 8
        watkow czas sie praktycznie nie zmienil a wrecz przeciwnie - nieznacznie wydluzyl do okolo 42ms.
        Oznacza to ze prowadzenie obliczen na jednym watku zajmuje najwiecej czasu,a  podwojenie liczby  watkow nie
         oznacza ze program bedzie dokladnie dwu krotnie szybszy poniewaz
        dodajac kollejne watki do rozwiazania zadania zwiekszamy obciazenie procesora synchronizacje watkow itp,
        'przesadzenie' z iloscia watkow w programie moze negatywnie wplynac na szybkosc programu - tak jak zauwazalnie
        jest to u mnie na komputerze gdzie uzycie 4 watkow jest najszybsze a uzycie 8 porownywalne do uzycia dwoch.
        Wynik w duzej mierze zalezy od sprzetu na jakim sie robi test, dla osiagniecia najlepszego wyniku trzeba
        testowac aplikacje na kazdym urzadzeniu indywidualnie, i wybrac optymalna liczbe watkow  dla danego sprzetu.
         */




    }
}