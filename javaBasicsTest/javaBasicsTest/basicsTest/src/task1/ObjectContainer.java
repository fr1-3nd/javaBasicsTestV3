package javaBasicsTest.basicsTest.src.task1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class ObjectContainer<T> implements Serializable {
    private Node<T> head;
    private SerializablePredicate<T> filterCondition;
    private boolean readOnly;

    public ObjectContainer(SerializablePredicate<T> filterCondition) {
        this.filterCondition = filterCondition;
        this.head = null;
    }


    public void add(T obj) {
        if (!filterCondition.test(obj)) {
            throw new IllegalArgumentException("Wrong city!");
        }
        if (readOnly) {
            throw new UnsupportedOperationException("You cannot add an item to a container in read-only mode.");
        }
        Node<T> newNode = new Node<>(obj);
        newNode.next = head;
        head = newNode;
    }


    public ObjectContainer<T> getWithFilter(Predicate<T> filterCondition) {
        ObjectContainer<T> result = new ObjectContainer<>(x -> true);
        Node<T> current = head;
        while (current != null) {
            if (filterCondition.test(current.value)) {
                result.add(current.value);
            }
            current = current.next;
        }
        return result;
    }

    public List<T> getWithFilterAsList(Predicate<T> filterCondition) {
        List<T> list = new ArrayList<>();
        Node<T> current = head;
        while (current != null) {
            if (filterCondition.test(current.value)) {
                list.add(current.value);
            }
            current = current.next;
        }
        return list;
    }

    public void removeIf(Predicate<T> removeCondition) {
        if (head == null) {
            return;
        }
        while (head != null && removeCondition.test(head.value)) {
            head = head.next;
        }

        Node<T> current = head;
        while (current != null && current.next != null) {
            if (removeCondition.test(current.next.value)) {
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }
    }

    public void storeToFile(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void storeToFile(String fileName, Predicate<T> filterCondition, Function<T, String> formatFunction) {
        try (FileWriter writer = new FileWriter(fileName)) {
            Node<T> current = head;
            while (current != null) {
                if (filterCondition.test(current.value)) {
                    String formattedValue = formatFunction.apply(current.value);
                    writer.write(formattedValue + System.lineSeparator());
                }
                current = current.next;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static <T> ObjectContainer<T> fromFile(String fileName) {
        try (FileInputStream fileInput = new FileInputStream(fileName);
             ObjectInputStream objectInput = new ObjectInputStream(fileInput)) {

            return (ObjectContainer<T>) objectInput.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public void print() {
        Node curr = head;
        while (curr != null) {
            System.out.println(curr.value);
            curr = curr.next;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectContainer<?> that = (ObjectContainer<?>) o;
        return readOnly == that.readOnly && Objects.equals(head, that.head);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, readOnly);
    }
}