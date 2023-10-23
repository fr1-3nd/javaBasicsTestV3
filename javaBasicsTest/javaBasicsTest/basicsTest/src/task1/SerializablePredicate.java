package javaBasicsTest.basicsTest.src.task1;

import java.io.Serializable;
import java.util.function.Predicate;

public interface SerializablePredicate<T> extends Predicate<T>, Serializable {
}