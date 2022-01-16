package fyi.rina;

public interface Parser<T> extends BaseParser<T> {
    T[] nextMany(int n);
    T[] peekMany(int n);
}
