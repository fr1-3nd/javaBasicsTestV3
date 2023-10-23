package javaBasicsTest.basicsTest.src.task1;

import java.io.*;
import java.util.Objects;

public class Person implements Serializable {
    private String name;
    private String city;
    private int age;

    public Person(String name, String city, int age) {
        this.name = name;
        this.city = city;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(name, person.name) && Objects.equals(city, person.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city, age);
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(name);
        out.writeInt(age);
        out.writeObject(city);
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        age = in.readInt();
        city = (String) in.readObject();
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", city=" + city + ", age=" + age + "]";
    }}
