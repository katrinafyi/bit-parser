package fyi.rina;

public interface BaseParser<T> {
    boolean hasNext();
    T next();
    T peek();
}
