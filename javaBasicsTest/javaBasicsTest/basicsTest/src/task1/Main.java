package javaBasicsTest.basicsTest.src.task1;

import java.util.List;

public class Main {
    public static void main(String[] args) {


        System.out.println("-----------------------------------------------------------------------------------------");
        ObjectContainer<Person> peopleFromWarsaw = new ObjectContainer<>(p -> p.getCity().equals("Warsaw"));
        peopleFromWarsaw.add(new Person("Jan", "Warsaw", 30));
        peopleFromWarsaw.add(new Person("Weronika", "Warsaw", 20));
        //peopleFromWarsaw.add(new Person("Waldek", "Monaco", 34));

        peopleFromWarsaw.print();

        System.out.println("-----------------------------------------------------------------------------------------");
        List<Person> females = peopleFromWarsaw.getWithFilterAsList(p -> p.getName().endsWith("a"));

        for (Person female : females) {
            System.out.println(female);
        }

        System.out.println("-----------------------------------------------------------------------------------------");
        peopleFromWarsaw.removeIf(p -> p.getAge() > 50);

        peopleFromWarsaw.print();

        System.out.println("-----------------------------------------------------------------------------------------");
        peopleFromWarsaw.storeToFile("youngPeopleFromWarsaw.txt", p -> p.getAge() < 30, p -> p.getName() + ";" + p.getAge() + ";" + p.getCity());

        System.out.println("-----------------------------------------------------------------------------------------");

        peopleFromWarsaw.storeToFile("warsawPeople.txt");

        System.out.println("-----------------------------------------------------------------------------------------");
        ObjectContainer<Person> peopleFromWarsawFromFile = ObjectContainer.fromFile("warsawPeople.txt");

        System.out.println(peopleFromWarsawFromFile.equals(peopleFromWarsaw));
    }
}