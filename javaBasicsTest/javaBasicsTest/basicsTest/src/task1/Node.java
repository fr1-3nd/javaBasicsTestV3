package javaBasicsTest.basicsTest.src.task1;

import java.io.Serializable;
import java.util.Objects;

public class Node<T> implements Serializable {
    T value;
    Node<T> next;

    public Node(T value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(value, node.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}