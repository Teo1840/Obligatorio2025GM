package um.edu.uy.tads;

public interface Heap<T extends Comparable<T>> {
    void insert(T element);
    T delete();
    T get();
    int size();
    boolean isEmpty();
}
